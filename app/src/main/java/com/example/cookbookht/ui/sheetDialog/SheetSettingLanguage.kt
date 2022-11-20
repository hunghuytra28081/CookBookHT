package com.example.cookbookht.ui.sheetDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.cookbookht.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.sheet_setting_language.*

class SheetSettingLanguage(
    private val deleteClick: (Int) -> Unit
): BottomSheetDialogFragment()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.LsBottomSheetFragmentStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sheet_setting_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerView()
    }
    private fun listenerView() {
        layoutLanguageVI.setOnClickListener {
            deleteClick(0)
            dismiss()
        }

        layoutLanguageEN.setOnClickListener {
            deleteClick(1)
            dismiss()
        }

        cancel.setOnClickListener {
            deleteClick(2)
            dismiss()
        }
    }
}