package eu.qm.fiszki;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andrevus on 16.02.16.
 */
public class CheckerTest {

    private Checker mChecker;

    @Before
    public void setUp() throws Exception {
        mChecker = new Checker();
    }

    @Test
    public void testShouldReturnTrueWhenSameWordsAreProvided() throws Exception {
        assertTrue(mChecker.Check(new String("test"), new String("test")));
    }

    @Test
    public void testShouldReturnFalseWhenSameWordsAreProvided() throws Exception {
        assertFalse(mChecker.Check(new String("test"), new String("test2")));
    }
}