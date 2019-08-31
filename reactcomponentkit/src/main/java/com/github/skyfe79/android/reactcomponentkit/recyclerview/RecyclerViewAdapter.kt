package com.github.skyfe79.android.reactcomponentkit.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.viewmodel.Token
import com.github.skyfe79.android.reactcomponentkit.recyclerview.sticky.StickyHeaders
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.IllegalStateException
import kotlin.reflect.KClass

open class RecyclerViewAdapter(private val token: Token, private val useDiff: Boolean = false): RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaders {

    private var items: MutableList<ItemModel> = mutableListOf()
    private val viewComponents: MutableMap<Int, KClass<*>> = mutableMapOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun isStickyHeader(position: Int): Boolean {
        return items[position].isSticky
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return item.componentClass.qualifiedName.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cls = viewComponents[viewType] ?: throw IllegalStateException("viewComponent is null")
        val cell = RecyclerViewCell(token)
        cell.viewComponent = cls.java.constructors.first().newInstance(token) as ViewComponent
        return cell.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cellViewHolder = (holder as? RecyclerViewCellViewHolder) ?: return
        val item = items[position]
        cellViewHolder.onBind(item, position)
    }

    internal fun update(viewComponents: MutableMap<Int, KClass<*>>) {
        this.viewComponents.clear()
        this.viewComponents.putAll(viewComponents)
    }

    fun register(component: KClass<*>) {
        viewComponents[component.qualifiedName.hashCode()] = component
    }

    open fun set(newItems: List<ItemModel>) {
        if (!useDiff) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        } else {
            doAsync {
                weakRef.get()?.let {
                    val diffCallback = ItemModelDiffCallback(it.items, newItems)
                    val diff = DiffUtil.calculateDiff(diffCallback)
                    uiThread {
                        weakRef.get()?.let { adapter ->
                            adapter.items.clear()
                            adapter.items.addAll(newItems)
                            diff.dispatchUpdatesTo(adapter)
                        }
                    }
                }
            }
        }
    }
}

internal class ItemModelDiffCallback(private val current: List<ItemModel>, private val update: List<ItemModel>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return current.size
    }

    override fun getNewListSize(): Int {
        return update.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return current[oldItemPosition].id == update[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return current[oldItemPosition] == update[newItemPosition]
    }
}