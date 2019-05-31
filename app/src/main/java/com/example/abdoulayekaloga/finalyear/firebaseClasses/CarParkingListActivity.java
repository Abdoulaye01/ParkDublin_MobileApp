//package com.example.abdoulayekaloga.finalyear.firebaseClasses;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.abdoulayekaloga.finalyear.Adapter.CarParkingListAdapter;
//import com.example.abdoulayekaloga.finalyear.R;
//import com.example.abdoulayekaloga.finalyear.Util.CarParkingList;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class CarParkingListActivity extends AppCompatActivity {
//    private RecyclerView parking_lists;
//    @NonNull
//    private final ArrayList <CarParkingList> list_of_carpark = new ArrayList <>();
//    private CarParkingListAdapter mcarParkingAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_parkinglist);
//        init();
//    }
//
//    private void init() {
//        parking_lists = findViewById(R.id.parking_lists);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        parking_lists.setLayoutManager(mLayoutManager);
//        mcarParkingAdapter = new CarParkingListAdapter(this,list_of_carpark);
//        parking_lists.setAdapter(mcarParkingAdapter);
//        fetchData();
//
//
//
//    }
//    // We are fetching data from firebase database
//    //we implement the firebase methods
//    private void fetchData() {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//
//        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Nodata");
//        databaseReference.keepSynced(true);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot carSnapShot: dataSnapshot.getChildren())
//                {
//                    CarParkingList carParking = carSnapShot.getValue(CarParkingList.class);
//                    list_of_carpark.add(carParking);
//                }
//                //mcarParkingAdapter =new CarParkingListAdapter(DataRetrieved.this,list_of_carpark);
//                mcarParkingAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//
//            }
//        });
//    }
//}
