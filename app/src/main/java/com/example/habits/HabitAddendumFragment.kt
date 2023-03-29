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
//        mainActivity.supportActionBar?.hide()
//        mainActivity.setSupportActionBar(binding.toolbarAddendum)
//        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        habitType = HabitType.toEnum(args.habitType)
        setHabitState(elementPosition)


//        choseButton.setOnClickListener{
//            val fragmentManager = supportFragmentManager
//            val transaction = fragmentManager.beginTransaction()
//            val fragment = ColorPicker()
//            transaction.add(R.id.constraint, fragment).commit()
//        }
        button.setOnClickListener {
            buttonClickListener(elementPosition, view)
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
            val countColorButtons = colorRadioGroup.childCount
            for (i in 0 until countColorButtons) {
                val radioButton = colorRadioGroup.getChildAt(i)
                if ((radioButton is RadioButton) && (radioButton.text == habitItem.stringHabitColor)
                ) {
                    radioButton.isChecked = true
                }
            }
            val countSpinnersChild = habitPriority.count
            val habitPriorityString = resources.getString(habitItem.habitPriority.text)
            for (i in 0 until countSpinnersChild) {
                val spinnerElement = habitPriority.getItemAtPosition(i)
                if (spinnerElement.toString() == habitPriorityString) {
                    habitPriority.setSelection(i)
                }
            }
        }
    }

    private fun buttonClickListener(elementPosition: Int, view: View) {
        val checkedRadioId = radioGroup.checkedRadioButtonId
        val checkedRadioColorId = colorRadioGroup.checkedRadioButtonId
        val habitTitleText = habitTitle.text.toString()
        val habitDescriptionText = habitDescription.text.toString()
        val habitFrequencyCountText = habitFrequencyCount.text.toString()
        val habitFrequencyPeriodText = habitFrequencyPeriod.text.toString()
        val habitPriorityText = habitPriority.selectedItem.toString()
        if (checkedRadioColorId != -1 && checkedRadioId != -1 && habitTitleText.isNotEmpty() && habitDescriptionText.isNotEmpty()
            && habitFrequencyCountText.isNotEmpty() && habitFrequencyPeriodText.isNotEmpty()
        ) {
            val checkedButton = view.findViewById<RadioButton>(checkedRadioId)
            val checkedColorButton = view.findViewById<RadioButton>(checkedRadioColorId)
            if (elementPosition == -1)
                addHabit(
                    habitTitleText,
                    habitDescriptionText,
                    habitPriorityText,
                    habitFrequencyCountText,
                    checkedButton,
                    habitFrequencyPeriodText,
                    checkedColorButton
                )
            else
                changeHabit(
                    elementPosition,
                    habitTitleText,
                    habitDescriptionText,
                    habitPriorityText,
                    habitFrequencyCountText,
                    checkedButton,
                    habitFrequencyPeriodText,
                    checkedColorButton
                )
            view.findNavController().navigateUp()
        } else {
            Toast.makeText(this.requireContext(), Constants.toastAllFieldsText, Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun addHabit(
        habitTitleText: String,
        habitDescriptionText: String,
        habitPriorityText: String,
        habitFrequencyCountText: String,
        checkedButton: RadioButton,
        habitFrequencyPeriodText: String,
        checkedColorButton: RadioButton
    ) {
        HabitsList.addHabit(
            HabitInformation(
                Id.getId(),
                habitTitleText, habitDescriptionText,
                HabitPriority.toEnum(habitPriorityText),
                HabitType.toEnum(checkedButton.text.toString()),
                habitFrequencyCountText.toInt(),
                habitFrequencyPeriodText,
                if (checkedColorButton.text.toString() == resources.getString(R.string.red_color_text)) {
                    Color.rgb(255, 0, 0)
                } else
                    Color.rgb(133, 200, 55),
                checkedColorButton.text.toString()
            )
        )
    }

    private fun changeHabit(
        elementPosition: Int,
        habitTitleText: String,
        habitDescriptionText: String,
        habitPriorityText: String,
        habitFrequencyCountText: String,
        checkedButton: RadioButton,
        habitFrequencyPeriodText: String,
        checkedColorButton: RadioButton
    ) {

        HabitsList.changeHabit(
            elementPosition,
            HabitInformation(
                when (habitType) {
                    HabitType.Good -> HabitsList.getGoodHabits()
                    HabitType.Bad -> HabitsList.getBadHabits()
                }[elementPosition].id,
                habitTitleText, habitDescriptionText,
                HabitPriority.toEnum(habitPriorityText),
                HabitType.toEnum(checkedButton.text.toString()),
                habitFrequencyCountText.toInt(),
                habitFrequencyPeriodText,
                if (checkedColorButton.text.toString() == resources.getString(R.string.red_color_text)) {
                    Color.rgb(255, 0, 0)
                } else
                    Color.rgb(133, 200, 55),
                checkedColorButton.text.toString()

            ), habitType
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        val mainActivity = activity as MainActivity
        mainActivity.changeBack()
    }
}