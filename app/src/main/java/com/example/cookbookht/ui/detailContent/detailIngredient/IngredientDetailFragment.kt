package com.example.cookbookht.ui.detailContent.detailIngredient

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookbookht.data.model.Ingredient
import com.example.cookbookht.databinding.FragmentIngredientDetailBinding
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Status
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientDetailFragment(
    private val recipeDetailId: Int?
) : Fragment() {

    private lateinit var binding: FragmentIngredientDetailBinding
    private val ingredientViewModel by viewModel<IngredientDetailViewModel>()
    private val ingredientAdapter = IngredientDetailAdapter(::onClickItemIngredient)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        registerObserver()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@IngredientDetailFragment
            ingredientViewModel = this@IngredientDetailFragment.ingredientViewModel
            ingredientAdapter = this@IngredientDetailFragment.ingredientAdapter
        }
    }

    private fun registerObserver() {
        with(ingredientViewModel) {
            fetchIngredientDetail(recipeDetailId)
            ingredientDetailLiveData.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressLayout.toVisible()
                    }
                    Status.SUCCESS -> {
                        binding.progressLayout.toGone()
                    }
                    Status.ERROR -> {
                        binding.progressLayout.toGone()
                    }
                }
            }
        }
    }

    private fun onClickItemIngredient(ingredient: Ingredient) {

    }

    companion object {

        fun newInstance(recipeDetailId: Int?) = IngredientDetailFragment(recipeDetailId)
    }
}
