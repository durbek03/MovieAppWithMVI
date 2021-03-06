package com.example.movieappwithmvi.presenter.mainPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.databinding.FeedFragmentBinding
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.mainPage.adapters.CustomPageTransformer
import com.example.movieappwithmvi.presenter.mainPage.adapters.HorizontalMarginDecor
import com.example.movieappwithmvi.pagination.MoviePagerAdapter
import com.example.movieappwithmvi.presenter.mainPage.states.FeedIntent
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import com.example.movieappwithmvi.presenter.movieDetailPage.MovieDetailFragment
import com.example.movieappwithmvi.presenter.savedPage.RvType
import com.example.movieappwithmvi.presenter.savedPage.SavedRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    lateinit var binding: FeedFragmentBinding
    lateinit var pagerAdapter: MoviePagerAdapter
    lateinit var savedRvAdapter: SavedRvAdapter
    private val TAG = "FeedFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        pagerAdapter = MoviePagerAdapter(
            MoviePagerAdapter.ViewType.VIEWPAGER,
            object : MoviePagerAdapter.MovieSelectedListener {
                override fun onSelected(movie: Movie) {
                    transitionToDetails(movie)
                }
            })
        savedRvAdapter =
            SavedRvAdapter(RvType.SMALL, object : MoviePagerAdapter.MovieSelectedListener {
                override fun onSelected(movie: Movie) {
                    transitionToDetails(movie)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.feed_fragment, container, false)
        binding = FeedFragmentBinding.bind(root)
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.setPageTransformer(CustomPageTransformer(requireContext()))
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.addItemDecoration(
            HorizontalMarginDecor(
                requireContext(),
                R.dimen.viewpager_current_item_horizontal_margin
            )
        )
        binding.rv.adapter = savedRvAdapter

        lifecycleScope.launch {
            viewModel.movieState.collect {
                when (it) {
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
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                    is FeedStates.MoviesFetched -> {
                        binding.apply {
                            viewPager.visibility = View.VISIBLE
                            progressCircular.visibility = View.INVISIBLE
                        }
                        pagerAdapter.submitData(it.movies)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.savedMovieStates.collect {
                when (it) {
                    is FeedStates.SavedMoviesFetched -> {
                        savedRvAdapter.submitList(it.movies)
                        Log.d(TAG, "onCreateView: ${it.movies.toString()}")
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.action.emit(FeedIntent.FetchMovies)
            viewModel.action.emit(FeedIntent.FetchSavedMovies)
        }

        return binding.root
    }

    private fun transitionToDetails(movie: Movie) {
        val beginTransaction = parentFragmentManager.beginTransaction()
        val frag = MovieDetailFragment()
        frag.arguments = Bundle().apply {
            putSerializable("movie", movie)
        }
        beginTransaction
            .replace(
                R.id.frag_container,
                frag,
            )
            .addToBackStack(frag.tag)
            .commit()
    }
}