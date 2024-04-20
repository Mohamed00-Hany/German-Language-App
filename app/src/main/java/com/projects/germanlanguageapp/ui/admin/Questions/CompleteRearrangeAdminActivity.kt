package com.projects.germanlanguageapp.ui.admin.Questions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.projects.germanlanguageapp.R

class CompleteRearrangeAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_rearrange_admin)
        val buttonClicked = intent.getStringExtra("buttonClicked")
        val submitButton = findViewById<Button>(R.id.Submit_button)
        if (buttonClicked == "add" || buttonClicked == "Edit") {
            submitButton.visibility = View.VISIBLE
        } else {
            submitButton.visibility = View.GONE
        }
    }
}