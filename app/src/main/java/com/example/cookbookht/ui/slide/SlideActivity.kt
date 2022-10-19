package com.example.cookbookht.ui.slide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.cookbookht.R
import com.example.cookbookht.data.model.Slide
import com.example.cookbookht.extension.viewInVisible
import com.example.cookbookht.extension.viewVisible
import com.example.cookbookht.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_slide.*

class SlideActivity : AppCompatActivity() {

    private val pagerAdapter by lazy { SlideIntroPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)

        initData()
        initHandles()
    }

    private fun initHandles() {
        tv_next.setOnClickListener {
            view_pager.currentItem++
        }

        btn_next.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tv_skip.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initData() {
        view_pager.adapter = pagerAdapter.apply {
            addSlide(
                Slide(
                        R.drawable.slide1,
                    resources.getString(R.string.slide_1_title),
                    resources.getString(R.string.slide_1_desc)
                )
            )

            addSlide(
                Slide(
                    R.drawable.slide2,
                    resources.getString(R.string.slide_2_title),
                    resources.getString(R.string.slide_2_desc)
                )
            )

            addSlide(
                Slide(
                    R.drawable.slide3,
                    resources.getString(R.string.slide_3_title),
                    resources.getString(R.string.slide_3_desc)
                )
            )
        }
        dots_indicator.setViewPager(view_pager)
        view_pager.apply {

            offscreenPageLimit = 3
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            tv_next.viewVisible()
                            tv_skip.viewVisible()
                            btn_next.viewInVisible()
                        }
                        1 -> {
                            tv_next.viewVisible()
                            tv_skip.viewVisible()
                            btn_next.viewInVisible()
                        }
                        2 -> {
                            tv_next.viewInVisible()
                            tv_skip.viewInVisible()
                            btn_next.viewVisible()
                        }
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

            })
        }
    }
}