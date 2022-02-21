package com.example.loginlesson26.presentation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginlesson26.R
import io.mockk.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @Test
    fun test_login_click() {
        // Создаем фейковую LoginViewModel
        //relaxed = true означает, что она по умолчанию проинициализирует все
        // функции и методы простыми значениями
        val viewModel = mockk<LoginViewModel>(relaxed = true)

        every { viewModel.authIsSuccessful } returns MutableLiveData()
        every { viewModel.userLiveData } returns MutableLiveData()
        every { viewModel.onSignInClick("", "") } just Runs

        //создаем фрагмент в состоянии CREATED
        val scenario = launchFragmentInContainer<LoginFragment>(

            initialState = Lifecycle.State.CREATED,
            themeResId = R.style.Theme_LoginLesson26
        )
        // инжектим ViewModelFactory
        scenario.onFragment {
            it.viewModelFactory = ViewModelFactory {
                viewModel
            }
        }
//        переводим фрагмент в сотояние RESUMED
        scenario.moveToState(Lifecycle.State.RESUMED)

//        нажимаем на кнопку
        onView(withId(R.id.buttonSignIn)).perform(click())

//        Проверям вызвался ли у ViewModel метод onSignInClick ровно один раз
        verify(exactly = 1) { viewModel.onSignInClick("", "") }

    }
}