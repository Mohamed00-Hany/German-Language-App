package com.projects.germanlanguageapp.ui.matchQuestions
import android.util.Log
import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import com.projects.germanlanguageapp.domain.models.MatchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchQuestionsViewModel @Inject constructor (private val webServices: WebServices) : ViewModel() {
    val Matchquestions = MutableStateFlow<List<MatchItem?>?>(null)
    val options: MutableList<List<String?>?> = mutableListOf()
    val questions: MutableList<List<String?>?> = mutableListOf()
    val correctMatches: MutableList<MutableMap<Int, Int>> = mutableListOf()
    suspend fun getMatchQuestions(levelId: Int, lessonId: Int) {
        Matchquestions.value = try {
            webServices.getMatchQuestions(levelId, lessonId).data
        } catch (e: Exception) {
            listOf()
        }

        Matchquestions.value?.forEach { matchItem ->
            val randomizedOptions = mutableListOf<String?>()
            val correctMatch = mutableMapOf<Int, Int>()
            val optionIndices = mutableListOf(0, 1, 2, 3).shuffled()

            for (i in 0 until 4) {
                val optionIndex = optionIndices[i]
                val answer = when (optionIndex) {
                    0 -> matchItem?.matchNameAnswer1
                    1 -> matchItem?.matchNameAnswer2
                    2 -> matchItem?.matchNameAnswer3
                    3 -> matchItem?.matchNameAnswer4
                    else -> null
                }
                randomizedOptions.add(answer)
                correctMatch[optionIndex] = i
            }
            options.add(randomizedOptions)
            questions.add(
                listOf(
                    matchItem?.matchNameQuestion1,
                    matchItem?.matchNameQuestion2,
                    matchItem?.matchNameQuestion3,
                    matchItem?.matchNameQuestion4
                )
            )
            correctMatches.add(correctMatch)
        }
    }

    suspend fun PostMatchQuestions(
         MatchNameQuestion1: String,
         MatchNameAnswer1: String,
         MatchNameQuestion2: String,
         MatchNameAnswer2: String,
         MatchNameQuestion3: String,
         MatchNameAnswer3: String,
         MatchNameQuestion4: String,
         MatchNameAnswer4: String,
         levelId: Int,
         lessonId: Int
    ) {
        try {
            webServices.postMatchQuestions(
                MatchNameQuestion1,
                MatchNameAnswer1,
                MatchNameQuestion2,
                MatchNameAnswer2,
                MatchNameQuestion3,
                MatchNameAnswer3,
                MatchNameQuestion4,
                MatchNameAnswer4,
                levelId,
                lessonId
            )
        } catch (e:Exception) {
            Log.e("PostMatchQuestions", "Error Match Questions: ${e.message}")
        }}

    suspend fun PutMatchQuestions(
        MatchId: Int,
        MatchNameQuestion1: String,
        MatchNameAnswer1: String,
        MatchNameQuestion2: String,
        MatchNameAnswer2: String,
        MatchNameQuestion3: String,
        MatchNameAnswer3: String,
        MatchNameQuestion4: String,
        MatchNameAnswer4: String
    ) {
        try {
            webServices.putMatchQuestion(
                MatchId,
                MatchNameQuestion1,
                MatchNameAnswer1,
                MatchNameQuestion2,
                MatchNameAnswer2,
                MatchNameQuestion3,
                MatchNameAnswer3,
                MatchNameQuestion4,
                MatchNameAnswer4,
            )
        }catch (e:Exception) {
            Log.e("PutMatchQuestions", "Error Match Questions: ${e.message}")
        }}

    suspend fun DeleteMatchQuestions(MatchId: Int) {
        try {
            webServices.deleteMatchQuestion(MatchId)
        }catch (e:Exception) {
            Log.e("DeleteMatchQuestions", "Error Match Questions: ${e.message}")
        }}

}