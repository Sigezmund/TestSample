package com.example.loginlesson26.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginlesson26.data.LoginRepository
import com.example.loginlesson26.extensions.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.AdditionalMatchers.not

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    //Позволяет запускать весь код в архитектурных компонентах синхронно
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_right_login_password() {
        val mock = mockk<LoginRepository>()
//        для каждого вызов getAuthorization возвращаем true
        coEvery { mock.getAuthorization("", "") } returns true

        val viewModel = LoginViewModel(
            mock,
            Dispatchers.Unconfined,
            Dispatchers.Unconfined
        )
        viewModel.onSignInClick("", "")
        assertEquals(viewModel.authIsSuccessful.getOrAwaitValue(), true)
    }

    @Test
    fun test_wrong_login_password() {
        val mock = mockk<LoginRepository>()
        coEvery { mock.getAuthorization("", "") } returns false

        val viewModel = LoginViewModel(
            mock,
            Dispatchers.Unconfined,
            Dispatchers.Unconfined
        )
        viewModel.onSignInClick("", "")
        assertEquals(viewModel.authIsSuccessful.getOrAwaitValue(), false)
    }
}