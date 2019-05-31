package com.example.abdoulayekaloga.finalyear.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.OpeningHoursInformation;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.CarParkingProductDetailActivity;

import java.util.List;


public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.MyViewHolder> {


    private final Context mCtx;
    Button btnBooking;
    private final List<OpeningHoursInformation> openingHours;

    public HourlyAdapter(Context mCtx, List <OpeningHoursInformation> openingHours) {
        this.mCtx = mCtx;
        this.openingHours = openingHours;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.inflate_carparking_information, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        //final OpeningHoursInformation opening = openingHours.get(i);
        myViewHolder.txt_address.setText(openingHours.get(i).getAddress());
        myViewHolder.txt_phone.setText(openingHours.get(i).getPhone());
        myViewHolder.txt_dayparking.setText(openingHours.get(i).getDayParking());
        myViewHolder.txt_mon_wed.setText(openingHours.get(i).getMon_wed());
        myViewHolder.txt_thur.setText(openingHours.get(i).getThur());
        myViewHolder.txt_frid.setText(openingHours.get(i).getFri());
        myViewHolder.txt_sat.setText(openingHours.get(i).getSat());
        myViewHolder.txt_sun.setText(openingHours.get(i).getSun());


    }



    @Override
    public int getItemCount() {
        return openingHours.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        //ImageView productImageView;
        final TextView txt_address;
        final TextView txt_phone;
        final TextView txt_dayparking;
        final TextView txt_mon_wed;
        final TextView txt_thur;
        final TextView txt_frid;
        final TextView txt_sat;
        final TextView txt_sun;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_address = itemView.findViewById(R.id.txt_address);
            txt_phone = itemView.findViewById(R.id.txt_phone);
            txt_dayparking = itemView.findViewById(R.id.txt_dayparking);
            txt_mon_wed = itemView.findViewById(R.id.txt_mon_wed);
            txt_thur = itemView.findViewById(R.id.txt_thur);
            txt_frid = itemView.findViewById(R.id.txt_frid);
            txt_sat = itemView.findViewById(R.id.txt_sat);
            txt_sun = itemView.findViewById(R.id.txt_sun);

        }
    }
}

