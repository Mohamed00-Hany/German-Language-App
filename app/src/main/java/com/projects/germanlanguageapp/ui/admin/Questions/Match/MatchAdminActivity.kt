package com.projects.germanlanguageapp.ui.admin.Questions.Match
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityMatchAdminBinding
import com.projects.germanlanguageapp.ui.matchQuestions.MatchQuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MatchAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchAdminBinding
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_admin)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        MatchId = intent.getIntExtra("MatchId", 0)
        MatchNameQuestion1 = intent.getStringExtra("MatchNameQuestion1")
        MatchNameAnswer1 = intent.getStringExtra("MatchNameAnswer1")
        MatchNameQuestion2 = intent.getStringExtra("MatchNameQuestion2")
        MatchNameAnswer2 = intent.getStringExtra("MatchNameAnswer2")
        MatchNameQuestion3 = intent.getStringExtra("MatchNameQuestion3")
        MatchNameAnswer3 = intent.getStringExtra("MatchNameAnswer3")
        MatchNameQuestion4 = intent.getStringExtra("MatchNameQuestion4")
        MatchNameAnswer4 = intent.getStringExtra("MatchNameAnswer4")
        val buttonClicked = intent.getStringExtra("buttonClicked")
        if (buttonClicked == "add") {
            binding.SubmitButton.visibility = View.VISIBLE
            binding.SubmitButton.setOnClickListener {
                val question1 = binding.Qusestions1EditText.text.toString()
                val answer1 = binding.Answer1EditText.text.toString()
                val question2 = binding.Qusestions2EditText.text.toString()
                val answer2 = binding.Answer2EditText.text.toString()
                val question3 = binding.Qusestions3EditText.text.toString()
                val answer3 = binding.Answer3EditText.text.toString()
                val question4 = binding.Qusestions4EditText.text.toString()
                val answer4 = binding.Answer4EditText.text.toString()
                if (question1.isNotEmpty() && answer1.isNotEmpty() &&
                    question2.isNotEmpty() && answer2.isNotEmpty() &&
                    question3.isNotEmpty() && answer3.isNotEmpty() &&
                    question4.isNotEmpty() && answer4.isNotEmpty() )
                {
                    lifecycleScope.launch {
                        matchviewModel.PostMatchQuestions(
                            question1,
                            answer1,
                            question2,
                            answer2,
                            question3,
                            answer3,
                            question4,
                            answer4,
                            levelId,
                            lessonId
                        )
                        setResult(RESULT_OK)
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this@MatchAdminActivity,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else if (buttonClicked == "Edit") {
            binding.SubmitButton.visibility = View.VISIBLE
            binding.Qusestions1EditText.setText(MatchNameQuestion1)
            binding.Answer1EditText.setText(MatchNameAnswer1)
            binding.Qusestions2EditText.setText(MatchNameQuestion2)
            binding.Answer2EditText.setText(MatchNameAnswer2)
            binding.Qusestions3EditText.setText(MatchNameQuestion3)
            binding.Answer3EditText.setText(MatchNameAnswer3)
            binding.Qusestions4EditText.setText(MatchNameQuestion1)
            binding.Answer4EditText.setText(MatchNameAnswer4)
            binding.SubmitButton.setOnClickListener {
                val question1 = binding.Qusestions1EditText.text.toString()
                val answer1 = binding.Answer1EditText.text.toString()
                val question2 = binding.Qusestions2EditText.text.toString()
                val answer2 = binding.Answer2EditText.text.toString()
                val question3 = binding.Qusestions3EditText.text.toString()
                val answer3 = binding.Answer3EditText.text.toString()
                val question4 = binding.Qusestions4EditText.text.toString()
                val answer4 = binding.Answer4EditText.text.toString()
                if (question1.isNotEmpty() && answer1.isNotEmpty() &&
                    question2.isNotEmpty() && answer2.isNotEmpty() &&
                    question3.isNotEmpty() && answer3.isNotEmpty() &&
                    question4.isNotEmpty() && answer4.isNotEmpty() )
                {
                    lifecycleScope.launch {
                        matchviewModel.PutMatchQuestions(
                            MatchId,
                            question1,
                            answer1,
                            question2,
                            answer2,
                            question3,
                            answer3,
                            question4,
                            answer4
                        )
                        setResult(RESULT_OK)
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this@MatchAdminActivity,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else{
            binding.SubmitButton.visibility = View.GONE
            binding.Qusestions1EditText.setText(MatchNameQuestion1)
            binding.Answer1EditText.setText(MatchNameAnswer1)
            binding.Qusestions2EditText.setText(MatchNameQuestion2)
            binding.Answer2EditText.setText(MatchNameAnswer2)
            binding.Qusestions3EditText.setText(MatchNameQuestion3)
            binding.Answer3EditText.setText(MatchNameAnswer3)
            binding.Qusestions4EditText.setText(MatchNameQuestion1)
            binding.Answer4EditText.setText(MatchNameAnswer4)
            binding.Qusestions1EditText.isEnabled = false
            binding.Answer1EditText.isEnabled = false
            binding.Qusestions2EditText.isEnabled = false
            binding.Answer2EditText.isEnabled = false
            binding.Qusestions3EditText.isEnabled = false
            binding.Answer3EditText.isEnabled = false
            binding.Qusestions4EditText.isEnabled = false
            binding.Answer4EditText.isEnabled = false
        }
    }
}