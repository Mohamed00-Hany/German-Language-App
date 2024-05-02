package com.projects.germanlanguageapp.ui.admin.questions.choose
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityChooseAdminBinding
import com.projects.germanlanguageapp.ui.chooseQuestions.ChooseQuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ChooseAdminActivity : AppCompatActivity() {
    private val ChooseviewModel: ChooseQuestionsViewModel by viewModels()
    private var levelId: Int = 0
    private var lessonId: Int = 0
    private var  ChooseNameQuestion: String? = null
    private var  ChooseNameAnswer: String? = null
    private var  ChooseNameAnswerRight: Int = 0
    private var  ChooseId: Int = 0
    private lateinit var binding: ActivityChooseAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_admin)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        ChooseId = intent.getIntExtra("ChooseId", 0)
        ChooseNameQuestion = intent.getStringExtra("ChooseNameQuestion")
        ChooseNameAnswer = intent.getStringExtra("ChooseNameAnswer")
        ChooseNameAnswerRight = intent.getIntExtra("ChooseNameAnswerRight", 0)
        val buttonClicked = intent.getStringExtra("buttonClicked")
        if (buttonClicked == "add") {
            binding.SubmitButton.visibility = View.VISIBLE
            binding.SubmitButton.setOnClickListener {
                val questions = binding.edittext1.text.toString()
                val Answer = binding.edittext2.text.toString()
                val AnswerRight = binding.edittext3.text.toString().toIntOrNull()
                if (  Answer.isNotEmpty()&& Answer.isNotEmpty()&&AnswerRight!=null) {
                        lifecycleScope.launch {
                            ChooseviewModel.PostChooseQuestions(
                                questions,
                                Answer,
                                AnswerRight,
                                levelId,
                                lessonId
                            )
                            setResult(RESULT_OK)
                            finish()
                        }
        } else {
                    Toast.makeText(
                        this@ChooseAdminActivity,
                        "Bitte füllen Sie drei Felder aus",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }else if (buttonClicked == "Edit") {
            binding.SubmitButton.visibility = View.VISIBLE
            binding.edittext1.setText(ChooseNameQuestion)
            binding.edittext2.setText(ChooseNameAnswer)
            binding.edittext3.setText(ChooseNameAnswerRight.toString())
            binding.SubmitButton.setOnClickListener {
                val questions = binding.edittext1.text.toString()
                val Answer = binding.edittext2.text.toString()
                val AnswerRight = binding.edittext3.text.toString().toIntOrNull()
                if (  Answer.isNotEmpty()&& Answer.isNotEmpty()&&AnswerRight!=null) {
                    lifecycleScope.launch {
                        ChooseviewModel.PutChooseQuestions(
                            ChooseId,
                            questions,
                            Answer,
                            AnswerRight
                        )
                        setResult(RESULT_OK)
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this@ChooseAdminActivity,
                        "Bitte füllen Sie drei Felder aus",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else{
            binding.SubmitButton.visibility = View.GONE
            binding.edittext1.setText(ChooseNameQuestion)
            binding.edittext2.setText(ChooseNameAnswer)
            binding.edittext3.setText(ChooseNameAnswerRight.toString())
            binding.edittext1.isEnabled = false
            binding.edittext2.isEnabled = false
            binding.edittext3.isEnabled = false
        }


}
}