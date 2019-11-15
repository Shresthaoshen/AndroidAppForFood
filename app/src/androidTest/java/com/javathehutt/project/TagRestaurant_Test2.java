package com.javathehutt.project;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TagRestaurant_Test2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void tagRestaurant_Test2() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.uiBtnAdd),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userTxtTitle),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.uiTxtTitleLabel),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("i"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.userTxtRating),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.uiTxtRatingLabel),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.userTxtPrice),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.uiTxtPriceLabel),
                                        0),
                                0)));
        appCompatEditText3.perform(scrollTo(), replaceText("3"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.userTxtTags),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.uiTxtTagsLabel),
                                        0),
                                0)));
        appCompatEditText4.perform(scrollTo(), replaceText("y"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.userTxtTags), withText("y"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.uiTxtTagsLabel),
                                        0),
                                0)));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction button = onView(
                allOf(withId(R.id.uiBtnAdd),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.scrollView1),
                                        0),
                                7),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
