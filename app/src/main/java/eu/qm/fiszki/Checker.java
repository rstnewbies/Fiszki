package eu.qm.fiszki;

/**
 * Created by mBoiler on 28.02.2016.
 */
public class Checker {

    public boolean check(String originalWord, String enteredWord) {
        if (originalWord.compareTo(enteredWord) == 0) return true;
        else return false;
    }
}
