package com.github.skyfe79.android.reactcomponentkit.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import kotlin.reflect.KClass

open class RecyclerViewAdapter(private val token: Token): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<ItemModel> = mutableListOf()
    private val viewHolderFactory: MutableMap<Int, KClass<*>> = mutableMapOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return item.componentClass.qualifiedName.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cls = viewHolderFactory[viewType]
        val cell = RecyclerViewCell(token = token, receiveState = false)
        cls?.let {
            cell.viewComponent = it.java.constructors.first().newInstance(token, false) as ViewComponent
        }
        return cell.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cellViewHolder = (holder as? RecyclerViewCellViewHolder) ?: return

        val item = items[position]
        cellViewHolder.onBind(item, position)
    }

    fun register(component: KClass<*>) {
        viewHolderFactory[component.qualifiedName.hashCode()] = component
    }

    fun set(items: Array<ItemModel>) {
        this.items = items.toMutableList()
        this.notifyDataSetChanged()
    }
}