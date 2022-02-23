package com.example.movieappwithmvi.presenter.mainPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.constants.collectFlow
import com.example.movieappwithmvi.databinding.DummyFragmentBinding
import com.example.movieappwithmvi.presenter.mainPage.adapters.MoviePagerAdapter
import com.example.movieappwithmvi.presenter.mainPage.states.FeedIntent
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class DummyFragment : Fragment() {

    private lateinit var viewModel: DummyViewModel
    lateinit var binding: DummyFragmentBinding
    lateinit var pagingAdapter: MoviePagerAdapter
    lateinit var genre: String

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[DummyViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        genre = requireArguments().getString("genre", "")
        val root = inflater.inflate(R.layout.dummy_fragment, container, false)
        binding = DummyFragmentBinding.bind(root)

        binding.genreName.text = genre
        handleStates()

        pagingAdapter = MoviePagerAdapter()
        binding.viewPager.adapter = pagingAdapter

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(requireContext(), "stopped", Toast.LENGTH_SHORT).show()
    }

    private fun handleStates() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                when (it) {
                    is FeedStates.Loading -> {

                    }
                    is FeedStates.Failed -> {

                    }
                    is FeedStates.MoviesFetched -> {
                        val movies = it.movies
                        pagingAdapter.submitData(movies)
                    }
                }
            }
        }
    }
}