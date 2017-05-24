package com.driverapp.View;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import tyrantgit.explosionfield.ExplosionField;

public class LoginActivity extends AppCompatActivity{

    private EditText mUsernameView, mPasswordView;
    private View mProgressView;
    private ConnectivityManager connectivityManager;
    private static CheckBox show_hide_password;
    private TextView tvSignUp;
    private ExplosionField mExplosionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

        UserInstance.getInstance().setAuth(new Auth());
        UserInstance.getInstance().setBus(new Bus());
        UserInstance.getInstance().setDriver(new Driver());
        UserInstance.getInstance().setRoute(new Route());
        UserInstance.getInstance().setVolleyApp(new VolleyApp());
        UserInstance.getInstance().setUtility(new Utility());
        UserInstance.getInstance().setBusLocation(0);
        UserInstance.getInstance().setQueue(Volley.newRequestQueue(getApplicationContext()));

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        tvSignUp = (TextView) findViewById(R.id.tvSignUp_text);
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
        show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            mPasswordView.setInputType(InputType.TYPE_CLASS_TEXT);
                            mPasswordView.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            mPasswordView.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            mPasswordView.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });

        tvSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mExplosionField = ExplosionField.attach2Window(LoginActivity.this);
                mExplosionField.explode(tvSignUp);
                new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
//                        Intent i = new Intent(LoginActivity.this, SplashActivity.class);
//                        startActivity(i);
                        Toast.makeText(LoginActivity.this, "Open Sign-Up Activity", Toast.LENGTH_SHORT).show();
                    }

                }.start();




            }
        });


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

