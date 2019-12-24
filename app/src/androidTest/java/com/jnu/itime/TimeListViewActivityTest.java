package com.jnu.itime;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TimeListViewActivityTest {

    @Rule
    public ActivityTestRule<TimeListViewActivity> mActivityTestRule = new ActivityTestRule<>(TimeListViewActivity.class);

    @Test
    public void timeListViewActivityTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_time_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_frame_layout),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.add_title_text),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.add_title_text),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("标题1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.add_description_text),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("备注1"), closeSoftKeyboard());

        ViewInteraction constraintLayout = onView(
                allOf(withId(R.id.select_time_layout),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        constraintLayout.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("确定"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("确定"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.add_time_toolbar_comfirm), withContentDescription("add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.mToolbar),
                                        3),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.time_title), withText("标题1"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout1),
                                        childAtPosition(
                                                withId(R.id.time_list_view),
                                                0)),
                                2),
                        isDisplayed()));
        textView.check(matches(withText("标题1")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.time_description), withText("备注1"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout1),
                                        childAtPosition(
                                                withId(R.id.time_list_view),
                                                0)),
                                4),
                        isDisplayed()));
        textView2.check(matches(withText("备注1")));

        DataInteraction relativeLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.time_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(0);
        relativeLayout.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.detail_time_toolbar_modify), withContentDescription("modify"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detail_time_toolbar),
                                        3),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.add_title_text), withText("标题1"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.add_title_text), withText("标题1"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("标题2"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.add_title_text), withText("标题2"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.add_description_text), withText("备注1"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("备注2"));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.add_description_text), withText("备注2"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout2),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.add_time_toolbar_comfirm), withContentDescription("add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.mToolbar),
                                        3),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.detail_fixed_title_text), withText("标题2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("标题2")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.detail_fixed_description_text), withText("备注2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("备注2")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.detail_fixed_description_text), withText("备注2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                2),
                        isDisplayed()));
        textView5.check(matches(withText("备注2")));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("转到上一层级"),
                        childAtPosition(
                                allOf(withId(R.id.detail_time_toolbar),
                                        childAtPosition(
                                                withContentDescription(" "),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.time_title), withText("标题2"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout1),
                                        childAtPosition(
                                                withId(R.id.time_list_view),
                                                0)),
                                2),
                        isDisplayed()));
        textView6.check(matches(withText("标题2")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.time_description), withText("备注2"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayout1),
                                        childAtPosition(
                                                withId(R.id.time_list_view),
                                                0)),
                                4),
                        isDisplayed()));
        textView7.check(matches(withText("备注2")));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("iTime"),
                        childAtPosition(
                                allOf(withId(R.id.general_toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        7),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("iTime"),
                        childAtPosition(
                                allOf(withId(R.id.general_toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        1),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        DataInteraction relativeLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.time_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(0);
        relativeLayout2.perform(click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.detail_time_toolbar_delete), withContentDescription("delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.detail_time_toolbar),
                                        3),
                                0),
                        isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("确定"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton3.perform(scrollTo(), click());
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
