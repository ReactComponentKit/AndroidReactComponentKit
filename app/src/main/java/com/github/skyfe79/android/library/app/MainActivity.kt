package com.github.skyfe79.android.library.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.skyfe79.android.reactcomponentkit.Action

data class MyAction(val name: String): Action

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action: Action = MyAction(name = "Burt.K")
        val act = action as? MyAction
        println(act?.name)
    }
}
