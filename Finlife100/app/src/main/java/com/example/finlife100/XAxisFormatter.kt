package com.example.finlife100
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class XAxisFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val formatter = SimpleDateFormat("M/d/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, value.toInt())
        return formatter.format(calendar.time)
    }
}