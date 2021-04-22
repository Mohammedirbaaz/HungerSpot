package com.example.hungerspot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log

class DonorLoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_login);

        var btnslogin=findViewById<Button>(R.id.btnloginid);
        var usernamess=findViewById<EditText>(R.id.usernameid);
        var passwordss=findViewById<EditText>(R.id.passwordid);

        var didnthaveacnt=findViewById<TextView>(R.id.donthaveacnt);
        didnthaveacnt.setOnClickListener {
            var intent3=Intent(this,DonorRegisterActivity::class.java);
            startActivity(intent3);
        }


        btnslogin.setOnClickListener {
            val reffs = FirebaseDatabase.getInstance().getReference("Donor");

            var userinfo=usernamess.text.toString();
            var passwordinfo=passwordss.text.toString();
            val emailsplitter2=userinfo.indexOf("@");
            val finalstring2=userinfo.substring(0,emailsplitter2);


            reffs.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userfromdb=dataSnapshot.child(finalstring2).child("email").value.toString();
                    val pswdfromdb=dataSnapshot.child(finalstring2).child("password").value.toString();
                    val username=dataSnapshot.child(finalstring2).child("name").value.toString();

                    if(userinfo==userfromdb && passwordinfo==pswdfromdb){
                            Toast.makeText(applicationContext,"loggedin",Toast.LENGTH_SHORT).show();

                            val user=User(finalstring2,username);
                            val sessionManagement = SessionManagment();
                            sessionManagement.SessionManagement2(this@DonorLoginActivity);
                            sessionManagement.saveSession(user);
                            movetomainactivity();


                    }else{
                        Toast.makeText(applicationContext,"userid or password is incorrect",Toast.LENGTH_SHORT).show();

                    }


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("firebase", "loadPost:onCancelled", databaseError.toException())
                }
            })

        }


    }

    override fun onStart() {
        super.onStart();

        val sessionManagement = SessionManagment();
        sessionManagement.SessionManagement2(this@DonorLoginActivity);

        val userid: String? = sessionManagement.getSession();

        if (userid != null) {
            Log.i("idss",userid)
        };

        if(userid!=" "){
            movetomainactivity();

        }
    }
    fun movetomainactivity(){
        val intent:Intent= Intent(this,DonorMainActivity::class.java);
        startActivity(intent);


    }
}