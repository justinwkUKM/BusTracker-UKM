package com.myxlab.bustracker.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.android.volley.toolbox.Volley;
import com.crashlytics.android.answers.Answers;
import com.github.florent37.viewanimator.ViewAnimator;
import com.myxlab.bustracker.BaseActivity;
import com.myxlab.bustracker.Controller.VolleyApp;
import com.myxlab.bustracker.Model.Auth;
import com.myxlab.bustracker.Model.UserInstance;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.Utility;
import com.myxlab.bustracker.View.Login.MainLoginActivity;
import io.fabric.sdk.android.Fabric;

/**
 * The type Splash activity.
 */
public class SplashActivity extends BaseActivity {

    /**
     * The Image view.
     */
    ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Answers());
        setContentView(R.layout.activity_splash);

        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

        imageView = (ImageView) findViewById(R.id.splashingBus);
        UserInstance.getInstance().setAuth(new Auth());
        UserInstance.getInstance().setQueue(Volley.newRequestQueue(getApplicationContext()));
        UserInstance.getInstance().setVolleyApp(new VolleyApp());
        UserInstance.getInstance().setUtility(new Utility());


        simpleAnimation();
        Boolean check = UserInstance.getInstance().getAuth().checkAuth(getApplication().getApplicationContext());

        if (!check) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        } else {
            UserInstance.getInstance().getVolleyApp().autoLogin(getResources().getString(R.string.url_login),getApplicationContext(),this);
        }
    }

    /**
     * Simple animation.
     */
    protected void simpleAnimation() {
        ViewAnimator.animate(imageView)
                .zoomIn().bounceIn().interpolator(new DecelerateInterpolator())
                .duration(2000)
                .start();
    }

}
