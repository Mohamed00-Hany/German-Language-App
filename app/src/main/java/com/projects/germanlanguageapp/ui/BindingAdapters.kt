package com.projects.germanlanguageapp.ui

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("error")
fun showError(textInputLayout: TextInputLayout,errorMessage:String?)
{
    textInputLayout.error=errorMessage
}