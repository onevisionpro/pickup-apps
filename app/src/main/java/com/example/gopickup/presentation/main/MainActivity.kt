package com.example.gopickup.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.gopickup.R
import com.example.gopickup.databinding.ActivityMainBinding
import com.example.gopickup.presentation.history.HistoryFragment
import com.example.gopickup.presentation.home.HomeFragment
import com.example.gopickup.presentation.more.MoreFragment
import com.example.gopickup.presentation.profile.ProfileFragment
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.NavigationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mOnNavigationItemSelected =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_history -> {
                    loadFragment(HistoryFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_more -> {
                    loadFragment(MoreFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) loadFragment(HomeFragment())

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelected)

        initView()
    }

    private fun initView() {
        binding.fab.setOnClickListener {
            NavigationUtils.navigateToOrderActivity(this)
//            loadFragment(OrderFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
//        Log.d("TAG", "onBackPressed: size: ${supportFragmentManager.backStackEntryCount}")
//        if (supportFragmentManager.backStackEntryCount > 1) {
//            supportFragmentManager.popBackStack()
//        } else {
//            super.onBackPressed()
//            finishAffinity()
//        }
    }

    override fun onResume() {
        super.onResume()
        // navigate after got trigger from dialog order
        when (intent.getStringExtra(Constants.NAVIGATE_TO)) {
            Constants.HISTORY -> {
                val view: View = binding.bottomNavigationView.findViewById(R.id.nav_history)
                view.performClick()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}