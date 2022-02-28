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
import com.example.movieappwithmvi.domain.locale.DatabaseRepository
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.AppBarState
import com.example.movieappwithmvi.presenter.MainViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieDetailViewModel
    val mainViewModel: MainViewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() }
    )
    private lateinit var binding: MovieDetailFragmentBinding
    lateinit var args: Movie

    @Inject
    lateinit var locale: DatabaseRepository

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

        lifecycleScope.launchWhenCreated {
            viewModel.movie.collectLatest {
                Picasso.get().load(POSTER_PATH + it.posterPath).into(binding.movieImage)
                binding.movieName.text = it.title
                binding.synopsis.text = it.overview
                binding.popularity.text = it.popularity.toInt().toString() + " views"
                binding.movieNameSmall.text = it.title
                binding.movieViewsSmall.text = it.popularity.toInt().toString() + " views"
                binding.progressBar.max = 10
                binding.progressBar.progress = it.voteAverage.toInt()
                binding.rating.text = it.voteAverage.toString()
                val checkIfExists = locale.checkIfExists(it.id)
                viewModel.isSaved.emit(checkIfExists)
            }
        }
        lifecycleScope.launch {
            viewModel.movie.emit(args)
        }

        lifecycleScope.launch {
            viewModel.isSaved.collect {
                if (it) {
                    binding.saveBtn.setImageResource(R.drawable.ic_saved)
                } else {
                    binding.saveBtn.setImageResource(R.drawable.ic_unsaved)
                }
            }
        }

        binding.returnBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.saveBtn.setOnClickListener {
            val flow = lifecycleScope.launch {
                viewModel.isSaved.emit(!viewModel.isSaved.value)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        val isSaved = viewModel.isSaved.value
        if (isSaved) {
            lifecycleScope.launch(Dispatchers.IO) {
                locale.saveMovie(args)
            }
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                locale.unSaveMovie(args.id)
            }
        }
        lifecycleScope.launch {
            mainViewModel.bottomBarState.emit(AppBarState.VISIBLE)
        }
        super.onDestroyView()
    }
}