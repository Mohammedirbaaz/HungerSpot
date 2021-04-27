package com.example.hungerspot.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hungerspot.*
import com.example.hungerspot.R
import com.example.hungerspot.ui.gallery.GalleryFragment
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class HomeFragment : Fragment() {


    lateinit var refsforfinal: DatabaseReference;

     var mRecyclerview2: RecyclerView?=null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManagement = SessionManagment();
        activity?.let { sessionManagement.SessionManagement2(it) };
        var useridsss=sessionManagement.getSession();
        val ff="||";
        val list= useridsss?.split(ff);
        val userid= list?.get(0);
        val pincode= list?.get(1);
        val typesofuser= list?.get(2);

        var foodupload=view.findViewById<Button>(R.id.idfooduploadbtn)
        var request=view.findViewById<Button>(R.id.idrequests)
        var mycontribution=view.findViewById<Button>(R.id.idmycontributes)

        if(typesofuser=="Volunteer"){

            Toast.makeText(activity,typesofuser.toString(),Toast.LENGTH_SHORT).show()
            foodupload.visibility=View.GONE;
            request.visibility=View.GONE;
            mycontribution.visibility=View.GONE;
            mRecyclerview2?.visibility=View.VISIBLE;
            mRecyclerview2=view.findViewById(R.id.idlistview2);


            var refsamepincode=FirebaseDatabase.getInstance().getReference("Donor").child(pincode.toString()).child(("MyContribution@@"));
            refsamepincode.keepSynced(true);
            logicRecyclerView(refsamepincode);

            mRecyclerview2?.setHasFixedSize(true);
            mRecyclerview2?.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)



        }
        else if(typesofuser=="Donor"){
            mRecyclerview2?.visibility=View.GONE;

            foodupload.setOnClickListener {
                val gm:Fragment=GalleryFragment();
                val Fragmentss: FragmentTransaction? = fragmentManager?.beginTransaction()
                Fragmentss?.replace(R.id.nav_home,gm);
                Fragmentss?.commit();

            }

        }








    }
    fun logicRecyclerView(refs:DatabaseReference){





            val firebaseRecyclerOptions =
                FirebaseRecyclerOptions.Builder<orders>().setQuery(refs, orders::class.java).build()


            val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<orders, orderViewHolder>(
                firebaseRecyclerOptions
            ) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): orderViewHolder {
                    val v: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_layout, parent, false)
                    return orderViewHolder(v)
                }

                override fun onBindViewHolder(
                    holder: orderViewHolder,
                    position: Int,
                    model: orders
                ) {
                    holder.notess.setText(model.notes);
//                Picasso.get().load(model.imgurl).into(holder.imgs)

                    holder.from.setText(model.timefrom);
                    holder.till.setText(model.timetill);

                }

            }
            firebaseRecyclerAdapter.startListening();
            mRecyclerview2?.adapter = firebaseRecyclerAdapter;

    }
}

class orderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    //    var imgs=itemView.findViewById<ImageView>(R.id.idimage);
    var notess=itemView.findViewById<TextView>(R.id.idnotess);
    var from=itemView.findViewById<TextView>(R.id.idfrom);
    var till=itemView.findViewById<TextView>(R.id.idtill);


}