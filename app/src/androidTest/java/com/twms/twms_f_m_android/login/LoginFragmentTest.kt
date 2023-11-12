package com.twms.twms_f_m_android.login

import android.os.Bundle
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.twms.twms_f_m_android.R
import com.twms.twms_f_m_android.launchFragmentInHiltContainer
import com.twms.twms_f_m_android.ui.login.LoginFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        // Populate @Inject fields in test class
        hiltRule.inject()
    }

    @Test
    fun loginFragment_Displayed() {

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInHiltContainer<LoginFragment>(Bundle(), R.style.Base_Theme_Twms_f_m_android, navController)

        Espresso.onView(withId(R.id.txtUserId))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(withId(R.id.txtPassword))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}