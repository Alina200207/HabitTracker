package com.example.habits.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habits.MainActivity
import com.example.habits.R
import com.example.habits.databinding.FragmentAppInfoBinding


class AppInfoFragment : Fragment() {
    private lateinit var binding: FragmentAppInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppInfoBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.title = resources.getString(R.string.menu_app_info)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appVersion.text = resources.getString(R.string.version, 1, 0)
    }

}