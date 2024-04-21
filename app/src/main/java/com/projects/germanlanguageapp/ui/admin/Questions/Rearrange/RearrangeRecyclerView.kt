package com.projects.germanlanguageapp.ui.admin.Questions.Rearrange
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
import com.projects.germanlanguageapp.domain.models.RearrangeItem
import com.projects.germanlanguageapp.ui.admin.Questions.CompleteRearrangeAdminActivity
import com.projects.germanlanguageapp.ui.rearrangeQuestions.RearrangeQuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class RearrangeRecyclerView: AppCompatActivity() {
    private lateinit var binding: ActivityQuestionsRecyclerViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private val rearrangeViewModel: RearrangeQuestionsViewModel by viewModels()
    private var levelId: Int = 0
    private var lessonId: Int = 0
    private var rearrangeQuestions: String? = null
    private var rearrangeNameAnswer: String? = null
    private var rearrangeId: Int = 0
    private val ADD_WORD_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions_recycler_view)
        recyclerView = binding.levelsAdminRecycler
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)

        lifecycleScope.launch(Dispatchers.IO) {
            rearrangeViewModel.getRearrangeQuestions(levelId, lessonId)
        }

        lifecycleScope.launch(Dispatchers.Main) {
            rearrangeViewModel.questions.collectLatest { questions ->
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

    private fun updateQuestionsAdapter(rearrangeList: List<RearrangeItem?>) {
        val filteredList = rearrangeList.filterNotNull()
        questionsAdapter = QuestionsAdapter(filteredList.map {"Frage"},
            object : QuestionsAdapter.QuestionClickListener {
                override fun onQuestionClick(position: Int) {
                    val questions = filteredList[position]
                    rearrangeQuestions = questions.rearrangeNameQuestion ?: ""
                    rearrangeNameAnswer = questions.rearrangeNameAnswer ?: ""
                    openCompleteRearrangeAdminActivity("Question")
                }

                override fun onDeleteClick(position: Int) {
                    val questionsDelete = filteredList[position]
                    val questionId = questionsDelete.rearrangeId ?: 0
                    lifecycleScope.launch {
                        rearrangeViewModel.DeleteRearrangeQuestions(questionId)
                        rearrangeViewModel.getRearrangeQuestions(levelId, lessonId)
                    }
                }

                override fun onEditClick(position: Int) {
                    val questionsToEdit = filteredList[position]
                    rearrangeQuestions = questionsToEdit.rearrangeNameQuestion ?: ""
                    rearrangeNameAnswer = questionsToEdit.rearrangeNameAnswer ?: ""
                    rearrangeId = questionsToEdit.rearrangeId ?: 0
                    openCompleteRearrangeAdminActivity("Edit")
                }
            })

        recyclerView.adapter = questionsAdapter


    }

    private fun openCompleteRearrangeAdminActivity(buttonClicked: String) {
        startActivityForResult(Intent(this, CompleteRearrangeAdminActivity::class.java).apply {
            putExtra("buttonClicked", buttonClicked)
            putExtra("questionsType", "Rearrange")
            putExtra("levelId", levelId)
            putExtra("lessonId", lessonId)
            putExtra("Questions", rearrangeQuestions)
            putExtra("Answer", rearrangeNameAnswer)
            putExtra("QuestionsId", rearrangeId)
        }, ADD_WORD_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch(Dispatchers.IO) {
            rearrangeViewModel.getRearrangeQuestions(levelId, lessonId)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            rearrangeViewModel.questions.collectLatest { questions ->
                questions?.let {
                    updateQuestionsAdapter(it)
                }
            }
        }

    }
}
