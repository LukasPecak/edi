package org.lukas.javach.model;

import org.junit.Before;
import org.junit.Test;

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
    public void getLineSeparator_shouldReturnTheOldMacLineSeparator_whenDocumentHasMultipleLinesAndOldMacFormatting() {
        // GIVEN
        String oldMacLineSeparator = "\r";
        document = new Document(("This is the first line" + oldMacLineSeparator + "This is the second line").getBytes());

        // WHEN
        byte[] separator = document.getLineSeparator();

        // THEN
        assertThat(separator, is(equalTo(OLD_MAC_LINE_SEPARATOR)));
    }
}
