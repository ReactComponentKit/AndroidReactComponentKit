package com.github.skyfe79.android.library.app


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.skyfe79.android.library.app.action.ClickCounterExampleButtonAction
import com.github.skyfe79.android.library.app.action.ResetRouteAction
import com.github.skyfe79.android.library.app.examples.counter.CounterActivity
import com.github.skyfe79.android.library.app.examples.counter.CounterActivity2
import com.github.skyfe79.android.reactcomponentkit.rx.AutoDisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.disposedBy
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val layout: MainViewLayout by lazy {
        MainViewLayout(viewModel.token)
    }
    private val disposeBag = AutoDisposeBag(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        layout.setContentView(this)

        handleViewModelOutput()
    }

    private fun handleViewModelOutput() {
        viewModel
            .route
            .asObservable()
            .subscribe {
                handleRoute(it)
            }
            .disposedBy(disposeBag)
    }

    override fun onPause() {
        super.onPause()
        viewModel.dispatch(ResetRouteAction)
    }

    private fun handleRoute(route: MainRoute) = when (route) {
        MainRoute.CounterExample -> startActivity<CounterActivity>()
        MainRoute.CounterExample2 -> startActivity<CounterActivity2>()
        MainRoute.None -> Unit
    }
}
