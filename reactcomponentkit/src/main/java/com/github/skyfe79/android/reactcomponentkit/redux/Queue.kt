package com.github.skyfe79.android.reactcomponentkit.redux

import java.util.concurrent.ConcurrentLinkedQueue

class Queue<T> {
    private var items = ConcurrentLinkedQueue<T>()

    val count: Int
        get() = items.count()

    val isEmpty: Boolean
        get() = items.isEmpty()

    fun enqueue(item: T) {
        items.add(item)
    }

    fun dequeue(): T? {
        return items.poll()
    }

    fun clear() = items.clear()
}