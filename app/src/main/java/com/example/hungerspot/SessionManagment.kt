package com.example.hungerspot

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast

public class SessionManagment{

      var sharedpref:SharedPreferences?=null;
     lateinit var editor:SharedPreferences.Editor;
    var SHARED_PREF_NAME="session";
    var SESSION_KEY="";


    fun SessionManagement2(context:Context){
        sharedpref=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor= sharedpref?.edit()!!;
    }
    fun saveSession(user:User){
        val ids: String? =user.ids;
        editor.putString(SESSION_KEY,ids)?.commit();
        if (ids != null) {
            Log.i("saved",ids)
        };
    }

    fun  getSession(): String? {
        var userids= sharedpref?.getString(SESSION_KEY," ");
        if (userids != null) {
            Log.i("checkme",userids)
        };
        return userids;
    }
    fun removeSession(){
        editor.putString(SESSION_KEY," ").commit();
    }

}