package com.projects.germanlanguageapp.ui.wordsdetails

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import com.projects.germanlanguageapp.domain.models.WordsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WordsDetailsViewModel @Inject constructor (private val webServices: WebServices) : ViewModel() {
    var wordsList = MutableStateFlow<List<WordsResponse>?>(null)

    private var textToSpeech: TextToSpeech? = null

    fun onMicrophoneClicked(context: Context?) {

        //Text = findViewById(R.id.Text);
        //btnText = findViewById(R.id.btnText);

        // create an object textToSpeech and adding features into it
        textToSpeech = TextToSpeech(context) { i ->
            // if No error is found then only it will run
            if (i != TextToSpeech.ERROR) {
                // To Choose language of speech
                textToSpeech!!.language = Locale.GERMAN
            }
        }
    }

    fun speak(text: String?) {
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    suspend fun loadRequiredData(levelId: Int, lessonId: Int, wordsType: String) {
        when(wordsType) {
            "adjectives" -> {
                getAdjectives(levelId,lessonId)
            }
            "nouns" -> {
                getNouns(levelId,lessonId)
            }
            "verbs" -> {
                getVerbs(levelId,lessonId)
            }
            "others" -> {
                getOthers(levelId,lessonId)
            }
        }
    }

    suspend fun getAdjectives(levelId: Int, lessonId: Int) {
        val wordsList: MutableList<WordsResponse>? = mutableListOf()
        webServices.getAdjectives(levelId,lessonId).data?.forEachIndexed { index,adjectiveItem ->
            Log.e("hany",index.toString())
            Log.e("hany",adjectiveItem.toString())
            wordsList?.add(WordsResponse(adjectiveItem?.adjectiveName,adjectiveItem?.adjectiveTname))
        }
        this.wordsList.value = wordsList
    }

    suspend fun getNouns(levelId: Int, lessonId: Int) {
        val wordsList: List<WordsResponse>? = listOf()
        webServices.getNouns(levelId,lessonId).data?.forEachIndexed { index,nounItem ->
            wordsList?.get(index)?.wordInGerman = nounItem?.nounName
            wordsList?.get(index)?.wordTranslationInArabic = nounItem?.nounTname
        }
        this.wordsList.value = wordsList
    }

    suspend fun getVerbs(levelId: Int, lessonId: Int) {
        val wordsList: List<WordsResponse>? = listOf()
        webServices.getVerbs(levelId,lessonId).data?.forEachIndexed { index,verbItem ->
            wordsList?.get(index)?.wordInGerman = verbItem?.verbName
            wordsList?.get(index)?.wordTranslationInArabic = verbItem?.verbTname
        }
        this.wordsList.value = wordsList
    }

    suspend fun getOthers(levelId: Int, lessonId: Int) {
        val wordsList: List<WordsResponse>? = listOf()
        webServices.getOthers(levelId,lessonId).data?.forEachIndexed { index,otherItem ->
            wordsList?.get(index)?.wordInGerman = otherItem?.sentenceName
            wordsList?.get(index)?.wordTranslationInArabic = otherItem?.sentenceTname
        }
        this.wordsList.value = wordsList
    }

}