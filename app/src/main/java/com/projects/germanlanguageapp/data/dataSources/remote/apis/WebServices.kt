package com.projects.germanlanguageapp.data.dataSources.remote.apis

import com.projects.germanlanguageapp.domain.models.LevelsResponse
import com.projects.germanlanguageapp.domain.models.ModelAiResponse
import retrofit2.http.*

interface WebServices {
    @GET("levels")
    suspend fun getLevels(): LevelsResponse
}