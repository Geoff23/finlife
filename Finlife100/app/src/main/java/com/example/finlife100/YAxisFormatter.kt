package com.example.finlife100

import com.github.mikephil.charting.formatter.ValueFormatter

class YAxisFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        var string = ""
        if (value >= 1000000000000) {
            string = "$"+String.format("%.2f", value/1000000000000)+"T"
        } else if (value >= 1000000000) {
            string = "$"+String.format("%.2f", value/1000000000)+"B"
        } else if (value >= 1000000) {
            string = "$"+String.format("%.2f", value/1000000)+"M"
        } else if (value >= 1000) {
            string = "$"+String.format("%.2f", value/1000)+"K"
        } else {
            string = "$"+String.format("%.2f", value)
        }
        return string
    }
}