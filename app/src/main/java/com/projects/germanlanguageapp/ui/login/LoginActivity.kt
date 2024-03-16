package com.projects.germanlanguageapp.ui.login

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityLoginBinding
import com.projects.germanlanguageapp.ui.OnDialogClickListener
import com.projects.germanlanguageapp.ui.levels.LevelsActivity
import com.projects.germanlanguageapp.ui.GlobalState
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    var progressDialog: ProgressDialog? = null
    var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner=this
        binding.vm=viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                observeLoginState()
            }
        }
    }

    private suspend fun observeLoginState() {
        viewModel.loginState.collect { loginState ->
            loginState?.let {
                when (it) {
                    is GlobalState.Loading -> {
                        showLoading(it.message)
                    }
                    is GlobalState.Success -> {
                        hideLoading()
                        navigateToLevelsScreen()
                    }
                    is GlobalState.Failed -> {
                        hideLoading()
                        showMessage(it.message, negActionTitle = "Ok")
                    }
                }
            }
        }
    }

    private fun navigateToLevelsScreen() {
        startActivity(Intent(this, LevelsActivity::class.java))
        finishAffinity()
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

    fun showMessage(
        message: String?, posActionTitle: String? = null, posAction: OnDialogClickListener? = null,
        negActionTitle: String? = null, negAction: OnDialogClickListener? = null
    ) {
        val builder = AlertDialog.Builder(this).setMessage(message).setCancelable(false)
        if (posActionTitle != null) {
            builder.setPositiveButton(posActionTitle) { dialog, which ->
                dialog.dismiss()
                posAction?.onClick()
            }
        }
        if (negActionTitle != null) {
            builder.setNegativeButton(negActionTitle) { dialog, which ->
                dialog.dismiss()
                negAction?.onClick()
            }
        }
        alertDialog = builder.show()
    }

}