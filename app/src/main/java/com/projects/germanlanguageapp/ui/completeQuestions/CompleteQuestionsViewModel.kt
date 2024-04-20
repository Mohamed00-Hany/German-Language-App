package com.projects.germanlanguageapp.ui.completeQuestions

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

}