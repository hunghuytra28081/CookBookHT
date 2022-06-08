package com.example.cookbookht.ui.detailContent

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookbookht.ui.detailContent.detailIngredient.IngredientDetailFragment
import com.example.cookbookht.ui.detailContent.detailNutrient.NutrientDetailFragment
import com.example.cookbookht.ui.detailContent.detailStep.StepDetailFragment
import com.example.cookbookht.utils.TypeContentDetail

class ContentDetailAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TypeContentDetail.INGREDIENT.ordinal -> IngredientDetailFragment.newInstance()
            TypeContentDetail.NUTRIENT.ordinal -> NutrientDetailFragment.newInstance()
            TypeContentDetail.STEP.ordinal -> StepDetailFragment.newInstance()
            else -> IngredientDetailFragment.newInstance()
        }
    }
}
