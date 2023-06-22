package com.example.storyapp.UI.Login

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.storyapp.R
import com.example.storyapp.UI.Register.RegisterActivity
import org.junit.After

@RunWith(AndroidJUnit4::class)
class LoginActivityTest{
    @Before
    fun setup() {
        Intents.init()
        ActivityScenario.launch(LoginActivity::class.java)
    }

    @After
    fun cleanup() {
        Intents.release()
    }

    @Test
    fun testRegisterButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnDaftar))
            .perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(RegisterActivity::class.java.name))
    }

    @Test
    fun testLoginSuccess() {
        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("example@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("password"))
        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.progressBar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun testInvalidPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("invalid@example.com"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("123456"))

        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))
    }

    @Test
    fun testInvalidEmail() {
        // Masukkan email dan password yang salah
        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("invalid@example"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("12345678"))

        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))
    }
}