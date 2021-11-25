package com.cornucopia.kotlin.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cornucopia.kotlin.recy.RecyMainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by thom on 6/2/2018.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class RecyMainSampleTest {

    val ITEM_BELOW_THE_FOLD = 40;

    @get:Rule  // @Rule must be public
    public val mActivityRule: ActivityTestRule<RecyMainActivity>
            = ActivityTestRule<RecyMainActivity>(RecyMainActivity::class.java);

    @Test
    fun scrollToItemBelowFold_checkItsText() {
        onView(ViewMatchers.withId(R.id.recyList))
                .perform(RecyclerViewActions.actionOnItemAtPosition
                <RecyMainActivity.MainAdapter.ViewHolder>(ITEM_BELOW_THE_FOLD, click()));

        var itemShow = "" + (ITEM_BELOW_THE_FOLD - 3);
        onView(ViewMatchers.withText(itemShow)).check(ViewAssertions.matches(
                ViewMatchers.isDisplayed()
        ));
    }

    @Test
    fun itemInMiddleOfList_hasSpecialText() {
        onView(ViewMatchers.withId(R.id.recyList))
                .perform(RecyclerViewActions.scrollToHolder(isMiddleMachers()));

        val middleElementText = "item in the middle";
        onView(ViewMatchers.withText(middleElementText)).check(
                ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    fun isMiddleMachers(): Matcher<RecyMainActivity.MainAdapter.ViewHolder> {
        return object : TypeSafeMatcher<RecyMainActivity.MainAdapter.ViewHolder>() {
            override fun describeTo(description: Description?) {
                description!!.appendText(" item in the middle");
            }

            override fun matchesSafely(item: RecyMainActivity.MainAdapter.ViewHolder?): Boolean {
                return item!!.isInTheMiddle();
            }

        };
    }

}