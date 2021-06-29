package com.javarush.task.task33.task3310.strategy;

public class OurHashMapStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;


    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        return getKey(value) != null;
    }

    @Override
    public void put(Long key, String value) {
        int index = indexFor(hash(key), table.length);
        addEntry(hash(key), key, value, index);
        if (size > threshold) {
            resize(table.length * 2);
        }
    }

    @Override
    public Long getKey(String value) {
        Entry entry;
        for (int i = 0; i < table.length; i++) {
            entry = table[i];
            while (entry != null) {
                if (entry.getValue().equals(value)) {
                    return entry.getKey();
                }
                entry = entry.next;
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);
        return (entry != null) ? entry.getValue() : null;
    }

    private int hash(Long k) {
        int h;
        return (k == null) ? 0 : (h = k.intValue()) ^ (h >>> 16);
    }

    private int indexFor(int hash, int length) {
        return (length - 1) & hash;
    }

    private Entry getEntry(Long key) {
        int index = indexFor(hash(key), table.length);
        Entry entry = table[index];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
            entry = entry.next;
        }
        return null;
    }

    private void resize(int newCapacity) {

//        System.out.println("Resize from " + table.length + " to " + newCapacity);
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) loadFactor * newCapacity;
        loadFactor = loadFactor < 1.0f ? loadFactor + 0.05f : loadFactor;
    }

    private void transfer(Entry[] newTable) {
        Entry entry, nextEntry;
        for (int i = 0; i < table.length; i++) {
            entry = table[i];
            while (entry != null) {
                int index = indexFor(hash(entry.getKey()), newTable.length);
                nextEntry = entry.next;
                entry.next = newTable[index];
                newTable[index] = entry;
                entry = nextEntry;
            }
        }
    }

    private void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }
        createEntry(hash, key, value, bucketIndex);
    }

    private void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = new Entry(hash, key, value, table[bucketIndex]);
        table[bucketIndex] = entry;
        size++;
    }
}
