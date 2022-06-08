package com.example.cookbookht.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.cookbookht.R
import com.example.cookbookht.databinding.FragmentHomePageBinding
import com.example.cookbookht.ui.favorite.FavoriteFragment
import com.example.cookbookht.ui.home.HomeFragment
import com.example.cookbookht.ui.search.SearchFragment
import com.example.cookbookht.utils.MenuHomePageContainerItem
import kotlinx.android.synthetic.main.fragment_home_page.*

class HomePageFragment : Fragment() {

    lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onInitViewPage()
        onInitNav()
    }

    private fun onInitNav() {
        binding.bottomNavigationBar.apply {
            setItemSelected(R.id.homeItem)
            setOnItemSelectedListener {
                when (it) {
                    R.id.homeItem -> {
                        homePageViewPager.currentItem = MenuHomePageContainerItem.HOME.ordinal
                    }
                    R.id.searchItem -> {
                        homePageViewPager.currentItem = MenuHomePageContainerItem.SEARCH.ordinal
                    }
                    R.id.favoriteItem -> {
                        homePageViewPager.currentItem = MenuHomePageContainerItem.FAVORITE.ordinal
                    }
                }
            }
        }
    }

    private fun onInitViewPage() {
        val listFragment = listOf(
            HomeFragment.newInstance(),
            SearchFragment.newInstance(),
            FavoriteFragment.newInstance()
        )
        childFragmentManager.let {
            binding.homePageViewPager.apply {
                adapter = HomePageAdapter(it, listFragment)
                offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
            }
        }
        binding.homePageViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.bottomNavigationBar.setItemSelected(bottomNavigationBar[position].id)
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
    }

    companion object {

        private const val OFF_SCREEN_PAGE_LIMIT = 3

        fun newInstance() = HomePageFragment()
    }
}
