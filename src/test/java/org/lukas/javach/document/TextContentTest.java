package org.lukas.javach.document;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private TextContent sut;

    @Before
    public void setupTests() {
        sut = new TextContent(new byte[0]);
    }

    @Test
    public void documentShouldProvideTheFileAsByteArray() {
        sut.getBytes();
    }

    @Test
    public void document_shouldReturnTeSameArrayOfBytesAsInitializedWith_whenNoActionOnTheDataWerePerformed() {
        String data = "This is a sample text";
        sut = new TextContent(data.getBytes());

        assertThat(sut.getBytes(), is(equalTo(data.getBytes())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_shouldThrowIllegalArgumentException_whenTryToInitializeWithNullValue() {
        new TextContent(null);
    }

    @Test
    public void getLineBreak_shouldReturnTheLineBreakUsedInTheDocument_whenDocumentHasMultipleLines() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        sut = new TextContent(("This is the first line" + windowsLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(LineBreak.WINDOWS_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheSystemDefaultLineBreak_whenDocumentIsEmpty() {
        // GIVEN
        byte[] systemLineBreak = System.lineSeparator().getBytes();

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak.getBytes(), is(equalTo(systemLineBreak)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheSystemDefaultLineLineBreak_whenDocumentHasOnlyOneLine() {
        // GIVEN
        sut = new TextContent("One line test only. Without line lineBreak".getBytes());
        byte[] systemLineBreak = System.lineSeparator().getBytes();

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak.getBytes(), is(equalTo(systemLineBreak)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheUnixLineLineBreak_whenDocumentHasMultipleLinesAndUnixFormatting() {
        // GIVEN
        String unixLineBreak = "\n";
        sut = new TextContent(("This is the first line" + unixLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineLineBreak_shouldReturnTheUnixLineLineBreak_whenDocumentEndsWithLineFeed() {
        // GIVEN
        String unixLineBreak = "\n";
        sut = new TextContent(("This is the first line" + unixLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheOldMacLineBreak_whenDocumentEndsWithCarriageReturn() {
        // GIVEN
        String oldMacLineBreak = "\r";
        sut = new TextContent(("This is the first line" + oldMacLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheWindowsLineBreak_whenDocumentEndsWithCarriageReturnPlusLineFeed() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        sut = new TextContent(("This is the first line" + windowsLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(WINDOWS_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnTheOldMacLineBreak_whenDocumentHasMultipleLinesAndOldMacFormatting() {
        // GIVEN
        String oldMacLineBreak = "\r";
        sut = new TextContent(("This is the first line" + oldMacLineBreak + "This is the second line").getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnUnixLineBreak_whenDocumentHasOnlyMultipleUnixLineBreaks() {
        // GIVEN
        String unixLineBreak = "\n";
        sut = new TextContent((unixLineBreak + unixLineBreak + unixLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnOldMacLineBreak_whenDocumentHasOnlyMultipleOldMacLineBreaks() {
        // GIVEN
        String oldMacLineBreak = "\r";
        sut = new TextContent((oldMacLineBreak + oldMacLineBreak + oldMacLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getLineBreak_shouldReturnWindowsLineBreak_whenDocumentHasOnlyMultipleWindowsLineBreaks() {
        // GIVEN
        String windowsLineBreak = "\r\n";
        sut = new TextContent((windowsLineBreak + windowsLineBreak + windowsLineBreak).getBytes());

        // WHEN
        LineBreak lineBreak = sut.getLineBreak();

        // THEN
        assertThat(lineBreak, is(equalTo(WINDOWS_LINE_BREAK)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLineBreak_shouldThrowIllegalArgumentException_whenPassesArgumentIsNull() {
        // GIVEN Setup

        // WHEN
        sut.setLineBreak(null);
        // THEN EXPECT EXCEPTION TO BE THROWN
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLineBreak_shouldThrowIllegalArgumentException_whenArgumentIsOtherThenDefinedLineBreaks() {
        // GIVEN Setup

        // WHEN
        sut.setLineBreak(UNDEFINED_LINE_BREAK);
        // THEN EXPECT EXCEPTION TO BE THROWN
    }

    @Test
    public void setLineBreak_shouldSetLineBreakToWindowsLineBreak_whenArgumentIsWindowsLineBreak() {
        // GIVEN Setup

        // WHEN
        sut.setLineBreak(WINDOWS_LINE_BREAK);

        // THEN
        assertThat(sut.getLineBreak(), is(equalTo(WINDOWS_LINE_BREAK)));
    }

    @Test
    public void setLineBreak_shouldSetLineBreakToUnixLineBreak_whenArgumentIsUnixLineBreak() {
        // GIVEN Setup

        // WHEN
        sut.setLineBreak(UNIX_LINE_BREAK);

        // THEN
        assertThat(sut.getLineBreak(), is(equalTo(UNIX_LINE_BREAK)));
    }

    @Test
    public void setLineBreak_shouldSetLineBreakToOldMacLineBreak_whenArgumentIsOldMacLineBreak() {
        // GIVEN Setup

        // WHEN
        sut.setLineBreak(OLD_MAC_LINE_BREAK);

        // THEN
        assertThat(sut.getLineBreak(), is(equalTo(OLD_MAC_LINE_BREAK)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountEqualToZeroInEmptyDocument_always() {
        // GIVEN
        String text = "";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(0)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountEqualToOneInNonEmptyDocumentWithOneLine_always() {
        // GIVEN
        String text = "One line";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(1)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithWindowsLineBreak_always() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithUnixLineBreak_always() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesInDocumentSeparatedWithOldMacLineBreak_always() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(3)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithWindowsLineBreak() {
        // GIVEN
        String text = "The first line\r\nThe second line\r\nThe third line\r\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithUnixLineBreak() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnCountOfLinesPlusOne_whenDocumentEndsWithOldMacLineBreak() {
        // GIVEN
        String text = "The first line\rThe second line\rThe third line\r";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeWindowsLineBreak() {
        // GIVEN
        String text = "\r\n\r\n\r\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeUnixLineBreak() {
        // GIVEN
        String text = "\n\n\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getNumberOfLines_shouldReturnFour_whenDocumentContainsOnlyThreeOldMacLineBreak() {
        // GIVEN
        String text = "\r\r\r";
        sut = new TextContent(text.getBytes());

        // WHEN
        int numberOfLines = sut.getNumberOfLines();

        // THEN
        assertThat(numberOfLines, is(equalTo(4)));
    }

    @Test
    public void getLines_shouldReturnEmptyList_whenFileContentIsEmpty() {
        // GIVEN
        String text = "";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.isEmpty(), is(true));
    }

    @Test
    public void getLines_shouldReturnListOfOneElementAndContainingTheLine_whenFileContentHasContentButNoLinebreak() {
        // GIVEN
        String text = "Some content";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(1));
        assertThat(lines.get(0), is(equalTo(text.getBytes())));
    }

    @Test
    public void getLines_shouldReturnListWithTwoEmptyLines_whenFileContentIsOnlyUnixLineBrakeSymbols() {
        // GIVEN
        String text = "\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoEmptyLines_whenFileContentIsOnlyOldMacLineBrakeSymbols() {
        // GIVEN
        String text = "\r";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoEmptyLines_whenFileContentIsOnlyWindowsLineBrakeSymbols() {
        // GIVEN
        String text = "\r\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsUnixLineBrakePlusText() {
        // GIVEN
        String text = "\nThe second Line";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1), is(equalTo("The second Line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsOldMacLineBrakePlusText() {
        // GIVEN
        String text = "\rThe second Line";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1), is(equalTo("The second Line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsWindowsLineBrakePlusText() {
        // GIVEN
        String text = "\r\nThe second Line";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0).length, is(0));
        assertThat(lines.get(1), is(equalTo("The second Line".getBytes())));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsTextPlusUnixLineBrake() {
        // GIVEN
        String text = "The second Line\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0), is(equalTo("The second Line".getBytes())));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsTextPlusOldMacLineBrake() {
        // GIVEN
        String text = "The second Line\r";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0), is(equalTo("The second Line".getBytes())));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListWithTwoLines_whenFileContentIsTextPlusWindowsLineBrake() {
        // GIVEN
        String text = "The second Line\r\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

        // THEN
        assertThat(lines.size(), is(2));
        assertThat(lines.get(0), is(equalTo("The second Line".getBytes())));
        assertThat(lines.get(1).length, is(0));
    }

    @Test
    public void getLines_shouldReturnListOfBytesRepresentingLines_whenFileContentHasMultipleLinesAndUnixLineBreak() {
        // GIVEN
        String text = "The first line\nThe second line\nThe third line";
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        List<byte[]> lines = sut.getLines();

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
        sut = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = sut.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByUnixLineBreak_whenUnixLineBreakIsSet() {
        // GIVEN
        String text = "First line.\nSecond line.\nThird line.";
        sut = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = sut.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByOldMacLineBreak_whenOldMacLineBreakIsSet() {
        // GIVEN
        String text = "First line.\rSecond line.\rThird line.";
        sut = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = sut.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByWindowsLineBreak_whenWasReadAsWindowsAndChangedToUnix() {
        // GIVEN
        String text = "First line.\r\nSecond line.\r\nThird line.";
        sut = new TextContent(text.getBytes());

        // WHEN
        sut.setLineBreak(UNIX_LINE_BREAK);
        byte[] bytes = sut.getBytes();

        // THEN
        String resultContent = "First line.\nSecond line.\nThird line.";
        assertThat(bytes, is(equalTo(resultContent.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByWindowsLineBreak_whenWindowsLineBreakIsSetAtBeginAndEnd() {
        // GIVEN
        String text = "\r\nFirst line.\r\nSecond line.\r\nThird line.\r\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = sut.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByUnixLineBreak_whenUnixLineBreakIsSetAtBeginAndEnd() {
        // GIVEN
        String text = "\nFirst line.\nSecond line.\nThird line.\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = sut.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getBytes_shouldReturnLinesOfBytesSeparatedByOldMacLineBreak_whenOldMacLineBreakIsSetAtBeginAndEnd() {
        // GIVEN
        String text = "\rFirst line.\rSecond line.\rThird line.\r";
        sut = new TextContent(text.getBytes());

        // WHEN
        byte[] bytes = sut.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(text.getBytes())));
    }

    @Test
    public void getLineRange_shouldReturnLineRangeOfContent_whenLineRangeInTheMiddleOfTheFile() {
        // GIVEN
        String text = "First line.\nSecond line.\nThird line.";
        sut = new TextContent(text.getBytes());

        // WHEN
        LineRange lineRange = sut.getLineRange(1, 2);

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
        sut = new TextContent(content.getBytes());

        // WHEN
        LineRange lineRange = sut.getLineRange(0, 0);

        // THEN
        assertThat(lineRange.getLines().isEmpty(), is(true));
        assertThat(lineRange.getStartIndex(), is(equalTo(0)));
        assertThat(lineRange.getEndIndex(), is(equalTo(0)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLineRange_shouldThrowIndexOutOfBoundException_whenStartIndexIsLessThenZero() {
        // GIVEN
        String content = "";
        sut = new TextContent(content.getBytes());

        // WHEN
        sut.getLineRange(-1, 0);

        // THEN throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void getLineRange_shouldThrowIllegalArgumentException_whenEndIndexIsLessThenStartIndex() {
        // GIVEN
        String content = "Something\n";
        sut = new TextContent(content.getBytes());

        // WHEN
        sut.getLineRange(0, -1);

        // THEN throw exception
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLineRange_shouldThrowIndexOutOfBoundException_whenEndIndexIsGreaterThanNumberOfLines() {
        // GIVEN
        String content = "Something\nNextLine";
        sut = new TextContent(content.getBytes());

        // WHEN
        sut.getLineRange(0, 3);

        // THEN throw exception
    }

    @Test
    public void getLineRange_shouldReturnLineRangeOfTheWholeContent_always() {
        // GIVEN
        String text = "\nFirst line.\nSecond line.\nThird line.\n";
        sut = new TextContent(text.getBytes());

        // WHEN
        LineRange lineRange = sut.getLineRangeAll();

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

    @Test(expected = IllegalArgumentException.class)
    public void setLineRange_shouldThrowIllegalArgumentException_whenLinesInLineRangeAreNull() {
        // GIVEN
        List<byte[]> lines = null;
        LineRange lineRange = new LineRange(lines, 0, 10);

        // WHEN
        sut.setLineRange(lineRange);

        // THEN throw exception
    }

    @Test
    public void setLineRange_shouldSetTheSelectedLineRange_whenLineRangeBeginsAtTheBeginOfTheContentNoSizeChange() {
        // GIVEN
        int originalSize = 10;
        sut = new TextContent(setupOriginalLines());
        List<byte[]> lineRangeLines = Stream.of(
                "Newly created or edited line - first.",
                "Newly created or edited line - second.",
                "Newly created or edited line - third.")
                .map(String::getBytes)
                .collect(Collectors.toList());
        LineRange lineRange = new LineRange(lineRangeLines, 0, 3);

        // WHEN
        sut.setLineRange(lineRange);

        // THEN
        assertThat(sut.getLines().size(), is(equalTo(originalSize)));
        assertThat(sut.getLines().get(0), is(equalTo("Newly created or edited line - first.".getBytes())));
        assertThat(sut.getLines().get(1), is(equalTo("Newly created or edited line - second.".getBytes())));
        assertThat(sut.getLines().get(2), is(equalTo("Newly created or edited line - third.".getBytes())));
        assertThat(sut.getLines().get(3), is(equalTo("Forth line.".getBytes())));
        assertThat(sut.getLines().get(4), is(equalTo("Fifth line.".getBytes())));
        assertThat(sut.getLines().get(5), is(equalTo("Sixth line.".getBytes())));
        assertThat(sut.getLines().get(6), is(equalTo("Seventh line".getBytes())));
        assertThat(sut.getLines().get(7), is(equalTo("Eight line".getBytes())));
        assertThat(sut.getLines().get(8), is(equalTo("Ninth line.".getBytes())));
        assertThat(sut.getLines().get(9), is(equalTo("Tenth line.".getBytes())));
    }

    @Test
    public void setLineRange_shouldSetTheSelectedLineRange_whenLineRangeBeginsAtTheBeginOfTheContentShrunken() {
        // GIVEN
        sut = new TextContent(setupOriginalLines());
        List<byte[]> lineRangeLines = Stream.of(
                "Newly created or edited line - first.",
                "Newly created or edited line - second.")
                .map(String::getBytes)
                .collect(Collectors.toList());
        LineRange lineRange = new LineRange(lineRangeLines, 0, 3);

        // WHEN
        sut.setLineRange(lineRange);

        // THEN
        assertThat(sut.getLines().size(), is(equalTo(9)));
        assertThat(sut.getLines().get(0), is(equalTo("Newly created or edited line - first.".getBytes())));
        assertThat(sut.getLines().get(1), is(equalTo("Newly created or edited line - second.".getBytes())));
        assertThat(sut.getLines().get(2), is(equalTo("Forth line.".getBytes())));
        assertThat(sut.getLines().get(3), is(equalTo("Fifth line.".getBytes())));
        assertThat(sut.getLines().get(4), is(equalTo("Sixth line.".getBytes())));
        assertThat(sut.getLines().get(5), is(equalTo("Seventh line".getBytes())));
        assertThat(sut.getLines().get(6), is(equalTo("Eight line".getBytes())));
        assertThat(sut.getLines().get(7), is(equalTo("Ninth line.".getBytes())));
        assertThat(sut.getLines().get(8), is(equalTo("Tenth line.".getBytes())));
    }

    @Test
    public void setLineRange_shouldSetTheSelectedLineRange_whenLineRangeBeginsAtTheBeginOfTheContentExpanded() {
        // GIVEN
        sut = new TextContent(setupOriginalLines());
        List<byte[]> lineRangeLines = Stream.of(
                "Newly created or edited line - first.",
                "Newly created or edited line - second.",
                "Newly created or edited line - third.",
                "Newly created or edited line - forth.")
                .map(String::getBytes)
                .collect(Collectors.toList());
        LineRange lineRange = new LineRange(lineRangeLines, 0, 3);

        // WHEN
        sut.setLineRange(lineRange);

        // THEN
        assertThat(sut.getLines().size(), is(equalTo(11)));
        assertThat(sut.getLines().get(0), is(equalTo("Newly created or edited line - first.".getBytes())));
        assertThat(sut.getLines().get(1), is(equalTo("Newly created or edited line - second.".getBytes())));
        assertThat(sut.getLines().get(2), is(equalTo("Newly created or edited line - third.".getBytes())));
        assertThat(sut.getLines().get(3), is(equalTo("Newly created or edited line - forth.".getBytes())));
        assertThat(sut.getLines().get(4), is(equalTo("Forth line.".getBytes())));
        assertThat(sut.getLines().get(5), is(equalTo("Fifth line.".getBytes())));
        assertThat(sut.getLines().get(6), is(equalTo("Sixth line.".getBytes())));
        assertThat(sut.getLines().get(7), is(equalTo("Seventh line".getBytes())));
        assertThat(sut.getLines().get(8), is(equalTo("Eight line".getBytes())));
        assertThat(sut.getLines().get(9), is(equalTo("Ninth line.".getBytes())));
        assertThat(sut.getLines().get(10), is(equalTo("Tenth line.".getBytes())));
    }

    private byte[] setupOriginalLines() {
        return String.join("\r\n",
                "First line.",
                "Second line.",
                "Third line.",
                "Forth line.",
                "Fifth line.",
                "Sixth line.",
                "Seventh line",
                "Eight line",
                "Ninth line.",
                "Tenth line.")
                .getBytes();
    }

}
