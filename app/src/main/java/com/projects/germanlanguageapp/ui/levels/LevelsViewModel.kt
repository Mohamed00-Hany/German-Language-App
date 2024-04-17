package com.projects.germanlanguageapp.ui.levels

import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.domain.models.LevelsResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LevelsViewModel @Inject constructor() : ViewModel() {
    val levels=MutableStateFlow<List<LevelsResponseItem?>?>(listOf(LevelsResponseItem(0,"Ebene 1"),
        LevelsResponseItem(2,"Ebene 2")))
}