package com.example.myapplication4321;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class Countdown_Timer extends AppCompatActivity {

    private TextView tvCountdown;
    private TextView tvUserName;
    String selectedTimeSlot = "";
    HashSet<String> months = new HashSet();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        tvCountdown = findViewById(R.id.tvCountdown);
        tvUserName = findViewById(R.id.textView2);
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        // Retrieve the selected time slot from the intent

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            // User is logged in
            String currentUserId = fAuth.getCurrentUser().getUid();

            // Fetch user data from Firestore
            getUserDataFromFirestore(currentUserId);
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserDataFromFirestore(String userId)
    {

        Button changeSlot = findViewById(R.id.button5);
        FirebaseFirestore fstoreCW = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fstoreCW.collection("User").document(userId);


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                if (documentSnapshot.exists()) {
                    // User data found

                    String userName = documentSnapshot.getString("UserName");
                    String timeSlot = documentSnapshot.getString("Type_of_slot");
                    String month = documentSnapshot.getString("registering_month");
                    changeSlot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
                            String curr_month = dateFormat.format(calendar.getTime());
                            System.out.println("curr_month "+curr_month+"month "+month);
                            if(curr_month.equals(month))
                            {
                                Toast.makeText(Countdown_Timer.this, "You can change slot next month!!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                startActivity(new Intent(getApplicationContext(),ChangeSlot.class));
                            }
                        }
                    });
                    //System.out.println("timeslot is "+timeSlot+"  "+selectedTimeSlot);
                    if (timeSlot.equals("6-7AM"))
                    {
                        selectedTimeSlot= "06:00:00";
                    } else if (timeSlot.equals("7-8AM")) {
                        selectedTimeSlot = "07:00:00";
                    } else if (timeSlot.equals("8-9AM")) {
                        selectedTimeSlot = "08:00:00";
                    } else {
                        selectedTimeSlot= "17:00:00";
                    }
                    // Set user name and time slot on the UI
                    tvUserName.setText("Hi, " + userName);

                }

                // Get the target time based on the selected time slot
                long targetTimeMillis = calculateTargetTime(selectedTimeSlot);
                System.out.println("Jayant"  +selectedTimeSlot);
                // Calculate the remaining time
                long currentTimeMillis = System.currentTimeMillis();
                long countdownMillis = targetTimeMillis - currentTimeMillis;

                // Create and start the countdown timer
                new CountDownTimer(countdownMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Convert milliseconds to hours, minutes, and seconds
                        long seconds = millisUntilFinished / 1000;
                        long hours = seconds / 3600;
                        long minutes = (seconds % 3600) / 60;
                        seconds = seconds % 60;

                        // Update the TextView with the remaining time
                        String countdownText = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                        tvCountdown.setText(countdownText);
                    }

                    @Override
                    public void onFinish() {
                        // Handle what happens when the countdown finishes
                        tvCountdown.setText("00:00:00");
                    }

                }.start();



            }
            });

    }

    private long calculateTargetTime(String selectedTimeSlot){

        SimpleDateFormat sdfCurrentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTimeString = sdfCurrentTime.format(System.currentTimeMillis());


        SimpleDateFormat sdfInput = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        try {
            Date currentDate = sdfCurrentTime.parse(currentTimeString);
            Date selectedDate = sdfInput.parse(selectedTimeSlot);


            long timeDifferenceMillis = selectedDate.getTime() - currentDate.getTime();

            // If the time difference is negative, it means the selected time is on the next day
            if (timeDifferenceMillis < 0) {
                // Add one day to the time difference
                timeDifferenceMillis += 24 * 60 * 60 * 1000; // 24 hours in milliseconds
            }

            return System.currentTimeMillis() + timeDifferenceMillis;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if parsing fails
    }

}
