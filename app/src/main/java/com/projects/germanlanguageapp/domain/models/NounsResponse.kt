package com.projects.germanlanguageapp.domain.models

import com.google.gson.annotations.SerializedName

data class NounsResponse(

	@field:SerializedName("data")
	val data: List<NounItem?>? = null
)

data class NounItem(

	@field:SerializedName("nounName")
	val nounName: String? = null,

	@field:SerializedName("nounTname")
	val nounTname: String? = null,

	@field:SerializedName("levelId")
	val levelId: Int? = null,

	@field:SerializedName("lessonId")
	val lessonId: Int? = null,

	@field:SerializedName("nounId")
	val nounId: Int? = null
)
