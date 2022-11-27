package com.example.cookbookht.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cookbookht.R
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.databinding.FragmentHomeBinding
import com.example.cookbookht.extension.clickWithDebounce
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.sharePreference.Preferences
import com.example.cookbookht.ui.search.SearchViewModel
import com.example.cookbookht.ui.sheetDialog.SheetSettingLanguage
import com.example.cookbookht.ui.slide.SlideActivity
import com.example.cookbookht.utils.Constant
import com.example.cookbookht.utils.Status
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.swipeRefresh
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var dataSlide = mutableListOf<Drawable>()
    private lateinit var sliderAdapter: SliderAdapter

    private val prefs: Preferences by inject()

    private val homeViewModel by viewModel<HomeViewModel>()
    private val searchViewModel by viewModel<SearchViewModel>()
    private val searchAdapter = HomeAdapter(this::onClickItemHome, prefs, lifecycleScope)
    private val homeAdapter = HomeAdapter(this::onClickItemHome, prefs, lifecycleScope)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initSliderView()
        registerObserver()
        initListener()
    }

    private fun initSliderView() {
        initSliderData()
        sliderAdapter = SliderAdapter(requireContext(), dataSlide, true)
        with(sliderViewPager) {
            adapter = sliderAdapter
        }
        indicator.highlighterViewDelegate = {
            val highlighter = View(requireContext())
            highlighter.layoutParams = FrameLayout.LayoutParams(24, 6)
            highlighter.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_white
                )
            )
            highlighter
        }
        indicator.unselectedViewDelegate = {
            val unselected = View(requireContext())
            unselected.layoutParams = LinearLayout.LayoutParams(24, 6)
            unselected.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_white
                )
            )
            unselected.alpha = 0.4f
            unselected
        }
//        indicator.updateIndicatorCounts(sliderViewPager.indicatorCount)
//        try {
//            sliderViewPager.onIndicatorProgress = { selectingPosition, progress ->
//                indicator.onPageScrolled(selectingPosition, progress)
//            }
//        }catch (e: Exception){
//
//        }
//        indicator.updateIndicatorCounts(sliderViewPager.indicatorCount)
    }

    private fun initSliderData() {
        if (dataSlide.isEmpty()) {
            dataSlide.apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.cookit)?.let { add(it) }
                ContextCompat.getDrawable(requireContext(), R.drawable.suonkhotet)?.let { add(it) }
                ContextCompat.getDrawable(requireContext(), R.drawable.tet)?.let { add(it) }
                ContextCompat.getDrawable(requireContext(), R.drawable.banhu)?.let { add(it) }
            }
        }
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@HomeFragment
            viewModel = this@HomeFragment.homeViewModel
            homeAdapter = this@HomeFragment.homeAdapter
            searchViewModel = this@HomeFragment.searchViewModel
            searchAdapter = this@HomeFragment.searchAdapter
        }
    }

    private fun registerObserver() {
        homeViewModel.resource.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayout.toVisible()
                }
                Status.SUCCESS -> {
                    binding.progressLayout.toGone()
                    binding.swipeRefresh.isRefreshing = false
                }
                Status.ERROR -> {
                    binding.progressLayout.toGone()
                    binding.swipeRefresh.isRefreshing = false
                }
            }

            binding.layoutEmpty.isVisible = it.data?.value.isNullOrEmpty()
        }

//        searchViewModel.searchRecipe(Constant.DEFAULT_FOOD)
        searchViewModel.resource.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressLayout.toVisible()
                }
                Status.SUCCESS -> {
                    binding.progressLayout.toGone()
                    binding.swipeRefreshFood.isRefreshing = false
                }
                Status.ERROR -> {
                    binding.progressLayout.toGone()
                    binding.swipeRefreshFood.isRefreshing = false
                }
            }

            binding.layoutEmpty.isVisible = it.data?.value.isNullOrEmpty()
        }
    }

    private fun initListener() {
        binding.settingLanguage.clickWithDebounce {
            SheetSettingLanguage {
                when (it) {
                    0 -> {
                        if (prefs.isLanguageVi.get() == "en") {
                            prefs.isLanguageVi.set("vi")
                            changeLanguage("vi")
                            activity?.finish()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.is_vietnamese),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    1 -> {
                        if (prefs.isLanguageVi.get() == "vi") {
                            prefs.isLanguageVi.set("en")
                            changeLanguage("en")
                            activity?.finish()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.is_english),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    else -> {
                    }
                }
            }.show(childFragmentManager, null)
        }
        binding.animateTab.onTabSelected = {
            Log.d("Hung_bottom_bar", "Selected tab: " + it.title)

            when (it.title) {
                resources.getString(R.string.random) -> {
                    //                    homeViewModel.onRefresh()
                    binding.layoutRandom.isVisible = true
                    binding.layoutFood.isVisible = false
                }
                resources.getString(R.string.foods) -> {
                    searchViewModel.onRefreshSearch("food")
                    binding.layoutRandom.isVisible = false
                    binding.layoutFood.isVisible = true

                    swipeRefreshFood.setOnRefreshListener {
                        searchViewModel.onRefreshSearch("food")
                    }
                }
                resources.getString(R.string.drinks) -> {
                    searchViewModel.onRefreshSearch("drinks")
                    binding.layoutRandom.isVisible = false
                    binding.layoutFood.isVisible = true

                    swipeRefreshFood.setOnRefreshListener {
                        searchViewModel.onRefreshSearch("drinks")
                    }
                }
            }
        }

        binding.animateTab.selectedTab
    }

    private fun changeLanguage(language: String) {
        val locale = Locale(language)
        val config = Configuration()
        config.locale = locale

        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
        val intent = Intent(requireContext(), SlideActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemHome(recipe: Recipe) {
//        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(recipe)
        val bundle = bundleOf("recipe" to recipe.id)
        findNavController().navigate(R.id.detailFragment, bundle)
        Log.d("Main12345", "onClickItemHome: ${recipe.id}")
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
