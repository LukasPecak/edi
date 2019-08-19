package org.lukas.javach;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.lukas.javach.document.DocumentMetadataTest;
import org.lukas.javach.document.LineBreakTest;
import org.lukas.javach.document.LineRangeTest;
import org.lukas.javach.document.TextContentTest;
import org.lukas.javach.editor.EditorTest;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EdiTest.class,
        TextContentTest.class,
        LineBreakTest.class,
        EditorTest.class,
        DocumentMetadataTest.class,
        LineRangeTest.class
})
public class TestSuite {}
