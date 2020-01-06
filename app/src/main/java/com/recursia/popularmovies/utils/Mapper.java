package com.recursia.popularmovies.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

abstract public class Mapper<T, V> {

    public final List<V> transform(List<T> list) {
        List<V> collection;
        if (isValid(list)) {
            collection = new ArrayList<>();
            for (T value : list) {
                collection.add(transform(value));
            }
        } else {
            collection = Collections.emptyList();
        }
        return collection;
    }

    private boolean isValid(Collection collection) {
        return (collection != null) && !(collection.isEmpty());
    }

    abstract public V transform(T t);

}
