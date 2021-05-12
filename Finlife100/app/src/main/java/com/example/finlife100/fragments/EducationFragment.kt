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
import com.example.finlife100.adapter.DegreeAdapter
import com.example.finlife100.data.Degrees
import com.example.finlife100.databinding.FragmentEducationBinding
import kotlin.math.roundToInt


class EducationFragment : Fragment() {
    private lateinit var binding: FragmentEducationBinding
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_education, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameViewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val degrees = Degrees().loadDegrees()
        val iter = degrees.iterator()
        while (iter.hasNext()) {
            val degree = iter.next()
            if (degree.name in sharedViewModel.degreesList) {
                iter.remove()
            }
        }
        val recyclerView = binding.recyclerView

        val newAdapter = DegreeAdapter(this, sharedViewModel, degrees)

        recyclerView.adapter = newAdapter
        sharedViewModel.pursuingDegree.observe(viewLifecycleOwner, {
            Log.d("Here", "1")
            if (sharedViewModel.pursuingDegree.value!!.isEmpty()) {
                Log.d("Here", "A")
                val finalPosition = recyclerView.childCount - 1
                loop@ for (i in 0..finalPosition) {
                    val item = recyclerView.findViewHolderForAdapterPosition(i)!!.itemView
                    val textView1 = item.findViewById<TextView>(R.id.item_title)
                    var duration = 0
                    for (degree in sharedViewModel.degreesList) {
                        if (degree == textView1.text) {
                            newAdapter.removeAt(i)
                            break@loop
                        }
                    }
                    for (degree in degrees) {
                        if (degree.name == textView1.text)
                            duration = degree.duration
                    }
                    val textView2 = item.findViewById<TextView>(R.id.item_duration)
                    textView2.text = requireContext().resources.getString(R.string.duration).format(duration)
                    val progressBar = item.findViewById<ProgressBar>(R.id.item_progress)
                    progressBar.progress = 0
                    if (sharedViewModel.duration.hasObservers()) {
                        sharedViewModel.duration.removeObservers(viewLifecycleOwner)
                    }
                    val button = item.findViewById<Button>(R.id.button)
                    button.text = "Start"
                    button.setBackgroundColor(Color.rgb(0, 255, 128))
                    button.isEnabled = true
                }
            } else {
                Log.d("Here", "B1")
                recyclerView.viewTreeObserver.addOnPreDrawListener(
                        object : ViewTreeObserver.OnPreDrawListener {
                            override fun onPreDraw(): Boolean {
                                Log.d("Here", "Listener" + recyclerView.childCount.toString())
                                val finalPosition = recyclerView.childCount - 1
                                for (i in 0..finalPosition) {
                                    val item = recyclerView.findViewHolderForAdapterPosition(i)!!.itemView
                                    val textView1 = item.findViewById<TextView>(R.id.item_title)
                                    val textView2 = item.findViewById<TextView>(R.id.item_duration)
                                    val progressBar = item.findViewById<ProgressBar>(R.id.item_progress)
                                    val button = item.findViewById<Button>(R.id.button)
                                    var duration = 0
                                    Log.d("Here", "BA")
                                    if (textView1.text == sharedViewModel.pursuingDegree.value!!) {
                                        Log.d("Here", "BB")
                                        for (degree in degrees) {
                                            if (degree.name == sharedViewModel.pursuingDegree.value!!)
                                                duration = degree.duration
                                        }
                                        Log.d("Here", "BC")
                                        sharedViewModel.duration.observe(viewLifecycleOwner, {
                                            Log.d("Here", "B2")
                                            textView2.text = requireContext().resources.getString(R.string.duration2).format(sharedViewModel.duration.value)
                                            var percentCompleted = 0.0
                                            percentCompleted = (1 - sharedViewModel.duration.value!!.toDouble() / (duration * 365)) * 100
                                            progressBar.progress = percentCompleted.roundToInt()
                                        })
                                        button.text = "Cancel"
                                        button.setBackgroundColor(Color.rgb(255, 0, 128))

                                    } else {
                                        button.isEnabled = false
                                        button.setBackgroundColor(Color.rgb(100, 100, 100))
                                    }
                                }
                                recyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                                return true
                            }
                        })
                Log.d("Here", "Normal" + recyclerView.childCount.toString())

            }
        })
    }
}