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
public class TextContent implements DocumentContent {

    private final List<byte[]> lines;
    private LineBreak lineBreak;

    TextContent(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Cannot initialize a document with a null array of bytes");
        }
        lineBreak = resolveLineBreak(bytes);
        lines = new LineSplitter().split(bytes, lineBreak);
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for (int i = 0; i < lines.size(); i++) {
            appendLineToBuffer(buffer, i);
        }
        return buffer.toByteArray();
    }

    private void appendLineToBuffer(ByteArrayOutputStream buffer, int i) {
        try {
            buffer.write(lines.get(i));
            if (!isLastLine(i)) {
                buffer.write(lineBreak.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isLastLine(int i) {
        return i == lines.size() - 1;
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

    @Override
    public LineRange getLineRange(int startIndex, int endIndex) {
        return new LineRange(lines.subList(startIndex, endIndex), startIndex, endIndex);
    }

    @Override
    public LineRange getLineRangeAll() {
        return getLineRange(0, lines.size());
    }
}
