package com.autonavi.minimap.ajx3.core;

import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.platform.ackor.Parcelable;

public class PageConfig implements Parcelable {
    private String id;
    private boolean runOnUi;
    private String url;

    public boolean writeToParcel(Parcel parcel) {
        return false;
    }

    public boolean isRunOnUi() {
        return this.runOnUi;
    }

    public String getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean readFromParcel(Parcel parcel) {
        parcel.reset();
        if (parcel.readInt() > 0) {
            this.id = parcel.readString();
        }
        if (parcel.readInt() > 0) {
            this.url = parcel.readString();
        }
        this.runOnUi = parcel.readBoolean();
        return true;
    }
}
