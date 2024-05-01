package com.projects.germanlanguageapp.ui.recording

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.projects.germanlanguageapp.R
import com.projects.germanlanguageapp.databinding.ActivityRecordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class RecordingActivity : AppCompatActivity() {
    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording: Boolean = false
    private lateinit var binding: ActivityRecordBinding
    private lateinit var targetWord: String
    private lateinit var outputFile:String
    val viewModel: RecordingViewModel by viewModels()

    private val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val requestCode = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_record)
        targetWord = intent.getStringExtra("TARGET_WORD") ?: ""
        binding.targetWord.text = targetWord
        binding.buttonStartRecording.setOnClickListener {
            if (isRecording) {
                stopRecording()
            } else {
                if (checkPermissions()) {
                    startRecording()
                } else {
                    requestPermissions()
                }
            }
        }
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun startRecording() {
        outputFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            externalMediaDirs[0]?.absolutePath + "/recording.wav"
        } else {
            externalCacheDir?.absolutePath + "/recording.wav"
        }

        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setOutputFile(outputFile)

        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
            isRecording = true
            binding.resultImage.visibility = View.GONE
            binding.textviewSoundRecorderHeading.text = "..."
            binding.buttonStartRecording.setImageDrawable(getDrawable(R.drawable.icon_stop))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.release()
        isRecording = false
        binding.textviewSoundRecorderHeading.text = ""
        binding.buttonStartRecording.setImageDrawable(getDrawable(R.drawable.icon_mic))
        getResponseThenShowResult()
    }

    private fun getResponseThenShowResult() {
        val data: ByteArray = convertFileToByteArray(outputFile)
        lifecycleScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
            lifecycleScope.launch(Dispatchers.Main) {
                binding.resultImage.setImageResource(R.drawable.loading_image)
                binding.resultImage.visibility = View.VISIBLE
            }
        }) {
            val result = viewModel.getModelAiData(data)
                .replace("\\s".toRegex(), "")
                .replace(".".toRegex(), "")
                .lowercase(Locale.ROOT)
            withContext(Dispatchers.Main) {
                if (result == targetWord.replace("\\s".toRegex(), "")
                        .replace(".".toRegex(), "").lowercase(Locale.ROOT)) {
                    binding.resultImage.setImageResource(R.drawable.result_image)
                    binding.resultImage.visibility = View.VISIBLE
                    binding.buttonStartRecording.visibility = View.GONE
                    binding.buttonBack.visibility = View.VISIBLE
                }
                else {
                    binding.resultImage.setImageResource(R.drawable.fault_image)
                    binding.resultImage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun convertFileToByteArray(filename: String): ByteArray {
        val file = File(filename)
        val inputStream = FileInputStream(file)
        val length = file.length().toInt()
        val byteArray = ByteArray(length)

        inputStream.read(byteArray, 0, length)
        inputStream.close()

        return byteArray
    }

    private fun checkPermissions(): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
            return true
        }
        return true
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == this.requestCode) {
            var allGranted = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false
                    break
                }
            }
            if (allGranted) {
                startRecording()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}