package com.amap.bundle.blutils.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class DeviceInfo {
    private static final long CELL_LOCATION_UPDATE_SPAN = 10000;
    private static DeviceInfo instance;
    private static volatile long lastCellLocationUpdateTime;
    private int ant;
    private int bid;
    private int cid;
    private int lac;
    private String mAMapVersion = "";
    private int mAccurate;
    private Context mContext;
    private float mDensity;
    private int mDensityDpi;
    private final String mDevice = Build.DEVICE;
    private String mGLRender = "";
    private int mHeight;
    private int mLastStartTime = -1;
    private int mLastStopTime = -1;
    private int mLat;
    private int mLon;
    private final String mManufacture = Build.MANUFACTURER;
    private final String mModel = Build.MODEL;
    private int mSDKVersion = VERSION.SDK_INT;
    private int mStartTime;
    private int mStopTime = -1;
    private int mStrength;
    private int mWidth;
    private int mcc = 460;
    private int mnc;
    private int nid;
    private int nt;
    private int pt;
    private int sid;

    static class a extends Thread {
        public final void run() {
            try {
                DeviceInfo.getInstance(AMapAppGlobal.getApplication()).getCellInfo();
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized DeviceInfo getInstance(Context context) {
        synchronized (DeviceInfo.class) {
            if (instance == null) {
                synchronized (DeviceInfo.class) {
                    instance = new DeviceInfo(context);
                }
                DeviceInfo deviceInfo = instance;
                return deviceInfo;
            }
            DeviceInfo deviceInfo2 = instance;
            return deviceInfo2;
        }
    }

    private DeviceInfo(Context context) {
        this.mContext = context.getApplicationContext();
        if (this.mContext != null && this.mContext.getResources() != null) {
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
            this.mWidth = displayMetrics.widthPixels;
            this.mHeight = displayMetrics.heightPixels;
            this.mDensity = displayMetrics.density;
            this.mDensityDpi = displayMetrics.densityDpi;
            try {
                PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
                if (packageInfo != null) {
                    this.mAMapVersion = packageInfo.versionName;
                }
            } catch (NameNotFoundException e) {
                kf.a((Throwable) e);
            }
        }
    }

    private byte[] get2BString(String str) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = str.getBytes("UTF-8");
            byteArrayOutputStream.write(ahg.b(bytes.length));
            byteArrayOutputStream.write(bytes);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[1];
        }
    }

    public String toString() {
        if (System.currentTimeMillis() - lastCellLocationUpdateTime > CELL_LOCATION_UPDATE_SPAN) {
            try {
                a aVar = new a();
                aVar.start();
                aVar.join(500);
            } catch (Throwable unused) {
            }
            lastCellLocationUpdateTime = System.currentTimeMillis();
        }
        getAppTimeStamp();
        return ahg.a(toByte());
    }

    public String toShortString() {
        if (System.currentTimeMillis() - lastCellLocationUpdateTime > CELL_LOCATION_UPDATE_SPAN) {
            try {
                a aVar = new a();
                aVar.start();
                aVar.join(500);
            } catch (Throwable unused) {
            }
            lastCellLocationUpdateTime = System.currentTimeMillis();
        }
        getAppTimeStamp();
        return ahg.a(toShortByte());
    }

    public void setLocation(int i, int i2, int i3) {
        this.mLon = i;
        this.mLat = i2;
        this.mAccurate = i3;
        if (this.mAccurate > 32767) {
            this.mAccurate = 32767;
        }
    }

    @Deprecated
    public int getMcc() {
        String networkOperator = ((TelephonyManager) this.mContext.getSystemService("phone")).getNetworkOperator();
        if (networkOperator != null && !"null".equals(networkOperator) && networkOperator.length() >= 3) {
            try {
                this.mcc = Integer.parseInt(networkOperator.substring(0, 3));
                this.mnc = Integer.parseInt(networkOperator.substring(3));
            } catch (Exception unused) {
            }
        }
        return this.mcc;
    }

    @Deprecated
    public int getMnc() {
        if (this.mnc != 0) {
            return this.mnc;
        }
        String networkOperator = ((TelephonyManager) this.mContext.getSystemService("phone")).getNetworkOperator();
        if (networkOperator != null && !"null".equals(networkOperator) && networkOperator.length() >= 3) {
            try {
                this.mnc = Integer.parseInt(networkOperator.substring(3));
            } catch (Exception unused) {
            }
        }
        return this.mnc;
    }

    private byte[] toByte() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(ahg.b(this.mWidth));
            byteArrayOutputStream.write(ahg.b(this.mHeight));
            byteArrayOutputStream.write(ahg.a(this.mLon));
            byteArrayOutputStream.write(ahg.a(this.mLat));
            byteArrayOutputStream.write(ahg.a((byte) this.ant));
            byteArrayOutputStream.write(ahg.a((byte) this.nt));
            byteArrayOutputStream.write(ahg.a((byte) this.pt));
            byteArrayOutputStream.write(ahg.b(this.mcc));
            byteArrayOutputStream.write(ahg.b(this.mnc));
            byteArrayOutputStream.write(ahg.a(this.lac));
            byteArrayOutputStream.write(ahg.a(this.cid));
            byteArrayOutputStream.write(ahg.a(this.sid));
            byteArrayOutputStream.write(ahg.a(this.nid));
            byteArrayOutputStream.write(ahg.a(this.bid));
            byteArrayOutputStream.write(ahg.a(this.mStrength));
            byteArrayOutputStream.write(ahg.b(this.mSDKVersion));
            byteArrayOutputStream.write(get2BString(""));
            byteArrayOutputStream.write(get2BString(this.mModel));
            byteArrayOutputStream.write(get2BString(this.mDevice));
            byteArrayOutputStream.write(get2BString(this.mManufacture));
            byteArrayOutputStream.write(get2BString(this.mAMapVersion));
            byteArrayOutputStream.write(ahg.a(this.mLastStartTime));
            byteArrayOutputStream.write(ahg.a(this.mLastStopTime));
            byteArrayOutputStream.write(get2BString(this.mGLRender));
            byteArrayOutputStream.write(ahg.b(this.mAccurate));
            this.mLastStartTime = 0;
            this.mLastStopTime = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] toShortByte() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(ahg.b(this.mWidth));
            byteArrayOutputStream.write(ahg.b(this.mHeight));
            byteArrayOutputStream.write(ahg.a(this.mLon));
            byteArrayOutputStream.write(ahg.a(this.mLat));
            byteArrayOutputStream.write(ahg.a((byte) this.ant));
            byteArrayOutputStream.write(ahg.a((byte) this.nt));
            byteArrayOutputStream.write(ahg.a((byte) this.pt));
            byteArrayOutputStream.write(ahg.b(this.mcc));
            byteArrayOutputStream.write(ahg.b(this.mnc));
            byteArrayOutputStream.write(ahg.a(this.lac));
            byteArrayOutputStream.write(ahg.a(this.cid));
            byteArrayOutputStream.write(ahg.a(this.sid));
            byteArrayOutputStream.write(ahg.a(this.nid));
            byteArrayOutputStream.write(ahg.a(this.bid));
            byteArrayOutputStream.write(ahg.a(this.mStrength));
            byteArrayOutputStream.write(ahg.b(this.mSDKVersion));
            byteArrayOutputStream.write(new byte[2]);
            byteArrayOutputStream.write(new byte[2]);
            byteArrayOutputStream.write(new byte[2]);
            byteArrayOutputStream.write(new byte[2]);
            byteArrayOutputStream.write(new byte[2]);
            byteArrayOutputStream.write(ahg.a(this.mLastStartTime));
            byteArrayOutputStream.write(ahg.a(this.mLastStopTime));
            byteArrayOutputStream.write(new byte[2]);
            byteArrayOutputStream.write(ahg.b(this.mAccurate));
            this.mLastStartTime = 0;
            this.mLastStopTime = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void setStartTime() {
        this.mStartTime = (int) lf.a();
        this.mSDKVersion = VERSION.SDK_INT;
        this.mLon = 0;
        this.mLat = 0;
        this.mAccurate = 0;
        this.mStrength = 0;
        this.mStopTime = -1;
        this.mLastStartTime = -1;
        this.mLastStopTime = -1;
    }

    private void getAppTimeStamp() {
        if (this.mLastStartTime == -1 && this.mLastStopTime == -1) {
            SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
            this.mLastStartTime = sharedPrefs.getInt("AppStartTime", 0);
            this.mLastStopTime = sharedPrefs.getInt("AppStopTime", 0);
            Editor edit = sharedPrefs.edit();
            edit.putInt("AppStartTime", 0);
            edit.putInt("AppStopTime", 0);
            edit.commit();
        }
    }

    /* access modifiers changed from: 0000 */
    public void getCellInfo() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                this.ant = activeNetworkInfo.getType();
            }
            this.nt = telephonyManager.getNetworkType();
            this.pt = telephonyManager.getPhoneType();
            if (this.pt == 1) {
                GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
                if (gsmCellLocation != null) {
                    this.lac = gsmCellLocation.getLac();
                    this.cid = gsmCellLocation.getCid();
                }
            } else if (this.pt == 2) {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
                if (cdmaCellLocation != null) {
                    this.sid = cdmaCellLocation.getSystemId();
                    this.nid = cdmaCellLocation.getNetworkId();
                    this.bid = cdmaCellLocation.getBaseStationId();
                }
            }
            String networkOperator = telephonyManager.getNetworkOperator();
            if (networkOperator != null && !"null".equals(networkOperator) && networkOperator.length() >= 3) {
                try {
                    this.mcc = Integer.parseInt(networkOperator.substring(0, 3));
                    this.mnc = Integer.parseInt(networkOperator.substring(3));
                } catch (Exception unused) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getRAM() {
        long j;
        ClassNotFoundException classNotFoundException;
        SecurityException securityException;
        IllegalArgumentException illegalArgumentException;
        IllegalAccessException illegalAccessException;
        InvocationTargetException invocationTargetException;
        NoSuchMethodException noSuchMethodException;
        long j2 = 0;
        try {
            Method method = Class.forName("android.os.Process").getMethod("readProcLines", new Class[]{String.class, String[].class, long[].class});
            String[] strArr = {"MemTotal:", "MemFree:", "Buffers:", "Cached:"};
            long[] jArr = new long[4];
            jArr[0] = 30;
            jArr[1] = -30;
            Object[] objArr = {"/proc/meminfo", strArr, jArr};
            if (method != null) {
                method.invoke(null, objArr);
                long j3 = 0;
                int i = 0;
                while (i < 4) {
                    try {
                        j = jArr[0];
                    } catch (ClassNotFoundException e) {
                        classNotFoundException = e;
                        j = j3;
                        classNotFoundException.printStackTrace();
                        return j;
                    } catch (SecurityException e2) {
                        securityException = e2;
                        j = j3;
                        securityException.printStackTrace();
                        return j;
                    } catch (IllegalArgumentException e3) {
                        illegalArgumentException = e3;
                        j = j3;
                        illegalArgumentException.printStackTrace();
                        return j;
                    } catch (IllegalAccessException e4) {
                        illegalAccessException = e4;
                        j = j3;
                        illegalAccessException.printStackTrace();
                        return j;
                    } catch (InvocationTargetException e5) {
                        invocationTargetException = e5;
                        j = j3;
                        invocationTargetException.printStackTrace();
                        return j;
                    } catch (NoSuchMethodException e6) {
                        noSuchMethodException = e6;
                        j = j3;
                        noSuchMethodException.printStackTrace();
                        return j;
                    }
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append(strArr[i]);
                        sb.append(" : ");
                        sb.append(jArr[i] / 1024);
                        AMapLog.e("GetFreeMem", sb.toString());
                        i++;
                        j3 = j;
                    } catch (ClassNotFoundException e7) {
                        e = e7;
                        classNotFoundException = e;
                        classNotFoundException.printStackTrace();
                        return j;
                    } catch (SecurityException e8) {
                        e = e8;
                        securityException = e;
                        securityException.printStackTrace();
                        return j;
                    } catch (IllegalArgumentException e9) {
                        e = e9;
                        illegalArgumentException = e;
                        illegalArgumentException.printStackTrace();
                        return j;
                    } catch (IllegalAccessException e10) {
                        e = e10;
                        illegalAccessException = e;
                        illegalAccessException.printStackTrace();
                        return j;
                    } catch (InvocationTargetException e11) {
                        e = e11;
                        invocationTargetException = e;
                        invocationTargetException.printStackTrace();
                        return j;
                    } catch (NoSuchMethodException e12) {
                        e = e12;
                        noSuchMethodException = e;
                        noSuchMethodException.printStackTrace();
                        return j;
                    }
                }
                j2 = j3;
            }
            return j2;
        } catch (ClassNotFoundException e13) {
            e = e13;
            j = 0;
            classNotFoundException = e;
            classNotFoundException.printStackTrace();
            return j;
        } catch (SecurityException e14) {
            e = e14;
            j = 0;
            securityException = e;
            securityException.printStackTrace();
            return j;
        } catch (IllegalArgumentException e15) {
            e = e15;
            j = 0;
            illegalArgumentException = e;
            illegalArgumentException.printStackTrace();
            return j;
        } catch (IllegalAccessException e16) {
            e = e16;
            j = 0;
            illegalAccessException = e;
            illegalAccessException.printStackTrace();
            return j;
        } catch (InvocationTargetException e17) {
            e = e17;
            j = 0;
            invocationTargetException = e;
            invocationTargetException.printStackTrace();
            return j;
        } catch (NoSuchMethodException e18) {
            e = e18;
            j = 0;
            noSuchMethodException = e;
            noSuchMethodException.printStackTrace();
            return j;
        }
    }

    public long[] getStorageInfo() {
        try {
            long[] jArr = new long[2];
            if ("mounted".equals(Environment.getExternalStorageState())) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                long blockSize = (long) statFs.getBlockSize();
                jArr[0] = ((long) statFs.getBlockCount()) * blockSize;
                jArr[1] = blockSize * ((long) statFs.getAvailableBlocks());
            }
            return jArr;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
