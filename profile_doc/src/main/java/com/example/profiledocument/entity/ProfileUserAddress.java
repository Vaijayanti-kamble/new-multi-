package com.example.profiledocument.entity;

import com.google.cloud.firestore.annotation.PropertyName;

public class ProfileUserAddress {

    private String houseNo;
    private String roadName;
    private String area;
    private String district;
    private String state;
    private String pincode;

    public ProfileUserAddress() {}

    @PropertyName("house_no")
    public String getHouseNo() {
        return houseNo;
    }

    @PropertyName("house_no")
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    @PropertyName("road_name")
    public String getRoadName() {
        return roadName;
    }

    @PropertyName("road_name")
    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    @PropertyName("area")
    public String getArea() {
        return area;
    }

    @PropertyName("area")
    public void setArea(String area) {
        this.area = area;
    }

    @PropertyName("district")
    public String getDistrict() {
        return district;
    }

    @PropertyName("district")
    public void setDistrict(String district) {
        this.district = district;
    }

    @PropertyName("state")
    public String getState() {
        return state;
    }

    @PropertyName("state")
    public void setState(String state) {
        this.state = state;
    }

    @PropertyName("pincode")
    public String getPincode() {
        return pincode;
    }

    @PropertyName("pincode")
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
