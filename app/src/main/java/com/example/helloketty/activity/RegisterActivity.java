package com.example.helloketty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;

import com.example.helloketty.R;
import com.example.helloketty.entity.ElavenUser;
import com.example.helloketty.util.FileUtil;
import com.example.helloketty.util.Utils;
import com.google.gson.Gson;

public class RegisterActivity extends ElavenActivity {

    private TextView register_userid;
    private TextView register_address;
    private TextView register_name;
    private RadioButton register_gender;
    private TextView register_phonenumber;
    private TextView register_email;
    private TextView register_region;
    private Button register_save;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.register);

        Log.i(Utils.log_page_tag, "Arrive register page");
        //Gson gson = new Gson();
        //Bundle bundle = this.getIntent().getExtras();
        //String userJson = bundle.getString("userJson");
        //elaven_user = gson.fromJson(userJson, ElavenUser.class);
        Log.i(Utils.log_info_tag, "elaven_user data: " + elavenUser.toString());

        initPage();
    }

    private void initPage() {

        initWidgetsStatus();
        register_save = (Button) findViewById(R.id.register_save);
        register_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Utils.log_info_tag, "register button clicked");
                initUserInfo();
                intentToMain();
            }
        });
    }

    private void intentToMain() {

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initWidgetsStatus() {
        register_userid = (TextView) findViewById(R.id.register_userid_text);
        register_address = (TextView) findViewById(R.id.register_address_text);

        register_name = (TextView) findViewById(R.id.register_name_text);
        register_phonenumber = (TextView) findViewById(R.id.register_phonenumber_text);
        register_email = (TextView) findViewById(R.id.register_email_text);
        register_region = (TextView) findViewById(R.id.register_region_text);

        register_userid.setText(elavenUser.getUser_id());
        register_address.setText(elavenUser.getAddress());
        register_name.setText(elavenUser.getName());
        register_phonenumber.setText(elavenUser.getPhone_number());
        register_email.setText(elavenUser.getEmail());
        register_region.setText(elavenUser.getRegion());

        if (elavenUser.getGender().equals(R.string.register_gender_male)) {
            ((RadioButton) findViewById(R.id.register_gender_male)).setChecked(true);
            ((RadioButton) findViewById(R.id.register_gender_female)).setChecked(false);
        } else {
            ((RadioButton) findViewById(R.id.register_gender_male)).setChecked(false);
            ((RadioButton) findViewById(R.id.register_gender_female)).setChecked(true);
        }
    }

    private void initUserInfo() {
        register_name = (TextView) findViewById(R.id.register_name_text);
        register_gender = (RadioButton) findViewById(
                ((RadioGroup) findViewById(R.id.register_gender_text))
                        .getCheckedRadioButtonId());
        register_phonenumber = (TextView) findViewById(R.id.register_phonenumber_text);
        register_email = (TextView) findViewById(R.id.register_email_text);
        register_region = (TextView) findViewById(R.id.register_region_text);


        elavenUser.setName(register_name.getText().toString());
        elavenUser.setGender(register_gender.getText().toString());
        elavenUser.setPhone_number(register_phonenumber.getText().toString());
        elavenUser.setEmail(register_email.getText().toString());
        elavenUser.setRegion(register_region.getText().toString());
    }


}
