package com.driverapp.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.driverapp.Model.Auth;
import com.driverapp.Model.Bus;
import com.driverapp.Model.BusStop;
import com.driverapp.Model.Driver;
import com.driverapp.Model.Route;
import com.driverapp.Model.UserInstance;
import com.driverapp.R;
import com.driverapp.Controller.VolleyApp;
import com.driverapp.Utility;

public class LoginActivity extends AppCompatActivity{

    private EditText mUsernameView, mPasswordView;
    private View mProgressView;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserInstance.getInstance().setAuth(new Auth());
        UserInstance.getInstance().setBus(new Bus());
        UserInstance.getInstance().setDriver(new Driver());
        UserInstance.getInstance().setRoute(new Route());
        UserInstance.getInstance().setVolleyApp(new VolleyApp());
        UserInstance.getInstance().setUtility(new Utility());
        UserInstance.getInstance().setBusLocation(0);
        UserInstance.getInstance().setQueue(Volley.newRequestQueue(getApplicationContext()));

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {

        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            boolean networkCheck = UserInstance.getInstance().getUtility().getNetworkInfo(connectivityManager);

            if (!networkCheck) {
                Toast.makeText(this, R.string.check_internet, Toast.LENGTH_SHORT).show();
            } else {

                mProgressView.setVisibility(View.VISIBLE);

                UserInstance.getInstance().getVolleyApp().UserLoginTask(getString(R.string.url_login),username,password,getApplicationContext(), mProgressView, this);
            }
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

}

