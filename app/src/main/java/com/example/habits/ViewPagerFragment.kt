package com.example.habits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.habits.databinding.FragmentHabitsListBinding
import com.example.habits.databinding.FragmentViewPagerBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerFragmentAdapter
    private val labels by lazy {
        arrayListOf<String>(
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
                    -1, resources.getString(R.string.good_habit)
                )
            )
        }
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager2
        adapter = ViewPagerFragmentAdapter(this)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (labels[position])
        }.attach()
    }

    override fun onResume() {
        super.onResume()
//        habitsViewModel = ViewModelProvider(this)[HabitsViewModel::class.java]
        //adapter.submitList(HabitsList.getHabits())
    }
}