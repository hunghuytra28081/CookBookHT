package com.example.cookbookht.ui.homepage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomePageAdapter(
    fragmentManager: FragmentManager,
    private var fragments: List<Fragment>
): FragmentPagerAdapter(
    fragmentManager
) {
    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]
}
