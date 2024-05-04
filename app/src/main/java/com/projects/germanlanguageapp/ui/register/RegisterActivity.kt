package com.projects.germanlanguageapp.ui.register

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
import com.projects.germanlanguageapp.databinding.ActivityRegisterBinding
import com.projects.germanlanguageapp.ui.GlobalState
import com.projects.germanlanguageapp.ui.OnDialogClickListener
import com.projects.germanlanguageapp.ui.login.LoginActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding
    var progressDialog: ProgressDialog? = null
    var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                observeRegisterState()
            }
        }
    }

    private suspend fun observeRegisterState() {
        viewModel.globalStateState.collect { registerState ->
            registerState?.let {
                when (it) {
                    is GlobalState.Loading -> {
                        showLoading(it.message)
                    }
                    is GlobalState.Success -> {
                        hideLoading()
                        showMessage(
                            it.message,
                            posActionTitle = "Anmeld",
                            posAction = { navigateToLoginScreen() },
                            negActionTitle = "Ja"
                        )
                    }
                    is GlobalState.Failed -> {
                        hideLoading()
                        showMessage(it.message, negActionTitle = "Ja")
                    }
                }
            }
        }
    }

    private fun navigateToLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
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