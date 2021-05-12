package com.example.finlife100.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finlife100.GameViewModel
import com.example.finlife100.R
import com.example.finlife100.databinding.FragmentInvestingBinding


class InvestingFragment : Fragment() {
    private lateinit var binding: FragmentInvestingBinding
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_investing, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.multiply.setOnClickListener { sharedViewModel.multiplyNetWorth(1.1) }
        binding.gameViewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}