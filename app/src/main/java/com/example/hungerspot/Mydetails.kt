package com.example.hungerspot

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Mydetails : Fragment() {

    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mydetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var namess=view.findViewById<TextView>(R.id.idname);
        var emailss=view.findViewById<EditText>(R.id.idemail);
        var addresss=view.findViewById<EditText>(R.id.idaddress);
        var landmarks=view.findViewById<EditText>(R.id.idlandmark);
        var phnoss=view.findViewById<EditText>(R.id.idphno);
        var pincodess=view.findViewById<EditText>(R.id.idpincode);
        var logoutbtnss=view.findViewById<Button>(R.id.idlogoutbtn)


        val sessionManagement = SessionManagment();
        activity?.let { sessionManagement.SessionManagement2(it) };
        var useridsss=sessionManagement.getSession();
        val ff="||";
        val list= useridsss?.split(ff);
        val userid= list?.get(0);
        val pincode= list?.get(1);
        val typesofuser= list?.get(2);


        if(typesofuser=="Donor") {
            var reffs =
                FirebaseDatabase.getInstance().getReference("Donor").child(pincode.toString())
                    .child(userid.toString());
            reffs.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (h in snapshot.children) {
                        if (h.key.toString() == "name") {
                            namess.text = h.value.toString();
                        } else if (h.key.toString() == "email") {
                            emailss.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        } else if (h.key.toString() == "address") {
                            addresss.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        } else if (h.key.toString() == "landmark") {
                            landmarks.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        } else if (h.key.toString() == "phno") {
                            phnoss.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        } else if (h.key.toString() == "pincode") {
                            pincodess.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        }

                    }
                }

            })
        }else if(typesofuser=="Volunteer"){

            var dob=view.findViewById<EditText>(R.id.iddobs)
//            var gender=view.findViewById<EditText>(R.id.idgender)

            dob.visibility=View.VISIBLE;
//            gender.visibility=View.VISIBLE;

            var reffs =
                FirebaseDatabase.getInstance().getReference("Volunteer").child(pincode.toString())
                    .child(userid.toString());
            reffs.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (h in snapshot.children) {
                        if (h.key.toString() == "name") {
                            namess.text = h.value.toString();
                        } else if (h.key.toString() == "email") {
                            emailss.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        } else if (h.key.toString() == "address") {
                            addresss.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        } else if (h.key.toString() == "landmark") {
                            landmarks.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        } else if (h.key.toString() == "phno") {
                            phnoss.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        } else if (h.key.toString() == "pincode") {
                            pincodess.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        }
//                        else if (h.key.toString() == "gender") {
//                            gender.text =
//                                Editable.Factory.getInstance().newEditable(h.value.toString());
//                        }
                        else if (h.key.toString() == "dob") {
                            dob.text =
                                Editable.Factory.getInstance().newEditable(h.value.toString());
                        }
                    }
                }
            })
        }

        logoutbtnss.setOnClickListener {
            sessionManagement.removeSession();
            var intentn=Intent(activity,DonorLoginActivity::class.java);
            startActivity(intentn);

        }
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Mydetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}