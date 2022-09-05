package com.example.cookbookht.ui.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import com.example.cookbookht.R
import com.example.cookbookht.databinding.ActivityMainBinding
import com.example.cookbookht.utils.Constant.DELAY_TIME

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var isDoubleBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        currentFocus?.let {
            if (it is SearchView.SearchAutoComplete) {
                val viewRect = Rect()
                it.getGlobalVisibleRect(viewRect)
                if (!viewRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    it.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /*override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else if (!isDoubleBackPressed) {
            isDoubleBackPressed = true
            Toast.makeText(this, R.string.back_again, Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({ isDoubleBackPressed = false }, DELAY_TIME)
            return
        } else {
            super.onBackPressed()
            return
        }
    }*/
}
