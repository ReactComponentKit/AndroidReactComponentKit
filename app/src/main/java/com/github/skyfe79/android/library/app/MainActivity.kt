package com.github.skyfe79.android.library.app


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.github.skyfe79.android.library.app.component.LoadingComponent
import com.github.skyfe79.android.reactcomponentkit.component.*
import org.jetbrains.anko.*

class MainActivity : FragmentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var layout: MainViewLayout2
    private val loadingComponent: LoadingComponent by lazy {
        fragmentComponent<LoadingComponent>(viewModel.token, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoadingComponent()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        layout = MainViewLayout2(viewModel)
        layout.bindTo(this)
        layout.setContentView(this)
        layout.setupViewModelOutputs()

        replaceFragment(loadingComponent, R.id.fragmentContainer)
    }
}
