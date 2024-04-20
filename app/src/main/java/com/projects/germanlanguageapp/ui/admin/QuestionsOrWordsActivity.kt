package com.projects.germanlanguageapp.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.projects.germanlanguageapp.R

class QuestionsOrWordsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_or_words)
        val QuestionsButton: Button = findViewById(R.id.QuestionsButton)
        val WordsButton: Button = findViewById(R.id.WordsButton)
        val levelId = intent.getIntExtra("levelId", 0)
        val lessonId =intent.getIntExtra("lessonId", 0)

        QuestionsButton.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra("levelId", levelId)
            intent.putExtra("lessonId", lessonId)
            startActivity(intent)
        }

        WordsButton.setOnClickListener {
            val intent = Intent(this, WordsActivity::class.java)
            intent.putExtra("levelId", levelId)
            intent.putExtra("lessonId", lessonId)
            startActivity(intent)
        }
    }
}
