package com.projects.germanlanguageapp.ui.admin
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.ui.admin.Questions.Complete.CompleteRecyclerView
import com.projects.germanlanguageapp.ui.admin.Questions.Rearrange.RearrangeRecyclerView

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

        chooseButton.setOnClickListener {}
        matchButton.setOnClickListener {}

        rearrangeButton.setOnClickListener {
            val intent = Intent(this, RearrangeRecyclerView::class.java).apply {
                putExtra("levelId", levelId)
                putExtra("lessonId", lessonId)
            }
            startActivity(intent)
        }



        completeButton.setOnClickListener {
            val intent = Intent(this, CompleteRecyclerView::class.java).apply {
                putExtra("levelId", levelId)
                putExtra("lessonId", lessonId)
            }
            startActivity(intent)
        }




    }
}