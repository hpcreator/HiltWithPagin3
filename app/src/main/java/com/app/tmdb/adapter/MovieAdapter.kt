package com.app.tmdb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.tmdb.R
import com.app.tmdb.data.ApiResponse
import com.app.tmdb.databinding.ItemMovieVerticalBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor() :
    PagingDataAdapter<ApiResponse.Movies, MovieAdapter.MovieViewHolder>(Diff) {

    val networkViewType = 1
    val movieViewType = 2
    lateinit var context: Context

    class MovieViewHolder(private val binding: ItemMovieVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: ApiResponse.Movies, context: Context) {
            binding.apply {
                imgMovie.load("https://image.tmdb.org/t/p/w500/${movies.posterPath}")
                tvMovieName.text = movies.title
                tvDate.text = movies.releaseDate
                progressRate.setProgress(movies.voteAverage!!, 10.0)
                when (movies.voteAverage) {
                    in 0.0..5.0 -> {
                        progressRate.progressColor = context.getColor(R.color.rate_50)
                        progressRate.dotColor = context.getColor(R.color.rate_50)
                    }
                    in 5.0..7.0 -> {
                        progressRate.progressColor = context.getColor(R.color.rate_70)
                        progressRate.dotColor = context.getColor(R.color.rate_70)
                    }
                    in 7.0..10.0 -> {
                        progressRate.progressColor = context.getColor(R.color.rate_100)
                        progressRate.dotColor = context.getColor(R.color.rate_100)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        if (movies != null) {
            holder.bind(movies, context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        context = parent.context
        return MovieViewHolder(
            ItemMovieVerticalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount -> {
                networkViewType
            }
            else -> {
                movieViewType
            }
        }
    }

    object Diff : DiffUtil.ItemCallback<ApiResponse.Movies>() {
        override fun areItemsTheSame(
            oldItem: ApiResponse.Movies,
            newItem: ApiResponse.Movies,
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ApiResponse.Movies,
            newItem: ApiResponse.Movies,
        ): Boolean =
            oldItem == newItem
    }
}