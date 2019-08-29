package com.autonavi.minimap.offlinesdk;

import android.util.Pair;
import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.jni.ae.bl.Parcelable;

public class OfflineConfig implements Parcelable {
    public String OfflineAOSDomain;
    public String OfflineDataPath;
    public String OfflinePath;
    public boolean WifiAutoUpdate;

    public boolean writeToParcel(Parcel parcel) {
        parcel.reset();
        parcel.writeString(this.OfflineAOSDomain);
        parcel.writeString(this.OfflineDataPath);
        parcel.writeString(this.OfflinePath);
        parcel.writeBoolean(this.WifiAutoUpdate);
        return true;
    }

    public boolean readFromParcel(Parcel parcel) {
        parcel.reset();
        this.OfflineAOSDomain = parcel.readString();
        this.OfflineDataPath = parcel.readString();
        this.OfflinePath = parcel.readString();
        this.WifiAutoUpdate = parcel.readBoolean();
        return true;
    }

    private void writePairArray(Pair<String, String>[] pairArr, Parcel parcel) {
        if (pairArr == null || pairArr.length <= 0) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(pairArr.length);
        for (int i = 0; i < pairArr.length; i++) {
            parcel.writeString((String) pairArr[i].first);
            parcel.writeString((String) pairArr[i].second);
        }
    }

    private void writeStringArray(String[] strArr, Parcel parcel) {
        if (strArr == null || strArr.length <= 0) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(strArr.length);
        for (String writeString : strArr) {
            parcel.writeString(writeString);
        }
    }

    private Pair<String, String>[] readPairArray(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt <= 0) {
            return null;
        }
        Pair<String, String>[] pairArr = new Pair[readInt];
        for (int i = 0; i < readInt; i++) {
            pairArr[i] = new Pair<>(parcel.readString(), parcel.readString());
        }
        return pairArr;
    }

    private String[] readStringArray(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt <= 0) {
            return null;
        }
        String[] strArr = new String[readInt];
        for (int i = 0; i < readInt; i++) {
            strArr[i] = parcel.readString();
        }
        return strArr;
    }
}
