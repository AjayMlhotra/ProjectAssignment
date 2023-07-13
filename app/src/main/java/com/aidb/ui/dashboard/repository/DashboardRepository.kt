package com.aidb.ui.dashboard.repository

import com.aidb.api.ApiService
import com.aidb.ui.dashboard.model.EmailArticleResponse
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(val apiService: ApiService) {
    suspend fun getEmailArticles(): Response<EmailArticleResponse> = apiService.getEmailArticles()
}