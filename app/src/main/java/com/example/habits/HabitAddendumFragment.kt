package com.example.habits

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habits.databinding.FragmentHabitAddendumBinding
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
    private lateinit var habitType: HabitType
    private var checkedRadioId by Delegates.notNull<Int>()
    private var checkedRadioColorId by Delegates.notNull<Int>()
    private lateinit var habitTitleText: String
    private lateinit var habitDescriptionText: String
    private lateinit var habitFrequencyCountText: String
    private lateinit var habitFrequencyPeriodText: String
    private var habitPrioritySelected by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val elementPosition = args.position
        habitType = HabitType.valueOf(resources.getString(args.habitType))
        setHabitState(elementPosition)
//        для ColorPicker
//        choseButton.setOnClickListener{
//            val fragmentManager = supportFragmentManager
//            val transaction = fragmentManager.beginTransaction()
//            val fragment = ColorPicker()
//            transaction.add(R.id.constraint, fragment).commit()
//        }
        button.setOnClickListener {
            getInformationFromFields()
            setHabit(elementPosition, view)
        }
    }

    private fun setHabitState(position: Int) {
        if (position != -1) {
            val habitItem = when (habitType) {
                HabitType.Good -> HabitsList.getGoodHabits()[position]
                HabitType.Bad -> HabitsList.getBadHabits()[position]
            }
            habitTitle.setText(habitItem.habitTitle)
            habitDescription.setText(habitItem.habitDescription)
            habitFrequencyCount.setText(habitItem.habitNumberExecution.toString())
            habitFrequencyPeriod.setText(habitItem.frequency)
            setTypeRadioButton(habitItem)
            setColorRadioButton(habitItem)
            setPrioritySpinner(habitItem)
        }
    }

    private fun setTypeRadioButton(habitItem: HabitInformation){
        val countRadioButtons = radioGroup.childCount
        for (i in 0 until countRadioButtons) {
            val radioButton = radioGroup.getChildAt(i)
            if ((radioButton is RadioButton) && (radioButton.text == resources.getString(
                    habitItem.habitType.text
                ))
            ) {
                radioButton.isChecked = true
            }
        }
    }

    private fun setColorRadioButton(habitItem: HabitInformation){
        val countColorButtons = colorRadioGroup.childCount
        for (i in 0 until countColorButtons) {
            val radioButton = colorRadioGroup.getChildAt(i)
            if ((radioButton is RadioButton) && (radioButton.text == habitItem.stringHabitColor)
            ) {
                radioButton.isChecked = true
            }
        }
    }

    private fun setPrioritySpinner(habitItem: HabitInformation){
        val countSpinnersChild = habitPriority.count
        val habitPriorityString = resources.getString(habitItem.habitPriority.text)
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
                    elementPosition,
                    radioGroup.indexOfChild(checkedButton),
                    colorRadioGroup.indexOfChild(checkedColorButton)
                )
            view.findNavController().navigateUp()
        } else {
            showToastMessage()
        }
    }

    private fun showToastMessage(){
        Toast.makeText(this.requireContext(), Constants.toastAllFieldsText, Toast.LENGTH_LONG)
            .show()
    }

    private fun addHabit(
        checkedButtonId: Int,
        checkedColorButtonId: Int
    ) {
        HabitsList.addHabit(
            HabitInformation(
                Id.getId(),
                habitTitleText, habitDescriptionText,
                HabitPriority.values()[habitPrioritySelected],
                HabitType.values()[checkedButtonId],
                habitFrequencyCountText.toInt(),
                habitFrequencyPeriodText,
                if (HabitColors.values()[checkedColorButtonId].text == R.string.red_color_text) {
                    Color.rgb(255, 0, 0)
                } else
                    Color.rgb(133, 200, 55),
                resources.getString(HabitColors.values()[checkedColorButtonId].text)
            )
        )
    }

    private fun changeHabit(
        elementPosition: Int,
        checkedButtonId: Int,
        checkedColorButtonId: Int
    ) {

        HabitsList.changeHabit(
            elementPosition,
            HabitInformation(
                when (habitType) {
                    HabitType.Good -> HabitsList.getGoodHabits()
                    HabitType.Bad -> HabitsList.getBadHabits()
                }[elementPosition].id,
                habitTitleText, habitDescriptionText,
                HabitPriority.values()[habitPrioritySelected],
                HabitType.values()[checkedButtonId],
                habitFrequencyCountText.toInt(),
                habitFrequencyPeriodText,
                if (HabitColors.values()[checkedColorButtonId].text == R.string.red_color_text) {
                    Color.rgb(255, 0, 0)
                } else
                    Color.rgb(133, 200, 55),
                resources.getString(HabitColors.values()[checkedColorButtonId].text)

            ), habitType
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        val mainActivity = activity as MainActivity
        mainActivity.changeBack()
    }
}