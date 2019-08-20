package org.lukas.javach.document;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.lukas.javach.document.LineBreak.*;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
public class TextContentTest {

    private TextContent document;

    @Before
    public void setupTests() {
        document = new TextContent(new byte[0]);
    }

    @Test
    public void documentShouldProvideTheFileAsByteArray() {
        document.getBytes();
    }

    @Test
    public void document_shouldReturnTeSameArrayOfBytesAsInitializedWith_whenNoActionOnTheDataWerePerformed() {
        String data = "This is a sample text";
        document = new TextContent(data.getBytes());

        assertThat(document.getBytes(), is(equalTo(data.getBytes())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldThrowIllegalArgumentException_whenTryToInitializeWithNullValue() {
        new TextContent(null);
    }

    @Test
    public void getLineBreak_shouldReturnTheLineBreakUsedInTheDocument_whenDocumentHasMultipleLines() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        document = new TextContent(("This is the first line" + windowsLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(LineBreak.WINDOWS_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheSystemDefaultLineBreak_whenDocumentIsEmpty() {
        // GIVEN
        byte[] systemLineBreak = System.lineSeparator().getBytes();

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak.getBytes(), is(equalTo(systemLineBreak)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheSystemDefaultLineLineBreak_whenDocumentHasOnlyOneLine() {
        // GIVEN
        document = new TextContent("One line test only. Without line lineBreak".getBytes());
        byte[] systemLineBreak = System.lineSeparator().getBytes();

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak.getBytes(), is(equalTo(systemLineBreak)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheUnixLineLineBreak_whenDocumentHasMultipleLinesAndUnixFormatting() {
        // GIVEN
        String unixLineBreak = "\n";
        document = new TextContent(("This is the first line" + unixLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheUnixLineLineBreak_whenDocumentEndsWithLineFeed() {
        // GIVEN
        String unixLineBreak = "\n";
        document = new TextContent(("This is the first line" + unixLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheOldMacLineBreak_whenDocumentEndsWithCarriageReturn() {
        // GIVEN
        String oldMacLineBreak = "\r";
        document = new TextContent(("This is the first line" + oldMacLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheWindowsLineBreak_whenDocumentEndsWithCarriageReturnPlusLineFeed() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        document = new TextContent(("This is the first line" + windowsLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(WINDOWS_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheOldMacLineBreak_whenDocumentHasMultipleLinesAndOldMacFormatting() {
        // GIVEN
        String oldMacLineBreak = "\r";
        document = new TextContent(("This is the first line" + oldMacLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnUnixLineBreak_whenDocumentHasOnlyMultipleUnixLineBreaks() {
        // GIVEN
        String unixLineBreak = "\n";
        document = new TextContent((unixLineBreak + unixLineBreak + unixLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnOldMacLineBreak_whenDocumentHasOnlyMultipleOldMacLineBreaks() {
        // GIVEN
        String oldMacLineBreak = "\r";
        document = new TextContent((oldMacLineBreak + oldMacLineBreak + oldMacLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = document.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnWindowsLineBreak_whenDocumentHasOnlyMultipleWindowsLineBreaks() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        document = new TextContent((windowsLineBreak + windowsLineBreak + windowsLineBreak).getBytes());

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
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(0)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountEqualToOneInNonEmptyDocumentWithOneLine_always() {
        // GIVEN
        String text = "One line";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(1)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithWindowsLineBreak_always() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithUnixLineBreak_always() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithOldMacLineBreak_always() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithWindowsLineBreak() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line\r\n";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithUnixLineBreak() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line\n";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithOldMacLineBreak() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line\r";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeWindowsLineBreak() {
        // GIVEN
        String text = "\r\n\r\n\r\n";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeUnixLineBreak() {
        // GIVEN
        String text = "\n\n\n";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeOldMacLineBreak() {
        // GIVEN
        String text = "\r\r\r";
        document = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = document.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getLines_shouldReturnEmptyList_whenFileContentIsEmpty() {
        // GIVEN
        String text = "";
        document = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = document.getLines();

        // THEN
        assertThat(lines.isEmpty(), is(true));
    }

    @Test
    public void getLines_shouldReturnListOfOneElementAndContainingTheLine_whenFileContentHasContentButNoLinebreak() {
        // GIVEN
        String text = "Some content";
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
        document = new TextContent(text.getBytes());

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
    public void getBytes_shouldReturnLinesOfBytesSeparatedByWindowsLineBreak_whenWindowsLineBreakIsSet() {
        // GIVEN
        String text = "First line.\r\nSecond line.\r\nThird line.";
        document = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = document.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByUnixLineBreak_whenUnixLineBreakIsSet() {
        // GIVEN
        String text = "First line.\nSecond line.\nThird line.";
        document = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = document.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByOldMacLineBreak_whenOldMacLineBreakIsSet() {
        // GIVEN
        String text = "First line.\rSecond line.\rThird line.";
        document = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = document.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByWindowsLineBreak_whenWasReadAsWindowsAndChangedToUnix() {
        // GIVEN
        String text = "First line.\r\nSecond line.\r\nThird line.";
        document = new TextContent(text.getBytes());

        // WHEN
        document.setLineBreak(UNIX_LINE_BREAK);
        byte[] bytes = document.getBytes();

        // THEN
        String resultContent = "First line.\nSecond line.\nThird line.";
        assertThat(bytes, is(equalTo(resultContent.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByWindowsLineBreak_whenWindowsLineBreakIsSetAtBeginAndEnd() {
        // GIVEN
        String text = "\r\nFirst line.\r\nSecond line.\r\nThird line.\r\n";
        document = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = document.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByUnixLineBreak_whenUnixLineBreakIsSetAtBeginAndEnd() {
        // GIVEN
        String text = "\nFirst line.\nSecond line.\nThird line.\n";
        document = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = document.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByOldMacLineBreak_whenOldMacLineBreakIsSetAtBeginAndEnd() {
        // GIVEN
        String text = "\rFirst line.\rSecond line.\rThird line.\r";
        document = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = document.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getLineRange_shouldReturnLineRangeOfContent_whenLineRangeInTheMiddleOfTheFile() {
        // GIVEN
        String text = "First line.\nSecond line.\nThird line.";
        document = new TextContent(text.getBytes());

        // WHEN
        LineRange lineRange = document.getLineRange(1, 2);

        // THEN
        assertThat(lineRange.getLines().size(), is(equalTo(1)));
        assertThat(lineRange.getStartIndex(), is(equalTo(1)));
        assertThat(lineRange.getEndIndex(), is(equalTo(2)));
        assertThat(lineRange.getLines().get(0), is(equalTo("Second line.".getBytes())));
    }

    @Test
    public void getLineRange_shouldReturnEmptyLineRangeWithIndexesZero_whenContentIsEmpty() {
        // GIVEN
        String content = "";
        document = new TextContent(content.getBytes());

        // WHEN
        LineRange lineRange = document.getLineRange(0, 0);

        // THEN
        assertThat(lineRange.getLines().isEmpty(), is(true));
        assertThat(lineRange.getStartIndex(), is(equalTo(0)));
        assertThat(lineRange.getEndIndex(), is(equalTo(0)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLineRange_shouldThrowIndexOutOfBoundException_whenStartIndexIsLessThenZero() {
        // GIVEN
        String content = "";
        document = new TextContent(content.getBytes());

        // WHEN
        document.getLineRange(-1, 0);

        // THEN throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void getLineRange_shouldThrowIllegalArgumentException_whenEndIndexIsLessThenStartIndex() {
        // GIVEN
        String content = "Something\n";
        document = new TextContent(content.getBytes());

        // WHEN
        document.getLineRange(0, -1);

        // THEN throw exception
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLineRange_shouldThrowIndexOutOfBoundException_whenEndIndexIsGreaterThanNumberOfLines() {
        // GIVEN
        String content = "Something\nNextLine";
        document = new TextContent(content.getBytes());

        // WHEN
        document.getLineRange(0, 3);

        // THEN throw exception
    }

    @Test
    public void getLineRange_shouldReturnLineRangeOfTheWholeContent_always() {
        // GIVEN
        String text = "\nFirst line.\nSecond line.\nThird line.\n";
        document = new TextContent(text.getBytes());

        // WHEN
        LineRange lineRange = document.getLineRangeAll();

        // THEN
        assertThat(lineRange.getLines().size(), is(equalTo(5)));
        assertThat(lineRange.getStartIndex(), is(equalTo(0)));
        assertThat(lineRange.getEndIndex(), is(equalTo(5)));
        assertThat(lineRange.getLines().get(0).length, is(equalTo(0)));
        assertThat(lineRange.getLines().get(1), is(equalTo("First line.".getBytes())));
        assertThat(lineRange.getLines().get(2), is(equalTo("Second line.".getBytes())));
        assertThat(lineRange.getLines().get(3), is(equalTo("Third line.".getBytes())));
        assertThat(lineRange.getLines().get(4).length, is(equalTo(0)));
    }

}
