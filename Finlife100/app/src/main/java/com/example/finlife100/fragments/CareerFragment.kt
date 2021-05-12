package com.example.finlife100.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finlife100.GameViewModel
import com.example.finlife100.R
import com.example.finlife100.adapter.CareerAdapter
import com.example.finlife100.adapter.DegreeAdapter
import com.example.finlife100.data.Careers
import com.example.finlife100.data.Degrees
import com.example.finlife100.databinding.FragmentCareerBinding
import kotlin.math.roundToInt

class CareerFragment : Fragment() {
    private lateinit var binding: FragmentCareerBinding
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_career, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameViewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val recyclerView = binding.recyclerView
        val careers = Careers().loadCareers()
        val newAdapter = CareerAdapter(this, sharedViewModel, careers)

        recyclerView.adapter = newAdapter
        sharedViewModel.currentTitle.observe(viewLifecycleOwner, {
            if (sharedViewModel.currentTitle.value!!.isEmpty()) {
                val finalPosition = recyclerView.childCount - 1
                for (i in 0..finalPosition) {
                    val item = recyclerView.findViewHolderForAdapterPosition(i)!!.itemView
                    if (sharedViewModel.duration.hasObservers()) {
                        sharedViewModel.duration.removeObservers(viewLifecycleOwner)
                    }
                    val button = item.findViewById<Button>(R.id.button)
                    button.text = "Start"
                    button.setBackgroundColor(Color.rgb(0, 255, 128))
                    button.isEnabled = true
                }
            } else {
                recyclerView.viewTreeObserver.addOnPreDrawListener(
                        object : ViewTreeObserver.OnPreDrawListener {
                            override fun onPreDraw(): Boolean {
                                val finalPosition = recyclerView.childCount - 1
                                for (i in 0..finalPosition) {
                                    val item = recyclerView.findViewHolderForAdapterPosition(i)!!.itemView
                                    val textView1 = item.findViewById<TextView>(R.id.item_title)
                                    val textView2 = item.findViewById<TextView>(R.id.item_salary)
                                    val button = item.findViewById<Button>(R.id.button)
                                    if (textView1.text == sharedViewModel.currentTitle.value!!) {
                                        button.text = "Quit"
                                        button.setBackgroundColor(Color.rgb(255, 0, 128))
                                    } else {
                                        button.isEnabled = false
                                        button.setBackgroundColor(Color.rgb(100, 100, 100))
                                    }
                                }
                                recyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                                return true
                            }
                        }
                )
            }
        })
    }
}