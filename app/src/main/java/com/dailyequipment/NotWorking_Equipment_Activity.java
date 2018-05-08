package com.dailyequipment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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

import com.dailyequipment.adapter.EquipmentRecyclerAdapter;
import com.dailyequipment.api.ApiClient;
import com.dailyequipment.api.ApiInterface;
import com.dailyequipment.jsonResponse.EquipmentListResponse;
import com.dailyequipment.jsonResponse.EquipmentNotWorkResponse;
import com.dailyequipment.parshingModel.EquipmentModel;

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

public class NotWorking_Equipment_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    DBAdapter db;
    Spinner spinner;
    RecyclerView rvContacts;

    String EquipmentName, EquipmentNameId, EquipmentWoR = "Not_working";
    EquipmentRecyclerAdapter equipmentRecyclerAdapter;
    FloatingActionButton Submit;
    TextView tv_equipment;
    TextView tv_currentdate;

    ApiInterface apiService;
    List<String> Equipment_Name = new ArrayList<>();
    List<String> Equipment_Meterial_id = new ArrayList<>();

    public List<String> Select_Equ_Name = new ArrayList<>();
    public List<String> Select_Equ_Name_ID = new ArrayList<>();

    List<EquipmentModel> equipmentModels = new ArrayList<>();

    String formattedDate, V_careID = "2", Amublance_number = "13", district = "Bangalore", city = "Bangalore", base_location = "Madiwala", EMTs_name = "XYZ", Emts_Designation = "PQR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_equipment_checklist);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        tv_equipment = (TextView) findViewById(R.id.equipment_tv);
        db = new DBAdapter(NotWorking_Equipment_Activity.this);
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        spinner = (Spinner) findViewById(R.id.spinner_search);
        Submit = (FloatingActionButton) findViewById(R.id.fab);

        tv_equipment.setText("Not Working/ Available Equipment");

        tv_currentdate = (TextView) findViewById(R.id.tv_date);
        Calendar c_date = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c_date.getTime());
        tv_currentdate.setText(formattedDate);
        Equipment_Name.clear();
        Equipment_Meterial_id.clear();
        Equipment_Name.add("Select Equipment");
        Equipment_Meterial_id.add("MC0000");
        checkInternetConenction();

        spinner.setOnItemSelectedListener(this);
        Submit.setOnClickListener(this);
    }

    private boolean checkInternetConenction() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
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
        final Dialog alertD = new Dialog(NotWorking_Equipment_Activity.this);
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
                Intent i2 = new Intent(NotWorking_Equipment_Activity.this, MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
    }

    private void ProgressDialog() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(NotWorking_Equipment_Activity.this, "Please wait ...", "", true);
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
                    System.out.println("Count**" + count);
                    for (int i = 0; i < response.body().size(); i++) {
                        Equipment_Name.add(response.body().get(i).getMaterialDescription());
                        Equipment_Meterial_id.add(response.body().get(i).getMaterialId());
                        ArrayAdapter<String> pcad = new ArrayAdapter<String>(NotWorking_Equipment_Activity.this, android.R.layout.simple_dropdown_item_1line, Equipment_Name);
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

            EquipmentNotWorkAPI();
            EquipmentListAdd();
        }


    }

    private void EquipmentListAdd() {

        Select_Equ_Name.add(EquipmentName);
        Select_Equ_Name_ID.add(EquipmentNameId);

        loadlistMethod();
    }

    private void loadlistMethod() {
        equipmentRecyclerAdapter = new EquipmentRecyclerAdapter(this, Select_Equ_Name, Select_Equ_Name_ID);
        rvContacts.setLayoutManager(new GridLayoutManager(NotWorking_Equipment_Activity.this, 2));
        rvContacts.setHasFixedSize(true);
        rvContacts.setAdapter(equipmentRecyclerAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void EquipmentNotWorkAPI() {
        Call<EquipmentNotWorkResponse> call = apiService.getEquipmentNoW(EquipmentName, EquipmentNameId, formattedDate, V_careID, Amublance_number, district, city, base_location, EMTs_name, Emts_Designation);
        call.enqueue(new Callback<EquipmentNotWorkResponse>() {
            @Override
            public void onResponse(Call<EquipmentNotWorkResponse> call, Response<EquipmentNotWorkResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NotWorking_Equipment_Activity.this, "This equipment is not working", Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent i2 = new Intent(NotWorking_Equipment_Activity.this, MainActivity.class);
                startActivity(i2);
                finish();
                break;
        }

    }
}
