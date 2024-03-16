package com.projects.germanlanguageapp.ui.loginorregister

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginOrRegisterViewModel : ViewModel() {

    var userAction: MutableStateFlow<UserAction?> = MutableStateFlow(null)

    fun openLoginActivity()
    {
        userAction.value=UserAction.LOGIN
    }
    fun openRegisterActivity()
    {
        userAction.value=UserAction.REGISTER
    }
}