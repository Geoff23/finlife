package com.example.finlife100.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finlife100.GameViewModel
import com.example.finlife100.R
import com.example.finlife100.XAxisFormatter
import com.example.finlife100.YAxisFormatter
import com.example.finlife100.databinding.FragmentSummaryBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class SummaryFragment : Fragment() {
    private lateinit var binding: FragmentSummaryBinding
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, 1000)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameViewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        var entries = mutableListOf<Entry>()
        entries.add(Entry(0f, sharedViewModel.netWorthHistory.value!![0].toFloat()))

        val dataSet = LineDataSet(entries, "Label")
        dataSet.setDrawCircles(false)
        dataSet.color = Color.rgb(0, 255, 128)
        dataSet.lineWidth = 1f

        val lineData = LineData(dataSet)
        binding.chart.data = lineData
        binding.chart.setTouchEnabled(false)
        binding.chart.legend.isEnabled = false

        binding.chart.axisRight.isEnabled = false
        var xAxis = binding.chart.xAxis
        xAxis.textColor = Color.rgb(200,200,200)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.axisLineColor = Color.rgb(100,100,100)
        xAxis.gridColor = Color.rgb(100,100,100)
        xAxis.granularity = 1f
        xAxis.labelCount = 4
        xAxis.valueFormatter = XAxisFormatter()

        var yAxis = binding.chart.axisLeft;
        yAxis.textColor = Color.rgb(200,200,200)
        yAxis.gridColor = Color.rgb(100,100,100)
        yAxis.axisLineColor = Color.rgb(100,100,100)
        yAxis.valueFormatter = YAxisFormatter()
        yAxis.setDrawZeroLine(true)
        yAxis.zeroLineWidth = 1f

        binding.chart.description.isEnabled = false
        binding.chart.setMaxVisibleValueCount(0)
        binding.chart.invalidate()

        sharedViewModel.netWorthHistory.observe(viewLifecycleOwner, {
            binding.chart.data.removeDataSet(0)
            var entries = mutableListOf<Entry>()
            var i = 0
            sharedViewModel.netWorthHistory.value?.forEach {
                entries.add(Entry(i.toFloat(), it.toFloat()))
                i = i.inc()
            }
            val dataSet = LineDataSet(entries, "Label")
            dataSet.setDrawCircles(false)
            dataSet.color = Color.rgb(0, 255, 128)
            dataSet.lineWidth = 1f
            val lineData = LineData(dataSet)
            binding.chart.data = lineData

            lineData.notifyDataChanged()
            binding.chart.notifyDataSetChanged()
            binding.chart.invalidate()
        })
    }
}