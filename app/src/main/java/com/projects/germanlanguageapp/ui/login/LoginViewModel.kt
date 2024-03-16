package com.projects.germanlanguageapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.projects.germanlanguageapp.ui.isMatch
import com.projects.germanlanguageapp.ui.GlobalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val email = MutableStateFlow<String?>(null)
    val password = MutableStateFlow<String?>(null)
    val emailError = MutableStateFlow<String?>(null)
    val passwordError = MutableStateFlow<String?>(null)
    val loginState = MutableStateFlow<GlobalState?>(null)

    fun login() {
        if (!validForm())
        {
            Log.e("hany",emailError.value?:"")
            return
        }

        loginState.value = GlobalState.Loading("Loading...")
        auth.signInWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        loginState.value = GlobalState.Success("Successful Login")
                    }
                } else {
                    GlobalState.Loading(null)
                    loginState.value = GlobalState.Failed(task.exception?.localizedMessage ?: "")
                }
            }
    }

    private fun validForm(): Boolean {
        var isValid = true

        if (email.value.isNullOrBlank()) {
            isValid = false
            emailError.value="Please enter your email"
        } else if (email.value?.isMatch() != true) {
            isValid = false
            emailError.value="The email address is badly formatted"
        } else {
            emailError.value=null
        }

        if (password.value.isNullOrBlank()) {
            isValid = false
            passwordError.value="Please enter password"
        } else if ((password.value?.length ?: 0) < 6) {
            isValid = false
            passwordError.value="The password should be at least 6 characters"
        } else {
            passwordError.value=null
        }

        return isValid
    }

}