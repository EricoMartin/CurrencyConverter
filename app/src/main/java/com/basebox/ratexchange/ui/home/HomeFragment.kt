package com.basebox.ratexchange.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.basebox.ratexchange.R
import com.basebox.ratexchange.databinding.FragmentHomeBinding
import com.basebox.ratexchange.ui.adapters.SpinnerAdapter
import com.github.mikephil.charting.charts.LineChart
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
        val arrayAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.drop_down_item,
            R.id.textView2,
            resources.getStringArray(R.array.currencies)
        )

//        binding.spinner.adapter = arrayAdapter
//        binding.spinner2.adapter = arrayAdapter
        val spinnerAdapter: SpinnerAdapter = SpinnerAdapter(
            requireContext(),
            resources.getStringArray(R.array.currencies)
        )
        spinnerAdapter.setDropDownViewResource(R.layout.drop_down_item)

        binding.spinner.adapter = spinnerAdapter
        binding.spinner2.adapter = spinnerAdapter


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
        lineList.add(Entry(10f, 460f))
        lineList.add(Entry(20f, 540f))
        lineList.add(Entry(30f, 600f))
        lineList.add(Entry(40f, 550f))
        lineList.add(Entry(50f, 1000f))
        lineList.add(Entry(60f, 1100f))
        lineList.add(Entry(70f, 950f))
        lineList.add(Entry(80f, 810f))
        lineList.add(Entry(90f, 640f))
        lineList.add(Entry(100f, 400f))

        lineDataSet = LineDataSet(lineList, "Past 30 days")
        lineData = LineData(lineDataSet)
        binding.chart.data = lineData
        lineDataSet.valueTextSize = 0f
        lineDataSet.color = Color.WHITE
        lineDataSet.valueTextColor = Color.WHITE
        lineDataSet.setCircleColor(Color.WHITE)
        lineDataSet.setDrawFilled(true)
        lineDataSet.lineWidth = 1.0f
        lineDataSet.circleRadius = 0.5f
        lineDataSet.circleHoleRadius = 0.5f
        lineDataSet.highLightColor = resources.getColor(R.color.new_green)

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}