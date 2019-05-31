package com.example.abdoulayekaloga.finalyear.firebaseClasses;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abdoulayekaloga.finalyear.Adapter.BookingAdapter;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BookingActivity extends AppCompatActivity {

    SearchView searchView;
    private RecyclerView brecyclerView;
    private List<Booking> bookingList;
    private BookingAdapter bookingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //holder
        brecyclerView = findViewById(R.id.bookingRecyclerView);
        brecyclerView.setHasFixedSize(true);
        brecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (bookingAdapter != null) {
                    bookingAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });


        bookingList = new ArrayList<>();

        //Database reference object
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("carparkings");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //iterating through all the nodes
                    for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                        //Getting the booking
                        Booking booking = bookingSnapshot.getValue(Booking.class);
                        //adding booking to the list
                        bookingList.add(booking);
                    }
                    //value holder
                    //Creating adapter
                    bookingAdapter = new BookingAdapter(BookingActivity.this, bookingList);
                    //attaching adapter to the recycleview
                    brecyclerView.setAdapter(bookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
