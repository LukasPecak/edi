package org.lukas.javach.document;

import java.util.List;

/**
 * Created by Lukas on 19.08.2019.
 *
 * @author Lukas Pecak
 */
class LineRange {

    private int startIndex;
    private int endIndex;
    private List<byte[]> lines;

    LineRange(List<byte[]> lines, int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.lines = lines;
    }

    int getStartIndex() {
        return startIndex;
    }

    int getEndIndex() {
        return endIndex;
    }

    List<byte[]> getLines() {
        return lines;
    }

}
