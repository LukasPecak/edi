package org.lukas.javach.model;

import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.Line;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.lukas.javach.model.LineBreak.*;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
public class TextDocumentTest {

    private TextDocument document;

    @Before
    public void setupTests() {
        document = new TextDocument(new byte[0]);
    }

    @Test
    public void documentShouldProvideTheFileAsByteArray() {
        document.getBytes();
    }

    @Test
    public void document_shouldReturnTeSameArrayOfBytesAsInitializedWith_whenNoActionOnTheDataWerePerformed() {
        String data = "This is a sample text";
        document = new TextDocument(data.getBytes());

        assertThat(document.getBytes(), is(equalTo(data.getBytes())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldThrowIllegalArgumentException_whenTryToInitializeWithNullValue() {
        new TextDocument(null);
    }

    @Test
    public void getLineBreak_shouldReturnTheLineBreakUsedInTheDocument_whenDocumentHasMultipleLines() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        document = new TextDocument(("This is the first line" + windowsLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(LineBreak.WINDOWS_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheSystemDefaultLineBreak_whenDocumentIsEmpty() {
        // GIVEN
        byte[] systemBreak = System.lineSeparator().getBytes();

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak.getBytes(), is(equalTo(systemBreak)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheSystemDefaultLineLineBreak_whenDocumentHasOnlyOneLine() {
        // GIVEN
        document = new TextDocument("One line test only. Without line lineBreak".getBytes());
        byte[] systemSeparator = System.lineSeparator().getBytes();

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak.getBytes(), is(equalTo(systemSeparator)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheUnixLineLineBreak_whenDocumentHasMultipleLinesAndUnixFormatting() {
        // GIVEN
        String unixLineBreak = "\n";
        document = new TextDocument(("This is the first line" + unixLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheUnixLineLineBreak_whenDocumentEndsWithLineFeed() {
        // GIVEN
        String unixLineBreak = "\n";
        document = new TextDocument(("This is the first line" + unixLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheOldMacLineBreak_whenDocumentEndsWithCarriageReturn() {
        // GIVEN
        String oldMacLineBreak = "\r";
        document = new TextDocument(("This is the first line" + oldMacLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheWindowsLineBreak_whenDocumentEndsWithCarriageReturnPlusLineFeed() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        document = new TextDocument(("This is the first line" + windowsLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(WINDOWS_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheOldMacLineBreak_whenDocumentHasMultipleLinesAndOldMacFormatting() {
        // GIVEN
        String oldMacLineBreak = "\r";
        document = new TextDocument(("This is the first line" + oldMacLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnUnixLineBreak_whenDocumentHasOnlyMultipleUnixLineBreaks() {
        // GIVEN
        String unixLineBreak = "\n";
        document = new TextDocument((unixLineBreak + unixLineBreak + unixLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnOldMacLineBreak_whenDocumentHasOnlyMultipleOldMacLineBreaks() {
        // GIVEN
        String oldMacLineBreak = "\r";
        document = new TextDocument((oldMacLineBreak + oldMacLineBreak + oldMacLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnWindowsLineBreak_whenDocumentHasOnlyMultipleWindowsLineBreaks() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        document = new TextDocument((windowsLineBreak + windowsLineBreak + windowsLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(WINDOWS_LINE_BREAK)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLineBreak_shouldThrowIllegalArgumentException_whenPassesArgumentIsNull() {
        // GIVEN Setup

        // WHEN
        document.setLineBreak(null);
        // THEN EXPECT EXCEPTION TO BE THROWN
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLineBreak_shouldThrowIllegalArgumentException_whenArgumentIsOtherThenDefinedLineBreaks() {
        // GIVEN Setup

        // WHEN
        document.setLineBreak(UNDEFINED_LINE_BREAK);
        // THEN EXPECT EXCEPTION TO BE THROWN
    }

    @Test
    public void setLineBreak_shouldSetLineBreakToWindowsLineBreak_whenArgumentIsWindowsLineBreak() {
        // GIVEN Setup

        // WHEN
        document.setLineBreak(WINDOWS_LINE_BREAK);

        // THEN
        assertThat(document.getLineBreak(), is(equalTo(WINDOWS_LINE_BREAK)));
    }

    @Test
    public void setLineBreak_shouldSetLineBreakToUnixLineBreak_whenArgumentIsUnixLineBreak() {
        // GIVEN Setup

        // WHEN
        document.setLineBreak(UNIX_LINE_BREAK);

        // THEN
        assertThat(document.getLineBreak(), is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void setLineBreak_shouldSetLineBreakToOldMacLineBreak_whenArgumentIsOldMacLineBreak() {
        // GIVEN Setup

        // WHEN
        document.setLineBreak(OLD_MAC_LINE_BREAK);

        // THEN
        assertThat(document.getLineBreak(), is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountEqualToZeroInEmptyDocument_always() {
        // GIVEN
        String text = "";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(0)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountEqualToOneInNonEmptyDocumentWithOneLine_always() {
        // GIVEN
        String text = "One line";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(1)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithWindowsLineBreak_always() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithUnixLineBreak_always() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithOldMacLineBreak_always() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithWindowsLineBreak() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line\r\n";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithUnixLineBreak() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line\n";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithOldMacLineBreak() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line\r";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeWindowsLineBreak() {
        // GIVEN
        String text = "\r\n\r\n\r\n";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeUnixLineBreak() {
        // GIVEN
        String text = "\n\n\n";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeOldMacLineBreak() {
        // GIVEN
        String text = "\r\r\r";
        document = new TextDocument(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getLines_shouldReturnEmptyList_whenFileContentIsEmpty() {
        // GIVEN
        String text = "";
        document = new TextDocument(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.isEmpty(), is(true));
    }

    @Test
    public void getLines_shouldReturnListOfOneElementAndContainingTheLine_whenFileContentHasContentButNoLinebreak() {
        // GIVEN
        String text = "Some content";
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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
        document = new TextDocument(text.getBytes());

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