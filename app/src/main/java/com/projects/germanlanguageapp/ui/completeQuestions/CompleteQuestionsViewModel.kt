package com.projects.germanlanguageapp.ui.completeQuestions

import android.util.Log
import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import com.projects.germanlanguageapp.domain.models.CompleteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CompleteQuestionsViewModel @Inject constructor (private val webServices: WebServices) : ViewModel() {
    val questions = MutableStateFlow<List<CompleteItem?>?>(null)

    suspend fun getCompleteQuestions(levelId: Int, lessonId: Int) {
        questions.value = try {
            webServices.getCompleteQuestions(levelId,lessonId).data
        } catch (e:Exception) {
            listOf()
        }
    }
    suspend fun PostCompleteQuestions(
        CompleteNameQuestion: String,
        CompleteNameAnswer: String,
        levelId: Int,
        lessonId: Int
    ) {
        try {
        webServices.PostCompleteQuestions(CompleteNameQuestion, CompleteNameAnswer, levelId, lessonId)
        } catch (e:Exception) {
            Log.e("PostCompleteQuestions", "Error Complete Questions: ${e.message}")
    }}

    suspend fun PutCompleteQuestions(CompleteId: Int, CompleteQuestions: String, CompleteNameAnswer: String) {
        try {
        webServices.PutCompleteQuestions(CompleteId, CompleteQuestions, CompleteNameAnswer)
    }catch (e:Exception) {
            Log.e("PutCompleteQuestions", "Error Complete Questions: ${e.message}")
        }}

    suspend fun DeleteCompleteQuestions(CompleteId: Int) {
        try {
        webServices.DeleteCompleteQuestions(CompleteId)
    }catch (e:Exception) {
        Log.e("DeleteCompleteQuestions", "Error Complete Questions: ${e.message}")
    }}
}