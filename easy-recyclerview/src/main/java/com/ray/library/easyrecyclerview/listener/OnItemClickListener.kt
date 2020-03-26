package com.ray.library.easyrecyclerview.listener

/**
 * @author Ray Huang
 * @since 2020-03-19
 */
interface OnItemClickListener {

  fun onRootViewClick(position: Int)

  fun onSpecificViewClick(
    id: Int,
    position: Int
  )
}