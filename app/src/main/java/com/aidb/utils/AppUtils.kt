package com.aidb.utils

import android.util.Patterns
import java.util.regex.Pattern

private const val PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Z0-9a-z!@$%*#?&_^+.=-]{8,35}$"

private val PASSWORD_PATTERN: Pattern = Pattern.compile(PASSWORD)

fun String.validUserNameLength(): Boolean {
    return length in 3..35
}

fun isValidEmail(target: CharSequence): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(target).matches()
}
fun isValidPassword(target: CharSequence): Boolean {
    return PASSWORD_PATTERN.matcher(target).matches()
}
