package org.lukas.javach.document;

/**
 * Created by Lukas on 17.08.2019.
 *
 * @author Lukas Pecak
 */
public interface DocumentContentFactory {

    /**
     * Creates a DocumentContent object for given byte array
     * @param bytes Input byte array - file content
     * @return DocumentContent object
     */
    DocumentContent createDocumentContent(byte[] bytes);
}
