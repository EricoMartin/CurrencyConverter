package com.basebox.ratexchange.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.basebox.ratexchange.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var charts: LineChart

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

        charts = LineChart(requireContext())
        charts = binding.graph.chart
        val toText = binding.textInputLayout

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
                        toText.editText?.setTextColor(Color.BLUE)
                        toText.editText?.text ?: it.result
                    }
                    is HomeViewModel.RateEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        toText.editText?.setTextColor(Color.RED)
                        binding.textInputLayout2.editText?.text ?: it.error
                    }
                    is HomeViewModel.RateEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data: LineData = getData(36, 100.toFloat())!!
    }

    private fun getData(count: Int, range: Float): LineData? {
        val values: ArrayList<Entry> = ArrayList()
        for (i in 0 until count) {
            val val1 = (Math.random() * range).toFloat() + 3
            values.add(Entry(i.toFloat(), val1))
        }

        // create a dataset and give it a type
        val set1 = LineDataSet(values, "DataSet 1")
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        set1.lineWidth = 1.75f
        set1.circleRadius = 5f
        set1.circleHoleRadius = 2.5f
        set1.color = Color.WHITE
        set1.setCircleColor(Color.WHITE)
        set1.highLightColor = Color.WHITE
        set1.setDrawValues(false)

        // create a data object with the data sets
        return LineData(set1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}