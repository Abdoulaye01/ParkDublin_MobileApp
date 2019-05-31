package com.example.abdoulayekaloga.finalyear;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.abdoulayekaloga.finalyear.UserModel.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UpdateProfileTest2 {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void updateProfileTest2() throws InterruptedException {
        Thread.sleep(2000);

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_login));
        Espresso.onView(withId(R.id.loginMail)).perform(typeText("sekousylla64@yahoo.com"));
        Espresso.onView(withId(R.id.loginPassword)).perform(typeText("123456"));
        closeSoftKeyboard();

        onView(withId(R.id.loginBtn)).perform(click());

//

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile));
        Thread.sleep(1000);
//
//        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
//        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_logOut));
//        Thread.sleep(1000);
//
//        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
//        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_about));
//        Thread.sleep(1000);
    }
}

//    @Rule
//    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
//
//    @Test
//    public void updateProfileTest2() {
////        ViewInteraction appCompatImageButton = onView(
////                allOf(withContentDescription("navigation_view"),
////                        childAtPosition(
////                                allOf(withId(R.id.toolbar),
////                                        childAtPosition(
////                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
////                                                0)),
////                                1),
////                        isDisplayed()));
////        appCompatImageButton.perform(click());
//
////        ViewInteraction navigationMenuItemView = onView(
////                allOf(childAtPosition(
////                        allOf(withId(R.id.design_navigation_view),
////                                childAtPosition(
////                                        withId(R.id.nav_view),
////                                        0)),
////                        4),
////                        isDisplayed()));
////        navigationMenuItemView.perform(click());
//
////        ViewInteraction appCompatImageButton2 = onView(
////                allOf(withContentDescription("navigation_view"),
////                        childAtPosition(
////                                allOf(withId(R.id.toolbar),
////                                        childAtPosition(
////                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
////                                                0)),
////                                1),
////                        isDisplayed()));
////        appCompatImageButton2.perform(click());
//
////        ViewInteraction navigationMenuItemView2 = onView(
////                allOf(childAtPosition(
////                        allOf(withId(R.id.design_navigation_view),
////                                childAtPosition(
////                                        withId(R.id.nav_view),
////                                        0)),
////                        1),
////                        isDisplayed()));
////        navigationMenuItemView2.perform(click());
//
//        assertNotNull(LoginActivity.findViewById(R.id.userName));
//
////        ViewInteraction appCompatButton = onView(
////                allOf(withId(R.id.loginBtn), withText("Log In"),
////                        childAtPosition(
////                                childAtPosition(
////                                        withId(android.R.id.content),
////                                        0),
////                                4),
////                        isDisplayed()));
////        appCompatButton.perform(click());
//
////        ViewInteraction appCompatEditText = onView(
////                allOf(withId(R.id.loginMail),
////                        childAtPosition(
////                                childAtPosition(
////                                        withId(android.R.id.content),
////                                        0),
////                                1),
////                        isDisplayed()));
////        appCompatEditText.perform(replaceText("sekousylla64@yahoo.com"), closeSoftKeyboard());
//        Espresso.onView(withId(R.id.loginMail)).perform(typeText("sekousylla64@yahoo.com"));
//
////        ViewInteraction appCompatEditText2 = onView(
////                allOf(withId(R.id.loginPassword),
////                        childAtPosition(
////                                childAtPosition(
////                                        withId(android.R.id.content),
////                                        0),
////                                2),
////                        isDisplayed()));
////        appCompatEditText2.perform(replaceText("123456"), closeSoftKeyboard());
//        Espresso.onView(withId(R.id.loginPassword)).perform(typeText("123456"));
//
////        ViewInteraction appCompatButton2 = onView(
////                allOf(withId(R.id.loginBtn), withText("Log In"),
////                        childAtPosition(
////                                childAtPosition(
////                                        withId(android.R.id.content),
////                                        0),
////                                4),
////                        isDisplayed()));
////        appCompatButton2.perform(click());
//
//        Espresso.onView(withId(R.id.loginBtn)).perform(click());
//
////        ViewInteraction appCompatImageButton3 = onView(
////                allOf(withContentDescription("navigation_view"),
////                        childAtPosition(
////                                allOf(withId(R.id.toolbar),
////                                        childAtPosition(
////                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
////                                                0)),
////                                1),
////                        isDisplayed()));
////        appCompatImageButton3.perform(click());
//
////        ViewInteraction navigationMenuItemView3 = onView(
////                allOf(childAtPosition(
////                        allOf(withId(R.id.design_navigation_view),
////                                childAtPosition(
////                                        withId(R.id.nav_view),
////                                        0)),
////                        4),
////                        isDisplayed()));
////        navigationMenuItemView3.perform(click());
//
////        ViewInteraction appCompatImageView = onView(
////                allOf(withId(R.id.editUserInformation),
////                        childAtPosition(
////                                allOf(withId(R.id.profile_layout),
////                                        childAtPosition(
////                                                withId(R.id.layout),
////                                                2)),
////                                0)));
////        appCompatImageView.perform(scrollTo(), click());
//        Espresso.onView(withId(R.id.editUserInformation)).perform(click());
//
//
////        ViewInteraction appCompatEditText3 = onView(
////                allOf(withId(R.id.userName),
////                        childAtPosition(
////                                childAtPosition(
////                                        withId(android.R.id.content),
////                                        0),
////                                1),
////                        isDisplayed()));
////        appCompatEditText3.perform(replaceText("seku"), closeSoftKeyboard());
//        Espresso.onView(withId(R.id.userName)).perform(typeText("seku"));
//
//
////        ViewInteraction appCompatEditText4 = onView(
////                allOf(withId(R.id.userFullName),
////                        childAtPosition(
////                                childAtPosition(
////                                        withId(android.R.id.content),
////                                        0),
////                                2),
////                        isDisplayed()));
////        appCompatEditText4.perform(replaceText("sekutest2"), closeSoftKeyboard());
//        Espresso.onView(withId(R.id.userFullName)).perform(typeText("sekutest2"));
//
////        ViewInteraction appCompatEditText5 = onView(
////                allOf(withId(R.id.userMobile),
////                        childAtPosition(
////                                childAtPosition(
////                                        withId(android.R.id.content),
////                                        0),
////                                3),
////                        isDisplayed()));
////        appCompatEditText5.perform(replaceText("0123456789"), closeSoftKeyboard());
//        Espresso.onView(withId(R.id.userMobile)).perform(typeText("0123456789"));
//
////        ViewInteraction appCompatButton3 = onView(
////                allOf(withId(R.id.updateUserInformationBtn), withText("Update Information"),
////                        childAtPosition(
////                                childAtPosition(
////                                        withId(android.R.id.content),
////                                        0),
////                                5),
////                        isDisplayed()));
////        appCompatButton3.perform(click());
////
////    }
//        Espresso.closeSoftKeyboard();
////
//        Espresso.onView(withId(R.id.updateUserInformationBtn)).perform(click());
//
////
////    private static Matcher<View> childAtPosition(
////            final Matcher<View> parentMatcher, final int position) {
////
////        return new TypeSafeMatcher<View>() {
////            @Override
////            public void describeTo(Description description) {
////                description.appendText("Child at position " + position + " in parent ");
////                parentMatcher.describeTo(description);
////            }
////
////            @Override
////            public boolean matchesSafely(View view) {
////                ViewParent parent = view.getParent();
////                return parent instanceof ViewGroup && parentMatcher.matches(parent)
////                        && view.equals(((ViewGroup) parent).getChildAt(position));
////            }
////        };
////    }
//    }
//}
//
