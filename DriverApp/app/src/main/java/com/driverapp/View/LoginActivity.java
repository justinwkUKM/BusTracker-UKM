package com.driverapp.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.driverapp.BaseActivity;

import com.driverapp.Model.Auth;
import com.driverapp.Model.Bus;
import com.driverapp.Model.BusStop;
import com.driverapp.Model.Driver;
import com.driverapp.Model.Route;
import com.driverapp.Model.UserInstance;
import com.driverapp.R;
import com.driverapp.Controller.VolleyApp;
import com.driverapp.Utility;
import com.github.florent37.viewanimator.ViewAnimator;
import com.github.glomadrian.roadrunner.IndeterminateRoadRunner;
import com.google.firebase.database.FirebaseDatabase;

import tyrantgit.explosionfield.ExplosionField;

public class LoginActivity extends BaseActivity {

    private EditText mUsernameView, mPasswordView;
    private ProgressBar mProgressView;
    private ConnectivityManager connectivityManager;
    private static CheckBox show_hide_password;
    private TextView tvSignUp;
    private ExplosionField mExplosionField;
    private RelativeLayout relativeLayout;
    private IndeterminateRoadRunner indeterminateRoadRunner;
    private static final int REQUEST_CODE_PERMISSION = 2;

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


        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        UserInstance.getInstance().setAuth(new Auth());
        UserInstance.getInstance().setBus(new Bus());
        UserInstance.getInstance().setDriver(new Driver());
        UserInstance.getInstance().setRoute(new Route());
        UserInstance.getInstance().setVolleyApp(new VolleyApp());
        UserInstance.getInstance().setUtility(new Utility());
        UserInstance.getInstance().setBusLocation(0);
        UserInstance.getInstance().setQueue(Volley.newRequestQueue(getApplicationContext()));

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeImageLayout);
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

        mProgressView = (ProgressBar) findViewById(R.id.login_progress);
        indeterminateRoadRunner = (IndeterminateRoadRunner) findViewById(R.id.material);

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
                        //Toast.makeText(LoginActivity.this, "Open Sign-Up Activity", Toast.LENGTH_SHORT).show();
                    }

                }.start();


            }
        });

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 4);
                    return;
                }
                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            } else {
                //
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Req Code", "" + requestCode);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length >0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                // Success Stuff here
                Log.e("Success Stuff here", "" + requestCode);
            }
            else{
                // Failure Stuff
                Log.e("Failure Stuff here", "" + requestCode);
            }
        }

    }


    private void attemptLogin() {

        mUsernameView.setError(null);
        mPasswordView.setError(null);

        final String username = mUsernameView.getText().toString();
        final String password = mPasswordView.getText().toString();

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


//                mProgressView.setIndeterminate(true);
//                //mProgressView.setIndeterminateDrawable(getResources().getDrawable(R.drawable.driver_bus));
//                mProgressView.setInterpolator(new AccelerateDecelerateInterpolator());
//                mProgressView.setVisibility(View.VISIBLE);
                indeterminateRoadRunner.setVisibility(View.VISIBLE);
//                indeterminateRoadRunner.restart();

                new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                       UserInstance.getInstance().getVolleyApp().UserLoginTask(getString(R.string.url_login), username, password, getApplicationContext(), mProgressView, LoginActivity.this);

                    }

                }.start();

                if (indeterminateRoadRunner.getVisibility() == View.VISIBLE) {
                    outAnimation();
                } else {
                inAnimation();
                    //indeterminateRoadRunner.stop();
                }
            }
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }
    protected void outAnimation() {
        ViewAnimator.animate(relativeLayout)
                .translationY(0, 0)
                ./*translationX(0,50)
                .interpolator(new AccelerateInterpolator())
                .duration(250)
                .thenAnimate(relativeLayout).*/translationX(0, -2000).alpha(1, 0).interpolator(new AnticipateOvershootInterpolator())
                .duration(1500)

                .start();
    }
    protected void inAnimation() {
        ViewAnimator.animate(relativeLayout)
                .translationY(0, 0)
                ./*translationX(0,50)
                .interpolator(new AccelerateInterpolator())
                .duration(250)
                .thenAnimate(relativeLayout).*/translationX(2000, 0).alpha(0, 1).interpolator(new AnticipateOvershootInterpolator())
                .duration(1500)

                .start();
    }
}

