package org.lukas.javach.editor;

import org.lukas.javach.document.DocumentContent;
import org.lukas.javach.document.LineRange;
import org.lukas.javach.exception.NoContentOpenException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lukas on 14.08.2019.
 *
 * @author Lukas Pecak
 */
class Editor {

    private static final byte[] EMPTY_LINE = new byte[0];

    private DocumentContent content;
    private LineRange currentLineRange;

    void openContent(DocumentContent content) {
        if (content == null) {
            throw new IllegalArgumentException("The provided content cannot be null");
        }
        this.content = content;
        currentLineRange = content.getLineRangeAll();
    }

    String readLine(int lineNumber) {
        validateLineNumber(lineNumber);
        currentLineRange = content.getLineRange(lineNumber, lineNumber + 1);
        return new String(currentLineRange.getLines().get(0));
    }

    private void validateLineNumber(int lineNumber) {
        String message = null;
        if (lineNumber < 0) {
            message = "Line number cannot be smaller then 0";
        } else if (lineNumber > content.getNumberOfLines()) {
            message = "Line number is bigger then content size";
        }
        if (message != null) {
            throw new IllegalArgumentException(message);
        }
    }

    List<String> readAllLines() {
        validateContentState();
        return currentLineRange.getLines()
                .stream()
                .map(String::new)
                .collect(Collectors.toList());
    }

    private void validateContentState() {
        if (content == null) {
            throw new NoContentOpenException("No content is open in editor");
        }
    }

    LineRange getCurrentLineRange() {
        validateContentState();
        return currentLineRange;
    }

    void updateLine(int lineIndex, String line) {
        if (line == null) {
            throw new IllegalArgumentException("The new value of line cannot be null value");
        }
        currentLineRange.getLines().set(lineIndex, line.getBytes());
    }

    void addLineAtIndex(int index, String newLine) {
        List<byte[]> lines = currentLineRange.getLines();
        List<byte[]> newLines = new ArrayList<>();
        int originalNumberOfLines = lines.size();
        if (index <= originalNumberOfLines) {
            newLines.addAll(lines.subList(0, index));
            newLines.add(newLine.getBytes());
            newLines.addAll(lines.subList(index, originalNumberOfLines));
        } else {
            newLines.addAll(lines.subList(0, originalNumberOfLines));
            for (int i = originalNumberOfLines; i < index; i++) {
                newLines.add(EMPTY_LINE);
            }
            newLines.add(newLine.getBytes());
        }

        currentLineRange = new LineRange(newLines, currentLineRange.getStartIndex(), currentLineRange.getEndIndex());
    }

    void deleteLineAtIndex(int index) {
        if (index < 0 || index >= getCurrentLineRange().size()) {
            throw new IllegalArgumentException("Index cannot be less then zero");
        }
        getCurrentLineRange().getLines().remove(index);
    }
}
