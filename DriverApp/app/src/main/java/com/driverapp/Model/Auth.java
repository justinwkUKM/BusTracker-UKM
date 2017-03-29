package com.driverapp.Model;

import java.util.HashMap;
import java.util.Map;

public class Auth {

    private String auth_token, username, password;
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "pass";

    public Auth() {
    }

    public String getAuth_token() {
        return auth_token;
    }

    public Map<String, String> getCredential() {
        Map<String, String> params = new HashMap<>();
        params.put(KEY_USERNAME, this.username);
        params.put(KEY_PASSWORD, this.password);
        return params;
    }

    public void saveAuth(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean checkAuth(){

        return username != null && password != null;

    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
