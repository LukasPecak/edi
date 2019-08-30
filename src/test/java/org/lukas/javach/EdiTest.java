package org.lukas.javach;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Lukas on 27.07.2019.
 *
 * @author Lukas Pecak
 */
public class EdiTest {

    private Edi sut;

    @Before
    public void setupTests() {
        sut = new Edi();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentException_whenNoFileNameIsProvided() {
        sut.loadBytes("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentException_whenPassedArgumentIsNull() {
        sut.loadBytes(null);
    }

    @Test
    public void returnNull_whenFileNotFoundOrOtherIOException() {
        // GIVEN

        // WHEN
        byte[] bytes = sut.loadBytes("nonExistingPath");

        // THEN
        assertThat(bytes, is(nullValue()));
    }

    @Test
    public void returnListWithFileLines_whenFileExists() {

        byte[] bytes = sut.loadBytes("src/test/resources/sampleFile.txt");

        assertThat(bytes, is(notNullValue()));
    }

    @Test
    public void loadFile() {
        String pathString = "src/test/resources/sampleFile.txt";
        byte[] bytes = sut.loadBytes(pathString);

        assertThat(new String(bytes), is(equalTo("Sample file with sample content")));
    }

    @Test
    public void readMultipleLinesFromFile_shouldHaveTheLineFeedAndCarriageReturn_whenWindowsMultiLineFile() {
        byte[] bytes = sut.loadBytes("src/test/resources/sampleMultilineFile.txt");

        assertThat(bytes, hasItemInArray((byte) 0x0A));
        assertThat(bytes, hasItemInArray((byte) 0x0D));
    }

    private Matcher<byte[]> hasItemInArray(byte charCode) {
        return new BaseMatcher<>() {
            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("array contains character with code : %d", charCode));
            }

            @Override
            public boolean matches(Object item) {
                if (!(item instanceof byte[])) {
                    return false;
                }
                for (byte character : (byte[]) item) {
                    if (character == charCode) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Test
    public void saveFile_shouldSaveFileAndRemainTheContentUnchanged_whenNoChangesMade() throws IOException {
        // Read existing content from file
        String pathString = "src/test/resources/sampleMultilineFile.txt";
        Path path = Paths.get(pathString);
        byte[] bytesOriginal = Files.readAllBytes(path);

        // Clear file content
        sut.saveBytes(new byte[0], pathString);
        byte[] bytesEdited = Files.readAllBytes(path);
        assertThat(bytesEdited.length, is(0));

        // Restore the original content
        sut.saveBytes(bytesOriginal, pathString);
        bytesEdited = Files.readAllBytes(path);
        assertThat(bytesEdited, is(equalTo(bytesOriginal)));
    }

    @Test
    public void saveFile_shouldThrowIOExceptionAndLogError_whenPathStringIsEmpty() {
        // GIVEN
        String pathString = "";
        byte[] content = "Some file content\n".getBytes();

        // WHEN
        sut.saveBytes(content, pathString);

        // THEN THROW/CATCH EXCEPTION AND LOG ERROR
    }
}