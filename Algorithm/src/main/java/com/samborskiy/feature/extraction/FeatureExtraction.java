package com.samborskiy.feature.extraction;

import com.samborskiy.feature.Feature;

/**
 * Created by Whiplash on 03.05.2015.
 */
public abstract class FeatureExtraction extends Feature {

    @Override
    public String toString() {
        return String.format("feature extraction algorithm %s", super.toString());
    }
}
