package com.example.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toolbar.title = resources.getString(R.string.habit_tracker)
        drawerLayout.addDrawerListener(drawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        navView.setNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.habitsListFragment -> navController.navigateUp()
//                R.id.appInfoFragment -> navController.navigateUp()
//                else -> navController.navigateUp()
//            }
//        }
//        val onBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                navController.navigateUp()
//            }
//        }

//        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        setupActionBarWithNavController(navController, drawerLayout)
        navView.setupWithNavController(navController)
        drawerToggle.syncState()
    }


    fun changeBehavior() {
        drawerToggle.isDrawerIndicatorEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle.toolbarNavigationClickListener =
            View.OnClickListener {
                navController.navigateUp()
            }
    }

    fun changeBack() {
        drawerToggle.isDrawerIndicatorEnabled = true
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        drawerToggle.syncState()
        toolbar.title = resources.getString(R.string.habit_tracker)
        drawerToggle.toolbarNavigationClickListener =
            View.OnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        Log.i("main", "kl")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
