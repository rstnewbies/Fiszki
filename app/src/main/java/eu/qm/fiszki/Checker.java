package eu.qm.fiszki;

public class Checker {


 //TODO: to rules
    public Boolean Check(String originalWord,String enteredWord){
        if(originalWord.compareTo(enteredWord)== 0) return true;
        else return false;
    }
}
