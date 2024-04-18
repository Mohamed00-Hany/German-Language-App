package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class OthersResponse(

	@field:SerializedName("data")
	val data: List<OtherItem?>? = null
)

data class OtherItem(

	@field:SerializedName("sentenceName")
	val sentenceName: String? = null,

	@field:SerializedName("sentenceTname")
	val sentenceTname: String? = null,

	@field:SerializedName("levelId")
	val levelId: Int? = null,

	@field:SerializedName("lessonId")
	val lessonId: Int? = null,

	@field:SerializedName("sentenceId")
	val sentenceId: Int? = null
)
