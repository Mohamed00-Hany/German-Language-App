package com.projects.germanlanguageapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.projects.germanlanguageapp.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val resultTextView: TextView = findViewById(R.id.result_text)
        val finishButton: Button = findViewById(R.id.finishButton)
        val restartButton: Button = findViewById(R.id.restartButton)
        val emotion: ImageView= findViewById(R.id.emotion)
        val score = intent.getIntExtra("SCORE", 0)
        val NoOfQuestions =intent.getIntExtra("NoOfQuestions",0)
        val sourceClass = intent.getStringExtra("SOURCE_CLASS")
        val halfScore = if (NoOfQuestions % 2 == 0) {
            NoOfQuestions / 2
        } else {
            (NoOfQuestions + 1) / 2
        }
        if (score <halfScore) {
            resultTextView.setTextColor(resources.getColor(R.color.red))
            emotion.setImageResource(R.drawable.sad)
        }
        resultTextView.text = "Ihr Ergebnis\n $score aus $NoOfQuestions"
        finishButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("destinationFragment", R.id.Questions_page)
            startActivity(intent)
        }

        restartButton.setOnClickListener {
            if (sourceClass == "choose_Q") {
                val intent = Intent(this, choose_Q::class.java)
                startActivity(intent)
            } else if (sourceClass == "rearrangequestions") {
                val intent = Intent(this, rearrangequestions::class.java)
                startActivity(intent)
            } else if (sourceClass == "MatchActivity") {
                val intent = Intent(this, MatchActivity::class.java)
                startActivity(intent)
            } else if (sourceClass == "complete_Q_Activity") {
                val intent = Intent(this, complete_Q_Activity::class.java)
                startActivity(intent)
            }
        }
    }

}