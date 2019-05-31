package com.example.abdoulayekaloga.finalyear.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.LiveCarparkFeed;

import java.util.ArrayList;


public class LiveCarParkFeedAdapter extends ArrayAdapter<LiveCarparkFeed> {
    public LiveCarParkFeedAdapter(@NonNull Context context, @NonNull ArrayList <LiveCarparkFeed> liveCarparkFeeds) {
        super(context, 0, liveCarparkFeeds);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_live_parking, parent, false);
        }

        // Get the data item for this position
        LiveCarparkFeed liveCarparkFeed = getItem(position);

        // Lookup view for data population
        TextView name = convertView.findViewById(R.id.idName);
        TextView space = convertView.findViewById(R.id.idSpaces);
        //TextView tv = (TextView) convertView.findViewById(R.id.tvHometown);
        // Populate the data into the template view using the data object
        name.setText( liveCarparkFeed.getName() + " - ");
        space.setText("Free Spaces:  " + liveCarparkFeed.getSpaces());
        //tv.setText( "Time: " + user.getTimestamp());

        // Return the completed view to render on screen
        return convertView;
    }


}


