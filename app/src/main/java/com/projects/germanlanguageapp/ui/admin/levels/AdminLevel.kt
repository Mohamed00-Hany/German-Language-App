package com.projects.germanlanguageapp.ui.admin.levels
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityAdminLevelBinding
import com.projects.germanlanguageapp.domain.models.LevelsResponseItem
import com.projects.germanlanguageapp.ui.admin.lessons.LessonsAdminActivity
class AdminLevel : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLevelBinding
    private lateinit var levelAdminAdapter: LevelAdminAdapter
    private lateinit var levelsRecycler: RecyclerView
    private var levelsList = mutableListOf<LevelsResponseItem>(
        LevelsResponseItem(1, "Ebene 1"),
        LevelsResponseItem(2, "Ebene 2")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_level)
        levelAdminAdapter = LevelAdminAdapter()
        levelsRecycler = binding.levelsAdminRecycler
        levelsRecycler.adapter = levelAdminAdapter
        levelAdminAdapter.changeData(levelsList)

        levelAdminAdapter.onLevelClickListener = object : LevelAdminAdapter.OnLevelClick {
            override fun onClick(position: Int, levelId: Int?) {
                val intent = Intent(this@AdminLevel, LessonsAdminActivity::class.java)
                intent.putExtra("levelId", levelId)
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
            hint = "Geben Sie den Ebenennamen ein"
            maxLines = 1
           gravity=Gravity.CENTER
        }
        builder.setView(editText)
        builder.setPositiveButton("Hinzufügen") { dialogInterface, _ ->
            val levelName = editText.text.toString()
            addLevelToList(levelName)
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
    private fun addLevelToList(levelName: String) {
        val newLevel = LevelsResponseItem(levelsList.size + 1, levelName)
        levelsList.add(newLevel)
        levelAdminAdapter.addLevel(newLevel)
    }
}