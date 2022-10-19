package com.example.cookbookht.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.example.cookbookht.R

fun ImageView.loadFromUrl(url: String) {
    Glide.with(this.context.applicationContext)
        .load(url)
        .error(R.drawable.cookit)
        .placeholder(R.drawable.loading_icon_opacity)
//        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
//        .skipMemoryCache(false)
        .into(this)
}

fun ImageView.loadUrlStaggered(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .fitCenter()
        .override(Target.SIZE_ORIGINAL)
        .into(this)
}
