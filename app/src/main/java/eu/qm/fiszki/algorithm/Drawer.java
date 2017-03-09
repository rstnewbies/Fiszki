package eu.qm.fiszki.algorithm;

import java.util.Random;
/**
 * Created by mBoiler on 31.03.2016.
 */
public class Drawer {
        public int drawInteger(int max){
            Random rand = new Random();
            return rand.nextInt(max);
        }
}
