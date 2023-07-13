package com.aidb.api

import com.aidb.ui.dashboard.model.EmailArticleResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(GET_EMAIL_ARTICLE)
    suspend fun getEmailArticles(): Response<EmailArticleResponse>
}