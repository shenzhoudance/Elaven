package com.example.helloketty.userinfo;

import org.elastos.carrier.AbstractCarrierHandler;
import org.elastos.carrier.Carrier;
import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.UserInfo;

import  org.elastos.carrier.exceptions.ElastosException;

import com.example.helloketty.entity.ElavenUser;
import com.example.helloketty.entity.ResearchList;
import com.example.helloketty.util.JsonFileLoader;
import  com.example.helloketty.util.Synchronizer;
import  com.example.helloketty.util.Utils;
import com.google.gson.Gson;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class ElavenUserInfoHelper {

    public Carrier carrierInst;
    private Context context;
    //private String userid;
    //private String address;

    public ElavenUserInfoHelper(Context context) {
        this.context = context;

        try {
            File file = context.getFilesDir();
            String path = file.getAbsolutePath();

            TestOptions options = new TestOptions(path);
            TestHandler handler = new TestHandler();

            //1.1获得Carrier的实例
            carrierInst = Carrier.getInstance(options, handler);

            //1.2获得Carrier的地址
            //address = carrierInst.getAddress();
            //Log.i(Utils.log_info_tag, "address: " + address);

            //1.3获得Carrier的用户ID
            //userid = carrierInst.getUserId();
            //Log.i(Utils.log_info_tag, "userID: " + userid);

        } catch (ElastosException e) {
            e.printStackTrace();
        }
    }

    static class TestHandler extends AbstractCarrierHandler {

        Synchronizer synch = new Synchronizer();
        String from;
        ConnectionStatus friendStatus;
        String CALLBACK="call back";

        public void onReady(Carrier carrier) {
            synch.wakeup();
        }

        public void onFriendConnection(Carrier carrier, String friendId, ConnectionStatus status) {

            Log.i(CALLBACK,"friendid:" + friendId + "connection changed to: " + status);
            from = friendId;
            friendStatus = status;
            if (friendStatus == ConnectionStatus.Connected)
                synch.wakeup();
        }

        //2.2 通过好友验证
        public void onFriendRequest(Carrier carrier, String userId, UserInfo info, String hello) {
            try {

                if (hello.equals("auto-accepted")) {
                    carrier.AcceptFriend(userId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        //3.2 接受好友信息
        public void onFriendMessage(Carrier carrier,String fromId, String message) {

            Log.i(CALLBACK,"address:" + fromId + "connection changed to: " + message);
        }

    }
}
