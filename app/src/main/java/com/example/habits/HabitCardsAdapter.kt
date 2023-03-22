package com.example.habits

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.databinding.FragmentHabitBinding

class HabitCardsAdapter(
    private val habits: ArrayList<HabitInformation>, val activity: Activity,
    val startActivity: ActivityResultLauncher<Intent>
) :
    RecyclerView.Adapter<HabitCardsAdapter.ViewHolder>() {
    inner class ViewHolder(private var binding: FragmentHabitBinding, itemView: View) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(habitCardInformation: HabitInformation) {
            itemView.setOnClickListener(this)
            itemView.isClickable = true
            itemView.isFocusable = true
            binding.habitTitle.text = habitCardInformation.habitTitle
            binding.description.text = "Описание: ${habitCardInformation.habitDescription}"
            binding.color.setBackgroundColor(habitCardInformation.habitColor)
            binding.habitType.text = "Тип: ${HabitType.toRus(habitCardInformation.habitType)}"
            binding.habitPriority.text =
                "Приоритет: ${HabitPriority.toRus(habitCardInformation.habitPriority)}"
            binding.habitFrequency.text =
                "Периодичность: ${habitCardInformation.habitNumberExecution} " +
                        "раз${Additions.defineWordEnding(habitCardInformation.habitNumberExecution)} в ${habitCardInformation.frequency}"
            //сверстать экран добавления и редактирования привычки
            //написать для него логику добавления новой привычки
            //сделать элементы редактирования и показа цвета при создании привычки
            //val resources = Resources.getSystem()
            //binding.description.text = resources.getString(R.string.habit_description, habitCardInformation.habitDescription)
            //binding.habitType.text = resources.getString(R.string.habit_type, habitCardInformation.habitType.toString())
            //binding.habitPriority.text = resources.getString(R.string.habit_priority, habitCardInformation.habitPriority.toString())
            //binding.habitFrequency.text = resources.getString(R.string.habit_frequency, habitCardInformation.habitNumberExecution,
            //Additions.defineWordEnding(habitCardInformation.habitNumberExecution), habitCardInformation.frequency)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            val intent = Intent(activity, HabitAddendum::class.java)
            intent.putExtra("position", position)
            startActivity.launch(intent)
        }
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentHabitBinding.inflate(LayoutInflater.from(parent.context)),
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_habit, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = habits[position]
        holder.bind(item)
    }
}