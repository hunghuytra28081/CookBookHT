package com.example.cookbookht.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookbookht.R
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.databinding.FragmentFavoriteBinding
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val favoriteViewModel by viewModel<FavoriteViewModel>()
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteAdapter = FavoriteAdapter(this::onClickItemFavorite)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        setupObserver()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@FavoriteFragment
            viewModel = this@FavoriteFragment.favoriteViewModel
            favoriteAdapter = this@FavoriteFragment.favoriteAdapter
        }
    }

    private fun setupObserver() {
        with(favoriteViewModel) {
            getFavorite()
            favoriteLiveData.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressLayout.toVisible()
                    }
                    Status.SUCCESS -> {
                        favoriteAdapter.submitList(it.data?.toMutableList())
                        binding.progressLayout.toGone()
                    }
                    Status.ERROR -> {
                        binding.progressLayout.toGone()
                    }
                }

                binding.layoutEmpty.isVisible = it.data.isNullOrEmpty()
            }
        }
    }

    private fun onClickItemFavorite(favorite: Favorite) {
        val bundle = bundleOf("recipe" to favorite.id)
        findNavController().navigate(R.id.detailFragment, bundle)
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}
