package com.projects.germanlanguageapp.ui.studentoradmin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityStudentAdminBinding
import com.projects.germanlanguageapp.ui.login.LoginActivity
import com.projects.germanlanguageapp.ui.loginorregister.LoginOrRegisterActivity
import kotlinx.coroutines.launch

class StudentOrAdminActivity:AppCompatActivity() {
    private val viewModel:StudentOrAdminViewModel by viewModels()
    private lateinit var binding:ActivityStudentAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_student_admin)
        binding.viewModel=viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                navigateToNextScreen()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.user.value = null
    }

    private suspend fun navigateToNextScreen()
    {
        viewModel.user.collect{user->
            user?.let {
                when(it)
                {
                    User.STUDENT -> {
                        navigateToLoginOrRegisterScreen()
                    }
                    User.ADMIN -> {
                        navigateToLoginScreen()
                    }
                }
            }
        }
    }

    private fun navigateToLoginOrRegisterScreen()
    {
        startActivity(Intent(this, LoginOrRegisterActivity::class.java))
    }

    private fun navigateToLoginScreen()
    {
        startActivity(Intent(this,LoginActivity::class.java))
    }

}