package com.example.abdoulayekaloga.finalyear.firebaseClasses;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.abdoulayekaloga.finalyear.Adapter.RecentBookingAdapter;
import com.example.abdoulayekaloga.finalyear.MainActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.RecentBooking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class RecentBookingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView recentBookingRecyclerView;
    private RecentBookingAdapter adapter;
    private List<com.example.abdoulayekaloga.finalyear.Util.RecentBooking> productDetails;
    @Nullable
    private
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_booking);

        mAuth = FirebaseAuth.getInstance();


        recentBookingRecyclerView = findViewById(R.id.recentBookingRecyclerView);
        recentBookingRecyclerView.setHasFixedSize(true);
        recentBookingRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        productDetails = new ArrayList<>();

        currentUser = mAuth.getCurrentUser();

        if (currentUser != null && currentUser.isEmailVerified() ) {

            final DatabaseReference dbProducts = FirebaseDatabase.getInstance().getReference("customerwhobought");
            dbProducts.keepSynced(true);

            dbProducts.limitToLast(10).addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                            for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {

                              RecentBooking rp = productSnapshot.getValue(RecentBooking.class);
                                    if(rp.getUsername().equals(currentUser.getEmail())){
                                    Log.d("Failed to convert a value of type java.lang.String to double", String.valueOf(rp));
                                    productDetails.add(0,rp);

                                }

                            }

                        }

                        adapter = new RecentBookingAdapter(RecentBookingActivity.this, productDetails);
                        recentBookingRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Car parking error", databaseError.getMessage());
                }
            });


        }
        if (currentUser == null) {
            Intent intent = new Intent(RecentBookingActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(RecentBookingActivity.this, "Your are not register,Please register or log in", Toast.LENGTH_LONG).show();
            finish();
        }

    }

}



