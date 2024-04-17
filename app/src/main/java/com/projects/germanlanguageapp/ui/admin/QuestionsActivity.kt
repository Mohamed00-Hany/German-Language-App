package com.projects.germanlanguageapp.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.ui.admin.Questions.QuestionsRecyclerViewActivity
class QuestionsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        findViewById<View>(R.id.choose_button).setOnClickListener(this)
        findViewById<View>(R.id.rearrange_button).setOnClickListener(this)
        findViewById<View>(R.id.match_button).setOnClickListener(this)
        findViewById<View>(R.id.complete_button).setOnClickListener(this)
    }

    override fun onClick(view: View) {

        val intent = Intent(this, QuestionsRecyclerViewActivity::class.java)

        when (view.id) {
            R.id.choose_button -> intent.putExtra("buttonClicked", "choose")
            R.id.rearrange_button -> intent.putExtra("buttonClicked", "rearrange")
            R.id.match_button -> intent.putExtra("buttonClicked", "match")
            R.id.complete_button -> intent.putExtra("buttonClicked", "complete")
        }
        startActivity(intent)
    }
}