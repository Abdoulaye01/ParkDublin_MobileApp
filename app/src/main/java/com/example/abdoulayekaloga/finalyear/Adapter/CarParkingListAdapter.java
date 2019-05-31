package com.example.abdoulayekaloga.finalyear.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.CarParkingList;

import java.util.ArrayList;

public class CarParkingListAdapter extends RecyclerView.Adapter<CarParkingListAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<CarParkingList>list;

    public CarParkingListAdapter(Context context, ArrayList <CarParkingList> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public CarParkingListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inflate_car_parkinglist,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarParkingListAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.name_text.setText(list.get(i).getName());
        myViewHolder.location_text.setText(list.get(i).getLocation());
        myViewHolder.website_text.setText(list.get(i).getWebsite_link());
        myViewHolder.space_text.setText(list.get(i).getSpaces());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        final TextView name_text;
        final TextView location_text;
        final TextView space_text;
        final TextView website_text;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name_text = itemView.findViewById(R.id.name_text);
            location_text = itemView.findViewById(R.id.location_text);
            space_text = itemView.findViewById(R.id.space_text);
            website_text = itemView.findViewById(R.id.website_text);

        }
    }
}
