package com.projects.germanlanguageapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivitySplashBinding
import com.projects.germanlanguageapp.ui.levels.LevelsActivity
import com.projects.germanlanguageapp.ui.login.LoginActivity
import com.projects.germanlanguageapp.ui.studentoradmin.StudentOrAdminActivity


class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startStudentAdminActivity()
        },3000)
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