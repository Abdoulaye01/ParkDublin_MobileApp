package com.example.abdoulayekaloga.finalyear.UserModel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.abdoulayekaloga.finalyear.R;

@SuppressWarnings("deprecation")
public class AboutUs extends AppCompatActivity {

    private static final String BULLET_SYMBOL ="&#8226" ;
    private static final String START_SYMBOL = "*";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView policyTxt = findViewById(R.id.policyTxt);

        //this takes you to the next Line
        policyTxt.setText(new StringBuilder().append(getString(R.string.what_we_collect)).append(System.getProperty("line.separator")).append(System.getProperty("line.separator")).append(Html.fromHtml(BULLET_SYMBOL + getString(R.string.collect_name))).append(System.getProperty("line.separator")).append(Html.fromHtml(BULLET_SYMBOL + getString(R.string.collect_email))).append(System.getProperty("line.separator")).append(Html.fromHtml(BULLET_SYMBOL + getString(R.string.collect_address))).append(System.getProperty("line.separator")).append(Html.fromHtml(BULLET_SYMBOL + getString(R.string.collect_username))).append(System.getProperty("line.separator")).append(Html.fromHtml(BULLET_SYMBOL + getString(R.string.collect_password))).append(System.getProperty("line.separator")).append(Html.fromHtml(BULLET_SYMBOL + getString(R.string.gps_location))).append(System.getProperty("line.separator")).append(Html.fromHtml(getString(R.string.if_you_reserve_parking))).append(System.getProperty("line.separator")).append(Html.fromHtml(BULLET_SYMBOL + getString(R.string.collecting_license_plate))).append(System.getProperty("line.separator")).append(Html.fromHtml(BULLET_SYMBOL + getString(R.string.collecting_user_card_information))).toString()
        );


    TextView myInformationTxt = findViewById(R.id.myInformationTxt);


        //this takes you to the next Line
        myInformationTxt.setText(new StringBuilder().append(getString(R.string.persona)).append(System.getProperty("line.separator")).append(System.getProperty("line.separator")).append(Html.fromHtml(START_SYMBOL + getString(R.string.developer_name))).append(System.getProperty("line.separator")).append(Html.fromHtml(START_SYMBOL + getString(R.string.developer_degree))).toString()

            );
}
}
