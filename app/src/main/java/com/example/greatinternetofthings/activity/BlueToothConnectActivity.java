package com.example.greatinternetofthings.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.adapter.ShowDeviceAdpater;
import com.example.greatinternetofthings.constant.CommonVairiable;
import com.example.greatinternetofthings.datastructor.BluetoothItem;
import com.example.greatinternetofthings.interfaces.OnRLItemClicklistener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BlueToothConnectActivity extends AppCompatActivity {

    TextView tvConnectState;
    Switch swBluetooth;
    LinearLayout llOperation;
    RecyclerView rlSearchedDevice;
    BluetoothAdapter bluetoothAdapter;
    ShowDeviceAdpater deviceAdpater;
    Dialog dialog;
    List<BluetoothItem> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth_connect);
        tvConnectState = findViewById(R.id.tv_connect_state);
        swBluetooth = findViewById(R.id.sw_bluetooth);
        llOperation = findViewById(R.id.ll_bluetooth_operation);
        rlSearchedDevice = findViewById(R.id.rl_searched_device);

        List<BluetoothItem> items=new ArrayList<>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(BlueToothConnectActivity.this, "该设备不支持蓝牙!", Toast.LENGTH_LONG).show();
            finish();
        }

        swBluetooth.setChecked(bluetoothAdapter.isEnabled());
        if (swBluetooth.isChecked()) {
            llOperation.setVisibility(View.VISIBLE);
            SearchMatchedDevices();
        } else {
            llOperation.setVisibility(View.INVISIBLE);
        }
        swBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    bluetoothAdapter.enable();
                    llOperation.setVisibility(View.VISIBLE);
                    mHandler.sendEmptyMessage(0);
                } else {
                    bluetoothAdapter.disable();
                    llOperation.setVisibility(View.INVISIBLE);
                }
            }
        });

        dialog=new Dialog(BlueToothConnectActivity.this);
        dialog.setCancelable(false);
    }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    items.clear();
                    SearchMatchedDevices();
                    break;
                case 1:
                    Toast.makeText(BlueToothConnectActivity.this, "连接成功!", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 2:
                    dialog.show();
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            CancelConnect();
                        }
                    });
                    break;
                    default:
            }
        }
    };

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action!=null&&action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothItem item = new BluetoothItem();
                items.add(item);
                deviceAdpater.notifyDataSetChanged();
            }
        }
    };

    /***
     * when the switch is on,the device searching is begin
     */
    void SearchMatchedDevices(){
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        if(devices.size()!=0){
            ShowMatchedDevice(devices);
        }
        else{
            boolean isStarted=bluetoothAdapter.startDiscovery();
            if(isStarted){
                IntentFilter intentFilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(receiver,intentFilter);
            }
            else{
                Toast.makeText(BlueToothConnectActivity.this, "设备搜索失败!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * get the matched devices list
     *
     * @param devices
     */
    void ShowMatchedDevice(Set<BluetoothDevice> devices) {
        for (BluetoothDevice singleDevice : devices) {
            BluetoothItem item = new BluetoothItem();
            item.setType(singleDevice.getType());
            item.setName(singleDevice.getName());
            item.setAddress(singleDevice.getAddress());
            items.add(item);
        }
        deviceAdpater=new ShowDeviceAdpater(items);
        rlSearchedDevice.setAdapter(deviceAdpater);
        deviceAdpater.setOnItemClickListener(new OnRLItemClicklistener() {
            @Override
            public void OnClick(int position) {
                bluetoothAdapter.cancelDiscovery();
                new Thread(){

                    @Override
                    public void run() {
                        try {
                            CommonVairiable.serverSocket=bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("admin", UUID.randomUUID());
                            mHandler.sendEmptyMessage(2);
                            BluetoothSocket socket;
                            while (true){
                                socket=CommonVairiable.serverSocket.accept();
                                if(socket!=null){
                                    mHandler.sendEmptyMessage(1);
                                    break;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    public void CancelConnect(){
        if(CommonVairiable.serverSocket!=null){
            try {
                CommonVairiable.serverSocket.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
