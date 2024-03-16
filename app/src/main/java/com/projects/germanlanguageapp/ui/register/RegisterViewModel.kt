package com.projects.germanlanguageapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.projects.germanlanguageapp.ui.GlobalState
import com.projects.germanlanguageapp.ui.isMatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth=FirebaseAuth.getInstance()
    val userName = MutableStateFlow<String?>(null)
    val email = MutableStateFlow<String?>(null)
    val password = MutableStateFlow<String?>(null)
    val passwordConfirmation = MutableStateFlow<String?>(null)
    val userNameError = MutableStateFlow<String?>(null)
    val emailError = MutableStateFlow<String?>(null)
    val passwordError = MutableStateFlow<String?>(null)
    val passwordConfirmationError = MutableStateFlow<String?>(null)
    val globalStateState = MutableStateFlow<GlobalState?>(null)

    fun register() {
        if (!validForm())
            return
        globalStateState.value = GlobalState.Loading("Loading...")
        auth.createUserWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        globalStateState.value = GlobalState.Success("Successful Register")
                    }
                } else {
                    GlobalState.Loading(null)
                    globalStateState.value = GlobalState.Failed(task.exception?.localizedMessage ?: "")
                }
            }
    }

    private fun validForm(): Boolean {
        var isValid = true

        if (userName.value.isNullOrBlank()) {
            isValid = false
            userNameError.value="Please enter your name"
        } else {
            userNameError.value=null
        }

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

        if (passwordConfirmation.value.isNullOrBlank()) {
            isValid = false
            passwordConfirmationError.value="Please re-enter password"
        } else if (!passwordConfirmation.value.equals(password.value)) {
            isValid = false
            passwordConfirmationError.value="Doesn't match"
        } else {
            passwordConfirmationError.value=null
        }

        return isValid
    }
}