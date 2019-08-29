package com.autonavi.jni.drive.offline;

public class Logger {
    private static final String TAG = "DriveOfflineManager";
    private OnLogCallBackListener mOnLogCallBack;
    private long mPtr;

    public interface OnLogCallBackListener {
        void onLogCall(int i, String str, String str2, String str3, String str4, String str5);
    }

    public void setOnLogCallBackListener(OnLogCallBackListener onLogCallBackListener) {
        this.mOnLogCallBack = onLogCallBackListener;
    }

    public void log(int i, String str, String str2, String str3, String str4, String str5) {
        if (this.mOnLogCallBack != null) {
            this.mOnLogCallBack.onLogCall(i, str, str2, str3, str4, str5);
        }
    }
}
