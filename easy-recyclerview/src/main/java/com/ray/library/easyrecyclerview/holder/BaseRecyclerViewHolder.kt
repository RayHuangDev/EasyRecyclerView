package com.ray.library.easyrecyclerview.holder

import android.util.SparseArray
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ray.library.easyrecyclerview.`interface`.IGetViewHelper
import com.ray.library.easyrecyclerview.listener.OnItemClickListener

/**
 * @author Ray Huang
 * @since 2020-03-19
 */
class BaseRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IGetViewHelper,
  View.OnClickListener {

  private var onItemClickListener: OnItemClickListener? = null
  private var viewSparseArray: SparseArray<View>? = null
  private var view: View? = null

  init {
    view = itemView
    viewSparseArray = SparseArray()
  }

  /**
   * Sets a [View.OnClickListener] on the entire parent view to trigger expansion.
   */
  fun setItemClickable() {
    itemView.setOnClickListener(this)
  }

  fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
    this.onItemClickListener = onItemClickListener
  }

  fun getOnItemClickListener(): OnItemClickListener? {
    return onItemClickListener
  }

  override fun onClick(v: View?) {
    if (onItemClickListener != null) {
      if (v == itemView) {
        onItemClickListener!!.onRootViewClick(adapterPosition)
      } else {
        onItemClickListener!!.onSpecificViewClick(
          v!!.id,
          adapterPosition
        )
      }
    }
  }

  /**
   * Get view from resource
   */
  private fun <T : View?> findViewByIdImp(id: Int): T? {
    var childView = viewSparseArray!![id]
    if (childView == null) {
      childView = view!!.findViewById(id)
      viewSparseArray!!.put(
        id,
        childView
      )
    }
    return childView as T
  }

  override fun getView(id: Int): View? = findViewByIdImp(id)

  override fun getButton(id: Int): Button? = findViewByIdImp(id)

  override fun getImageButton(id: Int): ImageButton? = findViewByIdImp(id)

  override fun getImageView(id: Int): ImageView? = findViewByIdImp(id)

  override fun getTextView(id: Int): TextView? = findViewByIdImp(id)

  override fun getEditText(id: Int): EditText? = findViewByIdImp(id)

  override fun getLinearLayout(id: Int): LinearLayout? = findViewByIdImp(id)

  override fun getRelativeLayout(id: Int): RelativeLayout? = findViewByIdImp(id)

  override fun getConstraintLayout(id: Int): ConstraintLayout? = findViewByIdImp(id)
}