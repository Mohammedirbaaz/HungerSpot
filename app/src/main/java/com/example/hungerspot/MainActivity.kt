package com.example.hungerspot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sessionManagement = SessionManagment();
        sessionManagement.SessionManagement2(this);
        var useridsss=sessionManagement.getSession();
        val ff="||";
        val list= useridsss?.split(ff);
//        val userid= list?.get(0);
//        val pincode= list?.get(1);
//        val typesofuser= list?.get(2);
//Toast.makeText(this,userid+" "+pincode+" "+typesofuser,Toast.LENGTH_LONG).show()

        var donorbtn=findViewById<Button>(R.id.donorbtnid);
        var volunteerbtn=findViewById<Button>(R.id.volunteerbtnid);

        donorbtn.setOnClickListener {
            var intent=Intent(this,DonorLoginActivity::class.java);
            startActivity(intent);
        }
        volunteerbtn.setOnClickListener {
            var intent2=Intent(this,VolunteerLoginActivity::class.java);
            startActivity(intent2);
        }
//        val intentss=intent.getStringExtra("secret1");
//        if(intentss=="toquit"){
//            finish()
//        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}