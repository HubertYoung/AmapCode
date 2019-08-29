package com.alipay.mobile.common.fgbg;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.fgbg.FgBgMonitor.ProcessType;

class ProcessInfo implements Parcelable, com.alipay.mobile.common.fgbg.FgBgMonitor.ProcessInfo {
    public static final Creator<ProcessInfo> CREATOR = new Creator<ProcessInfo>() {
        {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public final ProcessInfo createFromParcel(Parcel in) {
            return new ProcessInfo(in);
        }

        public final ProcessInfo[] newArray(int size) {
            return new ProcessInfo[size];
        }
    };
    @NonNull
    private String processName;
    @NonNull
    private ProcessType processType;
    @NonNull
    private String topActivity;

    @NonNull
    public ProcessType getType() {
        return this.processType;
    }

    @NonNull
    public String getProcessName() {
        return this.processName;
    }

    @NonNull
    public String getTopActivity() {
        return this.topActivity;
    }

    protected ProcessInfo(Parcel in) {
        try {
            this.processName = in.readString();
            this.processType = ProcessType.valueOf(in.readString());
            this.topActivity = in.readString();
        } catch (Throwable th) {
            this.processName = "unknown";
            this.processType = ProcessType.UNKNOWN;
            this.topActivity = "unknown";
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    ProcessInfo(@NonNull String processName2, @NonNull ProcessType processType2, @NonNull String topActivity2) {
        this.processName = processName2;
        this.processType = processType2;
        this.topActivity = topActivity2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.processName);
        parcel.writeString(this.processType.name());
        parcel.writeString(this.topActivity);
    }
}
