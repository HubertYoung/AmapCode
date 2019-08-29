package com.standardar.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SharedMemory;
import com.standardar.service.aidl.IDataFlowInterface;
import com.standardar.service.aidl.IDataFlowInterface.Stub;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ClientProxy {
    private static final String PACKAGE_NAME = "com.standardar.service";
    private static final int SEND_COMMAND_REQUEST_SUPPORT = 4;
    private static final String SERVICE_ACTION_NAME = "com.standardar.service.standarservice";
    public static ClientProxy mInstance;
    private Context mContext;
    /* access modifiers changed from: private */
    public IDataFlowInterface mService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Util.LOGI("service connect ".concat(String.valueOf(componentName)));
            ClientProxy.this.mService = Stub.asInterface(iBinder);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Util.LOGI("service disconnect ".concat(String.valueOf(componentName)));
            ClientProxy.this.mService = null;
        }
    };

    public static synchronized ClientProxy getInstance(Context context) {
        ClientProxy clientProxy;
        synchronized (ClientProxy.class) {
            try {
                if (mInstance == null) {
                    ClientProxy clientProxy2 = new ClientProxy();
                    mInstance = clientProxy2;
                    clientProxy2.mContext = context;
                    mInstance.bindService();
                }
                clientProxy = mInstance;
            }
        }
        return clientProxy;
    }

    public void bindService() {
        this.mContext.bindService(new Intent(SERVICE_ACTION_NAME).setPackage(PACKAGE_NAME), this.mServiceConnection, 1);
    }

    public void unbindService() {
        this.mContext.unbindService(this.mServiceConnection);
    }

    public void stopService() {
        this.mContext.stopService(new Intent(SERVICE_ACTION_NAME).setPackage(PACKAGE_NAME));
    }

    public boolean isServiceConnnect() {
        return this.mService != null;
    }

    public byte[] processFrame(byte[] bArr) throws RemoteException {
        return this.mService.processFrame(bArr);
    }

    public void setupSharedMemory(SharedMemory sharedMemory, int i) throws RemoteException {
        this.mService.setupSharedMemory(sharedMemory, i);
    }

    public int sendCommand(int i, byte[] bArr) throws RemoteException {
        return this.mService.sendCommand(i, bArr);
    }

    public byte[] processFrameShareMemoryV27() throws RemoteException {
        return this.mService.processFrameShareMemoryV27();
    }

    public byte[] processFrameShareMemory(ParcelFileDescriptor parcelFileDescriptor, int i) throws RemoteException {
        return this.mService.processFrameShareMemory(parcelFileDescriptor, i);
    }

    public int requestSupport(String str, String str2, String str3) {
        int i = 0;
        while (i < 10) {
            if (this.mService != null) {
                int length = str.getBytes(Charset.forName("UTF-8")).length;
                int length2 = str2.getBytes(Charset.forName("UTF-8")).length;
                int length3 = str3.getBytes(Charset.forName("UTF-8")).length;
                ByteBuffer allocate = ByteBuffer.allocate(length + 12 + length2 + length3);
                allocate.putInt(length);
                allocate.putInt(length2);
                allocate.putInt(length3);
                allocate.put(str.getBytes(Charset.forName("UTF-8")));
                allocate.put(str2.getBytes(Charset.forName("UTF-8")));
                allocate.put(str3.getBytes(Charset.forName("UTF-8")));
                try {
                    return this.mService.sendCommand(4, allocate.array());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                i++;
            }
        }
        return -1;
    }
}
