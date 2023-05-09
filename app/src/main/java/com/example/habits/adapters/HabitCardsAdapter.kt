package com.example.habits.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.entities.HabitInformation
import com.example.habits.HabitItemDiffCallback
import com.example.habits.R
import com.example.habits.databinding.FragmentHabitBinding

class HabitCardsAdapter(
    private val habits: List<HabitInformation>, val fragment: Fragment,
    private val onClickListener: OnClickListener

) :
    ListAdapter<HabitInformation, HabitCardsAdapter.ViewHolder>(HabitItemDiffCallback()) {
    inner class ViewHolder(private var binding: FragmentHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(habitCardInformation: HabitInformation) {
            itemView.isClickable = true
            itemView.isFocusable = true
            binding.habitTitle.text = habitCardInformation.habitTitle
            binding.color.setBackgroundColor(habitCardInformation.habitColor)
            val resources = fragment.resources
            binding.description.text = resources.getString(
                R.string.habit_description,
                habitCardInformation.habitDescription
            )
            binding.habitType.text =
                resources.getString(
                    R.string.habit_type,
                    resources.getString(habitCardInformation.habitType.text)
                )
            binding.habitPriority.text = resources.getString(
                R.string.habit_priority,
                resources.getString(habitCardInformation.habitPriority.text)
            )
            binding.habitFrequency.text = resources.getString(
                R.string.habit_frequency,
                habitCardInformation.habitNumberExecution,
                resources.getQuantityString(
                    R.plurals.pluralsForCount,
                    habitCardInformation.habitNumberExecution
                ),
                habitCardInformation.frequency,
                resources.getQuantityString(
                    R.plurals.pluralsForDays,
                    habitCardInformation.frequency
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentHabitBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = habits[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item.id)
        }
    }

    class OnClickListener(val clickListener: (id: Long) -> Unit) {
        fun onClick(id: Long) = clickListener(id)
    }
}