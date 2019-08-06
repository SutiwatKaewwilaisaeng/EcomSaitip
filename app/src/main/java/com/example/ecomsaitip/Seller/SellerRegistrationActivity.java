package com.example.ecomsaitip.Seller;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecomsaitip.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity
{
    private Button sellerLoginBegin;
    private EditText nameInput,phoneInput,emailInput,passwordInput,addressInput;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        sellerLoginBegin =findViewById(R.id.already_have_an_account_btn);

        nameInput = findViewById(R.id.seller_name);
        phoneInput = findViewById(R.id.seller_phone);
        emailInput = findViewById(R.id.seller_email);
        passwordInput = findViewById(R.id.seller_password);
        addressInput = findViewById(R.id.seller_address);
        registerButton = findViewById(R.id.seller_regis_btn);

        loadingbar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        sellerLoginBegin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerSeller();

            }
        });


    }

    private void registerSeller()
    {
        final String name = nameInput.getText().toString();
        final String phone = phoneInput.getText().toString();
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();
        final String address = addressInput.getText().toString();

        if (!name.equals("") && !phone.equals("")&& !email.equals("")&&
                !password.equals("")&& !address.equals(""))
        {
            loadingbar.setTitle("Creating Seller Account");
            loadingbar.setMessage("Please wait , while we are checking the credentials.");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                final DatabaseReference rootRef;
                                rootRef = FirebaseDatabase.getInstance().getReference();

                                String sid = mAuth.getCurrentUser().getUid();
                                HashMap<String,Object>sellerMap = new HashMap<>();
                                sellerMap.put("sid",sid);
                                sellerMap.put("phone",phone);
                                sellerMap.put("email",email);
                                sellerMap.put("password",password);
                                sellerMap.put("address",address);
                                sellerMap.put("name",name);

                                rootRef.child("Sellers").child(sid).updateChildren(sellerMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                loadingbar.dismiss();
                                                Toast.makeText(SellerRegistrationActivity.this,"You are registered sucessfully",Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(SellerRegistrationActivity.this, SellerHomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this,"Please complete the registeration form.",Toast.LENGTH_SHORT).show();

        }

    }
}
