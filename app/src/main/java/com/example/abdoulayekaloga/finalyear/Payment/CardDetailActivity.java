package com.example.abdoulayekaloga.finalyear.Payment;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.AsyncTask;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.ProductDetail;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.RecentBookingActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.app.NotificationChannel;
import android.graphics.Color;


public class CardDetailActivity extends AppCompatActivity {

    private Button btnext;
    private List<ProductDetail> paymentDetails;
    private TextView getAmount;
    private TextView getTitle;
    private TextView getName;
    private EditText carPlate;
    private FirebaseAuth auth;

    private static final int REQUEST_CODE = 1234;
    //In emulator ,localhost = 10.0.2.2
    private final String API_GET_TOKEN = "http://localhost:8888/braintree/main.php";
    private final String API_CHECK__OUT = "http://localhost:8888/braintree/checkout.php";

    private String token;
    private String amount;
    private String currencyText;
    private String theamount;
    private HashMap<String, String> paramHash;
    private boolean amountSuccessful = false;

    public static final String CHANNNEL_ID = "ParkME";
    private static final String CHANNEL_NAME = "ParkME";
    private static final String CHANNEL_DESC = "This is ParkME notification";
    public static final int NOTIFICATION_ID = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        btnext = findViewById(R.id.btnextActivity);
        getAmount = findViewById(R.id.getAmount);
        getTitle = findViewById(R.id.getTitle);
        getName = findViewById(R.id.getName);
        carPlate = findViewById(R.id.carPlate);

        auth = FirebaseAuth.getInstance();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNNEL_ID, CHANNEL_NAME, importance);
            mChannel.setDescription(CHANNEL_DESC);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        paymentDetails = new ArrayList<>();
        Intent intent = getIntent();
        final String value = intent.getStringExtra("id");
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
        Log.d("CardDetailActivity", "" + value);


        final DatabaseReference dbProducts = FirebaseDatabase.getInstance().getReference("carparkingfare").child(value);
        dbProducts.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        ProductDetail p = productSnapshot.getValue(ProductDetail.class);
                        getTitle.setText(p.getTitle());
                        getName.setText(p.getBranch());
                        // getAmount.setText(String.valueOf(p.getPrice()));
                        // st = st + p.getPrice() + "\n";


                        DecimalFormat decimalFormat = new DecimalFormat("€##,##");
                        currencyText = decimalFormat.format(Double.valueOf(p.getDisplayprice()));
                        getAmount.setText(String.valueOf(currencyText));

                        Toast.makeText(CardDetailActivity.this, currencyText, Toast.LENGTH_LONG).show();

                        theamount = String.valueOf(p.getPrice());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        btnext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = getName.getText().toString();
                final String price = getAmount.getText().toString();
                final String title = getTitle.getText().toString();
                final String plateNber = carPlate.getText().toString();

                //Get Firebase auth instance
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null ) {


                    Matcher m = Pattern.compile(("^(?![0-9]{7})[0-9a-zA-Z]{7}$")).matcher(plateNber);
                    if (m.find() && m != null) {
                        onBraintreeSubmit();
                        Toast.makeText(CardDetailActivity.this, "car number is valide", Toast.LENGTH_LONG).show();
//                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("customerwhobought").push();
//                    Map newPost = new HashMap();
//                        //^([A-HK-PRSVWY][A-HJ-PR-Y])\s?([0][2-9]|[1-9][0-9])\s?[A-HJ-PR-Z]{3}$      uknumber plate
//                    Matcher m = Pattern.compile(("^(?![0-9]{7})[0-9a-zA-Z]{7}$")).matcher(plateNber);
//                    if (m.find() && m != null) {
//                        Toast.makeText(CardDetailActivity.this, "car number is valide", Toast.LENGTH_LONG).show();
//                        newPost.put("carparkingName", name);
//                        newPost.put("price", price);
//                        newPost.put("typeofProduct", title);
//                        newPost.put("carNumberPlate", plateNber);
//                        newPost.put("uid", currentUser.getUid());
//                        newPost.put("username", currentUser.getEmail());
//                        myRef.setValue(newPost);
//                        onBraintreeSubmit();
//
                    } else {
                        Toast.makeText(CardDetailActivity.this, "car number is not a valid number plate", Toast.LENGTH_LONG).show();
                    }
                }

                if (currentUser == null) {

                    View  view = findViewById(R.id.btnextActivity);
                    Snackbar snackbar = Snackbar.make(view,"Please register first !",Snackbar.LENGTH_INDEFINITE)

                            .setAction("DISMISS", new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    snackbar.show();

                    Toast.makeText(CardDetailActivity.this, "You are not register  !", Toast.LENGTH_LONG).show();
                }

            }

        });

        new HttpRequest().execute();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.d("mylog", "Result: " + stringNonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
                if (!getAmount.getText().toString().isEmpty()) {
                    amount = getAmount.getText().toString();

                    paramHash = new HashMap<>();
                    paramHash.put("amount", theamount);
                    paramHash.put("nonce", stringNonce);
                    sendPaymentDetails();
                    savingCustomerInformation();
                } else
                    Toast.makeText(CardDetailActivity.this, "Not a valid amount.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }
    }

    private void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token.trim());
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);

    }

    private void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(CardDetailActivity.this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_CHECK__OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(@NonNull String response) {
                        if (response.contains("Successful")  ) {
                            //display notification on user phone
                            displayNotification();


                            Toast.makeText(CardDetailActivity.this, "Transaction successful", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(CardDetailActivity.this, "Transaction failed", Toast.LENGTH_LONG).show();
                        Log.d("mylog", "Final Response: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(@NonNull VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }

            @NonNull
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private class HttpRequest extends AsyncTask<String, Void, String> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(CardDetailActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
            progress.setCancelable(false);
            progress.setMessage("We are contacting our servers for token, Please wait");
            progress.setTitle("Getting token");
            progress.show();
        }

        @Override
        protected String doInBackground(String[] params) {
            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {
                    Log.d("mylog", responseBody);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CardDetailActivity.this, "Successfully got token", Toast.LENGTH_SHORT).show();
                            //llHolder.setVisibility(View.VISIBLE);
                        }
                    });
                    token = responseBody;
                }

                @Override
                public void failure(Exception exception) {
                    final Exception ex = exception;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CardDetailActivity.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            return token;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            token = result;
            progress.dismiss();
            Toast.makeText(CardDetailActivity.this, "token: " + token, Toast.LENGTH_LONG).show();

        }
    }


    public void savingCustomerInformation(){

        final String name = getName.getText().toString();
        final String price = getAmount.getText().toString();
        final String title = getTitle.getText().toString();
        final String plateNber = carPlate.getText().toString();
        boolean sendNotification = false;
        //Get Firebase auth instance
        FirebaseUser currentUser = auth.getCurrentUser();
       //if (currentUser != null) {

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("customerwhobought").push();
            Map newPost = new HashMap();
            //^([A-HK-PRSVWY][A-HJ-PR-Y])\s?([0][2-9]|[1-9][0-9])\s?[A-HJ-PR-Z]{3}$      uknumber plate

                newPost.put("carparkingName", name);
                newPost.put("price", price);
                newPost.put("typeofProduct", title);
                newPost.put("carNumberPlate", plateNber);
                newPost.put("uid", currentUser.getUid());
                newPost.put("username", currentUser.getEmail());
                myRef.setValue(newPost);

//            } else {
//                Toast.makeText(CardDetailActivity.this, "car number is not a valid number plate", Toast.LENGTH_LONG).show();
//            }
       // }

//        if (currentUser == null) {
//
//            View  view = findViewById(R.id.btnextActivity);
//            Snackbar snackbar = Snackbar.make(view,"Please register first !",Snackbar.LENGTH_INDEFINITE)
//                    .setAction("DISMISS", new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//            snackbar.show();
//
//            Toast.makeText(CardDetailActivity.this, "You are not register  !", Toast.LENGTH_LONG).show();
//        }

    }

    private void displayNotification() {

        final String name = getName.getText().toString();
        final String price = getAmount.getText().toString();
        final String title = getTitle.getText().toString();

        Intent intent = new Intent(CardDetailActivity.this, RecentBookingActivity.class);
        PendingIntent morePendingIntent =  PendingIntent.getActivity(this,1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        try{
            morePendingIntent.send();
        }catch (PendingIntent.CanceledException e){
            e.printStackTrace();
        }

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManag = (NotificationManager)getApplicationContext().getSystemService(ns);
        notificationManag.cancel(1);

        //Creating the notifiction builder object
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(name)
                .setContentText("Thank you for booking with us " + "\n"  +  " Amount Paid: " + price)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(morePendingIntent)
                .addAction(android.R.drawable.ic_menu_compass, "More", morePendingIntent);

        //finally displaying the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
