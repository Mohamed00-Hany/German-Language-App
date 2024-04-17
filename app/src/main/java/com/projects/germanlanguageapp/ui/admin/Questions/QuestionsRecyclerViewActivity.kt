package com.projects.germanlanguageapp.ui.admin.Questions

import QuestionsAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R

class QuestionsRecyclerViewActivity : AppCompatActivity() {
    private val questionsList: List<String> = listOf(
        "Questions 1", "Questions 2", "Questions 3", "Questions 4", "Questions 5",
        "Questions 6", "Questions 7", "Questions 8", "Questions 9", "Questions 10"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_recycler_view)

        val recyclerView: RecyclerView = findViewById(R.id.levels_admin_recycler)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = QuestionsAdapter(questionsList, object : QuestionsAdapter.QuestionClickListener {
            override fun onQuestionClick(position: Int) {

            }

            override fun onDeleteClick(position: Int) {

            }

            override fun onEditClick(position: Int) {

            }
        })
        recyclerView.adapter = adapter
    }


}
