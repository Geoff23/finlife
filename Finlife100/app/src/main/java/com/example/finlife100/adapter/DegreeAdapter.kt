package com.example.finlife100.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finlife100.GameViewModel
import com.example.finlife100.R
import com.example.finlife100.fragments.EducationFragment
import com.example.finlife100.model.Degree

class DegreeAdapter(
        private val context: EducationFragment,
        private val sharedViewModel: GameViewModel,
        private val dataset: MutableList<Degree>
) : RecyclerView.Adapter<DegreeAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView1: TextView = view.findViewById(R.id.item_title)
        val textView2: TextView = view.findViewById(R.id.item_duration)
        val button: Button = view.findViewById(R.id.button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.degree, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView1.text = item.name
        holder.textView2.text = context.resources.getString(R.string.duration).format(item.duration.toString())
        holder.button.setOnClickListener {
            if (sharedViewModel.pursuingDegree.value!!.isNotEmpty()) {
                sharedViewModel.setDegree("", 0)
            } else {
                sharedViewModel.setDegree(item.name, item.duration * 365)
            }
        }
    }
    fun removeAt(position: Int) {
        dataset.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataset.size)
    }
    override fun getItemCount() = dataset.size
}
