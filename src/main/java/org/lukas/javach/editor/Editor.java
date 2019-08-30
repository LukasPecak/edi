package org.lukas.javach.editor;

import org.lukas.javach.document.DocumentContent;
import org.lukas.javach.document.LineRange;
import org.lukas.javach.exception.NoContentOpenException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Lukas on 14.08.2019.
 *
 * @author Lukas Pecak
 */
public class Editor {

    private static final byte[] EMPTY_LINE = new byte[0];

    private DocumentContent content;
    private LineRange currentLineRange;

    public void openContent(DocumentContent content) {
        if (content == null) {
            throw new IllegalArgumentException("The provided content cannot be null");
        }
        this.content = content;
        currentLineRange = content.getLineRangeAll();
    }

    public String readLine(int lineNumber) {
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

    public List<String> readAllLines() {
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

    public LineRange getCurrentLineRange() {
        validateContentState();
        return currentLineRange;
    }

    public void updateLine(int lineIndex, String line) {
        if (line == null) {
            throw new IllegalArgumentException("The new value of line cannot be null value");
        }
        currentLineRange.getLines().set(lineIndex, line.getBytes());
    }

    public void addLineAtIndex(int index, String newLine) {
        if (index <= currentLineRange.getLines().size()) {
            currentLineRange = new LineRange(currentLineRange, insertLineInExistingRange(index, newLine));
        } else {
            currentLineRange = new LineRange(currentLineRange, insertLineOutsideOfTheExistingLineRange(index, newLine));
        }
    }

    private List<byte[]> insertLineInExistingRange(int index, String newLine) {
        List<byte[]> lines = currentLineRange.getLines();
        List<byte[]> result = new ArrayList<>(lines.subList(0, index));
        result.add(newLine.getBytes());
        result.addAll(lines.subList(index, lines.size()));
        return result;
    }

    private List<byte[]> insertLineOutsideOfTheExistingLineRange(int index, String newLine) {
        List<byte[]> lines = currentLineRange.getLines();
        List<byte[]> result = new ArrayList<>(lines.subList(0, lines.size()));
        for (int i = lines.size(); i < index; i++) {
            result.add(EMPTY_LINE);
        }
        result.add(newLine.getBytes());
        return result;
    }

    public void deleteLineAtIndex(int index) {
        if (index < 0 || index >= getCurrentLineRange().size()) {
            throw new IllegalArgumentException("Index cannot be less then zero");
        }
        getCurrentLineRange().getLines().remove(index);
    }

    public void deleteLinesOfRange(int startIndex, int endIndex) {
        int numberOfLines = getCurrentLineRange().size();
        if (startIndex < 0 || startIndex > endIndex || endIndex > numberOfLines) {
            throw new IllegalArgumentException("Any index cannot be less than zero");
        }
        getCurrentLineRange().getLines().subList(startIndex, endIndex).clear();
    }
}
