
package com.example.cookbookht.ui.detailContent.detailStep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookbookht.R

class StepDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step_detail, container, false)
    }

    companion object{
        fun newInstance() = StepDetailFragment()
    }
}
