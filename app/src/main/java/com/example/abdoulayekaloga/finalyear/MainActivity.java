package com.example.abdoulayekaloga.finalyear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;

import com.example.abdoulayekaloga.finalyear.UserModel.AboutUs;
import com.example.abdoulayekaloga.finalyear.UserModel.ProfileActivity;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.RecentBookingActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.BookingActivity;
import com.example.abdoulayekaloga.finalyear.ParserData.LiveCarParkFeedActivity;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.LoadingValuesfromFirebaseDatabase;
import com.example.abdoulayekaloga.finalyear.UserModel.LoginActivity;
import com.example.abdoulayekaloga.finalyear.MapClass.MapsActivity;
import com.example.abdoulayekaloga.finalyear.UserModel.RegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener  {
    private Context mycontext;
    private FirebaseAuth mAuth;
    private CircleImageView user_photo;
    private  TextView user_fullname;
    private DatabaseReference usersRef;
    private CardView openingHours,promoId,spaceAvailable,viewCarParking,viewMap;
    private String currentUserId;
    private AdView mAdView;
    private   FirebaseUser currentUser;
    //firebase
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mycontext = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();

        setupFirebaseListener();
  currentUser = FirebaseAuth.getInstance().getCurrentUser();


// Initialize the Mobile Ads SDK
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
       AdRequest  adRequest = new AdRequest.Builder().build();
       mAdView.loadAd(adRequest);

       //ad methods
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d("Ad Test","Ad loaded successfully");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d("Ad Test","Ad failed to load");            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("Ad Test","Ad is visible now");
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d("Ad Test","User clicked on the add");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d("Ad Test","User left the app");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d("Ad Test","User return back to the app after tapping on the ad");
            }
        });

        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

         user_fullname = headerView.findViewById(R.id.nav_username);
         user_photo = headerView.findViewById(R.id.nav_user_photo);

        if(currentUser != null && currentUser.isEmailVerified()) {
            currentUserId = currentUser.getUid();

            usersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.hasChild("fullname")) {
                            String fullname = dataSnapshot.child("fullname").getValue().toString();
                            user_fullname.setText(fullname);

                        }
                        if (dataSnapshot.hasChild("profileimage")) {
                            String image = dataSnapshot.child("profileimage").getValue().toString();
                            Picasso.get().load(image).placeholder(R.drawable.registration).into(user_photo);

                        } else {
                            //Toast.makeText(mycontext, "Profile name do not exists", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



        viewCarParking = findViewById(R.id.viewCarParking);
        viewCarParking.setOnClickListener(this);
        spaceAvailable = findViewById(R.id.spaceAvailable);
        spaceAvailable.setOnClickListener(this);
        viewMap = findViewById(R.id.viewMap);
        viewMap.setOnClickListener(this);
       // promoId = findViewById(R.id.promoId);
//        promoId.setOnClickListener(this);
        openingHours = findViewById(R.id.openingHours);
        openingHours.setOnClickListener(this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Toast.makeText(mycontext, "click login", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_recentTransaction) {

            Intent intent = new Intent(MainActivity.this, RecentBookingActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_registration) {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {
            if(currentUser != null) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT,"Try this new car parking app https://play.google.com/store/par");//link is just for test purpose app not yet on play store
            startActivity(Intent.createChooser(share,"Share Via"));



        } else if (id == R.id.nav_logOut) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onClick(@NonNull View v) {
        Intent i;

        switch (v.getId())
        {

            case R.id.viewCarParking: i = new Intent(this,BookingActivity.class);
               startActivity(i);
                Toast.makeText(mycontext, "click car parking", Toast.LENGTH_LONG).show();
                break;

            case  R.id.spaceAvailable: i = new Intent(this,LiveCarParkFeedActivity.class);
                startActivity(i);
                Toast.makeText(mycontext, "Clicking Review Tab", Toast.LENGTH_LONG).show();
                break;

            case  R.id.openingHours: i = new Intent(this,LoadingValuesfromFirebaseDatabase.class);
            startActivity(i);
                Toast.makeText(mycontext, "Clicking Location Tab", Toast.LENGTH_LONG).show();
                break;

            case  R.id.viewMap: i = new Intent(this,MapsActivity.class);
                startActivity(i);
                Toast.makeText(mycontext, "Clicking Map Tab", Toast.LENGTH_LONG).show();
                break;
        }


    }



    private void setupFirebaseListener(){
        // Log.d(TAG, "setupFirebaseListener: setting up the auth state listener.");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //   Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }else{
                    Toast.makeText(MainActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

}

