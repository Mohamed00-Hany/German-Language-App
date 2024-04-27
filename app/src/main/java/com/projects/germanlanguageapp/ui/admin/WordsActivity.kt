package com.projects.germanlanguageapp.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.ui.admin.words.WordsRecyclerActivity

class WordsActivity : AppCompatActivity(), View.OnClickListener {

    private var levelId: Int = 0
    private var lessonId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        levelId = intent.getIntExtra("levelId", 0)
        lessonId = intent.getIntExtra("lessonId", 0)

        findViewById<View>(R.id.Nouns_button).setOnClickListener(this)
        findViewById<View>(R.id.adjectives_button).setOnClickListener(this)
        findViewById<View>(R.id.Verbs_button).setOnClickListener(this)
        findViewById<View>(R.id.Other_button).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = Intent(this, WordsRecyclerActivity::class.java)
        intent.putExtra("levelId", levelId)
        intent.putExtra("lessonId", lessonId)

        when (view.id) {
            R.id.Nouns_button -> intent.putExtra("wordsType", "nouns")
            R.id.adjectives_button -> intent.putExtra("wordsType", "adjectives")
            R.id.Verbs_button -> intent.putExtra("wordsType", "verbs")
            R.id.Other_button -> intent.putExtra("wordsType", "others")
        }

        startActivity(intent)
    }
}