package com.example.habits.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.*
import com.example.habits.adapters.HabitCardsAdapter
import com.example.habits.databinding.FragmentHabitsListBinding
import com.example.habits.entities.HabitType
import com.example.habits.viewmodels.HabitsListViewModel


class HabitsListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var listHabitType = HabitType.Good
    private val habitsViewModel: HabitsListViewModel by activityViewModels()
    private lateinit var adapter: HabitCardsAdapter
    private lateinit var binding: FragmentHabitsListBinding
    private val listener = HabitCardsAdapter.OnClickListener { position ->
        findNavController()
            .navigate(
                ViewPagerFragmentDirections.actionViewPagerFragmentToHabitAddendumFragment(
                    position, listHabitType.enum_text
                )
            )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsListBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.title = resources.getString(R.string.menu_home)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        when (listHabitType) {
            HabitType.Good -> habitsViewModel.goodResultHabits.observe(viewLifecycleOwner) { list ->
                adapter = HabitCardsAdapter(list, this, listener)
                recyclerView.adapter = adapter
            }
            HabitType.Bad -> habitsViewModel.badResultHabits.observe(viewLifecycleOwner) { list ->
                adapter = HabitCardsAdapter(list, this, listener)
                recyclerView.adapter = adapter
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(habitType: HabitType) =
            HabitsListFragment().apply {
                listHabitType = habitType
            }
    }
}