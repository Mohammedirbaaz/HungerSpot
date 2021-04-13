package com.example.hungerspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class VolunteerLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer_login);

        var donthaveacnt=findViewById<TextView>(R.id.donthaveacntid);
        donthaveacnt.setOnClickListener {
            var intent5=Intent(this,VolunteerRegisterActivity::class.java);
            startActivity(intent5);
        }
    }
}