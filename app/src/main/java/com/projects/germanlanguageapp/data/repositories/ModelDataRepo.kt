package com.projects.germanlanguageapp.data.repositories

import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import com.projects.germanlanguageapp.data.dataSources.remote.apis.query
import com.projects.germanlanguageapp.domain.models.ModelAiResponse
import javax.inject.Inject

class ModelDataRepo @Inject constructor()  {
    suspend fun getModelData(data: ByteArray):String {
        return query(data).getString("text")
    }
}