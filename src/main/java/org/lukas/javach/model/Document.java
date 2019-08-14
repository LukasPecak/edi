package org.lukas.javach.model;

import java.util.List;

/**
 * Created by Lukas on 13.08.2019.
 *
 * @author Lukas Pecak
 */
public interface Document {

    /**
     * Method to get the full document content represented as array of bytes including line breaks
     * @return Document content as byte array
     */
    byte[] getBytes();

    /**
     * Method to get all lines in document separated by the document line break (lines without line break)
     * @return List of lines represented as byte arrays
     */
    List<byte[]> getLines();

    /**
     * Method to get the count of line in a document. Should match the number of lines returned by getLines() method
     * @return Number of lines in document
     */
    int getNumberOfLines();

    /**
     * Method to get the line separator that was detected or set for this document
     * @return Line separator as Enum
     */
    LineBreak getLineBreak();

    /**
     * Method to set the line separator (override the detected one)
     * @param lineBreak as Enum value
     */
    void setLineBreak(LineBreak lineBreak);
}
