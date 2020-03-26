package com.ray.library.easyrecyclerview.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.ray.library.easyrecyclerview.holder.BaseRecyclerViewHolder

/**
 * @author Ray Huang
 * @since 2020/3/25
 */
abstract class HeaderFooterVHAdapter<HVH : BaseRecyclerViewHolder, FVH : BaseRecyclerViewHolder, BVH : BaseRecyclerViewHolder, T> :
  BaseRecyclerViewAdapter<T>() {

  companion object {
    const val HEADER_VIEW = 1
    const val BODY_VIEW = 2
    const val FOOTER_VIEW = 3
  }

  private var hasHeader = false
  private var hasFooter = false
  private var context: Context? = null

  fun HeaderFooterVHAdapter(
    hasHeader: Boolean,
    hasFooter: Boolean,
    context: Context?,
    @NonNull list: List<T>
  ) {
    this.hasHeader = hasHeader
    this.hasFooter = hasFooter
    this.context = context
    addItems(list)
  }

  override fun getItemViewType(position: Int): Int {
    return if (hasHeader && position == 0) {
      HEADER_VIEW
    } else if (hasFooter && position == getCollectionSize() - 1) {
      FOOTER_VIEW
    } else {
      BODY_VIEW
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseRecyclerViewHolder {
    return when (viewType) {
      HEADER_VIEW -> {
        onCreateHeaderViewHolderImp(parent)
      }
      FOOTER_VIEW -> {
        onCreateFooterViewHolderImp(parent)
      }
      else -> {
        onCreateBodyViewHolderImp(parent)
      }
    }
  }

  @SuppressWarnings("unchecked")
  override fun onBindViewHolder(
    holder: BaseRecyclerViewHolder,
    position: Int
  ) {
    val listItem =
      AdapterHelper.getListItem(
        (getCollection() as List<T>?)!!,
        position
      )
    if (listItem == null) {
      //throw new IllegalStateException("Incorrect ViewHolder found");
    } else {
      holder.setItemClickable()
      when (holder.itemViewType) {
        HEADER_VIEW -> {
          onBindHeaderViewHolderImp(
            holder as HVH,
            position,
            listItem
          )
        }
        FOOTER_VIEW -> {
          onBindFooterViewHolderImp(
            holder as FVH,
            position,
            listItem
          )
        }
        else -> {
          onBindBodyViewHolderImp(
            holder as BVH,
            position,
            listItem
          )
        }
      }
    }
  }

  // public function
  open fun getContext(): Context? = context

  open fun hasHeader(): Boolean = hasHeader
  open fun hasFooter(): Boolean = hasFooter

  open fun updateListWithNotify(@NonNull list: List<T>?) {
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

  // abstract function
  protected abstract fun onCreateHeaderViewHolderImp(parent: ViewGroup?): BaseRecyclerViewHolder

  protected abstract fun onCreateBodyViewHolderImp(parent: ViewGroup?): BaseRecyclerViewHolder

  protected abstract fun onCreateFooterViewHolderImp(parent: ViewGroup?): BaseRecyclerViewHolder

  protected abstract fun onBindHeaderViewHolderImp(
    holder: HVH,
    position: Int,
    listItem: T
  )

  protected abstract fun onBindBodyViewHolderImp(
    holder: BVH,
    position: Int,
    listItem: T
  )

  protected abstract fun onBindFooterViewHolderImp(
    holder: FVH,
    position: Int,
    listItem: T
  )
}