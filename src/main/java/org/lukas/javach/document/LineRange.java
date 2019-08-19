package org.lukas.javach.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Lukas on 19.08.2019.
 *
 * @author Lukas Pecak
 */
class LineRange {

    private static final Logger LOG = LoggerFactory.getLogger(LineRange.class);

    private int startIndex;
    private int endIndex;
    private List<byte[]> lines;

    LineRange(List<byte[]> lines, int startIndex, int endIndex) {
        validateInputData(lines, startIndex, endIndex);

        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.lines = lines;
    }

    private void validateInputData(List<byte[]> lines, int startIndex, int endIndex) {
        String message = null;
        if (lines == null) {
            message = "A negative start or end line index provided";
        } else if (startIndex < 0 || endIndex < 0) {
            message = "Lines have illegal value - null";
        }
        if (message != null) {
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
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
