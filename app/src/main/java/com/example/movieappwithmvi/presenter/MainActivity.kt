package com.example.movieappwithmvi.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.databinding.ActivityMainBinding
import com.example.movieappwithmvi.presenter.mainPage.FeedFragment
import com.example.movieappwithmvi.presenter.profilePage.ProfileFragment
import com.example.movieappwithmvi.presenter.savedPage.SavedFragment
import com.example.movieappwithmvi.presenter.searchFragment.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val feedFragment = FeedFragment()
    val savedFragment = SavedFragment()
    val profileFragment = ProfileFragment()
    val searchFragment = SearchFragment()
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

        viewModel.currentFragment.observe(this) {
            when (it) {
                is FeedFragment -> openFragment(feedFragment)
                is SavedFragment -> openFragment(savedFragment)
                is ProfileFragment -> openFragment(profileFragment)
            }
        }

        lifecycleScope.launch {
            viewModel.appBarState.collectLatest {
                if (it is AppBarState.VISIBLE) {
                    binding.appBarLayout.visibility = View.VISIBLE
                    binding.bottomNavBar.visibility = View.VISIBLE
                } else if (it is AppBarState.INVISIBLE) {
                    binding.appBarLayout.visibility = View.GONE
                    binding.bottomNavBar.visibility = View.GONE
                }
            }
        }

        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main -> viewModel.setCurrentFragment(feedFragment)
                R.id.saved -> viewModel.setCurrentFragment(savedFragment)
                R.id.profile -> viewModel.setCurrentFragment(profileFragment)
            }
            true
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.search) {
                val beginTransaction = supportFragmentManager.beginTransaction()
                beginTransaction.replace(R.id.frag_container, searchFragment)
                    .addToBackStack(searchFragment.toString())
                    .commit()
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.frag_container, fragment).commit()
    }
}