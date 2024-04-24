package com.projects.germanlanguageapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.nl.translate.Translator
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivitySplashBinding
import com.projects.germanlanguageapp.domain.useCases.GetModelData
import com.projects.germanlanguageapp.ui.levels.LevelsActivity
import com.projects.germanlanguageapp.ui.studentoradmin.StudentOrAdminActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    @Inject
    lateinit var getModelData: GetModelData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startStudentAdminActivity()
        },3000)
        lifecycleScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
        }) {
            getModelData.invoke(byteArrayOf())
        }
    }

    private fun startStudentAdminActivity()
    {
        val prefs = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false)

        val intent:Intent = if(!isUserLoggedIn) {
            Intent(this, StudentOrAdminActivity::class.java)
        } else {
            if (auth.currentUser!!.email == "admin@education.o6u") {
                Intent(this, StudentOrAdminActivity::class.java)
            } else {
                Intent(this, LevelsActivity::class.java)
            }
        }

        startActivity(intent)
        finish()
    }

}