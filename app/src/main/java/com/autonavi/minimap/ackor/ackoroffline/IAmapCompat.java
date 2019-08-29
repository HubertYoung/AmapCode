package com.autonavi.minimap.ackor.ackoroffline;

import com.autonavi.jni.ae.bl.Parcel;

public interface IAmapCompat {
    boolean onDatabaseBackup(String str, String str2);

    Parcel onDatabaseCheckAndUpgrade(String str, String str2);
}
