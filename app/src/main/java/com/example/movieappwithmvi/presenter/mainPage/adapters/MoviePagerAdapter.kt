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

class MoviePagerAdapter :
    PagingDataAdapter<Movie, MoviePagerAdapter.Vh>(MovieDiffUtil()) {

    inner class Vh(val binding: ChildPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: Movie?) {
            movie ?: return
            binding.movieGenres.text = movie.genre.toSeparatedStr()
            binding.movieName.text = movie.title
            try {
                Picasso.get().load(movie.imageurl[0]).into(binding.movieImage)
            } catch (e:Exception) {
                e.printStackTrace()
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
            return oldItem.imdbid == newItem.imdbid
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.equals(newItem)
        }
    }

    fun List<String>.toSeparatedStr(): String {
        var str = ""
        for (i in this.indices) {
            if (i != 0) {
                str += ", ${this[i]}"
            } else {
                str += this[i]
            }
        }
        return str
    }
}