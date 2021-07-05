package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever {

    OriginalRetriever retriever;
    LRUCache<Long, Object> cache;

    public CachingProxyRetriever(Storage storage) {
        retriever = new OriginalRetriever(storage);
        cache = new LRUCache<>(10000);
    }

    @Override
    public Object retrieve(long id) {
        Object result = cache.find(id);
        if (result != null) return result;
        result = retriever.retrieve(id);
        cache.set(id, result);
        return result;
    }
}
