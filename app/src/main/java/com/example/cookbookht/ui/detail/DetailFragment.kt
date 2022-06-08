package com.example.cookbookht.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cookbookht.R
import com.example.cookbookht.databinding.FragmentDetailBinding
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Status
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private val recipeDetailViewModel: RecipeDetailViewModel by sharedViewModel()

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
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        handleEvent()
    }

    private fun handleEvent() {
        imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }

        buttonCook.setOnClickListener {
            findNavController().navigate(R.id.contentDetailFragment)
        }
    }

    private fun setupObserver() {
        with(recipeDetailViewModel) {
            fetchRecipeDetail(args.recipe.id)
            recipeDetailLiveData.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressLayout.toVisible()
                    }
                    Status.SUCCESS -> {
                        binding.progressLayout.toGone()
                        it.data.run {
                            binding.recipeDetail = this
                        }
                    }
                    Status.ERROR -> {
                        binding.progressLayout.toGone()
                    }
                }
            }
        }
    }
}
