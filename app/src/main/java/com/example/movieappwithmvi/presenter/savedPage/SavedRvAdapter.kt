package com.example.movieappwithmvi.presenter.savedPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.constants.Constants
import com.example.movieappwithmvi.databinding.SavedMovieItemBinding
import com.example.movieappwithmvi.models.Movie
import com.squareup.picasso.Picasso

class SavedRvAdapter : ListAdapter<Movie, SavedRvAdapter.Vh>(SavedDiffUtil()) {
    inner class Vh(val binding: SavedMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(savedMovie : Movie) {
            Picasso.get().load(Constants.POSTER_PATH + savedMovie.posterPath)
                .into(binding.movieImage)
            binding.movieName.text = savedMovie.title
            binding.movieRating.text = "Rating: " + savedMovie.voteAverage.toString() + " / 10"
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.saved_movie_item, parent, false)
        return Vh(SavedMovieItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}