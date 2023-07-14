package com.aidb.ui.signup.model

import com.google.gson.annotations.SerializedName

class SignUpRequest {
    @SerializedName("email")
    var email: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("password")
    var password: String = ""

}