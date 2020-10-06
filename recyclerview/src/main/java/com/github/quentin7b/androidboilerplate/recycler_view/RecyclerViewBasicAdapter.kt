package com.github.quentin7b.androidboilerplate.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewBasicAdapter<T> : RecyclerView.Adapter<RecyclerViewBasicAdapter.ViewHolder<T>> {

    private var layoutRes: Int
    private val items = mutableListOf<T>()
    private var binder: (T, View) -> Unit

    constructor(@LayoutRes layoutRes: Int, binder: (T, View) -> Unit) : this(listOf(), layoutRes, binder)
    constructor(items: List<T>, @LayoutRes layoutRes: Int, binder: (T, View) -> Unit) : super() {
        this.items.addAll(items)
        this.binder = binder
        this.layoutRes = layoutRes
    }

    fun addItem(item: T, index: Int?) {
        if (index !== null) {
            items.add(index, item)
            notifyItemInserted(index)
        } else {
            items.add(item)
            notifyItemInserted(items.size)
        }
    }

    fun removeItem(item: T) {
        val index = items.indexOf(item)
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutRes, parent, false),
            binder
        )

    override fun getItemCount(): Int = this.items.size

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) =
        binder.invoke(items[position], holder.itemView)

    data class ViewHolder<T>(private val itemView: View, private val binder: (T, View) -> Unit) :
        RecyclerView.ViewHolder(itemView)

}