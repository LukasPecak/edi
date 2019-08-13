package org.lukas.javach;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.lukas.javach.model.LineBreakTest;
import org.lukas.javach.model.TextDocumentTest;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EdiTest.class,
        TextDocumentTest.class,
        LineBreakTest.class
})
public class TestSuite {}
