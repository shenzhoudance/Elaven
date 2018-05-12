package com.example.helloketty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.helloketty.R;
import com.example.helloketty.adapter.ResearchResultAdapter;
import com.example.helloketty.entity.ElavenUser;
import com.example.helloketty.entity.Quesition;
import com.example.helloketty.entity.QuestionListBean;
import com.example.helloketty.entity.ResearchList;
import com.example.helloketty.entity.ResearchListBean;
import com.example.helloketty.userinfo.TestOptions;
import com.example.helloketty.util.JsonFileLoader;
import com.example.helloketty.util.Utils;
import com.google.gson.Gson;

import org.elastos.carrier.AbstractCarrierHandler;
import org.elastos.carrier.Carrier;
import org.elastos.carrier.ConnectionStatus;
import org.elastos.carrier.UserInfo;
import org.elastos.carrier.exceptions.ElastosException;

import com.example.helloketty.util.Synchronizer;

public class MainActivity extends ElavenActivity {
    public static int SEND_FRIEND_MESSAGE = 0x02;
    public final static int MY_MESSAGE = 0X01;
    private ListView listView_searchresult;
    private ResearchListBean the_research_list;
    private ElavenUser elaven_user = new ElavenUser();
    private String result = "";
    private ResearchResultAdapter messageAdapter;

    Carrier carrierInst = null;
    String carrierAddr = null;
    String carrierUserID = null;
    Gson gson = new Gson();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MY_MESSAGE:
                    messageAdapter = new ResearchResultAdapter(getBaseContext(), the_research_list.getLists());
                    listView_searchresult.setAdapter(messageAdapter);
                    listView_searchresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getBaseContext(), ResearchResultActivity.class);
                            intent.putExtra("itemInfo", gson.toJson(the_research_list.getLists().get(position)));
                            startActivity(intent);
                        }
                    });
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_FRIEND_MESSAGE) {
            if (data != null) {
                result = data.getExtras().getString("data_research");
                Log.e("onActivityResultMain", result);
                ResearchList researchList = gson.fromJson(result, ResearchList.class);
                the_research_list.getLists().add(researchList);
                messageAdapter.notifyDataSetChanged();
                try {
                    carrierInst.sendFriendMessage("M9MVQMg55E9jiY3KUzXA6Y9QyFXtQ5osmyRPxTD2eiwJFcNpLgUA", result);
                } catch (ElastosException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(Utils.log_page_tag, "Arrive main page");

        // 初始化数据
        initDate();
        // 接受消息
        TestOptions options = new TestOptions(Utils.getAppPath(this.getApplicationContext()));
        TestHandler handler = new TestHandler();

        try {
            carrierInst = Carrier.getInstance(options, handler);
            carrierAddr = elaven_user.getAddress();
            carrierUserID = elaven_user.getUser_id();
        } catch (ElastosException e) {
            e.printStackTrace();
        }

    }

    private void initDate() {
        String jsonResult = JsonFileLoader.getResearchListJson(this.getBaseContext());
        the_research_list = gson.fromJson(jsonResult, ResearchListBean.class);
        // 加载布局
        initView();
    }

    private void initView() {
        listView_searchresult = (ListView) findViewById(R.id.listView_searchresult);
        handler.sendEmptyMessage(MY_MESSAGE);

        // 提交按钮
        TextView button = (TextView) findViewById(R.id.tv_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreatResearchActivity.class);
                startActivityForResult(intent, SEND_FRIEND_MESSAGE);
            }
        });
    }

    static class TestHandler extends AbstractCarrierHandler {
        Synchronizer synch = new Synchronizer();
        String from;
        ConnectionStatus friendStatus;
        String CALLBACK = "call back";

        public void onReady(Carrier carrier) {
            synch.wakeup();
        }

        public void onFriendConnection(Carrier carrier, String friendId, ConnectionStatus status) {
            Log.i(CALLBACK, "friendid:" + friendId + "connection changed to: " + status);
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
        public void onFriendMessage(Carrier carrier, String fromId, String message) {
            Log.i(CALLBACK, "address:" + fromId + "connection changed to: " + message);
        }

    }

}
