package com.projects.germanlanguageapp.ui.admin
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.ui.admin.questions.choose.ChooseRecyclerActivity
import com.projects.germanlanguageapp.ui.admin.questions.complete.CompleteRecyclerActivity
import com.projects.germanlanguageapp.ui.admin.questions.match.MatchRecyclerActivity
import com.projects.germanlanguageapp.ui.admin.questions.rearrange.RearrangeRecyclerActivity

class QuestionsActivity : AppCompatActivity() {
    private var levelId: Int = 0
    private var lessonId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)

        val chooseButton = findViewById<View>(R.id.choose_button)
        val rearrangeButton = findViewById<View>(R.id.rearrange_button)
        val matchButton = findViewById<View>(R.id.match_button)
        val completeButton = findViewById<View>(R.id.complete_button)


        rearrangeButton.setOnClickListener {
            val intent = Intent(this, RearrangeRecyclerActivity::class.java).apply {
                putExtra("levelId", levelId)
                putExtra("lessonId", lessonId)
            }
            startActivity(intent)
        }


        completeButton.setOnClickListener {
            val intent = Intent(this, CompleteRecyclerActivity::class.java).apply {
                putExtra("levelId", levelId)
                putExtra("lessonId", lessonId)
            }
            startActivity(intent)
        }


        chooseButton.setOnClickListener {
            val intent = Intent(this, ChooseRecyclerActivity::class.java).apply {
                putExtra("levelId", levelId)
                putExtra("lessonId", lessonId)
            }
            startActivity(intent)
        }


        matchButton.setOnClickListener {
            val intent = Intent(this, MatchRecyclerActivity::class.java).apply {
                putExtra("levelId", levelId)
                putExtra("lessonId", lessonId)
            }
            startActivity(intent)
        }
    }
}