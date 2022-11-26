package com.example.cookbookht.ui.detailContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookbookht.R
import com.example.cookbookht.databinding.FragmentContentDetailBinding
import com.example.cookbookht.sharePreference.Preferences
import com.example.cookbookht.ui.detailContent.detailIngredient.IngredientDetailFragment
import com.example.cookbookht.ui.detailContent.detailNutrient.NutrientDetailFragment
import com.example.cookbookht.ui.detailContent.detailStep.StepDetailFragment
import com.example.cookbookht.utils.TypeContentDetail
import com.example.cookbookht.utils.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_content_detail.*
import org.koin.android.ext.android.inject

class ContentDetailFragment : Fragment() {

    private lateinit var binding: FragmentContentDetailBinding
    private val contentDetailAdapter by lazy { ContentDetailAdapter(this) }
    private var recipeDetailId: Int? = null

    private val prefs: Preferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentDetailBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = this@ContentDetailFragment
            }
        arguments?.let {
            recipeDetailId = it.getInt("recipeDetail")
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
                adapter = contentDetailAdapter.apply {
                    addFragments(IngredientDetailFragment.newInstance(recipeDetailId),
                        NutrientDetailFragment.newInstance(recipeDetailId),
                        StepDetailFragment.newInstance(recipeDetailId))
                }
                setPageTransformer(ZoomOutPageTransformer())
            }

            TabLayoutMediator(tabLayoutContentDetail, viewPagerContentDetail) { tab, position ->
                when (position) {
                    TypeContentDetail.INGREDIENT.ordinal -> tab.text = if (prefs.isLanguageVi.get() != "vi") TypeContentDetail.INGREDIENT.nameType else "Thành Phần"
                    TypeContentDetail.NUTRIENT.ordinal -> tab.text = if (prefs.isLanguageVi.get() != "vi") TypeContentDetail.NUTRIENT.nameType else "Dinh Dưỡng"
                    TypeContentDetail.STEP.ordinal -> tab.text =  if (prefs.isLanguageVi.get() != "vi") TypeContentDetail.STEP.nameType else "Các Bước"
                    else -> tab.text = if (prefs.isLanguageVi.get() != "vi") TypeContentDetail.INGREDIENT.nameType else "Thành Phần"
                }
            }.attach()
        }
    }
}
