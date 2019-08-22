package com.github.skyfe79.android.reactcomponentkit.redux

import java.util.concurrent.ConcurrentLinkedQueue

class Queue<T> {
    private var items = ConcurrentLinkedQueue<T>()

    val count: Int
        get() = items.count()

    val isEmpty: Boolean
        get() = items.isEmpty()

    val isNotEmpty: Boolean
        get() = items.isNotEmpty()

    fun enqueue(item: T) {
        items.add(item)
    }

    fun dequeue(): T? {
        return items.poll()
    }

    fun clear() = items.clear()

    fun peek(): T? {
        return items.peek()
    }
}