package org.lukas.javach.editor;

import org.junit.Before;
import org.junit.Test;
import org.lukas.javach.document.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Lukas on 14.08.2019.
 *
 * @author Lukas Pecak
 */
public class EditorTest {

    private Editor editor;
    private DocumentContentFactory contentFactory;

    @Before
    public void setupTest() {
        editor = new Editor();
        contentFactory = new DocumentContentFactoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void openDocument_shouldThrowIllegalArgumentException_whenDocumentReferenceIsNull() {
        // GIVEN
        DocumentContent content = null;

        // WHEN
        editor.openContent(content);

        // THEN THROW
    }

    @Test
    public void getLine_shouldReturnLineRepresentationAsString_whenDocumentIsOpened(){
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);

        // WHEN
        String line0 = editor.getLine(0);
        String line1 = editor.getLine(1);
        String line2 = editor.getLine(2);

        // THEN
        assertThat(line0, is(equalTo("First line")));
        assertThat(line1, is(equalTo("The second line")));
        assertThat(line2, is(equalTo("Third line")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getLine_shouldThrowIllegalArgumentException_whenLineNumberIsLessThenZero(){
        // GIVEN
        String content = "First line\r\nThe second line\r\nThird line";
        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
        editor.openContent(documentContent);
        int lineNumber = -1;

        // WHEN
        editor.getLine(lineNumber);

        // THEN Should throw exception
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void getLine_shouldThrowIllegalArgumentException_whenLineNumberIsGreaterThanNumberOfLinesInContent(){
//        // GIVEN
//        String content = "First line\r\nThe second line\r\nThird line";
//        DocumentContent documentContent = contentFactory.createDocumentContent(content.getBytes());
//        editor.openContent(documentContent);
//        int lineNumber = 4;
//
//        // WHEN
//
//
//        // THEN Should throw exception
//    }
}
