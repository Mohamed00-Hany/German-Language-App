package com.projects.germanlanguageapp.ui.chooseQuestions

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityChooseQBinding
import com.projects.germanlanguageapp.ui.main.ResultActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseQuestionsActivity : AppCompatActivity() {

    private val viewModel: ChooseQuestionsViewModel by viewModels()
    private lateinit var binding: ActivityChooseQBinding

    private var levelId = 0
    private var lessonId = 0

    private var currentQuestionsIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseQBinding.inflate(layoutInflater)
        setContentView(binding.root)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getChooseQuestions(levelId,lessonId)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.questions.collectLatest {
                if (it?.isNotEmpty() == true) {
                    displayQuestion()
                }
            }
        }
        binding.op1Button.setOnClickListener {
            checkAnswer(1)
            disableOptionButtons()
        }
        binding.op2Button.setOnClickListener {
            checkAnswer(2)
            disableOptionButtons()
        }
        binding.op3Button.setOnClickListener {
            checkAnswer(3)
            disableOptionButtons()
        }
        binding.op4Button.setOnClickListener {
            checkAnswer(4)
            disableOptionButtons()
        }
        binding.nextButton.setOnClickListener { nextQuestion() }
    }

    private fun correctButttonColors(buttonIndex: Int) {
        val colorStateList = ColorStateList.valueOf(Color.GREEN)
        when (buttonIndex) {
            1 -> binding.op1Button.backgroundTintList = colorStateList
            2 -> binding.op2Button.backgroundTintList = colorStateList
            3 -> binding.op3Button.backgroundTintList = colorStateList
            4 -> binding.op4Button.backgroundTintList = colorStateList
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        val colorStateList = ColorStateList.valueOf(Color.RED)
        when (buttonIndex) {
            1 -> binding.op1Button.backgroundTintList = colorStateList
            2 -> binding.op2Button.backgroundTintList = colorStateList
            3 -> binding.op3Button.backgroundTintList = colorStateList
            4 -> binding.op4Button.backgroundTintList = colorStateList
        }
    }

    private fun resetButtonColors() {
        val colorStateList = ContextCompat.getColorStateList(this, R.color.lead)
        binding.op1Button.backgroundTintList = colorStateList
        binding.op2Button.backgroundTintList = colorStateList
        binding.op3Button.backgroundTintList = colorStateList
        binding.op4Button.backgroundTintList = colorStateList
    }

    private fun displayQuestion() {
        val question = viewModel.questions.value!![currentQuestionsIndex]?.chooseNameQuestion
        binding.questionText.text = question
        try {
            binding.op1Button.text = viewModel.options[currentQuestionsIndex]!![0]
            binding.op1Button.isEnabled = true
        }
        catch (e:Exception) {
            binding.op1Button.text = ""
            binding.op1Button.isEnabled = false
        }
        try {
            binding.op2Button.text = viewModel.options[currentQuestionsIndex]!![1]
            binding.op2Button.isEnabled = true
        }
        catch (e:Exception) {
            binding.op2Button.text = ""
            binding.op2Button.isEnabled = false
        }
        try {
            binding.op3Button.text = viewModel.options[currentQuestionsIndex]!![2]
            binding.op3Button.isEnabled = true
        }
        catch (e:Exception) {
            binding.op3Button.text = ""
            binding.op3Button.isEnabled = false
        }
        try {
            binding.op4Button.text = viewModel.options[currentQuestionsIndex]!![3]
            binding.op4Button.isEnabled = true
        }
        catch (e:Exception) {
            binding.op4Button.text = ""
            binding.op4Button.isEnabled = false
        }
        resetButtonColors()
    }

    private fun disableOptionButtons() {
        binding.op1Button.isEnabled = false
        binding.op2Button.isEnabled = false
        binding.op3Button.isEnabled = false
        binding.op4Button.isEnabled = false
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = viewModel.correctAnswers!![currentQuestionsIndex]
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButttonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            if (correctAnswerIndex != null) {
                correctButttonColors(correctAnswerIndex)
            }
        }
    }

    private fun nextQuestion() {
        if (currentQuestionsIndex < (viewModel.questions.value?.size?.minus(1) ?: 0)){
            currentQuestionsIndex++
            displayQuestion()
        }
        if(currentQuestionsIndex == (viewModel.questions.value?.size?.minus(1) ?: 0)){
            binding.nextButton.text=getString(R.string.Show_Result)
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SOURCE_CLASS", "choose_Q")
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