package com.github.skyfe79.android.library.app.examples.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.skyfe79.android.reactcomponentkit.collectionmodels.ItemModel
import com.github.skyfe79.android.reactcomponentkit.eventbus.Token
import kotlin.reflect.KClass

class RecyclerAdapter(private val token: Token): RecyclerView.Adapter<ViewHolder>() {

    private var items: MutableList<ItemModel> = mutableListOf(
        TextMessage("안녕하세요1"),
        TextMessage("안녕하세요2"),
        TextMessage("안녕하세요3"),
        TextMessage("안녕하세요4"),
        TextMessage("안녕하세요5"),
        TextMessage("안녕하세요6"),
        TextMessage("안녕하세요7"),
        TextMessage("안녕하세요8"),
        TextMessage("안녕하세요9"),
        TextMessage("안녕하세요10"),
        TextMessage("안녕하세요11"),
        TextMessage("안녕하세요12"),
        TextMessage("안녕하세요13"),
        TextMessage("안녕하세요14"),
        TextMessage("안녕하세요15"),
        TextMessage("안녕하세요16"),
        TextMessage("안녕하세요17"),
        TextMessage("안녕하세요18"),
        TextMessage("안녕하세요19"))
    private val viewHolderFactory: MutableMap<Int, KClass<*>> = mutableMapOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return item.componentClass.qualifiedName.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cls = viewHolderFactory[viewType]
        val instance = cls!!.java.newInstance() as RecyclerViewCell<*>
        instance.set(token = token, receiveState = false)
        return instance.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cellViewHolder = (holder as? CellViewHolder) ?: return

        val item = items[position]
        cellViewHolder.onBind(item, position)
    }

    fun registerCell(cls: KClass<*>) {
        viewHolderFactory[cls.qualifiedName.hashCode()] = cls
    }
}