package com.example.hungerspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class DonorRegisterActivity : AppCompatActivity() {
    lateinit var name:EditText;
    lateinit var phno:EditText;
    lateinit var email:EditText;
    lateinit var address:EditText;
    lateinit var landmark:EditText;
    lateinit var pincode:EditText;
    lateinit var password:EditText;
    lateinit var cpassword:EditText;




    lateinit var regbtn:Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_register)

        var alreadyhaveaccount=findViewById<TextView>(R.id.alreadyhaveacntid);
        alreadyhaveaccount.setOnClickListener {
            var intent4=Intent(this,DonorLoginActivity::class.java);
            startActivity(intent4);
        }


        name=findViewById(R.id.donorregusernameid);
        phno=findViewById(R.id.donorregphnoid);
        email=findViewById(R.id.donorregemailid);
        address=findViewById(R.id.donorregaddressid);
        landmark=findViewById(R.id.donorreglanmrkid);
        pincode=findViewById(R.id.donorregpincodeid);
        password=findViewById(R.id.donorregpswdid);
        cpassword=findViewById(R.id.donorregcpswdid);

        regbtn=findViewById(R.id.registerbtniddonor);



        regbtn.setOnClickListener {


            val names = name.text.toString().trim();
            val phnos = phno.text.toString().trim();
            val emails = email.text.toString().trim();
            val addresss = address.text.toString().trim();
            val landmarks = landmark.text.toString().trim();
            val pincodes = pincode.text.toString().trim();
            val passwords = password.text.toString().trim();
            val cpasswords = cpassword.text.toString().trim();

            if(cpasswords.equals(passwords)){
                val donorreg = donorclass(names, phnos,emails,addresss,landmarks,pincodes,passwords);
                val reffs = FirebaseDatabase.getInstance().getReference("Donor");

                reffs.push().key?.let { it1 ->
                    reffs.child(it1).setValue(donorreg).addOnCompleteListener{
                        Toast.makeText(this,"uploaded Successfully",Toast.LENGTH_SHORT).show();

                    }.addOnCanceledListener {
                        Toast.makeText(this,"Uploading Failed",Toast.LENGTH_SHORT).show();

                    }
                };

            }else{
                Toast.makeText(this,"Passwords are not matching",Toast.LENGTH_SHORT).show();

            }


        }

    }
}