package com.javarush.task.task33.task3310.strategy;

public class FileStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;

    int size;
    long maxBucketSize;

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
        if (maxBucketSize > bucketSizeLimit) {
            resize(table.length * 2);
        }

    }

    @Override
    public Long getKey(String value) {
        Entry entry;
        FileBucket bucket;
        for (int i = 0; i < table.length; i++) {
            bucket = table[i];
            entry = (bucket == null) ? null : bucket.getEntry();
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
        FileBucket bucket = table[index];
        Entry entry = (bucket == null) ? null : bucket.getEntry();
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
            entry = entry.next;
        }
        return null;
    }

    private void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        for (FileBucket bucket : table) {
            bucket.remove();
        }
        table = newTable;
//        threshold = (int) loadFactor * newCapacity;
//        loadFactor = loadFactor < 1.0f ? loadFactor + 0.05f : loadFactor;
    }

    private void transfer(FileBucket[] newTable) {
        Entry entry, nextEntry;
        FileBucket bucket;
        maxBucketSize = 0L;
        for (int i = 0; i < table.length; i++) {
            bucket = table[i];
            entry = (bucket == null) ? null : bucket.getEntry();
            while (entry != null) {
                int index = indexFor(hash(entry.getKey()), newTable.length);
                nextEntry = entry.next;
                entry.next = newTable[index].getEntry();
                newTable[index].putEntry(entry);
                maxBucketSize = Math.max(maxBucketSize, newTable[index].getFileSize());
                entry = nextEntry;
            }
        }
    }

    private void addEntry(int hash, Long key, String value, int bucketIndex) {
        FileBucket bucket = table[bucketIndex];
        Entry entry = (bucket == null) ? null : bucket.getEntry();
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                entry.value = value;
                table[bucketIndex].putEntry(entry);
                return;
            }
            entry = entry.next;
        }
        createEntry(hash, key, value, bucketIndex);
    }


    private void createEntry(int hash, Long key, String value, int bucketIndex) {
        if (table[bucketIndex] == null) table[bucketIndex] = new FileBucket();
        Entry entry = new Entry(hash, key, value, table[bucketIndex].getEntry());
        table[bucketIndex].putEntry(entry);
        long bucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = Math.max(maxBucketSize, bucketSize);
        size++;
    }

}
