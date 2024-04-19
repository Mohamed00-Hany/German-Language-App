package com.projects.germanlanguageapp.domain.models


data class WordsResponse(
    var wordId: Int? = null,
    var wordInGerman: String? = null,
    var wordTranslationInArabic: String? = null
)