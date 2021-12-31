package com.cornucopia.kotlin.test.ml

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.cornucopia.kotlin.ml.LeakActivity
import com.cornucopia.kotlin.test.R
import org.junit.Rule
import org.junit.Test

class LeakActivityTest {

    @get:Rule
    var mainActivityTestRule = ActivityTestRule(LeakActivity::class.java)


    @Test
    fun testIgnoreLeaks() {
        onView(withId(R.id.btn_leak)).perform(click())
    }

    @Test
    @LeakTest
    fun testLeaks() {
        onView(withId(R.id.btn_leak)).perform(click())
    }
}