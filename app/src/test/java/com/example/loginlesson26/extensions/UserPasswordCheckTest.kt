package com.example.loginlesson26.extensions

import org.junit.Test
import org.junit.Assert.*

class UserPasswordCheckTest {

    @Test
    fun check_password_to_short() {
        val testPassword = "1234"
        assertTrue(testPassword.isPasswordValid() == PasswordStatus.TOO_SHORT)
    }

    @Test
    fun check_password_has_digits() {
        val testPassword = "abcdef"
        assertTrue(testPassword.isPasswordValid() == PasswordStatus.NO_DIGITS)
    }

    @Test
    fun check_password_has_uppercase() {
        val testPassword = "abcdef1"
        assertTrue(testPassword.isPasswordValid() == PasswordStatus.NO_UPPERCASE_LETTERS)
    }

    @Test
    fun check_password_has_lowercase() {
        val testPassword = "ABCDEF1"
        assertTrue(testPassword.isPasswordValid() == PasswordStatus.NO_LOWERCASE_LETTERS)
    }

    @Test
    fun check_password_valid_password() {
        val testPassword = "abcABCDEF1"
        assertTrue(testPassword.isPasswordValid() == PasswordStatus.OK)
    }
}