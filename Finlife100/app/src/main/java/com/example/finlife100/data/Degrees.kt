package com.example.finlife100.data

import com.example.finlife100.R
import com.example.finlife100.model.Degree

class Degrees {

    fun loadDegrees() : MutableList<Degree> {
        return mutableListOf(
                Degree("Engineering", 1, "Undergraduate"),
                Degree("Law", 3, "Graduate"),
                Degree("Medicine", 4, "Graduate")
        )
    }
}