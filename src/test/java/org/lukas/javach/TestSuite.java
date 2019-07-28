package org.lukas.javach;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.lukas.javach.model.DocumentTest;

/**
 * Created by Lukas on 28.07.2019.
 *
 * @author Lukas Pecak
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EdiTest.class,
        DocumentTest.class
})
public class TestSuite {}
