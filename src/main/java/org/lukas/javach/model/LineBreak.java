package org.lukas.javach.model;

import java.util.Arrays;

/**
 * Created by Lukas on 13.08.2019.
 *
 * @author Lukas Pecak
 */
public enum LineBreak {
    WINDOWS_LINE_BREAK(new byte[]{0x0D, 0x0A}),
    UNIX_LINE_BREAK(new byte[]{0x0A}),
    OLD_MAC_LINE_BREAK(new byte[]{0x0D}),
    UNDEFINED_LINE_BREAK(new byte[0]);

    private byte[] bytes;

    LineBreak(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Method to get the line break represented as a array of bytes
     * @return Byte array representing line break
     */
    byte[] getBytes() {
        return bytes;
    }

    /**
     * Method to get LineBreak object out of byte array representation of a line break
     * @param bytes Arrays of bytes representing line break
     * @return LineBreak The line break object
     */
    static LineBreak valueOf(byte[] bytes) {
        if (Arrays.equals(WINDOWS_LINE_BREAK.bytes, bytes)) {
            return WINDOWS_LINE_BREAK;
        } else if (Arrays.equals(UNIX_LINE_BREAK.bytes, bytes)) {
            return UNIX_LINE_BREAK;
        } else if (Arrays.equals(OLD_MAC_LINE_BREAK.bytes, bytes)) {
            return OLD_MAC_LINE_BREAK;
        }
        return UNDEFINED_LINE_BREAK;
    }

    /**
     * Method to resolve the current system line break symbol as a Enum
     * @return LineBreak of current system line break
     */
    static LineBreak systemDefaultLineBreak() {
        return valueOf(System.lineSeparator().getBytes());
    }
}
