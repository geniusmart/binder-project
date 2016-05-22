package com.geniusmart.binder.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.geniusmart.binder.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Server";
    private ICompute mICompute;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, TAG+"Server进程：onServiceConnected : " + service.getClass().getName());
            mICompute = ICompute.Stub.asInterface(service);
            Log.i(TAG, TAG+"Server进程：asInterface : " + mICompute.getClass().getName());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();

        try {
            int result = mICompute.add(5, 10);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void bindService(){
        Intent intent = new Intent(this,ComputeService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
    }
}
