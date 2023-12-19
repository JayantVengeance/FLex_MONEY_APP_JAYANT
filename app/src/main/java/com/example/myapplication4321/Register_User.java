package com.example.myapplication4321;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Register_User extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fstoreCW;
    TextView goToLogin;
    ProgressBar progressBar;
    Button goToPayment;
    Button registerBtn;
    EditText mFullName, mEmail, mPassword, mPhone, mAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        fstoreCW=FirebaseFirestore.getInstance();
        mFullName= findViewById(R.id.UserName);
        mEmail=findViewById(R.id.companyloginEmailAdress);
        mPassword=findViewById(R.id.companyloginPassword);
        mPhone=findViewById(R.id.companyPhone);
        mAge = findViewById(R.id.UserAge);
        goToLogin=findViewById(R.id.goFromLoginToRegister);
        goToPayment=findViewById(R.id.addUser);
        fAuth=FirebaseAuth.getInstance();
        registerBtn = findViewById(R.id.addUser);
        progressBar=findViewById(R.id.progressBar);

        Intent intent = getIntent();
        int slot = 0;
        if (intent != null) {
          slot   = intent.getIntExtra("Slot_ID", 1);


        }
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            }
        });
        goToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Payment_Gateway.class));
            }
        });
        int finalSlot = slot;
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String fullName=mFullName.getText().toString();
                String phone=mPhone.getText().toString();
                String age = mAge.getText().toString();
                int intage = Integer.parseInt(age);
                final String[] userId = new String[1];
                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required");
                    return;
                }
                if(password.length()<6)
                {
                    mPassword.setError("Password must be greater or equal to length 6.");
                    return;
                }
                if(intage<18||intage>65)
                {
                    Toast.makeText(Register_User.this, "Age is "+intage, Toast.LENGTH_SHORT).show();
                    mAge.setError("Age should be between 18 and 65");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Registering the USER
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
                            String registeringMonth = dateFormat.format(calendar.getTime());

                            // Add registering_month to the user data

                            userId[0] =fAuth.getCurrentUser().getUid();
                            Toast.makeText(Register_User.this, "User Added Successfully!!! please complete payment", Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference=fstoreCW.collection("User").document(userId[0]);
                            HashMap<String, Object> user =new HashMap<>();
                            user.put("UserName", fullName);
                            user.put("Email", email);
                            user.put("Phone", phone);
                            user.put("Age", age);
                            user.put("registering_month", registeringMonth);
                            if(finalSlot ==1)
                            user.put("Type_of_slot","6-7AM");
                            else if(finalSlot == 2)
                            user.put("Type_of_slot","7-8AM");
                            else if(finalSlot ==3)
                                user.put("Type_of_slot","8-9AM");
                            else
                                user.put("Type_of_slot","5-6PM");

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("tag"," user created");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("failure","failed");
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), Payment_Gateway.class));
                            //Task is completed and now user is redirected to his dashBoard
                        }
                        else
                        {
                            Toast.makeText(Register_User.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }
}