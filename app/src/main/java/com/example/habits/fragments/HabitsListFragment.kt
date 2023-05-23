package com.example.habits.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.*
import com.example.habits.adapters.HabitCardsAdapter
import com.example.habits.databinding.FragmentHabitsListBinding
import com.example.domain.entities.HabitType
import com.example.habits.di.HabitsListViewModelComponent
import com.example.habits.di.HabitsListViewModelComponentProvider
import com.example.habits.di.HabitsListViewModelModule
import com.example.habits.viewmodels.HabitsListViewModel
import javax.inject.Inject


class HabitsListFragment : Fragment() {
    private val typeKey = "type"
    private lateinit var recyclerView: RecyclerView
    private var listHabitType = HabitType.Good
    private lateinit var application: HabitsApplication
//    private val habitsViewModel: HabitsListViewModel by activityViewModels {
//        HabitsListViewModel.Companion.HabitsListViewModelFactory(
//            application.repository
//        )
//    }
    @Inject
    lateinit var habitsViewModel: HabitsListViewModel
    private lateinit var adapter: HabitCardsAdapter
    private lateinit var binding: FragmentHabitsListBinding
    private val listener = HabitCardsAdapter.OnClickListener { id ->
        run {
            findNavController()
                .navigate(
                    ViewPagerFragmentDirections.actionViewPagerFragmentToHabitAddendumFragment(
                        id
                    )
                )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        application = activity?.application as HabitsApplication
        getHabitsViewModelComponent().inject(this)
        super.onCreate(savedInstanceState)
        arguments?.let {
            listHabitType = HabitType.valueOf(
                it.getString(typeKey)
                    ?: resources.getString(HabitType.Good.enum_text)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsListBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.title = resources.getString(R.string.menu_home)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        when (listHabitType) {
            HabitType.Good -> habitsViewModel.resultGoodHabits.observe(viewLifecycleOwner) { list ->
                adapter = HabitCardsAdapter(list, this, listener)
                recyclerView.adapter = adapter
            }
            HabitType.Bad -> habitsViewModel.resultBadHabits.observe(viewLifecycleOwner) { list ->
                adapter = HabitCardsAdapter(list, this, listener)
                recyclerView.adapter = adapter
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(typeKey, listHabitType.toString())
    }

    fun getHabitsViewModelComponent():
        HabitsListViewModelComponent =
            (application as HabitsListViewModelComponentProvider).provideHabitsListViewModelComponent().create(
                HabitsListViewModelModule(this.activity as MainActivity)
            )


    companion object {
        @JvmStatic
        fun newInstance(habitType: HabitType) =
            HabitsListFragment().apply {
                arguments = Bundle().apply {
                    putString(typeKey, habitType.toString())
                }
            }
    }
}