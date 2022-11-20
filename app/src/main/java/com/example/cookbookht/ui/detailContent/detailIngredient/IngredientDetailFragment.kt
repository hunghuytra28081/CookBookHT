package com.example.cookbookht.ui.detailContent.detailIngredient

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cookbookht.R
import com.example.cookbookht.binding.loadImage
import com.example.cookbookht.data.model.Ingredient
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.databinding.FragmentIngredientDetailBinding
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Constant
import com.example.cookbookht.utils.Status
import kotlinx.android.synthetic.main.fragment_ingredient_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientDetailFragment(
    private val recipeDetailId: Int?
) : Fragment() {

    private lateinit var binding: FragmentIngredientDetailBinding
    private val ingredientViewModel by viewModel<IngredientDetailViewModel>()
    private val ingredientAdapter = IngredientDetailAdapter(::onClickItemIngredient)
    private val searchIngredientAdapter = SearchIngredientAdapter(::onClickItemSearchIngredient)

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
        initListener()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@IngredientDetailFragment
            ingredientViewModel = this@IngredientDetailFragment.ingredientViewModel
            ingredientAdapter = this@IngredientDetailFragment.ingredientAdapter
            searchIngredientAdapter = this@IngredientDetailFragment.searchIngredientAdapter
        }
    }

    private fun registerObserver() {
        with(ingredientViewModel) {
            fetchIngredientDetail(recipeDetailId)
            ingredientDetailLiveData.observe(viewLifecycleOwner) {
                Log.d("Main12345", "registerObserver: ${it.data?.value?.size}")
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

            resource.observe(viewLifecycleOwner) {
                binding.layoutEmptySearchIngredient.isVisible = it.data?.value.isNullOrEmpty()
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressLayoutContent.toVisible()
                    }
                    Status.SUCCESS -> {
                        binding.progressLayoutContent.toGone()
                    }
                    Status.ERROR -> {
                        binding.progressLayoutContent.toGone()
                    }
                }
            }
        }
    }

    private fun initListener() {
        closeSearchIngredient.setOnClickListener {
            layoutSearchIngredient.animate().translationY(-2500f).duration = 500
            ingredientViewModel.onClearData()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.progressLayoutContent.toVisible()
            },500)
        }
    }

    private fun onClickItemIngredient(ingredient: Ingredient) {
        binding.imageIngredient.loadImage(Constant.BASE_URL_IMAGE_INGREDIENT + ingredient.image)
        titleIngredient.text = ingredient.name

        val textSearch = ingredient.name?.replace(" ","%2C")
        textSearch?.let {
            ingredientViewModel.fetchSearchIngredient(it)
        }
        layoutSearchIngredient.animate().translationY(0f).duration = 500
    }

    private fun onClickItemSearchIngredient(recipe: Recipe) {
        val bundle = bundleOf("recipe" to recipe.id)
        findNavController().popBackStack()
        findNavController().navigate(R.id.detailFragment, bundle)
    }

    companion object {
        fun newInstance(recipeDetailId: Int?) = IngredientDetailFragment(recipeDetailId)
    }
}
