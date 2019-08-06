package com.example.ecomsaitip.Buyers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomsaitip.Prevalent.Prevalent;
import com.example.ecomsaitip.R;
import com.example.ecomsaitip.Seller.SellerProductCategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity
{

    private String check = "";
    private TextView pageTitle,titleQuestions;
    private EditText phoneNumber,question1,question2;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        check = getIntent().getStringExtra("check");


        pageTitle = findViewById(R.id.page_title);
        titleQuestions = findViewById(R.id.title_questions);
        phoneNumber = findViewById(R.id.find_question_phone);
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        verifyButton = findViewById(R.id.verify_btn);


    }

    @Override
    protected void onStart()
    {
        super.onStart();


        phoneNumber.setVisibility(View.GONE);

        if (check.equals("settings"))
        {
            pageTitle.setText("Set Questions");
            titleQuestions.setText("Please set Answer the Following Security Questions?");
            verifyButton.setText("Set");

            displayPreviousAnswers();


            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                        setAnswers();

                }
            });

        }
        else if (check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);

            verifyButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    verifyUser();
                }
            });

        }

    }

    private  void setAnswers()
    {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if (question1.equals("") && question2.equals(""))
        {
            Toast.makeText(ResetPasswordActivity.this,"Please answer both questions.",Toast.LENGTH_SHORT).show();

        }
        else
        {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Users")
                    .child(Prevalent.currentonlineusers.getPhone());

            HashMap<String,Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1",answer1);
            userdataMap.put("answer2",answer2);

            ref.child("Security Questions").updateChildren(userdataMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ResetPasswordActivity.this,"you have answer security questions successfully",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);

                                startActivity(intent);

                            }

                        }
                    });
        }
    }


    private void displayPreviousAnswers()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child("Users")
                .child(Prevalent.currentonlineusers.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String ans1 = dataSnapshot.child("answer1").getValue().toString();
                    String ans2 = dataSnapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void verifyUser()
    {
         final String phone = phoneNumber.getText().toString();
         final String answer1 = question1.getText().toString().toLowerCase();
         final String answer2 = question2.getText().toString().toLowerCase();

         if (!phone.equals("")&& !answer1.equals("")&& !answer2.equals(""))
         {
             final DatabaseReference ref = FirebaseDatabase.getInstance()
                     .getReference()
                     .child("Users")
                     .child(phone);

             ref.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot)
                 {
                     if (dataSnapshot.exists())
                     {
                         String mPhone = dataSnapshot.child("phone").getValue().toString();

                         if (dataSnapshot.hasChild("Security Questions"))
                         {
                             String ans1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                             String ans2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                             if (!ans1.equals(answer1))
                             {
                                 Toast.makeText(ResetPasswordActivity.this,"your 1st answer is wrong.",Toast.LENGTH_SHORT).show();
                             }
                             else if (!ans2.equals(answer2))
                             {
                                 Toast.makeText(ResetPasswordActivity.this,"your 2nd answer is wrong.",Toast.LENGTH_SHORT).show();

                             }
                             else
                             {
                                 AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                 builder.setTitle("New Password");

                                 final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                 newPassword.setHint("Write Password here...");
                                 builder.setView(newPassword);

                                 builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which)
                                     {
                                         if (!newPassword.getText().toString().equals(""))
                                         {
                                             ref.child("password").setValue(newPassword.getText().toString())
                                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task) {
                                                             if (task.isSuccessful())
                                                             {
                                                                 Toast.makeText(ResetPasswordActivity.this,"Password Changed Successfully.",Toast.LENGTH_SHORT).show();

                                                                 Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                                 startActivity(intent);
                                                             }
                                                         }
                                                     })  ;
                                         }
                                     }
                                 });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i)
                                        {
                                            dialog.cancel();
                                        }
                                    });
                                 builder.show();


                             }

                         }

                         else
                         {
                             Toast.makeText(ResetPasswordActivity.this,"ัyou have not set the security questions.",Toast.LENGTH_SHORT).show();

                         }
                     }
                     else
                     {
                         Toast.makeText(ResetPasswordActivity.this,"This phone number not exist.",Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError)
                 {

                 }
             });
         }
         else
         {
             Toast.makeText(this,"please complete the form",Toast.LENGTH_SHORT).show();
         }

    }
}






