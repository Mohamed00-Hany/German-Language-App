package com.projects.germanlanguageapp.data.dataSources.remote.apis

import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

const val API_URL = "https://api-inference.huggingface.co/models/jonatasgrosman/wav2vec2-large-xlsr-53-german"
const val API_TOKEN = "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"  // Your Hugging Face API token
val headers = mapOf("Authorization" to "Bearer $API_TOKEN")

suspend fun query(data:ByteArray): JSONObject {
    val client = OkHttpClient()
    val requestBody = data.toRequestBody("application/octet-stream".toMediaTypeOrNull())
    val apiRequest = Request.Builder()
        .url(API_URL)
        .headers(headers.toHeaders())
        .post(requestBody!!)
        .build()
    val apiResponse = client.newCall(apiRequest).execute()
    val responseBody = apiResponse.body?.string()

    return JSONObject(responseBody)
}
