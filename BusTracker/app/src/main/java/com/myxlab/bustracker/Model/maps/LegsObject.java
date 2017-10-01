package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */

import java.util.List;

/**
 * The type Legs object.
 */
public class LegsObject {

    private List<StepsObject> steps;

    /**
     * Instantiates a new Legs object.
     *
     * @param steps the steps
     */
    public LegsObject(List<StepsObject> steps) {
        this.steps = steps;
    }

    /**
     * Gets steps.
     *
     * @return the steps
     */
    public List<StepsObject> getSteps() {
        return steps;
    }


}