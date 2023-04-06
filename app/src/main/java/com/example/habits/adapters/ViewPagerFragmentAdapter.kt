package com.example.habits.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits.constants.Constants
import com.example.habits.entities.HabitType
import com.example.habits.fragments.HabitsListFragment

class ViewPagerFragmentAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return Constants.habitTypesCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HabitsListFragment.newInstance(HabitType.Good)
            1 -> HabitsListFragment.newInstance(HabitType.Bad)
            else -> HabitsListFragment.newInstance(HabitType.Good)
        }
    }
}