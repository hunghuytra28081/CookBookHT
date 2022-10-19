package com.example.cookbookht.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
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

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private var recipeId: Int? = null
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

        buttonCook.setOnClickListener {
            val bundle = bundleOf("recipeDetail" to recipeId)
            findNavController().navigate(R.id.contentDetailFragment, bundle)
        }
    }

    private fun setupObserver() {
        with(recipeDetailViewModel) {
            fetchRecipeDetail(recipeId)
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
