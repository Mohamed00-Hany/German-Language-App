package com.projects.germanlanguageapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivitySplashBinding
import com.projects.germanlanguageapp.ui.studentoradmin.StudentOrAdminActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startStudentAdminActivity()
        },3000)
    }

    private fun startStudentAdminActivity()
    {
        val intent=Intent(this, StudentOrAdminActivity::class.java)
        startActivity(intent)
        finish()
    }

}