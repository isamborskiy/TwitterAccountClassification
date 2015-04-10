package com.samborskiy.entity.instances;

/**
 * Created by Whiplash on 30.03.2015.
 */
public abstract class Instance implements Iterable<String> {

    protected final int classId;

    /**
     * Returns new instance of {@code Instance} with init {@code classId}.
     *
     * @param classId id of class (eg. 0 is personal, 1 is corporate)
     */
    public Instance(int classId) {
        this.classId = classId;
    }

    public int getClassId() {
        return classId;
    }

    public abstract int size();
}
