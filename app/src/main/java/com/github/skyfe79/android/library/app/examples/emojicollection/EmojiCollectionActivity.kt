package com.github.skyfe79.android.library.app.examples.emojicollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.github.skyfe79.android.library.app.R
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.AddEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.RemoveEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.actions.ShuffleEmojiAction
import com.github.skyfe79.android.library.app.examples.emojicollection.components.EmojiViewComponent
import com.github.skyfe79.android.library.app.examples.emojicollection.util.EmojiHelper
import com.github.skyfe79.android.reactcomponentkit.recyclerview.RecyclerViewAdapter
import com.github.skyfe79.android.reactcomponentkit.recyclerview.layout.DiffGridLayoutManager
import com.github.skyfe79.android.reactcomponentkit.rx.AutoDisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.disposedBy
import kotlinx.android.synthetic.main.activity_emoji_collection.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class EmojiCollectionActivity : AppCompatActivity() {

    private lateinit var viewModel: EmojiCollectionViewModel
    private val disposeBag: AutoDisposeBag by lazy {
        AutoDisposeBag(this)
    }

    private val gridLayoutManager: DiffGridLayoutManager by lazy {
        val layout = DiffGridLayoutManager(this, 7)
        layout
    }

    private val adapter: RecyclerViewAdapter by lazy {
        RecyclerViewAdapter(viewModel.token, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emoji_collection)

        viewModel = ViewModelProviders.of(this).get(EmojiCollectionViewModel::class.java)

        adapter.register(EmojiViewComponent::class)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter

        handleClickButtonEvents()
        handleViewModelOutputs()
    }

    private fun handleClickButtonEvents() {
        addEmojiButton.onClick {
            viewModel.dispatch(AddEmojiAction(EmojiHelper.emoji))
        }

        removeEmojiButton.onClick {
            viewModel.dispatch(RemoveEmojiAction)
        }

        shuffleEmojiButton.onClick {
            viewModel.dispatch(ShuffleEmojiAction)
        }
    }

    private fun handleViewModelOutputs() {
        viewModel
            .itemModels
            .asObservable()
            .subscribe {
                adapter.set(it)
            }
            .disposedBy(disposeBag)
    }
}
