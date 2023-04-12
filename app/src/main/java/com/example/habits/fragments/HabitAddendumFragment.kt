package com.example.habits.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habits.*
import com.example.habits.constants.Constants
import com.example.habits.databinding.FragmentHabitAddendumBinding
import com.example.habits.entities.HabitColors
import com.example.habits.entities.HabitInformation
import com.example.habits.entities.HabitPriority
import com.example.habits.entities.HabitType
import com.example.habits.viewmodels.HabitAddendumViewModel
import kotlin.properties.Delegates


class HabitAddendumFragment : Fragment() {

    private lateinit var binding: FragmentHabitAddendumBinding
    private lateinit var habitTitleEditText: EditText
    private lateinit var habitDescriptionEditText: EditText
    private lateinit var typeRadioGroup: RadioGroup
    private lateinit var habitPrioritySpinner: Spinner
    private lateinit var habitFrequencyCountEditText: EditText
    private lateinit var habitFrequencyPeriodEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var colorRadioGroup: RadioGroup

    private var habitType = HabitType.Good
    private lateinit var habit: HabitInformation
    private var position = -1

    private var checkedTypeRadioButtonId by Delegates.notNull<Int>()
    private var checkedColorRadioButtonId by Delegates.notNull<Int>()
    private var habitPrioritySelected by Delegates.notNull<Int>()

    private lateinit var habitTitleText: String
    private lateinit var habitDescriptionText: String
    private lateinit var habitFrequencyCountText: String
    private lateinit var habitFrequencyPeriodText: String

    private val habitAddendumViewModel: HabitAddendumViewModel by viewModels {
        HabitAddendumViewModel.Companion.Factory(
            position, habitType
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitAddendumBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.changeBehavior()
        mainActivity.supportActionBar?.title = resources.getString(R.string.add_habit)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        habitTitleEditText = binding.editHabitName
        habitDescriptionEditText = binding.editHabitDescription
        typeRadioGroup = binding.editTypeRadioGroup
        habitPrioritySpinner = binding.editPrioritySpinner
        habitFrequencyCountEditText = binding.editCount
        habitFrequencyPeriodEditText = binding.editPeriod
        saveButton = binding.saveButton
        colorRadioGroup = binding.editColorRadioGroup
        val args: HabitAddendumFragmentArgs by navArgs()
        position = args.position
        habitType = HabitType.valueOf(resources.getString(args.habitType))
        habit = habitAddendumViewModel.habit.value!!
        setHabitState()
//        для ColorPicker
//        choseButton.setOnClickListener{
//            val fragmentManager = supportFragmentManager
//            val transaction = fragmentManager.beginTransaction()
//            val fragment = ColorPicker()
//            transaction.add(R.id.constraint, fragment).commit()
//        }
        saveButton.setOnClickListener {
            getInformationFromFields()
            setInformationToViewModel()
            saveHabit(position, view)
        }
    }

    private fun setHabitState() {
        if (position != -1) {
            habitTitleEditText.setText(habit.habitTitle)
            habitDescriptionEditText.setText(habit.habitDescription)
            habitFrequencyCountEditText.setText(habit.habitNumberExecution.toString())
            habitFrequencyPeriodEditText.setText(habit.frequency)
            setTypeRadioButton()
            setPrioritySpinner()
            setColorRadioButton()
        }
    }

    private fun setTypeRadioButton() {
        val countRadioButtons = typeRadioGroup.childCount
        for (i in 0 until countRadioButtons) {
            val radioButton = typeRadioGroup.getChildAt(i)
            if ((radioButton is RadioButton) && (radioButton.text == habitAddendumViewModel.habit.value?.habitType?.text?.let {
                    resources.getString(
                        it
                    )
                })
            ) {
                radioButton.isChecked = true
            }
        }
    }

    private fun setColorRadioButton() {
        val countColorButtons = colorRadioGroup.childCount
        for (i in 0 until countColorButtons) {
            val radioButton = colorRadioGroup.getChildAt(i)
            if ((radioButton is RadioButton) && (radioButton.text == habit.stringHabitColor)
            ) {
                radioButton.isChecked = true
            }
        }
    }

    private fun setPrioritySpinner() {
        val countSpinnersChild = habitPrioritySpinner.count
        val habitPriorityString = resources.getString(habit.habitPriority.text)
        for (i in 0 until countSpinnersChild) {
            val spinnerElement = habitPrioritySpinner.getItemAtPosition(i)
            if (spinnerElement.toString() == habitPriorityString) {
                habitPrioritySpinner.setSelection(i)
            }
        }
    }

    private fun getInformationFromFields() {
        checkedTypeRadioButtonId = typeRadioGroup.checkedRadioButtonId
        checkedColorRadioButtonId = colorRadioGroup.checkedRadioButtonId
        habitTitleText = habitTitleEditText.text.toString()
        habitDescriptionText = habitDescriptionEditText.text.toString()
        habitFrequencyCountText = habitFrequencyCountEditText.text.toString()
        habitFrequencyPeriodText = habitFrequencyPeriodEditText.text.toString()
        habitPrioritySelected = habitPrioritySpinner.selectedItemPosition
    }

    private fun setInformationToViewModel() {
        val title = habitTitleText
        val description = habitDescriptionText
        val frequencyCount = habitFrequencyCountText.toIntOrNull() ?: 0
        val frequencyPeriod = habitFrequencyPeriodText
        val priority = HabitPriority.values()[habitPrioritySelected]
        var type = HabitType.Good
        var color = HabitColors.Green
        if (checkedTypeRadioButtonId != -1) {
            val checkedButton = view?.findViewById<RadioButton>(checkedTypeRadioButtonId)
            type =
                HabitType.values()[typeRadioGroup.indexOfChild(checkedButton)]
        }
        if (checkedColorRadioButtonId != -1) {
            val checkedColorButton = view?.findViewById<RadioButton>(checkedColorRadioButtonId)
            color = HabitColors.values()[colorRadioGroup.indexOfChild(checkedColorButton)]

        }
        habitAddendumViewModel.changeViewModelState(
            title, description, priority, type, frequencyCount,
            frequencyPeriod, color, resources.getString(color.text)
        )
    }

    private fun checkFieldsFullness(): Boolean {
        return (checkedColorRadioButtonId != -1 && checkedTypeRadioButtonId != -1 && habitTitleText.isNotEmpty() && habitDescriptionText.isNotEmpty()
                && habitFrequencyCountText.isNotEmpty() && habitFrequencyPeriodText.isNotEmpty())
    }

    private fun saveHabit(elementPosition: Int, view: View) {
        if (checkFieldsFullness()) {
            if (elementPosition == -1)
                addHabit()
            else
                changeHabit()
            view.findNavController().navigateUp()
        } else {
            showToastMessage()
        }
    }

    private fun showToastMessage() {
        Toast.makeText(this.requireContext(), Constants.toastAllFieldsText, Toast.LENGTH_LONG)
            .show()
    }

    private fun addHabit() {
        habitAddendumViewModel.addHabit(
        )
    }

    private fun changeHabit() {
        habitAddendumViewModel.changeHabit(
        )

    }

    override fun onStop() {
        super.onStop()
        getInformationFromFields()
        setInformationToViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        val mainActivity = activity as MainActivity
        mainActivity.changeBack()
    }
}