package com.example.cookbookht.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.example.cookbookht.R
import kotlinx.android.synthetic.main.item_slide_pager.view.*

class SliderAdapter(
    private val context: Context,
    itemList: MutableList<Drawable>,
    isInfinite: Boolean
) : LoopingPagerAdapter<Drawable>(itemList, isInfinite) {

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        Glide.with(context).load(itemList?.get(listPosition)).centerCrop().into(convertView.slideImageView)
    }

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_slide_pager, container, false)
    }
}
