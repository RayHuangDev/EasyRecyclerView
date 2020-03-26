package com.ray.library.easyrecyclerview.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Ray Huang
 * @since 2020/3/25
 */
class EasyDecoration(
  val rect: Rect,
  val orientation: Int,
  val dividerDrawable: ColorDrawable,
  val dividerSize: Int,
  val dividerOffset: Int
) : RecyclerView.ItemDecoration() {


  companion object Functions {
    class Builder {
      private val rect: Rect = Rect()
      private var orientation: Int
      private val dividerDrawable: ColorDrawable
      private var dividerSize: Int
      private var dividerOffset: Int

      fun setMarginLeft(left: Int): Builder {
        rect.left = left
        return this
      }

      fun setMarginTop(top: Int): Builder {
        rect.top = top
        return this
      }

      fun setMarginRigth(right: Int): Builder {
        rect.right = right
        return this
      }

      fun setMarginBottom(bottom: Int): Builder {
        rect.bottom = bottom
        return this
      }

      fun setMargin(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
      ): Builder {
        rect[left, top, right] = bottom
        return this
      }

      fun setMargin(rect: Rect?): Builder {
        this.rect.set(rect)
        return this
      }

      fun setDividerOrientation(orientation: Int): Builder {
        this.orientation = orientation
        return this
      }

      fun setDividerOrientation(layoutManager: RecyclerView.LayoutManager?): Builder {
        if (layoutManager is LinearLayoutManager) {
          orientation = layoutManager.orientation
        } else {
          throw UnsupportedOperationException(
            "Set orientation but layout manager is not LinearLayoutManager"
          )
        }
        return this
      }

      fun setDividerDrawable(@ColorInt color: Int): Builder {
        dividerDrawable.color = color
        return this
      }

      fun setDividerSize(dividerSize: Int): Builder {
        this.dividerSize = dividerSize
        return this
      }

      fun setDividerOffset(offset: Int): Builder {
        dividerOffset = offset
        return this
      }

      fun build(): EasyDecoration {
        if (orientation == LinearLayoutManager.VERTICAL) {
          rect.bottom = rect.bottom + dividerSize
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
          rect.right = rect.right + dividerSize
        }
        return EasyDecoration(
          rect,
          orientation,
          dividerDrawable,
          dividerSize,
          dividerOffset
        )
      }

      init {
        orientation = -1
        dividerDrawable = ColorDrawable(Color.BLACK)
        dividerSize = 4
        dividerOffset = 0
      }
    }
  }

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    outRect.set(rect)
  }

  override fun onDraw(
    c: Canvas,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    when (orientation) {
      LinearLayoutManager.VERTICAL -> drawHorizontalLine(
        c,
        parent
      )
      LinearLayoutManager.HORIZONTAL -> drawVerticalLine(
        c,
        parent
      )
      else -> {
      }
    }
  }

  private fun drawHorizontalLine(
    c: Canvas,
    parent: RecyclerView
  ) {
    val left = rect.left + parent.paddingLeft
    var top: Int
    val right = parent.right - rect.right - parent.paddingRight
    var bottom: Int
    val offset = (rect.bottom + dividerSize) / 2
    // minus offset, those items no needs to draw line
    val childCount = parent.childCount - dividerOffset
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      val params =
        child.layoutParams as RecyclerView.LayoutParams
      top = child.bottom + params.bottomMargin + offset
      bottom = top + dividerSize
      dividerDrawable.setBounds(
        left,
        top,
        right,
        bottom
      )
      dividerDrawable.draw(c)
    }
  }

  private fun drawVerticalLine(
    c: Canvas,
    parent: RecyclerView
  ) {
    var left: Int
    val top = rect.top + parent.paddingTop
    var right: Int
    val bottom = parent.bottom - rect.bottom - parent.paddingBottom
    val offset = (rect.right + dividerSize) / 2
    // minus offset, those items no needs to draw line
    val childCount = parent.childCount - dividerOffset
    for (i in 0 until childCount) {
      val child = parent.getChildAt(i)
      val params =
        child.layoutParams as RecyclerView.LayoutParams
      left = child.right + params.rightMargin + offset
      right = left + dividerSize
      dividerDrawable.setBounds(
        left,
        top,
        right,
        bottom
      )
      dividerDrawable.draw(c)
    }
  }
}