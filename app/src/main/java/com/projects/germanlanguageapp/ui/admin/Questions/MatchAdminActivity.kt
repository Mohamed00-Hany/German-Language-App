package com.projects.germanlanguageapp.ui.admin.Questions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.projects.germanlanguageapp.R

class MatchAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val buttonClicked = intent.getStringExtra("buttonClicked")
        setContentView(R.layout.activity_match_admin)
        val submitButton = findViewById<Button>(R.id.Submit_button)
        if (buttonClicked == "add" || buttonClicked == "Edit") {
            submitButton.visibility = View.VISIBLE
        } else {
            submitButton.visibility = View.GONE
        }
    }
}