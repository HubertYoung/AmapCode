package com.autonavi.map.search.manager;

import android.os.Message;
import android.support.v4.view.InputDeviceCompat;
import com.autonavi.common.Callback;
import com.autonavi.map.mapinterface.IMapRequestManager.a;

public class PoiMarkManager$1 implements Callback<a> {
    final /* synthetic */ byz a;

    public void error(Throwable th, boolean z) {
    }

    public PoiMarkManager$1(byz byz) {
        this.a = byz;
    }

    public void callback(a aVar) {
        Message obtainMessage = this.a.obtainMessage(InputDeviceCompat.SOURCE_STYLUS);
        obtainMessage.obj = aVar;
        this.a.sendMessage(obtainMessage);
    }
}
