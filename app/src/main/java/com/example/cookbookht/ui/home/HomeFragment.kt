package com.example.cookbookht.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookbookht.R
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.databinding.FragmentHomeBinding
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Status
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class HomeFragment : Fragment() {

    private var dataSlide = mutableListOf<Drawable>()
    private lateinit var sliderAdapter: SliderAdapter

    private val homeViewModel by viewModel<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private val homeAdapter = HomeAdapter(this::onClickItemHome)

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
        indicator.updateIndicatorCounts(sliderViewPager.indicatorCount)
        try {
            sliderViewPager.onIndicatorProgress = { selectingPosition, progress ->
                indicator.onPageScrolled(selectingPosition, progress)
            }
        }catch (e: Exception){

        }
        indicator.updateIndicatorCounts(sliderViewPager.indicatorCount)
    }

    private fun initSliderData() {
        if (dataSlide.isEmpty()){
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
