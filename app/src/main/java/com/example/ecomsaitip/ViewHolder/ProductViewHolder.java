package com.example.ecomsaitip.ViewHolder;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecomsaitip.Interface.ItemClickListener;
import com.example.ecomsaitip.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription,txtProductPrice;
    public ImageView imageView;
    public  ItemClickListener listener;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductDescription = itemView.findViewById(R.id.product_description);
        txtProductPrice = itemView.findViewById(R.id.product_price);


    }

    public void setItemClickLister(ItemClickListener listner)
    {
        this.listener = listner;

    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(),false);
    }
}
