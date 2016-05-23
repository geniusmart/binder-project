package com.geniusmart.binder.aidl;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.geniusmart.binder.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Server进程";
    private ICompute mICompute;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, TAG + "触发onServiceConnected : " + service.getClass().getName());
            mICompute = ICompute.Stub.asInterface(service);
            Log.i(TAG, TAG + "触发asInterface : " + mICompute.getClass().getName());
            try {
                Log.i(TAG, TAG + "触发add() : result = " + mICompute.add(3, 5));
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
        Intent intent = new Intent(this, ComputeService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    /**
     * 通过反射，获取Provider对象
     */
    private void acquireAndLogProvider() {
        Uri uri = Uri.parse("content://com.geniusmart.binder.AccountProvider/account");
        ContentResolver contentResolver = getContentResolver();
        try {
            Method method = ContentResolver.class.getMethod("acquireProvider", new Class[]{Uri.class});
            Object object = method.invoke(contentResolver, uri);
            Log.i(TAG, object.getClass().toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
