package com.projects.germanlanguageapp.ui.main

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.core.content.ContextCompat
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityChooseQBinding

class choose_Q : AppCompatActivity() {
    private lateinit var binding: ActivityChooseQBinding
    private val questions = arrayOf(
        "Was sagt man am Morgen", "Was sagt man am Abend", "Was sagt man am Nacht",
        "Was sagt man in der Nacht", "wie verabschiedet auf Deutsch"
    )
    private val options = arrayOf(
        arrayOf("Guten Tag", "Guten Abend", "Gute Nacht", "Guten Morgen"),
        arrayOf("Guten Tag", "Guten Abend", "Gute Nacht", "Guten Morgen"),
        arrayOf("Guten Tag", "Guten Abend", "Gute Nacht", "Guten Morgen"),
        arrayOf("Guten Tag", "Guten Abend", "Gute Nacht", "Guten Morgen"),
        arrayOf("Guten Tag", "Guten Abend", "auf Wiedersehen", "Guten Morgen")
    )
    private val correctAnswer = arrayOf(3, 1, 2, 2, 2)
    private var currentQuestionsIndex = 0
    private var score = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseQBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayQuestion()
        binding.op1Button.setOnClickListener {
            checkAnswer(0)
            disableOptionButtons()
        }
        binding.op2Button.setOnClickListener {
            checkAnswer(1)
            disableOptionButtons()
        }
        binding.op3Button.setOnClickListener {
            checkAnswer(2)
            disableOptionButtons()
        }
        binding.op4Button.setOnClickListener {
            checkAnswer(3)
            disableOptionButtons()
        }
        binding.nextButton.setOnClickListener { nextQuestion() }
    }

    private fun correctButttonColors(buttonIndex: Int) {
        val colorStateList = ColorStateList.valueOf(Color.GREEN)
        when (buttonIndex) {
            0 -> binding.op1Button.backgroundTintList = colorStateList
            1 -> binding.op2Button.backgroundTintList = colorStateList
            2 -> binding.op3Button.backgroundTintList = colorStateList
            3 -> binding.op4Button.backgroundTintList = colorStateList
        }
    }

    private fun wrongButttonColors(buttonIndex: Int) {
        val colorStateList = ColorStateList.valueOf(Color.RED)
        when (buttonIndex) {
            0 -> binding.op1Button.backgroundTintList = colorStateList
            1 -> binding.op2Button.backgroundTintList = colorStateList
            2 -> binding.op3Button.backgroundTintList = colorStateList
            3 -> binding.op4Button.backgroundTintList = colorStateList

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
        val questionNumber = currentQuestionsIndex + 1
        val questionText = "$questionNumber: ${questions[currentQuestionsIndex]}"
        binding.questionText.text = questionText
        binding.questionText.movementMethod = ScrollingMovementMethod.getInstance()
        binding.op1Button.text = options[currentQuestionsIndex][0]
        binding.op2Button.text = options[currentQuestionsIndex][1]
        binding.op3Button.text = options[currentQuestionsIndex][2]
        binding.op4Button.text = options[currentQuestionsIndex][3]
        resetButtonColors()
    }
    private fun disableOptionButtons() {
        binding.op1Button.isEnabled = false
        binding.op2Button.isEnabled = false
        binding.op3Button.isEnabled = false
        binding.op4Button.isEnabled = false
    }
    private fun ableOptionButtons() {
        binding.op1Button.isEnabled = true
        binding.op2Button.isEnabled = true
        binding.op3Button.isEnabled = true
        binding.op4Button.isEnabled = true
    }
    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswer[currentQuestionsIndex]
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButttonColors(selectedAnswerIndex)
        } else {
            wrongButttonColors(selectedAnswerIndex)
            correctButttonColors(correctAnswerIndex)
        }
    }

    private fun nextQuestion() {
        if (currentQuestionsIndex < questions.size-1){
            currentQuestionsIndex++
            binding.questionText.postDelayed({ displayQuestion() }, 100)
            ableOptionButtons()
        }
        if(currentQuestionsIndex ==questions.size-1){
            binding.nextButton.text=getString(R.string.Show_Result)
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SOURCE_CLASS", "choose_Q")
                intent.putExtra("SCORE", score)
                intent.putExtra("NoOfQuestions", questions.size)
                startActivity(intent)
            }
        }
    }
}