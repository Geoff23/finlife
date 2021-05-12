package com.example.finlife100.data

import com.example.finlife100.model.Career

class Careers {

    fun loadCareers() : MutableList<Career>{
        return mutableListOf(
                Career("Engineer", 100000),
                Career("Lawyer", 150000),
                Career("Doctor", 200000)
        )
    }
}