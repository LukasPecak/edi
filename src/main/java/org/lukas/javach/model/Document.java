package org.lukas.javach.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
class Document {

    private static final int CARRIAGE_RETURN = 0x0D;
    private static final int LINE_FEED = 0x0A;
    private static final int NOT_INITIALIZED = -1;

    private static final byte[] NO_LINE_SEPARATOR = {};
    static final byte[] WINDOWS_LINE_SEPARATOR = {CARRIAGE_RETURN, LINE_FEED};
    static final byte[] UNIX_LINE_SEPARATOR = {LINE_FEED};
    static final byte[] OLD_MAC_LINE_SEPARATOR = {CARRIAGE_RETURN};

    private byte[] bytes;
    private byte[] lineSeparator;
    private int numberOfLines = NOT_INITIALIZED;

    Document(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Cannot initialize a document with a null array of bytes");
        }
        this.bytes = bytes;

        lineSeparator = resolveLineSeparator(bytes);
    }

    private byte[] resolveLineSeparator(byte[] bytes) {
        if (bytes.length == 0) {
            return System.lineSeparator().getBytes();
        }
        byte[] firstLineSeparator = findFirstLineSeparator();
        if (firstLineSeparator != NO_LINE_SEPARATOR) {
            return firstLineSeparator;
        }
        byte[] lineSeparatorAtLastByte = findLineSeparatorAtLastByte();
        if (lineSeparatorAtLastByte != NO_LINE_SEPARATOR) {
            return lineSeparatorAtLastByte;
        }
        return System.lineSeparator().getBytes();
    }

    private byte[] findFirstLineSeparator() {
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == CARRIAGE_RETURN) {
                if (bytes[i + 1] == LINE_FEED) {
                    return WINDOWS_LINE_SEPARATOR;
                }
                return OLD_MAC_LINE_SEPARATOR;
            } else if (bytes[i] == LINE_FEED) {
                return UNIX_LINE_SEPARATOR;
            }
        }
        return NO_LINE_SEPARATOR;
    }

    private byte[] findLineSeparatorAtLastByte() {
        byte lastByte = bytes[bytes.length - 1];
        if (lastByte == LINE_FEED) {
            return UNIX_LINE_SEPARATOR;
        } else if (lastByte == CARRIAGE_RETURN) {
            return OLD_MAC_LINE_SEPARATOR;
        }
        return NO_LINE_SEPARATOR;
    }

    byte[] getBytes() {
        return bytes;
    }

    byte[] getLineSeparator() {
        return lineSeparator;
    }

    int getNumberOfLines() {
        if (numberOfLines < 0) {
            numberOfLines = countNumberOfLines();
        }
        return numberOfLines;
    }

    private int countNumberOfLines() {
        if (bytes.length == 0) {
            return 0;
        }
        if (Arrays.equals(lineSeparator, WINDOWS_LINE_SEPARATOR)) {
            return countLinesForWindowsLineSeparator();
        }
        if (Arrays.equals(lineSeparator, UNIX_LINE_SEPARATOR)) {
            return countLinesForUnixLineSeparator();
        }
        return countLinesForOldMacLineSeparator();
    }

    private int countLinesForWindowsLineSeparator() {
        int lineCount = 1;
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == lineSeparator[0] && bytes[i + 1] == lineSeparator[1]) {
                lineCount++;
            }
        }
        return lineCount;
    }

    private int countLinesForUnixLineSeparator() {
        int lineCount = 1;
        for (byte aByte : bytes) {
            if (aByte == LINE_FEED) {
                lineCount++;
            }
        }
        return lineCount;
    }

    private int countLinesForOldMacLineSeparator() {
        int lineCount = 1;
        for (byte aByte : bytes) {
            if (aByte == CARRIAGE_RETURN) {
                lineCount++;
            }
        }
        return lineCount;
    }

    List<byte[]> getLines() {
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
        if (Arrays.equals(WINDOWS_LINE_SEPARATOR, lineSeparator)) {
            return calculateIndexesForWindowsLineSeparator();
        }
        if (Arrays.equals(UNIX_LINE_SEPARATOR, lineSeparator)) {
            return calculateIndexesForUnixLineSeparator();
        }
        if (Arrays.equals(OLD_MAC_LINE_SEPARATOR, lineSeparator)) {
            return calculateIndexesForOldMacLineSeparator();
        }
        throw new IllegalStateException("Line separator for document not set");
    }

    private int[] calculateIndexesForOldMacLineSeparator() {
        List<Integer> lineBreaks = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == CARRIAGE_RETURN) {
                lineBreaks.add(i);
                lineBreaks.add(i + 1);
            }
        }
        return toIntArray(lineBreaks);
    }

    private int[] calculateIndexesForWindowsLineSeparator() {
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

    private int[] calculateIndexesForUnixLineSeparator() {
        List<Integer> lineBreaks = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == LINE_FEED) {
                lineBreaks.add(i);
                lineBreaks.add(i + 1);
            }
        }
        return toIntArray(lineBreaks);
    }

    void setLineSeparator(byte[] lineSeparator) {
        if (!isKnownLineSeparator(lineSeparator)) {
            throw new IllegalArgumentException("Tried to set null value as line separator");
        }
        this.lineSeparator = lineSeparator;
    }

    private boolean isKnownLineSeparator(byte[] lineSeparator) {
        return Arrays.equals(WINDOWS_LINE_SEPARATOR, lineSeparator)
                || Arrays.equals(UNIX_LINE_SEPARATOR, lineSeparator)
                || Arrays.equals(OLD_MAC_LINE_SEPARATOR, lineSeparator);
    }
}
