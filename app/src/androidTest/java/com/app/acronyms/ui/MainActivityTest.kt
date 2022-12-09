package com.app.acronyms.ui

import android.support.test.espresso.IdlingResource
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.app.acronyms.AndroidXIdlingResource
import com.app.acronyms.MockServerDispatcher
import com.app.acronyms.R
import com.app.acronyms.di.AppModule
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var okHttp: OkHttpClient

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        mockWebServer.start(8080)
        val idlingResource: IdlingResource = OkHttp3IdlingResource.create("OkHttp", okHttp)
        IdlingRegistry.getInstance().register(AndroidXIdlingResource.asAndroidX(idlingResource))

    }

    @Test
    fun verify_first_launch_shows_correct_message_on_UI() {
        onView(withId(R.id.messageView)).check(matches(withText(appContext.getString(R.string.starting_message))))
    }

    @Test
    fun verify_all_views() {
        onView(withId(R.id.messageView)).check(matches(withText(appContext.getString(R.string.starting_message))))
        onView(withId(R.id.txtInput)).check(matches(withHint(appContext.getString(R.string.enter_acronym))))
        onView(withId(R.id.button)).check(matches(withText(appContext.getString(R.string.clear))))
    }

    @Test
    fun verify_recycler_view_is_displayed_when_acronym_is_searched() {
        onView(withId(R.id.txtInput)).perform(clearText(), typeText("FBI"))
        Thread.sleep(5000)
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


}