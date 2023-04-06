package com.example.habits.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.*
import com.example.habits.adapters.HabitCardsAdapter
import com.example.habits.databinding.FragmentFilteredAndSortedHabitsListBinding
import com.example.habits.entities.HabitType
import com.example.habits.viewmodels.HabitsListViewModel


class FilteredAndSortedHabitsListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var habitType = HabitType.Good
    private val habitsViewModel: HabitsListViewModel by viewModels {
        HabitsListViewModel.Companion.Factory(
            habitType
        )
    }
    private lateinit var adapter: HabitCardsAdapter
    private lateinit var binding: FragmentFilteredAndSortedHabitsListBinding
    private val listener = HabitCardsAdapter.OnClickListener { position ->
        findNavController()
            .navigate(
                ViewPagerFragmentDirections.actionViewPagerFragmentToHabitAddendumFragment(
                    position, habitsViewModel.getHabitsType().enum_text
                )
            )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilteredAndSortedHabitsListBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.title = resources.getString(R.string.menu_home)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = this
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.editFilterTitle.text.isNullOrEmpty()) {
                    habitsViewModel.setOriginState()
                    adapter = HabitCardsAdapter(habitsViewModel.habits.value ?: ArrayList(), fragment, listener)
                    recyclerView.adapter = adapter

                } else {
                    habitsViewModel.filteredByTitle(binding.editFilterTitle.text.toString())
                    adapter = HabitCardsAdapter(habitsViewModel.habits.value ?: ArrayList(), fragment, listener)
                    recyclerView.adapter = adapter

                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.editFilterTitle.text.isNullOrEmpty()) {
                    habitsViewModel.setOriginState()
                    adapter = HabitCardsAdapter(habitsViewModel.habits.value ?: ArrayList(), fragment, listener)
                    recyclerView.adapter = adapter

                } else {
                    habitsViewModel.filteredByTitle(binding.editFilterTitle.text.toString())
                    adapter = HabitCardsAdapter(habitsViewModel.habits.value ?: ArrayList(), fragment, listener)
                    recyclerView.adapter = adapter

                }
            }

        }
        binding.editFilterTitle.addTextChangedListener(textWatcher)
        binding.arrowDown.setOnClickListener {
            habitsViewModel.sortByTitle()
            adapter = HabitCardsAdapter(habitsViewModel.habits.value ?: ArrayList(), this, listener)
            recyclerView.adapter = adapter

        }
        binding.arrowUp.setOnClickListener {
            habitsViewModel.reverseSortByTitle()
            adapter = HabitCardsAdapter(habitsViewModel.habits.value ?: ArrayList(), this, listener)
            recyclerView.adapter = adapter
        }
        binding.arrowDownPriority.setOnClickListener {
            habitsViewModel.sortByPriority()
            adapter = HabitCardsAdapter(habitsViewModel.habits.value ?: ArrayList(), this, listener)
            recyclerView.adapter = adapter
        }
        binding.arrowUpPriority.setOnClickListener {
            habitsViewModel.reverseSortByPriority()
            adapter = HabitCardsAdapter(habitsViewModel.habits.value ?: ArrayList(), this, listener)
            recyclerView.adapter = adapter
        }
        recyclerView = binding.recyclerViewFilteredAndSorted
        habitsViewModel.habits.observe(
            viewLifecycleOwner,
            Observer { list ->
                adapter = HabitCardsAdapter(list, this, listener)
                recyclerView.adapter = adapter
            }
        )
    }


    companion object {
        @JvmStatic
        fun newInstance(
            newHabitType: HabitType
        ) =
            FilteredAndSortedHabitsListFragment().apply {
                habitType = newHabitType
            }
    }
}