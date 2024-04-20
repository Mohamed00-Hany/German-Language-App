package com.projects.germanlanguageapp.ui.main
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.ui.rearrangeQuestions.RearrangeQuestionsActivity


class ResultActivity : AppCompatActivity() {

    private var levelId = 0
    private var lessonId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)
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
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        restartButton.setOnClickListener {
            if (sourceClass == "choose_Q") {
                val intent = Intent(this, choose_Q::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("levelId",levelId)
                intent.putExtra("lessonId",lessonId)
                startActivity(intent)
            } else if (sourceClass == "Rearrangequestions") {
                val intent = Intent(this, RearrangeQuestionsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("levelId",levelId)
                intent.putExtra("lessonId",lessonId)
                startActivity(intent)
            } else if (sourceClass == "MatchActivity") {
                val intent = Intent(this, MatchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("levelId",levelId)
                intent.putExtra("lessonId",lessonId)
                startActivity(intent)
            } else if (sourceClass == "complete_Q_Activity") {
                val intent = Intent(this, complete_Q_Activity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("levelId",levelId)
                intent.putExtra("lessonId",lessonId)
                startActivity(intent)
            }
            finish()
        }

    }


}