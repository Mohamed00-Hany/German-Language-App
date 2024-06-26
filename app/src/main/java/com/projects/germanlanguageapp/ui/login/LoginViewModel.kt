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
    var email = MutableStateFlow<String?>(null)
    val password = MutableStateFlow<String?>(null)
    val emailError = MutableStateFlow<String?>(null)
    val passwordError = MutableStateFlow<String?>(null)
    val loginState = MutableStateFlow<GlobalState?>(null)

    fun login() {
        if (!validForm())
        {
            return
        }

        loginState.value = GlobalState.Loading("Wird geladen...")
        auth.signInWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        loginState.value = GlobalState.Success("Erfolgreiche Registrierung")
                    }
                } else {
                    GlobalState.Loading(null)
                    loginState.value = GlobalState.Failed("Fehler")
                }
            }
    }

    private fun validForm(): Boolean {
        var isValid = true

        if (email.value.isNullOrBlank()) {
            isValid = false
            emailError.value="Bitte geben Sie ihre E-Mail-Adresse ein"
        } else if (email.value?.isMatch() != true) {
            isValid = false
            emailError.value="Die E-Mail-Adresse ist falsch formatiert"
        } else {
            emailError.value=null
        }

        if (password.value.isNullOrBlank()) {
            isValid = false
            passwordError.value="Bitte Passwort eingeben"
        } else if ((password.value?.length ?: 0) < 6) {
            isValid = false
            passwordError.value="Das Passwort sollte mindestens 6 Zeichen lang sein"
        } else {
            passwordError.value=null
        }

        return isValid
    }

}