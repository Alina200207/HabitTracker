package com.example.habits

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

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