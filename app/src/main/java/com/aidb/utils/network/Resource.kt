package com.aidb.utils.network

import java.io.Serializable

data class Resource<out T>(
    val apiConstant: String?,
    val status: Status,
    val data: T?,
    var message: String?,
    val resourceId: Int?
) : Serializable {

    companion object {
        fun <T> success(apiConstant: String, data: T?): Resource<T> {
            return Resource(apiConstant, Status.SUCCESS, data, null, -1)
        }

        fun <T> error(apiConstant: String, data: T?, msg: String): Resource<T> {
            return Resource(apiConstant, Status.ERROR, data, msg, -1)
        }

        fun <T> requiredResource(apiConstant: String, resourceId: Int): Resource<T> {
            return Resource(apiConstant, Status.RESOURCE, null, null, resourceId)
        }

        fun <T> loading(apiConstant: String, data: T?): Resource<T> {
            return Resource(apiConstant, Status.LOADING, data, null, -1)
        }
    }
}