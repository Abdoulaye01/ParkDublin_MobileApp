package com.example.abdoulayekaloga.finalyear.ParserData;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdoulayekaloga.finalyear.Adapter.LiveCarParkFeedAdapter;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.LiveCarparkFeed;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.CarParkingProductDetailActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class LiveCarParkFeedActivity extends AppCompatActivity {

    private Dialog feedDialog;
   private ImageView closePopFeed;
    private TextView txtAlertLiveFeed;
    private TextView txtAlertLiveFeedTitle;
    @NonNull
    private final
    List<LiveCarparkFeed> carparkFeeds = new ArrayList<>();
    private  LiveCarparkFeed carparkFeed ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_car_park_feed);
        feedDialog = new Dialog(this);


        //url server we connecting to
          String urlstring = "http://www.dublincity.ie/dublintraffic/cpdata.xml";
          new getXMLdata().execute(urlstring);

        feedDialog.setContentView(R.layout.custom_pop_api_feed);
        closePopFeed = feedDialog.findViewById(R.id.closePopFeed);
        txtAlertLiveFeed = feedDialog.findViewById(R.id.txtAlertLiveFeed);
        txtAlertLiveFeedTitle = feedDialog.findViewById(R.id.txtAlertLiveFeedTitle);
        closePopFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedDialog.dismiss();
            }
        });
        feedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        feedDialog.show();
    }


    // REST calls
    private class getXMLdata extends AsyncTask<String, Void, String> {
        @NonNull
        @Override
        protected String doInBackground(String[] params) {
            String result = "";
            String urlString = params[0];

            try {
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");

                if (httpURLConnection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    result = stringBuilder.toString();

                    httpURLConnection.disconnect();


                } else
                    result = "response code is not 200";


            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Construct the data source
            ArrayList <LiveCarparkFeed> arrayOfParkings = null;
            try {
                arrayOfParkings = (ArrayList <LiveCarparkFeed>) parseXML(result);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Create the adapter to convert the array to views
            final LiveCarParkFeedAdapter adapter = new LiveCarParkFeedAdapter(LiveCarParkFeedActivity.this, arrayOfParkings);
            // Attach the adapter to a ListView
            ListView listView = findViewById(R.id.parking_list);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String value= String.valueOf(adapter.getItemId(position));

                    //This concatinate and 0 to the position of the list
                    //example 01,02,03,04....013
                    String concValue = "0".concat(value);
                    Log.d("livefeedvalue",value);
                   //  creating an intent
                    Intent intent = new Intent(getApplicationContext(), CarParkingProductDetailActivity.class);

                    //putting value and id to intent
                    intent.putExtra("productId",concValue);

                   // starting the activity  wiht intent
                   startActivity(intent);
                  Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();


                }
            });
        }


    }

    @NonNull
    private List <LiveCarparkFeed> parseXML(String result) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
//Get the eventtype of xmlPullParser
        xpp.setInput(new StringReader(result));
        int eventType = xpp.getEventType();
        String currentRegion = "";
        String txt = "";

        //Loop until reach the document end
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG)

                if (xpp.getName().equalsIgnoreCase("carpark")) {
                    String valueName = xpp.getAttributeValue(null,"name");
                    carparkFeed = new LiveCarparkFeed();

                    carparkFeed.setName(valueName);


                    String valueSpace = xpp.getAttributeValue(null, "spaces");
                    carparkFeed.setSpaces(valueSpace);

                    carparkFeeds.add(carparkFeed);

                }

            eventType = xpp.next();

        }

        return carparkFeeds;
    }

}