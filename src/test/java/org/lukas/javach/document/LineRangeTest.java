package org.lukas.javach.document;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Lukas on 19.08.2019.
 *
 * @author Lukas Pecak
 */
public class LineRangeTest {

    @Test
    public void getStartIndex_shouldReturnRangeStartIndex_whenStartIndexGreaterOrEqualToZero() {
        // GIVEN
        LineRange lineRange = new LineRange(Collections.emptyList(), 5, 10);

        // WHEN
        int startIndex = lineRange.getStartIndex();

        // THEN
        assertThat(startIndex, is(equalTo(5)));
    }

    @Test
    public void getStartIndex_shouldReturnRangeStartIndexZero_whenStartIndexEqualToZero() {
        // GIVEN
        LineRange lineRange = new LineRange(Collections.emptyList(), 0, 10);

        // WHEN
        int startIndex = lineRange.getStartIndex();

        // THEN
        assertThat(startIndex, is(equalTo(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStartIndex_shouldThrowIllegalArgumentException_whenStartIndexLessThenZero() {
        // WHEN try to initialize object
        new LineRange(Collections.emptyList(), -1, 10);

        // THEN throw exception
    }

    @Test
    public void getEndIndex_shouldReturnRangeEndIndex_whenEndIndexGreaterOrEqualToZero() {
        // GIVEN
        LineRange lineRange = new LineRange(Collections.emptyList(), 5, 10);

        // WHEN
        int endIndex = lineRange.getEndIndex();

        // THEN
        assertThat(endIndex, is(equalTo(10)));
    }

    @Test
    public void getEndIndex_shouldReturnRangeEndIndexZero_whenEndIndexEqualToZero() {
        // GIVEN
        LineRange lineRange = new LineRange(Collections.emptyList(), 5, 0);

        // WHEN
        int endIndex = lineRange.getEndIndex();

        // THEN
        assertThat(endIndex, is(equalTo(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getEndIndex_shouldThrowIllegalArgumentException_whenEndIndexLessThenZero() {
        // WHEN try to initialize object
        new LineRange(Collections.emptyList(), 5, -5);

        // THEN throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void getLines_shouldThrowIllegalArgumentException_whenLinesAreNull() {
        // WHEN try to initialize object
        new LineRange(null, 5, 10);

        // THEN throw exception
    }

    @Test
    public void getLines_shouldReturnEmptyLines_whenInitializedWithAnEmptyList() {
        // GIVEN
        LineRange lineRange = new LineRange(Collections.emptyList(), 5, 10);

        // WHEN
        List<byte[]> lines = lineRange.getLines();

        // THEN
        assertThat(lines, is(notNullValue()));
        assertThat(lines.isEmpty(), is(true));
    }

    @Test
    public void getLines_shouldReturnNonEmptyLines_whenInitializedWithAnNonEmptyList() {
        // GIVEN
        byte[] line = new byte[5];
        LineRange lineRange = new LineRange(Arrays.asList(line, line, line), 5, 10);

        // WHEN
        List<byte[]> lines = lineRange.getLines();

        // THEN
        assertThat(lines, is(notNullValue()));
        assertThat(lines.size(), is(3));
        assertThat(lines.get(0), is(equalTo(line)));
        assertThat(lines.get(1), is(equalTo(line)));
        assertThat(lines.get(2), is(equalTo(line)));
    }
}
