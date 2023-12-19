https://drive.google.com/file/d/185ZuENt5r3t75AyzK6Fto78W_FKqlCaX/view?usp=drivesdk  This is the video demonstration of this app.
The overall flow of app is such that it is explained below:-
1->The first activity shows 4 slots (6-7 AM slot), (7-8 AM slot), (8-9 AM slot) and (5-6 PM slot). 
2-> Now when you choose any slot that info will be passed to the next activity and then you will be asked to register, on registering your info (Name, Age should be between 18 and 65, email, and also registering_month which has been used further for implementing that feature of not letting the user to change slot within same month of registering) that data will be passed as object of user class and saved in database of your file.
3-> After that payment page will be shown in which is a dummy page having two options payment failed and payment passed. If payment is passed we will be redirected to the next page having a countdown timer.
4-> The countdown timer works in such a way that it shows hours, minutes and seconds left for your next yoga session. If suppose the user who has been logged in has chosen slot 6-7 Am and current time is 11 pm the countdown will be shown a time of 7 hours 0 mins 0 secs and it will be running getting updated every second. The logic behind this is taking current time from system and slot time from database, finding the difference, showing it in xml and updating it every second.
5-> After that it will also have a button having the option of changing slot, which will be activated only after the registration month has passed.

