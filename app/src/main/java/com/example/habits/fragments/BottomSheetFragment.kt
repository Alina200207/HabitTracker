package com.example.habits.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habits.HabitsApplication
import com.example.habits.MainActivity
import com.example.habits.databinding.FragmentBottomSheetBinding
import com.example.habits.di.HabitsListViewModelComponent
import com.example.habits.di.HabitsListViewModelComponentProvider
import com.example.habits.di.HabitsListViewModelModule
import com.example.habits.viewmodels.HabitsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var application: HabitsApplication


    @Inject
    lateinit var habitsViewModel: HabitsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        application = activity?.application as HabitsApplication
        getHabitsViewModelComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                habitsViewModel.filteredByTitle(binding.editFilterTitle.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
        binding.editFilterTitle.addTextChangedListener(textWatcher)
        binding.arrowDown.setOnClickListener {
            habitsViewModel.sortByTitle()

        }
        binding.arrowUp.setOnClickListener {
            habitsViewModel.reverseSortByTitle()
        }
        binding.arrowDownPriority.setOnClickListener {
            habitsViewModel.sortByPriority()
        }
        binding.arrowUpPriority.setOnClickListener {
            habitsViewModel.reverseSortByPriority()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.editFilterTitle.setText(habitsViewModel.getFilterText())
    }

    private fun getHabitsViewModelComponent():
            HabitsListViewModelComponent =
        (application as HabitsListViewModelComponentProvider).provideHabitsListViewModelComponent()
            .create(
                HabitsListViewModelModule(this.activity as MainActivity)
            )
}