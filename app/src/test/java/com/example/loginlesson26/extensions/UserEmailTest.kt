package com.example.loginlesson26.extensions

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class UserEmailTest {

    @Test
    fun test_wrong_email_address() {
        val email = "asdasds"
        assertFalse(email.isEmailValid())
    }

    @Test
    fun test_right_email_address() {
        val email = "asdasds@sss.com"
        assertTrue(email.isEmailValid())
    }
}