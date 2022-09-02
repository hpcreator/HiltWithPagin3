package com.app.tmdb.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.tmdb.data.ApiResponse
import com.app.tmdb.di.AppModule.apiKey
import com.app.tmdb.network.ApiService

class MoviesPagingSource constructor(private val apiService: ApiService) :
    PagingSource<Int, ApiResponse.Movies>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiResponse.Movies> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getMovies(apiKey = apiKey, page = page)
            val responseData = ArrayList<ApiResponse.Movies>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
            return LoadResult.Page(
                data = responseData,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ApiResponse.Movies>): Int? {
        return null
    }
}