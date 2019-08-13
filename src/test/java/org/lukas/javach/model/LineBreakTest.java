package org.lukas.javach.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Lukas on 13.08.2019.
 *
 * @author Lukas Pecak
 */
public class LineBreakTest {

    private static final int CARRIAGE_RETURN = 0x0D;
    private static final int LINE_FEED = 0x0A;

    @Test
    public void getBytes_shouldReturnCarriageReturnAndLineFeed_whenLineBreakIsWindows() {
        // GIVEN
        LineBreak lineBreak = LineBreak.WINDOWS_LINE_BREAK;

        // WHEN
        byte[] bytes = lineBreak.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(new byte[]{CARRIAGE_RETURN, LINE_FEED})));
    }

    @Test
    public void getBytes_shouldReturnLineFeed_whenLineBreakIsUnix() {
        // GIVEN
        LineBreak lineBreak = LineBreak.UNIX_LINE_BREAK;

        // WHEN
        byte[] bytes = lineBreak.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(new byte[]{LINE_FEED})));
    }

    @Test
    public void getBytes_shouldReturnCarriageReturn_whenLineBreakIsOldMac() {
        // GIVEN
        LineBreak lineBreak = LineBreak.OLD_MAC_LINE_BREAK;

        // WHEN
        byte[] bytes = lineBreak.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(new byte[]{CARRIAGE_RETURN})));
    }

    @Test
    public void getBytes_shouldReturnEmptyArray_whenLineBreakIsUndefinedLineBreak() {
        // GIVEN
        LineBreak lineBreak = LineBreak.UNDEFINED_LINE_BREAK;

        // WHEN
        byte[] bytes = lineBreak.getBytes();

        // THEN
        assertThat(bytes, is(equalTo(new byte[]{})));
    }

    @Test
    public void valueOf_shouldReturnWindowsLineBreak_whenSuppliedByteArrayRepresentsWindowsLineBreak() {
        // GIVEN
        byte[] bytes = new byte[]{0x0D, 0x0A};

        // WHEN
        LineBreak lineBreak = LineBreak.valueOf(bytes);

        // THEN
        assertThat(lineBreak, is(LineBreak.WINDOWS_LINE_BREAK));
    }

    @Test
    public void valueOf_shouldReturnUnixLineBreak_whenSuppliedByteArrayRepresentsUnixLineBreak() {
        // GIVEN
        byte[] bytes = new byte[]{0x0A};

        // WHEN
        LineBreak lineBreak = LineBreak.valueOf(bytes);

        // THEN
        assertThat(lineBreak, is(LineBreak.UNIX_LINE_BREAK));
    }

    @Test
    public void valueOf_shouldReturnOldMacLineBreak_whenSuppliedByteArrayRepresentsOldMacLineBreak() {
        // GIVEN
        byte[] bytes = new byte[]{0x0D};

        // WHEN
        LineBreak lineBreak = LineBreak.valueOf(bytes);

        // THEN
        assertThat(lineBreak, is(LineBreak.OLD_MAC_LINE_BREAK));
    }

    @Test
    public void valueOf_shouldReturnUndefinedLineBreak_whenSuppliedByteArrayIsEmpty() {
        // GIVEN
        byte[] bytes = new byte[]{};

        // WHEN
        LineBreak lineBreak = LineBreak.valueOf(bytes);

        // THEN
        assertThat(lineBreak, is(LineBreak.UNDEFINED_LINE_BREAK));
    }

    @Test
    public void valueOf_shouldReturnUndefinedLineBreak_whenSuppliedByteArrayHasContentWithNoSens() {
        // GIVEN
        byte[] bytes = new byte[]{13,15,19,21};

        // WHEN
        LineBreak lineBreak = LineBreak.valueOf(bytes);

        // THEN
        assertThat(lineBreak, is(LineBreak.UNDEFINED_LINE_BREAK));
    }
}