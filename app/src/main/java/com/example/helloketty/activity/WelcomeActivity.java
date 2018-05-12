package com.example.helloketty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.helloketty.R;
import com.example.helloketty.entity.ElavenUser;
import com.example.helloketty.userinfo.ElavenUserInfoHelper;
import com.example.helloketty.util.FileUtil;
import com.example.helloketty.util.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;

import static com.example.helloketty.util.Utils.default_file_directory;
import static com.example.helloketty.util.Utils.log_info_tag;

/**
 * Created by Houfa.Zhou on 2018/5/10.
 */

public class WelcomeActivity extends ElavenActivity{

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.welcome);

        Log.i(Utils.log_page_tag,"Arrive welcome page");
        initPage();
    }

    private  void  initPage(){
        initLogin();
    }

    private void initLogin() {

        Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
        //ElavenUser user = initUserInfo();
        //Bundle bundle = new Bundle();
        //bundle.putString("userJson", user.toString());
        //Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
        //intent.putExtras(bundle);
    }

    /*
    private void initLogin() {

        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserIdAndAddress();
                handler.sendEmptyMessageDelayed(0x01, 2000);
            }
        });

    }
    * */

    /*
    private void initRegister() {
        register_button = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserIdAndAddress();
                handler.sendEmptyMessageDelayed(0x01, 2000);
            }
        });
    }
*/
    /*
    private ElavenUser initUserInfo (){

        File file = new File(default_file_directory, Utils.userinfo_file_name);
        ElavenUser user = new ElavenUser();
        if (!file.exists()) {
            String data = createUserIdAndAddress();
            String[] datas = data.split(",");
            user.setUser_id(datas[0]);
            user.setAddress(datas[1]);
        }
        else {
            Log.i(log_info_tag, "need load file datas");
            Gson gson = new Gson();
            String data = FileUtil.getFile(Utils.userinfo_file_name);
            user = gson.fromJson(data, ElavenUser.class);
        }

        return user;
    }

    private String createUserIdAndAddress1 (){
        ElavenUserInfoHelper helper = new ElavenUserInfoHelper(WelcomeActivity.this);
        String userid = helper.getUserid();
        String address = helper.getAddress();

        return userid + ","+ address;
    }

*/

}
