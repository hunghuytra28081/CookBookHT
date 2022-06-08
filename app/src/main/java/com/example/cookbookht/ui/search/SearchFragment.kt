package com.example.cookbookht.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.databinding.FragmentSearchBinding
import com.example.cookbookht.ui.home.HomeAdapter
import com.example.cookbookht.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private val searchAdapter = HomeAdapter(this::onClickItemHome)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(
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
            lifecycleOwner = this@SearchFragment
            viewModel = searchViewModel
            adapter = searchAdapter
        }
    }

    private fun registerObserver() {
        searchViewModel.resource.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                Status.ERROR -> {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun onClickItemHome(recipe: Recipe) {

    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
