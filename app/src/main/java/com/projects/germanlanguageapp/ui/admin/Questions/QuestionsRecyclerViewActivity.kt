package com.projects.germanlanguageapp.ui.admin.Questions
import QuestionsAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
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
        val buttonClicked = intent.getStringExtra("buttonClicked")
        recyclerView.layoutManager = layoutManager
        val adapter = QuestionsAdapter(questionsList, object : QuestionsAdapter.QuestionClickListener {
            override fun onQuestionClick(position: Int) {
                if (buttonClicked == "choose") {
                    openChooseAdminActivity("Question")
                } else if (buttonClicked == "match"){
                    openMatchAdminActivity("Question")
                }else{
                    openCompleteRearrangeAdminActivity("Question")
                }
            }

            override fun onDeleteClick(position: Int) {

            }

            override fun onEditClick(position: Int) {
                if (buttonClicked == "choose") {
                    openChooseAdminActivity("Edit")
                } else if (buttonClicked == "match"){
                    openMatchAdminActivity("Edit")
                }else{
                    openCompleteRearrangeAdminActivity("Edit")
                }
            }
        })
        recyclerView.adapter = adapter

        val addButton = findViewById<ImageButton>(R.id.add_button)
        addButton.setOnClickListener {
            if (buttonClicked == "choose") {
                openChooseAdminActivity("add")
            }
            else if (buttonClicked == "match"){
                openMatchAdminActivity("add")
        }else{
            openCompleteRearrangeAdminActivity("add")
        }

        }
    }

    private fun openChooseAdminActivity(buttonClicked: String) {
        val intent = Intent(this, ChooseAdminActivity::class.java)
        intent.putExtra("buttonClicked", buttonClicked)
        startActivity(intent)
    }

    private fun openMatchAdminActivity(buttonClicked: String) {
        val intent = Intent(this, MatchAdminActivity::class.java)
        intent.putExtra("buttonClicked", buttonClicked)
        startActivity(intent)
    }
    private fun openCompleteRearrangeAdminActivity(buttonClicked: String) {
        val intent = Intent(this, CompleteRearrangeAdminActivity::class.java)
        intent.putExtra("buttonClicked", buttonClicked)
        startActivity(intent)
    }
}



