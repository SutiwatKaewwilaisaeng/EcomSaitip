package com.example.ecomsaitip.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomsaitip.Admin.AdminHomeActivity;
import com.example.ecomsaitip.Seller.SellerProductCategoryActivity;
import com.example.ecomsaitip.Model.Users;
import com.example.ecomsaitip.Prevalent.Prevalent;
import com.example.ecomsaitip.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog loadingbar;
    private EditText InputName,InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private  String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;
    private TextView AdminLink, NotAdminLink,ForgetPasswordLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginButton = findViewById(R.id.login_btn);

        InputPhoneNumber = findViewById(R.id.login_phone_number_input);
        InputPassword = findViewById(R.id.login_password_input);
        loadingbar = new ProgressDialog(this);
        AdminLink = findViewById(R.id.admin_panel_link);
        NotAdminLink = findViewById(R.id.not_admin_panel_link);
        ForgetPasswordLink = findViewById(R.id.forget_password_link);



        chkBoxRememberMe = findViewById(R.id.remember_me_chkb);

        ForgetPasswordLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
            }
        });

        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";



            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login ");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void LoginUser() {

        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
     if (TextUtils.isEmpty(phone)) {
        Toast.makeText(this, "Please write your phone...", Toast.LENGTH_SHORT).show();

        }
     else if(TextUtils.isEmpty(password)) {
        Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();

         }
     else {
         loadingbar.setTitle("Login Account");
         loadingbar.setMessage("Please wait , while we are checking the credentials.");
         loadingbar.setCanceledOnTouchOutside(false);
         loadingbar.show();

         AllowAccessToAccount(phone,password);

         }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        if (chkBoxRememberMe.isChecked()){

            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);

        }


       final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)){

                        if (usersData.getPassword().equals(password)){

                            if (parentDbName.equals("Admins")){
                                Toast.makeText(LoginActivity.this,"Welcome Admin , you are logged in Successfully...",Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);

                            }else if(parentDbName.equals("Users")){
                                Toast.makeText(LoginActivity.this,"logged in Successfully...",Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentonlineusers = usersData;
                                startActivity(intent);


                            }
                        }
                        else{
                            loadingbar.dismiss();
                            Toast.makeText(LoginActivity.this,"Password is incorrect.",Toast.LENGTH_SHORT).show();


                        }

                    }

                }else {
                    loadingbar.dismiss();
                    Toast.makeText(LoginActivity.this,"Account with this"+phone+"Number do not exitst",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
