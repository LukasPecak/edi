package org.lukas.javach.model;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
class Document {

    private byte[] bytes;

    Document(byte[] bytes) {
        this.bytes = bytes;
    }

    byte[] getBytes() {
        return bytes;
    }
}
