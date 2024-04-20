package com.projects.germanlanguageapp.ui.completeQuestions

import com.projects.germanlanguageapp.databinding.ActivityCompleteQactivityBinding
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.ui.main.ResultActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CompleteQuestionsActivity : AppCompatActivity() {

    private val viewModel: CompleteQuestionsViewModel by viewModels()
    private lateinit var binding: ActivityCompleteQactivityBinding

    private var currentQuestionsIndex = 0
    private var score = 0

    private var levelId = 0
    private var lessonId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_qactivity)
        binding = ActivityCompleteQactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getCompleteQuestions(levelId,lessonId)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.questions.collectLatest {
                if (it?.isNotEmpty() == true) {
                    displayQuestion()
                }
            }
        }
        binding.nextButton.setOnClickListener {
            if (viewModel.questions.value?.isNotEmpty() == true) {
                nextQuestion()
            }
        }
        binding.SubmitButton.setOnClickListener {
            if (viewModel.questions.value?.isNotEmpty() == true) {
                checkAnswer()
            }
        }
    }

    private fun displayQuestion() {
        val questionText = "${viewModel.questions.value!![currentQuestionsIndex]?.completeNameQuestion}"
        binding.completeQuestionText.text= questionText
        binding.completeQuestionText.movementMethod = ScrollingMovementMethod.getInstance()
        binding.answerText.text.clear()
        binding.answerResult.text = ""
    }
    private fun checkAnswer() {
        val correctAnswer = viewModel.questions.value!![currentQuestionsIndex]?.completeNameAnswer?.lowercase(
            Locale.ROOT
        )?.replace("\\s".toRegex(), "")
            ?.replace("-".toRegex(), "")
            ?.replace("\"".toRegex(), "")
            ?.replace('_'.toString(), "")
            ?.replace('/'.toString(), "")
            ?.replace("\\", "")
            ?.replace('('.toString(), "")
            ?.replace(')'.toString(), "")
            ?.replace('['.toString(), "")
            ?.replace(']'.toString(), "")
            ?.replace('{'.toString(), "")
            ?.replace('}'.toString(), "")

        val userAnswer =
            binding.answerText.text.toString().lowercase(Locale.ROOT).replace("\\s".toRegex(), "")
                .replace("-".toRegex(), "")
                .replace("\"".toRegex(), "")
                .replace('_'.toString(), "")
                .replace('/'.toString(), "")
                .replace("\\", "")
                .replace('('.toString(), "")
                .replace(')'.toString(), "")
                .replace('['.toString(), "")
                .replace(']'.toString(), "")
                .replace('{'.toString(), "")
                .replace('}'.toString(), "")

        if ( userAnswer== correctAnswer) {
            score++
            binding.answerResult.setTextColor(Color.GREEN)
            binding.answerResult.text="korrekte Antwort"
        } else {
            binding.answerResult.setTextColor(Color.RED)
            val wrongAnswer = "falsche Antwort \n${viewModel.questions.value!![currentQuestionsIndex]?.completeNameAnswer}"
            binding.answerResult.text=wrongAnswer
        }
        binding.SubmitButton.isEnabled=false
        binding.answerText.isEnabled = false
    }
    private fun nextQuestion() {
        if (currentQuestionsIndex < viewModel.questions.value!!.size-1){
            currentQuestionsIndex++
            binding.completeQuestionText.postDelayed({ displayQuestion() }, 100)
            binding.SubmitButton.isEnabled=true
            binding.answerText.isEnabled = true
        }
        if(currentQuestionsIndex ==viewModel.questions.value!!.size-1){
            binding.nextButton.text=getString(R.string.Show_Result)
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SOURCE_CLASS", "complete_Q_Activity")
                intent.putExtra("SCORE", score)
                intent.putExtra("NoOfQuestions", viewModel.questions.value!!.size)
                intent.putExtra("levelId",levelId)
                intent.putExtra("lessonId",lessonId)
                startActivity(intent)
                finish()
            }
        }
    }
}