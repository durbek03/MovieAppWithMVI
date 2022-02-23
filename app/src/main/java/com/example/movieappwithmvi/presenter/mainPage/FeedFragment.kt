package com.example.movieappwithmvi.presenter.mainPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.constants.collectFlow
import com.example.movieappwithmvi.databinding.FeedFragmentBinding
import com.example.movieappwithmvi.presenter.mainPage.adapters.ParentViewPagerAdapter
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    lateinit var binding: FeedFragmentBinding
    lateinit var pagerAdapter: ParentViewPagerAdapter
    private val TAG = "FeedFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.feed_fragment, container, false)
        binding = FeedFragmentBinding.bind(root)

        collectFlow(viewModel.state) {
            when (it) {
                is FeedStates.GenresFetched -> {
                    pagerAdapter = ParentViewPagerAdapter(it.genres, requireActivity())
                    binding.viewPager.adapter = pagerAdapter
                    binding.apply {
                        viewPager.visibility = View.VISIBLE
                        progressCircular.visibility = View.INVISIBLE
                    }
                }
                is FeedStates.Loading -> {
                    binding.apply {
                        viewPager.visibility = View.INVISIBLE
                        progressCircular.visibility = View.VISIBLE
                    }
                }
                is FeedStates.Failed -> {
                    binding.apply {
                        viewPager.visibility = View.VISIBLE
                        progressCircular.visibility = View.INVISIBLE
                    }
                }
            }
        }

        return binding.root
    }
}