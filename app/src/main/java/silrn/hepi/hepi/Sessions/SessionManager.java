package silrn.hepi.hepi.Sessions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

import silrn.hepi.hepi.LoginActivity;
import silrn.hepi.hepi.MainActivity;

public class SessionManager {
    SharedPreferences preferences;
    Editor editor;
    Context _context;

    /** Shared preference mode*/
    int PRIVATE_MODE = 0;
    /** Shared preference file name*/
    private  static final String PREF_NAME = "Hepi";
    /** All shared preference keys*/
    private  static final String IS_LOGIN = "LoggedIn";
    public static final String KEY_USERNAME = "username";


    /**Constructor*/
    public SessionManager(Context context){
        this._context = context;
        preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /**Login session creation*/
    public void createLoginSession(String username){
        /* Storing login value as TRUE*/
        editor.putBoolean(IS_LOGIN, true);
        /* Storing username in preferences*/
        editor.putString(KEY_USERNAME, username);
        /* Saving the changes*/
        editor.commit();
    }

    /**
     * Checking user login status
     * if FALSE, redirect to login page
     * Else do nothing
     * */
    public void checkLogin(){
        // Checking login status
        if (!this.isLoggedIn()){
            // Redirect to Login Activity
            Intent intent = new Intent(_context, LoginActivity.class);
            // Close all the activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Start Login Activity
            _context.startActivity(intent);
        }
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USERNAME, preferences.getString(KEY_USERNAME, null));
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        /* Clear data from shared preferences*/
        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
    }

    /**
     * Quick check for login
     *
     * Get login State
     * */
    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGIN, false);
    }
}

