package eu.qm.fiszki.algorithm;

import eu.qm.fiszki.R;

/**
 * Created by mBoiler on 31.03.2016.
 */
public class MultiplierPoints {

    int[] priority;
    int[] priorytyRange;

    public MultiplierPoints(int[] priority) {
        this.priority = priority;
        priorytyRange = new int[5];
    }

    public int[] multipler() {

        priorytyRange[0] = priority[0] * 25;
        priorytyRange[1] = (priority[1] * 20) + priorytyRange[0];
        priorytyRange[2] = (priority[2] * 15) + priorytyRange[1];
        priorytyRange[3] = (priority[3] * 10) + priorytyRange[2];
        priorytyRange[4] = (priority[4] * 5) + priorytyRange[3];

        return priorytyRange;
    }
}
