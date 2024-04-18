package com.projects.germanlanguageapp.ui.levels

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
            levels.value = webServices.getLevels().levelsResponse
        }
    }
}