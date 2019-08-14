package org.lukas.javach.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.lukas.javach.document.LineBreak.*;

class LineSplitter {

    private static final int CARRIAGE_RETURN = 0x0D;
    private static final int LINE_FEED = 0x0A;

    private byte[] bytes;
    private LineBreak lineBreak;
    private List<byte[]> lines;

    List<byte[]> split(byte[] bytes, LineBreak lineBreak) {
        this.bytes = bytes;
        this.lineBreak = lineBreak;
        lines = new ArrayList<>();

        return splitBytesToLines();
    }

    private List<byte[]> splitBytesToLines() {
        if (bytes.length == 0) {
            return Collections.emptyList();
        }
        int[] lineBreakIndexes = getLineBrakeIndexes();

        int lastLineStartIndex = addAllLinesButLast(lineBreakIndexes);
        if (!lines.isEmpty()) {
            lines.add(Arrays.copyOfRange(bytes, lastLineStartIndex, bytes.length));
        } else {
            lines.add(bytes);
        }
        return lines;
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

    private int addAllLinesButLast(int[] lineBreakIndexes) {
        int fromIndex = 0;
        int toIndex;
        for (int i = 0; i < lineBreakIndexes.length; i = i + 2) {
            toIndex = lineBreakIndexes[i];
            lines.add(Arrays.copyOfRange(bytes, fromIndex, toIndex));
            fromIndex = lineBreakIndexes[i + 1];
        }
        return fromIndex;
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

    private int[] toIntArray(List<Integer> lineBreaks) {
        return lineBreaks.stream().mapToInt(i -> i).toArray();
    }
}