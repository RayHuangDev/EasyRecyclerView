package com.ray.library.easyrecyclerview.adapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.recyclerview.widget.RecyclerView
import com.ray.library.easyrecyclerview.holder.BaseRecyclerViewHolder
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Ray Huang
 * @since 2020-03-19
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

  companion object {
    const val VISIBLE_ITEM_THRESHOLD = 15
    const val VALID_ITEM_COUNT = 30
  }

  private val list: MutableList<T> = ArrayList()
  private val handler: RecyclerViewHandler = RecyclerViewHandler(Looper.getMainLooper())
  private val atomicLoading: AtomicBoolean = AtomicBoolean()
  private var autoLoad = false
  private var visibleItemThreshold = VISIBLE_ITEM_THRESHOLD
  private var validItemCount = VALID_ITEM_COUNT
  private var currentItemCount = 0

  open fun postNotify() {
    handler!!.post { notifyDataSetChanged() }
  }

  override fun getItemCount(): Int {
    currentItemCount = if (!isAutoLoad()) {
      return getCollectionSize()
    } else {
      if (getCollectionSize() < currentItemCount + VALID_ITEM_COUNT) {
        getCollectionSize()
      } else {
        if (currentItemCount == getInitItemCount()) VALID_ITEM_COUNT else currentItemCount
      }
    }
    return currentItemCount
  }

  open fun getCollection(): Collection<T>? {
    return list
  }

  open fun getCollectionSize(): Int {
    synchronized(list) { return list.size }
  }

  open fun clearItems() {
    synchronized(list) { list.clear() }
  }

  open fun addItem(item: T) {
    synchronized(list) { list.add(item) }
  }

  open fun addItems(items: Collection<T>) {
    for (item in items) {
      addItem(item)
    }
  }

  open fun removeItem(index: Int) {
    synchronized(list) {
      if (AdapterHelper.checkIsLegalIndex(
          list,
          index
        )) {
        list.removeAt(index)
      }
    }
  }

  open fun removeItem(item: T) {
    synchronized(list) { list.remove(item) }
  }

  open fun removeItemSucceeded(index: Int): Boolean {
    synchronized(list) {
      val bCheckIsLegalIndex: Boolean = AdapterHelper.checkIsLegalIndex(list, index)
      if (bCheckIsLegalIndex) {
        list.removeAt(index)
        return true
      }
    }
    return false
  }

  open fun getItem(index: Int): T? {
    synchronized(list) {
      return AdapterHelper.getListItem(
        list,
        index
      )
    }
  }

  open fun getIndex(item: T): Int {
    synchronized(list) {
      if (list.contains(item)) {
        val size = list.size
        for (i in 0 until size) {
          if (list[i] == item) {
            return i
          }
        }
      }
    }
    return -1
  }

  open fun setItem(
    index: Int,
    item: T
  ) {
    synchronized(list) {
      list.set(
        index,
        item
      )
    }
  }

  open fun setItem(
    oldItem: T,
    newItem: T
  ) {
    val index = getIndex(oldItem)
    if (index > -1) {
      synchronized(list) {
        list.set(
          index,
          newItem
        )
      }
    }
  }

  protected abstract fun getInitItemCount(): Int

  open fun isAutoLoad(): Boolean {
    return autoLoad
  }

  open fun setAutoLoad(autoLoad: Boolean) {
    this.autoLoad = autoLoad
  }

  open fun getVisibleItemThreshold(): Int {
    return visibleItemThreshold
  }

  open fun setVisibleItemThreshold(visibleItemThreshold: Int) {
    this.visibleItemThreshold = visibleItemThreshold
  }

  open fun getValidItemCount(): Int {
    return validItemCount
  }

  open fun setValidItemCount(validItemCount: Int) {
    this.validItemCount = validItemCount
  }

  open fun addCurrentItemCount(count: Int) {
    currentItemCount =
      if (getCollectionSize() < currentItemCount + count) getCollectionSize() else currentItemCount + count
  }

  open fun getCurrentItemCount(): Int {
    return currentItemCount
  }

  open fun setCurrentItemCount(itemCount: Int) {
    currentItemCount = itemCount
  }

  open fun canLoad(): Boolean {
    return atomicLoading!!.compareAndSet(
      false,
      true
    )
  }

  open fun setLoaded() {
    atomicLoading!!.set(false)
  }

  open fun isLoading(): Boolean {
    return atomicLoading!!.get()
  }

  private class RecyclerViewHandler internal constructor(looper: Looper?) :
    Handler(looper) {
    override fun handleMessage(msg: Message) {
      super.handleMessage(msg)
    }
  }
}