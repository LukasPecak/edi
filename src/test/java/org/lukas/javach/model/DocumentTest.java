package org.lukas.javach.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.lukas.javach.model.Document.*;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
public class DocumentTest {

    private Document document;

    @Before
    public void setupTests() {
        document = new Document(new byte[0]);
    }

    @Test
    public void documentShouldProvideTheFileAsByteArray() {
        document.getBytes();
    }

    @Test
    public void document_shouldReturnTeSameArrayOfBytesAsInitializedWith_whenNoActionOnTheDataWerePerformed() {
        String data = "This is a sample text";
        document = new Document(data.getBytes());

        assertThat(document.getBytes(), is(equalTo(data.getBytes())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldThrowIllegalArgumentException_whenTryToInitializeWithNullValue() {
        new Document(null);
    }

    @Test
    public void getLineSeparator_shouldReturnTheLineSeparatorUsedInTheDocument_whenDocumentHasMultipleLines() {
        // GIVEN
        String windowsLineSeparator = "\r\n";
        document = new Document(("This is the first line" + windowsLineSeparator + "This is the second line").getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(WINDOWS_LINE_SEPARATOR)));
    }

    @Test
    public void getLineSeparator_shouldReturnTheSystemDefaultLineSeparator_whenDocumentIsEmpty() {
        // GIVEN
        byte[] systemSeparator = System.lineSeparator().getBytes();

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(systemSeparator)));
    }

    @Test
    public void getLineSeparator_shouldReturnTheSystemDefaultLineSeparator_whenDocumentHasOnlyOneLine() {
        // GIVEN
        document = new Document("One line test only. Without line separator".getBytes());
        byte[] systemSeparator = System.lineSeparator().getBytes();

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(systemSeparator)));
    }

    @Test
    public void getLineSeparator_shouldReturnTheUnixLineSeparator_whenDocumentHasMultipleLinesAndUnixFormatting() {
        // GIVEN
        String unixLineSeparator = "\n";
        document = new Document(("This is the first line" + unixLineSeparator + "This is the second line").getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(UNIX_LINE_SEPARATOR)));
    }

    @Test
    public void getLineSeparator_shouldReturnTheUnixLineSeparator_whenDocumentEndsWithLineFeed() {
        // GIVEN
        String unixLineSeparator = "\n";
        document = new Document(("This is the first line" + unixLineSeparator).getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(UNIX_LINE_SEPARATOR)));
    }

    @Test
    public void getLineSeparator_shouldReturnTheOldMacLineSeparator_whenDocumentEndsWithCarriageReturn() {
        // GIVEN
        String oldMacLineSeparator = "\r";
        document = new Document(("This is the first line" + oldMacLineSeparator).getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(OLD_MAC_LINE_SEPARATOR)));
    }

    @Test
    public void getLineSeparator_shouldReturnTheWindowsLineSeparator_whenDocumentEndsWithCarriageReturnPlusLineFeed() {
        // GIVEN
        String windowsLineSeparator = "\r\n";
        document = new Document(("This is the first line" + windowsLineSeparator).getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(WINDOWS_LINE_SEPARATOR)));
    }

    @Test
    public void getLineSeparator_shouldReturnTheOldMacLineSeparator_whenDocumentHasMultipleLinesAndOldMacFormatting() {
        // GIVEN
        String oldMacLineSeparator = "\r";
        document = new Document(("This is the first line" + oldMacLineSeparator + "This is the second line").getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(OLD_MAC_LINE_SEPARATOR)));
    }

    @Test
    public void getLineSeparator_shouldReturnUnixLineSeparator_whenDocumentHasOnlyMultipleUnixSeparators() {
        // GIVEN
        String unixLineSeparator = "\n";
        document = new Document((unixLineSeparator + unixLineSeparator + unixLineSeparator).getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(UNIX_LINE_SEPARATOR)));
    }

    @Test
    public void getLineSeparator_shouldReturnOldMacLineSeparator_whenDocumentHasOnlyMultipleOldMacSeparators() {
        // GIVEN
        String oldMacLineSeparator = "\r";
        document = new Document((oldMacLineSeparator + oldMacLineSeparator + oldMacLineSeparator).getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(OLD_MAC_LINE_SEPARATOR)));
    }

    @Test
    public void getLineSeparator_shouldReturnWindowsLineSeparator_whenDocumentHasOnlyMultipleWindowsSeparators() {
        // GIVEN
        String windowsLineSeparator = "\r\n";
        document = new Document((windowsLineSeparator + windowsLineSeparator + windowsLineSeparator).getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(WINDOWS_LINE_SEPARATOR)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountEqualToZeroInEmptyDocument_always() {
        // GIVEN
        String text = "";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(0)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountEqualToOneInNonEmptyDocumentWithOneLine_always() {
        // GIVEN
        String text = "One line";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(1)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithWindowsLineSeparator_always() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithUnixLineSeparator_always() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithOldMacLineSeparator_always() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithWindowsLineSeparator() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line\r\n";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithUnixLineSeparator() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line\n";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithOldMacLineSeparator() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line\r";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeWindowsLineSeparators() {
        // GIVEN
        String text = "\r\n\r\n\r\n";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeUnixLineSeparators() {
        // GIVEN
        String text = "\n\n\n";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeOldMacLineSeparators() {
        // GIVEN
        String text = "\r\r\r";
        document = new Document(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getLines_shouldReturnEmptyList_whenFileContentIsEmpty() {
        // GIVEN
        String text = "";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.isEmpty(), is(true));
    }

    @Test
    public void getLines_shouldReturnListOfOneElementAndContainingTheLine_whenFileContentHasContentButNoLinebreak() {
        // GIVEN
        String text = "Some content";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(1));
        assertThat(lines.get(0), is(equalTo(text.getBytes())));
    }

    @Test
    public void getLines_shouldReturnListWithTwoEmptyLines_whenFileContentIsOnlyUnixLineBrakeSymbols() {
        // GIVEN
        String text = "\n";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoEmptyLines_whenFileContentIsOnlyOldMacLineBrakeSymbols() {
        // GIVEN
        String text = "\r";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoEmptyLines_whenFileContentIsOnlyWindowsLineBrakeSymbols() {
        // GIVEN
        String text = "\r\n";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsUnixLineBrakePlusText() {
        // GIVEN
        String text = "\nThe second Line";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1), is(equalTo("The second Line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsOldMacLineBrakePlusText() {
        // GIVEN
        String text = "\rThe second Line";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1), is(equalTo("The second Line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsWindowsLineBrakePlusText() {
        // GIVEN
        String text = "\r\nThe second Line";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1), is(equalTo("The second Line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsTextPlusUnixLineBrake() {
        // GIVEN
        String text = "The second Line\n";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0), is(equalTo("The second Line".getBytes())));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsTextPlusOldMacLineBrake() {
        // GIVEN
        String text = "The second Line\r";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0), is(equalTo("The second Line".getBytes())));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsTextPlusWindowsLineBrake() {
        // GIVEN
        String text = "The second Line\r\n";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0), is(equalTo("The second Line".getBytes())));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListOfBytesRepresentingLines_whenFileContentHasMultipleLinesAndUnixLineBreak() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(3));
        assertThat(lines.get(0), is(equalTo("The first line".getBytes())));
        assertThat(lines.get(1), is(equalTo("The second line".getBytes())));
        assertThat(lines.get(2), is(equalTo("The third line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnListOfBytesRepresentingLines_whenFileContentHasMultipleLinesAndOldMacLineBreak() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(3));
        assertThat(lines.get(0), is(equalTo("The first line".getBytes())));
        assertThat(lines.get(1), is(equalTo("The second line".getBytes())));
        assertThat(lines.get(2), is(equalTo("The third line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnListOfBytesRepresentingLines_whenFileContentHasMultipleLinesAndWindowsLineBreak() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(3));
        assertThat(lines.get(0), is(equalTo("The first line".getBytes())));
        assertThat(lines.get(1), is(equalTo("The second line".getBytes())));
        assertThat(lines.get(2), is(equalTo("The third line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnFourEmptyLines_whenFileContainsOnlyThreeUnixLineBreaks() {
        // GIVEN
        String text = "\n\n\n";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(4));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
        assertThat(lines.get(2).length, is(0));
        assertThat(lines.get(3).length, is(0));
    }

    @Test
    public void getLines_shouldReturnFourEmptyLines_whenFileContainsOnlyThreeOldMacLineBreaks() {
        // GIVEN
        String text = "\r\r\r";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(4));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
        assertThat(lines.get(2).length, is(0));
        assertThat(lines.get(3).length, is(0));
    }

    @Test
    public void getLines_shouldReturnFourEmptyLines_whenFileContainsOnlyThreeWindowsLineBreaks() {
        // GIVEN
        String text = "\r\n\r\n\r\n";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(4));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
        assertThat(lines.get(2).length, is(0));
        assertThat(lines.get(3).length, is(0));
    }

    @Test
    public void getLines_shouldReturnFourEmptyLines_whenFileContainsThreeUnixLineBreaksAndText() {
        // GIVEN
        String text = "\n\ntext\n";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(4));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
        assertThat(lines.get(2), is(equalTo("text".getBytes())));
        assertThat(lines.get(3).length, is(0));
    }

    @Test
    public void getLines_shouldReturnFourEmptyLines_whenFileContainsThreeOldMacLineBreaksAndText() {
        // GIVEN
        String text = "\r\rtext\r";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(4));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
        assertThat(lines.get(2), is(equalTo("text".getBytes())));
        assertThat(lines.get(3).length, is(0));
    }

    @Test
    public void getLines_shouldReturnFourEmptyLines_whenFileContainsThreeWindowsLineBreaksAndText() {
        // GIVEN
        String text = "\r\n\r\ntext\r\n";
        document = new Document(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.size(), is(4));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
        assertThat(lines.get(2), is(equalTo("text".getBytes())));
        assertThat(lines.get(3).length, is(0));
    }

}
