package com.projects.germanlanguageapp.ui.admin.Questions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityCompleteRearrangeAdminBinding
import com.projects.germanlanguageapp.ui.completeQuestions.CompleteQuestionsViewModel
import com.projects.germanlanguageapp.ui.rearrangeQuestions.RearrangeQuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class CompleteRearrangeAdminActivity : AppCompatActivity() {
    private val rearrangeViewModel: RearrangeQuestionsViewModel by viewModels()
    private val CompleteViewModel: CompleteQuestionsViewModel by viewModels()
    private var levelId: Int = 0
    private var lessonId: Int = 0
    private var Questions: String? = null
    private var Answer: String? = null
    private var rearrangeId: Int = 0
    private var questionsType: String? = null
    private lateinit var binding: ActivityCompleteRearrangeAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_rearrange_admin)
        questionsType = intent.getStringExtra("questionsType")
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        rearrangeId = intent.getIntExtra("QuestionsId", 0)
        Questions = intent.getStringExtra("Questions")
        Answer = intent.getStringExtra("Answer")
        val buttonClicked = intent.getStringExtra("buttonClicked")
        if (buttonClicked == "add") {
            binding.SubmitButton.visibility = View.VISIBLE
            binding.SubmitButton.setOnClickListener {
                val questions = binding.edittext1.text.toString()
                val Answer = binding.edittext2.text.toString()
                if (questions.isNotEmpty() && Answer.isNotEmpty()) {
                    if (questionsType=="Rearrange"){
                        lifecycleScope.launch {
                            rearrangeViewModel.PostRearrangeQuestions(
                                questions,
                                Answer,
                                levelId,
                                lessonId
                            )
                            setResult(RESULT_OK)
                            finish()
                        }

                    }else{
                    lifecycleScope.launch {
                        CompleteViewModel.PostCompleteQuestions(
                            questions,
                            Answer,
                            levelId,
                            lessonId
                        )
                        setResult(RESULT_OK)
                        finish()
                    }}
                } else {
                    Toast.makeText(
                        this@CompleteRearrangeAdminActivity,
                        "Please fill both fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else if (buttonClicked == "Edit") {
            binding.SubmitButton.visibility = View.VISIBLE
            binding.edittext1.setText(Questions)
            binding.edittext2.setText(Answer)
            binding.SubmitButton.setOnClickListener {
                val Questions = binding.edittext1.text.toString()
                val Answer = binding.edittext2.text.toString()
                if (Questions.isNotEmpty() && Answer.isNotEmpty()) {
                    if (questionsType=="Rearrange"){
                    lifecycleScope.launch {
                        rearrangeViewModel.PutRearrangeQuestions(rearrangeId, Questions, Answer)
                        setResult(RESULT_OK)
                        finish()
                    } }else {

                        lifecycleScope.launch {
                            CompleteViewModel.PutCompleteQuestions(rearrangeId, Questions, Answer)
                            setResult(RESULT_OK)
                            finish()
                        }

                    }
                } else {
                    Toast.makeText(
                        this@CompleteRearrangeAdminActivity,
                        "Please fill both fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else{
            binding.SubmitButton.visibility = View.GONE
            binding.edittext1.setText(Questions)
            binding.edittext2.setText(Answer)
            binding.edittext1.isEnabled = false
            binding.edittext2.isEnabled = false
        }

    }
}