package com.example.ecomsaitip.Admin;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecomsaitip.Interface.ItemClickListener;
import com.example.ecomsaitip.Model.Products;
import com.example.ecomsaitip.R;
import com.example.ecomsaitip.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminCheckNewProductActivity extends AppCompatActivity
{
        private RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        private DatabaseReference unverifiedProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_new_product);

        unverifiedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView = findViewById(R.id.admin_products_checklist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products>options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedProductsRef.orderByChild("productState").equalTo("Not Approved"),Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder>adapter
                = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, final int position, @NonNull final Products model)
            {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductPrice.setText("Price = "+ model.getPrice()+"$");
                Picasso.get().load(model.getImage()).into(holder.imageView);



               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view)
                   {
                       final String productID = model.getPid();

                       CharSequence options[] = new CharSequence[]
                               {
                                       "Yes",
                                       "No"

                               };

                       AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckNewProductActivity.this);
                       builder.setTitle("Do you want to Approved this Product. Are you Sure?");
                       builder.setItems(options, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which)
                           {
                               if (position == 0)
                               {

                                   ChangeProductState(productID);
                               }
                               if (position == 1)
                               {


                               }
                           }
                       });
                       builder.show();

                   }
               });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void ChangeProductState(String productID)
    {
        unverifiedProductsRef.child(productID)
                .child("productState")
                .setValue("Approved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(AdminCheckNewProductActivity.this,"That item has been approved, and it is now available for sale from the seller.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
