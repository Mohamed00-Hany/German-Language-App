package com.projects.germanlanguageapp.ui.admin.Questions.Match
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
import com.projects.germanlanguageapp.domain.models.MatchItem
import com.projects.germanlanguageapp.ui.matchQuestions.MatchQuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MatchRecyclerView : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionsRecyclerViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private val matchviewModel: MatchQuestionsViewModel by viewModels()
    private var levelId: Int = 0
    private var lessonId: Int = 0
    private var  MatchNameQuestion1: String? = null
    private var  MatchNameAnswer1: String? = null
    private var  MatchNameQuestion2: String? = null
    private var  MatchNameAnswer2: String? = null
    private var  MatchNameQuestion3: String? = null
    private var  MatchNameAnswer3: String? = null
    private var  MatchNameQuestion4:String? = null
    private var  MatchNameAnswer4: String? = null
    private var  MatchId: Int = 0
    private val ADD_WORD_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions_recycler_view)
        recyclerView = binding.levelsAdminRecycler
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)

        lifecycleScope.launch(Dispatchers.IO) {
            matchviewModel.getMatchQuestions(levelId, lessonId)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            matchviewModel.Matchquestions.collectLatest { questions ->
                questions?.let {
                    updateQuestionsAdapter(it)
                }
            }
        }
        val addButton = findViewById<ImageButton>(R.id.add_button)
        addButton.setOnClickListener {
            openMatchAdminActivity("add")
        }
    }


    private fun updateQuestionsAdapter(MatchList: List<MatchItem?>) {
        val filteredList = MatchList.filterNotNull()
        questionsAdapter = QuestionsAdapter(filteredList.map {"Frage"},
            object : QuestionsAdapter.QuestionClickListener {
                override fun onQuestionClick(position: Int) {
                    val questions = filteredList[position]
                    MatchNameQuestion1=questions.matchNameQuestion1 ?: ""
                    MatchNameAnswer1=questions.matchNameAnswer1 ?: ""
                    MatchNameQuestion2=questions.matchNameQuestion2 ?: ""
                    MatchNameAnswer2=questions.matchNameAnswer2 ?: ""
                    MatchNameQuestion3=questions.matchNameQuestion3 ?: ""
                    MatchNameAnswer3=questions.matchNameAnswer3 ?: ""
                    MatchNameQuestion4=questions.matchNameQuestion4 ?: ""
                    MatchNameAnswer4=questions.matchNameAnswer4 ?: ""
                    openMatchAdminActivity("Question")
                }

                override fun onDeleteClick(position: Int) {
                    val questionsDelete = filteredList[position]
                    val questionId = questionsDelete.matchId ?: 0
                    lifecycleScope.launch {
                        matchviewModel.DeleteMatchQuestions(questionId)
                        matchviewModel.getMatchQuestions(levelId, lessonId)
                    }
                }
                override fun onEditClick(position: Int) {
                    val questionsToEdit = filteredList[position]
                    MatchNameQuestion1=questionsToEdit.matchNameQuestion1 ?: ""
                    MatchNameAnswer1=questionsToEdit.matchNameAnswer1 ?: ""
                    MatchNameQuestion2=questionsToEdit.matchNameQuestion2 ?: ""
                    MatchNameAnswer2=questionsToEdit.matchNameAnswer2 ?: ""
                    MatchNameQuestion3=questionsToEdit.matchNameQuestion3 ?: ""
                    MatchNameAnswer3=questionsToEdit.matchNameAnswer3 ?: ""
                    MatchNameQuestion4=questionsToEdit.matchNameQuestion4 ?: ""
                    MatchNameAnswer4=questionsToEdit.matchNameAnswer4 ?: ""
                    MatchId = questionsToEdit.matchId ?: 0
                    openMatchAdminActivity("Edit")
                }
            })

        recyclerView.adapter = questionsAdapter

    }
    private fun openMatchAdminActivity(buttonClicked: String) {
        startActivityForResult(Intent(this, MatchAdminActivity::class.java).apply {
            putExtra("buttonClicked", buttonClicked)
            putExtra("levelId", levelId)
            putExtra("lessonId", lessonId)
            putExtra("MatchNameQuestion1", MatchNameQuestion1)
            putExtra("MatchNameAnswer1", MatchNameAnswer1)
            putExtra("MatchNameQuestion2", MatchNameQuestion2)
            putExtra("MatchNameAnswer2", MatchNameAnswer2)
            putExtra("MatchNameQuestion3", MatchNameQuestion3)
            putExtra("MatchNameAnswer3", MatchNameAnswer3)
            putExtra("MatchNameQuestion4", MatchNameQuestion1)
            putExtra("MatchNameAnswer4", MatchNameAnswer4)
            putExtra("MatchId", MatchId)
        }, ADD_WORD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleScope.launch(Dispatchers.IO) {
            matchviewModel.getMatchQuestions(levelId, lessonId)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            matchviewModel.Matchquestions.collectLatest { questions ->
                questions?.let {
                    updateQuestionsAdapter(it)
                }
            }
        }

    }
}