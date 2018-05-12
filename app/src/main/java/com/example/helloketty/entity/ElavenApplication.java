package com.example.helloketty.entity;

import android.app.Application;

import com.example.helloketty.userinfo.ElavenUserInfoHelper;
import com.example.helloketty.util.Utils;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.Log;
import org.elastos.carrier.exceptions.ElastosException;

public class ElavenApplication extends Application {
    private static Carrier elavenCarrier;
    private static ElavenUser elavenUser;

    public static Carrier getElavenCarrier() {
        return elavenCarrier;
    }

    public static void setElavenCarrier(Carrier elavenCarrier) {
        ElavenApplication.elavenCarrier = elavenCarrier;
    }

    public static ElavenUser getElavenUser() {
        return elavenUser;
    }

    public static void setElavenUser(ElavenUser elavenUser) {
        ElavenApplication.elavenUser = elavenUser;
    }

    public ElavenApplication(){
        Log.i(Utils.log_info_tag, "Init elaven application data");
        initCarrier();
    }

    void initCarrier(){
        if (elavenCarrier != null)
            return;

        try {
            ElavenUserInfoHelper helper = new ElavenUserInfoHelper(ElavenApplication.this);
            elavenCarrier = helper.carrierInst;
            elavenUser.setUser_id(elavenCarrier.getUserId());
            elavenUser.setAddress(elavenCarrier.getAddress());

        } catch (ElastosException e) {
            e.printStackTrace();
        }
    }
}
