package com.example.loginlesson26.extensions

import android.util.Patterns


fun String.isEmailValid(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPasswordValid(): PasswordStatus {
    if (this.length < 5) {
        return PasswordStatus.TOO_SHORT
    }
    if (!this.any { it.isDigit() }) {
        return PasswordStatus.NO_DIGITS
    }

    if (!this.any { it.isUpperCase() }) {
        return PasswordStatus.NO_UPPERCASE_LETTERS
    }

    if (!this.any { it.isLowerCase() }) {
        return PasswordStatus.NO_LOWERCASE_LETTERS
    }

    return PasswordStatus.OK
}

enum class PasswordStatus {
    OK,
    TOO_SHORT,
    NO_DIGITS,
    NO_UPPERCASE_LETTERS,
    NO_LOWERCASE_LETTERS
}