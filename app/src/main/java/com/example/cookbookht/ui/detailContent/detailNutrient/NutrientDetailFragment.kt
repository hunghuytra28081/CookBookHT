package com.example.cookbookht.ui.detailContent.detailNutrient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookbookht.data.model.Nutrient
import com.example.cookbookht.databinding.FragmentNutrientDetailBinding
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class NutrientDetailFragment(
    private val nutrientId: Int?
) : Fragment() {

    private lateinit var binding: FragmentNutrientDetailBinding
    private val nutrientViewModel by viewModel<NutrientDetailViewModel>()
    private val nutrientAdapter by lazy { NutrientDetailAdapter(::onClickItemNutrient) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutrientDetailBinding.inflate(
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
            lifecycleOwner = this@NutrientDetailFragment
            nutrientViewModel = this@NutrientDetailFragment.nutrientViewModel
            nutrientAdapter = this@NutrientDetailFragment.nutrientAdapter
        }
    }

    private fun registerObserver() {
        with(nutrientViewModel) {
            fetchNutrientDetail(nutrientId)
            nutrientDetailLiveData.observe(viewLifecycleOwner) {
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

    private fun onClickItemNutrient(nutrient: Nutrient) {

    }

    companion object {
        fun newInstance(nutrientId: Int?) = NutrientDetailFragment(nutrientId)
    }
}
