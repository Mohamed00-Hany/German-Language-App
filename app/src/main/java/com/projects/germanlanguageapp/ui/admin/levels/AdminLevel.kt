package com.projects.germanlanguageapp.ui.admin.levels
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityAdminLevelBinding
import com.projects.germanlanguageapp.ui.admin.lessons.LessonsAdminActivity
import com.projects.germanlanguageapp.ui.levels.LevelsAdapter
import com.projects.germanlanguageapp.ui.levels.LevelsViewModel
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class AdminLevel : AppCompatActivity() {
    private val viewModel: LevelsViewModel by viewModels()
    private lateinit var binding: ActivityAdminLevelBinding
    private lateinit var levelsAdapter: LevelsAdapter
    private lateinit var levelsRecycler: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_level)
        binding.vm=viewModel
        viewModel.getLevels()
        levelsAdapter=LevelsAdapter(viewModel.levels.value)
        lifecycleScope.launch {
            viewModel.levels.collect {
                levelsAdapter.changeData(it)
            }
        }
        levelsRecycler=binding.levelsAdminRecycler
        levelsRecycler.adapter=levelsAdapter

        levelsAdapter.onLevelClickListener=object : LevelsAdapter.OnLevelClick {
            override fun onClick(position: Int, levelId: Int?) {
                val intent=Intent(this@AdminLevel, LessonsAdminActivity::class.java)
                intent.putExtra("levelId",levelId)
                startActivity(intent)
            }
        }
        val addButton: ImageButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            showAddLevelDialog()
        }
    }

    private fun showAddLevelDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ebene hinzufügen")
        val editText = EditText(this).apply {
            maxLines = 1
            gravity = Gravity.CENTER
        }
        builder.setView(editText)
        builder.setPositiveButton("Hinzufügen") { dialogInterface, _ ->
            val levelName = editText.text.toString().trim()
            if (levelName.isNotEmpty()) {
                lifecycleScope.launch {
                    viewModel.postLevels(levelName)
                    viewModel.getLevels()
                    levelsAdapter.updateData(viewModel.levels.value)

                }
            } else {
                Toast.makeText(this@AdminLevel, "Please enter a level name", Toast.LENGTH_SHORT).show()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Abbrechen") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.apply {
            isAllCaps = false
        }
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.apply {
            isAllCaps = false
        }

    }



}