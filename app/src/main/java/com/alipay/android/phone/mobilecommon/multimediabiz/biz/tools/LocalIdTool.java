package com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools;

import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.db.LocalIdDao;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.db.LocalIdDao.Config;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.utils.MD5Util;
import java.util.HashMap;
import java.util.Map;

public class LocalIdTool {
    public static final String PREFIX = "apml";
    private static LocalIdTool a;
    private Map<String, String> b = new HashMap();
    private LruCache<String, String> c = new LruCache<>(1000);
    private LocalIdDao d;

    private LocalIdTool() {
        long start = System.currentTimeMillis();
        this.d = new LocalIdDao(this.b);
        Logger.D("LocalIdTool", "LocalIdTool init time=" + (System.currentTimeMillis() - start), new Object[0]);
    }

    public static synchronized LocalIdTool get() {
        LocalIdTool localIdTool;
        synchronized (LocalIdTool.class) {
            try {
                if (a == null) {
                    a = new LocalIdTool();
                }
                localIdTool = a;
            }
        }
        return localIdTool;
    }

    public String encodeToLocalId(String path) {
        String localId;
        if (path == null) {
            return null;
        }
        String str = path;
        if (TextUtils.isEmpty(path) || path.startsWith(PREFIX)) {
            return str;
        }
        String cache = (String) this.c.get(path);
        if (cache == null) {
            localId = new StringBuilder(PREFIX).append(MD5Util.getMD5String(path)).toString();
            this.b.put(localId, path);
            this.c.put(path, localId);
        } else {
            localId = cache;
        }
        this.d.save(localId, path);
        return localId;
    }

    public String decodeToPath(String localId) {
        if (localId == null) {
            return null;
        }
        String str = localId;
        if (!isLocalIdRes(localId)) {
            return str;
        }
        String path = this.b.get(localId);
        if (path != null) {
            this.d.save(localId, path);
            return path;
        } else if (!AppUtils.isInTinyProcess() || ConfigManager.getInstance().getCommonConfigItem().loacalIdQueryDbSwitch != 1) {
            return localId;
        } else {
            String path2 = this.d.queryPathByLocalId(localId);
            if (TextUtils.isEmpty(path2)) {
                return localId;
            }
            this.b.put(localId, path2);
            return path2;
        }
    }

    public static boolean isLocalIdRes(String path) {
        return !TextUtils.isEmpty(path) && path.startsWith(PREFIX);
    }

    public void setConfig(Config config) {
        this.d.setConfig(config);
    }
}
