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
    private val listener = HabitCardsAdapter.OnClickListener { position ->
        findNavController()
            .navigate(
                HabitsListFragmentDirections.actionHabitsListFragmentToHabitAddendumFragment(
                    position
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener {
            Log.i("main", "ki")
            findNavController().navigate(
                HabitsListFragmentDirections.actionHabitsListFragmentToHabitAddendumFragment(
                    -1
                )
            )
        }
    }


    override fun onResume() {
        super.onResume()
        adapter = HabitCardsAdapter(
            HabitsList.getHabits(),
            this,
            listener
        )
        habitsViewModel = ViewModelProvider(this)[HabitsViewModel::class.java]
        //adapter.submitList(HabitsList.getHabits())
        recyclerView.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HabitsListFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HabitsListFragment().apply {}
    }
}