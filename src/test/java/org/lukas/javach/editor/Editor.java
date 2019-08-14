package org.lukas.javach.editor;

import org.lukas.javach.document.Document;

/**
 * Created by Lukas on 14.08.2019.
 *
 * @author Lukas Pecak
 */
class Editor {

    void openDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("The provided document cannot be null");
        }
    }
}
