package com.github.skyfe79.android.library.app.examples.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.skyfe79.android.library.app.R
import com.github.skyfe79.android.library.app.examples.recyclerview.component.SectionViewComponent
import com.github.skyfe79.android.library.app.examples.recyclerview.component.TextMessageViewComponent
import com.github.skyfe79.android.library.app.examples.recyclerview.model.TextMessage
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import com.github.skyfe79.android.reactcomponentkit.recyclerview.RecyclerViewAdapter
import com.github.skyfe79.android.reactcomponentkit.recyclerview.sticky.StickyHeadersLinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        this.recyclerView.layoutManager = StickyHeadersLinearLayoutManager<RecyclerViewAdapter>(this)
        this.recyclerView.setHasFixedSize(true)

        adapter = RecyclerViewAdapter(token = Token.empty, useDiff = true)
        adapter.register(TextMessageViewComponent::class)
        adapter.register(SectionViewComponent::class)
        recyclerView.adapter = adapter

        adapter.set(listOf(
            TextMessage("Hello, Wordl1", isSticky = true),
            TextMessage("Hello, World2"),
            TextMessage("Hello, World3"),
            TextMessage("Hello, World4"),
            TextMessage("Hello, World5"),

            TextMessage("Hello, World6", isSticky = true),
            TextMessage("Hello, World7"),
            TextMessage("Hello, World8"),
            TextMessage("Hello, World9"),
            TextMessage("Hello, World10"),

            TextMessage("Hello, World11"),
            TextMessage("Hello, World12"),
            TextMessage("Hello, World13"),
            TextMessage("Hello, World14"),
            TextMessage("Hello, World15"),

            TextMessage("Hello, World16"),
            TextMessage("Hello, World17"),
            TextMessage("Hello, World18"),
            TextMessage("Hello, World19"),
            TextMessage("Hello, World20")
        ))
    }
}