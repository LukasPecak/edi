package org.lukas.javach.editor;

import org.lukas.javach.document.DocumentContent;

/**
 * Created by Lukas on 14.08.2019.
 *
 * @author Lukas Pecak
 */
class Editor {

    private DocumentContent content;

    void openContent(DocumentContent content) {
        if (content == null) {
            throw new IllegalArgumentException("The provided content cannot be null");
        }
        this.content = content;
    }

    String getLine(int lineNumber) {
        if (lineNumber < 0) {
            throw new IllegalArgumentException("Line number cannot be smaller then 0");
        }
        if (lineNumber > content.getNumberOfLines()) {
            throw new IllegalArgumentException("Line number is bigger then content size");
        }
        return new String(content.getLines().get(lineNumber));
    }
}
