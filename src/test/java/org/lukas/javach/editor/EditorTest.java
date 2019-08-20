package org.lukas.javach.editor;

import org.junit.Before;
import org.junit.Test;
import org.lukas.javach.document.DocumentContent;
import org.lukas.javach.document.DocumentContentFactory;
import org.lukas.javach.document.DocumentContentFactoryImpl;
import org.lukas.javach.document.LineRange;
import org.lukas.javach.exception.NoContentOpenException;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Lukas on 14.08.2019.
 *
 * @author Lukas Pecak
 */
public class EditorTest {

    private Editor editor;
    private DocumentContentFactory contentFactory;

    @Before
    public void setupTest() {
        editor = new Editor();
        contentFactory = new DocumentContentFactoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void openDocument_shouldThrowIllegalArgumentException_whenDocumentReferenceIsNull() {
        // GIVEN
        DocumentContent content = null;

        // WHEN
        editor.openContent(content);

        // THEN THROW
    }

    @Test
    public void readLine_shouldReturnLineRepresentationAsString_whenDocumentIsOpened() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        String line0 = editor.readLine(0);
        String line1 = editor.readLine(1);
        String line2 = editor.readLine(2);

        // THEN
        assertThat(line0, is(equalTo("First line")));
        assertThat(line1, is(equalTo("The second line")));
        assertThat(line2, is(equalTo("Third line")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void readLine_shouldThrowIllegalArgumentException_whenLineNumberIsLessThenZero() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);
        int lineNumber = -1;

        // WHEN
        editor.readLine(lineNumber);

        // THEN Should throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void readLine_shouldThrowIllegalArgumentException_whenLineNumberIsGreaterThanNumberOfLinesInContent() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);
        int lineNumber = 4;

        // WHEN
        editor.readLine(lineNumber);

        // THEN Should throw exception
    }

    @Test
    public void readAllLines_shouldReturnListOfStringLineRepresentations_whenDocumentIsOpened() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        List<String> lines = editor.readAllLines();

        // THEN
        assertThat(lines.get(0), is(equalTo("First line")));
        assertThat(lines.get(1), is(equalTo("The second line")));
        assertThat(lines.get(2), is(equalTo("Third line")));
    }

    @Test(expected = NoContentOpenException.class)
    public void getCurrentLineRange_shouldThrowNoContentOpenException_whenDocumentIsNotOpenedInEditor() {
        // GIVEN setup

        // WHEN
        editor.getCurrentLineRange();

        // THEN throw exception
    }

    @Test
    public void getCurrentLineRange_shouldReturnTheCurrentLineRange_whenDocumentIsOpenedInEditor() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        LineRange lineRange = editor.getCurrentLineRange();

        // THEN
        List<byte[]> lines = lineRange.getLines();
        assertThat(lines.get(0), is(equalTo("First line".getBytes())));
        assertThat(lines.get(1), is(equalTo("The second line".getBytes())));
        assertThat(lines.get(2), is(equalTo("Third line".getBytes())));
        assertThat(lineRange.getStartIndex(), is(equalTo(0)));
        assertThat(lineRange.getEndIndex(), is(equalTo(3)));
    }

    @Test
    public void updateLine_shouldSubstituteExistingLineWithNewValue_whenIndexExistsInCurrentOpenedLineRange() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        editor.updateLine(1, "The modified second line;");

        // THEN
        List<String> lines = editor.readAllLines();
        assertThat(lines.get(0), is(equalTo("First line")));
        assertThat(lines.get(1), is(equalTo("The modified second line;")));
        assertThat(lines.get(2), is(equalTo("Third line")));
        LineRange currentLineRange = editor.getCurrentLineRange();
        assertThat(currentLineRange.getStartIndex(), is(equalTo(0)));
        assertThat(currentLineRange.getEndIndex(), is(equalTo(3)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void updateLine_shouldThrowIndexOutOfBoundsException_whenLineIndexIsLessThenZero() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        editor.updateLine(-1, "The modified second line;");

        // THEN throw exception
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void updateLine_shouldThrowIndexOutOfBoundsException_whenLineIndexIsGreaterThanSizeOfCurrentLineRange() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        editor.updateLine(3, "The modified second line;");

        // THEN throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateLine_shouldThrowIllegalArgumentException_whenProvidedLineIsNull() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);
        String newLineContent = null;

        // WHEN
        editor.updateLine(3, newLineContent);

        // THEN throw exception
    }

    @Test
    public void addLineAtIndex_shouldAddNewLineAtIndex_whenIndexIsZero() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        String newLine = "The new line";
        editor.addLineAtIndex(0, newLine);

        // THEN
        List<String> lines = editor.readAllLines();
        assertThat(lines.get(0), is(equalTo("The new line")));
        assertThat(lines.get(1), is(equalTo("First line")));
        assertThat(lines.get(2), is(equalTo("The second line")));
        assertThat(lines.get(3), is(equalTo("Third line")));
        LineRange currentLineRange = editor.getCurrentLineRange();
        assertThat(currentLineRange.getStartIndex(), is(equalTo(0)));
        assertThat(currentLineRange.getEndIndex(), is(equalTo(3)));
    }

    @Test
    public void addLineAtIndex_shouldAddNewLineAtIndex_whenIndexIsOne() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        String newLine = "The new line";
        editor.addLineAtIndex(1, newLine);

        // THEN
        List<String> lines = editor.readAllLines();
        assertThat(lines.get(0), is(equalTo("First line")));
        assertThat(lines.get(1), is(equalTo("The new line")));
        assertThat(lines.get(2), is(equalTo("The second line")));
        assertThat(lines.get(3), is(equalTo("Third line")));
        LineRange currentLineRange = editor.getCurrentLineRange();
        assertThat(currentLineRange.getStartIndex(), is(equalTo(0)));
        assertThat(currentLineRange.getEndIndex(), is(equalTo(3)));
    }

    @Test
    public void addLineAtIndex_shouldAddNewLineAtIndex_whenIndexIsTwo() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        String newLine = "The new line";
        editor.addLineAtIndex(2, newLine);

        // THEN
        List<String> lines = editor.readAllLines();
        assertThat(lines.get(0), is(equalTo("First line")));
        assertThat(lines.get(1), is(equalTo("The second line")));
        assertThat(lines.get(2), is(equalTo("The new line")));
        assertThat(lines.get(3), is(equalTo("Third line")));
        LineRange currentLineRange = editor.getCurrentLineRange();
        assertThat(currentLineRange.getStartIndex(), is(equalTo(0)));
        assertThat(currentLineRange.getEndIndex(), is(equalTo(3)));
    }

    @Test
    public void addLineAtIndex_shouldAddNewLineAtIndex_whenIndexIsThree() {
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        String newLine = "The new line";
        editor.addLineAtIndex(3, newLine);

        // THEN
        List<String> lines = editor.readAllLines();
        assertThat(lines.get(0), is(equalTo("First line")));
        assertThat(lines.get(1), is(equalTo("The second line")));
        assertThat(lines.get(2), is(equalTo("Third line")));
        assertThat(lines.get(3), is(equalTo("The new line")));
        LineRange currentLineRange = editor.getCurrentLineRange();
        assertThat(currentLineRange.getStartIndex(), is(equalTo(0)));
        assertThat(currentLineRange.getEndIndex(), is(equalTo(3)));
    }

}
