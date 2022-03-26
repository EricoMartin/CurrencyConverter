package com.basebox.ratexchange.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.basebox.ratexchange.R
import com.basebox.ratexchange.databinding.FragmentHomeBinding
import com.basebox.ratexchange.ui.adapters.SpinnerAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var lineList: ArrayList<Entry>
    private lateinit var lineDataSet: LineDataSet
    private lateinit var lineData: LineData
    private lateinit var chart: LineChart

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        chart = root.findViewById(R.id.chart)
        Utils.init(context)
        val toText = binding.textInputLayout

        val spinnerAdapter = SpinnerAdapter(
            requireContext(),
            resources.getStringArray(R.array.currencies)
        )

        homeViewModel.getHistoricalRates()
        spinnerAdapter.setDropDownViewResource(R.layout.drop_down_item)

        binding.spinner.adapter = spinnerAdapter
        binding.spinner2.adapter = spinnerAdapter

        binding.imageView2.setOnClickListener {
            binding.textInputLayout.editText?.text?.clear()
            binding.textInputLayout2.editText?.text?.clear()

            val spinValue = binding.spinner2
                .selectedItem.toString()
            binding.spinner2.setSelection(
                spinnerAdapter.getPosition(
                    binding.spinner
                        .selectedItem.toString()
                )
            )

            binding.spinner.setSelection(
                spinnerAdapter.getPosition(
                    spinValue
                )
            )
        }

        binding.button.setOnClickListener {
            homeViewModel.convert(
                toText.editText?.text.toString(),
                binding.spinner.selectedItem.toString(),
                binding.spinner2.selectedItem.toString()
            )
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.conversion.collect {
                when (it) {
                    is HomeViewModel.RateEvent.Success -> {
                        binding.progressBar.isVisible = false
                        toText.editText?.setText(homeViewModel.baseCurrencyTyped.value)
                        binding.textInputLayout2.editText?.setTextColor(Color.BLUE)
                        binding.textInputLayout2.editText?.setText("= ${it.result}")
                    }
                    is HomeViewModel.RateEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        toText.editText?.setText(homeViewModel.baseCurrencyTyped.value)
                        binding.textInputLayout2.editText?.setTextColor(Color.RED)
                        binding.textInputLayout2.editText?.setText(it.error)
                    }
                    is HomeViewModel.RateEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

        lineList = ArrayList()
        for (i in homeViewModel.timeRateData.value.toString().indices) {
            lineList.add(
                Entry(
                    i.toFloat() + 10f,
                    homeViewModel.timeRateData.value.toString()[i].code.toFloat()
                )
            )
        }

        lineDataSet = LineDataSet(lineList, "Past 30 days")
        lineData = LineData(lineDataSet)
        binding.chart.data = lineData
        chart.setViewPortOffsets(10f, 0f, 10f, 0f)
        chart.setDrawGridBackground(false)
        chart.axisLeft.isEnabled = true
        chart.axisLeft.spaceTop = 40f
        chart.axisLeft.spaceBottom = 40f
        chart.axisRight.isEnabled = false
        chart.axisLeft.axisLineColor = Color.WHITE
        chart.xAxis.axisLineColor = Color.WHITE
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.isEnabled = true
        chart.tooltipText = lineData.getDataSetForEntry(lineList[0]).toString()

        // animate calls invalidate()...
        chart.animateX(2500)
        lineDataSet.color = Color.WHITE
        lineData.setValueTextSize(0f)
        lineDataSet.setDrawIcons(false)
        lineDataSet.setDrawFilled(true)
        lineDataSet.disableDashedLine()
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.setGradientColor(Color.WHITE, Color.BLUE)
        lineDataSet.setDrawValues(false)
        lineDataSet.valueTextColor = Color.WHITE
        lineDataSet.setCircleColor(Color.WHITE)
        lineDataSet.setDrawFilled(true)
        lineDataSet.lineWidth = 0.5f
        lineDataSet.circleRadius = 0f
        lineDataSet.circleHoleRadius = 0f

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}