package com.example.myapplication4321;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    Button six7btnA;
    Button seven8btnA;
    Button eight9btnA;
    Button five6btnP;
    TextView gotoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        six7btnA=findViewById(R.id.button1);
        seven8btnA = findViewById(R.id.button2);
        eight9btnA = findViewById(R.id.button3);
        five6btnP = findViewById(R.id.button4);
        gotoLogin = findViewById(R.id.gotoLogin);
        six7btnA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int slotID= 1; // Replace with your actual integer value
                Intent intent = new Intent(getApplicationContext(), Register_User.class);
                intent.putExtra("Slot_ID", slotID);
                startActivity(intent);


            }
        });
        seven8btnA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int slotID= 2; // Replace with your actual integer value
                Intent intent = new Intent(getApplicationContext(), Register_User.class);
                intent.putExtra("Slot_ID", slotID);
                startActivity(intent);
            }
        });
        eight9btnA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int slotID= 3; // Replace with your actual integer value
                Intent intent = new Intent(getApplicationContext(), Register_User.class);
                intent.putExtra("Slot_ID", slotID);
                startActivity(intent);
            }
        });
        five6btnP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                int slotID= 4; // Replace with your actual integer value
                Intent intent = new Intent(getApplicationContext(), Register_User.class);
                intent.putExtra("Slot_ID", slotID);
                startActivity(intent);
            }
        });
        gotoLogin.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {

            // Replace with your actual integer value
            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);
        }
    });
    }
}