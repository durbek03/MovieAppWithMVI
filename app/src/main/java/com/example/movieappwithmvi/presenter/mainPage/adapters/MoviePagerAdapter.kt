package com.example.movieappwithmvi.presenter.mainPage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.databinding.ChildPagerItemBinding
import com.example.movieappwithmvi.models.Movie
import com.squareup.picasso.Picasso

class MoviePagerAdapter(val listener: MovieSelectedListener) :
    PagingDataAdapter<Movie, MoviePagerAdapter.Vh>(MovieDiffUtil()) {

    inner class Vh(val binding: ChildPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: Movie?) {
            movie ?: return
            try {
                val base = "https://image.tmdb.org/t/p/w300"
                Picasso.get().load(base + movie.posterPath).into(binding.movieImage)
                binding.movieImage.clipToOutline = true
                binding.movieName.text = movie.title
                binding.movieRating.text = movie.voteAverage.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            binding.root.setOnClickListener {
                listener.onSelected(movie)
            }
        }
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.child_pager_item, parent, false)
        return Vh(ChildPagerItemBinding.bind(itemView))
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.equals(newItem)
        }
    }

    interface MovieSelectedListener {
        fun onSelected(movie: Movie)
    }
}