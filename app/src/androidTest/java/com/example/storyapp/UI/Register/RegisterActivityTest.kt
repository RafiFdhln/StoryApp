package com.example.storyapp.UI.Register

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.storyapp.R
import com.example.storyapp.UI.Login.LoginActivity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest{
    @Before
    fun setup() {
        Intents.init()
        ActivityScenario.launch(RegisterActivity::class.java)
    }

    @After
    fun cleanup() {
        Intents.release()
    }

    @Test
    fun testLoginButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(LoginActivity::class.java.name))
    }

    @Test
    fun testRegisterSuccess() {
        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("example@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("12345678"))
        Espresso.onView(ViewMatchers.withId(R.id.etNama))
            .perform(ViewActions.typeText("name"))
        Espresso.onView(ViewMatchers.withId(R.id.btnDaftar))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.progressBar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun testInvalidPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("example@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("123456"))
        Espresso.onView(ViewMatchers.withId(R.id.etNama))
            .perform(ViewActions.typeText("name"))
        Espresso.onView(ViewMatchers.withId(R.id.btnDaftar))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnDaftar))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))
    }

    @Test
    fun testInvalidEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("example@gmail"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("12345678"))
        Espresso.onView(ViewMatchers.withId(R.id.etNama))
            .perform(ViewActions.typeText("name"))
        Espresso.onView(ViewMatchers.withId(R.id.btnDaftar))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnDaftar))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))
    }

    fun testInvalidName() {
        Espresso.onView(ViewMatchers.withId(R.id.etEmail))
            .perform(ViewActions.typeText("example@gmail"))
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("12345678"))
        Espresso.onView(ViewMatchers.withId(R.id.etNama))
            .perform(ViewActions.typeText(""))
        Espresso.onView(ViewMatchers.withId(R.id.btnDaftar))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnDaftar))
            .check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))
    }
}