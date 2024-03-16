package com.projects.germanlanguageapp.ui.main

import com.projects.germanlanguageapp.databinding.ActivityCompleteQactivityBinding
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.projects.germanlanguageapp.R

class complete_Q_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCompleteQactivityBinding
    private val questions = arrayOf(
        "- Hallo!.....bin Simon.....heißt du?\n"+
                "- Ich.....Rolf.\n"+"- Und.....kommst.....?\n"+
                "- Aus Osterreich?\n"+"- Nein, ich.....aus Deutschland.\n",
        "-Hallo! Ich bin Thomas,.....Wer.....du?\n- .....heiße Philipp.\n- Und woher.....du?\n"+
                "- Ich komme.....der Schweiz.\n" )

    private val answers = arrayOf(
        "ich - wie - bin - woher - du - komme", "und - bist - ich - kommst - aus")
    private var currentQuestionsIndex = 0
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_qactivity)
        binding = ActivityCompleteQactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayQuestion()
        binding.nextButton.setOnClickListener { nextQuestion() }
        binding.SubmitButton.setOnClickListener { checkAnswer() }
    }

    private fun displayQuestion() {
        val questionText = "${questions[currentQuestionsIndex]}"
        binding.completeQuestionText.text= questionText
        binding.completeQuestionText.movementMethod = ScrollingMovementMethod.getInstance()
        binding.answerText.text.clear()
        binding.answerResult.text = ""
    }
    private fun checkAnswer() {
        val correctAnswer = answers[currentQuestionsIndex].toLowerCase().replace("\\s".toRegex(), "")
        val userAnswer = binding.answerText.text.toString().toLowerCase().replace("\\s".toRegex(), "")
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
            binding.completeQuestionText.postDelayed({ displayQuestion() }, 100)
            binding.SubmitButton.isEnabled=true

        }
        if(currentQuestionsIndex ==questions.size-1){
            binding.nextButton.text=getString(R.string.Show_Result)
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SOURCE_CLASS", "complete_Q_Activity")
                intent.putExtra("SCORE", score)
                intent.putExtra("NoOfQuestions", questions.size)
                startActivity(intent)
            }


        }
    }
}