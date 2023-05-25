package com.example.habits.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.HabitsDatabaseRepository
import com.example.data.network.HabitsServerRepository
import com.example.domain.entities.HabitInformation
import com.example.domain.entities.HabitType
import com.example.habits.HabitItemDiffCallback
import com.example.habits.R
import com.example.habits.databinding.FragmentHabitBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitCardsAdapter(
    val habits: List<HabitInformation>, val fragment: Fragment,
    private val onClickListener: OnClickListener, private val coroutineScope: CoroutineScope,
    private val serverRepository: HabitsServerRepository
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
            binding.countDone.text = resources.getString(
                R.string.done_count,
                HabitInformation.getCountDone(habitCardInformation),
                habitCardInformation.habitNumberExecution
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
        holder.itemView.findViewById<ImageButton>(R.id.habit_done).setOnClickListener {
            updateDoneDates(position)
            this.notifyItemChanged(position)
            coroutineScope.launch {
                serverRepository.postHabitDone(habits[position])
            }
            showToast(item, holder.itemView.context)
        }
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item.id)
        }

    }


    private fun updateDoneDates(position: Int) {
        habits[position].doneDates += (System.currentTimeMillis() / 1000).toInt()
        Log.i("okdfsg", habits[position].doneDates.toString())
    }

    private fun showToast(habit: HabitInformation, context: Context) {
        val text = getText(habit)
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    private fun getText(habit: HabitInformation): String {
        val info = HabitInformation.getHabitDoneInfo(habit)
        val resources = fragment.resources
        var text: String = when (habit.habitType) {
            HabitType.Good -> {
                if (info.first <= 0) {
                    resources.getString(R.string.good_habits_can_not_do)
                } else {
                    resources.getString(
                        R.string.good_habits_can_do, info.first, resources.getQuantityString(
                            R.plurals.pluralsForCount,
                            info.first
                        )
                    )
                }
            }
            HabitType.Bad -> {
                if (info.first <= 0) {
                    resources.getString(R.string.bad_habits_can_not_do)
                } else {
                    resources.getString(
                        R.string.bad_habits_can_do, info.first, resources.getQuantityString(
                            R.plurals.pluralsForCount,
                            info.first
                        )
                    )
                }
            }
        }
        text += resources.getString(
            R.string.days_period_end, info.second,
            resources.getQuantityString(
                R.plurals.pluralsForDays,
                info.second
            )
        )
        return text
    }

    class OnClickListener(val clickListener: (id: Long) -> Unit) {
        fun onClick(id: Long) = clickListener(id)
    }
}