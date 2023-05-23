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
import com.example.domain.constants.Constants
import com.example.habits.databinding.FragmentHabitAddendumBinding
import com.example.domain.entities.HabitColors
import com.example.domain.entities.HabitInformation
import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType
import com.example.habits.di.AddendumViewModelFactory
import com.example.habits.di.provideFactory
import com.example.habits.viewmodels.HabitAddendumViewModel
import javax.inject.Inject
import kotlin.properties.Delegates

class HabitAddendumFragment : Fragment() {

    private lateinit var binding: FragmentHabitAddendumBinding
    private lateinit var habitTitleEditText: EditText
    private lateinit var habitDescriptionEditText: EditText
    private lateinit var typeRadioGroup: RadioGroup
    private lateinit var habitPrioritySpinner: Spinner
    private lateinit var habitFrequencyCountEditText: EditText
    private lateinit var habitFrequencyPeriodEditText: EditText
    private lateinit var save: ImageButton
    private lateinit var delete: ImageButton
    private lateinit var colorRadioGroup: RadioGroup

    private var habitId = -1L

    private var checkedTypeRadioButtonId by Delegates.notNull<Int>()
    private var checkedColorRadioButtonId by Delegates.notNull<Int>()
    private var habitPrioritySelected by Delegates.notNull<Int>()

    private lateinit var habitTitleText: String
    private lateinit var habitDescriptionText: String
    private lateinit var habitFrequencyCountText: String
    private lateinit var habitFrequencyPeriodText: String
    private lateinit var application: HabitsApplication



    @Inject
    lateinit var addendumViewModelFactory: AddendumViewModelFactory

    private val habitAddendumViewModel: HabitAddendumViewModel by viewModels {
        provideFactory(addendumViewModelFactory, habitId)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        application = activity?.application as HabitsApplication
        application.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitAddendumBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.changeDrawerBehavior()
        mainActivity.supportActionBar?.title = resources.getString(R.string.add_habit)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: HabitAddendumFragmentArgs by navArgs()
        habitId = args.id
        habitTitleEditText = binding.editHabitName
        habitDescriptionEditText = binding.editHabitDescription
        typeRadioGroup = binding.editTypeRadioGroup
        habitPrioritySpinner = binding.editPrioritySpinner
        habitFrequencyCountEditText = binding.editCount
        habitFrequencyPeriodEditText = binding.editPeriod
        save = binding.save
        delete = binding.delete
        colorRadioGroup = binding.editColorRadioGroup
        habitAddendumViewModel.request.observe(viewLifecycleOwner){
            habit -> setHabitState(habit)
        }
//        для ColorPicker
//        choseButton.setOnClickListener{
//            val fragmentManager = supportFragmentManager
//            val transaction = fragmentManager.beginTransaction()
//            val fragment = ColorPicker()
//            transaction.add(R.id.constraint, fragment).commit()
//        }
        save.setOnClickListener {
            getInformationFromFields()
            setInformationToViewModel()
            saveHabit(habitId, view)
        }
        delete.setOnClickListener{
            deleteHabit(view)
        }
    }

    private fun setHabitState(habit: HabitInformation) {
        if (habitId != -1L) {
            habitTitleEditText.setText(habit.habitTitle)
            habitDescriptionEditText.setText(habit.habitDescription)
            habitFrequencyCountEditText.setText(habit.habitNumberExecution.toString())
            habitFrequencyPeriodEditText.setText(habit.frequency.toString())
            setTypeRadioButton()
            setPrioritySpinner(habit.habitPriority)
            setColorRadioButton(habit.stringHabitColor)
        }
    }

    private fun setTypeRadioButton() {
        val countRadioButtons = typeRadioGroup.childCount
        for (i in 0 until countRadioButtons) {
            val radioButton = typeRadioGroup.getChildAt(i)
            if ((radioButton is RadioButton) && (radioButton.text == habitAddendumViewModel.request.value?.habitType?.text?.let {
                    resources.getString(
                        it
                    )
                })
            ) {
                radioButton.isChecked = true
            }
        }
    }

    private fun setColorRadioButton(habitColor: String) {
        val countColorButtons = colorRadioGroup.childCount
        for (i in 0 until countColorButtons) {
            val radioButton = colorRadioGroup.getChildAt(i)
            if ((radioButton is RadioButton) && (radioButton.text == habitColor)
            ) {
                radioButton.isChecked = true
            }
        }
    }

    private fun setPrioritySpinner(habitPriority: HabitPriority) {
        val countSpinnersChild = habitPrioritySpinner.count
        val habitPriorityString = resources.getString(habitPriority.text)
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
        val frequencyPeriod = habitFrequencyPeriodText.toIntOrNull() ?: 0
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

    private fun saveHabit(habitId: Long, view: View) {
        if (checkFieldsFullness()) {
            if (habitId == -1L)
                addHabit()
            else
                changeHabit()
            view.findNavController().navigateUp()
        } else {
            showToastMessage()
        }
    }

    private fun deleteHabit(view: View){
        habitAddendumViewModel.deleteHabit()
        view.findNavController().navigateUp()
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
        mainActivity.changeDrawerBehaviorBack()
    }
}