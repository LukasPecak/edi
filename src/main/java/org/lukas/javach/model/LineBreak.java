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

    private static final int CARRIAGE_RETURN = 0x0D;
    private static final int LINE_FEED = 0x0A;

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

    /**
     * Method to test if the given LineBreak object is a known line break
     * @param lineBreak Given LineBreak object
     * @return true if input object represents windows, unix or old mac line break; false for other cases
     */
    static boolean isKnownLineBreak(LineBreak lineBreak) {
        return WINDOWS_LINE_BREAK.equals(lineBreak)
                || UNIX_LINE_BREAK.equals(lineBreak)
                || OLD_MAC_LINE_BREAK.equals(lineBreak);
    }

    /**
     * Method to resolve line break type for the given byte array
     * @param bytes Input byte arrays where line break to be found
     * @return First found line break type in the array or system line brake if not found
     */
    static LineBreak resolveLineBreak(byte[] bytes) {
        if (bytes.length == 0) {
            return LineBreak.systemDefaultLineBreak();
        }
        LineBreak firstLineBreak = findFirstLineBreak(bytes);
        if (firstLineBreak != UNDEFINED_LINE_BREAK) {
            return firstLineBreak;
        }
        LineBreak lineBreakAtLastByte = findLineBreakAtLastByte(bytes);
        if (lineBreakAtLastByte != UNDEFINED_LINE_BREAK) {
            return lineBreakAtLastByte;
        }
        return systemDefaultLineBreak();
    }

    private static LineBreak findFirstLineBreak(byte[] bytes) {
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == CARRIAGE_RETURN) {
                if (bytes[i + 1] == LINE_FEED) {
                    return WINDOWS_LINE_BREAK;
                }
                return OLD_MAC_LINE_BREAK;
            } else if (bytes[i] == LINE_FEED) {
                return UNIX_LINE_BREAK;
            }
        }
        return UNDEFINED_LINE_BREAK;
    }

    private static LineBreak findLineBreakAtLastByte(byte[] bytes) {
        byte lastByte = bytes[bytes.length - 1];
        if (lastByte == LINE_FEED) {
            return UNIX_LINE_BREAK;
        } else if (lastByte == CARRIAGE_RETURN) {
            return OLD_MAC_LINE_BREAK;
        }
        return UNDEFINED_LINE_BREAK;
    }
}
