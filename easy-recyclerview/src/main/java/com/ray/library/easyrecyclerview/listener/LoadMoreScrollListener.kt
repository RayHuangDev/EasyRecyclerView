package com.ray.library.easyrecyclerview.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.ray.library.easyrecyclerview.adapter.BaseRecyclerViewAdapter

/**
 * @author Ray Huang
 * @since 2020-03-20
 */
class LoadMoreScrollListener : OnScrollListener() {

  override fun onScrolled(
    recyclerView: RecyclerView,
    dx: Int,
    dy: Int
  ) {
    super.onScrolled(
      recyclerView,
      dx,
      dy
    )

    val linearLayoutManager: LinearLayoutManager? =
      recyclerView.layoutManager as LinearLayoutManager?
    if (recyclerView.childCount > 0) {
      val totalItemCount = linearLayoutManager!!.itemCount
      val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
      val adapter = recyclerView.adapter as BaseRecyclerViewAdapter<*>
      if (!recyclerView.isComputingLayout
        && totalItemCount <= lastVisibleItem + BaseRecyclerViewAdapter.VISIBLE_ITEM_THRESHOLD
      ) {
        if (adapter.itemCount >= adapter.getCollectionSize()) {
          return
        }
        if (adapter.canLoad()) {
          val loadingIndex: Int = adapter.itemCount - 1
          recyclerView.postDelayed(
            {
              adapter.notifyItemChanged(loadingIndex)
              adapter.setLoaded()
              adapter.notifyItemChanged(loadingIndex)
              val startIndex = loadingIndex + 1
              adapter.addCurrentItemCount(BaseRecyclerViewAdapter.VALID_ITEM_COUNT)
              val endIndex: Int = adapter.itemCount
              adapter.notifyItemRangeInserted(
                startIndex,
                endIndex
              )
            },
            500
          )
        }
      }
    }
  }
}