package com.github.skyfe79.android.library.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.skyfe79.android.reactcomponentkit.Action
import com.github.skyfe79.android.reactcomponentkit.EventBus
import com.github.skyfe79.android.reactcomponentkit.EventType

data class MyAction(val name: String): Action

enum class MyEvent: EventType {
    TEST,
    HELLO
}

class MainActivity : AppCompatActivity() {

    val eventBus = EventBus<MyEvent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action: Action = MyAction(name = "Burt.K")
        val act = action as? MyAction
        println(act?.name)

        eventBus.start()

        eventBus.post(MyEvent.TEST)
        eventBus.post(MyEvent.HELLO)
    }
}
