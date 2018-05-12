package com.example.helloketty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.helloketty.R;
import com.example.helloketty.adapter.ResearchItemListAdapter;
import com.example.helloketty.entity.Quesition;
import com.example.helloketty.entity.QuestionListBean;
import com.example.helloketty.entity.ResearchList;
import com.example.helloketty.entityutil.QuestionType;
import com.example.helloketty.userinfo.TestOptions;
import com.example.helloketty.util.Synchronizer;
import com.example.helloketty.util.Utils;
import com.google.gson.Gson;

import org.elastos.carrier.AbstractCarrierHandler;
import org.elastos.carrier.Carrier;
import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.UserInfo;
import org.elastos.carrier.exceptions.ElastosException;

import java.util.ArrayList;

/**
 * Created by HelloKetty on 2018/5/6.
 */

public class CreatResearchActivity extends Activity {
    private TextView buttonCreatItem;
    private TextView txt_sendtoFriend;
    private EditText ed_Title;
    private ListView question_list;
    public static int MESSAGECODE = 10;
    private ResearchList researchList = new ResearchList();
    private QuestionListBean questions = new QuestionListBean();
    private Gson gson = new Gson();
    private ResearchItemListAdapter adapter;
    private String result = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MESSAGECODE) {
            if (data != null) {
                result = data.getExtras().getString("data");
                Quesition questionItem = gson.fromJson(result, Quesition.class);
                questions.getList().add(questionItem);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_research);
        initView();
    }

    public void initView() {
        buttonCreatItem = (TextView) findViewById(R.id.txt_creatItem);
        txt_sendtoFriend = (TextView) findViewById(R.id.txt_sendtoFriend);
        ed_Title = (EditText) findViewById(R.id.edit_title);
        question_list = (ListView) findViewById(R.id.question_list);
        buttonCreatItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreatQuestionActivity.class);
                startActivityForResult(intent, MESSAGECODE);
            }
        });
        txt_sendtoFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Intent intent = new Intent();
                researchList.setQuesitions(questions.getList());
                researchList.setTitle(ed_Title.getText().toString());
                researchList.setSummary(ed_Title.getText().toString());

                intent.putExtra("data_research", gson.toJson(researchList));
                setResult(MainActivity.SEND_FRIEND_MESSAGE, intent);
            }
        });
        adapter = new ResearchItemListAdapter(this.getBaseContext(), questions.getList());
        question_list.setAdapter(adapter);
    }
}
