package com.example.habits

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<HabitCardsAdapter.ViewHolder>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val status = result.data?.getStringExtra("status") ?: "Nothing"
                    val index = result.data?.getIntExtra("index", -1) ?: -1
                    when (ChangeStatus.valueOf(status)) {
                        ChangeStatus.Changed -> {
                            adapter.notifyItemChanged(index)

                        }
                        ChangeStatus.Added -> {
                            adapter.notifyItemInserted(index)
                        }
                        ChangeStatus.Deleted -> adapter.notifyItemRemoved(index)
                        ChangeStatus.Nothing -> ""
                    }
                }
            }
        adapter = HabitCardsAdapter(HabitsList.getHabits(), this, startForResult)
        recyclerView.adapter = adapter
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, HabitAddendum::class.java).apply { }
            intent.putExtra("position", -1)
            startForResult.launch(intent)
        }

    }
}
