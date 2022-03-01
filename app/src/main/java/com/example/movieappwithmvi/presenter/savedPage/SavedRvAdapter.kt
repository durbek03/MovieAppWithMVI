package com.example.movieappwithmvi.presenter.savedPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.constants.Constants
import com.example.movieappwithmvi.databinding.BigSavedMovieItemBinding
import com.example.movieappwithmvi.databinding.SavedMovieItemBinding
import com.example.movieappwithmvi.models.Movie
import com.example.movieappwithmvi.presenter.mainPage.adapters.MoviePagerAdapter
import com.squareup.picasso.Picasso

class SavedRvAdapter(val rvType: RvType, val listener: MoviePagerAdapter.MovieSelectedListener) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(SavedDiffUtil()) {
    inner class SmallViewVh(val binding: SavedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(savedMovie: Movie) {
            Picasso.get().load(Constants.POSTER_PATH + savedMovie.posterPath)
                .into(binding.movieImage)
            binding.movieName.text = savedMovie.title
            binding.movieRating.text = "Rating: " + savedMovie.voteAverage.toString() + " / 10"

            binding.root.setOnClickListener {
                listener.onSelected(savedMovie)
            }
        }
    }

    inner class BigViewVh(private val binding: BigSavedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(savedMovie: Movie) {
            Picasso.get().load(Constants.POSTER_PATH + savedMovie.posterPath)
                .into(binding.movieImage)
            binding.movieName.text = savedMovie.title
            binding.movieRating.text = "Rating: " + savedMovie.voteAverage.toString() + " / 10"

            binding.root.setOnClickListener {
                listener.onSelected(savedMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (rvType) {
            is RvType.SMALL -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.saved_movie_item, parent, false)
                SmallViewVh(SavedMovieItemBinding.bind(itemView))
            }
            is RvType.BIG -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.big_saved_movie_item, parent, false)
                BigViewVh(BigSavedMovieItemBinding.bind(itemView))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (rvType is RvType.SMALL) {
            holder as SmallViewVh
            holder.onBind(getItem(position))
        } else if (rvType is RvType.BIG) {
            holder as BigViewVh
            holder.onBind(getItem(position))
        }
    }

    class SavedDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.equals(newItem)
        }

    }
}

sealed class RvType {
    object SMALL : RvType()
    object BIG : RvType()
}