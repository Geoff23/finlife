package com.example.finlife100

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class GameViewModel : ViewModel() {

    private val _turn = MutableLiveData(0)
    val turn: LiveData<Int> = _turn

    private val _netWorth = MutableLiveData(0.0)
    val netWorth: LiveData<Double> = _netWorth

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _age = MutableLiveData(18)
    val age: LiveData<Int> = _age
    var startDate = ""

    private val _money = MutableLiveData(0.0)
    val money: LiveData<Double> = _money

    private val _gems = MutableLiveData(0)
    val gems: LiveData<Int> = _gems

    private val _title = MutableLiveData("Unemployed")
    val title: LiveData<String> = _title

    private val _health = MutableLiveData(100)
    val health: LiveData<Int> = _health

    private val _happiness = MutableLiveData(100)
    val happiness: LiveData<Int> = _happiness

    private val _netWorthHistory = MutableLiveData<MutableList<Double>>()
    val netWorthHistory: MutableLiveData<MutableList<Double>> = _netWorthHistory
    private val netWorthList = mutableListOf(0.0)

    private val _pursuingDegree = MutableLiveData("")
    val pursuingDegree: LiveData<String> = _pursuingDegree
    private val _duration = MutableLiveData(0)
    val duration: LiveData<Int> = _duration
    val degreesList = mutableListOf<String>()

    private val _currentTitle = MutableLiveData("")
    val currentTitle: LiveData<String> = _currentTitle
    var currentSalary = 0

    init {
        val formatter = SimpleDateFormat("M/d/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        _date.value = formatter.format(calendar.time)
        startDate = date.value!!
        startDate.reversed().substring(0, 5).reversed()
        _netWorthHistory.value = netWorthList
    }

    private fun incrementDate() {
        val formatter = SimpleDateFormat("M/d/yyyy", Locale.getDefault())
        val date = formatter.parse(_date.value!!)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, 1)
        _date.value = formatter.format(calendar.time)
    }
    fun setDegree(degree:String, duration:Int) {
        _pursuingDegree.value = degree
        _duration.value = duration
    }
    fun setCareer(career:String, salary:Int) {
        _currentTitle.value = career
        currentSalary = salary
    }
    fun update() {
        _turn.value = _turn.value?.inc()
        incrementDate()

        if (_turn.value!!%5==0) {
            _money.value = _money.value?.plus(currentSalary/12)
            _netWorth.value = _money.value
        }

        _netWorth.value?.let { netWorthList.add(it) }
        _netWorthHistory.value = netWorthList

        if (_date.value!!.reversed().substring(0, 5).reversed() == startDate) {
            _age.value = _age.value?.inc()
        }
        if (_pursuingDegree.value!!.isNotEmpty()) {
            _duration.value = _duration.value?.dec()
            if (_duration.value == 0) {
                degreesList.add(_pursuingDegree.value!!)
                _pursuingDegree.value = ""
            }
        }
    }
    fun multiplyNetWorth(x: Double) {
        _netWorth.value = _netWorth.value?.times(x)
    }
    fun format(value: Double) : String {
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