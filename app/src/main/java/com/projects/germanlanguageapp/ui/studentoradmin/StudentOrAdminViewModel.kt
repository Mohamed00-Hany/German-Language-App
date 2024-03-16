package com.projects.germanlanguageapp.ui.studentoradmin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class StudentOrAdminViewModel : ViewModel() {

    var user:MutableStateFlow<User?> = MutableStateFlow(null)

    fun openRegisterLoginScreen()
    {
        user.value = User.STUDENT
    }

    fun openLoginScreen()
    {
        user.value = User.ADMIN
    }

}