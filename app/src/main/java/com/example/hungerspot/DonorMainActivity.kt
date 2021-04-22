package com.example.hungerspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DonorMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_main);

        var logoutbtn=findViewById<Button>(R.id.logoutbtnid);
        var textviews=findViewById<TextView>(R.id.sessioidtext);
        var  sg= SessionManagment();
        sg.SessionManagement2(this@DonorMainActivity);

        textviews.text=sg.getSession();

        logoutbtn.setOnClickListener {
            sg.removeSession();
            movetopublicmainactivity();


        }
    }
    fun movetopublicmainactivity(){
        val intent: Intent = Intent(this,MainActivity::class.java);
        startActivity(intent);
    }
}