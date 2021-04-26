package com.example.hungerspot.ui.gallery

import android.app.Activity
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hungerspot.R
import com.example.hungerspot.SessionManagment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class GalleryFragment : Fragment() {

    lateinit var filepath:Uri
    lateinit var imgurl:String



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_gallery, container, false);
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);





        val btnfrom=view.findViewById<Button>(R.id.btntimefrom);
        val btntill=view.findViewById<Button>(R.id.btnidtimetill);
        val textdatefrom=view.findViewById<TextView>(R.id.idtimefrom);
        val textdatetill=view.findViewById<TextView>(R.id.idtimetill);
        var btnfoodupload=view.findViewById<Button>(R.id.idbtnfoodupload);
        var addressf=view.findViewById<EditText>(R.id.idaddressdonor);
        var landmarkf=view.findViewById<EditText>(R.id.idlandmarkdonor);
        var notesf=view.findViewById<EditText>(R.id.idnotesdonor);
        var images=view.findViewById<ImageView>(R.id.idimage);

        btnfrom.setOnClickListener {
            val cal=Calendar.getInstance();
            val timeSetListener=TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);

                textdatefrom.text=SimpleDateFormat("HH:mm").format(cal.time);

            }
            TimePickerDialog(activity,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }
        btntill.setOnClickListener {
            val cal=Calendar.getInstance();
            val timeSetListener=TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);

                textdatetill.text=SimpleDateFormat("HH:mm").format(cal.time);

            }
            TimePickerDialog(activity,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()
        }

        btnfoodupload.setOnClickListener {

            val progressDialog=ProgressDialog(activity);
            progressDialog.setMessage("Uploading..");
            progressDialog.setCancelable(false);
            progressDialog.show();

            uploadImageAndDetail(progressDialog);


        }
        images.setOnClickListener{
            startFileChooser();
        }
    }
    fun startFileChooser(){

        var i=Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Choose pics"),111);
    }
    fun uploadImageAndDetail(Prog:ProgressDialog){

        val textdatefrom=view?.findViewById<TextView>(R.id.idtimefrom);
        val textdatetill=view?.findViewById<TextView>(R.id.idtimetill);
        var addressf=view?.findViewById<EditText>(R.id.idaddressdonor);
        var landmarkf=view?.findViewById<EditText>(R.id.idlandmarkdonor);
        var notesf=view?.findViewById<EditText>(R.id.idnotesdonor);

        var imgreffs=FirebaseStorage.getInstance().reference.child("DonorContributions").child( "pic.jpg");
        imgreffs.putFile(filepath).addOnSuccessListener(object:OnSuccessListener<UploadTask.TaskSnapshot>{
            override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                imgreffs.downloadUrl.addOnSuccessListener(object :OnSuccessListener<Uri>{
                    override fun onSuccess(p0: Uri?) {
                        imgurl=p0.toString();

                        val sessionManagement = SessionManagment();
                        activity?.let { it1 -> sessionManagement.SessionManagement2(it1) };
                        val idss=sessionManagement.getSession();

                        var fooduploaddata=foodupload(imgurl, textdatefrom?.text.toString(), textdatetill?.text.toString(), addressf?.text.toString(), landmarkf?.text.toString(), notesf?.text.toString());
                        var reffs2= idss?.let { it1 -> FirebaseDatabase.getInstance().getReference("Donor").child(it1).child("MyContribution").push() }

                        reffs2?.setValue(fooduploaddata)?.addOnCompleteListener {
                            Prog.dismiss();
                            Toast.makeText(activity,"Item Uploaded", Toast.LENGTH_SHORT).show();
                        }?.addOnCanceledListener {
                            Toast.makeText(activity,"Failed to Upload",Toast.LENGTH_SHORT).show();

                        }
                    }

                })
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data);
        var images= view?.findViewById<ImageView>(R.id.idimage);

        if (requestCode==111 && resultCode==Activity.RESULT_OK && data!=null){
            filepath=data.data!!;
            var bitmap=MediaStore.Images.Media.getBitmap(activity?.contentResolver,filepath);
            if (images != null) {
                images.setImageBitmap(bitmap)
            };
        }
    }
}




class foodupload(var imgurl:String,var timefrom:String,var timetill:String,var address:String?,var landmark:String?,var notes:String?)