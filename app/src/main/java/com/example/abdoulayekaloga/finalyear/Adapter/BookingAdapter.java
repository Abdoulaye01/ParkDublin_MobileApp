package com.example.abdoulayekaloga.finalyear.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdoulayekaloga.finalyear.Util.Product;
import com.example.abdoulayekaloga.finalyear.firebaseClasses.CarParkingProductDetailActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.Booking;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter <BookingAdapter.MyViewHolder>
{

    private final Context context;
    private final List<Booking>bookingList;
    public  ArrayList<Booking> mStringFilterList;

    public BookingAdapter(Context context,List<Booking>bookingList){
       this.context = context;
       this.bookingList =bookingList;
       this.mStringFilterList = new ArrayList<>(bookingList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.inflate_activity_booking, viewGroup, false);
        //noinspection UnnecessaryLocalVariable
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        //final Booking booking = bookingList.get(i);
        myViewHolder.PlaceNameTxt.setText(bookingList.get(i).getName());
        Picasso.get().load(bookingList.get(i).getImage()).into(myViewHolder.imgPlace);

        myViewHolder.shareAppbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Check this link to make reservations"
                        + bookingList.get(i).getName() +
                        "\nHere is the link to the website : " + bookingList.get(i).getPlaceGuide());
                intent.setType("text/plain");
                context.startActivity(Intent.createChooser(intent, "Send To "));
            }
        });

        myViewHolder.urlLinkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(String.valueOf(bookingList.get(i).getPlaceGuide())));
                context.startActivity(intent);
            }
        });

        myViewHolder.bookWithUsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                Context context = v.getContext();

             //getting the selected value
             String value = bookingList.get(i).getId();

            // creating an intent
                Intent intent = new Intent(context, CarParkingProductDetailActivity.class);

                //putting value and id to intent
                intent.putExtra("productId",value);

                //starting the activity  wiht intent
                context.startActivity(intent);

            }
        });
}

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgPlace;
        final TextView PlaceNameTxt;
        final Button shareAppbtn;
        final Button urlLinkbtn;
        final Button bookWithUsbtn;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPlace = itemView.findViewById(R.id.imgPlace);
            PlaceNameTxt = itemView.findViewById(R.id.PlaceNameTxt);
            shareAppbtn = itemView.findViewById(R.id.shareAppbtn);
            urlLinkbtn = itemView.findViewById(R.id.urlLinkbtn);
            bookWithUsbtn = itemView.findViewById(R.id.bookWithUsbtn);

        }

    }

    public Filter getFilter()
    {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Booking> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mStringFilterList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Booking item : mStringFilterList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            bookingList.clear();
            bookingList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
