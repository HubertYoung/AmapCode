package com.standardar.common;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.system.OsConstants;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.standardar.common.CameraSource.ICameraNotifyCallback;
import com.standardar.common.IMUReader.ISensorNotifyCallback;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client implements ICameraNotifyCallback, ISensorNotifyCallback {
    private static final int DESTROY_SLAM = 3;
    private static final int INIT_SLAM = 0;
    private static final int MAX_IMU_SIZE = 400;
    private static final String PACKAGE_NAME = "com.standardar.service";
    private static final int RESET_SLAM = 1;
    private static final int RETRY_INIT_SLAM = 0;
    private static final int RETRY_INIT_SLAM_TIME = 100;
    private static final int RETRY_START_SLAM = 1;
    private static final int RETRY_START_SLAM_TIME = 100;
    private static final int SEND_COMMAND_CHECK_AUTHORIZATION = 4;
    private static final int SEND_COMMAND_OK = 0;
    private static final int SEND_COMMAND_SET_PACKAGE_NAME = 5;
    private static final String SERVICE_ACTION_NAME = "com.standardar.service.standarservice";
    private static final int START_SLAM = 2;
    private static final boolean USE_SHARED_MEMORY = true;
    private int mAccSize = 0;
    private CameraSource mCameraSource;
    private Handler mClientHandler;
    private HandlerThread mClientHandlerThread = null;
    private ClientProxy mClinetProxy;
    private Context mContext;
    private int mDataLength = 0;
    private Datagram mDatagram = new Datagram();
    private int mGravitySize = 0;
    private int mGyrSize = 0;
    private List<IMUData> mIMUDatas = new ArrayList();
    private boolean mIsInitSlam = false;
    private long mNativeClientPtr = 0;
    private String mPackageName = "unknow";
    private int mRVSize = 0;
    private AtomicBoolean mSLAMStart = new AtomicBoolean(false);
    private Object mSensorLock = new Object();

    class ClientHandler extends Handler {
        public ClientHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 0:
                    Client.this.initSLAM();
                    return;
                case 1:
                    Client.this.startSLAM();
                    return;
                default:
                    StringBuilder sb = new StringBuilder("unknown msg:");
                    sb.append(message.what);
                    Util.LOGW(sb.toString());
                    return;
            }
        }
    }

    static class IMUData {
        public int mTag;
        public long mTimestamp;
        public float[] mValue;

        public IMUData(float[] fArr, long j, int i) {
            this.mValue = (float[]) fArr.clone();
            this.mTimestamp = j;
            this.mTag = i;
        }
    }

    private native void arProcessResultWithImage(long j, byte[] bArr, byte[] bArr2);

    private native void arUpdateFrame(long j);

    private int getMaxImuSize() {
        return 14400;
    }

    private void startClientThread() {
        if (this.mClientHandlerThread == null) {
            this.mClientHandlerThread = new HandlerThread("client");
            this.mClientHandlerThread.start();
            this.mClientHandler = new ClientHandler(this.mClientHandlerThread.getLooper());
        }
    }

    private void stopClientThread() {
        if (this.mClientHandlerThread != null) {
            this.mClientHandler.removeMessages(0);
            this.mClientHandler.removeMessages(1);
            this.mClientHandlerThread.quitSafely();
            try {
                this.mClientHandlerThread.join();
                this.mClientHandlerThread = null;
                this.mClientHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Client(long j, Context context, CameraSource cameraSource) {
        this.mNativeClientPtr = j;
        this.mContext = context;
        this.mCameraSource = cameraSource;
        this.mClinetProxy = ClientProxy.getInstance(context);
    }

    private boolean isIMUDataZero() {
        return this.mAccSize == 0 || this.mGravitySize == 0 || this.mGyrSize == 0 || this.mRVSize == 0;
    }

    private boolean isIMUDataOverFlow() {
        return ((this.mAccSize + this.mGravitySize) + this.mRVSize) + this.mGyrSize > 400;
    }

    private void clipIMUData() {
        this.mIMUDatas = this.mIMUDatas.subList(this.mIMUDatas.size() - 400, this.mIMUDatas.size() - 1);
        this.mGyrSize = 0;
        this.mGravitySize = 0;
        this.mAccSize = 0;
        this.mRVSize = 0;
        for (IMUData next : this.mIMUDatas) {
            switch (next.mTag) {
                case 0:
                    this.mAccSize++;
                    break;
                case 1:
                    this.mGyrSize++;
                    break;
                case 2:
                    this.mGravitySize++;
                    break;
                case 3:
                    this.mRVSize++;
                    break;
                default:
                    StringBuilder sb = new StringBuilder("unknown tag:");
                    sb.append(next.mTag);
                    Util.LOGW(sb.toString());
                    break;
            }
        }
        StringBuilder sb2 = new StringBuilder("clip imu data ");
        sb2.append(this.mAccSize);
        sb2.append(Token.SEPARATOR);
        sb2.append(this.mGyrSize);
        sb2.append(Token.SEPARATOR);
        sb2.append(this.mGravitySize);
        sb2.append(Token.SEPARATOR);
        sb2.append(this.mRVSize);
        Util.LOGD(sb2.toString());
    }

    public void onCameraNotify(byte[] bArr, long j, long j2) {
        byte[] bArr2;
        if (bArr != null) {
            if (!this.mSLAMStart.get()) {
                arProcessResultWithImage(this.mNativeClientPtr, null, bArr);
            } else if (this.mClinetProxy.isServiceConnnect() && this.mCameraSource != null) {
                this.mCameraSource.setImageReaderActive(false);
                ByteBuffer packFrameYUVData = packFrameYUVData(bArr, j, j2);
                if (packFrameYUVData == null) {
                    this.mCameraSource.setImageReaderActive(true);
                    return;
                }
                try {
                    bArr2 = processFrameRemote(packFrameYUVData, true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    bArr2 = null;
                }
                if (bArr2 != null) {
                    displayFrameWidthResult(bArr2, bArr);
                }
            }
        }
    }

    private byte[] processFrameRemote(ByteBuffer byteBuffer, boolean z) throws RemoteException {
        byte[] bArr;
        if (!this.mClinetProxy.isServiceConnnect() || this.mCameraSource == null) {
            Util.LOGW("processFrameRemote mService or mCameraSource is null!");
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("start arservice-transfer ");
        sb.append(byteBuffer.array().length);
        Util.LOGI(sb.toString());
        if (z) {
            bArr = processFrameRemoteSharedMemory(byteBuffer.array());
        } else {
            bArr = this.mClinetProxy.processFrame(byteBuffer.array());
        }
        StringBuilder sb2 = new StringBuilder("processFrameRemote time : ");
        sb2.append(System.currentTimeMillis() - currentTimeMillis);
        Util.LOGI(sb2.toString());
        return bArr;
    }

    private void clearIMUDataLock() {
        synchronized (this.mSensorLock) {
            Util.LOGD("clear imu data");
            this.mIMUDatas.clear();
            this.mAccSize = 0;
            this.mGyrSize = 0;
            this.mRVSize = 0;
            this.mGravitySize = 0;
        }
    }

    public void onSensorChanged(float[] fArr, int i, long j) {
        if (this.mSLAMStart.get()) {
            synchronized (this.mSensorLock) {
                this.mIMUDatas.add(new IMUData(fArr, j, i));
                switch (i) {
                    case 0:
                        this.mAccSize++;
                        break;
                    case 1:
                        this.mGyrSize++;
                        break;
                    case 2:
                        this.mGravitySize++;
                        break;
                    case 3:
                        this.mRVSize++;
                        break;
                    default:
                        Util.LOGW("unknown sensor tag ".concat(String.valueOf(i)));
                        break;
                }
            }
        }
    }

    private void displayFrameWidthResult(byte[] bArr, byte[] bArr2) {
        arProcessResultWithImage(this.mNativeClientPtr, bArr, bArr2);
        this.mCameraSource.setImageReaderActive(true);
    }

    private void bindService() {
        Util.LOGI("bind service");
        this.mClinetProxy.bindService();
    }

    private void unbindSerice() {
        Util.LOGI("unbind service");
        this.mClinetProxy.unbindService();
    }

    private void stopService() {
        Util.LOGI("stop service");
        this.mClinetProxy.stopService();
    }

    private void countTotalDataLength() {
        this.mDataLength = (((this.mCameraSource.getPreviewWidth() * this.mCameraSource.getPreviewHeight()) * 3) / 2) + 8 + 8 + 8 + 4 + getMaxImuSize();
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    public void initSLAM() {
        Util.LOGI("Init SLAM");
        startClientThread();
        if (this.mCameraSource == null || !this.mClinetProxy.isServiceConnnect()) {
            bindService();
            this.mClientHandler.sendEmptyMessageDelayed(0, 100);
            Util.LOGI("Retry init slam in 100 ms");
            return;
        }
        countTotalDataLength();
        if (Util.checkAndroidExceed(26)) {
            this.mDatagram.createSharedMemoryV27(this.mDataLength);
            try {
                if (this.mDatagram.getSharedMemory() != null) {
                    this.mClinetProxy.setupSharedMemory(this.mDatagram.getSharedMemory(), OsConstants.PROT_READ | OsConstants.PROT_WRITE);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        int length = this.mPackageName.getBytes(Charset.forName("UTF-8")).length;
        ByteBuffer allocate = ByteBuffer.allocate(length + 4);
        allocate.putInt(length);
        allocate.put(this.mPackageName.getBytes(Charset.forName("UTF-8")));
        try {
            int sendCommand = this.mClinetProxy.sendCommand(5, allocate.array());
            if (sendCommand != 0) {
                StringBuilder sb = new StringBuilder("set package name failed: ");
                sb.append(sendCommand);
                sb.append(", ");
                sb.append(this.mPackageName);
                Util.LOGE(sb.toString());
                return;
            }
        } catch (RemoteException unused) {
            StringBuilder sb2 = new StringBuilder("send command failed:5, ");
            sb2.append(this.mPackageName);
            Util.LOGE(sb2.toString());
        }
        int previewWidth = this.mCameraSource.getPreviewWidth();
        int previewHeight = this.mCameraSource.getPreviewHeight();
        float fovH = this.mCameraSource.getFovH();
        ByteBuffer allocate2 = ByteBuffer.allocate(12);
        allocate2.putInt(previewWidth);
        allocate2.putInt(previewHeight);
        allocate2.putFloat(fovH);
        try {
            int sendCommand2 = this.mClinetProxy.sendCommand(0, allocate2.array());
            if (sendCommand2 != 0) {
                Util.LOGE("init slam failed: ".concat(String.valueOf(sendCommand2)));
                return;
            }
        } catch (RemoteException unused2) {
            Util.LOGE("send command failed:0");
        }
        this.mCameraSource.setImageReaderActive(true);
        this.mIsInitSlam = true;
        clearIMUDataLock();
    }

    public void startSLAM() {
        Util.LOGI("Start SLAM");
        if (!this.mIsInitSlam) {
            Util.LOGI("Init SLAM is not finished");
            if (this.mClientHandler != null) {
                this.mClientHandler.sendEmptyMessageDelayed(1, 100);
            }
        } else if (!this.mClinetProxy.isServiceConnnect() || this.mCameraSource == null) {
            bindService();
            this.mClientHandler.sendEmptyMessageDelayed(1, 100);
            Util.LOGI("Retry start slam in 100 ms");
        } else {
            try {
                this.mCameraSource.setImageReaderActive(true);
                int sendCommand = this.mClinetProxy.sendCommand(2, new byte[1]);
                if (sendCommand != 0) {
                    Util.LOGE("start slam failed:".concat(String.valueOf(sendCommand)));
                    return;
                }
                clearIMUDataLock();
                this.mSLAMStart.set(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopSLAM() {
        Util.LOGI("Stop SLAM");
        if (this.mCameraSource != null && this.mClinetProxy.isServiceConnnect() && this.mIsInitSlam) {
            byte[] bArr = new byte[1];
            this.mSLAMStart.set(false);
            try {
                this.mCameraSource.setImageReaderActive(true);
                int sendCommand = this.mClinetProxy.sendCommand(1, bArr);
                if (sendCommand != 0) {
                    Util.LOGE("reset slam failed:".concat(String.valueOf(sendCommand)));
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            clearIMUDataLock();
        }
    }

    @RequiresApi(api = 18)
    public void destroySLAM() {
        Util.LOGI("Destroy SLAM");
        stopClientThread();
        if (this.mCameraSource != null && this.mClinetProxy.isServiceConnnect() && this.mIsInitSlam) {
            if (Util.checkAndroidExceed(26)) {
                this.mDatagram.releaseSharedMemory();
            }
            byte[] bArr = new byte[1];
            this.mSLAMStart.set(false);
            try {
                int sendCommand = this.mClinetProxy.sendCommand(3, bArr);
                if (sendCommand != 0) {
                    Util.LOGE("destroy slam failed:".concat(String.valueOf(sendCommand)));
                }
                this.mIsInitSlam = false;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            clearIMUDataLock();
        }
    }

    private ByteBuffer packFrameYUVData(byte[] bArr, long j, long j2) {
        if (bArr == null) {
            Util.LOGW("packData: image is null!");
            return null;
        }
        int previewWidth = ((this.mCameraSource.getPreviewWidth() * this.mCameraSource.getPreviewHeight()) * 3) / 2;
        ByteBuffer createBufferNeed = this.mDatagram.createBufferNeed(this.mDataLength);
        StringBuilder sb = new StringBuilder("image length:");
        sb.append(bArr.length);
        sb.append(" buffer size:");
        sb.append(createBufferNeed.capacity());
        Util.LOGD(sb.toString());
        synchronized (this.mSensorLock) {
            if (isIMUDataZero()) {
                StringBuilder sb2 = new StringBuilder("imu size ");
                sb2.append(this.mAccSize);
                sb2.append(Token.SEPARATOR);
                sb2.append(this.mGravitySize);
                sb2.append(Token.SEPARATOR);
                sb2.append(this.mRVSize);
                sb2.append(Token.SEPARATOR);
                sb2.append(this.mGyrSize);
                Util.LOGW(sb2.toString());
                return null;
            }
            if (isIMUDataOverFlow()) {
                clipIMUData();
            }
            createBufferNeed.putInt(Util.VERSION_1);
            createBufferNeed.putInt(Util.SLAM_TAG);
            createBufferNeed.put(bArr, 0, previewWidth);
            createBufferNeed.putLong(j2);
            createBufferNeed.putLong(j);
            createBufferNeed.putInt(this.mIMUDatas.size());
            for (IMUData next : this.mIMUDatas) {
                createBufferNeed.putInt(next.mTag);
                for (float putFloat : next.mValue) {
                    createBufferNeed.putFloat(putFloat);
                }
                createBufferNeed.putLong(next.mTimestamp);
            }
            this.mIMUDatas.clear();
            this.mAccSize = 0;
            this.mRVSize = 0;
            this.mGravitySize = 0;
            this.mGyrSize = 0;
            return createBufferNeed;
        }
    }

    private byte[] processFrameRemoteSharedMemory(byte[] bArr) {
        byte[] bArr2;
        if (bArr == null) {
            return null;
        }
        if (Util.checkAndroidExceed(26)) {
            try {
                this.mDatagram.fillSharedMemoryBufferV27(bArr);
                bArr2 = this.mClinetProxy.processFrameShareMemoryV27();
            } catch (Exception e) {
                e.printStackTrace();
                bArr2 = null;
                return bArr2;
            }
        } else {
            try {
                bArr2 = this.mClinetProxy.processFrameShareMemory(this.mDatagram.packDataShareMemory(bArr), bArr.length);
            } catch (Exception e2) {
                e2.printStackTrace();
                bArr2 = null;
                return bArr2;
            }
        }
        return bArr2;
    }
}
