package com.projects.germanlanguageapp.ui.matchQuestions
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityMatchBinding
import com.projects.germanlanguageapp.ui.main.ResultActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchActivity : AppCompatActivity() {
    private var levelId = 0
    private var lessonId = 0
    private val matchViewModel: MatchQuestionsViewModel by viewModels()
    private var currentQuestionsIndex = 0
    private var score = 0
    private lateinit var binding: ActivityMatchBinding
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        lifecycleScope.launch(Dispatchers.IO) {
            matchViewModel.getMatchQuestions(levelId,lessonId)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            matchViewModel.matchQuestions.collectLatest {
                if (it?.isNotEmpty() == true) {
                    matchViewModel.getQuestionsAndAnswers()
                    displayQuestion()
                }
            }
        }

        binding.SubmitButton.setOnClickListener { checkAnswer() }
        binding.nextButton.setOnClickListener { nextQuestion() }
    }

    private fun displayQuestion() {
        if (currentQuestionsIndex < matchViewModel.questions.size) {
            val currentQuestion = matchViewModel.questions[currentQuestionsIndex] ?: emptyList<String>()
            val currentOptions = matchViewModel.options[currentQuestionsIndex] ?: emptyList<String>()
            resetButtonColors()
            binding.statement1TextView.text = "1: ${currentQuestion?.get(0)}"
            binding.statement1TextView.movementMethod= ScrollingMovementMethod.getInstance()
            binding.statement2TextView.text = "2: ${currentQuestion?.get(1)}"
            binding.statement2TextView.movementMethod= ScrollingMovementMethod.getInstance()
            binding.statement3TextView.text = "3: ${currentQuestion?.get(2)}"
            binding.statement3TextView.movementMethod= ScrollingMovementMethod.getInstance()
            binding.statement4TextView.text = "4: ${currentQuestion?.get(3)}"
            binding.statement4TextView.movementMethod= ScrollingMovementMethod.getInstance()


            adapter = ArrayAdapter(this@MatchActivity, R.layout.custom_spinner_item, currentOptions)
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
        try {
            val correctMatchesForQuestion = matchViewModel.correctMatches[currentQuestionsIndex]


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
        catch (e:Exception) {
            Log.e("MatchQuestions", "Error Match Questions: ${e.message}")
        }
    }


    private fun nextQuestion() {
        if (currentQuestionsIndex < matchViewModel.questions.size - 1) {
            currentQuestionsIndex++
            displayQuestion()
            binding.statement1Spinner.isEnabled = true
            binding.statement2Spinner.isEnabled = true
            binding.statement3Spinner.isEnabled = true
            binding.statement4Spinner.isEnabled = true
            binding.SubmitButton.isEnabled = true
        }
        if (currentQuestionsIndex == matchViewModel.questions.size - 1) {
            binding.nextButton.text = getString(R.string.Show_Result)
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SOURCE_CLASS", "MatchActivity")
                intent.putExtra("SCORE", score)
                intent.putExtra("NoOfQuestions", matchViewModel.questions.size)
                intent.putExtra("levelId",levelId)
                intent.putExtra("lessonId",lessonId)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun resetButtonColors() {
        val colorStateList = ContextCompat.getColorStateList(this, R.color.black)
        binding.statement1TextView.setTextColor(colorStateList)
        binding.statement2TextView.setTextColor(colorStateList)
        binding.statement3TextView.setTextColor(colorStateList)
        binding.statement4TextView.setTextColor(colorStateList)
    }
}