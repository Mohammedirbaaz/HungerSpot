package com.example.hungerspot

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.jar.Manifest

class DonorProcessActivity : AppCompatActivity() {
    var REQUEST_PHONE_CALL=1;
    var phns:String?=null;
    var imgid1idnn:ImageView?=null;
    var imgid2idnn:ImageView?=null;
    var btnsatisfied:Button?=null;
    var btnnotsatisfied:Button?=null;
    var imguri1:Uri?=null;
    var imguri2:Uri?=null;

    var ratedc:Int?=0;
    var avgc:Float?=0F;






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_process);

        this.setTitle("Delivery Details")

        var namefield=findViewById<TextView>(R.id.voluntnameproid);
        var callbtnfield=findViewById<Button>(R.id.btncallid);
        val intss=intent;
        val voluntsid=intss.getStringExtra("voluntprocessdetail");
        val dishesids=intss.getStringExtra("voluntprocessdetail2");
        imgid1idnn=findViewById(R.id.imgproid1);
        imgid2idnn=findViewById(R.id.imgproid2);
        btnsatisfied=findViewById(R.id.btnsatisfiedid);
        btnnotsatisfied=findViewById(R.id.btnunsatisfied);

        findViewById<LinearLayout>(R.id.ldidd).visibility=View.GONE;
        imgid1idnn?.visibility= View.GONE;
        imgid2idnn?.visibility= View.GONE;
        btnsatisfied?.visibility= View.GONE;
        btnnotsatisfied?.visibility=View.GONE;
        findViewById<TextView>(R.id.didntsuplytxtid).visibility=View.VISIBLE;



        val sessionManagement = SessionManagment();
        sessionManagement.SessionManagement2(this) ;
        var useridsss=sessionManagement.getSession();
        val ff="||";
        val list= useridsss?.split(ff);
        val userid= list?.get(0);
        val pincode= list?.get(1);
        val typesofuser= list?.get(2);



        val reffsfortemp=
            FirebaseDatabase.getInstance().getReference("Volunteer").child(pincode.toString()).child(voluntsid.toString());
        reffsfortemp.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                for(h in snapshot.children){
                    if(h.key.toString()=="name"){
                        namefield.text=h.value.toString();
                    }
                }
            }
        })

        val reffstemp=FirebaseDatabase.getInstance().getReference("Donor").child(pincode.toString()).child(userid.toString()).child("Finishedwork@");

        reffstemp.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                for (h in snapshot.children){

                    for(k in h.children){
                        if (k.key.toString()=="dishid"){
                            if(k.value.toString()==dishesids.toString()){

                                Toast.makeText(this@DonorProcessActivity,k.key.toString(),Toast.LENGTH_SHORT).show()
                                findViewById<LinearLayout>(R.id.ldidd).visibility=View.VISIBLE;
                                imgid1idnn?.visibility= View.VISIBLE;
                                imgid2idnn?.visibility= View.VISIBLE;
                                btnsatisfied?.visibility= View.VISIBLE;
                                btnnotsatisfied?.visibility=View.VISIBLE;
                                findViewById<TextView>(R.id.didntsuplytxtid).visibility=View.GONE;
                            }
                        }
                        else if(k.key.toString()=="imgurl1"){
                            imguri1=k.value.toString().toUri();
                            Log.i("sdfdfdf",imguri1.toString());
                            Picasso.get().load(imguri1).into(imgid1idnn);
                        }
                        else if(k.key.toString()=="imgurl2"){
                            imguri2=k.value.toString().toUri();
                            Log.i("sdfdfdf2",imguri2.toString());
                            Picasso.get().load(imguri2).into(imgid2idnn);
                        }

                    }
                }
            }

        })

        btnsatisfied?.setOnClickListener {
            var ratingb=findViewById<RatingBar>(R.id.ratingbarid);
            var ratingcounts: Float = 0F;
            ratingcounts=ratingb.rating;
            val refforrating=FirebaseDatabase.getInstance().getReference("Volunteer").child(pincode.toString()).child(voluntsid.toString()).child("Ratings@@");
            refforrating.addValueEventListener(object :ValueEventListener{
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (h in snapshot.children){
                        if(h.key.toString()=="noofclient") {
                            ratedc=h.value.toString().toInt();
                        }
                        if(h.key.toString()=="avgrating"){
                            avgc=h.value.toString().toFloat();
                        }
                    }
                }
            })

            var ratedint: Int? =ratedc;
            if (ratedint != null) {
                ratedint=ratedint+1
            }
            Toast.makeText(this,ratedint.toString(),Toast.LENGTH_SHORT).show();

            var avgint:Float?= avgc;
            if (avgint != null) {
                avgint=avgint+ratingcounts
            }
            Toast.makeText(this,avgint.toString(),Toast.LENGTH_SHORT).show();


            val insertratingdata=satisfiedclient(ratedint.toString(),avgint.toString());
            refforrating.setValue(insertratingdata).addOnCompleteListener {
                Toast.makeText(this,avgint.toString(),Toast.LENGTH_SHORT).show();
                Log.i("ratingwork",ratedint.toString()+avgint.toString());
            }












            val reforremovalacpt=FirebaseDatabase.getInstance().getReference("Donor").child(pincode.toString()).child(userid.toString()).child("Accepts");
            val refforremoverequest=FirebaseDatabase.getInstance().getReference("Donor").child(pincode.toString()).child(userid.toString()).child("Requests");
            val refforreomverprocessing=FirebaseDatabase.getInstance().getReference("Volunteer").child(pincode.toString()).child(voluntsid.toString()).child("Processing");

            reforremovalacpt.addValueEventListener(object:ValueEventListener{
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (h in snapshot.children){
                        for(k in h.children){
                            if (k.key.toString()=="idofdish"){
                                if(k.value.toString()==dishesids.toString()){
                                    reforremovalacpt.child(h.key.toString()).removeValue();
                                }
                            }
                        }

                    }
                }
            })

            refforremoverequest.addValueEventListener(object:ValueEventListener{
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (h in snapshot.children){
                        for(k in h.children){
                            if (k.key.toString()=="idofdishes"){
                                if(k.value.toString()==dishesids.toString()){
                                    refforremoverequest.child(h.key.toString()).removeValue();
                                }
                            }
                        }

                    }
                }

            })
            refforreomverprocessing.addValueEventListener(object:ValueEventListener{
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (h in snapshot.children){
                        for(k in h.children){
                            if (k.key.toString()=="idofdish"){
                                if(k.value.toString()==dishesids.toString()){
                                    refforreomverprocessing.child(h.key.toString()).removeValue();
                                }
                            }
                        }

                    }
                }

            })
        }





        callbtnfield.setOnClickListener {
            val reffsfortemp2=
                FirebaseDatabase.getInstance().getReference("Volunteer").child(pincode.toString()).child(voluntsid.toString());
            reffsfortemp2.addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(h in snapshot.children){
                        if(h.key.toString()=="phno"){
                            phns=h.value.toString();
                            if(ActivityCompat.checkSelfPermission(applicationContext,android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(this@DonorProcessActivity, arrayOf(android.Manifest.permission.CALL_PHONE),REQUEST_PHONE_CALL)
                            }else{
                                    startcall(h.value.toString());
                            }
                        }
                    }
                }
            })
        }
    }

    @SuppressLint("MissingPermission")
    fun startcall(strr:String?){
        val callintent=Intent(Intent.ACTION_CALL);
        callintent.data= Uri.parse("tel:"+strr);
        startActivity(callintent);
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==REQUEST_PHONE_CALL){
            startcall(phns);
        }
    }
}