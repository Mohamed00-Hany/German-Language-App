package com.projects.germanlanguageapp.ui.chooseQuestions

import android.util.Log
import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import com.projects.germanlanguageapp.domain.models.ChooseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.forEach
import javax.inject.Inject

@HiltViewModel
class ChooseQuestionsViewModel @Inject constructor (private val webServices: WebServices) : ViewModel() {

    val questions = MutableStateFlow<List<ChooseItem?>?>(null)

    val options: MutableList<List<String?>?> = mutableListOf()

    val correctAnswers: MutableList<Int?>? = mutableListOf()

    suspend fun getChooseQuestions(levelId: Int, lessonId: Int) {
        questions.value = try {
            webServices.getChooseQuestions(levelId,lessonId).data
        } catch (e:Exception) {
            listOf()
        }
        questions.value?.forEach { chooseItem ->
            options.add(chooseItem?.chooseNameAnswer
                ?.replace("\\s".toRegex(), "")
                ?.replace("\"".toRegex(), "")
                ?.replace('('.toString(), "")
                ?.replace(')'.toString(), "")
                ?.replace('['.toString(), "")
                ?.replace(']'.toString(), "")
                ?.replace('{'.toString(), "")
                ?.replace('}'.toString(), "")
                ?.replace('_'.toString(), "-")
                ?.replace('/'.toString(), "-")
                ?.replace("\\", "-")
                ?.split('-'))
            correctAnswers?.add(chooseItem?.chooseNameAnswerRight)
        }
    }

    suspend fun PostChooseQuestions(
        ChooseNameQuestion: String,
        ChooseNameAnswer: String,
        ChooseNameAnswerRight: Int,
        levelId: Int,
        lessonId: Int
    ) {
        try {
            webServices.PostChooseQuestions(ChooseNameQuestion, ChooseNameAnswer,ChooseNameAnswerRight,levelId, lessonId)
        } catch (e:Exception) {
            Log.e("PostChooseQuestions", "Error Choose Questions: ${e.message}")
        }}

    suspend fun PutChooseQuestions(
        ChooseId: Int,
        ChooseNameQuestion: String,
        ChooseNameAnswer: String,
        ChooseNameAnswerRight: Int) {
        try {
            webServices.PutChooseQuestions(ChooseId, ChooseNameQuestion,ChooseNameAnswer ,ChooseNameAnswerRight,)
        }catch (e:Exception) {
            Log.e("PutChooseQuestions", "Error Choose Questions: ${e.message}")
        }}

    suspend fun DeleteChooseQuestions(ChooseId: Int) {
        try {
            webServices.DeleteChooseQuestions(ChooseId)
        }catch (e:Exception) {
            Log.e("DeleteChooseQuestions", "Error Choose Questions: ${e.message}")
        }}

}