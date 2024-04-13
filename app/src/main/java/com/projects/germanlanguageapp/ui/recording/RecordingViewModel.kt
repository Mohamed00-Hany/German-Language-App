package com.projects.germanlanguageapp.ui.recording

import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.domain.useCases.GetModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor(private val getModelData: GetModelData) : ViewModel() {
    suspend fun getModelAiData(data: ByteArray):String {
        return getModelData.invoke(data)
    }
}