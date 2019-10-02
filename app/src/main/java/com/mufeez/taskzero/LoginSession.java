package com.mufeez.taskzero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;



public class LoginSession {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;


    public LoginSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(AllConstants.INSTANCE.getPREF_NAME(), PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String username,String email,String photourl)
    {
        Log.i("coming here","user"+username);
        editor.putBoolean(AllConstants.INSTANCE.getIS_LOGIN(),true);
        editor.putString(AllConstants.INSTANCE.getUSERNAME(),username);
        editor.putString(AllConstants.INSTANCE.getEMAIL(),email);
        editor.putString(AllConstants.INSTANCE.getPHOTO(),photourl);
        editor.commit();

    }

    public boolean isLoggedIn() {
        return pref.getBoolean(AllConstants.INSTANCE.getIS_LOGIN(), false);
    }

    public Map<String,String> getLoginDetails() {
        Map<String,String> logindetailsMap=new HashMap<>();
        logindetailsMap.put(AllConstants.INSTANCE.getUSERNAME(),pref.getString(AllConstants.INSTANCE.getUSERNAME(),""));
        logindetailsMap.put(AllConstants.INSTANCE.getEMAIL(),pref.getString(AllConstants.INSTANCE.getEMAIL(),""));
        logindetailsMap.put(AllConstants.INSTANCE.getPHOTO(),pref.getString(AllConstants.INSTANCE.getPHOTO(),""));
        return logindetailsMap;
    }

}