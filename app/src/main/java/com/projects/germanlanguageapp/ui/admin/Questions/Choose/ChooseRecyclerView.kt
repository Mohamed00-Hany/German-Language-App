package com.projects.germanlanguageapp.ui.admin.Questions.Choose

import QuestionsAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityQuestionsRecyclerViewBinding
import com.projects.germanlanguageapp.domain.models.ChooseItem
import com.projects.germanlanguageapp.ui.chooseQuestions.ChooseQuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseRecyclerView : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionsRecyclerViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private val ChooseviewModel: ChooseQuestionsViewModel by viewModels()
    private var levelId: Int = 0
    private var lessonId: Int = 0
    private var  ChooseNameQuestion: String? = null
    private var  ChooseNameAnswer: String? = null
    private var  ChooseNameAnswerRight: Int = 0
    private var  ChooseId: Int = 0
    private val ADD_WORD_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions_recycler_view)
        recyclerView = binding.levelsAdminRecycler
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)


        lifecycleScope.launch(Dispatchers.IO) {
            ChooseviewModel.getChooseQuestions(levelId, lessonId)
        }

        lifecycleScope.launch(Dispatchers.Main) {
            ChooseviewModel.questions.collectLatest { questions ->
                questions?.let {
                    updateQuestionsAdapter(it)
                }
            }
        }
        val addButton = findViewById<ImageButton>(R.id.add_button)
        addButton.setOnClickListener {
            openChooseAdminActivity("add")
        }
    }

    private fun updateQuestionsAdapter(ChooseList: List<ChooseItem?>) {
        val filteredList = ChooseList.filterNotNull()
        questionsAdapter = QuestionsAdapter(filteredList.map {"Frage"},
            object : QuestionsAdapter.QuestionClickListener {
                override fun onQuestionClick(position: Int) {
                    val questions = filteredList[position]
                    ChooseNameQuestion = questions.chooseNameQuestion ?: ""
                    ChooseNameAnswer = questions.chooseNameAnswer ?: ""
                    ChooseNameAnswerRight=questions.chooseNameAnswerRight?: 0
                    openChooseAdminActivity("Question")
                }

                override fun onDeleteClick(position: Int) {
                    val questionsDelete = filteredList[position]
                    val questionId = questionsDelete.chooseId ?: 0
                    lifecycleScope.launch {
                        ChooseviewModel.DeleteChooseQuestions(questionId)
                        ChooseviewModel.getChooseQuestions(levelId, lessonId)
                    }
                }
                override fun onEditClick(position: Int) {
                    val questionsToEdit = filteredList[position]
                    ChooseNameQuestion = questionsToEdit.chooseNameQuestion ?: ""
                    ChooseNameAnswer = questionsToEdit.chooseNameAnswer ?: ""
                    ChooseNameAnswerRight=questionsToEdit.chooseNameAnswerRight?: 0
                    ChooseId = questionsToEdit.chooseId ?: 0
                    openChooseAdminActivity("Edit")
                }
            })

        recyclerView.adapter = questionsAdapter

    }
    private fun openChooseAdminActivity(buttonClicked: String) {
        startActivityForResult(Intent(this, ChooseAdminActivity::class.java).apply {
            putExtra("buttonClicked", buttonClicked)
            putExtra("levelId", levelId)
            putExtra("lessonId", lessonId)
            putExtra("ChooseNameQuestion", ChooseNameQuestion)
            putExtra("ChooseNameAnswer", ChooseNameAnswer)
            putExtra("ChooseNameAnswerRight", ChooseNameAnswerRight)
            putExtra("ChooseId", ChooseId)
        }, ADD_WORD_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch(Dispatchers.IO) {
            ChooseviewModel.getChooseQuestions(levelId, lessonId)
        }

        lifecycleScope.launch(Dispatchers.Main) {
            ChooseviewModel.questions.collectLatest { questions ->
                questions?.let {
                    updateQuestionsAdapter(it)
                }
            }
        }

    }


}