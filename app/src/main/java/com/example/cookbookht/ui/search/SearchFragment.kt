package com.example.cookbookht.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookbookht.R
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.data.repository.source.local.History
import com.example.cookbookht.databinding.FragmentSearchBinding
import com.example.cookbookht.ui.home.HomeAdapter
import com.example.cookbookht.utils.Constant
import com.example.cookbookht.utils.Status
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private val searchAdapter = HomeAdapter(this::onClickItemHome)
    private val historyAdapter by lazy {
        HistoryAdapter(requireContext()) {
            onClickItemHistory(it)
        }
    }

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
        initData()
        initBinding()
        registerObserver()
        initListener()
    }

    private fun initData() {
        recyclerViewHistory.layoutManager = FlexboxLayoutManager(requireContext())
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@SearchFragment
            viewModel = this@SearchFragment.searchViewModel
            searchAdapter = this@SearchFragment.searchAdapter
            historyAdapter = this@SearchFragment.historyAdapter

        }
    }

    private fun registerObserver() {
        searchViewModel.searchRecipe(Constant.DEFAULT_STRING)
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

        searchViewModel.getAllHistory()
        searchViewModel.history.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                }
                Status.ERROR -> {

                }
            }
        }
    }

    private fun initListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.querySearch(it)
                    if (searchViewModel.checkIsExistHistory(it)) {
                        searchViewModel.insertHistory(History(history = it))
                    } else {
                        searchViewModel.deleteHistory(it)
                        searchViewModel.insertHistory(History(history = it))
                    }
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun onClickItemHome(recipe: Recipe) {
        val bundle = bundleOf("recipe" to recipe.id)
        findNavController().navigate(R.id.detailFragment, bundle)

    }

    private fun onClickItemHistory(history: History) {
        searchView.setQuery(history.history, false)
        searchViewModel.searchRecipe(history.history)
        binding.searchView.clearFocus()
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
