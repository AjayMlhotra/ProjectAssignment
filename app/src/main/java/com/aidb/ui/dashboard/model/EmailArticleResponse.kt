package com.aidb.ui.dashboard.model

import com.aidb.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class EmailArticleResponse(
    @SerializedName("results")
    val results: List<Result?>?,
    @SerializedName("status")
    val status: String?
) : BaseResponse() {
    data class Result(
        @SerializedName("media")
        val media: List<Media?>?,
        @SerializedName("title")
        val title: String?,
    ) {
        data class Media(
            @SerializedName("media-metadata")
            val mediaMetadata: List<MediaMetadata?>?,
        ) {
            data class MediaMetadata(
                @SerializedName("url")
                val url: String?,
            )
        }
    }
}