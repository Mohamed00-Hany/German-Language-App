package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class ModelAiResponse(
    @field:SerializedName("text")
    val text: String? = null
)
