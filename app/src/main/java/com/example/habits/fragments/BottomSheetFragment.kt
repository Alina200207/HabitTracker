package com.example.habits.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.habits.HabitsApplication
import com.example.habits.databinding.FragmentBottomSheetBinding
import com.example.habits.viewmodels.HabitsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var application: HabitsApplication
    private val habitsViewModel: HabitsListViewModel by activityViewModels {
        HabitsListViewModel.Companion.Factory(
            application.repository
        )
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        application = activity?.application as HabitsApplication
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
}