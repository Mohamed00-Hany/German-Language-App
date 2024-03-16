package com.projects.germanlanguageapp.ui.loginorregister

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityLoginOrRegisterBinding
import com.projects.germanlanguageapp.ui.login.LoginActivity
import com.projects.germanlanguageapp.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginOrRegisterActivity:AppCompatActivity() {
    val viewModel: LoginOrRegisterViewModel by viewModels()
    private lateinit var binding: ActivityLoginOrRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login_or_register)
        binding.viewModel=viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                navigateToNextScreen()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.userAction.value = null
    }

    private suspend fun navigateToNextScreen()
    {
        viewModel.userAction.collect{userAction->
            userAction?.let {
                when(it)
                {
                    UserAction.LOGIN -> {
                        openLoginScreen()
                    }
                    UserAction.REGISTER -> {
                        navigateRegisterScreen()
                    }
                }
            }
        }
    }

    private fun openLoginScreen()
    {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun navigateRegisterScreen()
    {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

}