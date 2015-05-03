package com.samborskiy.entity.instances;

import com.samborskiy.entity.instances.functions.AttributeFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Whiplash on 21.04.2015.
 */
public abstract class Instance implements Iterable<Attribute> {

    protected final int classId;
    protected final List<Attribute> attrs;

    public Instance(int classId) {
        this.classId = classId;
        attrs = new ArrayList<>();
    }

    public int getClassId() {
        return classId;
    }

    public abstract void addAttr(AttributeFunction attributeFunction);

    public void addAttr(Attribute attribute) {
        attrs.add(attribute);
    }

    @Override
    public Iterator<Attribute> iterator() {
        return attrs.iterator();
    }

    @Override
    public void forEach(Consumer<? super Attribute> action) {
        attrs.forEach(action);
    }

    @Override
    public Spliterator<Attribute> spliterator() {
        return attrs.spliterator();
    }
}
