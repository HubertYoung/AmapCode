package com.autonavi.minimap.offline.koala.internal;

import android.os.Binder;
import com.autonavi.minimap.offline.koala.KoalaConfig;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadDashboard;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadListener;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class KoalaDownloadBinder extends Binder {
    private Map<String, IKoalaDownloadDashboard> mDashboardMap = new HashMap();
    private IKoalaDownloadDashboard mEmptyDownloadDashboard = new a(0);

    static class a implements IKoalaDownloadDashboard {
        public final void bind(IKoalaDownloadListener iKoalaDownloadListener) {
        }

        public final void destroy() {
        }

        public final void forcePersistence() {
        }

        public final boolean hasRunningTask() {
            return false;
        }

        public final boolean isBind(IKoalaDownloadListener iKoalaDownloadListener) {
            return false;
        }

        public final boolean isRunning(int i) {
            return false;
        }

        public final void pause(int i) {
        }

        public final void pauseAll() {
        }

        public final void remove(int i) {
        }

        public final void removeAll() {
        }

        public final void resume(int i) {
        }

        public final void resumeAll() {
        }

        public final void start(String str) {
        }

        public final void start(String[] strArr) {
        }

        public final void stop(int i) {
        }

        public final void stopAll() {
        }

        public final void unbind(IKoalaDownloadListener iKoalaDownloadListener) {
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final KoalaDownloadProfile getProfile(int i) {
            return KoalaDownloadProfile.empty();
        }
    }

    public void onDestroy() {
        for (Entry next : this.mDashboardMap.entrySet()) {
            if (next.getValue() != null) {
                ((IKoalaDownloadDashboard) next.getValue()).destroy();
            }
        }
        this.mDashboardMap.clear();
        this.mDashboardMap = null;
    }

    public IKoalaDownloadDashboard getDashboard(String str, KoalaConfig koalaConfig) {
        if (this.mDashboardMap == null) {
            return this.mEmptyDownloadDashboard;
        }
        if (this.mDashboardMap.containsKey(str)) {
            return this.mDashboardMap.get(str);
        }
        dsb dsb = new dsb(str, koalaConfig);
        this.mDashboardMap.put(str, dsb);
        return dsb;
    }

    public void releaseDashboard(String str) {
        if (this.mDashboardMap.containsKey(str)) {
            this.mDashboardMap.get(str).destroy();
            this.mDashboardMap.remove(str);
        }
    }
}
