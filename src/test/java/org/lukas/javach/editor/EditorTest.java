package org.lukas.javach.editor;

import org.junit.Before;
import org.junit.Test;
import org.lukas.javach.document.Document;
import org.lukas.javach.document.TextDocument;
import org.lukas.javach.document.TextDocumentTest;

/**
 * Created by Lukas on 14.08.2019.
 *
 * @author Lukas Pecak
 */
public class EditorTest {

    private Editor editor;

    @Before
    public void setupTest() {
        editor = new Editor();
    }

    @Test(expected = IllegalArgumentException.class)
    public void openDocument_shouldThrowIllegalArgumentException_whenDocumentReferenceIsNull() {
        // GIVEN
        Document document = null;

        // WHEN
        editor.openDocument(document);

        // THEN THROW
    }

    @Test
    public void openDocument_shouldOpenDocument_whenDocumentReferenceIsNotNull() {
        // GIVE
        byte[] contentBytes = "first line\nsecond line".getBytes();
        Document document = new TextDocument(contentBytes);

        // WHEN
        editor.openDocument(document);

        // THEN
        // document is open
    }
}
