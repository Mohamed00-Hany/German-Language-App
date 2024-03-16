package com.projects.germanlanguageapp.domain.useCases

import com.projects.germanlanguageapp.data.repositories.LevelsRepo
import javax.inject.Inject

class GetLevelsUseCase @Inject constructor(private val levelsRepo: LevelsRepo) {
    suspend operator fun invoke() = levelsRepo.getLevels().levelsResponse
}