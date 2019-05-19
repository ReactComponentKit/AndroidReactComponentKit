package com.github.skyfe79.android.library.app


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import org.jetbrains.anko.*

class MainActivity : FragmentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var layout: MainViewLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        layout = MainViewLayout(viewModel)
        layout.bindTo(this)
        layout.setContentView(this)
        layout.setupViewModelOutputs()
    }
}
