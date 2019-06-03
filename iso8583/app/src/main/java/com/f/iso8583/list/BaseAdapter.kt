package com.f.iso8583.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class BaseAdapter<T> : RecyclerView.Adapter<BaseHolder>() {

    /**
     * Header list.
     */
    private var mHeaders: MutableList<BaseItem<T>> = mutableListOf()
    /**
     * Item list.
     */
    private var mItems: MutableList<BaseItem<T>> = mutableListOf()
    /**
     * Footer list.
     */
    private var mFooters: MutableList<BaseItem<T>> = mutableListOf()


    override fun getItemCount(): Int = mHeaders.size + mItems.size + mFooters.size

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        val item: BaseItem<T> = getItemAt(position)
        item.onBindView(holder?.itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder =
        BaseHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    /**
     * Return layout resource of item
     */
    override fun getItemViewType(position: Int): Int = when {
        position < mHeaders.size -> mHeaders[position].onInitLayout()
        position >= mHeaders.size + mItems.size -> mFooters[position - (mHeaders.size + mItems.size)].onInitLayout()
        else -> mItems[position - mHeaders.size].onInitLayout()
    }

    /**
     * Get item at position.
     */
    fun getItemAt(position: Int): BaseItem<T> {
        if (position < mHeaders.size) {
            return mHeaders[position]
        }

        if (position < mHeaders.size + mItems.size) {
            return mItems[position - mHeaders.size]
        }

        return mFooters[position - mHeaders.size - mItems.size]
    }

    /**
     * Add item.
     */
    fun addItem(item: BaseItem<T>) {
        mItems.add(item)
        val position = mHeaders.size + mItems.size - 1
        notifyItemInserted(position)
    }

    /**
     * Add footer.
     */
    fun addFooter(item: BaseItem<T>) {
        mFooters.add(item)
        val position = mHeaders.size + mItems.size + mFooters.size - 1
        notifyItemInserted(position)
    }

    /**
     * Add header.
     */
    fun addHeader(item: BaseItem<T>) {
        mHeaders.add(item)
        val position = mHeaders.size - 1
        notifyItemInserted(position)
    }

    /**
     * Remove header at position.
     */
    fun removeHeaderAt(position: Int) {
        mHeaders.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Remove header by passing object.
     */
    fun removeHeader(item: BaseItem<T>) {
        val position = mHeaders.indexOf(item)
        if (position == -1) {
            return
        }
        val result = mHeaders.remove(item)
        if (result) {
            notifyItemRemoved(position)
        }
    }

    /**
     * Remove item at position.
     */
    fun removeItemAt(position: Int) {
        mItems.removeAt(position)
        val pos = mHeaders.size + position
        notifyItemRemoved(pos)
    }

    /**
     * Remove all item
     */
    fun removeAllItem() {
        val size = mItems.size
        mItems.clear()
        notifyDataSetChanged()
    }

    /**
     * Remove from position
     */
    fun removeFromItem(pos: Int) {
        val size = mItems.size

        while (pos < mItems.size) {
            mItems.removeAt(mItems.size - 1)
        }

        notifyItemRangeRemoved(mHeaders.size + pos, size)
    }

    /**
     * Remove all
     */
    fun removeAll() {
        val size = mHeaders.size + mItems.size + mFooters.size
        mHeaders.clear()
        mItems.clear()
        mFooters.clear()
        notifyItemRangeRemoved(0, size)
    }

    /**
     * Remove item by passing object.
     */
    fun removeItem(item: BaseItem<T>) {
        val position = mItems.indexOf(item)
        if (position == -1) {
            return
        }
        val result = mItems.remove(item)
        if (result) {
            val pos = mHeaders.size + position
            notifyItemRemoved(pos)
        }
    }

    /**
     * Remove footer at position.
     */
    fun removeFooterAt(position: Int) {
        mFooters.removeAt(position)
        val pos = mHeaders.size + mItems.size + position
        notifyItemRemoved(pos)
    }

    /**
     * Remove footer by passing object.
     */
    fun removeFooter(item: BaseItem<T>) {
        val position = mFooters.indexOf(item)
        if (position == -1) {
            return
        }
        val result = mFooters.remove(item)
        if (result) {
            val pos = mHeaders.size + mItems.size + position
            notifyItemRemoved(pos)
        }
    }

    fun removeAllFooter() {
        val size = mFooters.size
        mFooters.clear()
        val pos = mHeaders.size + mItems.size
        notifyItemRemoved(pos)
        notifyItemRangeRemoved(pos, pos + size)
    }

    /**
     * Item size.
     */
    fun getItemSize(): Int {
        return mItems.size
    }


    /**
     * Get position
     */
    fun getPosition(item: BaseItem<T>): Int {
        val position = mItems.indexOf(item)
        if (position == -1) {
            return -1
        }
        return mHeaders.size + position + mFooters.size
    }

    /**
     * Get position
     */
    fun getPosition(data: T): Int {
        var find = -1
        for ((index, value) in mItems.withIndex()) {
            if (value?.mData?.equals(data)!!) {
                find = index
                break
            }
        }

        if (find == -1) {
            return -1
        }

        return mHeaders.size + find + mFooters.size
    }

    /**
     * Get items.
     */
    fun getItems(): MutableList<BaseItem<T>> {
        return this.mItems
    }

    /**
     * Notify item
     */
    fun notifyItem(data: T) {
        var position: Int = 0
        for ((index, value) in mItems.withIndex()) {
            if (value.mData == data) {
                position = index
            }
        }

        position += mHeaders.size
        notifyItemChanged(position)
    }

}