package com.geniusmart.binder.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.geniusmart.binder.aidl.ICompute;
import com.geniusmart.client.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Client";
    private ICompute mICompute;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "Client进程触发onServiceConnected : " + service.getClass().getName());
            mICompute = ICompute.Stub.asInterface(service);
            Log.i(TAG, "Client进程触发asInterface : " + mICompute.getClass().getName());
            try {
                Log.i(TAG, "Client进程触发add() : result = " + mICompute.add(3, 5));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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
    }

    public void bindService() {
        Intent intent = new Intent("com.geniusmart.server.ComputeService").setPackage("com.geniusmart.binder.server");
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
