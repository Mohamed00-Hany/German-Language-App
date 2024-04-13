package com.projects.germanlanguageapp.ui.wordsdetails

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.domain.models.WordsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class WordsDetailsViewModel : ViewModel() {
    val wordsList = MutableStateFlow<List<WordsResponse>?>(listOf(
        WordsResponse(word = "Guten morgen", wordTranslation = "صباح الخير"),
        WordsResponse(word = "Hund", wordTranslation = "كلب"),
        WordsResponse(word = "Danke", wordTranslation = "شكرا"),
        WordsResponse(word = "Lächeln", wordTranslation = "يبتسم"),
        WordsResponse(word = "Katze", wordTranslation = "قط"),
        WordsResponse(word = "Hallo", wordTranslation = "مرحبا"),
        WordsResponse(word = "Tschüss", wordTranslation = "مع السلامة"),
        WordsResponse(word = "Glück", wordTranslation = "سعادة"),
        WordsResponse(word = "Liebe", wordTranslation = "حب"),
        WordsResponse(word = "Ja", wordTranslation = "نعم")
    ))

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
}