package com.projects.germanlanguageapp.data.dataSources.remote.apis

import com.projects.germanlanguageapp.domain.models.*
import retrofit2.http.*

interface WebServices {
    @GET("Level/Get")
    suspend fun getLevels():LevelsResponse

    @GET("adjective/Get")
    suspend fun getAdjectives(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):AdjectivesResponse

    @GET("noun/Get")
    suspend fun getNouns(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):NounsResponse

    @GET("verb/Get")
    suspend fun getVerbs(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):VerbsResponse

    @GET("sentence/Get")
    suspend fun getOthers(@Query("levelId") levelId: Int,@Query("lessonId") lessonId: Int):OthersResponse

}