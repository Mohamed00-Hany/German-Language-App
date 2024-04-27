package com.projects.germanlanguageapp.ui.admin.words
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityWordsAdminBinding
import com.projects.germanlanguageapp.ui.wordsdetails.WordsDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WordsAdminActivity : AppCompatActivity() {
    private var wordsType: String? = null
    private var levelId: Int = 0
    private var lessonId: Int = 0
    private var wordId: Int = 0
    private var wordname: String? = null
    private var wordtname: String? = null
    private val viewModel: WordsDetailsViewModel by viewModels()
    private lateinit var binding: ActivityWordsAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_words_admin)
        binding.vm = viewModel
        wordsType = intent.getStringExtra("wordsType")
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
        wordId = intent.getIntExtra("wordId", 0)
        wordname = intent.getStringExtra("wordname")
        wordtname = intent.getStringExtra("wordtname")
        val buttonClicked = intent.getStringExtra("buttonClicked")
        if (buttonClicked == "add") {
            binding.SubmitButton.visibility = View.VISIBLE
            binding.SubmitButton.setOnClickListener {
                val words = binding.words.text.toString()
                val translation = binding.wordsTranslation.text.toString()
                if (words.isNotEmpty() && translation.isNotEmpty()) {
                    lifecycleScope.launch {
                        viewModel.PostWord(wordsType!!, words, translation, levelId, lessonId)
                        setResult(RESULT_OK)
                        finish()
                    }
                } else {
                    Toast.makeText(this@WordsAdminActivity, "Please fill both fields", Toast.LENGTH_SHORT).show()
                }
            }
        }else if(buttonClicked == "Edit"){
            binding.SubmitButton.visibility = View.VISIBLE
            binding.words.setText(wordname)
            binding.wordsTranslation.setText(wordtname)
            binding.SubmitButton.setOnClickListener {
                val words = binding.words.text.toString()
                val translation = binding.wordsTranslation.text.toString()
                if (words.isNotEmpty() && translation.isNotEmpty()) {
                    lifecycleScope.launch {
                        viewModel.PutWord(wordsType!!, wordId, words, translation)
                        setResult(RESULT_OK)
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this@WordsAdminActivity,
                        "Please fill both fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }else{
            binding.SubmitButton.visibility = View.GONE
            binding.words.setText(wordname)
            binding.wordsTranslation.setText(wordtname)
            binding.words.isEnabled = false
            binding.wordsTranslation.isEnabled = false
        }

    }
}
