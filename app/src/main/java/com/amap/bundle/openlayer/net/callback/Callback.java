package com.amap.bundle.openlayer.net.callback;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import java.util.ArrayList;
import java.util.Iterator;

public class Callback implements AosResponseCallback<AosByteResponse>, Runnable {
    private int a;

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        int i;
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse != null) {
            abk abk = new abk();
            abk.parser((byte[]) aosByteResponse.getResult());
            awq awq = abk.a;
            if (awq == null) {
                return;
            }
            if (this.a == 0 || this.a == 2) {
                awp awp = awq.a;
                if (awp != null) {
                    ArrayList<LayerItem> arrayList = awp.c;
                    if ((arrayList != null && arrayList.size() > 0) || awp.a) {
                        abm.a(awp);
                        ArrayList<LayerItem> a2 = abm.a();
                        Iterator<Integer> it = abm.b().iterator();
                        while (it.hasNext()) {
                            int intValue = it.next().intValue();
                            if (!a2.contains(Integer.valueOf(intValue))) {
                                abm.a(intValue);
                            }
                        }
                        Iterator<LayerItem> it2 = a2.iterator();
                        while (true) {
                            i = 0;
                            if (!it2.hasNext()) {
                                break;
                            }
                            LayerItem next = it2.next();
                            int control_status = next.getControl_status();
                            int switch_status = next.getSwitch_status();
                            if (control_status == 0) {
                                if (switch_status == 1 || switch_status == 2) {
                                    abm.a(next.getLayer_id(), true);
                                }
                            } else if (control_status == 1) {
                                abm.a(next.getLayer_id(), false);
                            }
                        }
                        aho.a(this);
                        StringBuilder sb = new StringBuilder("thread: ");
                        sb.append(Thread.currentThread().getName());
                        sb.append(" OpenLayer updated completed:");
                        if (arrayList != null) {
                            i = arrayList.size();
                        }
                        sb.append(i);
                        AMapLog.d("OpenLayerService", sb.toString());
                    }
                }
            }
        }
    }

    public Callback() {
        this.a = -1;
        this.a = 0;
    }

    public void run() {
        abl a2 = abl.a();
        if (a2.a.size() > 0) {
            Iterator<a> it = a2.a.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        }
    }
}
