package com.github.skyfe79.android.reactcomponentkit.recyclerview

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.component.ViewComponent
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import com.github.skyfe79.android.reactcomponentkit.recyclerview.sticky.StickyHeaders
import com.github.skyfe79.android.reactcomponentkit.rx.DisposeBag
import com.github.skyfe79.android.reactcomponentkit.rx.disposedBy
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

open class RecyclerViewAdapter(private val token: Token, private val useDiff: Boolean = false): RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaders {

    private var items: MutableList<ItemModel> = mutableListOf()
    private val viewHolderFactory: MutableMap<Int, KClass<*>> = mutableMapOf()
    private val disposeBag = DisposeBag()
    private val diffJobQ = BehaviorRelay.createDefault(Pair<List<ItemModel>, List<ItemModel>>(listOf(), listOf()))

    init {
        diffJobQ
            .skip(1)
            .observeOn(Schedulers.computation())
            .map {
                val diffCallback = ItemModelDiffCallback(it.first, it.second)
                val diff = DiffUtil.calculateDiff(diffCallback, true)
                this.items.clear()
                this.items.addAll(it.second)
                Pair(diff, it.second)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val diff = it.first
                diff?.dispatchUpdatesTo(this@RecyclerViewAdapter)
            }
            .disposedBy(disposeBag)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun isStickyHeader(position: Int): Boolean {
        return items[position].isSticky
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

    open fun set(newItems: List<ItemModel>) {
        if (!useDiff) {
            this.items.clear()
            this.items.addAll(newItems)
            this.notifyDataSetChanged()
        } else {
            diffJobQ.accept(Pair(items.toList(), newItems))
//            doAsync {
//                weakRef.get()?.let {
//                    val diffCallback = ItemModelDiffCallback(it.items, newItems)
//                    val diff = DiffUtil.calculateDiff(diffCallback)
//
//                    uiThread {
//                        weakRef.get()?.let { adapter ->
//                            adapter.items.clear()
////                            adapter.items.addAll(newItems)
//                            adapter.items = newItems.toMutableList()
//                            diff?.dispatchUpdatesTo(adapter)
//                        }
//                    }
//                }
//            }
        }
    }
}

private class ItemModelDiffCallback(private val current: List<ItemModel>, private val update: List<ItemModel>): DiffUtil.Callback() {

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