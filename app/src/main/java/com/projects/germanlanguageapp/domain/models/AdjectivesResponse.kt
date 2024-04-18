package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class AdjectivesResponse(

	@field:SerializedName("data")
	val data: List<AdjectiveItem?>? = null
)

data class AdjectiveItem(

	@field:SerializedName("adjectiveName")
	val adjectiveName: String? = null,

	@field:SerializedName("adjectiveTname")
	val adjectiveTname: String? = null,

	@field:SerializedName("levelId")
	val levelId: Int? = null,

	@field:SerializedName("lessonId")
	val lessonId: Int? = null,

	@field:SerializedName("adjectiveId")
	val adjectiveId: Int? = null
)
