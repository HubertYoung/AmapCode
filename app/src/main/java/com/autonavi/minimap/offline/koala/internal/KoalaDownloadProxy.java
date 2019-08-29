package com.autonavi.minimap.offline.koala.internal;

import com.autonavi.minimap.offline.koala.KoalaConfig;
import com.autonavi.minimap.offline.koala.internal.KoalaDownloadClient.Action;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadDashboard;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadListener;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadProfile;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class KoalaDownloadProxy implements IKoalaDownloadDashboard {
    private List<Action> mActionList = new ArrayList();
    private KoalaConfig mConfig;
    private String mKey;

    public KoalaDownloadProxy(String str, KoalaConfig koalaConfig) {
        this.mKey = str;
        this.mConfig = koalaConfig;
    }

    private WeakReference<Action> getWeakAction(Action action) {
        this.mActionList.add(action);
        return new WeakReference<>(action);
    }

    public void start(final String str) {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.start(str);
            }
        }));
    }

    public void start(final String[] strArr) {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.start(strArr);
            }
        }));
    }

    public void stop(final int i) {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.stop(i);
            }
        }));
    }

    public void stopAll() {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.stopAll();
            }
        }));
    }

    public void pause(final int i) {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.pause(i);
            }
        }));
    }

    public void pauseAll() {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.pauseAll();
            }
        }));
    }

    public void resume(final int i) {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.resume(i);
            }
        }));
    }

    public void resumeAll() {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.resumeAll();
            }
        }));
    }

    public void remove(final int i) {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.remove(i);
            }
        }));
    }

    public void removeAll() {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.removeAll();
            }
        }));
    }

    public void bind(final IKoalaDownloadListener iKoalaDownloadListener) {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.bind(iKoalaDownloadListener);
            }
        }));
    }

    public boolean isBind(IKoalaDownloadListener iKoalaDownloadListener) {
        return KoalaDownloadClient.getInstance().isConnected() && KoalaDownloadClient.getInstance().getDashboard(this.mKey, this.mConfig).isBind(iKoalaDownloadListener);
    }

    public void unbind(final IKoalaDownloadListener iKoalaDownloadListener) {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.unbind(iKoalaDownloadListener);
            }
        }));
    }

    public void forcePersistence() {
        KoalaDownloadClient.getInstance().run(this.mKey, this.mConfig, getWeakAction(new Action() {
            public final void execute(IKoalaDownloadDashboard iKoalaDownloadDashboard) {
                iKoalaDownloadDashboard.forcePersistence();
            }
        }));
    }

    public KoalaDownloadProfile getProfile(int i) {
        if (KoalaDownloadClient.getInstance().isConnected()) {
            return KoalaDownloadClient.getInstance().getDashboard(this.mKey, this.mConfig).getProfile(i);
        }
        return KoalaDownloadProfile.empty();
    }

    public boolean hasRunningTask() {
        return KoalaDownloadClient.getInstance().isConnected() && KoalaDownloadClient.getInstance().getDashboard(this.mKey, this.mConfig).hasRunningTask();
    }

    public boolean isRunning(int i) {
        return KoalaDownloadClient.getInstance().isConnected() && KoalaDownloadClient.getInstance().getDashboard(this.mKey, this.mConfig).isRunning(i);
    }

    public void destroy() {
        this.mActionList.clear();
        this.mActionList = null;
    }
}
