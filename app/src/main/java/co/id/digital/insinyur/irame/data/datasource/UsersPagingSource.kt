package co.id.digital.insinyur.irame.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.data.network.API

class UsersPagingSource(private val api: API, private val bearer: String, private val query: String): PagingSource<Int, UsersResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersResponse> {
        val pagePosition = params.key ?: 0
        return try {
            // start refresh at page 1 if undefined
            val response = api.fetchPagedUsers(bearer, pagePosition, 10).users
            val prevKey = if (pagePosition == 0) null else response.first().id
            val nextKey = if (response.isEmpty()) null else response.last().id

            LoadResult.Page(response, prevKey, nextKey)

        } catch (e: Exception) { LoadResult.Error(e) }
    }

    override fun getRefreshKey(state: PagingState<Int, UsersResponse>): Int? {
        return null
    }

}