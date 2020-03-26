package com.ray.library.easyrecyclerview.adapter

import androidx.annotation.Nullable

/**
 * @author Ray Huang
 * @since 2020-03-20
 */
class AdapterHelper {

  companion object Functions {

    /**
     * Gets the list item held at the specified adapter position.
     *
     * @param index The index of the list item to return
     * @return The list item at the specified position
     */
    @Nullable
    fun <T> getListItem(
      itemList: List<T>,
      index: Int
    ): T? {
      return if (checkIsLegalIndex(itemList, index))
        itemList[index]
      else
        null
    }

    fun <T> checkIsLegalIndex(
      itemList: List<T>,
      index: Int
    ): Boolean {
      return index >= 0 && index < itemList.size
    }
  }
}