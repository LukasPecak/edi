package org.lukas.javach.document;

import java.util.List;

/**
 * Created by Lukas on 19.08.2019.
 *
 * @author Lukas Pecak
 */
public class LineRange {

    private int startIndex;
    private int endIndex;
    private List<byte[]> lines;

    LineRange(List<byte[]> lines, int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.lines = lines;
    }

    public LineRange(LineRange toCopy, List<byte[]> lines) {
        this.startIndex = toCopy.startIndex;
        this.endIndex = toCopy.endIndex;
        this.lines = lines;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public List<byte[]> getLines() {
        return lines;
    }

    public int size() {
        return lines.size();
    }
}
