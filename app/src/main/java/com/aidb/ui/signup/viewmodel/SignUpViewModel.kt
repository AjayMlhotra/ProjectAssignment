package com.aidb.ui.signup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aidb.AppAdib
import com.aidb.R
import com.aidb.ui.signup.model.SignUpRequest
import com.aidb.utils.isValidEmail
import com.aidb.utils.isValidPassword
import com.aidb.utils.livedata.SingleLiveEvent
import com.aidb.utils.network.Resource
import com.aidb.utils.validUserNameLength
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    private val _signUpRequestModel = SignUpRequest()
    val signUpRequest: SignUpRequest = _signUpRequestModel

    private val _isValidated = SingleLiveEvent<Boolean>()
    val isValidated: LiveData<Boolean> = _isValidated

    private val _validationError = SingleLiveEvent<Resource<String>>()
    val validationError: LiveData<Resource<String>> = _validationError

    fun validateSignUpFields() {
        val resources = getApplication<AppAdib>().resources
        when {
            _signUpRequestModel.name.isEmpty() -> {
                showValidationMessage(resources.getString(R.string.err_enter_user_name))
                return
            }

            !_signUpRequestModel.name.validUserNameLength() -> {
                showValidationMessage(resources.getString(R.string.err_enter_valid_name))
                return
            }

            _signUpRequestModel.email.isEmpty() -> {
                showValidationMessage(resources.getString(R.string.err_enter_email_address))
                return
            }

            !isValidEmail(_signUpRequestModel.email) -> {
                showValidationMessage(resources.getString(R.string.err_valid_email))
                return
            }

            _signUpRequestModel.password.isEmpty() -> {
                showValidationMessage(resources.getString(R.string.err_enter_password))
                return
            }

            !isValidPassword(_signUpRequestModel.password) -> {
                showValidationMessage(resources.getString(R.string.err_valid_password))
                return
            }
            else -> _isValidated.value = true
        }
    }

    private fun showValidationMessage(message: String) {
        _validationError.value = Resource.error("", null, message)
    }
}