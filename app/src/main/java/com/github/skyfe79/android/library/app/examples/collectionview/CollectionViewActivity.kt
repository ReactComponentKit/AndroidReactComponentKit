package com.github.skyfe79.android.library.app.examples.collectionview

import android.annotation.SuppressLint
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.*
import com.github.skyfe79.android.library.app.R
import com.github.skyfe79.android.library.app.examples.emojicollection.components.EmojiViewComponent
import com.github.skyfe79.android.library.app.examples.emojicollection.models.EmojiBoxModel
import com.github.skyfe79.android.library.app.examples.emojicollection.util.EmojiHelper
import com.github.skyfe79.android.library.app.examples.recyclerview.component.SectionViewComponent
import com.github.skyfe79.android.library.app.examples.recyclerview.model.TextMessage
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.DefaultSectionModel
import com.github.skyfe79.android.reactcomponentkit.collectionview.CollectionViewAdapter
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.recyclerview.sticky.StickyHeadersLinearLayoutManager
import kotlinx.android.synthetic.main.activity_collection_view.*

class CollectionViewActivity : AppCompatActivity() {

    private lateinit var adapter: CollectionViewAdapter

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_view)

        val windowSize = Point()
        windowManager.defaultDisplay.getSize(windowSize)

        val layout = StickyHeadersLinearLayoutManager<CollectionViewAdapter>(this)
        layout.setExtraLayoutSpace(windowSize.y * 2)
        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)

        adapter = CollectionViewAdapter(Token.empty)
        adapter.register(SectionViewComponent::class)
        adapter.register(EmojiViewComponent::class)

        recyclerView.adapter = adapter

        val sectionModels = listOf(
            DefaultSectionModel(
                items = listOf(
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji)
                ),
                header = TextMessage("Section #1", bgColor = 0xffd01774.toInt(), isSticky = true),
                footer = TextMessage("Section #1 Footer", bgColor = 0xffd01774.toInt(), isSticky = true)
            ) { recyclerView ->
                recyclerView.layoutManager = GridLayoutManager(applicationContext, 8)
            },
            DefaultSectionModel(
                items = listOf(
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji)
                ),
                header = TextMessage("Section #2", bgColor = 0xfff7f93c.toInt()),
                footer = TextMessage("Section #2 Footer", bgColor = 0xfff7f93c.toInt())
            ) { recyclerView ->
                recyclerView.layoutManager = GridLayoutManager(applicationContext, 4)
            },
            DefaultSectionModel(
                items = listOf(
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji)
                ),
                header = TextMessage("Section #3", bgColor = 0xfff07777.toInt(), isSticky = true),
                footer = TextMessage("Section #3 Footer", bgColor = 0xfff07777.toInt())
            ) { recyclerView ->
                recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            },
            DefaultSectionModel(
                items = listOf(
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji)
                ),
                header = TextMessage("Section #4", bgColor = 0xfffcce62.toInt(), isSticky = true),
                footer = TextMessage("Section #4 Footer", bgColor = 0xfffcce62.toInt())
            ) { recyclerView ->
                recyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
                recyclerView.setHasFixedSize(true)
            },
            DefaultSectionModel(
                items = listOf(
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji),
                    EmojiBoxModel(EmojiHelper.emoji)
                ),
                header = TextMessage("Section #4", bgColor = 0xff58c8d8.toInt(), isSticky = true),
                footer = TextMessage("Section #4 Footer", bgColor = 0xff58c8d8.toInt())
            ) { recyclerView ->
                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.setHasFixedSize(true)
            }
        )

        adapter.set(sectionModels)
    }
}
