package org.lukas.javach.editor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.lukas.javach.document.Document;

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
}
