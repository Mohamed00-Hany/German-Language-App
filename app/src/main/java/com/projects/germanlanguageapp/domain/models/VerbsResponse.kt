package com.projects.germanlanguageapp.domain.models
import com.google.gson.annotations.SerializedName

data class VerbsResponse(

	@field:SerializedName("data")
	val data: List<VerbItem?>? = null
)

data class VerbItem(

	@field:SerializedName("verbName")
	val verbName: String? = null,

	@field:SerializedName("verbTname")
	val verbTname: String? = null,

	@field:SerializedName("levelId")
	val levelId: Int? = null,

	@field:SerializedName("lessonId")
	val lessonId: Int? = null,

	@field:SerializedName("verbId")
	val verbId: Int? = null
)
