package org.lukas.javach.document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.lukas.javach.document.LineBreak.isKnownLineBreak;
import static org.lukas.javach.document.LineBreak.resolveLineBreak;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
class TextDocument implements Document {

    private final List<byte[]> lines;
    private LineBreak lineBreak;

    TextDocument(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Cannot initialize a document with a null array of bytes");
        }
        lineBreak = resolveLineBreak(bytes);
        lines = new LineSplitter().split(bytes, lineBreak);
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        for (int i = 0; i < lines.size(); i++) {
            try {
                bytes.write(lines.get(i));
                if (i < lines.size() - 1) {
                    bytes.write(lineBreak.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes.toByteArray();
    }

    @Override
    public LineBreak getLineBreak() {
        return lineBreak;
    }

    @Override
    public int getNumberOfLines() {
        return lines.size();
    }

    @Override
    public List<byte[]> getLines() {
        return lines;
    }

    @Override
    public void setLineBreak(LineBreak lineBreak) {
        if (!isKnownLineBreak(lineBreak)) {
            throw new IllegalArgumentException("Tried to set null value as line break");
        }
        this.lineBreak = lineBreak;
    }
}
