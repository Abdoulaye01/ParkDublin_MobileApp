package com.example.abdoulayekaloga.finalyear.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.RecentBooking;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecentBookingAdapter extends RecyclerView.Adapter<RecentBookingAdapter.MyViewHolder> {


    private final Context mCtx;
    private final List <RecentBooking> productDetail;

    public RecentBookingAdapter(Context mCtx, List <RecentBooking> productDetail) {
        this.mCtx = mCtx;
        this.productDetail = productDetail;
    }

    @NonNull
    @Override
    public RecentBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.inflate_recentpayment, parent, false);
        //noinspection UnnecessaryLocalVariable
        RecentBookingAdapter.MyViewHolder myViewHolder = new RecentBookingAdapter.MyViewHolder(view);
        return myViewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull RecentBookingAdapter.MyViewHolder myViewHolder, final int i) {
        final RecentBooking prd = productDetail.get(i);
        myViewHolder.productTitle.setText(prd.getCarNumberPlate());
        myViewHolder.productShortDesc.setText(prd.getTypeofProduct());
        myViewHolder.carparkName.setText(prd.getCarparkingName());
        myViewHolder.productPrice.setText(prd.getPrice());

    }

    @Override
    public int getItemCount() {
        return productDetail.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView productImageView;
        TextView productTitle;
        final TextView productShortDesc;
        final TextView productPrice;
        final TextView carparkName;



        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageView = itemView.findViewById(R.id.productImageView);
            productTitle = itemView.findViewById(R.id.productTitle);
            productTitle = itemView.findViewById(R.id.productTitle);
            productShortDesc = itemView.findViewById(R.id.productShortDesc);
            productPrice = itemView.findViewById(R.id.productPrice);
            carparkName = itemView.findViewById(R.id.carparkName);


        }
    }
}
