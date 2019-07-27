package org.lukas.javach;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Lukas on 27.07.2019.
 *
 * @author Lukas Pecak
 */
public class EdiTest {

    private Edi edi;

    @Before
    public void setupTests() {
        edi = new Edi();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentException_whenNoFileNameIsProvided() {
        edi.loadFile("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentException_whenPassedArgumentIsNull() {
        edi.loadFile(null);
    }

    @Test
    public void returnNull_whenFileNotFound() {
        List<String> lines = edi.loadFile("nonExistingPath");

        assertThat(lines, is(nullValue()));
    }

    @Test
    public void returnListWithFileLines_whenFileExists() {
        List<String> lines = edi.loadFile("src/test/resources/sampleFile.txt");

        assertThat(lines, is(notNullValue()));
    }

    @Test
    public void readMultipleLinesFromFile_whenFileExists() {
        List<String> lines = edi.loadFile("src/test/resources/sampleMultilineFile.txt");

        assertThat(lines.size(), is(greaterThan(1)));
    }
}