package com.example.abdoulayekaloga.finalyear.firebaseClasses;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abdoulayekaloga.finalyear.Adapter.HourlyAdapter;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.OpeningHoursInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarparkingInfoHourActivity extends AppCompatActivity {


    private RecyclerView info_recyclerview;
    private HourlyAdapter adapter;
    private List<OpeningHoursInformation> openingHour ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carparking_information);

        getIncomingIntent();

        info_recyclerview = findViewById(R.id.info_recyclerview);
        info_recyclerview.setHasFixedSize(true);
        info_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        openingHour = new ArrayList<>();

        Intent intent = getIntent();
        String value = intent.getStringExtra("id");
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();

        final DatabaseReference dbProducts = FirebaseDatabase.getInstance().getReference("carparkinghours").child(value);
        dbProducts.keepSynced(true);
        dbProducts.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        OpeningHoursInformation openingHours1 = productSnapshot.getValue(OpeningHoursInformation.class);
                        openingHour.add(openingHours1);
                    }

                    adapter = new HourlyAdapter(CarparkingInfoHourActivity.this,openingHour);
                    info_recyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void getIncomingIntent() {

        if(getIntent().hasExtra("image")&& getIntent().hasExtra("image_title") && getIntent().hasExtra("description")){
            String image = getIntent().getStringExtra("image");
            String image_title = getIntent().getStringExtra("image_title");
           // String description = getIntent().getStringExtra("description");

            setImage(image,image_title );

        }
    }

    private void setImage(String imageUrl, String imageName){
        //Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);
       // TextView desption = findViewById(R.id.description);
        //desption.setText(description);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

    

}
