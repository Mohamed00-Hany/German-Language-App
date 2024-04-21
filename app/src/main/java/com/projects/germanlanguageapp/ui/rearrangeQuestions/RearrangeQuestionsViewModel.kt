package com.projects.germanlanguageapp.ui.rearrangeQuestions

import android.util.Log
import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import com.projects.germanlanguageapp.domain.models.RearrangeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RearrangeQuestionsViewModel @Inject constructor (private val webServices: WebServices) : ViewModel() {
    val questions = MutableStateFlow<List<RearrangeItem?>?>(null)

    suspend fun getRearrangeQuestions(levelId: Int, lessonId: Int) {
        questions.value = try {
            webServices.getRearrangeQuestions(levelId,lessonId).data
        } catch (e:Exception) {
            listOf()
        }
    }
    suspend fun PostRearrangeQuestions(
        RearrangeNameQuestion: String,
        RearrangeNameAnswer: String,
        levelId: Int,
        lessonId: Int
    ) {
        try {
            webServices.PostRearrangeQuestions(RearrangeNameQuestion, RearrangeNameAnswer, levelId, lessonId)
        } catch (e:Exception) {
            Log.e("PostRearrangeQuestions", "Error Rearrange Questions: ${e.message}")
        }}

    suspend fun PutRearrangeQuestions(RearrangeId: Int, RearrangeQuestions: String, RearrangeNameAnswer: String) {
        try {
            webServices.PutRearrangeQuestions(RearrangeId, RearrangeQuestions, RearrangeNameAnswer)
        }catch (e:Exception) {
            Log.e("PutRearrangeQuestions", "Error Rearrange Questions: ${e.message}")
        }}

    suspend fun DeleteRearrangeQuestions(RearrangeId: Int) {
        try {
            webServices.DeleteRearrangeQuestions(RearrangeId)
        }catch (e:Exception) {
            Log.e("DeleteRearrangeQuestions", "Error Rearrange Questions: ${e.message}")
        }}

}