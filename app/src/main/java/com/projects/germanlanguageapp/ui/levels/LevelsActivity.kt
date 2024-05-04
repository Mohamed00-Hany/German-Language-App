package com.projects.germanlanguageapp.ui.levels

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityLevelsBinding
import com.projects.germanlanguageapp.ui.lessons.LessonsActivity
import com.projects.germanlanguageapp.ui.studentoradmin.StudentOrAdminActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LevelsActivity: AppCompatActivity() {
    private val viewModel:LevelsViewModel by viewModels()
    private lateinit var binding: ActivityLevelsBinding
    private lateinit var levelsAdapter:LevelsAdapter
    private lateinit var levelsRecycler:RecyclerView
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_levels)
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
        levelsRecycler=binding.levelsRecycler
        levelsRecycler.adapter=levelsAdapter
        levelsAdapter.onLevelClickListener=object : LevelsAdapter.OnLevelClick {
            override fun onClick(position: Int, levelId: Int?) {
                val intent=Intent(this@LevelsActivity,LessonsActivity::class.java)
                intent.putExtra("levelId",levelId)
                startActivity(intent)
            }
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
                logout()
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

    private fun logout() {
        val prefs: SharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = prefs.edit()
        myEdit.putBoolean("isUserLoggedIn", false)
        myEdit.apply()
        val intent = Intent(this@LevelsActivity, StudentOrAdminActivity::class.java)
        startActivity(intent)
        finish()
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