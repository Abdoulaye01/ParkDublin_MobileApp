//package com.example.abdoulayekaloga.finalyear.Payment;
//
//import android.app.RemoteInput;
//import android.content.BroadcastReceiver;
//
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//
//import com.example.abdoulayekaloga.finalyear.Payment.CardDetailActivity;
//
//public class NotificationReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
//        //getting the remote input bundle from intent
//        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
//
//        //if there is some input
//        if (remoteInput != null) {
//
//            //getting the input value
//            CharSequence name = remoteInput.getCharSequence(CardDetailActivity.NOTIFICATION_REPLY);
//
//            //updating the notification with the input value
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CardDetailActivity.CHANNNEL_ID)
//                    .setSmallIcon(android.R.drawable.ic_menu_info_details)
//                    .setContentTitle("Hey Thanks, " + name);
//            NotificationManager notificationManager = (NotificationManager) context.
//                    getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(CardDetailActivity.NOTIFICATION_ID, mBuilder.build());
//        }
//
//        //if help button is clicked
//        if (intent.getIntExtra(CardDetailActivity.KEY_INTENT_HELP, -1) == CardDetailActivity.REQUEST_CODE_HELP) {
//            Toast.makeText(context, "You Clicked Help", Toast.LENGTH_LONG).show();
//        }
//
//        //if more button is clicked
//        if (intent.getIntExtra(CardDetailActivity.KEY_INTENT_MORE, -1) == CardDetailActivity.REQUEST_CODE_MORE) {
//            Toast.makeText(context, "You Clicked More", Toast.LENGTH_LONG).show();
//        }
//    }
//}