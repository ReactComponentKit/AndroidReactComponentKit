package com.github.skyfe79.android.library.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventBus
import com.github.skyfe79.android.reactcomponentkit.eventbus.EventType
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.redux.*
import io.reactivex.Observable
import kotlin.math.log

class SomeClass {

}

data class MyAction(val name: String): Action

sealed class MyEvent: EventType {
    class TEST: MyEvent()
}

data class MyState(
    var a: String? = null,
    var b: String? = null,
    var c: Int = 0,
    var d: Int = 0): State()

fun m1(state: State, action: Action): Observable<State> {
    val myState = (state as? MyState)?.let { it } ?: return Observable.just(state)

    val mutabledState = myState.copy(a = "mutabled a")
    return Observable.just(mutabledState)
}

fun m2(state: State, action: Action): Observable<State> {
    val myState = (state as? MyState)?.let { it } ?: return Observable.just(state)

    return Observable.just(myState.copy(b = "mutabled b"))
}

fun MainActivity.r1(state: State, action: Action): Observable<State> {
    val myState = (state as? MyState)?.let { it } ?: return Observable.just(state)

    return Observable.just(myState.copy(c = 10))
}

fun r2(state: State, action: Action): Observable<State> {
    val myState = (state as? MyState)?.let { it } ?: return Observable.just(state)

    return Observable.just(myState.copy(d = 200))
}

fun p1(state: State, action: Action): Observable<State> {
    println("state = $state, action = $action")
    return Observable.just(state)
}

class MainActivity : AppCompatActivity() {

    val token = Token()
    val eventBus = EventBus<MyEvent>(token)
    val eventBusReceiver = EventBus<MyEvent>(token)
    val eventBusReceiver2 = EventBus<MyEvent>(Token())
    val store = Store<MyState>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store.set(
            initialState =  MyState("Burt.K"),
            middlewares = arrayOf(::m1, ::m2),
            reducers = arrayOf(this::r1, ::r2),
            postwares = arrayOf(::p1)
        )
    }

    override fun onStop() {
        super.onStop()

        eventBus.stop()
        eventBusReceiver.stop()
        eventBusReceiver2.stop()
    }

    fun clickedButton(sender: View) {
        store.dispatch(MyAction("TEST"))
            .toObservable()
            .subscribe {

            }
            .dispose()
    }

    fun testEventBus() {
        val action: Action = MyAction(name = "Burt.K")
        val act = action as? MyAction
        println(act?.name)

        println(eventBus.isAlive)

        eventBusReceiver.on { e ->
            when (e) {
                is MyEvent.TEST -> println("[1] MyEvent.TEST")
            }
        }

        eventBusReceiver2.on { e ->
            when (e) {
                is MyEvent.TEST -> println("[2] MyEvent.TEST")
            }
        }

        eventBus.post(MyEvent.TEST())
    }

    fun testQueue() {
        val queue = Queue<String>()

        queue.enqueue("Hello")
        queue.enqueue("Name")

        println("${queue.isEmpty}")
        println("${queue.count}")
        println("${queue.dequeue()}")
        println("${queue.dequeue()}")
        println("${queue.dequeue()}")
    }
}
