package com.autonavi.minimap.offline.auto.protocol;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.minimap.offline.auto.model.ATJsApkInfo;
import com.autonavi.minimap.offline.auto.model.ATJsApkInfo.ATJsApkItem;
import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.KoalaUtils;
import com.autonavi.minimap.offline.koala.intf.IKoalaPersistence;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadModel;
import com.autonavi.minimap.offline.koala.model.KoalaDownloadSpecialData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ApkDownloadPersistence implements IKoalaPersistence {
    private static final String PREF_FILE = "auto_apk_download";
    private static final String PREF_KEY = "download";
    private ATJsApkInfo mApkInfo;
    private HashMap<String, a> mExtraMap;
    private SharedPreferences mPref;

    static final class a {
        public String a;
        public String b;
        public String c;
        public String d;
        public long e;
        public String f;

        public a(String str, String str2, String str3, String str4, long j, String str5) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
            this.e = j;
            this.f = str5;
        }
    }

    static class b {
        /* access modifiers changed from: private */
        public static ApkDownloadPersistence a = new ApkDownloadPersistence();
    }

    private ApkDownloadPersistence() {
        this.mExtraMap = new HashMap<>();
        this.mPref = AMapAppGlobal.getApplication().getSharedPreferences(PREF_FILE, 0);
    }

    public static ApkDownloadPersistence get() {
        return b.a;
    }

    public synchronized ATJsApkInfo getApkInfo() {
        try {
            loadApkInfo();
        }
        return this.mApkInfo;
    }

    public synchronized ATJsApkItem getLatestApkItem() {
        try {
            loadApkInfo();
            if (this.mApkInfo.items != null) {
                if (this.mApkInfo.items.size() != 0) {
                    for (ATJsApkItem next : this.mApkInfo.items) {
                        if (next.status != KoalaDownloadStatus.COMPLETE.getValue()) {
                            return next;
                        }
                    }
                    return this.mApkInfo.items.get(0);
                }
            }
            return new ATJsApkItem();
        }
    }

    private void loadApkInfo() {
        if (this.mApkInfo == null) {
            String string = this.mPref.getString("download", "");
            if (TextUtils.isEmpty(string)) {
                this.mApkInfo = new ATJsApkInfo();
                return;
            }
            try {
                ATJsApkInfo aTJsApkInfo = (ATJsApkInfo) JsonUtil.fromString(string, ATJsApkInfo.class);
                if (aTJsApkInfo.items == null) {
                    aTJsApkInfo.items = new ArrayList();
                }
                this.mApkInfo = aTJsApkInfo;
            } catch (Exception e) {
                e.printStackTrace();
                this.mApkInfo = new ATJsApkInfo();
            }
            forceCheck();
        }
    }

    public synchronized void forceCheck() {
        if (this.mApkInfo != null) {
            for (ATJsApkItem next : this.mApkInfo.items) {
                if (!(next.status == KoalaDownloadStatus.DOWNLOADING.getValue() || next.status == KoalaDownloadStatus.CONNECTED.getValue())) {
                    if (next.status != KoalaDownloadStatus.BLOCK_COMPLETE.getValue()) {
                        if (next.status == KoalaDownloadStatus.COMPLETE.getValue()) {
                            next.downloadBytes = KoalaUtils.getFileSize(next.local_file);
                            if (next.downloadBytes <= 0) {
                                next.status = KoalaDownloadStatus.ERROR.getValue();
                            }
                        }
                    }
                }
                next.status = KoalaDownloadStatus.PAUSE.getValue();
                next.downloadBytes = KoalaUtils.getFileSize(next.local_file);
                if (next.downloadBytes == next.totalBytes) {
                    next.status = KoalaDownloadStatus.COMPLETE.getValue();
                }
            }
        }
    }

    public synchronized void addExtra(String str, String str2, String str3, String str4, String str5, long j, String str6) {
        synchronized (this) {
            try {
                HashMap<String, a> hashMap = this.mExtraMap;
                a aVar = new a(str2, str3, str4, str5, j, str6);
                hashMap.put(str, aVar);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public synchronized KoalaDownloadModel load(String str) {
        KoalaDownloadModel koalaDownloadModel;
        loadApkInfo();
        koalaDownloadModel = new KoalaDownloadModel(str);
        for (ATJsApkItem next : this.mApkInfo.items) {
            KoalaDownloadEntity koalaDownloadEntity = new KoalaDownloadEntity(koalaDownloadModel);
            koalaDownloadEntity.setId(next.id);
            koalaDownloadEntity.setStatus(KoalaDownloadStatus.valueOf(next.status));
            KoalaDownloadSpecialData koalaDownloadSpecialData = new KoalaDownloadSpecialData(koalaDownloadEntity);
            koalaDownloadSpecialData.setUrl(next.url);
            koalaDownloadSpecialData.setLocalPath(next.local_file);
            koalaDownloadSpecialData.setDownloadBytes(next.downloadBytes);
            koalaDownloadSpecialData.setTotalBytes(next.totalBytes);
            koalaDownloadEntity.setSpecialDatas(new KoalaDownloadSpecialData[]{koalaDownloadSpecialData});
            koalaDownloadModel.add(koalaDownloadEntity);
        }
        return koalaDownloadModel;
    }

    public synchronized void save(KoalaDownloadModel koalaDownloadModel) {
        if (koalaDownloadModel != null) {
            loadApkInfo();
            Iterator it = koalaDownloadModel.iterator();
            while (it.hasNext()) {
                KoalaDownloadEntity koalaDownloadEntity = (KoalaDownloadEntity) it.next();
                KoalaDownloadSpecialData[] specialDatas = koalaDownloadEntity.getSpecialDatas();
                if (!(specialDatas == null || specialDatas.length == 0)) {
                    KoalaDownloadSpecialData koalaDownloadSpecialData = specialDatas[0];
                    ATJsApkItem findApkItem = findApkItem(koalaDownloadEntity.getId());
                    if (findApkItem == null) {
                        findApkItem = new ATJsApkItem();
                        findApkItem.id = koalaDownloadEntity.getId();
                        this.mApkInfo.items.add(findApkItem);
                        if (this.mExtraMap.containsKey(koalaDownloadSpecialData.getUrl())) {
                            a aVar = this.mExtraMap.get(koalaDownloadSpecialData.getUrl());
                            findApkItem.build = aVar.a;
                            findApkItem.display_ver = aVar.c;
                            findApkItem.div = aVar.d;
                            findApkItem.version = aVar.b;
                            findApkItem.totalBytes = aVar.e;
                            findApkItem.sendParams = aVar.f;
                            this.mExtraMap.remove(findApkItem.url);
                        }
                    }
                    findApkItem.url = koalaDownloadSpecialData.getUrl();
                    findApkItem.local_file = koalaDownloadSpecialData.getLocalPath();
                    findApkItem.status = koalaDownloadEntity.getStatus().getValue();
                    findApkItem.downloadBytes = koalaDownloadSpecialData.getDownloadBytes();
                    findApkItem.errorCause = koalaDownloadEntity.getErrorCause();
                }
            }
            for (int size = this.mApkInfo.items.size() - 1; size >= 0; size--) {
                if (findDownloadEntity(koalaDownloadModel, this.mApkInfo.items.get(size).id) == null) {
                    this.mApkInfo.items.remove(size);
                }
            }
            save();
        }
    }

    public synchronized void save() {
        loadApkInfo();
        try {
            this.mPref.edit().putString("download", JsonUtil.toString(this.mApkInfo)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private KoalaDownloadEntity findDownloadEntity(KoalaDownloadModel koalaDownloadModel, int i) {
        Iterator it = koalaDownloadModel.iterator();
        while (it.hasNext()) {
            KoalaDownloadEntity koalaDownloadEntity = (KoalaDownloadEntity) it.next();
            if (koalaDownloadEntity.getId() == i) {
                return koalaDownloadEntity;
            }
        }
        return null;
    }

    public synchronized ATJsApkItem findApkItem(int i) {
        try {
            for (ATJsApkItem next : this.mApkInfo.items) {
                if (next.id == i) {
                    return next;
                }
            }
            return null;
        }
    }
}
