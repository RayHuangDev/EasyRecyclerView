package com.ray.library.easyrecyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.ray.library.easyrecyclerview.holder.BaseRecyclerViewHolder

/**
 * @author Ray Huang
 * @since 2020/3/25
 */
abstract class SingleVHAdapter<VH : BaseRecyclerViewHolder, T>(
  context: Context,
  @NonNull list: List<T>
) : BaseRecyclerViewAdapter<T>() {

  private var context: Context = context

  init {
    Log.e(
      "yoyo",
      "single list size = " + list.size
    )
    addItems(list)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseRecyclerViewHolder {
    return onCreateViewHolderImp(parent!!)
  }

  override fun onBindViewHolder(
    holder: BaseRecyclerViewHolder,
    position: Int
  ) {
    AdapterHelper.getListItem(
      (getCollection() as List<T>?)!!,
      position
    )
      ?.run {
        holder.setItemClickable()
        onBindViewHolderImp(
          holder as VH,
          position,
          this
        )
      } ?: throw IllegalStateException("Incorrect ViewHolder found")
  }

  // public function
  open fun getContext(): Context? = context

  open fun updateListWithNotify(@NonNull list: List<T>?): Unit {
    clearItems()
    postNotify()
    addItems(list!!)
    postNotify()
  }

  open fun updateListWithoutNotify(@NonNull list: List<T>?) {
    clearItems()
    postNotify()
    addItems(list!!)
  }

  // abstract
  protected abstract fun onCreateViewHolderImp(parent: ViewGroup): BaseRecyclerViewHolder

  protected abstract fun onBindViewHolderImp(
    holder: VH,
    position: Int,
    listItem: T
  )
}