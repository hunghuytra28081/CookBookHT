package com.example.cookbookht.ui.detailContent.detailStep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookbookht.data.model.Step
import com.example.cookbookht.databinding.FragmentStepDetailBinding
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class StepDetailFragment(
    private val stepId: Int?
) : Fragment() {

    private lateinit var binding: FragmentStepDetailBinding
    private val stepAdapter by lazy { StepDetailAdapter(::onClickItemStep) }
    private val stepViewModel by viewModel<StepDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        registerObserver()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@StepDetailFragment
            stepViewModel = this@StepDetailFragment.stepViewModel
            stepAdapter = this@StepDetailFragment.stepAdapter
        }
    }

    private fun registerObserver() {
        with(stepViewModel) {
            fetchStepDetail(stepId)
            stepDetailLiveData.observe(viewLifecycleOwner) {
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

    private fun onClickItemStep(step: Step) {

    }

    companion object {
        fun newInstance(stepId: Int?) = StepDetailFragment(stepId)
    }
}
