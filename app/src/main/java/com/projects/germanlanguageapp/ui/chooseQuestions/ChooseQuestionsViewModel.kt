package com.projects.germanlanguageapp.ui.chooseQuestions

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

}