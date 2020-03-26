package com.ray.library.easyrecyclerview.`interface`

import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @author Ray Huang
 * @since 2020-03-18
 */
interface IGetViewHelper {

  // Get any type of view
  fun getView(id: Int): View?

  fun getButton(id: Int): Button?

  fun getImageButton(id: Int): ImageButton?

  fun getImageView(id: Int): ImageView?

  fun getTextView(id: Int): TextView?

  fun getEditText(id: Int): EditText?

  fun getLinearLayout(id: Int): LinearLayout?

  fun getRelativeLayout(id: Int): RelativeLayout?

  fun getConstraintLayout(id: Int): ConstraintLayout?
}