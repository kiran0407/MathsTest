package kiran541.ench.com.mathstest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Kiran";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public static final String KEY_NAME1 = "user";

    public static final String KEY_EMAIL1 = "email";

    private static final String IS_LOGIN = "IsLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void createLoginSession(String user, String email,boolean isLoggedIn){
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // Storing name in pref
        editor.putString(KEY_NAME1, user);

        // Storing email in pref
        editor.putString(KEY_EMAIL1, email);

        // commit changes
        editor.commit();
    }
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Signin.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user1 = new HashMap<String, String>();
        // user name
        user1.put(KEY_NAME1, pref.getString(KEY_NAME1, null));

        // user email id
        user1.put(KEY_EMAIL1, pref.getString(KEY_EMAIL1, null));

        // return user
        return user1;
    }
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Signin.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

//    public void setLogin(boolean isLoggedIn) {
//
//        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
//        // commit changes
//        editor.commit();
//
//        Log.d(TAG, "User login session modified!");
//    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}