package com.github.skyfe79.android.reactcomponentkit.collectionview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.R
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.recyclerview.RecyclerViewAdapter
import org.jetbrains.anko.*
import kotlin.reflect.KClass

class NestedCollectionViewComponent(
    override var token: Token
): ViewComponent(token) {
    internal lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var currentContent: SectionContent

    internal fun setup(section: SectionContent, viewComponents: MutableMap<Int, KClass<*>>) {
        currentContent = section
        adapter = RecyclerViewAdapter(token)
        adapter.update(viewComponents)
        recyclerView.adapter = adapter
        recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool())
        recyclerView.isNestedScrollingEnabled = false
        section.recyclerViewHelper(recyclerView)
        adapter.set(section.itemModels)
    }

    override fun layout(ui: AnkoContext<Context>): View {
        val view = ui.include<View>(R.layout.rck_nested_collection_view_component)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    override fun on(content: SectionContent,  position: Int) {
        if (content.id != currentContent.id) {
            currentContent = content
            adapter.set(content.itemModels)
        }
    }
}