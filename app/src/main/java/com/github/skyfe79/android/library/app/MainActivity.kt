package com.github.skyfe79.android.library.app


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import org.jetbrains.anko.*

class MainActivity : FragmentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var layout: MainViewLayout2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

//        MainViewLayout2(viewModel).setContentView(this)

        layout = MainViewLayout2(viewModel)
        layout.bindTo(this)
        layout.setContentView(this)
        layout.setupViewModelOutputs()
    }
}
