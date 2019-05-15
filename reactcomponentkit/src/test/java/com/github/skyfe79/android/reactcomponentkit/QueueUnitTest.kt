package com.github.skyfe79.android.reactcomponentkit

import com.github.skyfe79.android.reactcomponentkit.redux.Queue
import org.junit.Test

import org.junit.Assert.*

class QueueUnitTest {
    @Test
    fun testQueue_whenInit_countIsZero() {
        val queue = Queue<Int>()
        assertEquals(0, queue.count)
    }

    @Test
    fun testQueue_whenInit_isEmpty() {
        val queue = Queue<Int>()
        assertEquals(true, queue.isEmpty)
    }

    @Test
    fun testQueue_whenEnqueue_countIsOne() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        assertEquals(false, queue.isEmpty)
        assertEquals(1, queue.count)
    }

    @Test
    fun testQueue_whenEnqueueTwoItems_countIsTwo() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        assertEquals(false, queue.isEmpty)
        assertEquals(2, queue.count)
    }

    @Test
    fun testQueue_whenEnqueue_1_2_AndDeque_is_1() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        val item = queue.dequeue()
        assertEquals(1, item)
        assertEquals(false, queue.isEmpty)
        assertEquals(1, queue.count)
    }

    @Test
    fun testQueue_whenEnqueue_1_2_AndDequeDeque_is_2() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.dequeue()
        val item = queue.dequeue()
        assertEquals(2, item)
    }

    @Test
    fun testQueue_whenEnqueue_1_2_AndDequeDeque_countIsZero() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.dequeue()
        queue.dequeue()
        assertEquals(true, queue.isEmpty)
        assertEquals(0, queue.count)
    }

    @Test
    fun testQueue_whenEnqueue_1_2_AndDequeDequeDeque_is_null() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.dequeue()
        queue.dequeue()
        val item = queue.dequeue()
        assertEquals(null, item)
    }

    @Test
    fun testQueue_whenEnqueue_1_2_AndDequeDequeDeque_countIsZero() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.dequeue()
        queue.dequeue()
        queue.dequeue()
        assertEquals(0, queue.count)
        assertEquals(true, queue.isEmpty)
    }

    @Test
    fun testQueue_whenEnqueue_1_2_AndClear_countIsZero() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.clear()
        assertEquals(0, queue.count)
        assertEquals(true, queue.isEmpty)
    }

    @Test
    fun testQueue_whenEnqueue_1_2_AndClear_dequeItemIsZero() {
        val queue = Queue<Int>()
        queue.enqueue(1)
        queue.enqueue(2)
        queue.clear()
        val item = queue.dequeue()
        assertEquals(null, item)
    }

}