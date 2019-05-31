package com.example.abdoulayekaloga.finalyear;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.abdoulayekaloga.finalyear.MapClass.MapsActivity;
import com.example.abdoulayekaloga.finalyear.ParserData.LiveCarParkFeedActivity;
import com.example.abdoulayekaloga.finalyear.Util.LiveCarparkFeed;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.BookingActivity;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.LoadingValuesfromFirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity>mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private  MainActivity mainActivity = null;

    //Monitor and return the Booking activity or Map Activity
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LiveCarParkFeedActivity.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    /*
    This method below test for the booking activity
     */
//    @Test
//    public void testLaunchOfBookingActivityOnButtonClick(){
//
//        assertNotNull(mainActivity.findViewById(R.id.carparkId));
//
//        //onclick of this button go to booking activity
//        onView(withId(R.id.carparkId)).perform(click());
//
//        //thread running
//        Activity bookingActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
//
//        assertNotNull(bookingActivity);
//
//        bookingActivity.finish();
//
//
//    }


    /*
    This method below test for the Map activity
     */
//    @Test
//    public void testLaunchOfMapActivityOnButtonClick(){
//
//        assertNotNull(mainActivity.findViewById(R.id.viewMap));
//
//        //onclick of this button go to booking activity
//        onView(withId(R.id.viewMap)).perform(click());
//
//        //thread running
//        Activity mapsActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
//
//        assertNotNull(mapsActivity);
//
//        mapsActivity.finish();
//    }


    /*
   This method below test for the load the values from the firebase and display the car parking information
    */
   // @Test
//    public void testLaunchOfLoadingValuesActivityOnButtonClick(){
//
//        assertNotNull(mainActivity.findViewById(R.id.openingHours));
//
//        //onclick of this button go to booking activity
//        onView(withId(R.id.openingHours)).perform(click());
//
//        //thread running
//        Activity loadingvaluesActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
//
//        assertNotNull(loadingvaluesActivity);
//
//        loadingvaluesActivity.finish();
//    }

    /*
This method below test for the live data generate from the parser
*/
    @Test
    public void testLaunchOfMapActivityOnButtonClick(){

        assertNotNull(mainActivity.findViewById(R.id.spaceAvailable));

        //onclick of this button go to booking activity
        onView(withId(R.id.spaceAvailable)).perform(click());

        //thread running
        Activity liveActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(liveActivity);

        liveActivity.finish();

    }
    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}