package com.projects.germanlanguageapp.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.projects.germanlanguageapp.R

class QuestionsOrWordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_or_words)
        val QuestionsButton: Button = findViewById(R.id.QuestionsButton)
        val WordsButton: Button = findViewById(R.id.WordsButton)
        QuestionsButton.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            startActivity(intent)
        }
        WordsButton.setOnClickListener {
            val intent = Intent(this, WordsActivity::class.java)
            startActivity(intent)
        }

    }

}