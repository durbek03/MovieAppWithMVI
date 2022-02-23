package com.example.movieappwithmvi.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.databinding.ActivityMainBinding
import com.example.movieappwithmvi.presenter.mainPage.FeedFragment
import com.example.movieappwithmvi.presenter.profilePage.ProfileFragment
import com.example.movieappwithmvi.presenter.savedPage.SavedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val feedFragment = FeedFragment()
    val savedFragment = SavedFragment()
    val profileFragment = ProfileFragment()
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        if (viewModel.currentFragment.value == null) {
            openFragment(feedFragment)
        }

        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main -> viewModel.setCurrentFragment(feedFragment)
                R.id.saved -> viewModel.setCurrentFragment(savedFragment)
                R.id.profile -> viewModel.setCurrentFragment(profileFragment)
            }
            true
        }

        viewModel.currentFragment.observe(this) {
            when (it) {
                is FeedFragment -> openFragment(feedFragment)
                is SavedFragment -> openFragment(savedFragment)
                is ProfileFragment -> openFragment(profileFragment)
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.frag_container, fragment).commit()
    }
}