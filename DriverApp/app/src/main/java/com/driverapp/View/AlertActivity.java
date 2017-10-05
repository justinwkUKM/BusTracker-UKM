package com.driverapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.driverapp.BaseActivity;
import com.driverapp.Model.UserInstance;
import com.driverapp.R;


/**
 * Created by MyXLab on 30/1/2017.
 * Activity for Sending Alerts.
 */


public class AlertActivity extends AppCompatActivity {


    EditText etSubject, etMessage;
    Button btSubmit;
    AlertActivity alertActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }

        etSubject = (EditText) findViewById(R.id.etAlertSubject);
        etMessage = (EditText) findViewById(R.id.etAlertMessage);

    }

    public void submitAlertButton(View view){
        String subject = etSubject.getText().toString();
        String message = etMessage.getText().toString();
        String reportType = "Testing";
        int reporterID = 35555;
        UserInstance.getInstance().getVolleyApp().submitAlert(getString(R.string.url_alert),subject,message,reportType,reporterID, this);
        etSubject.setText("");
        etMessage.setText("");
    }


    public void setEmpty(){
        //etSubject.setText("");
        //etMessage.setText("");
        //finish();
    }
}
