package com.septems.avinash.ngrid.utils;

import android.content.Context;
import android.nfc.NdefMessage;

import java.util.HashMap;

public class AppProfile {
    private volatile static AppProfile instance;
    private HashMap<String, String> Params = new HashMap<String, String>();
    private NdefMessage ncfMessage;
    private String CurrentUserName;
    private String CurrentUserI;
    private String CurrentEmployeeI;
    private String UserId;
    private String Password;
    private String DeviceId;


    private com.google.firebase.auth.FirebaseUser FirebaseUser;
    private String AuthId;

    public String getLoginID() {
        return LoginID;
    }

    public void setLoginID(String loginID) {
        LoginID = loginID;
    }

    private String LoginID;
    private String ApplicationId = "1";
    private boolean SignedIn = false;
    private String urlBase;

    public void setNFCMessage(NdefMessage nfcMessage) {
        this.ncfMessage = nfcMessage;
    }

    public NdefMessage getNFCMessage() {
        return ncfMessage;
    }

    public Object getValue(String key) {
        if (Params.containsKey(key)) return Params.get(key);
        else return null;
    }

    public void addValue(String key, String value) {
        Params.put(key, value);
    }

    public String getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(String argApplicationId) {
        ApplicationId = argApplicationId;
    }

    public String getCurrentUserName() {
        return CurrentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        CurrentUserName = currentUserName;
    }

    public String getCurrentUserI() {
        return CurrentUserI;
    }

    public void setCurrentUserI(String currentUserI) {
        CurrentUserI = currentUserI;
    }

    public String getCurrentEmployeeI() {
        return CurrentEmployeeI;
    }

    public void setCurrentEmployeeI(String currentEmployeeI) {
        CurrentEmployeeI = currentEmployeeI;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getAuthId() {
        return AuthId;
    }

    public com.google.firebase.auth.FirebaseUser getFirebaseUser() {
        return FirebaseUser;
    }

    public void setFirebaseUser(com.google.firebase.auth.FirebaseUser firebaseUser) {
        FirebaseUser = firebaseUser;
    }

    public void setAuthId(String authId) {
        AuthId = authId;
        if (authId != null && authId.length() > 0) {
            SignedIn = true;
        }
    }

    public boolean IsSigned() {
        return SignedIn;
    }

    public static AppProfile getInstance() {
        if (instance == null) {
            synchronized (AppProfile.class) {
                if (instance == null) {
                    instance = new AppProfile();
                }
            }
        }
        return instance;
    }

    private AppProfile() {
    }

    public void onDestroy(Context context) {
        instance = null;
    }
}
