package com.ray.easyrecyclerview

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ray.library.easyrecyclerview.adapter.SingleVHAdapter
import com.ray.library.easyrecyclerview.decoration.EasyDecoration
import com.ray.library.easyrecyclerview.holder.BaseRecyclerViewHolder
import com.ray.library.easyrecyclerview.listener.LoadMoreScrollListener
import com.ray.library.easyrecyclerview.listener.OnItemClickListener
import com.ray.library.easyrecyclerview.utils.ViewUtils
import java.lang.ref.WeakReference

class DemoActivity : AppCompatActivity(), OnItemClickListener {

  private val LIST_SIZE = 200

  private lateinit var demoList: MutableList<Int>

  init {
    if (::demoList.isInitialized.not()){
      demoList = ArrayList(LIST_SIZE)

      for (i in 0 until LIST_SIZE) {
        demoList.add(i)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_demo)

    verticalDemo()
    horizontalDemo()
  }

  // private function
  private fun verticalDemo(): Unit {
    val recyclerView: RecyclerView = findViewById(R.id.vertical_rv)
    val autoLoadAdapter: VerticalAdapter = VerticalAdapter(
      this,
      applicationContext,
      demoList
    )
    autoLoadAdapter.setAutoLoad(true)
    recyclerView.adapter = autoLoadAdapter
    recyclerView.setHasFixedSize(true)
    val layoutManager = LinearLayoutManager(
      applicationContext,
      LinearLayoutManager.VERTICAL,
      false
    )
    recyclerView.layoutManager = layoutManager
    recyclerView.addOnScrollListener(LoadMoreScrollListener())
    recyclerView.addItemDecoration(
      EasyDecoration.Functions.Builder().setMargin(
        10,
        10,
        10,
        10
      )
        .setDividerOrientation(layoutManager)
        .setDividerSize(
          ViewUtils.convertDp2Pixel(
            2f,
            applicationContext
          )
        ).build()
    )
  }

  private fun horizontalDemo() {
    val recyclerView: RecyclerView = findViewById(R.id.horizontal_rv)
    val adapter: HorizontalAdapter = HorizontalAdapter(
      this,
      applicationContext,
      demoList
    )
    recyclerView.adapter = adapter
    recyclerView.setHasFixedSize(true)
    val layoutManager = LinearLayoutManager(
      applicationContext,
      LinearLayoutManager.HORIZONTAL,
      false
    )
    recyclerView.layoutManager = layoutManager
    recyclerView.addOnScrollListener(LoadMoreScrollListener())
    // Don't know why the bottom margin not working correctly at HORIZONTAL orientation
    recyclerView.addItemDecoration(
      EasyDecoration.Functions.Builder().setMargin(
        10,
        10,
        10,
        0
      ).setDividerOrientation(layoutManager)
        .setDividerSize(
          ViewUtils.convertDp2Pixel(
            3f,
            applicationContext
          )
        )
        .build()
    )
  }

  private class VerticalAdapter(
    reference: DemoActivity,
    ctx: Context,
    itemList: List<Int>
  ) : SingleVHAdapter<BaseRecyclerViewHolder, Int>(ctx, itemList) {
    private val instance: DemoActivity?

    init {
      val weakReference = WeakReference(reference)
      instance = weakReference.get()
    }

    override fun onCreateViewHolderImp(parent: ViewGroup): BaseRecyclerViewHolder {
      val recyclerViewHolder = BaseRecyclerViewHolder(
        LayoutInflater.from(getContext()).inflate(
          R.layout.list_content,
          parent,
          false
        )
      )
      // Register click listener of root view
      recyclerViewHolder.setOnItemClickListener(instance)
      return recyclerViewHolder
    }

    override fun onBindViewHolderImp(
      holder: BaseRecyclerViewHolder,
      position: Int,
      listItem: Int
    ) {
      // Register click listener of specific view
      holder.getView(R.id.item_tv)!!.setOnClickListener(holder)
      holder.getView(R.id.item_tv)!!.setBackgroundColor(Color.RED)
      if (isLoading() && position == itemCount - 1) {
        holder.getView(R.id.item_tv)!!.visibility = View.GONE
        holder.getView(R.id.loading)!!.visibility = View.VISIBLE
      } else {
        holder.getView(R.id.loading)!!.visibility = View.GONE
        holder.getTextView(R.id.item_tv)!!.visibility = View.VISIBLE
        holder.getTextView(R.id.item_tv)!!.text = listItem.toString()
      }
    }

    override fun getInitItemCount(): Int = 0
  }

  private class HorizontalAdapter (
    reference: DemoActivity,
    ctx: Context,
    @NonNull itemList: List<Int>
  ) : SingleVHAdapter<BaseRecyclerViewHolder, Int>(ctx, itemList) {

    private val instance: DemoActivity?

    init {
      val weakReference = WeakReference(reference)
      instance = weakReference.get()
    }
    override fun onCreateViewHolderImp(parent: ViewGroup): BaseRecyclerViewHolder {
      val recyclerViewHolder = BaseRecyclerViewHolder(
        LayoutInflater.from(getContext()).inflate(R.layout.list_content, parent, false)
      )
      // Register click listener of root view
      recyclerViewHolder.setOnItemClickListener(instance)
      return recyclerViewHolder
    }

    override fun onBindViewHolderImp(
      holder: BaseRecyclerViewHolder, position: Int,
      listItem: Int
    ) { // Register click listener of specific view
      holder.getView(R.id.item_tv)!!.setOnClickListener(holder)
      holder.getView(R.id.item_tv)!!.setBackgroundColor(Color.BLUE)
      if (isLoading() && position == itemCount - 1) {
        holder.getView(R.id.item_tv)!!.visibility = View.GONE
        holder.getView(R.id.loading)!!.visibility = View.VISIBLE
      } else {
        holder.getView(R.id.loading)!!.visibility = View.GONE
        holder.getTextView(R.id.item_tv)!!.visibility = View.VISIBLE
        holder.getTextView(R.id.item_tv)!!.text = listItem.toString()
      }
    }

    override fun getInitItemCount(): Int = 0
  }

  /**
   * Root click callback from {@link BaseRecyclerViewHolder}
   *
   * @param position position of data set
   */
  override fun onRootViewClick(position: Int) {
    Toast.makeText(
      applicationContext,
      "Position: $position, parent view clicked.",
      Toast.LENGTH_SHORT
    ).show()
  }

  /**
   * Specific child view callback from {@link BaseRecyclerViewHolder}
   *
   * @param id child view id
   * @param position position of data set
   */
  override fun onSpecificViewClick(id: Int, position: Int) {
    Toast.makeText(
      applicationContext,
      "Position: $position, child view id: $id clicked.",
      Toast.LENGTH_SHORT
    ).show()
  }
}
