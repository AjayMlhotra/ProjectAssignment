package com.aidb.ui.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aidb.api.GET_EMAIL_ARTICLE
import com.aidb.ui.dashboard.model.EmailArticleResponse
import com.aidb.ui.dashboard.repository.DashboardRepository
import com.aidb.utils.livedata.SingleLiveEvent
import com.aidb.utils.network.Resource
import com.aidb.utils.network.networkCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {
    private val _emailArticleResponse = SingleLiveEvent<Resource<EmailArticleResponse>>()
    val emailArticleResponse: LiveData<Resource<EmailArticleResponse>> = _emailArticleResponse

    fun getEmailArticles() {
        viewModelScope.launch {
            networkCall(GET_EMAIL_ARTICLE) {
                dashboardRepository.getEmailArticles()
            }.collect {
                _emailArticleResponse.value = it
            }
        }
    }
}