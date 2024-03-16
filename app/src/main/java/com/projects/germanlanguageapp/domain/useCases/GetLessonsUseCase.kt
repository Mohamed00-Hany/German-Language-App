package com.projects.germanlanguageapp.domain.useCases

import com.projects.germanlanguageapp.data.repositories.LessonsRepo
import javax.inject.Inject

class GetLessonsUseCase @Inject constructor(private val lessonsRepo: LessonsRepo) {
    operator fun invoke()
    {

    }
}