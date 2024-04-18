package com.projects.germanlanguageapp.ui.levels

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityLevelsBinding
import com.projects.germanlanguageapp.ui.lessons.LessonsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LevelsActivity: AppCompatActivity() {
    private val viewModel:LevelsViewModel by viewModels()
    private lateinit var binding: ActivityLevelsBinding
    private lateinit var levelsAdapter:LevelsAdapter
    private lateinit var levelsRecycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_levels)
        binding.vm=viewModel
        viewModel.getLevels()
        levelsAdapter=LevelsAdapter(viewModel.levels.value)
        lifecycleScope.launch {
            viewModel.levels.collect {
                levelsAdapter.changeData(it)
            }
        }
        levelsRecycler=binding.levelsRecycler
        levelsRecycler.adapter=levelsAdapter
        levelsAdapter.onLevelClickListener=object : LevelsAdapter.OnLevelClick {
            override fun onClick(position: Int, levelId: Int?) {
                val intent=Intent(this@LevelsActivity,LessonsActivity::class.java)
                intent.putExtra("levelId",levelId)
                startActivity(intent)
            }
        }
    }
}