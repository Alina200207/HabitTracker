package com.example.habits

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.habits.databinding.ActivityHabitAddendumBinding


class HabitAddendum : AppCompatActivity() {

    private lateinit var binding: ActivityHabitAddendumBinding
    private lateinit var habitTitle: EditText
    private lateinit var habitDescription: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var habitPriority: Spinner
    private lateinit var habitFrequencyCount: EditText
    private lateinit var habitFrequencyPeriod: EditText
    private lateinit var button: Button
    private lateinit var colorRadioGroup: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitAddendumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        habitTitle = binding.editHabitName
        habitDescription = binding.editHabitDescription
        radioGroup = binding.editTypeRadioGroup
        habitPriority = binding.editPrioritySpinner
        habitFrequencyCount = binding.editCount
        habitFrequencyPeriod = binding.editPeriod
        button = binding.saveButton
        colorRadioGroup = binding.editColorRadioGroup

        val extras = intent.extras
        var elementPosition = -1
        if (extras != null) {
            val position = extras.getInt(Constants.positionExtraName)
            elementPosition = position
            setHabitState(position)
        }
//        choseButton.setOnClickListener{
//            val fragmentManager = supportFragmentManager
//            val transaction = fragmentManager.beginTransaction()
//            val fragment = ColorPicker()
//            transaction.add(R.id.constraint, fragment).commit()
//        }
        button.setOnClickListener {
            buttonClickListener(elementPosition)
        }
    }

    private fun setHabitState(position: Int) {
        if (position != -1) {
            val habitItem = HabitsList.getHabits()[position]
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

    private fun buttonClickListener(elementPosition: Int) {
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
            val checkedButton = findViewById<RadioButton>(checkedRadioId)
            val checkedColorButton = findViewById<RadioButton>(checkedRadioColorId)
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
            finish()
        }
        else {
            Toast.makeText(this, Constants.toastAllFieldsText, Toast.LENGTH_LONG).show()
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
                HabitsList.size(),
                habitTitleText, habitDescriptionText,
                HabitPriority.toEnum(habitPriorityText),
                HabitType.toEnum(checkedButton.text.toString()),
                habitFrequencyCountText.toInt(),
                habitFrequencyPeriodText,
                if (checkedColorButton.text.toString() == Constants.redColorText) {
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
            elementPosition, HabitInformation(
                HabitsList.getHabits()[elementPosition].id,
                habitTitleText, habitDescriptionText,
                HabitPriority.toEnum(habitPriorityText),
                HabitType.toEnum(checkedButton.text.toString()),
                habitFrequencyCountText.toInt(),
                habitFrequencyPeriodText,
                if (checkedColorButton.text.toString() == Constants.redColorText) {
                    Color.rgb(255, 0, 0)
                } else
                    Color.rgb(133, 200, 55),
                checkedColorButton.text.toString()

            )
        )

    }
}