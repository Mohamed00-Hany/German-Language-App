package com.projects.germanlanguageapp.data.dataSources.remote.apis

import com.projects.germanlanguageapp.domain.models.LevelsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("levels")
    suspend fun getLevels(): LevelsResponse
}