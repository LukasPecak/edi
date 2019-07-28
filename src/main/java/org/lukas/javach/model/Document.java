package org.lukas.javach.model;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
class Document {

    private static final int CARRIAGE_RETURN = 0x0D;
    private static final int LINE_FEED = 0x0A;
    static final byte[] WINDOWS_LINE_SEPARATOR = {CARRIAGE_RETURN, LINE_FEED};
    static final byte[] UNIX_LINE_SEPARATOR = {LINE_FEED};
    static final byte[] OLD_MAC_LINE_SEPARATOR = {CARRIAGE_RETURN};

    private byte[] bytes;
    private byte[] lineSeparator;

    Document(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("Cannot initialize a document with a null array of bytes");
        }
        this.bytes = bytes;

        lineSeparator = resolveLineSeparator(bytes);
    }

    private byte[] resolveLineSeparator(byte[] bytes) {
        if (bytes.length == 0) {
            return System.lineSeparator().getBytes();
        }
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == CARRIAGE_RETURN) {
                if (bytes[i + 1] == LINE_FEED) {
                    return WINDOWS_LINE_SEPARATOR;
                }
                return OLD_MAC_LINE_SEPARATOR;
            } else if (bytes[i] == LINE_FEED) {
                return UNIX_LINE_SEPARATOR;
            }
        }
        return System.lineSeparator().getBytes();
    }

    byte[] getBytes() {
        return bytes;
    }

    byte[] getLineSeparator() {
        return lineSeparator;
    }
}
