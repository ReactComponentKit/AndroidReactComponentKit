package com.github.skyfe79.android.library.app.examples.counter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView

class CounterActivity: AppCompatActivity() {
    private lateinit var viewModel: CounterViewModel
    private val layout: CounterLayout by lazy {
        CounterLayout(viewModel.token, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CounterViewModel::class.java)
        layout.setContentView(this)
        layout.countTextView.text = "${viewModel.count.value}"
    }
}