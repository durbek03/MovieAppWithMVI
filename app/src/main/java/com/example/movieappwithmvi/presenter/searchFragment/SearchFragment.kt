package com.example.movieappwithmvi.presenter.searchFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.databinding.SearchFragmentBinding
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.AppBarState
import com.example.movieappwithmvi.presenter.MainViewModel
import com.example.movieappwithmvi.pagination.MoviePagerAdapter
import com.example.movieappwithmvi.presenter.movieDetailPage.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val TAG = "SearchFragment"
    private lateinit var viewModel: SearchViewModel
    lateinit var binding: SearchFragmentBinding
    private val mainViewModel by viewModels<MainViewModel>(ownerProducer = {requireActivity()})
    lateinit var rvAdapter: MoviePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        rvAdapter = MoviePagerAdapter(MoviePagerAdapter.ViewType.RECYCLER, object : MoviePagerAdapter.MovieSelectedListener {
            override fun onSelected(movie: Movie) {
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
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.search_fragment, container, false)
        binding = SearchFragmentBinding.bind(root)
        binding.rv.adapter = rvAdapter

        binding.et.addTextChangedListener {
            val text = it.toString().trim()

            lifecycleScope.launch {
                viewModel.searchText.emit(text)
            }
        }

        lifecycleScope.launch {
            viewModel.movies.collectLatest {
                Log.d(TAG, "onCreateView: ${it.toString()}")
                rvAdapter.submitData(it)
            }
        }

        return binding.root
    }

    override fun onStart() {
        lifecycleScope.launch {
            mainViewModel.appBarState.emit(AppBarState.INVISIBLE)
        }
        super.onStart()
    }

    override fun onStop() {
        lifecycleScope.launch {
            mainViewModel.appBarState.emit(AppBarState.VISIBLE)
        }
        super.onStop()
    }
}