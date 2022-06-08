package com.example.cookbookht.ui.detailContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookbookht.databinding.FragmentContentDetailBinding
import com.example.cookbookht.ui.detailContent.detailIngredient.IngredientDetailFragment
import com.example.cookbookht.ui.detailContent.detailNutrient.NutrientDetailFragment
import com.example.cookbookht.ui.detailContent.detailStep.StepDetailFragment
import com.example.cookbookht.utils.TypeContentDetail
import com.example.cookbookht.utils.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_content_detail.*

class ContentDetailFragment : Fragment() {

    private lateinit var binding: FragmentContentDetailBinding
    private val contentDetailAdapter by lazy { ContentDetailAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentDetailBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@ContentDetailFragment
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTab()
        eventHandle()
    }

    private fun eventHandle() {
        binding.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initTab() {
        binding.run {
            viewPagerContentDetail.apply {
                adapter = contentDetailAdapter
                setPageTransformer(ZoomOutPageTransformer())
            }

            TabLayoutMediator(tabLayoutContentDetail, viewPagerContentDetail) { tab, position ->
                when (position) {
                    TypeContentDetail.INGREDIENT.ordinal -> tab.text = TypeContentDetail.INGREDIENT.nameType
                    TypeContentDetail.NUTRIENT.ordinal -> tab.text = TypeContentDetail.NUTRIENT.nameType
                    TypeContentDetail.STEP.ordinal -> tab.text = TypeContentDetail.STEP.nameType
                    else -> tab.text = TypeContentDetail.INGREDIENT.nameType
                }
            }.attach()
        }
    }
}
