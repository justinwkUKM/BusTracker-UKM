package com.myxlab.bustracker.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.toolbox.Volley;
import com.myxlab.bustracker.Controller.VolleyApp;
import com.myxlab.bustracker.Model.Auth;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.Utility;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UserInstance.getInstance().setAuth(new Auth());
        UserInstance.getInstance().setQueue(Volley.newRequestQueue(getApplicationContext()));
        UserInstance.getInstance().setVolleyApp(new VolleyApp());
        UserInstance.getInstance().setUtility(new Utility());

        Boolean check = UserInstance.getInstance().getAuth().checkAuth(getApplication().getApplicationContext());

        if (!check) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }, 4000);
        } else {
            UserInstance.getInstance().getVolleyApp().autoLogin(getResources().getString(R.string.url_login),getApplicationContext(),this);
        }
    }
}
