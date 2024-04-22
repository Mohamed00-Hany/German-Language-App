package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @field:SerializedName("data")
    val data: List<MatchItem?>? = null
)

data class MatchItem(
    @field:SerializedName("matchId")
    val matchId: Int? = null,

    @field:SerializedName("matchNameQuestion1")
    val matchNameQuestion1: String? = null,

    @field:SerializedName("matchNameAnswer1")
    val matchNameAnswer1: String? = null,

    @field:SerializedName("matchNameQuestion2")
    val matchNameQuestion2: String? = null,

    @field:SerializedName("matchNameAnswer2")
    val matchNameAnswer2: String? = null,


    @field:SerializedName("matchNameQuestion3")
    val matchNameQuestion3: String? = null,

    @field:SerializedName("matchNameAnswer3")
    val matchNameAnswer3: String? = null,

    @field:SerializedName("matchNameQuestion4")
    val matchNameQuestion4: String? = null,

    @field:SerializedName("matchNameAnswer4")
    val matchNameAnswer4: String? = null,


    @field:SerializedName("levelId")
    val levelId: Int? = null,

    @field:SerializedName("lessonId")
    val lessonId: Int? = null
)

