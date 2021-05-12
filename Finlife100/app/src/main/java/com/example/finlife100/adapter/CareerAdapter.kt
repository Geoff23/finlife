package com.example.finlife100.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finlife100.GameViewModel
import com.example.finlife100.R
import com.example.finlife100.fragments.CareerFragment
import com.example.finlife100.fragments.EducationFragment
import com.example.finlife100.model.Career
import com.example.finlife100.model.Degree
import java.text.NumberFormat

class CareerAdapter(
    private val context: CareerFragment,
    private val sharedViewModel: GameViewModel,
    private val dataset: MutableList<Career>
) : RecyclerView.Adapter<CareerAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView1: TextView = view.findViewById(R.id.item_title)
        val textView2: TextView = view.findViewById(R.id.item_salary)
        val button: Button = view.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.career, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView1.text = item.name
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 0
        holder.textView2.text = formatter.format(item.salary)
        holder.button.setOnClickListener {
            if (!sharedViewModel.currentTitle.value!!.isEmpty()) {
                sharedViewModel.setCareer("", 0)
            } else {
                sharedViewModel.setCareer(item.name, item.salary)
            }
        }
    }
    override fun getItemCount() = dataset.size
}
