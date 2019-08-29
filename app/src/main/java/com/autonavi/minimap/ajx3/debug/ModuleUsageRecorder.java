package com.autonavi.minimap.ajx3.debug;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.appmonitor.offline.TempEvent;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModuleUsageRecorder {
    private static final String RECORD_KEY = "record";
    private static final String URL_HOST = "http://30.28.11.152:8888/";
    private static volatile ModuleUsageRecorder instance;
    private String mADiu;
    private String mDiu;
    private MyHandler mHandler = new MyHandler(this.mHandlerThread.getLooper());
    private HandlerThread mHandlerThread;
    private String mVersionName;

    static class MyHandler extends Handler {
        public static final int RECORD_MODULE_RECORDS = 101;
        public static final int UPLOAD_MODULE_RECORDS = 100;
        private ConcurrentHashMap<String, RecordBean> mRecordMap = new ConcurrentHashMap<>();

        public MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 100:
                    uploadRecords();
                    return;
                case 101:
                    Bundle data = message.getData();
                    if (data != null) {
                        recordModuleCall((RecordBean) data.getSerializable(ModuleUsageRecorder.RECORD_KEY));
                        break;
                    } else {
                        return;
                    }
            }
        }

        private void recordModuleCall(RecordBean recordBean) {
            if (recordBean != null) {
                RecordBean recordBean2 = this.mRecordMap.get(recordBean.getKey());
                if (recordBean2 == null) {
                    recordBean.increaseCallCount();
                    this.mRecordMap.put(recordBean.getKey(), recordBean);
                } else {
                    recordBean2.increaseCallCount();
                }
                if (!hasMessages(100)) {
                    Message obtainMessage = obtainMessage();
                    obtainMessage.what = 100;
                    sendMessageDelayed(obtainMessage, 10000);
                }
            }
        }

        private void uploadRecords() {
            JSONArray jSONArray = new JSONArray();
            for (RecordBean next : this.mRecordMap.values()) {
                if (!(next == null || next.toJson() == null)) {
                    jSONArray.put(next.toJson());
                }
            }
            if (jSONArray.length() <= 0) {
                return;
            }
            if (upload(jSONArray.toString()) || this.mRecordMap.size() > 10000) {
                this.mRecordMap.clear();
            }
        }

        private boolean upload(String str) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://30.28.11.152:8888/upload").openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                httpURLConnection.setRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
                httpURLConnection.connect();
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                StringBuilder sb = new StringBuilder("param=");
                sb.append(URLEncoder.encode(str, "UTF-8"));
                dataOutputStream.writeBytes(sb.toString());
                dataOutputStream.flush();
                dataOutputStream.close();
                if (httpURLConnection.getResponseCode() == 200) {
                    streamToString(httpURLConnection.getInputStream());
                } else {
                    new StringBuilder("upload failed!").append(httpURLConnection.getResponseCode());
                }
                httpURLConnection.disconnect();
                return true;
            } catch (MalformedURLException unused) {
                return false;
            } catch (IOException unused2) {
                return false;
            } catch (Throwable unused3) {
                return false;
            }
        }

        public String streamToString(InputStream inputStream) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        byteArrayOutputStream.close();
                        inputStream.close();
                        return new String(byteArrayOutputStream.toByteArray());
                    }
                }
            } catch (Exception unused) {
                return null;
            }
        }
    }

    class RecordBean implements Serializable {
        private static final String PLATFORM = "ANDROID";
        public String mADiu;
        public int mCallCount;
        public String mContext;
        public String mDiu;
        public String mField = "";
        public String mMethod;
        public String mModule;
        public String mPagePath;
        public long mTime;
        public String mVersionName;

        public RecordBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            this.mDiu = str;
            this.mADiu = str2;
            this.mVersionName = str3;
            this.mPagePath = str4;
            this.mContext = str5;
            this.mModule = str6;
            this.mMethod = str7;
            this.mCallCount = 0;
            this.mTime = System.currentTimeMillis();
        }

        public RecordBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
            this.mDiu = str;
            this.mADiu = str2;
            this.mVersionName = str3;
            this.mPagePath = str4;
            this.mContext = str5;
            this.mModule = str6;
            this.mMethod = str7;
            this.mField = str8;
            this.mCallCount = 0;
            this.mTime = System.currentTimeMillis();
        }

        public String getKey() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mPagePath);
            sb.append("-");
            sb.append(this.mMethod);
            sb.append("-");
            sb.append(this.mField);
            return sb.toString();
        }

        public void increaseCallCount() {
            this.mCallCount++;
        }

        public void resetCallCount() {
            this.mCallCount = 0;
        }

        public JSONObject toJson() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(LocationParams.PARA_COMMON_DIU, this.mDiu);
                jSONObject.put(LocationParams.PARA_COMMON_ADIU, this.mADiu);
                jSONObject.put("vername", this.mVersionName);
                jSONObject.put("page", this.mPagePath);
                jSONObject.put("context", this.mContext);
                jSONObject.put(TempEvent.TAG_MODULE, this.mModule);
                jSONObject.put("method", this.mMethod);
                if (!TextUtils.isEmpty(this.mField)) {
                    jSONObject.put("field", this.mField);
                }
                jSONObject.put(NewHtcHomeBadger.COUNT, this.mCallCount);
                jSONObject.put("time", this.mTime);
                jSONObject.put("platform", PLATFORM);
                return jSONObject;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private void sendRecordMsg(RecordBean recordBean) {
    }

    private ModuleUsageRecorder(String str, Context context) {
        this.mHandlerThread = new HandlerThread(str);
        this.mHandlerThread.start();
        this.mDiu = DeviceUtil.getDiu(context);
        this.mVersionName = DeviceUtil.getVersionName(context);
        this.mADiu = getADiu();
    }

    private String getADiu() {
        try {
            Class<?> cls = Class.forName("com.amap.bundle.network.request.param.NetworkParam");
            Object invoke = cls.getMethod("getAdiu", new Class[0]).invoke(cls.newInstance(), new Object[0]);
            if (invoke != null && !TextUtils.isEmpty(invoke.toString())) {
                return invoke.toString();
            }
        } catch (Exception unused) {
        }
        return "";
    }

    public static ModuleUsageRecorder getInstance(Context context) {
        if (instance == null) {
            synchronized (ModuleUsageRecorder.class) {
                if (instance == null) {
                    instance = new ModuleUsageRecorder("ModuleUsageRecorder", context);
                }
            }
        }
        return instance;
    }

    public void recordModuleFieldSet(String str, String str2, String str3, String str4) {
        RecordBean recordBean = new RecordBean(this.mDiu, this.mADiu, this.mVersionName, str, str2, str3, "set", str4);
        sendRecordMsg(recordBean);
    }

    public void recordModuleFieldGet(String str, String str2, String str3, String str4) {
        RecordBean recordBean = new RecordBean(this.mDiu, this.mADiu, this.mVersionName, str, str2, str3, "get", str4);
        sendRecordMsg(recordBean);
    }

    public void recordModuleMethodCall(String str, String str2, String str3, String str4) {
        RecordBean recordBean = new RecordBean(this.mDiu, this.mADiu, this.mVersionName, str, str2, str3, str4);
        sendRecordMsg(recordBean);
    }

    public void stopRecorder() {
        try {
            if (this.mHandlerThread.isAlive()) {
                this.mHandlerThread.quit();
            }
        } catch (Exception unused) {
        }
    }
}
