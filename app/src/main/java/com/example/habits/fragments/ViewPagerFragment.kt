package com.example.habits.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.habits.*
import com.example.habits.adapters.ViewPagerFragmentAdapter
import com.example.habits.adapters.ViewPagerFragmentFilteredAndSortedAdapter
import com.example.habits.databinding.FragmentViewPagerBinding
import com.example.habits.entities.HabitType
import com.example.habits.viewmodels.HabitsListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerFragmentAdapter
    private lateinit var bottomSheetLayout: ConstraintLayout
    private var hasSet = false
    private lateinit var filteredAndSortedAdapter: ViewPagerFragmentFilteredAndSortedAdapter
    private val habitsViewModel: HabitsListViewModel by viewModels {
        HabitsListViewModel.Companion.Factory(
            HabitType.values()[viewPager.currentItem]
        )
    }

    private val labels by lazy {
        arrayListOf(
            resources.getString(R.string.good_habits),
            resources.getString(R.string.bad_habits)
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener {
            findNavController().navigate(
                ViewPagerFragmentDirections.actionViewPagerFragmentToHabitAddendumFragment(
                    -1, R.string.good_enum
                )
            )
        }
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager2
        adapter = ViewPagerFragmentAdapter(this)
        filteredAndSortedAdapter = ViewPagerFragmentFilteredAndSortedAdapter(
            this
        )
        viewPager.adapter = filteredAndSortedAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (labels[position])
        }.attach()
    }

}