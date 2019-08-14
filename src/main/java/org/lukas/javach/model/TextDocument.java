package org.lukas.javach.model;

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
    private static final int NOT_INITIALIZED = -1;

    private byte[] bytes;
    private LineBreak lineBreak;
    private int numberOfLines = NOT_INITIALIZED;

    TextDocument(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Cannot initialize a document with a null array of bytes");
        }
        this.bytes = bytes;

        lineBreak = resolveLineBreak(bytes);
    }

    private LineBreak resolveLineBreak(byte[] bytes) {
        if (bytes.length == 0) {
            return LineBreak.systemDefaultLineBreak();
        }
        LineBreak firstLineBreak = findFirstLineBreak();
        if (firstLineBreak != UNDEFINED_LINE_BREAK) {
            return firstLineBreak;
        }
        LineBreak lineBreakAtLastByte = findLineBreakAtLastByte();
        if (lineBreakAtLastByte != UNDEFINED_LINE_BREAK) {
            return lineBreakAtLastByte;
        }
        return LineBreak.systemDefaultLineBreak();
    }

    private LineBreak findFirstLineBreak() {
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == CARRIAGE_RETURN) {
                if (bytes[i + 1] == LINE_FEED) {
                    return WINDOWS_LINE_BREAK;
                }
                return OLD_MAC_LINE_BREAK;
            } else if (bytes[i] == LINE_FEED) {
                return UNIX_LINE_BREAK;
            }
        }
        return UNDEFINED_LINE_BREAK;
    }

    private LineBreak findLineBreakAtLastByte() {
        byte lastByte = bytes[bytes.length - 1];
        if (lastByte == LINE_FEED) {
            return UNIX_LINE_BREAK;
        } else if (lastByte == CARRIAGE_RETURN) {
            return OLD_MAC_LINE_BREAK;
        }
        return UNDEFINED_LINE_BREAK;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public LineBreak getLineBreak() {
        return lineBreak;
    }

    @Override
    public int getNumberOfLines() {
        if (numberOfLines < 0) {
            numberOfLines = countNumberOfLines();
        }
        return numberOfLines;
    }

    private int countNumberOfLines() {
        if (bytes.length == 0) {
            return 0;
        }
        if (WINDOWS_LINE_BREAK.equals(lineBreak)) {
            return countLinesForWindowsLineBreak();
        }
        if (UNIX_LINE_BREAK.equals(lineBreak)) {
            return countLinesForUnixLineBreak();
        }
        return countLinesForOldMacLineBreak();
    }

    private int countLinesForWindowsLineBreak() {
        int lineCount = 1;
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == CARRIAGE_RETURN && bytes[i + 1] == LINE_FEED) {
                lineCount++;
            }
        }
        return lineCount;
    }

    private int countLinesForUnixLineBreak() {
        int lineCount = 1;
        for (byte aByte : bytes) {
            if (aByte == LINE_FEED) {
                lineCount++;
            }
        }
        return lineCount;
    }

    private int countLinesForOldMacLineBreak() {
        int lineCount = 1;
        for (byte aByte : bytes) {
            if (aByte == CARRIAGE_RETURN) {
                lineCount++;
            }
        }
        return lineCount;
    }

    @Override
    public List<byte[]> getLines() {
        if (bytes.length == 0) {
            return Collections.emptyList();
        }
        int[] lineBreakIndexes = getLineBrakeIndexes();
        List<byte[]> lines = new ArrayList<>();

        int lastLineStartIndex = addAllLinesButLast(lineBreakIndexes, lines);
        if (!lines.isEmpty()) {
            lines.add(Arrays.copyOfRange(bytes, lastLineStartIndex, bytes.length));
        } else {
            lines.add(bytes);
        }
        return lines;
    }

    private int addAllLinesButLast(int[] lineBreakIndexes, List<byte[]> lines) {
        int fromIndex = 0;
        int toIndex;
        for (int i = 0; i < lineBreakIndexes.length; i = i + 2) {
            toIndex = lineBreakIndexes[i];
            lines.add(Arrays.copyOfRange(bytes, fromIndex, toIndex));
            fromIndex = lineBreakIndexes[i + 1];
        }
        return fromIndex;
    }

    private int[] getLineBrakeIndexes() {
        if (WINDOWS_LINE_BREAK.equals(lineBreak)) {
            return calculateIndexesForWindowsLineBreak();
        }
        if (UNIX_LINE_BREAK.equals(lineBreak)) {
            return calculateIndexesForUnixLineBreak();
        }
        if (OLD_MAC_LINE_BREAK.equals(lineBreak)) {
            return calculateIndexesForOldMacLineBreak();
        }
        throw new IllegalStateException("Line break for document not set");
    }

    private int[] calculateIndexesForOldMacLineBreak() {
        List<Integer> lineBreaks = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == CARRIAGE_RETURN) {
                lineBreaks.add(i);
                lineBreaks.add(i + 1);
            }
        }
        return toIntArray(lineBreaks);
    }

    private int[] calculateIndexesForWindowsLineBreak() {
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

    private int[] calculateIndexesForUnixLineBreak() {
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
