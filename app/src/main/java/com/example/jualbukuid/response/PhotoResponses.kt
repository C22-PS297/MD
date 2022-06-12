package com.example.jualbukuid.response

import com.google.gson.annotations.SerializedName

data class PhotoResponses(

	@field:SerializedName("confidence")
	val confidence: Double? = null,

	@field:SerializedName("class")
	val jsonMemberClass: String? = null
)
