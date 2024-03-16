package com.projects.germanlanguageapp.ui


sealed class GlobalState() {
    class Loading(val message: String?) : GlobalState()
    class Success(val message: String?) : GlobalState()
    class Failed(val message: String?) : GlobalState()
}