package com.example.abdoulayekaloga.finalyear.firebaseClasses;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.example.abdoulayekaloga.finalyear.Adapter.ProductDetailAdapter;
import com.example.abdoulayekaloga.finalyear.Util.ProductDetail;
import com.example.abdoulayekaloga.finalyear.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import java.util.List;


public class CarParkingProductDetailActivity extends AppCompatActivity {

    private RecyclerView PrdRecyclerView;
    private ProductDetailAdapter adapter;
    private List <ProductDetail> productDetails ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_parking_product_detail);


        PrdRecyclerView = findViewById(R.id.PrdrecyclerView);
        PrdRecyclerView.setHasFixedSize(true);
        PrdRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        productDetails = new ArrayList<>();
        Intent intent = getIntent();
        String value = intent.getStringExtra("productId");
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();


        DatabaseReference dbProducts = FirebaseDatabase.getInstance().getReference("carparkingproducts").child(value);
        dbProducts.keepSynced(true);
        dbProducts.orderByChild("branch").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        ProductDetail p = productSnapshot.getValue(ProductDetail.class);
                        productDetails.add(p);
                    }

                    adapter = new ProductDetailAdapter(CarParkingProductDetailActivity.this, productDetails);
                    PrdRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Car parking error", databaseError.getMessage());
            }
        });
    }

}