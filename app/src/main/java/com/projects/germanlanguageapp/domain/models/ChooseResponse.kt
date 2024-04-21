package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class ChooseResponse(
    @field:SerializedName("data")
    val data: List<ChooseItem?>? = null
)

data class ChooseItem(
    @field:SerializedName("chooseId")
    val chooseId: Int? = null,

    @field:SerializedName("chooseNameQuestion")
    val chooseNameQuestion: String? = null,

    @field:SerializedName("chooseNameAnswer")
    val chooseNameAnswer: String? = null,

    @field:SerializedName("chooseNameAnswerRight")
    val chooseNameAnswerRight:Int? = null,

    @field:SerializedName("levelId")
    val levelId: Int? = null,

    @field:SerializedName("lessonId")
    val lessonId: Int? = null
)

