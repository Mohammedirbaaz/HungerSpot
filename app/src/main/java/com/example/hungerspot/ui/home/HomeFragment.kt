package com.example.hungerspot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hungerspot.R
import com.example.hungerspot.ui.gallery.GalleryFragment
import com.google.android.gms.dynamic.SupportFragmentWrapper

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        homeViewModel =
//                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var foodupload=view.findViewById<Button>(R.id.idfooduploadbtn)
        var request=view.findViewById<Button>(R.id.idrequests)
        var mycontribution=view.findViewById<Button>(R.id.idmycontributes)

        foodupload.setOnClickListener {
            val gm:Fragment=GalleryFragment();
//            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_gallery,hm)?.commit()
            val Fragmentss: FragmentTransaction? = fragmentManager?.beginTransaction()
            Fragmentss?.replace(R.id.nav_home,gm);
            Fragmentss?.commit();

        }


    }
}