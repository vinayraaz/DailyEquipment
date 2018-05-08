package com.dailyequipment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dailyequipment.api.ApiClient;
import com.dailyequipment.api.ApiInterface;
import com.dailyequipment.jsonResponse.EquipmentListResponse;
import com.dailyequipment.jsonResponse.EquipmentNotWorkResponse;
import com.dailyequipment.model.Equipment_ParshingModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 19-Apr-18.
 */

public class Working_Equipment_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    DBAdapter db;
    Spinner spinner;
    RecyclerView rvContacts;
    List<Equipment_ParshingModel> equipment_parshingModels = new ArrayList<>();
    List<String> Working_Equipment = new ArrayList<>();
    FloatingActionButton Submit;
    TextView tv_equipment;
    TextView tv_currentdate;


    ApiInterface apiService;
    List<String> Equipment_Name = new ArrayList<>();
    List<String> Equipment_Meterial_id = new ArrayList<>();
    String EquipmentName, EquipmentNameId;
    String formattedDate, V_careID = "2", Amublance_number = "13", district = "Bangalore", city = "Bangalore", base_location = "Madiwala", EMTs_name = "XYZ", Emts_Designation = "PQR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_equipment_checklist);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        db = new DBAdapter(Working_Equipment_Activity.this);
        tv_equipment = (TextView) findViewById(R.id.equipment_tv);
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        spinner = (Spinner) findViewById(R.id.spinner_search);
        Submit = (FloatingActionButton) findViewById(R.id.fab);

        tv_equipment.setText("Working Equipment");
        tv_currentdate = (TextView) findViewById(R.id.tv_date);
        Calendar c_date = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c_date.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c_date.getTime());
        tv_currentdate.setText(formattedDate);

        Equipment_Name.clear();
        Equipment_Meterial_id.clear();
        Equipment_Name.add("Select Equipment");
        spinner.setOnItemSelectedListener(this);
        Submit.setOnClickListener(this);
        checkInternetConenction();

     //   EquipmentList();


    }


    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            ProgressDialog();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            InternetError();
            return false;
        }
        return false;
    }

    private void InternetError() {
        final Dialog alertD = new Dialog(Working_Equipment_Activity.this);
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
                Intent i2 = new Intent(Working_Equipment_Activity.this, MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
    }

    private void ProgressDialog() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(Working_Equipment_Activity.this, "Please wait ...", "", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EquipmentList();
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
                ringProgressDialog.dismiss();
            }

        }).start();
    }

    private void EquipmentList() {
        Call<List<EquipmentListResponse>> call = apiService.getEquipmentList();
        call.enqueue(new Callback<List<EquipmentListResponse>>() {
            @Override
            public void onResponse(Call<List<EquipmentListResponse>> call, Response<List<EquipmentListResponse>> response) {
                if (response.isSuccessful()) {
                    int count = response.body().size();
                    //System.out.println("Count**" + count);
                    for (int i = 0; i < response.body().size(); i++) {
                        Equipment_Name.add(response.body().get(i).getMaterialDescription());
                        Equipment_Meterial_id.add(response.body().get(i).getMaterialId());
                        ArrayAdapter<String> pcad = new ArrayAdapter<String>(Working_Equipment_Activity.this, android.R.layout.simple_dropdown_item_1line, Equipment_Name);
                        spinner.setAdapter(pcad);

                    }
                }

            }

            @Override
            public void onFailure(Call<List<EquipmentListResponse>> call, Throwable t) {
                Log.i("ERROR", t.toString());
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        EquipmentName = String.valueOf(parent.getItemAtPosition(position));
        int select_equi_int = spinner.getSelectedItemPosition();

        EquipmentNameId = Equipment_Meterial_id.get(select_equi_int).toString();
        Log.i("EquipmentNameId", "" + EquipmentNameId);
        Log.i("EquipmentName", "" + EquipmentName);

        if (!EquipmentName.equals("Select Equipment")) {

            EquipmentWorkAPI();
            //EquipmentListAdd();
        }

    }

    private void EquipmentWorkAPI() {
        Call<EquipmentNotWorkResponse> call = apiService.getEquipmentNoW("null", "null", formattedDate, V_careID, Amublance_number, district, city, base_location, EMTs_name, Emts_Designation);
        call.enqueue(new Callback<EquipmentNotWorkResponse>() {
            @Override
            public void onResponse(Call<EquipmentNotWorkResponse> call, Response<EquipmentNotWorkResponse> response) {
                Toast.makeText(Working_Equipment_Activity.this, "Equipment is working ", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {

                    Log.i("Response***", "" + response.body().getCode().toString());
                    Log.i("Response***", "" + response.body().getMessage().toString());
                }

            }

            @Override
            public void onFailure(Call<EquipmentNotWorkResponse> call, Throwable t) {
                Log.i("ERROR", t.toString());

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent i2 = new Intent(Working_Equipment_Activity.this, MainActivity.class);
                startActivity(i2);
                finish();
                break;
        }

    }
}
