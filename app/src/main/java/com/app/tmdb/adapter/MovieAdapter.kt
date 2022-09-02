package com.app.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.tmdb.data.ApiResponse
import com.app.tmdb.databinding.ItemMovieBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor() :
    PagingDataAdapter<ApiResponse.Movies, MovieAdapter.MovieViewHolder>(Diff) {

    val NETWORK_VIEW_TYPE = 1
    val WALLPAPER_VIEW_TYPE = 2

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: ApiResponse.Movies) {
            binding.apply {
                imgMovie.load("https://image.tmdb.org/t/p/w500/${movies.posterPath}")
                tvMovieName.text = movies.title
                tvDate.text = movies.releaseDate
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null) {
            holder.bind(movies)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount -> {
                NETWORK_VIEW_TYPE
            }
            else -> {
                WALLPAPER_VIEW_TYPE
            }
        }
    }

    object Diff : DiffUtil.ItemCallback<ApiResponse.Movies>() {
        override fun areItemsTheSame(
            oldItem: ApiResponse.Movies,
            newItem: ApiResponse.Movies
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ApiResponse.Movies,
            newItem: ApiResponse.Movies
        ): Boolean =
            oldItem == newItem
    }
}