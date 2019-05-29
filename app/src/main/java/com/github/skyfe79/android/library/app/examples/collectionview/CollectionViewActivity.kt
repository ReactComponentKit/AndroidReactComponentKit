package com.github.skyfe79.android.library.app.examples.collectionview

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.github.skyfe79.android.library.app.R
import com.github.skyfe79.android.library.app.examples.collectionview.action.LoadAction
import com.github.skyfe79.android.library.app.examples.emojicollection.components.EmojiViewComponent
import com.github.skyfe79.android.library.app.examples.recyclerview.component.SectionViewComponent
import com.github.skyfe79.android.reactcomponentkit.collectionview.CollectionViewAdapter
import com.github.skyfe79.android.reactcomponentkit.recyclerview.sticky.StickyHeadersLinearLayoutManager
import com.github.skyfe79.android.reactcomponentkit.rx.AutoDisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.disposedBy
import kotlinx.android.synthetic.main.activity_collection_view.*

class CollectionViewActivity : AppCompatActivity() {

    private lateinit var viewModel: CollectionViewModel

    private val disposeBag: AutoDisposeBag by lazy {
        AutoDisposeBag(this)
    }

    private val adapter: CollectionViewAdapter by lazy {
        val adapter = CollectionViewAdapter(viewModel.token)
        adapter.register(SectionViewComponent::class)
        adapter.register(EmojiViewComponent::class)
        adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_view)

        viewModel = ViewModelProviders.of(this).get(CollectionViewModel::class.java)

        val windowSize = Point()
        windowManager.defaultDisplay.getSize(windowSize)

        val layout = StickyHeadersLinearLayoutManager<CollectionViewAdapter>(this)
        layout.setExtraLayoutSpace(windowSize.y * 2)
        recyclerView.layoutManager = layout
        recyclerView.adapter = adapter

        handleViewModelOutpus()

        viewModel.dispatch(LoadAction)
    }

    private fun handleViewModelOutpus() {
        viewModel
            .sections
            .asObservable()
            .subscribe {
                adapter.set(it)
            }
            .disposedBy(disposeBag)
    }
}
