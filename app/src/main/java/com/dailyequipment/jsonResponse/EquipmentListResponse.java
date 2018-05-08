package com.dailyequipment.jsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 30-Apr-18.
 */

public class EquipmentListResponse {
    @SerializedName("Material_Id")
    @Expose
    private String materialId;
    @SerializedName("Material_Description")
    @Expose
    private String materialDescription;

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }


}
