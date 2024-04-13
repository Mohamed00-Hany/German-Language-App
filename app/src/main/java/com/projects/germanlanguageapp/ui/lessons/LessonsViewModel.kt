package com.projects.germanlanguageapp.ui.lessons

import androidx.lifecycle.ViewModel
import com.projects.germanlanguageapp.domain.useCases.GetModelData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(private val getModelData: GetModelData) : ViewModel() {
    val lessons=MutableStateFlow<List<String>?>(listOf("Lektion 1","Lektion 2","Lektion 3","Lektion 4","Lektion 5","Lektion 6",
        "Lektion 7","Lektion 8","Lektion 9","Lektion 10","Lektion 11","Lektion 12","Lektion 13","Lektion 14","Lektion 15",
        "Lektion 16","Lektion 17","Lektion 18","Lektion 19","Lektion 20","Lektion 21","Lektion 22","Lektion 23","Lektion 24"))

}