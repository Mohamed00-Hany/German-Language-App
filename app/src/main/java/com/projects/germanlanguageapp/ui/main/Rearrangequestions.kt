package com.projects.germanlanguageapp.ui.main

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityRearrangequestionsBinding

class rearrangequestions : AppCompatActivity() {
    private lateinit var binding: ActivityRearrangequestionsBinding
    private val questions =arrayOf("alt /bist /Wie /du/?","du /her /? /Wo /kommst",
        "dein /Lieblingsessen /? /ist /Was", "Hobbys /? /deine /sind /Was", "verheiratet /? /du /Bist")

    private val answers = arrayOf(
        "Wie alt bist du?", "Wo kommst du her?", "Was ist dein Lieblingsessen?",
        "Was sind deine Hobbys?", "Bist du verheiratet?"
    )

    private var currentQuestionsIndex = 0
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rearrangequestions)
        binding = ActivityRearrangequestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayQuestion()
        binding.nextButton.setOnClickListener { nextQuestion() }
        binding.SubmitButton.setOnClickListener { checkAnswer() }



    }
    private fun displayQuestion() {
        val questionNumber = currentQuestionsIndex + 1
        val questionText = "$questionNumber: ${questions[currentQuestionsIndex]}"
        binding.rearrangeQuestionText.text= questionText
        binding.answerText.text.clear()
        binding.answerResult.text = ""
    }

    private fun checkAnswer() {
        val correctAnswer = answers[currentQuestionsIndex].toLowerCase()
        val userAnswer = binding.answerText.text.toString().toLowerCase()
        if ( userAnswer== correctAnswer) {
            score++
            binding.answerResult.setTextColor(Color.GREEN)
            binding.answerResult.text="korrekte Antwort"
        } else {
            binding.answerResult.setTextColor(Color.RED)
            val wrongAnswer = "falsche Antwort \n${answers[currentQuestionsIndex]}"
            binding.answerResult.text=wrongAnswer
        }
        binding.SubmitButton.isEnabled=false
    }

    private fun nextQuestion() {
        if (currentQuestionsIndex < questions.size-1){
            currentQuestionsIndex++
            binding.rearrangeQuestionText.postDelayed({ displayQuestion() }, 100)
            binding.SubmitButton.isEnabled=true

        }
        if(currentQuestionsIndex ==questions.size-1){
            binding.nextButton.text=getString(R.string.Show_Result)
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SOURCE_CLASS", "rearrangequestions")
                intent.putExtra("SCORE", score)
                intent.putExtra("NoOfQuestions", questions.size)
                startActivity(intent)
            }


        }
    }

}