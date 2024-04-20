package com.projects.germanlanguageapp.ui.levels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import com.projects.germanlanguageapp.domain.models.LevelsResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelsViewModel @Inject constructor(val webServices: WebServices) : ViewModel() {
    val levels=MutableStateFlow<List<LevelsResponseItem?>?>(null)
    fun getLevels() {
        viewModelScope.launch {
            try {
                levels.value = webServices.getLevels().levelsResponse
            } catch (e: Exception) {
                Log.e("getLevelsError", "Error fetching levels: ${e.message}")
            }
        }
    }

    suspend fun postLevels(levelName: String) {
        try {
            webServices.postLevels(levelName)
        } catch (e: Exception) {
            Log.e("postLevelsError", "Error posting levels: ${e.message}")
        }
    }
}