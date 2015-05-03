package com.samborskiy.feature.selection;

import com.samborskiy.feature.Feature;

/**
 * Created by Whiplash on 30.04.2015.
 */
public abstract class FeatureSelection extends Feature {

    @Override
    public String toString() {
        return String.format("feature subset selection algorithm %s", super.toString());
    }
}
