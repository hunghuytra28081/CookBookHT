package com.example.cookbookht.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookbookht.R
import com.example.cookbookht.data.model.RecipeDetail
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.databinding.FragmentDetailBinding
import com.example.cookbookht.extension.clickWithDebounce
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Status
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {

    private var isFavorite = false
    private lateinit var binding: FragmentDetailBinding
    private var recipeId: Int? = null
    private val recipeDetailViewModel: RecipeDetailViewModel by sharedViewModel()
    private var favorite: Favorite? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        ).apply {
            lifecycleOwner = this@DetailFragment
            arguments?.let {
                recipeId = it.getInt("recipe")
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setupObserver()
        handleEvent()
    }

    private fun setUpView() {
        binding.progressLayout.toVisible()
    }

    private fun handleEvent() {
        imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }

        buttonCook.clickWithDebounce {
            val bundle = bundleOf("recipeDetail" to recipeId)
            findNavController().navigate(R.id.contentDetailFragment, bundle)
        }

        binding.layoutFavorite.setOnClickListener {
            if (!isFavorite) {
                favorite?.let { imageLocalData ->
                    recipeDetailViewModel.insertFavorite(imageLocalData)
                }
            } else {
                favorite?.let { imageLocalData ->
                    recipeDetailViewModel.deleteFavorite(imageLocalData)
                }
            }
        }
    }

    private fun setupObserver() {
        with(recipeDetailViewModel) {
            fetchRecipeDetail(recipeId)
            alreadyFavorite(recipeId)
            recipeDetailLiveData.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressLayout.toVisible()
                    }
                    Status.SUCCESS -> {
                        binding.progressLayout.toGone()
                        it.data.run {
                            binding.recipeDetail = this
                            insertData(this)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressLayout.toGone()
                    }
                }
            }

            isFavorite.observe(viewLifecycleOwner) {
                it.data?.let { isFavoriteData ->
                    this@DetailFragment.isFavorite = isFavoriteData
                }
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        if (it.data == true) {
                            binding.imageFavorite.setImageResource(R.drawable.ic_favorite)
                        } else {
                            binding.imageFavorite.setImageResource(R.drawable.ic_un_favorite)
                        }
                    }
                    Status.ERROR -> {
                    }
                }
            }
        }
    }

    private fun insertData(data: RecipeDetail?) {
        data?.apply {
            id.let {
                favorite = Favorite(
                    id = it,
                    title = title,
                    image = image
                )
            }
        }
    }
}
