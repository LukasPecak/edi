package org.lukas.javach.document;

import java.util.List;

/**
 * Created by Lukas on 15.08.2019.
 *
 * @author Lukas Pecak
 */
public interface DocumentContent {
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
     * Method to get the line break that was detected or set for this document
     * @return LineBreak type Enum
     */
    LineBreak getLineBreak();

    /**
     * Method to set the line break (override the detected one)
     * @param lineBreak as Enum value
     */
    void setLineBreak(LineBreak lineBreak);

    /**
     * Method to get the line range of the content between startIndex inclusive and endIndex exclusive
     * @param startIndex line range inclusive
     * @param endIndex line range exclusive
     * @return LineRange object which contains the list of lines in the requested range and the start and end indexes
     */
    LineRange getLineRange(int startIndex, int endIndex) ;

    /**
     * Method to get the line range of the whole content - startIndex = 0 to endIndex = lines.size()
     * @return LineRange object which contains the list in content with indexes set accordingly
     */
    LineRange getLineRangeAll();

    /**
     * Method to set the LineRang that was edited
     * @param lineRange
     */
    void setLineRange(LineRange lineRange);
}
