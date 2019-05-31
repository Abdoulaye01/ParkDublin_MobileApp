package com.example.abdoulayekaloga.finalyear.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdoulayekaloga.finalyear.firebaseClasses.CarparkingInfoHourActivity;
import com.example.abdoulayekaloga.finalyear.R;
import com.example.abdoulayekaloga.finalyear.Util.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private final Context mCtx;
    private final List<Product> productList;
    public  ArrayList<Product> mStringFilterList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mStringFilterList = new ArrayList<>(productList);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.product_layout,parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);


        holder.textViewTitle.setText(product.getName());

        Picasso.get().load(productList.get(position).getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {

                Context context = v.getContext();
                //getting the selected value
                String value = productList.get(position).getId();

                Intent intent = new Intent(context, CarparkingInfoHourActivity.class);

                intent.putExtra("id",value);
                intent.putExtra("image",productList.get(position).getImage());
                intent.putExtra("image_title",productList.get(position).getName());
                intent.putExtra("description",productList.get(position).getDescription());

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView textViewTitle;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            textViewTitle = itemView.findViewById(R.id.image_title);

        }
    }

    public Filter getFilter()
    {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mStringFilterList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Product item : mStringFilterList) {
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
            productList.clear();
            productList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}