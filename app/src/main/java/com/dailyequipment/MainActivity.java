package com.dailyequipment;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout Relative_Layout1, Relative_Layout2;
    TextView tv_currentdate;
    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Relative_Layout1 = (RelativeLayout) findViewById(R.id.relative1);
        Relative_Layout2 = (RelativeLayout) findViewById(R.id.relative2);
        tv_currentdate = (TextView) findViewById(R.id.tv_date);
        Relative_Layout1.setOnClickListener(this);
        Relative_Layout2.setOnClickListener(this);

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpledateformat.format(calander.getTime());
        tv_currentdate.setText(Date);
        //checkInternetConenction();
        // Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
    }

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
           // Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(this, " Not Internet Connected ", Toast.LENGTH_LONG).show();
           // InternetError();
            return false;
        }
        return false;
    }

    private void InternetError() {
        final Dialog alertD = new Dialog(MainActivity.this);
        alertD.setCancelable(false);
        alertD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertD.setContentView(R.layout.internet_activity);

        alertD.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        alertD.show();
        final TextView InternetCheck = (TextView) alertD.findViewById(R.id.internet_error);
        InternetCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.cancel();
                Intent i2 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative1:
                Intent notworking = new Intent(MainActivity.this, NotWorking_Equipment_Activity.class);
                startActivity(notworking);
                finish();
                break;
            case R.id.relative2:
                Intent workiing = new Intent(MainActivity.this, Working_Equipment_Activity.class);
                startActivity(workiing);
                finish();
                break;


        }
    }
}
