package com.projects.germanlanguageapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivitySplashBinding
import com.projects.germanlanguageapp.domain.useCases.GetModelData
import com.projects.germanlanguageapp.ui.levels.LevelsActivity
import com.projects.germanlanguageapp.ui.studentoradmin.StudentOrAdminActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    @Inject
    lateinit var getModelData: GetModelData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash)
        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)
            startStudentAdminActivity()
        }
        lifecycleScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
        }) {
            getModelData.invoke(byteArrayOf())
        }
        lateinit var translator: Translator
        lateinit var translator1: Translator
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ARABIC)
            .setTargetLanguage(TranslateLanguage.GERMAN)
            .build()
        val options1 = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.GERMAN)
            .setTargetLanguage(TranslateLanguage.ARABIC)
            .build()
        translator1 = Translation.getClient(options)
        translator = Translation.getClient(options1)
        translator.downloadModelIfNeeded()
        translator1.downloadModelIfNeeded()
    }

    private fun startStudentAdminActivity()
    {
        val prefs = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false)

        val intent:Intent = if(!isUserLoggedIn) {
            Intent(this, StudentOrAdminActivity::class.java)
        } else {
            if (auth.currentUser!!.email == "admin@education.o6u") {
                Intent(this, StudentOrAdminActivity::class.java)
            } else {
                Intent(this, LevelsActivity::class.java)
            }
        }

        startActivity(intent)
        finish()
    }

}