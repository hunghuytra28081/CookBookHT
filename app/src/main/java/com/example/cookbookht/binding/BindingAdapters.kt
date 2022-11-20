package com.example.cookbookht.binding

import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cookbookht.extension.loadFromUrl
import com.example.cookbookht.utils.BindingDataRecyclerView
import com.example.cookbookht.utils.LoadMoreRecyclerViewListener
import com.example.cookbookht.utils.RefreshRecyclerViewListener
import java.util.*

@BindingAdapter("bind:onLoadImage")
fun ImageView.loadImage(url: String?) {
    url?.let {
        loadFromUrl(url)
    }
}

@BindingAdapter("bind:setTextSize")
fun TextView.setTextSize(size: Int?) {
    text = size.toString()
}

@BindingAdapter(value = ["bind:setTextHtml"])
fun TextView.setTextHTML(html: String?) {
    text = when {
        html == null -> {
            "Don't have Summary"
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        }
        else -> {
            Html.fromHtml(html)
        }
    }
}

@BindingAdapter("bind:data")
fun <T> setData(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindingDataRecyclerView<*>) {
        (recyclerView.adapter as BindingDataRecyclerView<T>).setData(data)
    }
}

@BindingAdapter(value = ["bind:adapter"])
fun RecyclerView.setAdapter(adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
}

@BindingAdapter(value = ["bind:isLoad", "bind:onLoadMore"])
fun RecyclerView.onScrollListener(
    isLoad: Boolean,
    loadMoreListener: LoadMoreRecyclerViewListener
) {
    clearOnScrollListeners()
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                when (recyclerView.layoutManager) {
                    is LinearLayoutManager -> {
                        (recyclerView.layoutManager as LinearLayoutManager).run {
                            if (!isLoad && findLastCompletelyVisibleItemPosition() == itemCount - 1) {
                                loadMoreListener.onLoadData()
                            }
                        }
                    }
                    is GridLayoutManager -> {
                        (recyclerView.layoutManager as GridLayoutManager).run {
                            if (!isLoad && (childCount + findFirstVisibleItemPosition()) >= itemCount) {
                                loadMoreListener.onLoadData()
                            }
                        }
                    }
                }
            }
        }
    })
}

@BindingAdapter(value = ["bind:onRefresh"])
fun SwipeRefreshLayout.onRefresh(refresh: RefreshRecyclerViewListener) {
    setOnRefreshListener {
        refresh.onRefresh()
    }
}
