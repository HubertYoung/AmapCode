package com.autonavi.jni.drive.offline;

public class BehaviorService {
    private OnBehaviorLogInterface mOnBehaviorLogInterface;
    private long mPtr;

    public interface OnBehaviorLogInterface {
        void onBehaviorLogCall(String str, String str2, String str3);
    }

    public void setOnBehaviorLogInterface(OnBehaviorLogInterface onBehaviorLogInterface) {
        this.mOnBehaviorLogInterface = onBehaviorLogInterface;
    }

    public void log(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder("BehaviorService.log is called, pageId = ");
        sb.append(str);
        sb.append(", btnId = ");
        sb.append(str2);
        sb.append(", content = ");
        sb.append(str3);
        if (this.mOnBehaviorLogInterface != null) {
            this.mOnBehaviorLogInterface.onBehaviorLogCall(str, str2, str3);
        }
    }
}
