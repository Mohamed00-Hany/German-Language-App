package com.projects.germanlanguageapp.ui.lessons

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityLessonsBinding
import com.projects.germanlanguageapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonsActivity:AppCompatActivity() {
    val viewModel: LessonsViewModel by viewModels()
    private lateinit var binding: ActivityLessonsBinding
    private lateinit var lessonsAdapter: LessonsAdapter
    private lateinit var lessonsRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_lessons)
        binding.vm=viewModel
        lessonsAdapter= LessonsAdapter(viewModel.lessons.value)
        lessonsRecycler=binding.lessonsRecycler
        lessonsRecycler.adapter=lessonsAdapter
        lessonsAdapter.onLessonClickListener=object : LessonsAdapter.OnLessonClick {
            override fun onClick(position: Int, lessonName: String?) {
                val levelId=intent.getIntExtra("levelId",0)
                val intent = Intent(this@LessonsActivity,MainActivity::class.java)
                intent.putExtra("levelId",levelId)
                intent.putExtra("lessonId",position)
                startActivity(intent)
            }
        }
    }
}