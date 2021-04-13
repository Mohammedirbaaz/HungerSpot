package com.example.hungerspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class VolunteerRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer_register);


        var haveacnt=findViewById<TextView>(R.id.haveanaccountid);
        haveacnt.setOnClickListener {
            var intent6=Intent(this,VolunteerLoginActivity::class.java);
            startActivity(intent6);
        }
    }
}