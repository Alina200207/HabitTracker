package com.example.habits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitCardsAdapter
    private lateinit var habitsViewModel: HabitsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, HabitAddendum::class.java).apply { }
            intent.putExtra(Constants.positionExtraName, -1)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        adapter = HabitCardsAdapter(
            HabitsList.getHabits(),
            this
        )
        habitsViewModel = ViewModelProvider(this)[HabitsViewModel::class.java]
        //adapter.submitList(HabitsList.getHabits())
        recyclerView.adapter = adapter
    }

}
