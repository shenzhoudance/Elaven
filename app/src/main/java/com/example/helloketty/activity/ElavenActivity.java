package com.example.helloketty.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.helloketty.entity.ElavenUser;
import com.example.helloketty.userinfo.ElavenUserInfoHelper;
import com.example.helloketty.util.Utils;

import org.elastos.carrier.Carrier;
import org.elastos.carrier.Log;
import org.elastos.carrier.exceptions.ElastosException;

public class ElavenActivity extends Activity {

    public static Carrier elavenCarrier;
    public static ElavenUser elavenUser;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        Log.i(Utils.log_info_tag, "Arrive elaven base page");
        initCarrier();
    }

    void initCarrier(){
        if (elavenCarrier != null)
            return;

        try {
            Log.i(Utils.log_info_tag, "init carrier ...");
            ElavenUserInfoHelper helper = new ElavenUserInfoHelper(ElavenActivity.this);
            elavenCarrier = helper.carrierInst;
            elavenUser = new ElavenUser();
            elavenUser.setUser_id(elavenCarrier.getUserId());
            elavenUser.setAddress(elavenCarrier.getAddress());

        } catch (ElastosException e) {
            e.printStackTrace();
        }
    }
}
