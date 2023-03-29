package com.example.habits

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.habits.databinding.FragmentHabitsListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Use the [HabitsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HabitsListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitCardsAdapter
    private lateinit var habitsViewModel: HabitsViewModel
    private lateinit var binding: FragmentHabitsListBinding
    private lateinit var listHabitType: HabitType
    private val listener = HabitCardsAdapter.OnClickListener { position ->
        findNavController()
            .navigate(
                ViewPagerFragmentDirections.actionViewPagerFragmentToHabitAddendumFragment(
                    position, resources.getString(listHabitType.text)
                )
            )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
//        val fab: FloatingActionButton = binding.fab
//        fab.setOnClickListener {
//            findNavController().navigate(
//                HabitsListFragmentDirections.actionHabitsListFragmentToHabitAddendumFragment(
//                    -1
//                )
//            )
//        }
    }


    override fun onResume() {
        super.onResume()
        adapter = HabitCardsAdapter(
            when (listHabitType) {
                HabitType.Good -> HabitsList.getGoodHabits()
                HabitType.Bad -> HabitsList.getBadHabits()
            },
            this,
            listener
        )
        habitsViewModel = when (listHabitType) {
            HabitType.Good -> HabitsViewModel(HabitsList.getGoodHabits())
            HabitType.Bad -> HabitsViewModel(HabitsList.getBadHabits())
        }
//        habitsViewModel = ViewModelProvider(this)[HabitsViewModel::class.java]
        //adapter.submitList(HabitsList.getHabits())
        recyclerView.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance(habitType: HabitType) =
            HabitsListFragment().apply {
                listHabitType = habitType
            }
    }
}