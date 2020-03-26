package com.ray.library.easyrecyclerview.listener

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Ray Huang
 * @since 2020/3/25
 */
abstract class BaseHideScrollListener: RecyclerView.OnScrollListener() {

  private val SCROLL_THRESHOLD = 50
  private var scrolledDistance = 0
  private var isHeaderVisible = false


  override fun onScrollStateChanged(
    recyclerView: RecyclerView,
    newState: Int
  ) {
  }

  override fun onScrolled(
    recyclerView: RecyclerView,
    dx: Int,
    dy: Int
  ) {
    if (scrolledDistance > SCROLL_THRESHOLD && isHeaderVisible) {
      hideHeaderViewAnimateImp(recyclerView)
    } else if (scrolledDistance < -SCROLL_THRESHOLD && !isHeaderVisible) {
      showHeaderViewAnimateImp(recyclerView)
    }
    if (isHeaderVisible && dy > 0 || !isHeaderVisible && dy < 0) {
      scrolledDistance += dy
    }
  }

  // public function
  open fun setHeaderVisible(visible: Boolean) {
    isHeaderVisible = visible
    scrolledDistance = 0
  }

  open fun isHeaderVisible(): Boolean {
    return isHeaderVisible
  }

  // abstract function
  abstract fun hideHeaderViewAnimateImp(recyclerView: RecyclerView?)

  abstract fun showHeaderViewAnimateImp(recyclerView: RecyclerView?)

  abstract fun hideHeaderViewImp(recyclerView: RecyclerView?)

  abstract fun showHeaderViewImp(recyclerView: RecyclerView?)
}