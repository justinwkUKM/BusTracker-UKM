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

import com.driverapp.R;
import com.github.florent37.viewanimator.ViewAnimator;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;

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

                .translationX(2000, -200)
                .interpolator(new DecelerateInterpolator())
                .duration(2000)

                .thenAnimate(imageView)
                .translationX(-200, 0)
                .interpolator(new DecelerateInterpolator())
                .duration(1000)

                .start();
    }

}
