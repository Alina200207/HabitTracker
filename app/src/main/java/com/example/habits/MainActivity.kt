package com.example.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.habits.constants.Constants
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
        drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                toolbar.setNavigationOnClickListener {
                    drawerLayout.closeDrawer(GravityCompat.START)

                }
                drawerToggle.syncState()
            }

            override fun onDrawerClosed(drawerView: View) {
                drawerToggle.isDrawerIndicatorEnabled = true
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toolbar.setNavigationOnClickListener {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                drawerToggle.syncState()
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })
        drawerLayout.addDrawerListener(drawerToggle)
        val headerLayout = navView.getHeaderView(0)
        val image = headerLayout.findViewById<ImageView>(R.id.image_view)
        Glide.with(headerLayout)
            .load(Constants.imageUrl)
            .placeholder(R.drawable.done_icon)
            .error(R.drawable.header_error_image)
            .circleCrop()
            .into(image)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.viewPagerFragment, R.id.appInfoFragment), drawerLayout
        )
        navView.setupWithNavController(navController)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        drawerToggle.syncState()
    }


    fun changeDrawerBehavior() {
        drawerToggle.isDrawerIndicatorEnabled = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()        }
    }


    fun changeDrawerBehaviorBack() {
        drawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        drawerToggle.syncState()
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        drawerLayout.closeDrawers()
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
