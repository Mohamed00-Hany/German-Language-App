package com.projects.germanlanguageapp.ui.wordsdetails

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityWordsDetailsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class WordsDetailsActivity: AppCompatActivity() {

    val viewModel: WordsDetailsViewModel by viewModels()
    private lateinit var binding: ActivityWordsDetailsBinding
    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var wordsRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_words_details)
        binding.vm=viewModel
        wordsAdapter= WordsAdapter(null)
        wordsRecycler=binding.wordsRecycler
        wordsRecycler.adapter=wordsAdapter
        viewModel.onMicrophoneClicked(applicationContext)
        lifecycleScope.launch {
            viewModel.wordsList.collectLatest {
                wordsAdapter.changeData(it)
            }
        }
        wordsAdapter.onMicrophoneClickListener=object : WordsAdapter.OnMicrophoneClick {
            override fun onClick(position: Int, word: String?) {
                viewModel.speak(word)
            }
        }
    }

}