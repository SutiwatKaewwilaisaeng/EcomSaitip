package com.example.ecomsaitip.Seller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ecomsaitip.R;

public class SellerProductCategoryActivity extends AppCompatActivity {

    private ImageView laptop,books,sweathers,watch;
    private ImageView maggi,mama,flour,rosdee;
    private ImageView chiness_noodle,sugar,fishsauce,orange;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_category);


//        LogoutBtn = findViewById(R.id.admin_logout_btn);
//        CheckOrdersBtn = findViewById(R.id.check_orders_btn);
//        maintainProductsBtn = findViewById(R.id.maintain_btn);
//
//        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(SellerProductCategoryActivity.this, HomeActivity.class);
//                intent.putExtra("Admin","Admin");
//                startActivity(intent);
//
//            }
//        });
//
//        LogoutBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(SellerProductCategoryActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        CheckOrdersBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(SellerProductCategoryActivity.this, AdminNewOrdersActivity.class);
//                startActivity(intent);
//
//            }
//        });

        //category 1
        laptop = findViewById(R.id.laptop);
        books = findViewById(R.id.books);
        sweathers = findViewById(R.id.sweathers);
        watch = findViewById(R.id.watch);

        //category 2
        maggi = findViewById(R.id.maggi);
        mama = findViewById(R.id.mama);
        flour = findViewById(R.id.flour);
        rosdee = findViewById(R.id.rosdee);

        //category 3
        chiness_noodle = findViewById(R.id.chiness_noodle);
        sugar = findViewById(R.id.sugar);
        fishsauce = findViewById(R.id.fishsauce);
        orange = findViewById(R.id.orange);



        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","laptop");
                startActivity(intent);
            }
        });

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","watch");
                startActivity(intent);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","books");
                startActivity(intent);
            }
        });

        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","sweathers");
                startActivity(intent);
            }
        });

        maggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","maggi");
                startActivity(intent);
            }
        });

        mama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","mama");
                startActivity(intent);
            }
        });

        flour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","flour");
                startActivity(intent);
            }
        });


        rosdee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","rosdee");
                startActivity(intent);
            }
        });

        chiness_noodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","chiness_noodle");
                startActivity(intent);
            }
        });

        sugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","sugar");
                startActivity(intent);
            }
        });

        fishsauce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","fishsauce");
                startActivity(intent);
            }
        });

        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category","orange");
                startActivity(intent);
            }
        });

    }
}
