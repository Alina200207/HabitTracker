package com.example.habits

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.databinding.FragmentHabitBinding

class HabitCardsAdapter(
    private val habits: ArrayList<HabitInformation>, val activity: Activity
) :
    ListAdapter<HabitInformation, HabitCardsAdapter.ViewHolder>(HabitItemDiffCallback()) {
    inner class ViewHolder(private var binding: FragmentHabitBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(habitCardInformation: HabitInformation) {
            itemView.setOnClickListener(this)
            itemView.isClickable = true
            itemView.isFocusable = true
            binding.habitTitle.text = habitCardInformation.habitTitle
            binding.color.setBackgroundColor(habitCardInformation.habitColor)
            val resources = activity.resources
            binding.description.text = resources.getString(
                R.string.habit_description,
                habitCardInformation.habitDescription
            )
            binding.habitType.text =
                resources.getString(R.string.habit_type, resources.getString(habitCardInformation.habitType.text))
            binding.habitPriority.text = resources.getString(
                R.string.habit_priority,
                resources.getString(habitCardInformation.habitPriority.text)
            )
            binding.habitFrequency.text = resources.getString(
                R.string.habit_frequency,
                habitCardInformation.habitNumberExecution,
                resources.getQuantityString(R.plurals.pluralsForCount,habitCardInformation.habitNumberExecution),
                habitCardInformation.frequency
            )
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            val intent = Intent(activity, HabitAddendum::class.java)
            intent.putExtra(Constants.positionExtraName, position)
            view?.context?.startActivity(intent)
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
    }
}