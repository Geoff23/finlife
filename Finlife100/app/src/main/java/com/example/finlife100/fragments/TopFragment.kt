package com.example.finlife100.fragments
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
import com.example.finlife100.databinding.FragmentTopBinding

class TopFragment : Fragment() {
    private lateinit var binding: FragmentTopBinding
    private val sharedViewModel: GameViewModel by activityViewModels()

    companion object {
        var mainHandler = Handler(Looper.getMainLooper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameViewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onStop() {
        super.onStop()
        mainHandler.removeCallbacksAndMessages(null);
    }

    override fun onResume() {
        super.onResume()
        mainHandler.postDelayed(object : Runnable {
            override fun run() {
                sharedViewModel.update()
                mainHandler.postDelayed(this, 500)
            }
        }, 500)
    }
}