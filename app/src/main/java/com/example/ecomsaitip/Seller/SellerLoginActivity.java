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

public class SellerLoginActivity extends AppCompatActivity
{

    private Button loginSellerBtn;
    private EditText emailInput,passwordInput;
    private ProgressDialog loadingbar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login2);

        emailInput = findViewById(R.id.seller_login_email);
        passwordInput = findViewById(R.id.seller_login_password);
        loginSellerBtn = findViewById(R.id.seller_Login_btn);
        mAuth = FirebaseAuth.getInstance();


        loadingbar = new ProgressDialog(this);

        loginSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginSeller();

            }
        });

    }

    private void loginSeller()
    {
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

        if ( !email.equals("")&&!password.equals(""))
        {

            loadingbar.setTitle("Seller Account Login");
            loadingbar.setMessage("Please wait , while we are checking the credentials.");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();


            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                loadingbar.dismiss();
                                Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                        }
                    });
        }
        else
            {
                Toast.makeText(this,"Please complete the Login form.",Toast.LENGTH_SHORT).show();

        }
    }
}
