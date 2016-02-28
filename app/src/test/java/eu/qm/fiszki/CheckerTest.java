package eu.qm.fiszki;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mBoiler on 28.02.2016.
 */
public class CheckerTest {

    private Checker tchecker;

    @Before
    public void setUp() throws Exception {
        tchecker = new Checker();
    }

    @Test
    public void testCheckTrue() throws Exception {
        assertTrue(tchecker.check(new String("test"), new String("test")));
    }

    @Test
    public void testCheckFalse() throws Exception {
        assertFalse(tchecker.check(new String("test"),new String("notest")));
    }
}