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
class WordsDetailsViewModel @Inject constructor(private val webServices: WebServices) :
    ViewModel() {
    var wordsList = MutableStateFlow<List<WordsResponse>?>(null)
    private var textToSpeech: TextToSpeech? = null
    fun onMicrophoneClicked(context: Context?) {
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
        try {
            when (wordsType) {
                "adjectives" -> {
                    getAdjectives(levelId, lessonId)
                }
                "nouns" -> {
                    getNouns(levelId, lessonId)
                }
                "verbs" -> {
                    getVerbs(levelId, lessonId)
                }
                "others" -> {
                    getOthers(levelId, lessonId)
                }
            }
        } catch (e: Exception) {


        }
    }

    suspend fun DeleteWord(wordsType: String, wordId: Int) {
        try {
            when (wordsType) {
                "adjectives" -> {
                    DeleteAdjectives(wordId)
                }
                "nouns" -> {
                    DeleteNouns(wordId)
                }
                "verbs" -> {
                    DeleteVerbs(wordId)
                }
                "others" -> {
                    DeleteOthers(wordId)
                }
            }
        } catch (e: Exception) {
            Log.e("DeleteWord", "Error deleting word: $e")
        }
    }

    suspend fun PostWord(
        wordsType: String,
        wordName: String,
        wordTName: String,
        levelId: Int,
        lessonId: Int
    ) {
        try {
            when (wordsType) {
                "adjectives" -> {
                    PostAdjectives(wordName, wordTName, levelId, lessonId)
                }
                "nouns" -> {
                    PostNouns(wordName, wordTName, levelId, lessonId)
                }
                "others" -> {
                    PostOthers(wordName, wordTName, levelId, lessonId)
                }
                "verbs" -> {
                    PostVerbs(wordName, wordTName, levelId, lessonId)
                }
            }
        } catch (e: Exception) {
            Log.e("PostWordError", "Error posting word: ${e.message}")
        }
    }

    suspend fun PutWord(wordsType: String, Wordid: Int, Wordname: String, WordtName: String) {
        try {
            when (wordsType) {
                "adjectives" -> PutAdjectives(Wordid, Wordname, WordtName)
                "nouns" -> PutNouns(Wordid, Wordname, WordtName)
                "others" -> PutOthers(Wordid, Wordname, WordtName)
                "verbs" -> PutVerbs(Wordid, Wordname, WordtName)
            }
        } catch (e: Exception) {
            Log.e("PutWordError", "Error putting word: ${e.message}")
        }
    }

    suspend fun getAdjectives(levelId: Int, lessonId: Int) {
        val wordsList: MutableList<WordsResponse>? = mutableListOf()
        webServices.getAdjectives(levelId, lessonId).data?.forEachIndexed { index, adjectiveItem ->
            wordsList?.add(
                WordsResponse(
                    adjectiveItem?.adjectiveId,
                    adjectiveItem?.adjectiveName,
                    adjectiveItem?.adjectiveTname
                )
            )
        }
        this.wordsList.value = wordsList
    }

    suspend fun PostAdjectives(
        adjectiveName: String,
        adjectiveTName: String,
        levelId: Int,
        lessonId: Int
    ) {
        webServices.PostAdjectives(adjectiveName, adjectiveTName, levelId, lessonId)
    }

    suspend fun PutAdjectives(adjectiveId: Int, adjectiveName: String, adjectiveTName: String) {
        webServices.PutAdjectives(adjectiveId, adjectiveName, adjectiveTName)
    }

    suspend fun DeleteAdjectives(adjectiveId: Int) {
        webServices.DeleteAdjectives(adjectiveId)
    }

    suspend fun getNouns(levelId: Int, lessonId: Int) {
        val wordsList: MutableList<WordsResponse>? = mutableListOf()
        webServices.getNouns(levelId, lessonId).data?.forEachIndexed { index, nounItem ->
            wordsList?.add(WordsResponse(nounItem?.nounId, nounItem?.nounName, nounItem?.nounTname))
        }
        this.wordsList.value = wordsList
    }

    suspend fun PostNouns(NounName: String, NounTName: String, levelId: Int, lessonId: Int) {
        webServices.Postnouns(NounName, NounTName, levelId, lessonId)
    }

    suspend fun PutNouns(NounId: Int, NounName: String, NounTName: String) {
        webServices.Putnouns(NounId, NounName, NounTName)
    }

    suspend fun DeleteNouns(NounId: Int) {
        webServices.Deletenouns(NounId)
    }

    suspend fun getVerbs(levelId: Int, lessonId: Int) {
        val wordsList: MutableList<WordsResponse>? = mutableListOf()
        webServices.getVerbs(levelId, lessonId).data?.forEachIndexed { index, verbItem ->
            wordsList?.add(WordsResponse(verbItem?.verbId, verbItem?.verbName, verbItem?.verbTname))
        }
        this.wordsList.value = wordsList
    }

    suspend fun PostVerbs(VerbName: String, VerbTName: String, levelId: Int, lessonId: Int) {
        webServices.Postverbs(VerbName, VerbTName, levelId, lessonId)
    }

    suspend fun PutVerbs(VerbId: Int, VerbName: String, VerbTName: String) {
        webServices.Putverbs(VerbId, VerbName, VerbTName)
    }

    suspend fun DeleteVerbs(VerbId: Int) {
        webServices.Deleteverbs(VerbId)
    }

    suspend fun getOthers(levelId: Int, lessonId: Int) {
        val wordsList: MutableList<WordsResponse>? = mutableListOf()
        webServices.getOthers(levelId, lessonId).data?.forEachIndexed { index, otherItem ->
            wordsList?.add(
                WordsResponse(
                    otherItem?.sentenceId,
                    otherItem?.sentenceName,
                    otherItem?.sentenceTname
                )
            )
        }
        this.wordsList.value = wordsList
    }

    suspend fun PostOthers(
        SentenceName: String,
        SentenceTName: String,
        levelId: Int,
        lessonId: Int
    ) {
        webServices.PostOthers(SentenceName, SentenceTName, levelId, lessonId)
    }

    suspend fun PutOthers(SentenceId: Int, SentenceName: String, SentenceTName: String) {
        webServices.PutOthers(SentenceId, SentenceName, SentenceTName)
    }

    suspend fun DeleteOthers(SentenceId: Int) {
        webServices.DeleteOthers(SentenceId)
    }
}