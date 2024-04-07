package com.projects.germanlanguageapp.ui.main

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityMatchBinding

class MatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchBinding
    private val questions = arrayOf(
        arrayOf("Guten Morgen", "Guten Tag", "Gute Nacht", "Guten Abend"),
        arrayOf("Wie geht es dir?", "Was machst du?", "Woher kommst du?", "Wie spät ist es?"),
        arrayOf("Guten Morgen", "Guten Tag", "Gute Nacht", "Guten Abend")
    )

    private val options = arrayOf(
        arrayOf("15:00 Uhr", "9:00 Uhr", "22:00 Uhr", "bevor schlafen"),
        arrayOf(
            "Es ist drei Uhr.",
            "Ich komme aus Deutschland.",
            "Ich lese ein Buch.",
            "Mir geht es gut."
        ),
        arrayOf("15:00 Uhr", "9:00 Uhr", "22:00 Uhr", "bevor schlafen")
    )
    private val correctMatches = arrayOf(
        mapOf(0 to 1, 1 to 0, 2 to 3, 3 to 2),
        mapOf(0 to 3, 1 to 2, 2 to 1, 3 to 0),
        mapOf(0 to 1, 1 to 0, 2 to 3, 3 to 2)
    )
    private var currentQuestionsIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.SubmitButton.setOnClickListener { checkAnswer() }
        binding.nextButton.setOnClickListener { nextQuestion() }
    }

    private fun displayQuestion() {
        if (currentQuestionsIndex < questions.size) {
            val currentQuestion = questions[currentQuestionsIndex]
            val currentOptions = options[currentQuestionsIndex]
            resetButtonColors()
            binding.statement1TextView.text = "1: ${currentQuestion[0]}"
            binding.statement1TextView.movementMethod= ScrollingMovementMethod.getInstance()
            binding.statement2TextView.text = "2: ${currentQuestion[1]}"
            binding.statement2TextView.movementMethod= ScrollingMovementMethod.getInstance()
            binding.statement3TextView.text = "3: ${currentQuestion[2]}"
            binding.statement3TextView.movementMethod= ScrollingMovementMethod.getInstance()
            binding.statement4TextView.text = "4: ${currentQuestion[3]}"
            binding.statement4TextView.movementMethod= ScrollingMovementMethod.getInstance()


            val adapter = ArrayAdapter(this, R.layout.custom_spinner_item, currentOptions)

            adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked)

            binding.statement1Spinner.adapter = adapter
            binding.statement2Spinner.adapter = adapter
            binding.statement3Spinner.adapter = adapter
            binding.statement4Spinner.adapter = adapter
        }
    }

    private fun checkAnswer() {
        val selectedIndices = intArrayOf(
            binding.statement1Spinner.selectedItemPosition,
            binding.statement2Spinner.selectedItemPosition,
            binding.statement3Spinner.selectedItemPosition,
            binding.statement4Spinner.selectedItemPosition
        )

        val correctMatchesForQuestion = correctMatches[currentQuestionsIndex]

        var isCorrect = true
        for ((index, optionIndex) in selectedIndices.withIndex()) {
            if (correctMatchesForQuestion[index] != optionIndex) {
                isCorrect = false
                break
            }
        }

        if (isCorrect) {
            score++
            binding.statement1TextView.setTextColor(Color.GREEN)
            binding.statement2TextView.setTextColor(Color.GREEN)
            binding.statement3TextView.setTextColor(Color.GREEN)
            binding.statement4TextView.setTextColor(Color.GREEN)
        } else {

            binding.statement1Spinner.setSelection(correctMatchesForQuestion[0] ?: 0)
            binding.statement2Spinner.setSelection(correctMatchesForQuestion[1] ?: 0)
            binding.statement3Spinner.setSelection(correctMatchesForQuestion[2] ?: 0)
            binding.statement4Spinner.setSelection(correctMatchesForQuestion[3] ?: 0)


            binding.statement1TextView.setTextColor(if (correctMatchesForQuestion[0] == selectedIndices[0]) Color.GREEN else Color.RED)
            binding.statement2TextView.setTextColor(if (correctMatchesForQuestion[1] == selectedIndices[1]) Color.GREEN else Color.RED)
            binding.statement3TextView.setTextColor(if (correctMatchesForQuestion[2] == selectedIndices[2]) Color.GREEN else Color.RED)
            binding.statement4TextView.setTextColor(if (correctMatchesForQuestion[3] == selectedIndices[3]) Color.GREEN else Color.RED)
        }
        binding.statement1Spinner.isEnabled = false
        binding.statement2Spinner.isEnabled = false
        binding.statement3Spinner.isEnabled = false
        binding.statement4Spinner.isEnabled = false
        binding.SubmitButton.isEnabled = false
    }


    private fun nextQuestion() {
        if (currentQuestionsIndex < questions.size - 1) {
            currentQuestionsIndex++
            displayQuestion()
            binding.statement1Spinner.isEnabled = true
            binding.statement2Spinner.isEnabled = true
            binding.statement3Spinner.isEnabled = true
            binding.statement4Spinner.isEnabled = true
            binding.SubmitButton.isEnabled = true
        }
        if (currentQuestionsIndex == questions.size - 1) {
            binding.nextButton.text = getString(R.string.Show_Result)
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SOURCE_CLASS", "MatchActivity")
                intent.putExtra("SCORE", score)
                intent.putExtra("NoOfQuestions", questions.size)
                startActivity(intent)
            }
        }
    }

    private fun resetButtonColors() {
        val colorStateList = ContextCompat.getColorStateList(this, R.color.greeen1)

        binding.statement1TextView.setTextColor(colorStateList)
        binding.statement2TextView.setTextColor(colorStateList)
        binding.statement3TextView.setTextColor(colorStateList)
        binding.statement4TextView.setTextColor(colorStateList)
    }
}