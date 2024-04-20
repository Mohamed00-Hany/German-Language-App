package com.projects.germanlanguageapp.ui.rearrangeQuestions

import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import com.projects.germanlanguageapp.domain.models.RearrangeItem
import com.projects.germanlanguageapp.domain.models.RearrangeResponse
import com.projects.germanlanguageapp.domain.models.WordsResponse
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

}