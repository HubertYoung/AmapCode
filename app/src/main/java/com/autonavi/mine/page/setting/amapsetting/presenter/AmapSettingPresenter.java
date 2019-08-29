package com.autonavi.mine.page.setting.amapsetting.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.ae.dice.NaviEngine;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.mine.page.setting.amapsetting.page.AmapSettingPage;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Iterator;

public final class AmapSettingPresenter extends AbstractBasePresenter<AmapSettingPage> {
    public b a;
    public ProgressDlg b;
    /* access modifiers changed from: private */
    public boolean c = true;
    /* access modifiers changed from: private */
    public defpackage.epy.a d;
    /* access modifiers changed from: private */
    public Thread e;
    private BroadcastReceiver f;
    /* access modifiers changed from: private */
    public final a g = new a();

    static class AmapSettingReceiver extends BroadcastReceiver {
        private WeakReference<AmapSettingPresenter> a;

        public AmapSettingReceiver(AmapSettingPresenter amapSettingPresenter) {
            this.a = new WeakReference<>(amapSettingPresenter);
        }

        public void onReceive(Context context, Intent intent) {
            if (this.a != null && intent != null) {
                AmapSettingPresenter amapSettingPresenter = (AmapSettingPresenter) this.a.get();
                if (amapSettingPresenter.mPage != null) {
                    AmapSettingPage amapSettingPage = (AmapSettingPage) amapSettingPresenter.mPage;
                    boolean booleanExtra = intent.getBooleanExtra(UploadConstants.STATUS_PUSH_NOTIFIED, false);
                    if (booleanExtra != amapSettingPage.e.isChecked()) {
                        amapSettingPage.e.setChecked(booleanExtra);
                        amapSettingPage.e.setContentDescription(amapSettingPage.getString(booleanExtra ? R.string.checkbox_open_desc : R.string.checkbox_close_desc));
                        bim.aa().a((String) UploadConstants.STATUS_PUSH_NOTIFIED, booleanExtra ? 1 : 0);
                    }
                }
            }
        }
    }

    class a implements Runnable {
        boolean a = false;

        a() {
        }

        public final void run() {
            try {
                this.a = true;
                AmapSettingPresenter amapSettingPresenter = AmapSettingPresenter.this;
                StringBuilder sb = new StringBuilder();
                sb.append(FileUtil.getFilesDir().toString());
                sb.append("/discovery/");
                AmapSettingPresenter.a(amapSettingPresenter, sb.toString());
                if (!Environment.getExternalStorageState().equals("mounted")) {
                    AmapSettingPresenter amapSettingPresenter2 = AmapSettingPresenter.this;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(FileUtil.getCacheDir().toString());
                    sb2.append("/");
                    AmapSettingPresenter.a(amapSettingPresenter2, sb2.toString());
                    AmapSettingPresenter.this.b.cancel();
                    ((AmapSettingPage) AmapSettingPresenter.this.mPage).a(R.string.history_has_del);
                } else {
                    File externalStorageDirectory = Environment.getExternalStorageDirectory();
                    String path = Environment.getExternalStorageDirectory().getPath();
                    if (externalStorageDirectory != null && externalStorageDirectory.exists()) {
                        path = externalStorageDirectory.toString();
                    }
                    AmapSettingPresenter amapSettingPresenter3 = AmapSettingPresenter.this;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(path);
                    sb3.append("/autonavi/imagecache/");
                    AmapSettingPresenter.a(amapSettingPresenter3, sb3.toString());
                    AmapSettingPresenter amapSettingPresenter4 = AmapSettingPresenter.this;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(path);
                    sb4.append("/autonavi/panocache/");
                    AmapSettingPresenter.a(amapSettingPresenter4, sb4.toString());
                    AmapSettingPresenter amapSettingPresenter5 = AmapSettingPresenter.this;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(path);
                    sb5.append("/autonavi/log/");
                    AmapSettingPresenter.a(amapSettingPresenter5, sb5.toString());
                    AmapSettingPresenter amapSettingPresenter6 = AmapSettingPresenter.this;
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(path);
                    sb6.append("/autonavi/tmp/");
                    AmapSettingPresenter.a(amapSettingPresenter6, sb6.toString());
                    String mapBaseStorage = FileUtil.getMapBaseStorage(AMapAppGlobal.getApplication().getApplicationContext());
                    AmapSettingPresenter amapSettingPresenter7 = AmapSettingPresenter.this;
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(mapBaseStorage);
                    sb7.append("/autonavi/AnGeoMap/EagleMapCache/");
                    AmapSettingPresenter.a(amapSettingPresenter7, sb7.toString());
                    AmapSettingPresenter amapSettingPresenter8 = AmapSettingPresenter.this;
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(mapBaseStorage);
                    sb8.append("/autonavi/AnGeoMap/");
                    AmapSettingPresenter.a(amapSettingPresenter8, sb8.toString(), "cache");
                    awo awo = (awo) defpackage.esb.a.a.a(awo.class);
                    if (awo != null) {
                        awo.a(9001);
                        awo.a(9003);
                    }
                    AmapSettingPresenter.a();
                    if (awo != null) {
                        awo.a(awo.c);
                        if (awo.d()) {
                            awo.a(awo.d);
                        }
                    }
                    if (DoNotUseTool.getMapView() != null) {
                        NaviEngine.clearCache();
                    }
                    AmapSettingPresenter.this.b.cancel();
                    ((AmapSettingPage) AmapSettingPresenter.this.mPage).a(R.string.history_has_del);
                }
            } finally {
                this.a = false;
            }
        }
    }

    public AmapSettingPresenter(AmapSettingPage amapSettingPage) {
        super(amapSettingPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        bim.aa().z();
        b();
        bim.aa().a((biw) new biw() {
            public final void a() {
                AmapSettingPresenter.this.b();
            }
        });
        this.a = new b() {
            public final void a() {
                if (!FileUtil.iSHasSdcardPath(((AmapSettingPage) AmapSettingPresenter.this.mPage).getContext())) {
                    if (AmapSettingPresenter.this.d != null) {
                        AmapSettingPresenter.this.d.a();
                    }
                    return;
                }
                if (!AmapSettingPresenter.this.g.a) {
                    AmapSettingPresenter.this.e = new Thread(AmapSettingPresenter.this.g, "AmapSettingFragmentThread");
                    AmapSettingPresenter.this.e.start();
                }
            }

            public final void a(Context context) {
                SearchHistoryHelper.getInstance(context).deleteAll();
                dfm dfm = (dfm) ank.a(dfm.class);
                if (dfm != null) {
                    dfm.g();
                }
                new epz(context);
                lc lcVar = new lc("QuickNaviHistory");
                if (lcVar.a.exists()) {
                    lcVar.a.delete();
                }
                si.a().b();
            }
        };
        this.d = new defpackage.epy.a() {
            public final void a() {
                if (!AmapSettingPresenter.this.g.a) {
                    AmapSettingPresenter.this.e = new Thread(AmapSettingPresenter.this.g, "AmapSettingFragmentThread");
                    AmapSettingPresenter.this.e.start();
                }
            }
        };
        this.f = new AmapSettingReceiver(this);
        LocalBroadcastManager.getInstance(((AmapSettingPage) this.mPage).getContext().getApplicationContext()).registerReceiver(this.f, new IntentFilter(UploadConstants.STATUS_PUSH_NOTIFIED));
    }

    public final void onDestroy() {
        super.onDestroy();
        bim.aa().a((biw) null);
        LocalBroadcastManager.getInstance(((AmapSettingPage) this.mPage).getContext()).unregisterReceiver(this.f);
    }

    /* access modifiers changed from: private */
    public void b() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                AmapSettingPage amapSettingPage = (AmapSettingPage) AmapSettingPresenter.this.mPage;
                amapSettingPage.d.setChecked(bim.aa().k((String) UploadConstants.STATUS_PUSH_RECEIVED));
                amapSettingPage.e.setChecked(bim.aa().k((String) UploadConstants.STATUS_PUSH_NOTIFIED));
            }
        });
    }

    private void a(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            if (listFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    if (this.c) {
                        listFiles[i].delete();
                    } else {
                        return;
                    }
                } else if (listFiles[i].isDirectory()) {
                    a(listFiles[i]);
                }
            }
            file.delete();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((AmapSettingPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final void onResume() {
        super.onResume();
        ((AmapSettingPage) this.mPage).a();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        ((AmapSettingPage) this.mPage).getClass();
        if (i == 1) {
            ((AmapSettingPage) this.mPage).a();
        }
    }

    public final void onStop() {
        boolean z;
        fhb fhb = (fhb) defpackage.esb.a.a.a(fhb.class);
        if (fhb != null) {
            fhc a2 = fhb.a();
            if (a2 != null) {
                AmapSettingPage amapSettingPage = (AmapSettingPage) this.mPage;
                if (amapSettingPage.d == null) {
                    z = false;
                } else {
                    z = amapSettingPage.d.isChecked();
                }
                if (z) {
                    a2.a();
                } else {
                    a2.b();
                }
            }
        }
        super.onStop();
    }

    static /* synthetic */ void a(AmapSettingPresenter amapSettingPresenter, String str) {
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                int i = 0;
                while (i < listFiles.length) {
                    if (listFiles[i].isDirectory()) {
                        amapSettingPresenter.a(listFiles[i]);
                    } else {
                        listFiles[i].delete();
                    }
                    if (amapSettingPresenter.c) {
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    static /* synthetic */ void a(AmapSettingPresenter amapSettingPresenter, String str, String str2) {
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                int i = 0;
                while (i < listFiles.length) {
                    if (listFiles != null && !listFiles[i].isDirectory()) {
                        String name = listFiles[i].getName();
                        try {
                            if (name.substring(name.lastIndexOf("") + 1).equals(str2)) {
                                listFiles[i].delete();
                            }
                        } catch (IndexOutOfBoundsException unused) {
                        }
                    }
                    if (amapSettingPresenter.c) {
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    static /* synthetic */ void a() {
        if (DoNotUseTool.getMapManager() != null && DoNotUseTool.getSuspendManager() != null) {
            cde suspendManager = DoNotUseTool.getSuspendManager();
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (suspendManager != null && mapManager != null) {
                awo awo = (awo) defpackage.esb.a.a.a(awo.class);
                if (awo != null) {
                    Iterator<LayerItem> it = awo.i().iterator();
                    while (it.hasNext()) {
                        LayerItem next = it.next();
                        awo.a(next.getLayer_id());
                        awo.b(next.getLayer_id());
                    }
                }
            }
        }
    }
}
