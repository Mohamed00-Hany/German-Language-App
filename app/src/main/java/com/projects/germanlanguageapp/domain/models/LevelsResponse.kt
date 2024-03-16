package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class LevelsResponse(

	@field:SerializedName("LevelsResponse")
	val levelsResponse: List<LevelsResponseItem?>? = null
)

data class LevelsResponseItem(

	@field:SerializedName("levelId")
	val levelId: Int? = null,

	@field:SerializedName("levelName")
	val levelName: String? = null
)
