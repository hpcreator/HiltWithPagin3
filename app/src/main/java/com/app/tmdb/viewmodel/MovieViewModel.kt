package com.app.tmdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.app.tmdb.network.ApiService
import com.app.tmdb.repo.MoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    /*fun getMovies(): Flow<PagingData<ApiResponsee.Result>> =
        Pager(config = PagingConfig(20, enablePlaceholders = true)) {
            MoviesPagingSource(apiService = apiService)
        }.flow.cachedIn(viewModelScope)*/
    val movieData = Pager(PagingConfig(pageSize = 6)) {
        MoviesPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)
}