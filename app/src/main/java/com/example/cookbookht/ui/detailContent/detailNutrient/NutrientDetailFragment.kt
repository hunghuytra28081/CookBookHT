package com.example.cookbookht.ui.detailContent.detailNutrient

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.cookbookht.R
import com.example.cookbookht.data.model.Nutrient
import com.example.cookbookht.databinding.FragmentNutrientDetailBinding
import com.example.cookbookht.extension.chart.nutrientToGam
import com.example.cookbookht.extension.toGone
import com.example.cookbookht.extension.toVisible
import com.example.cookbookht.utils.Status
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.fragment_nutrient_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NutrientDetailFragment(
    private val nutrientId: Int?
) : Fragment() {

    private lateinit var tfRegular: Typeface
    private lateinit var tfLight: Typeface

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
        initData()
        initChart()
        registerObserver()
        initListener()
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = this@NutrientDetailFragment
            nutrientViewModel = this@NutrientDetailFragment.nutrientViewModel
            nutrientAdapter = this@NutrientDetailFragment.nutrientAdapter
        }
    }

    private fun initData() {
        tfRegular = ResourcesCompat.getFont(requireContext(), R.font.open_sans_regular)!!
        tfLight = ResourcesCompat.getFont(requireContext(), R.font.open_sans_light)!!
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
                        it.data?.value.apply {
                            this@apply?.let { data -> setDataChart(data) }
                        }
                    }
                    Status.ERROR -> {
                        binding.progressLayout.toGone()
                    }
                }
            }
        }
    }

    private fun initChart() {
        binding.apply {
            chartNutrient.setUsePercentValues(true)
            chartNutrient.description.isEnabled = false
            chartNutrient.setExtraOffsets(5f, 10f, 5f, 5f)

            chartNutrient.dragDecelerationFrictionCoef = 0.95f

            chartNutrient.setCenterTextTypeface(tfLight)
            chartNutrient.centerText = generateCenterSpannableText()

            chartNutrient.isDrawHoleEnabled = true
            chartNutrient.setHoleColor(Color.WHITE)

            chartNutrient.setTransparentCircleColor(Color.WHITE)
            chartNutrient.setTransparentCircleAlpha(110)

            chartNutrient.holeRadius = 58f
            chartNutrient.transparentCircleRadius = 61f

            chartNutrient.setDrawCenterText(true)

            chartNutrient.rotationAngle = 0f
            // enable rotation of the chart by touch
            // enable rotation of the chart by touch
            chartNutrient.isRotationEnabled = true
            chartNutrient.isHighlightPerTapEnabled = true

            // setUnit(" €");
            // setDrawUnitsInChart(true);

            // add a selection listener

            // setUnit(" €");
            // setDrawUnitsInChart(true);

            // add a selection listener
            chartNutrient.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    TODO("Not yet implemented")
                }

                override fun onNothingSelected() {
                    TODO("Not yet implemented")
                }

            })

            chartNutrient.animateY(1400, Easing.EaseInOutQuad)
            // chart.spin(2000, 0, 360);
            val legend: Legend = chartNutrient.legend
            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                xEntrySpace = 7f
                yEntrySpace = 0f
                yOffset = 0f
            }

            // entry label styling

            // entry label styling
            chartNutrient.setEntryLabelColor(Color.WHITE)
            chartNutrient.setEntryLabelTypeface(tfRegular)
            chartNutrient.setEntryLabelTextSize(12f)
        }
    }

    private fun setDataChart(listData: MutableList<Nutrient>) {
        val entries = ArrayList<PieEntry>()
        for (i in listData) {
            entries.add(
                PieEntry(
                    nutrientToGam(i.unit.toString(), i.amount ?: 0.0),
                    i.name,
                    resources.getDrawable(R.drawable.star)
                )
            )
        }

        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f

        // add a lot of colors


        // add a lot of colors
        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(tfLight)
        chartNutrient.data = data

        // undo all highlights

        // undo all highlights
        chartNutrient.highlightValues(null)

        for (set in chartNutrient.data.dataSets) set.setDrawValues(false)

        chartNutrient.setDrawEntryLabels(false)

        chartNutrient.invalidate()
    }

    private fun onClickItemNutrient(nutrient: Nutrient) {

    }

    private fun initListener() {
        imgOpenChart.setOnClickListener {
            layoutChart.animate().translationY(0F).duration = 500
        }

        imgClose.setOnClickListener {
            layoutChart.animate().translationY(3000F).duration = 500
        }
    }

    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("HT-Cook")
        s.setSpan(RelativeSizeSpan(1.7f), 0, s.length, 0)
        s.setSpan(ForegroundColorSpan(resources.getColor(R.color.color_purple_dark)), 3, 7, 0)
        s.setSpan(StyleSpan(Typeface.BOLD), 0, s.length, 0)
        return s
    }

    companion object {
        fun newInstance(nutrientId: Int?) = NutrientDetailFragment(nutrientId)
    }
}
