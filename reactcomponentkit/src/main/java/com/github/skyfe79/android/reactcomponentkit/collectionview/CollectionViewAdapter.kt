package com.github.skyfe79.android.reactcomponentkit.collectionview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.RecyclerViewHelper
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.SectionModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.recyclerview.CollectionViewCellViewHolder
import com.github.skyfe79.android.reactcomponentkit.recyclerview.RecyclerViewCell
import com.github.skyfe79.android.reactcomponentkit.recyclerview.RecyclerViewCellViewHolder
import com.github.skyfe79.android.reactcomponentkit.recyclerview.sticky.StickyHeaders
import com.github.skyfe79.android.reactcomponentkit.redux.RCKViewModel
import com.github.skyfe79.android.reactcomponentkit.redux.State
import java.lang.ref.WeakReference
import kotlin.reflect.KClass


private data class SectionHeader(val itemModel: ItemModel): ItemModel() {
    override val id: Int = itemModel.id
    override val componentClass: KClass<*> = itemModel.componentClass
    override val isSticky: Boolean = itemModel.isSticky
}

private data class SectionFooter(val itemModel: ItemModel): ItemModel() {
    override val id: Int = itemModel.id
    override val componentClass: KClass<*> = itemModel.componentClass
    override val isSticky: Boolean = itemModel.isSticky
}

data class SectionContent(
    val itemModels: List<ItemModel>,
    val recyclerViewHelper: RecyclerViewHelper): ItemModel() {
    override val componentClass: KClass<*> = NestedCollectionViewComponent::class
    override val id: Int = itemModels.hashCode()
}


private fun SectionModel.toItemModels(): List<ItemModel> {
    val result: MutableList<ItemModel> = mutableListOf()
    header?.let {
        result.add(SectionHeader(it))
    }

    result.add(SectionContent(items, recyclerViewHelper))

    footer?.let {
        result.add(SectionFooter(it))
    }

    return result
}

open class CollectionViewAdapter(private val token: Token): RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaders {

    enum class ViewType {
        HEADER,
        CONTENT,
        FOOTER
    }

    private val viewComponents: MutableMap<Int, KClass<*>> = mutableMapOf()
    private var sections: MutableList<SectionModel> = mutableListOf()
    private var items: MutableList<ItemModel> = mutableListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun isStickyHeader(position: Int): Boolean {
        return when(val item = items[position]) {
            is SectionHeader -> {
                item.itemModel.isSticky
            }

            is SectionFooter -> {
                item.itemModel.isSticky
            }

            else -> {
                false
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when(val item = items[position]) {
            is SectionContent -> {
                val cell = RecyclerViewCell(token)
                val viewCompoent = NestedCollectionViewComponent(token)
                cell.viewComponent = viewCompoent
                val viewHolder = cell.onCreateCollectionViewHolder(parent)
                viewCompoent.setup(item, viewComponents)
                return viewHolder
            }

            else -> {
                val cls = viewComponents[item.componentClass.qualifiedName.hashCode()] ?: throw IllegalStateException("viewComponent is null")
                val cell = RecyclerViewCell(token)
                cell.viewComponent = cls.java.constructors.first().newInstance(token) as ViewComponent
                cell.onCreateViewHolder(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = items[position]) {
            is SectionHeader -> {
                val cellViewHolder = (holder as? RecyclerViewCellViewHolder) ?: return
                cellViewHolder.onBind(item.itemModel, position)
            }
            is SectionFooter -> {
                val cellViewHolder = (holder as? RecyclerViewCellViewHolder) ?: return
                cellViewHolder.onBind(item.itemModel, position)
            }
            is SectionContent -> {
                val cellViewHolder = (holder as? CollectionViewCellViewHolder) ?: return
                cellViewHolder.onBind(item, position)
            }
        }
    }

    open fun set(sections: List<SectionModel>) {
        this.sections.clear()
        this.sections.addAll(sections)

        val newItems = this.sections.map {
            it.toItemModels()
        }.flatten()

        items.clear()
        items.addAll(newItems)
        this.notifyDataSetChanged()
    }

    fun register(component: KClass<*>) {
        viewComponents[component.qualifiedName.hashCode()] = component
    }
}