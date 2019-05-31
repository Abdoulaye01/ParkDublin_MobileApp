package com.example.abdoulayekaloga.finalyear.MapClass;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapsActivityTest {

    @Test
    public void MapsActivityTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.abdoulayekaloga.finalyear", appContext.getPackageName());
    }

}