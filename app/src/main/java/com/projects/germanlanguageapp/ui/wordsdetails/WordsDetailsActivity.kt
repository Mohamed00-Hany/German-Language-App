package com.projects.germanlanguageapp.ui.wordsdetails

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityWordsDetailsBinding
import com.projects.germanlanguageapp.ui.main.MainActivity
import com.projects.germanlanguageapp.ui.recording.RecordingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WordsDetailsActivity: AppCompatActivity() {

    val viewModel: WordsDetailsViewModel by viewModels()
    private lateinit var binding: ActivityWordsDetailsBinding
    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var wordsRecycler: RecyclerView
    private var levelId: Int? = null
    private var lessonId: Int? = null
    private var wordsType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        wordsType = intent.getStringExtra("wordsType")
        binding= DataBindingUtil.setContentView(this, R.layout.activity_words_details)
        binding.vm=viewModel
        wordsAdapter= WordsAdapter(null)
        wordsRecycler=binding.wordsRecycler
        wordsRecycler.adapter=wordsAdapter
        viewModel.onMicrophoneClicked(applicationContext)
        lifecycleScope.launch(Dispatchers.IO) {
            wordsType?.let { viewModel.loadRequiredData(levelId!!, lessonId!!, it) }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.wordsList.collectLatest {
                wordsAdapter.changeData(it)
            }
        }
        wordsAdapter.onFeatureClickListener=object : WordsAdapter.OnFeatureClick {
            override fun onClick(position: Int, word: String?,featureNumber: Int) {
                if (featureNumber == 1) {
                    viewModel.speak(word)
                }
                else {
                    val intent = Intent(this@WordsDetailsActivity, RecordingActivity::class.java)
                    intent.putExtra("TARGET_WORD",word)
                    startActivity(intent)
                }
            }
        }
        binding.speaker.setOnClickListener {
            val enteredWord:String = binding.enteredWord.text.toString()
            if (enteredWord.isNotEmpty()) {
                viewModel.speak(enteredWord)
            }
        }
        binding.mic.setOnClickListener {
            val enteredWord:String = binding.enteredWord.text.toString()
            if (enteredWord.isNotEmpty()) {
                val intent = Intent(this@WordsDetailsActivity, RecordingActivity::class.java)
                intent.putExtra("TARGET_WORD",enteredWord)
                startActivity(intent)
            }
        }
    }
}