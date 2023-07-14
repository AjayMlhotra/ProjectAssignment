package com.aidb.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AppUtilsKtTest{

    @Test
    fun isEmailValid(){
        val result = isValidEmail("test@gmail.com")
        assertThat(result).isEqualTo(true)
    }
}