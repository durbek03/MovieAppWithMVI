package com.example.movieappwithmvi.presenter.movieDetailPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.constants.Constants.POSTER_PATH
import com.example.movieappwithmvi.databinding.MovieDetailFragmentBinding
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.AppBarState
import com.example.movieappwithmvi.presenter.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieDetailViewModel
    val mainViewModel: MainViewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() }
    )
    private lateinit var binding: MovieDetailFragmentBinding
    lateinit var args: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        lifecycleScope.launch {
            mainViewModel.bottomBarState.emit(AppBarState.INVISIBLE)
        }
        args = arguments?.getSerializable("movie") as Movie
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.movie_detail_fragment, container, false)
        binding = MovieDetailFragmentBinding.bind(root)
        Picasso.get().load(POSTER_PATH + args.posterPath).into(binding.movieImage)
        binding.movieName.text = args.title
        binding.synopsis.text = args.overview
        binding.popularity.text = args.popularity.toInt().toString() + " views"

        return binding.root
    }

    override fun onDestroyView() {
        lifecycleScope.launch {
            mainViewModel.bottomBarState.emit(AppBarState.VISIBLE)
        }
        super.onDestroyView()
    }
}