package com.hmt.analytics.android;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

/* compiled from: OpenUDID_service_hmt */
public class n extends Service {
    public IBinder onBind(Intent intent) {
        return new Binder() {
            public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
                SharedPreferences sharedPreferences = n.this.getSharedPreferences("openudid_prefs", 0);
                parcel2.writeInt(parcel.readInt());
                parcel2.writeString(sharedPreferences.getString("openudid", null));
                return true;
            }
        };
    }
}
