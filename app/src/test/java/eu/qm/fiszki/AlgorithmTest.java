package eu.qm.fiszki;

import org.junit.Before;
import org.junit.Test;

import eu.qm.fiszki.model.flashcard.Flashcard;

import static org.junit.Assert.*;

/**
 * Created by mBoiler on 26.02.2016.
 */
public class AlgorithmTest {

    Flashcard fiszka;

    @Before
    public void setUp() throws Exception {
        fiszka = new Flashcard();
    }

    @Test
    public void testDrawCardAlgorithm() throws Exception {
        assertNotNull(fiszka);
    }
}