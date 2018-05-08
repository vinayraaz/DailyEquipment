package com.dailyequipment.parshingModel;

/**
 * Created by admin on 30-Apr-18.
 */

public class EquipmentModel {
    private String materialId;
    private String materialDescription;

   /* public EquipmentModel() {

    }*/

    public EquipmentModel(String materialId, String materialDescription) {
        this.materialId = materialId;
        this.materialDescription = materialDescription;
    }

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
