package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class RearrangeResponse(
    @field:SerializedName("data")
    val data: List<RearrangeItem?>? = null
)

data class RearrangeItem(
    @field:SerializedName("rearrangeId")
    val rearrangeId: Int? = null,

    @field:SerializedName("rearrangeNameQuestion")
    val rearrangeNameQuestion: String? = null,

    @field:SerializedName("rearrangeNameAnswer")
    val rearrangeNameAnswer: String? = null,

    @field:SerializedName("levelId")
    val levelId: Int? = null,

    @field:SerializedName("lessonId")
    val lessonId: Int? = null
)

