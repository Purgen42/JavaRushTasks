package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object();

    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<E, Object>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        map = new HashMap<E, Object>(Math.max(16, (int)Math.ceil(collection.size()/.75f)));
        addAll(collection);
    }

    @Override
    public boolean add(E e) {
        return (map.put(e, PRESENT) == null);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean remove(Object o) {
        return (map.remove(o) != null);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone() {
        try {
            AmigoSet<E> clone = (AmigoSet<E>) super.clone();
            clone.map = (HashMap<E, Object>) map.clone();
            return clone;
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        stream.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        stream.writeInt(map.keySet().size());
        for(E entry: map.keySet()) {
            stream.writeObject(entry);
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int capacity = stream.readInt();
        float loadFactor = stream.readFloat();
        int size = stream.readInt();
        map = new HashMap<E, Object>(capacity, loadFactor);
        for (int i = 0; i < size; i++) {
            map.put((E) stream.readObject(), PRESENT);
        }
    }

}
