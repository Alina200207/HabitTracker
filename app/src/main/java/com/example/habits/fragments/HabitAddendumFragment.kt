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
import com.example.habits.entities.HabitPriority
import com.example.habits.entities.HabitType
import com.example.habits.viewmodels.HabitAddendumViewModel
import kotlin.properties.Delegates


class HabitAddendumFragment : Fragment() {

    private lateinit var binding: FragmentHabitAddendumBinding
    private lateinit var habitTitle: EditText
    private lateinit var habitDescription: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var habitPriority: Spinner
    private lateinit var habitFrequencyCount: EditText
    private lateinit var habitFrequencyPeriod: EditText
    private lateinit var button: Button
    private lateinit var colorRadioGroup: RadioGroup
    private var habitType = HabitType.Good
    private var checkedRadioId by Delegates.notNull<Int>()
    private var checkedRadioColorId by Delegates.notNull<Int>()
    private lateinit var habitTitleText: String
    private lateinit var habitDescriptionText: String
    private lateinit var habitFrequencyCountText: String
    private lateinit var habitFrequencyPeriodText: String
    private var position = -1
    private var habitPrioritySelected by Delegates.notNull<Int>()
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
        habitTitle = binding.editHabitName
        habitDescription = binding.editHabitDescription
        radioGroup = binding.editTypeRadioGroup
        habitPriority = binding.editPrioritySpinner
        habitFrequencyCount = binding.editCount
        habitFrequencyPeriod = binding.editPeriod
        button = binding.saveButton
        colorRadioGroup = binding.editColorRadioGroup
        val args: HabitAddendumFragmentArgs by navArgs()
        position = args.position
        habitType = HabitType.valueOf(resources.getString(args.habitType))
        setHabitState()
//        для ColorPicker
//        choseButton.setOnClickListener{
//            val fragmentManager = supportFragmentManager
//            val transaction = fragmentManager.beginTransaction()
//            val fragment = ColorPicker()
//            transaction.add(R.id.constraint, fragment).commit()
//        }
        button.setOnClickListener {
            getInformationFromFields()
            setInformationToViewModel()
            setHabit(position, view)
        }
    }

    private fun setHabitState() {
        if (position != -1) {
            habitTitle.setText(habitAddendumViewModel.habitTitle)
            habitDescription.setText(habitAddendumViewModel.habitDescription)
            habitFrequencyCount.setText(habitAddendumViewModel.habitFrequencyCount)
            habitFrequencyPeriod.setText(habitAddendumViewModel.habitFrequencyPeriod)
            if (habitAddendumViewModel.habitType != 0)
                setTypeRadioButton()
            if (habitAddendumViewModel.habitColor.isNotEmpty())
                setColorRadioButton()
            if (habitAddendumViewModel.habitPriority != 0)
                setPrioritySpinner()
        }
    }

    private fun setTypeRadioButton() {
        val countRadioButtons = radioGroup.childCount
        for (i in 0 until countRadioButtons) {
            val radioButton = radioGroup.getChildAt(i)
            if ((radioButton is RadioButton) && (radioButton.text == resources.getString(
                    habitAddendumViewModel.habitType
                ))
            ) {
                radioButton.isChecked = true
            }
        }
    }

    private fun setColorRadioButton() {
        val countColorButtons = colorRadioGroup.childCount
        for (i in 0 until countColorButtons) {
            val radioButton = colorRadioGroup.getChildAt(i)
            if ((radioButton is RadioButton) && (radioButton.text == habitAddendumViewModel.habitColor)
            ) {
                radioButton.isChecked = true
            }
        }
    }

    private fun setPrioritySpinner() {
        val countSpinnersChild = habitPriority.count
        val habitPriorityString = resources.getString(habitAddendumViewModel.habitPriority)
        for (i in 0 until countSpinnersChild) {
            val spinnerElement = habitPriority.getItemAtPosition(i)
            if (spinnerElement.toString() == habitPriorityString) {
                habitPriority.setSelection(i)
            }
        }
    }

    private fun getInformationFromFields() {
        checkedRadioId = radioGroup.checkedRadioButtonId
        checkedRadioColorId = colorRadioGroup.checkedRadioButtonId
        habitTitleText = habitTitle.text.toString()
        habitDescriptionText = habitDescription.text.toString()
        habitFrequencyCountText = habitFrequencyCount.text.toString()
        habitFrequencyPeriodText = habitFrequencyPeriod.text.toString()
        habitPrioritySelected = habitPriority.selectedItemPosition
    }

    private fun setInformationToViewModel(){
        habitAddendumViewModel.habitTitle = habitTitleText
        habitAddendumViewModel.habitDescription = habitDescriptionText
        habitAddendumViewModel.habitFrequencyCount = habitFrequencyCountText
        habitAddendumViewModel.habitFrequencyPeriod = habitFrequencyPeriodText
        habitAddendumViewModel.habitPriority = HabitPriority.values()[habitPrioritySelected].text
        if (checkedRadioId != -1) {
            val checkedButton = view?.findViewById<RadioButton>(checkedRadioId)
            habitAddendumViewModel.habitType =
                HabitType.values()[radioGroup.indexOfChild(checkedButton)].text
        }
        if (checkedRadioColorId != -1) {
            val checkedColorButton = view?.findViewById<RadioButton>(checkedRadioColorId)
            habitAddendumViewModel.habitColor =
                resources.getString(
                    HabitColors.values()[colorRadioGroup.indexOfChild(checkedColorButton)].text
                )
        }
    }

    private fun checkFieldsFullness(): Boolean {
        return (checkedRadioColorId != -1 && checkedRadioId != -1 && habitTitleText.isNotEmpty() && habitDescriptionText.isNotEmpty()
                && habitFrequencyCountText.isNotEmpty() && habitFrequencyPeriodText.isNotEmpty())
    }

    private fun setHabit(elementPosition: Int, view: View) {
        if (checkFieldsFullness()) {
            val checkedButton = view.findViewById<RadioButton>(checkedRadioId)
            val checkedColorButton = view.findViewById<RadioButton>(checkedRadioColorId)
            if (elementPosition == -1)
                addHabit(
                    radioGroup.indexOfChild(checkedButton),
                    colorRadioGroup.indexOfChild(checkedColorButton)
                )
            else
                changeHabit(
                    radioGroup.indexOfChild(checkedButton),
                    colorRadioGroup.indexOfChild(checkedColorButton)
                )
            view.findNavController().navigateUp()
        } else {
            showToastMessage()
        }
    }

    private fun showToastMessage() {
        Toast.makeText(this.requireContext(), Constants.toastAllFieldsText, Toast.LENGTH_LONG)
            .show()
    }

    private fun addHabit(
        checkedButtonId: Int,
        checkedColorButtonId: Int
    ) {
        habitAddendumViewModel.addHabit(
            habitPrioritySelected,
            checkedButtonId,
            checkedColorButtonId
        )
    }

    private fun changeHabit(
        checkedButtonId: Int,
        checkedColorButtonId: Int
    ) {
        habitAddendumViewModel.changeHabit(
            habitPrioritySelected,
            checkedButtonId,
            checkedColorButtonId
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