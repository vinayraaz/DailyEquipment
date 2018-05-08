package com.dailyequipment.api;

import com.dailyequipment.jsonResponse.EquipmentListResponse;
import com.dailyequipment.jsonResponse.EquipmentNotWorkResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vinay on 12/8/2017.
 */

public interface ApiInterface {
    //All Equipment List
    @GET("DAILY_EQUIPMENT")
    Call<List<EquipmentListResponse>> getEquipmentList();
    // Not Working API Calling
    @GET("Insert_equipment")
    Call<EquipmentNotWorkResponse> getEquipmentNoW(@Query("e_not_working")String equ_name,@Query("Equiments_id")String equ_name_id,@Query("date")String select_date,
                                                   @Query("V_care_id")String v_care_id,@Query("Amublance_number")String ambulance_name,@Query("district")String dist,
                                                   @Query("city")String city,@Query("base_location")String b_location,@Query("EMTs_name")String emt_name,
                                                   @Query("Emts_Designation")String emt_distination);

    //http://paypre.info/Insert_equipment?e_not_working=xx&Equiments_id=2&date=2019-02-12&V_care_id=2&Amublance_number=13&district=ss&city=ss&base_location=ss&EMTs_name=ss&Emts_Designation=ss


}


