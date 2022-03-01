package com.example.movieappwithmvi.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.constants.Constants
import com.example.movieappwithmvi.constants.Constants.POSTER_PATH
import com.example.movieappwithmvi.databinding.BigSavedMovieItemBinding
import com.example.movieappwithmvi.databinding.ChildPagerItemBinding
import com.example.movieappwithmvi.models.Movie
import com.squareup.picasso.Picasso

class MoviePagerAdapter(val viewType: ViewType, val listener: MovieSelectedListener) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffUtil()) {

    inner class ViewPagerVh(val binding: ChildPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: Movie?) {
            movie ?: return
            try {
                Picasso.get().load(POSTER_PATH + movie.posterPath).into(binding.movieImage)
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

    inner class RecyclerVh(private val binding: BigSavedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: Movie?) {
            movie ?: return
            Picasso.get().load(Constants.POSTER_PATH + movie.posterPath)
                .into(binding.movieImage)
            binding.movieName.text = movie.title
            binding.movieRating.text = "Rating: " + movie.voteAverage.toString() + " / 10"

            binding.root.setOnClickListener {
                listener.onSelected(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.viewType) {
            is ViewType.VIEWPAGER -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.child_pager_item, parent, false)
                ViewPagerVh(ChildPagerItemBinding.bind(itemView))
            }
            is ViewType.RECYCLER -> {
                val itemView =
                    LayoutInflater.from(parent.context).inflate(R.layout.big_saved_movie_item, parent, false)
                RecyclerVh(BigSavedMovieItemBinding.bind(itemView))
            }
        }
    }

    interface MovieSelectedListener {
        fun onSelected(movie: Movie)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (this.viewType) {
            is ViewType.RECYCLER -> {
                holder as RecyclerVh
                holder.onBind(getItem(position))
            }
            is ViewType.VIEWPAGER -> {
                holder as ViewPagerVh
                holder.onBind(getItem(position))
            }
        }
    }

    class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.equals(newItem)
        }
    }

    sealed class ViewType {
        object VIEWPAGER: ViewType()
        object RECYCLER: ViewType()
    }
}