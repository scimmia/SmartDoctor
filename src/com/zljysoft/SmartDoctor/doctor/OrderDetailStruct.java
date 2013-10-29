package com.zljysoft.SmartDoctor.doctor;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-28
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public class OrderDetailStruct {
    String medicine;
    String dosage;

    public OrderDetailStruct(String medicine, String dosage) {
        this.medicine = medicine;
        this.dosage = dosage;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getMedicine() {

        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
