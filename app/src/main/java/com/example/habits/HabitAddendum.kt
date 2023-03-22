package com.example.habits

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.habits.databinding.ActivityHabitAddendumBinding


class HabitAddendum : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_addendum)
        val binding = ActivityHabitAddendumBinding.inflate(layoutInflater)
        val button = findViewById<Button>(R.id.save_button)
        //val choseButton = findViewById<Button>(R.id.choose_color_button)
        val habitTitle = findViewById<EditText>(R.id.edit_habit_name)
        val habitDescription = findViewById<EditText>(R.id.edit_habit_description)
        val habitFrequencyCount = findViewById<EditText>(R.id.edit_count)
        val habitFrequencyPeriod = findViewById<EditText>(R.id.edit_period)
        val habitPriority = findViewById<Spinner>(R.id.edit_priority_spinner)
        val radioGroup = findViewById<RadioGroup>(R.id.edit_type_radio_group)
        val colorRadioGroup = findViewById<RadioGroup>(R.id.edit_color_radio_group)
//        val habit_title = binding.editHabitName
//        val habit_description = binding.editHabitDescription
//        val radio_group = binding.editTypeRadioGroup
//        val habit_priority = binding.editPrioritySpinner
//        val habit_frequency_count = binding.editCount
//        val habit_frequency_period = binding.editPeriod

        val extras = intent.extras
        var elementPosition = -1
        if (extras != null) {
            val position = extras.getInt("position")
            if (position != -1) {
                elementPosition = position
                val habitItem = HabitsList.getHabits()[position]
                habitTitle.setText(habitItem.habitTitle)
                habitDescription.setText(habitItem.habitDescription)
                habitFrequencyCount.setText(habitItem.habitNumberExecution.toString())
                habitFrequencyPeriod.setText(habitItem.frequency)
                val countRadioButtons = radioGroup.childCount
                for (i in 0 until countRadioButtons) {
                    val radioButton = radioGroup.getChildAt(i)
                    if ((radioButton is RadioButton) && (radioButton.text == HabitType.toRus(
                            habitItem.habitType
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
                val habitPriorityString = HabitPriority.toRus(habitItem.habitPriority)
                for (i in 0 until countSpinnersChild) {
                    val spinnerElement = habitPriority.getItemAtPosition(i)
                    if (spinnerElement.toString() == habitPriorityString) {
                        habitPriority.setSelection(i)
                    }
                }
            }
        }
//        choseButton.setOnClickListener{
//            val fragmentManager = supportFragmentManager
//            val transaction = fragmentManager.beginTransaction()
//            val fragment = ColorPicker()
//            transaction.add(R.id.constraint, fragment).commit()
//        }
        button.setOnClickListener {
            val id = radioGroup.checkedRadioButtonId
            val colorId = colorRadioGroup.checkedRadioButtonId
            val habit_title_text = habitTitle.text.toString()
            val habit_description_text = habitDescription.text.toString()
            val habit_frequency_count_text = habitFrequencyCount.text.toString()
            val habit_frequency_period_text = habitFrequencyPeriod.text.toString()
            val habit_priority_text = habitPriority.selectedItem.toString()
            if (colorId != -1 && id != -1 && habit_title_text.isNotEmpty() && habit_description_text.isNotEmpty()
                && habit_frequency_count_text.isNotEmpty() && habit_frequency_period_text.isNotEmpty()
            ) {
                val checkedButton = findViewById<RadioButton>(id)
                val checkedColorButton = findViewById<RadioButton>(colorId)
                if (elementPosition == -1)
                    HabitsList.addHabit(
                        HabitInformation(
                            habit_title_text, habit_description_text,
                            HabitPriority.toEnum(habit_priority_text),
                            HabitType.toEnum(checkedButton.text.toString()),
                            habit_frequency_count_text.toInt(),
                            habit_frequency_period_text,
                            if (checkedColorButton.text.toString() == "Красный") {
                                Color.rgb(255, 0, 0)
                            } else
                                Color.rgb(133, 200, 55),
                            checkedColorButton.text.toString()
                        )
                    )
                else
                    HabitsList.changeHabit(
                        elementPosition, HabitInformation(
                            habit_title_text, habit_description_text,
                            HabitPriority.toEnum(habit_priority_text),
                            HabitType.toEnum(checkedButton.text.toString()),
                            habit_frequency_count_text.toInt(),
                            habit_frequency_period_text,
                            if (checkedColorButton.text.toString() == "Красный") {
                                Color.rgb(255, 0, 0)
                            } else
                                Color.rgb(133, 200, 55),
                            checkedColorButton.text.toString()

                        )
                    )
                val resultIntent = Intent()
                if (elementPosition == -1) {
                    resultIntent.putExtra("status", "Added")
                    resultIntent.putExtra("index", HabitsList.getHabits().size - 1)
                } else {
                    resultIntent.putExtra("status", "Changed")
                    resultIntent.putExtra("index", elementPosition)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show()
            }
        }
    }
}