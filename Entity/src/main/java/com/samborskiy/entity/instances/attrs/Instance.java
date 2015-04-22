package com.samborskiy.entity.instances.attrs;

import com.samborskiy.entity.instances.attributes.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 21.04.2015.
 */
public abstract class Instance {

    protected final int classId;
    protected final List<Double> attrs;

    public Instance(int classId) {
        this.classId = classId;
        attrs = new ArrayList<>();
    }

    public int getClassId() {
        return classId;
    }

    public int size() {
        return attrs.size();
    }

    public double get(int i) {
        return attrs.get(i);
    }

    public abstract void addAttr(Attribute attribute);
}
