package com.example.abdoulayekaloga.finalyear.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdoulayekaloga.finalyear.Payment.CardDetailActivity;
//import com.example.abdoulayekaloga.finalyear.Model.CardFormActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.ProductDetail;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.MyViewHolder> {


    private final Context mCtx;
    private final List <ProductDetail> productDetail;

    public ProductDetailAdapter(Context mCtx, List <ProductDetail> productDetail) {
        this.mCtx = mCtx;
        this.productDetail = productDetail;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.inflate_car_parking_detail, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
       final ProductDetail prd = productDetail.get(i);
        myViewHolder.productTitle.setText(prd.getTitle());
        myViewHolder.productShortDesc.setText(prd.getDescription());
        //myViewHolder.productPrice.setText(String.valueOf(prd.getPrice()));

//        Locale locale = Locale.UK;
//        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        DecimalFormat decimalFormat = new DecimalFormat("€ ##,##");
        String currencyText = decimalFormat.format(Double.valueOf(prd.getDisplayprice()));
        myViewHolder.productPrice.setText(String.valueOf(currencyText));
       // myViewHolder.productPrice.setText(prd.getPrice());

        Picasso.get().load(productDetail.get(i).getImage()).into(myViewHolder.productImageView);


        //SENDING DATA FROM PRODUCTDETAILAPTER TO  CARDACTIVITY

        myViewHolder.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                //final ProductDetail prd = productDetail.get(i);
                Context context = v.getContext();
                String value = productDetail.get(i).getId();
                Intent intent = new Intent(context, CardDetailActivity.class);

                intent.putExtra("id", "" + value);
                context.startActivity(intent);

                Toast.makeText(context,"SENDING DATA FROM PRODUCTDETAILAPTER TO  CARDACTIVITY", Toast.LENGTH_LONG).show();
            }
        });
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
        final Button btnBooking;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageView = itemView.findViewById(R.id.productImageView);
            productTitle = itemView.findViewById(R.id.productTitle);
            productTitle = itemView.findViewById(R.id.productTitle);
            productShortDesc = itemView.findViewById(R.id.productShortDesc);
            productPrice = itemView.findViewById(R.id.productPrice);
            btnBooking = itemView.findViewById(R.id.btnBooking);


        }
    }
}
