package com.example.movieappwithmvi.presenter.savedPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.databinding.SavedFragmentBinding
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.pagination.MoviePagerAdapter
import com.example.movieappwithmvi.presenter.mainPage.states.FeedIntent
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import com.example.movieappwithmvi.presenter.movieDetailPage.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : Fragment() {
    private lateinit var viewModel: SavedViewModel
    lateinit var binding: SavedFragmentBinding
    lateinit var rvAdapter: SavedRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SavedViewModel::class.java]
        rvAdapter = SavedRvAdapter(RvType.BIG, object : MoviePagerAdapter.MovieSelectedListener {
            override fun onSelected(movie: Movie) {
                transitionToDetails(movie)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.saved_fragment, container, false)
        binding = SavedFragmentBinding.bind(root)
        binding.rv.adapter = rvAdapter

        lifecycleScope.launch {
            viewModel.savedMovies.collectLatest {
                if (it is FeedStates.SavedMoviesFetched) {
                    rvAdapter.submitList(it.movies)
                }
            }
        }

        lifecycleScope.launch {
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