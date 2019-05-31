package com.example.abdoulayekaloga.finalyear.MapClass;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.abdoulayekaloga.finalyear.Util.CordinateLtLg;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.CarParkingProductDetailActivity;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.SupportMapFragment;
import com.example.abdoulayekaloga.finalyear.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("deprecation")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private FloatingActionButton bt_showAllCarParking;
    private FloatingActionButton bt_nearestCarParking;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int Request_User_Location_Code = 99;
    private List<CordinateLtLg> cordinateLtLgList;
    private Dialog feedDialog;
    private ImageView closePopFeed;
    private SeekBar distanceSeekBar;
    private int range ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        feedDialog = new Dialog(this);

        //relative_layout = (RelativeLayout)findViewById(R.id.relative_layout);
        bt_showAllCarParking = findViewById(R.id.bt_showAllCarParking);
        bt_nearestCarParking = findViewById(R.id.bt_nearestCarParking);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        feedDialog.setContentView(R.layout.custom_pop_api_map);
        closePopFeed = feedDialog.findViewById(R.id.closePopFeed);
        TextView txtAlertLiveFeed = feedDialog.findViewById(R.id.txtAlertLiveFeed);
        TextView   txtAlertLiveFeedTitle = feedDialog.findViewById(R.id.txtAlertLiveFeedTitle);
        closePopFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedDialog.dismiss();
            }
        });
        feedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        feedDialog.show();

        // Get seek bar and set max progress value.
       distanceSeekBar = findViewById(R.id.distanceSeekBar);
        //distanceSeekBar.setAlpha(range);

        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                range = progress;
                Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                if(progress == 1){
                    range = 1000;
                    Toast.makeText(getApplicationContext(),"1 Km", Toast.LENGTH_SHORT).show();
                }
                if(progress == 2){
                    range = 2000;
                    Toast.makeText(getApplicationContext(),"2 Km", Toast.LENGTH_SHORT).show();
                }
                if(progress == 3){
                    range = 3000;
                    Toast.makeText(getApplicationContext(),"3 Km", Toast.LENGTH_SHORT).show();
                }
                if(progress == 4){
                    range = 4000;
                    Toast.makeText(getApplicationContext(),"4 Km", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng( 53.339831974,-6.267165598 );
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)).setTitle("Dublin");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        //Showing the markers on the map
        bt_showAllCarParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
               // addLayer();
                showAllCarParking();
            }
        });



    }




    private void showAllCarParking() {
        cordinateLtLgList = new ArrayList<>();


        final DatabaseReference coordinates = FirebaseDatabase.getInstance().getReference("carparkingdistance");//.child(intent.getStringExtra("id"));
        coordinates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        final  CordinateLtLg cordinateLtLg = dt.getValue(CordinateLtLg.class);

                        LatLng location = new LatLng(Double.parseDouble(cordinateLtLg.getLatitude()),Double.parseDouble(cordinateLtLg.getLongitude()));
                        Log.d("location" + "", String.valueOf(location));

                        Location currentParkLoc = new Location("current Location");
                        currentParkLoc.setLatitude(Double.parseDouble(cordinateLtLg.getLatitude()));
                        currentParkLoc.setLongitude(Double.parseDouble(cordinateLtLg.getLongitude()));

                        double distance_bw_one_and_one = Math.ceil(currentParkLoc.distanceTo(mLastLocation));
                        Toast.makeText(getApplicationContext(), "Result: " + distance_bw_one_and_one + " m", Toast.LENGTH_LONG).show();
                        Log.d("verified", "Distance using distanceBetween(): " + distance_bw_one_and_one + " m"); //<-- CONVERT FROM METERS TO KM

                        if (distance_bw_one_and_one <= 4000) {

                            mMap.addMarker(new MarkerOptions().position(location)

                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_parking_48_allparkings))
                                    .title(cordinateLtLg.getId() + " - "+ cordinateLtLg.getDescription())
                                    .snippet(new StringBuilder("Distance: ").append(Math.round(distance_bw_one_and_one)).append(" m").toString()));

                            Toast.makeText(getApplicationContext(), "Result: " + distance_bw_one_and_one + " m", Toast.LENGTH_LONG).show();

                            Log.d("verified", "Distance Parnell: " + distance_bw_one_and_one + " m"); //<-- CONVERT FROM METERS TO KM
                        }

                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(@NonNull Marker marker) {
                                Intent info = new Intent(getApplicationContext(), CarParkingProductDetailActivity.class);
                                Toast.makeText(getApplicationContext(),marker.getTitle() + marker.getSnippet(), Toast.LENGTH_LONG).show();

                                info.putExtra("productId", marker.getTitle().substring(0,2));

                                Log.d("IDcarpark" + "", marker.getTitle().substring(0,2));
                                startActivity(info);

                            }
                        });

                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    private void checkUserLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_map_pin_usercurrentlocation_40));
        mCurrLocationMarker = mMap.addMarker(markerOptions);




    nearestCarParking();

        mMap.clear();



        cordinateLtLgList = new ArrayList<>();

        final DatabaseReference cordinates = FirebaseDatabase.getInstance().getReference("carparkingdistance");
        cordinates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        CordinateLtLg cordinateLtLg = dt.getValue(CordinateLtLg.class);

                        LatLng location = new LatLng(Double.parseDouble(cordinateLtLg.getLatitude()), Double.parseDouble(cordinateLtLg.getLongitude()));
                        Log.d("location" + "", String.valueOf(location));

                        Location currentParkLoc = new Location("currentParkLocation");
                        currentParkLoc.setLatitude(Double.parseDouble(cordinateLtLg.getLatitude()));
                        currentParkLoc.setLongitude(Double.parseDouble(cordinateLtLg.getLongitude()));

                        double distance_bw_one_and_one = Math.ceil(currentParkLoc.distanceTo(mLastLocation));//in M
                        Toast.makeText(getApplicationContext(), "Result: " + distance_bw_one_and_one + " m", Toast.LENGTH_LONG).show();
                        Log.d("verified", "Distance using distanceBetween(): " + distance_bw_one_and_one + " m");

                        if (distance_bw_one_and_one <= 3000) {

                            mMap.addMarker(new MarkerOptions().position(location)

                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_parking_48_nearest))
                                    .title(cordinateLtLg.getId().intern() + " - "+ cordinateLtLg.getDescription())
                                    .snippet(new StringBuilder("Distance: ").append(Math.round(distance_bw_one_and_one)).append(" m").toString()));

                            Toast.makeText(getApplicationContext(), "Result: " + distance_bw_one_and_one + " m", Toast.LENGTH_LONG).show();

                            Log.d("verified", "Distance Parnell: " + distance_bw_one_and_one + " m");

                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(@NonNull Marker marker) {


                                    Intent info = new Intent(getApplicationContext(), CarParkingProductDetailActivity.class);

                                    Toast.makeText(getApplicationContext(),marker.getTitle() + marker.getSnippet(), Toast.LENGTH_LONG).show();

                                    info.putExtra("productId", marker.getTitle().substring(0,2));


                                    Log.d("IDcarpark" + "", marker.getTitle().substring(0,2));
                                    startActivity(info);

                                }
                            });

                        }
                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    private void nearestCarParking() {
        bt_nearestCarParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.clear();

                cordinateLtLgList = new ArrayList<>();


                final DatabaseReference coordinates = FirebaseDatabase.getInstance().getReference("carparkingdistance");
                coordinates.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                final  CordinateLtLg cordinateLtLg = dt.getValue(CordinateLtLg.class);

                                LatLng location = new LatLng(Double.parseDouble(cordinateLtLg.getLatitude()),Double.parseDouble(cordinateLtLg.getLongitude()));
                                Log.d("location" + "", String.valueOf(location));

                                Location currentParkLoc = new Location("current Location");
                                currentParkLoc.setLatitude(Double.parseDouble(cordinateLtLg.getLatitude()));
                                currentParkLoc.setLongitude(Double.parseDouble(cordinateLtLg.getLongitude()));


                                double distance_bw_one_and_one = Math.ceil(currentParkLoc.distanceTo(mLastLocation));
                                Toast.makeText(getApplicationContext(), "Result: " + distance_bw_one_and_one + " m", Toast.LENGTH_LONG).show();
                                Log.d("verified", "Distance using distanceBetween(): " + distance_bw_one_and_one + " m");

                                if (distance_bw_one_and_one <= range) {

                                    //
                                    //new FetchURL(MapsActivity.this).execute(getURL(mLastLocation,location,"driving"),"driving");

                                    mMap.addMarker(new MarkerOptions().position(location)

                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_parking_48_nearest))
                                            .title(cordinateLtLg.getId() + " - "+ cordinateLtLg.getDescription())
                                            .snippet(new StringBuilder("Distance: ").append(Math.round(distance_bw_one_and_one)).append(" m").toString()));
                                    // .snippet(cordinateLtLg.getId()));

                                    Toast.makeText(getApplicationContext(), "Result: " + distance_bw_one_and_one + " m", Toast.LENGTH_LONG).show();

                                    Log.d("verified", "Distance Parnell: " + distance_bw_one_and_one + " m"); //<-- CONVERT FROM METERS TO M
                                }

                                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(@NonNull Marker marker) {


                                        Intent info = new Intent(getApplicationContext(), CarParkingProductDetailActivity.class);

                                        //for (int i = 0; i < cordinateLtLgList.size(); i++) {
                                        Toast.makeText(getApplicationContext(),marker.getTitle() + marker.getSnippet(), Toast.LENGTH_LONG).show();

                                        info.putExtra("productId", marker.getTitle().substring(0,2));
                                        //String.valueOf(cordinateLtLg.getId()));

                                        Log.d("IDcarpark" + "", marker.getTitle().substring(0,2));
                                        startActivity(info);

                                    }
                                });

                            }

                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Request_User_Location_Code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                    }
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    //this method to load the kml file on the map is not need right for this project
    //just leaving it here in case firebase crashes
    public void addLayer() {

        try {
            KmlLayer layer = new KmlLayer(mMap, R.raw.dublinpark, getApplicationContext());
            layer.addLayerToMap();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}






