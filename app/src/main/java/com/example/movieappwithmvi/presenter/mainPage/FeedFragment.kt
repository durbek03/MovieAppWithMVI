package com.example.movieappwithmvi.presenter.mainPage

import android.graphics.Canvas
import android.graphics.Rect
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.constants.collectFlow
import com.example.movieappwithmvi.databinding.FeedFragmentBinding
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.mainPage.adapters.ChildViewPagerAdapter
import com.example.movieappwithmvi.presenter.mainPage.adapters.CustomPageTransformer
import com.example.movieappwithmvi.presenter.mainPage.adapters.HorizontalMarginDecor
import com.example.movieappwithmvi.presenter.mainPage.adapters.MoviePagerAdapter
import com.example.movieappwithmvi.presenter.mainPage.states.FeedStates
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    val list = mutableListOf<Movie>()
    private lateinit var viewModel: FeedViewModel
    lateinit var binding: FeedFragmentBinding
    lateinit var pagerAdapter: ChildViewPagerAdapter
    private val TAG = "FeedFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        pagerAdapter = ChildViewPagerAdapter()
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
        binding.viewPager.addItemDecoration(HorizontalMarginDecor(requireContext(), R.dimen.viewpager_current_item_horizontal_margin))
        loadData()

        pagerAdapter.submitList(list)

//        collectFlow(viewModel.movieState) {
//            when (it) {
//                is FeedStates.Loading -> {
//                    binding.apply {
//                        viewPager.visibility = View.INVISIBLE
//                        progressCircular.visibility = View.VISIBLE
//                    }
//                }
//                is FeedStates.Failed -> {
//                    binding.apply {
//                        viewPager.visibility = View.VISIBLE
//                        progressCircular.visibility = View.INVISIBLE
//                    }
//                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//                }
//                is FeedStates.MoviesFetched -> {
////                    binding.apply {
////                        viewPager.visibility = View.VISIBLE
////                        progressCircular.visibility = View.INVISIBLE
////                    }
////                    pagerAdapter.submitData(it.movies)
//                }
//                is FeedStates.SavedMoviesFetched -> {
//
//                }
//            }
//        }

        return binding.root
    }

    fun loadData() {
        for (i in 1..50) {
            list.add(
                Movie(
                    listOf("Action", "Drama", "Thriller"),
                    listOf("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRf61mker2o4KH3CbVE7Zw5B1-VogMH8LfZHEaq3UdCMLxARZAB"),
                    "0",
                    10.0,
                    2022,
                    "something here",
                    "Interstellar$i",
                    "Movie"
                )
            )
        }
    }
}