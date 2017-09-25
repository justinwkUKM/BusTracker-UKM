package com.myxlab.bustracker.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.myxlab.bustracker.R;
import com.myxlab.bustracker.View.Login.MainLoginActivity;

public class MaintainaceActivity extends AppCompatActivity {

    private android.widget.TextView tvMaintainMessage;
    private android.widget.TextView tvBtnSkip;
    private android.widget.TextView tvUsernamePassword;
    private android.widget.RelativeLayout activitymaintainace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainace);
        //changing statusbar color

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

        this.activitymaintainace = (RelativeLayout) findViewById(R.id.activity_maintainace);

        this.tvUsernamePassword = (TextView) findViewById(R.id.tvUsernamePassword);
        tvUsernamePassword.setSelected(true);

        tvMaintainMessage = (TextView) findViewById(R.id.tvMaintainMessage);
        simpleBlinkAnim(tvMaintainMessage);

        this.tvBtnSkip = (TextView) findViewById(R.id.tvBtnSkip);


        tvBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaintainaceActivity.this, MainLoginActivity.class));
            }
        });

    }

    private void simpleBlinkAnim(View itemView) {
        ViewAnimator.animate(itemView)
                .fadeIn().interpolator(new DecelerateInterpolator())
                .duration(2500)
                .start();
    }
}
