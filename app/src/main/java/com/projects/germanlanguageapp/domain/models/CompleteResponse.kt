package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class CompleteResponse(
    @field:SerializedName("data")
    val data: List<CompleteItem?>? = null
)

data class CompleteItem(
    @field:SerializedName("completeId")
    val completeId: Int? = null,

    @field:SerializedName("completeNameQuestion")
    val completeNameQuestion: String? = null,

    @field:SerializedName("completeNameAnswer")
    val completeNameAnswer: String? = null,

    @field:SerializedName("levelId")
    val levelId: Int? = null,

    @field:SerializedName("lessonId")
    val lessonId: Int? = null
)

