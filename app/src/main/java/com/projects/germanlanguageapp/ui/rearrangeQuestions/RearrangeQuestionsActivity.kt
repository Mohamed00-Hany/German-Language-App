package com.projects.germanlanguageapp.ui.rearrangeQuestions

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityRearrangequestionsBinding
import com.projects.germanlanguageapp.ui.main.ResultActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class RearrangeQuestionsActivity : AppCompatActivity() {
    private val viewModel: RearrangeQuestionsViewModel by viewModels()
    private lateinit var binding: ActivityRearrangequestionsBinding

    private var currentQuestionsIndex = 0
    private var score = 0

    private var levelId = 0
    private var lessonId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rearrangequestions)
        binding = ActivityRearrangequestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getRearrangeQuestions(levelId,lessonId)
            withContext(Dispatchers.Main) {
                viewModel.questions.collectLatest {
                    if (it?.isNotEmpty() == true) {
                        displayQuestion()
                    }
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
        val questionText = "${viewModel.questions.value!![currentQuestionsIndex]?.rearrangeNameQuestion}"
        binding.rearrangeQuestionText.text = questionText
        binding.rearrangeQuestionText.movementMethod = ScrollingMovementMethod.getInstance()
        binding.answerText.text.clear()
        binding.answerResult.text = ""
    }

    private fun checkAnswer() {
        val correctAnswer = viewModel.questions.value!![currentQuestionsIndex]?.rearrangeNameAnswer?.lowercase(
            Locale.ROOT
        )
            ?.replace("\\s".toRegex(), "")
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

        val userAnswer = binding.answerText.text.toString().lowercase(Locale.ROOT)
            .replace("\\s".toRegex(), "")
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

        if (userAnswer == correctAnswer) {
            score++
            binding.answerResult.setTextColor(Color.GREEN)
            binding.answerResult.text = "korrekte Antwort"
        } else {
            binding.answerResult.setTextColor(Color.RED)
            val wrongAnswer =
                "falsche Antwort \n${viewModel.questions.value!![currentQuestionsIndex]?.rearrangeNameAnswer}"
            binding.answerResult.text = wrongAnswer
        }
        binding.SubmitButton.isEnabled = false
        binding.answerText.isEnabled = false
    }

    private fun nextQuestion() {
        if (currentQuestionsIndex < (viewModel.questions.value?.size?.minus(1) ?: 0)) {
            currentQuestionsIndex++
            binding.rearrangeQuestionText.postDelayed({ displayQuestion() }, 100)
            binding.SubmitButton.isEnabled = true
            binding.answerText.isEnabled = true

        }
        if (currentQuestionsIndex == (viewModel.questions.value?.size?.minus(1) ?: 0)) {
            binding.nextButton.text = getString(R.string.Show_Result)
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SOURCE_CLASS", "Rearrangequestions")
                intent.putExtra("SCORE", score)
                intent.putExtra("NoOfQuestions", viewModel.questions.value?.size)
                intent.putExtra("levelId",levelId)
                intent.putExtra("lessonId",lessonId)
                startActivity(intent)
                finish()
            }
        }
    }
}