package com.breakfast.library.data.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jliang on 7/18/2017.
 */

public class UserModel implements Parcelable {
    private String sessionId;
    private String ownerId;
    private String orgId;
    private String orgName;
    private String defaultOrgId;
    private String ownerName;
    private String shift;
    private String businessDate;
    private String employeeName;

    protected UserModel(Parcel in) {
        sessionId = in.readString();
        ownerId = in.readString();
        orgId = in.readString();
        orgName = in.readString();
        defaultOrgId = in.readString();
        ownerName = in.readString();
        shift = in.readString();
        businessDate = in.readString();
        employeeName = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDefaultOrgId() {
        return defaultOrgId;
    }

    public void setDefaultOrgId(String defaultOrgId) {
        this.defaultOrgId = defaultOrgId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sessionId);
        dest.writeString(ownerId);
        dest.writeString(orgId);
        dest.writeString(orgName);
        dest.writeString(defaultOrgId);
        dest.writeString(ownerName);
        dest.writeString(shift);
        dest.writeString(businessDate);
        dest.writeString(employeeName);
    }
}
