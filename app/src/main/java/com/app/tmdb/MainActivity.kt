package com.app.tmdb

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.app.tmdb.adapter.LoadingStateAdapter
import com.app.tmdb.adapter.MovieAdapter
import com.app.tmdb.databinding.ActivityMainBinding
import com.app.tmdb.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val movieViewModel: MovieViewModel by viewModels()

    @Inject
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        lifecycleScope.launchWhenCreated {
            movieViewModel.movieData.collect {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rvMovies.apply {
                setHasFixedSize(true)
                val manager = GridLayoutManager(this@MainActivity, 2)
                manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (movieAdapter.getItemViewType(position)) {
                            movieAdapter.WALLPAPER_VIEW_TYPE -> 1
                            movieAdapter.NETWORK_VIEW_TYPE -> 2
                            else -> -1
                        }
                    }
                }
                layoutManager = manager
                adapter = movieAdapter.withLoadStateHeaderAndFooter(
                    header = LoadingStateAdapter { movieAdapter.retry() },
                    footer = LoadingStateAdapter { movieAdapter.retry() }
                )
            }
        }
    }
}