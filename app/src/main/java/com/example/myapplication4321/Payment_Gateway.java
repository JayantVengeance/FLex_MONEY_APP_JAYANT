package com.example.myapplication4321;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Payment_Gateway extends AppCompatActivity {

    Button paymentPass;
    Button paymentFail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        paymentPass = findViewById(R.id.paymentSuccessful);
        paymentFail = findViewById(R.id.paymentUnsuccessful);

        paymentPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getApplicationContext(), Countdown_Timer.class);
                startActivity(intent);
            }
        });
        paymentFail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(Payment_Gateway.this, "Please Complete the payment!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}