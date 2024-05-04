package com.projects.germanlanguageapp.ui.admin.levels
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityAdminLevelsBinding
import com.projects.germanlanguageapp.ui.admin.lessons.LessonsAdminActivity
import com.projects.germanlanguageapp.ui.levels.LevelsAdapter
import com.projects.germanlanguageapp.ui.levels.LevelsViewModel
import com.projects.germanlanguageapp.ui.studentoradmin.StudentOrAdminActivity
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LevelsAdminActivity : AppCompatActivity() {
    private val viewModel: LevelsViewModel by viewModels()
    private lateinit var binding: ActivityAdminLevelsBinding
    private lateinit var levelsAdapter: LevelsAdapter
    private lateinit var levelsRecycler: RecyclerView
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_levels)
        binding.vm=viewModel
        viewModel.getLevels()
        levelsAdapter=LevelsAdapter(viewModel.levels.value)
        showLoading("Wird geladen...")
        lifecycleScope.launch {
            viewModel.levels.collect {
                if (it?.isEmpty()==false) {
                    hideLoading()
                    levelsAdapter.changeData(it)
                }
            }
        }
        levelsRecycler=binding.levelsAdminRecycler
        levelsRecycler.adapter=levelsAdapter
        val layoutManager = LinearLayoutManager(this)
        levelsRecycler.layoutManager = layoutManager

        levelsAdapter.onLevelClickListener=object : LevelsAdapter.OnLevelClick {
            override fun onClick(position: Int, levelId: Int?) {
                val intent=Intent(this@LevelsAdminActivity, LessonsAdminActivity::class.java)
                intent.putExtra("levelId",levelId)
                startActivity(intent)
            }
        }
        val addButton: ImageButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            showAddLevelDialog()
        }
        binding.icLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Sind Sie sicher, dass Sie sich abmelden möchten?")
            .setCancelable(false)
            .setPositiveButton("Ausloggen") { dialog, id ->
                performLogout()
            }
            .setNegativeButton("Stornieren") { dialog, id ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Ausloggen")
        alert.show()
        val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton?.isAllCaps = false

        val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
        negativeButton?.isAllCaps = false
    }

    private fun performLogout() {
        lifecycleScope.launch(Dispatchers.Main) {
            val prefs: SharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit = prefs.edit()
            myEdit.putBoolean("isUserLoggedIn", false)
            myEdit.apply()
            val intent = Intent(this@LevelsAdminActivity, StudentOrAdminActivity::class.java)
            startActivity(intent)
            finish()
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
                    levelsAdapter.changeData(viewModel.levels.value)
                    Handler().postDelayed({
                        levelsRecycler.scrollToPosition(levelsAdapter.itemCount - 1)
                    }, 500)   }}
                else {
                Toast.makeText(this@LevelsAdminActivity, "Bitte geben Sie einen Levelnamen ein", Toast.LENGTH_SHORT).show()
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

    private fun showLoading(message: String?) {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(message)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    private fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}