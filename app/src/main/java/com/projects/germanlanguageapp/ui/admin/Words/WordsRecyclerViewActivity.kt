package com.projects.germanlanguageapp.ui.admin.Words

import QuestionsAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityWordsRecyclerViewBinding
import com.projects.germanlanguageapp.domain.models.WordsResponse
import com.projects.germanlanguageapp.ui.wordsdetails.WordsDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WordsRecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordsRecyclerViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private val ADD_WORD_REQUEST_CODE = 1

    private val viewModel: WordsDetailsViewModel by viewModels()
    private var wordsType: String? = null
    private var levelId: Int = 0
    private var lessonId: Int = 0
    private var wordname: String? = null
    private var wordtname: String? = null
    private var wordId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_words_recycler_view)
        recyclerView = binding.levelsAdminRecycler

        wordsType = intent.getStringExtra("wordsType")
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)

        binding.vm = viewModel

        lifecycleScope.launch {
            wordsType?.let { viewModel.loadRequiredData(levelId, lessonId, it) }
            viewModel.wordsList.collectLatest { wordsList ->
                wordsList?.let {
                    updateQuestionsAdapter(it)
                }
            }
        }

        val addButton = findViewById<ImageButton>(R.id.add_button)
        addButton.setOnClickListener {
            openWordsAdminActivity("add")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_WORD_REQUEST_CODE && resultCode == RESULT_OK) {
            lifecycleScope.launch {
                wordsType?.let { viewModel.loadRequiredData(levelId, lessonId, it) }
                viewModel.wordsList.collectLatest { wordsList ->
                    wordsList?.let {
                        updateQuestionsAdapter(it)
                    }
                }
            }
        }
    }

    private fun openWordsAdminActivity(buttonClicked: String) {
        startActivityForResult(Intent(this, WordsAdminActivity::class.java).apply {
            putExtra("buttonClicked", buttonClicked)
            putExtra("wordsType", wordsType)
            putExtra("levelId", levelId)
            putExtra("lessonId", lessonId)
            putExtra("wordname", wordname)
            putExtra("wordtname", wordtname)
            putExtra("wordId", wordId)
        }, ADD_WORD_REQUEST_CODE)
    }

    private fun updateQuestionsAdapter(wordsList: List<WordsResponse>) {
        questionsAdapter = QuestionsAdapter(wordsList.map { it.wordInGerman ?: "" },
            object : QuestionsAdapter.QuestionClickListener {
                override fun onQuestionClick(position: Int) {
                    val wordToEdit = wordsList[position]
                    wordname = wordToEdit.wordInGerman ?: ""
                    wordtname = wordToEdit.wordTranslationInArabic ?: ""
                    openWordsAdminActivity("Question")
                }

                override fun onDeleteClick(position: Int) {
                    val wordToDelete = wordsList[position]
                    val wordId = wordToDelete.wordId ?: 0
                    val wordsType = wordsType ?: ""
                    lifecycleScope.launch {
                        viewModel.DeleteWord(wordsType, wordId)
                        viewModel.loadRequiredData(levelId, lessonId, wordsType)
                    }
                }

                override fun onEditClick(position: Int) {
                    val wordToEdit = wordsList[position]
                    wordname = wordToEdit.wordInGerman ?: ""
                    wordtname = wordToEdit.wordTranslationInArabic ?: ""
                    wordId = wordToEdit.wordId ?: 0
                    openWordsAdminActivity("Edit")
                }
            })
        recyclerView.adapter = questionsAdapter
    }
}