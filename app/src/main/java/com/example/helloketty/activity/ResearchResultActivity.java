package com.example.helloketty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.helloketty.R;
import com.example.helloketty.adapter.ResearchItemListAdapter;
import com.example.helloketty.entity.QuestionListBean;
import com.example.helloketty.entity.ResearchList;
import com.example.helloketty.util.JsonFileLoader;
import com.google.gson.Gson;

/**
 * Created by HelloKetty on 2018/5/3.
 */

public class ResearchResultActivity extends Activity{
    private ListView listView_searchresult;
    private TextView txt_title;
    private ResearchList the_Question_list;
    private final static int MY_MESSAGE = 0X01;
    Gson gson = new Gson();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MY_MESSAGE:
                    ResearchItemListAdapter messageAdapter = new ResearchItemListAdapter(getBaseContext(), the_Question_list.getQuesitions());
                    listView_searchresult.setAdapter(messageAdapter);
                    listView_searchresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_researchlist_detail);
        // 初始化数据
        initDate();
    }

    private void initDate() {
        String infoString = getIntent().getStringExtra("itemInfo");
        String jsonResult = JsonFileLoader.getResearchItemListJson(this.getBaseContext());
        the_Question_list = gson.fromJson(infoString, ResearchList.class);
        // 加载布局
        initView();
    }

    private void initView( ) {
        listView_searchresult = (ListView) findViewById(R.id.listView_01);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText(the_Question_list.getTitle());
        handler.sendEmptyMessage(MY_MESSAGE);
    }
}
