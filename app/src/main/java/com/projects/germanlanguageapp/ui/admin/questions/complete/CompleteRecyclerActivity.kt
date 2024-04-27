package com.projects.germanlanguageapp.ui.admin.questions.complete
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
import com.projects.germanlanguageapp.databinding.ActivityQuestionsRecyclerViewBinding
import com.projects.germanlanguageapp.domain.models.CompleteItem
import com.projects.germanlanguageapp.ui.admin.questions.CompleteRearrangeAdminActivity
import com.projects.germanlanguageapp.ui.completeQuestions.CompleteQuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteRecyclerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityQuestionsRecyclerViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private val CompleteViewModel: CompleteQuestionsViewModel by viewModels()
    private var levelId: Int = 0
    private var lessonId: Int = 0
    private var  CompleteQuestions: String? = null
    private var  CompleteNameAnswer: String? = null
    private var  CompleteId: Int = 0
    private val ADD_WORD_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions_recycler_view)
        recyclerView = binding.levelsAdminRecycler
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)

        lifecycleScope.launch(Dispatchers.IO) {
            CompleteViewModel.getCompleteQuestions(levelId, lessonId)
        }

        lifecycleScope.launch(Dispatchers.Main) {
            CompleteViewModel.questions.collectLatest { questions ->
                questions?.let {
                    updateQuestionsAdapter(it)
                }
            }
        }
        val addButton = findViewById<ImageButton>(R.id.add_button)
        addButton.setOnClickListener {
            openCompleteRearrangeAdminActivity("add")
        }
    }

    private fun updateQuestionsAdapter(CompleteList: List<CompleteItem?>) {
        val filteredList = CompleteList.filterNotNull()
        questionsAdapter = QuestionsAdapter(filteredList.map {"Frage"},
            object : QuestionsAdapter.QuestionClickListener {
                override fun onQuestionClick(position: Int) {
                    val questions = filteredList[position]
                    CompleteQuestions = questions.completeNameQuestion ?: ""
                    CompleteNameAnswer = questions.completeNameAnswer ?: ""
                    openCompleteRearrangeAdminActivity("Question")
                }

                override fun onDeleteClick(position: Int) {
                    val questionsDelete = filteredList[position]
                    val questionId = questionsDelete.completeId ?: 0
                    lifecycleScope.launch {
                        CompleteViewModel.DeleteCompleteQuestions(questionId)
                        CompleteViewModel.getCompleteQuestions(levelId, lessonId)
                    }
                }
                override fun onEditClick(position: Int) {
                    val questionsToEdit = filteredList[position]
                    CompleteQuestions = questionsToEdit.completeNameQuestion ?: ""
                    CompleteNameAnswer = questionsToEdit.completeNameAnswer ?: ""
                    CompleteId = questionsToEdit.completeId ?: 0
                    openCompleteRearrangeAdminActivity("Edit")
                }
            })

        recyclerView.adapter = questionsAdapter


    }

    private fun openCompleteRearrangeAdminActivity(buttonClicked: String) {
        startActivityForResult(Intent(this, CompleteRearrangeAdminActivity::class.java).apply {
            putExtra("buttonClicked", buttonClicked)
            putExtra("questionsType", "Complete")
            putExtra("levelId", levelId)
            putExtra("lessonId", lessonId)
            putExtra("Questions", CompleteQuestions)
            putExtra("Answer", CompleteNameAnswer)
            putExtra("QuestionsId", CompleteId)
        }, ADD_WORD_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch(Dispatchers.IO) {
            CompleteViewModel.getCompleteQuestions(levelId, lessonId)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            CompleteViewModel.questions.collectLatest { questions ->
                questions?.let {
                    updateQuestionsAdapter(it)
                }
            }
        }
    }
}


