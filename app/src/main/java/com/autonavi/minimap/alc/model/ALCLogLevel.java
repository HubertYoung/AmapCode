package com.autonavi.minimap.alc.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public enum ALCLogLevel implements Parcelable {
    P1(1),
    P2(2),
    P3(3),
    P4(4),
    P5(5),
    P6(6),
    P7(7),
    LOG_DEBUG(8),
    LOG_INFO(16),
    LOG_WARN(32),
    LOG_ERROR(64),
    LOG_FATAL(128),
    LOG_PERFORMANCE(256),
    LOG_TRACING(512),
    P8(1048576);
    
    public static final Creator<ALCLogLevel> CREATOR = null;
    public static final int DEFAULT_LOG_LEVEL = 0;
    private int num;

    public final int describeContents() {
        return 0;
    }

    static {
        DEFAULT_LOG_LEVEL = LOG_DEBUG.getNum() | LOG_INFO.getNum() | LOG_WARN.getNum() | LOG_ERROR.getNum() | LOG_FATAL.getNum();
        CREATOR = new Creator<ALCLogLevel>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new ALCLogLevel[0];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                int readInt = parcel.readInt();
                if (readInt == 16) {
                    return ALCLogLevel.LOG_INFO;
                }
                if (readInt == 32) {
                    return ALCLogLevel.LOG_WARN;
                }
                if (readInt == 64) {
                    return ALCLogLevel.LOG_ERROR;
                }
                if (readInt == 128) {
                    return ALCLogLevel.LOG_FATAL;
                }
                if (readInt == 256) {
                    return ALCLogLevel.LOG_PERFORMANCE;
                }
                if (readInt == 512) {
                    return ALCLogLevel.LOG_TRACING;
                }
                if (readInt == 1048576) {
                    return ALCLogLevel.P8;
                }
                switch (readInt) {
                    case 1:
                        return ALCLogLevel.P1;
                    case 2:
                        return ALCLogLevel.P2;
                    case 3:
                        return ALCLogLevel.P3;
                    case 4:
                        return ALCLogLevel.P4;
                    case 5:
                        return ALCLogLevel.P5;
                    case 6:
                        return ALCLogLevel.P6;
                    case 7:
                        return ALCLogLevel.P7;
                    case 8:
                        return ALCLogLevel.LOG_DEBUG;
                    default:
                        return ALCLogLevel.P5;
                }
            }
        };
    }

    private ALCLogLevel(int i) {
        this.num = i;
    }

    public final int getNum() {
        return this.num;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.num);
    }
}
