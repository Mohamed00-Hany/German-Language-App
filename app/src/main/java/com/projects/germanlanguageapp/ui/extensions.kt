package com.projects.germanlanguageapp.ui

fun String.isMatch():Boolean
{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}