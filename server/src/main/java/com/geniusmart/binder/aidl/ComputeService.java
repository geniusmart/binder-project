package com.geniusmart.binder.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ComputeService extends Service {

    public static final String TAG = "Server";

    @Override
    public IBinder onBind(Intent intent) {
        return new ComputeBinder();
    }

    private static class ComputeBinder extends ICompute.Stub {

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.i(TAG, "Server进程：" + this.getClass().getName() + "执行add()");
            return a + b;
        }
    }
}
