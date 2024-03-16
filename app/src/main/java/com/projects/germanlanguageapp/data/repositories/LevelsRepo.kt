package com.projects.germanlanguageapp.data.repositories

import com.projects.germanlanguageapp.data.dataSources.remote.apis.WebServices
import javax.inject.Inject

class LevelsRepo @Inject constructor(private val webServices: WebServices) {
    suspend fun getLevels() = webServices.getLevels()
}