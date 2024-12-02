package com.driverapp.Model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Auth.
 */
public class Auth {

    private String auth_token, username, password;
    /**
     * The constant KEY_USERNAME.
     */
    public static final String KEY_USERNAME = "username";
    /**
     * The constant KEY_PASSWORD.
     */
    public static final String KEY_PASSWORD = "password";

    /**
     * Instantiates a new Auth.
     */
    public Auth() {
    }

    /**
     * Gets auth token.
     *
     * @return the auth token
     */
    public String getAuth_token() {
        return auth_token;
    }

    /**
     * Gets credential.
     *
     * @return the credential
     */
    public Map<String, String> getCredential() {
        Map<String, String> params = new HashMap<>();
        params.put(KEY_USERNAME, this.username);
        params.put(KEY_PASSWORD, this.password);
        return params;
    }

/*    public void saveAuth(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean checkAuth(){

        return username != null && password != null;

    }*/

    /**
     * Sets auth token.
     *
     * @param auth_token the auth token
     */
    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    /**
     * Save auth.
     *
     * @param context  the context
     * @param username the username
     * @param password the password
     */
    public void saveAuth(Context context, String username, String password){
        this.username = username;
        this.password = password;

        SharedPreferences sp = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();
    }

    /**
     * Check auth boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public boolean checkAuth(Context context){
        SharedPreferences sp = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
        String username = sp.getString("username",null);
        String password = sp.getString("password",null);

        if (username != null && password != null){
            this.username = username;
            this.password = password;

            return true;
        }

        return  false;
    }

    /**
     * Check out auth.
     *
     * @param context the context
     */
    public void checkOutAuth(Context context){
        this.username = null;
        this.password = null;

        SharedPreferences sp = context.getSharedPreferences("Auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",null);
        editor.putString("password",null);
        editor.apply();
    }
}

