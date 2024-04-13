package com.projects.germanlanguageapp.domain.useCases

import com.projects.germanlanguageapp.data.repositories.ModelDataRepo
import javax.inject.Inject

class GetModelData @Inject constructor(private val modelDataRepo: ModelDataRepo) {
    suspend operator fun invoke(data: ByteArray):String
    {
        return modelDataRepo.getModelData(data)
    }
}