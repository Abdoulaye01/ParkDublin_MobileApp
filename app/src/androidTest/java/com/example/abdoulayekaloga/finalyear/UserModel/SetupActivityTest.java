package com.example.abdoulayekaloga.finalyear.UserModel;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.abdoulayekaloga.finalyear.Payment.CardDetailActivity;
import com.example.abdoulayekaloga.finalyear.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class SetupActivityTest {


    @Rule
    public ActivityTestRule<CardDetailActivity> setupActivityTestRule  = new ActivityTestRule<>(CardDetailActivity.class);

    private String fullName = "sekousylla@yahoo.com";
    private  String userName = "123456";
    private String phoneNumber = "0123456789";
    private String image = "https://firebasestorage.googleapis.com/v0/b/root-app-230716.appspot.com/o/Profile%20Images%2FDszaZ824Y8RMHg7BIlfuXBNIazS2.jpg?alt=media&token=ea668dac-77ba-41dd-8e44-6ca721f3fe38";

    @Before
    public void setUp() throws Exception {
    }



    @Rule
    public ActivityTestRule<CardDetailActivity> rule  = new  ActivityTestRule<CardDetailActivity>(CardDetailActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("MYKEY", "Hello");
            return intent;
        }
    };

//    @Test
//    public void ensureIntentDataIsDisplayed() throws Exception {
//        CardDetailActivity activity = rule.getActivity();
//
//        View viewById = activity.findViewById(R.id.target);
//
//        assertThat(viewById,notNullValue());
//        assertThat(viewById, instanceOf(TextView.class));
//        TextView textView = (TextView) viewById;
//        assertThat(textView.getText().toString(),is("Hello"));
//    }
    @Test
    public void testUserInputScenario(){
        //Input some text in the edit text
        Espresso.onView(withId(R.id.userMail)).perform(typeText(userName));

        Espresso.onView(withId(R.id.userPassword)).perform(typeText(fullName));
        Espresso.onView(withId(R.id.loginBtn)).perform(click());

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile));

//        Espresso.onView(withId(R.id.userMobile)).perform(typeText(phoneNumber));
//
//        Espresso.onView(withId(R.id.profileImage)).perform(typeText(image));

        //Close soft keyboard
        Espresso.closeSoftKeyboard();

        //Perform button click
        Espresso.onView(withId(R.id.loginBtn)).perform(click());
//
//        //Checking the text in the textView
//
//
////        intended(allOf(
////                hasComponent(hasShortClassName(".DisplayMessageActivity")),
////                toPackage(PACKAGE_NAME),
////                hasExtra(MainActivity.EXTRA_MESSAGE, MESSAGE)));
//    }


//    @Test
//    public void testUserInputScenario(){
//        //Input some text in the edit text
//        Espresso.onView(withId(R.id.carPlate)).perform(typeText(userName));
//
////        Espresso.onView(withId(R.id.userFullName)).perform(typeText(fullName));
//
//        Espresso.onView(withId(R.id.userMobile)).perform(typeText(phoneNumber));
//
//        Espresso.onView(withId(R.id.profileImage)).perform(typeText(image));

        //Close soft keyboard
        Espresso.closeSoftKeyboard();

        //Perform button click
        Espresso.onView(withId(R.id.btnextActivity)).perform(click());

        //Checking the text in the textView


//        intended(allOf(
//                hasComponent(hasShortClassName(".DisplayMessageActivity")),
//                toPackage(PACKAGE_NAME),
//                hasExtra(MainActivity.EXTRA_MESSAGE, MESSAGE)));
    }

    @After
    public void tearDown() throws Exception {
    }
}