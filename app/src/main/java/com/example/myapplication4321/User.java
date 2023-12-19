package com.example.myapplication4321;

public class User
{
    public String fullName, email, age, selected_slot, registering_month;
    public User()
    {

    }

    public User(String fullName, String email, String age, String selected_slot, String registering_month)
    {
        this.fullName=fullName;
        this.age=age;
        this.email=email;
        this.selected_slot = selected_slot;
        this.registering_month = registering_month;
    }

}
