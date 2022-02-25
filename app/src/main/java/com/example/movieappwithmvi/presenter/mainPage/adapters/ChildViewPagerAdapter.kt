package com.example.movieappwithmvi.presenter.mainPage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappwithmvi.R
import com.example.movieappwithmvi.constants.toSeparatedStr
import com.example.movieappwithmvi.databinding.ChildPagerItemBinding
import com.example.movieappwithmvi.models.Movie
import com.squareup.picasso.Picasso

class ChildViewPagerAdapter : ListAdapter<Movie,ChildViewPagerAdapter.Vh >(ChildPagerDiffUtil()) {
    inner class Vh(val binding: ChildPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(movie: Movie) {
            if (movie.imageurl.isNotEmpty()) {
                Picasso.get().load(movie.imageurl[0]).into(binding.movieImage)
            }
        }
    }

    class ChildPagerDiffUtil: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbid == newItem.imdbid
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.child_pager_item, parent, false)
        return Vh(ChildPagerItemBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}