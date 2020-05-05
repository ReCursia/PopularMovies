package com.recursia.popularmovies.utils

import java.util.*
import java.util.Collections.emptyList

abstract class Mapper<T, V> {

    fun transform(list: List<T>?): List<V> {
        val collection: MutableList<V>
        if (isValid(list)) {
            collection = ArrayList()
            for (value in list!!) {
                collection.add(transform(value))
            }
        } else {
            collection = emptyList()
        }
        return collection
    }

    private fun isValid(collection: Collection<*>?): Boolean {
        return collection != null && !collection.isEmpty()
    }

    abstract fun transform(t: T): V
}
