package com.example.finlife100

import android.app.Activity
import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finlife100.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.summary -> {
                val fragment = SummaryFragment()
                supportFragmentManager.beginTransaction().replace(R.id.center, fragment)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.education -> {
                val fragment = EducationFragment()
                supportFragmentManager.beginTransaction().replace(R.id.center, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.career -> {
                val fragment = CareerFragment()
                supportFragmentManager.beginTransaction().replace(R.id.center, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.shopping -> {
                val fragment = ShoppingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.center, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.investing -> {
                val fragment = InvestingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.center, fragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val taskDescription = TaskDescription()
        (this as Activity).setTaskDescription(taskDescription)
    }
}