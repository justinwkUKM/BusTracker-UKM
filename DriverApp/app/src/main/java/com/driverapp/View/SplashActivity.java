package com.driverapp.View;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.driverapp.BaseActivity;
import com.driverapp.R;
import com.github.florent37.viewanimator.ViewAnimator;

public class SplashActivity extends BaseActivity {

    ImageView imageView;
TextView tvSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

        imageView = (ImageView) findViewById(R.id.splashingBus);
        tvSplash = (TextView) findViewById(R.id.splashText);

        simpleAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 6000);
    }

    protected void simpleAnimation() {
        ViewAnimator.animate(imageView)
                .translationY(0, 0)
                .alpha(0, 1)
                .translationX(2000, -250)
                .interpolator(new DecelerateInterpolator())
                .duration(2000)
                .thenAnimate(imageView)
                .translationX(-250, 0)
                .interpolator(new DecelerateInterpolator())
                .bounce()
                .andAnimate(tvSplash)
                .newsPaper()
                .duration(1500)
                .start();
    }

}
