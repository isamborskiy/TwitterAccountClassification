package com.samborskiy.classifier.entities.sequences;

import java.util.Arrays;
import java.util.List;

/**
 * Created by whiplash on 08.06.15.
 */
public abstract class Sequence<E> {

    protected final String name;
    protected final List<E> sequence;

    public Sequence(String name, E... sequence) {
        this.name = name;
        this.sequence = Arrays.asList(sequence);
    }

    public abstract int count(String tweet);

    public boolean match(String tweet) {
        return count(tweet) > 0;
    }

    @Override
    public String toString() {
        return name;
    }

}
