package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class WordsResponse(
    @field:SerializedName("wordId")
    val wordId: Int? = null,

    @field:SerializedName("word")
    val word: String? = null,

    @field:SerializedName("wordTranslation")
    val wordTranslation: String? = null
)