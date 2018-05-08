package com.dailyequipment.model;

/**
 * Created by admin on 19-Apr-18.
 */

public class Equipment_ParshingModel {
    private String slNo;
    private String equipmentId;
    private String equipmentName;
    private String equipmentWoR;

    public Equipment_ParshingModel(String slNo, String equipmentId, String equipmentName, String equipmentWoR) {
        this.slNo = slNo;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentWoR = equipmentWoR;
    }

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentWoR() {
        return equipmentWoR;
    }

    public void setEquipmentWoR(String equipmentWoR) {
        this.equipmentWoR = equipmentWoR;
    }
}
