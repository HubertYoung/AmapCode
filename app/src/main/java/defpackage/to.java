package defpackage;

import android.media.AudioManager;
import android.support.annotation.NonNull;
import com.amap.bundle.drivecommon.tools.TripSpUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;

/* renamed from: to reason: default package */
/* compiled from: DriveAudioUtils */
public final class to {
    public static boolean a(@NonNull AbstractBasePage abstractBasePage) {
        boolean tripBroadCastState = TripSpUtil.getTripBroadCastState(AMapAppGlobal.getApplication());
        if (!tripBroadCastState) {
            tr.a(abstractBasePage, abstractBasePage.getString(R.string.autonavi_navi_audio_switch_closed), R.drawable.voice_closed);
        }
        return !tripBroadCastState;
    }

    public static void a(@NonNull final AbstractBasePage abstractBasePage, final boolean z) {
        aho.a(new Runnable() {
            public final void run() {
                if (abstractBasePage != null && abstractBasePage.isAlive()) {
                    to.b(abstractBasePage);
                    if (z) {
                        AbstractBasePage abstractBasePage = abstractBasePage;
                        if (abstractBasePage != null) {
                            AudioManager audioManager = (AudioManager) abstractBasePage.getActivity().getSystemService("audio");
                            if (audioManager != null) {
                                try {
                                    if (audioManager.getStreamVolume(3) < audioManager.getStreamMaxVolume(3) / 3) {
                                        tr.a(abstractBasePage, abstractBasePage.getString(R.string.autonavi_volume_is_too_low), R.drawable.voice);
                                    }
                                } catch (Exception unused) {
                                }
                            }
                        }
                    }
                }
            }
        }, 100);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean b(@android.support.annotation.NonNull final com.autonavi.map.fragmentcontainer.page.AbstractBasePage r12) {
        /*
            android.bluetooth.BluetoothAdapter r0 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            r1 = 12
            r2 = 0
            r3 = 3
            r4 = -1
            r5 = 1
            r6 = 2
            if (r0 == 0) goto L_0x007a
            android.bluetooth.BluetoothAdapter r7 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            int r7 = r7.getState()
            if (r7 != r1) goto L_0x0037
            android.bluetooth.BluetoothAdapter r7 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            int r8 = r7.getProfileConnectionState(r6)
            int r9 = r7.getProfileConnectionState(r5)
            int r7 = r7.getProfileConnectionState(r3)
            if (r8 != r6) goto L_0x002b
            r7 = r8
            goto L_0x0033
        L_0x002b:
            if (r9 != r6) goto L_0x002f
            r7 = r9
            goto L_0x0033
        L_0x002f:
            if (r7 != r6) goto L_0x0032
            goto L_0x0033
        L_0x0032:
            r7 = -1
        L_0x0033:
            if (r7 == r4) goto L_0x0037
            r7 = 1
            goto L_0x0038
        L_0x0037:
            r7 = 0
        L_0x0038:
            if (r7 == 0) goto L_0x007a
            java.util.Set r7 = r0.getBondedDevices()
            int r8 = r7.size()
            if (r8 <= 0) goto L_0x007a
            java.util.Iterator r7 = r7.iterator()
        L_0x0048:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x007a
            java.lang.Object r8 = r7.next()
            android.bluetooth.BluetoothDevice r8 = (android.bluetooth.BluetoothDevice) r8
            if (r8 == 0) goto L_0x0048
            org.json.JSONObject r9 = new org.json.JSONObject
            r9.<init>()
            java.lang.String r10 = "itemID"
            java.lang.String r11 = r8.getAddress()     // Catch:{ JSONException -> 0x006e }
            r9.put(r10, r11)     // Catch:{ JSONException -> 0x006e }
            java.lang.String r10 = "itemName"
            java.lang.String r8 = r8.getName()     // Catch:{ JSONException -> 0x006e }
            r9.put(r10, r8)     // Catch:{ JSONException -> 0x006e }
            goto L_0x0072
        L_0x006e:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0072:
            java.lang.String r8 = "P00025"
            java.lang.String r10 = "B102"
            com.amap.bundle.statistics.util.LogUtil.actionLogV2(r8, r10, r9)
            goto L_0x0048
        L_0x007a:
            if (r0 == 0) goto L_0x0083
            int r7 = r0.getState()
            if (r7 == r1) goto L_0x0083
            return r2
        L_0x0083:
            if (r0 == 0) goto L_0x00ab
            int r1 = r0.getProfileConnectionState(r6)
            int r7 = r0.getProfileConnectionState(r5)
            int r8 = r0.getProfileConnectionState(r3)
            if (r1 != r6) goto L_0x0095
            r3 = 2
            goto L_0x009d
        L_0x0095:
            if (r7 != r6) goto L_0x0099
            r3 = 1
            goto L_0x009d
        L_0x0099:
            if (r8 != r6) goto L_0x009c
            goto L_0x009d
        L_0x009c:
            r3 = -1
        L_0x009d:
            if (r3 == r4) goto L_0x00ab
            android.content.Context r1 = r12.getContext()
            to$2 r4 = new to$2
            r4.<init>(r12)
            r0.getProfileProxy(r1, r4, r3)
        L_0x00ab:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.to.b(com.autonavi.map.fragmentcontainer.page.AbstractBasePage):boolean");
    }
}
