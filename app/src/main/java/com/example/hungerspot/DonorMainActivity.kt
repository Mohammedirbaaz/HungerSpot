package com.example.hungerspot

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DonorMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val sessionManagement = SessionManagment();
        sessionManagement.SessionManagement2(this);
        var useridsss=sessionManagement.getSession();
        val ff="||";
        val list= useridsss?.split(ff);
        val userid= list?.get(0);
        val pincode= list?.get(1);
        val typesofuser= list?.get(2);




            Toast.makeText(this,typesofuser,Toast.LENGTH_SHORT).show();

            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            val navView: NavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            appBarConfiguration = AppBarConfiguration(setOf(
                    R.id.nav_home2, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_mycontribution,R.id.nav_mydetail), drawerLayout)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController);



    }
    override fun onBackPressed(){

        var alert=AlertDialog.Builder(this);

        alert.setMessage("Are you sure? you want to exit?").setPositiveButton("Yes"){dialog: DialogInterface?, which: Int ->
//            finish()
              val Intents=Intent(this,MainActivity::class.java);
//              Intents.putExtra("secret1","toquit")
              startActivity(Intents);

        }.setNegativeButton("Stay"){dialog:DialogInterface, which -> }
        val alertDialog: AlertDialog = alert.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.donor_main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}