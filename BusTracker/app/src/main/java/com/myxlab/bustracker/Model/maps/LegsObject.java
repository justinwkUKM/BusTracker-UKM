package com.myxlab.bustracker.Model.maps;

/**
 * Created by MyXLab on 4/8/2017.
 */

import java.util.List;
public class LegsObject {

    private List<StepsObject> steps;

    public LegsObject(List<StepsObject> steps) {
        this.steps = steps;
    }

    public List<StepsObject> getSteps() {
        return steps;
    }


}