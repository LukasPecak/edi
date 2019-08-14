package org.lukas.javach;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.lukas.javach.document.LineBreakTest;
import org.lukas.javach.document.TextDocumentTest;
import org.lukas.javach.editor.EditorTest;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EdiTest.class,
        TextDocumentTest.class,
        LineBreakTest.class,
        EditorTest.class
})
public class TestSuite {}
