package com.example.greatinternetofthings.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.adapter.MonitorItemAdapter;
import com.example.greatinternetofthings.constant.CommonVairiable;
import com.example.greatinternetofthings.constant.ConstantAssemble;
import com.example.greatinternetofthings.datastructor.MonitorDetail;

import java.util.ArrayList;
import java.util.List;

public class MonitorRecieverActivity extends AppCompatActivity {

    ListView lvMonitor;
    Toolbar toolbar;
    Button btLocalTest;
    MonitorItemAdapter adapter;
    List<MonitorDetail> monitorDetails;
    int updateTimes=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_reciever);
        lvMonitor=findViewById(R.id.lv_monitor);
        toolbar=findViewById(R.id.toolbar);
        btLocalTest=findViewById(R.id.bt_local_test);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btLocalTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateLocalData();
            }
        });

        monitorDetails=new ArrayList<>();
        if(CommonVairiable.serverSocket==null){
            Intent intent=new Intent(MonitorRecieverActivity.this,BlueToothConnectActivity.class);
            startActivityForResult(intent,ConstantAssemble.ACTIVITY_REQUEST_CODE_BLUETEETH_CONNECT);
            return;
        }
        CreateLocalData();
    }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    adapter.notifyDataSetChanged();
            }
        }
    };

    void CreateLocalData(){
        int size= ConstantAssemble.monitorTypeName.length;
        for(int index=0;index!=size;index++){
            MonitorDetail singleDetail=new MonitorDetail();
            singleDetail.setName(ConstantAssemble.monitorTypeName[index]);
            singleDetail.setValue(ConstantAssemble.monitorInitValue[index]);
            singleDetail.setState("");
            monitorDetails.add(singleDetail);
        }
        adapter=new MonitorItemAdapter(this,R.layout.item_monitor,monitorDetails);
        lvMonitor.setAdapter(adapter);
        MonitorChange monitorChange=new MonitorChange();
        monitorChange.start();
    }

    class MonitorChange extends Thread{
        @Override
        public void run() {
            try {
                while (true){
                    Log.d("TAG","thread loop:---  "+updateTimes);
                    Thread.sleep(5000);
                    UpdateMonitorState();
                    updateTimes++;
                    mHandler.sendEmptyMessage(0);
                }
            }
            catch (Exception e){
e.printStackTrace();
            }
        }
    }

    /**
    *
    * @Methodname
    * @Decription update the monitor data
    * @Params
    * @Author Alexander
    * @Createdate 2020/5/10
    */
    private void UpdateMonitorState() {
        for(MonitorDetail eachDetail:monitorDetails){
            float value=eachDetail.getValue();
            value+=((updateTimes+1)%5*2);
            eachDetail.setValue(value);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ConstantAssemble.ACTIVITY_REQUEST_CODE_BLUETEETH_CONNECT){
            if(resultCode==ConstantAssemble.ACTIVITY_RESULT_CODE_SUCCESS){
                CreateLocalData();
            }
        }
    }
}
