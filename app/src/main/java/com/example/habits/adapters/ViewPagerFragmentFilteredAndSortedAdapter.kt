package com.example.habits.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habits.constants.Constants
import com.example.habits.entities.HabitType
import com.example.habits.fragments.FilteredAndSortedHabitsListFragment

class ViewPagerFragmentFilteredAndSortedAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return Constants.habitTypesCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FilteredAndSortedHabitsListFragment.newInstance(HabitType.Good)
            1 -> FilteredAndSortedHabitsListFragment.newInstance(HabitType.Bad)
            else -> FilteredAndSortedHabitsListFragment.newInstance(HabitType.Bad)
        }
    }
}