package com.example.habits

import androidx.recyclerview.widget.DiffUtil
import com.example.habits.entities.HabitInformation

class HabitItemDiffCallback : DiffUtil.ItemCallback<HabitInformation>() {
    override fun areItemsTheSame(oldItem: HabitInformation, newItem: HabitInformation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HabitInformation, newItem: HabitInformation): Boolean {
        return oldItem == newItem
    }
}