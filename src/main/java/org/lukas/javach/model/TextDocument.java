package org.lukas.javach.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.lukas.javach.model.LineBreak.*;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
class TextDocument implements Document  {

    private static final int CARRIAGE_RETURN = 0x0D;
    private static final int LINE_FEED = 0x0A;

    private List<byte[]> lines;
    private LineBreak lineBreak;

    TextDocument(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Cannot initialize a document with a null array of bytes");
        }
        lineBreak = resolveLineBreak(bytes);
        lines = splitToLines(bytes);
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

    private List<byte[]> splitToLines(byte[] bytes) {
        if (bytes.length == 0) {
            return Collections.emptyList();
        }
        int[] lineBreakIndexes = getLineBrakeIndexes(bytes);
        List<byte[]> lines = new ArrayList<>();

        int lastLineStartIndex = addAllLinesButLast(bytes, lines, lineBreakIndexes);
        if (!lines.isEmpty()) {
            lines.add(Arrays.copyOfRange(bytes, lastLineStartIndex, bytes.length));
        } else {
            lines.add(bytes);
        }
        return lines;
    }

    private int addAllLinesButLast(byte[] bytes, List<byte[]> lines, int[] lineBreakIndexes) {
        int fromIndex = 0;
        int toIndex;
        for (int i = 0; i < lineBreakIndexes.length; i = i + 2) {
            toIndex = lineBreakIndexes[i];
            lines.add(Arrays.copyOfRange(bytes, fromIndex, toIndex));
            fromIndex = lineBreakIndexes[i + 1];
        }
        return fromIndex;
    }

    private int[] getLineBrakeIndexes(byte[] bytes) {
        if (WINDOWS_LINE_BREAK.equals(lineBreak)) {
            return calculateIndexesForWindowsLineBreak(bytes);
        }
        if (UNIX_LINE_BREAK.equals(lineBreak)) {
            return calculateIndexesForUnixLineBreak(bytes);
        }
        if (OLD_MAC_LINE_BREAK.equals(lineBreak)) {
            return calculateIndexesForOldMacLineBreak(bytes);
        }
        throw new IllegalStateException("Line break for document not set");
    }

    private int[] calculateIndexesForOldMacLineBreak(byte[] bytes) {
        List<Integer> lineBreaks = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == CARRIAGE_RETURN) {
                lineBreaks.add(i);
                lineBreaks.add(i + 1);
            }
        }
        return toIntArray(lineBreaks);
    }

    private int[] calculateIndexesForWindowsLineBreak(byte[] bytes) {
        List<Integer> lineBreaks = new ArrayList<>();
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == CARRIAGE_RETURN && bytes[i + 1] == LINE_FEED) {
                lineBreaks.add(i);
                lineBreaks.add(i + 2);
            }
        }
        return toIntArray(lineBreaks);
    }

    private int[] toIntArray(List<Integer> lineBreaks) {
        return lineBreaks.stream().mapToInt(i -> i).toArray();
    }

    private int[] calculateIndexesForUnixLineBreak(byte[] bytes) {
        List<Integer> lineBreaks = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == LINE_FEED) {
                lineBreaks.add(i);
                lineBreaks.add(i + 1);
            }
        }
        return toIntArray(lineBreaks);
    }

    @Override
    public void setLineBreak(LineBreak lineBreak) {
        if (!isKnownLineBreak(lineBreak)) {
            throw new IllegalArgumentException("Tried to set null value as line break");
        }
        this.lineBreak = lineBreak;
    }

    private boolean isKnownLineBreak(LineBreak lineBreak) {
        return WINDOWS_LINE_BREAK.equals(lineBreak)
                || UNIX_LINE_BREAK.equals(lineBreak)
                || OLD_MAC_LINE_BREAK.equals(lineBreak);
    }
}
