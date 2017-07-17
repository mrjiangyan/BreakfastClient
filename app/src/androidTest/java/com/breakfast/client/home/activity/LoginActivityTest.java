package com.breakfast.client.home.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.breakfast.client.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {
        ViewInteraction deletableEditText = onView(
                allOf(withId(R.id.tv_url), isDisplayed()));
        deletableEditText.perform(click());

        ViewInteraction deletableEditText2 = onView(
                allOf(withId(R.id.tv_url), isDisplayed()));
        deletableEditText2.perform(replaceText("localhost:31119"), closeSoftKeyboard());

        ViewInteraction deletableEditText3 = onView(
                allOf(withId(R.id.tv_username), isDisplayed()));
        deletableEditText3.perform(replaceText("superadmin"), closeSoftKeyboard());

        ViewInteraction deletableEditText4 = onView(
                allOf(withId(R.id.tv_password), isDisplayed()));
        deletableEditText4.perform(replaceText("bullshit"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.sign_in_button), withText("登录"), isDisplayed()));
        appCompatButton.perform(click());

    }

}
