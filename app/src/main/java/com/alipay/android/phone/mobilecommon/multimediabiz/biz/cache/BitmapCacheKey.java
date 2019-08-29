package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.MarkUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;

public class BitmapCacheKey extends CacheKey {
    public static final String CACHE_KEY_SEPARATOR = "##";
    public static final String CACHE_MARK_PREFIX = "mark@@";
    public static final String CACHE_MARK_SEPARATOR = "@@";
    public static final String CACHE_QUALITY_CHAR = "q";
    public static final String CACHE_QUALITY_KEY = "##q";
    public static final int RES_TYPE_NORMAL = 0;
    public static final int RES_TYPE_SRC = 1;
    @JSONField(deserialize = false, serialize = false)
    private String a;
    @JSONField(deserialize = false, serialize = false)
    public String aliasKey;
    @JSONField(deserialize = false, serialize = false)
    private String b;
    @JSONField(name = "h")
    public int height;
    @JSONField(name = "px")
    public int pixels;
    @JSONField(name = "pk")
    public String pluginKey;
    @JSONField(name = "q")
    public int quality;
    @JSONField(name = "rt")
    public int resType;
    @JSONField(name = "st")
    public int scaleType;
    @JSONField(name = "tag")
    public int tag;
    @JSONField(name = "wm")
    public String waterMarkKey;
    @JSONField(deserialize = false, serialize = false)
    public APImageMarkRequest waterMarkReq;
    @JSONField(name = "w")
    public int width;

    public BitmapCacheKey() {
        this.resType = 0;
    }

    /* JADX WARNING: Illegal instructions before constructor call commented (this can break semantics) */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public BitmapCacheKey(java.lang.String r10, int r11, int r12, com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType r13, com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin r14, int r15, com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest r16, int r17) {
        /*
            r9 = this;
            int r4 = r13.getValue()
            if (r14 != 0) goto L_0x0016
            r5 = 0
        L_0x0007:
            java.lang.String r7 = a(r16)
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
            r6 = r15
            r8 = r17
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        L_0x0016:
            java.lang.String r5 = r14.getPluginKey()
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey.<init>(java.lang.String, int, int, com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType, com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin, int, com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest, int):void");
    }

    public BitmapCacheKey(String key, int width2, int height2, CutScaleType scaleType2, ImageWorkerPlugin plugin, int quality2, APImageMarkRequest imageMarkRequest) {
        this(key, width2, height2, scaleType2, plugin, quality2, imageMarkRequest, 0);
    }

    public BitmapCacheKey(String key, int width2, int height2, int scaleType2, String pluginKey2, int quality2, String waterMarkKey2) {
        this(key, width2, height2, scaleType2, pluginKey2, quality2, waterMarkKey2, 0);
    }

    public BitmapCacheKey(String key, int width2, int height2, int scaleType2, String pluginKey2, int quality2, String waterMarkKey2, int resType2) {
        super(key);
        this.resType = 0;
        this.width = width2;
        this.height = height2;
        this.scaleType = scaleType2;
        this.pluginKey = pluginKey2;
        this.waterMarkKey = waterMarkKey2;
        this.quality = quality2;
        this.pixels = a(width2, height2);
        this.tag = b(width2, height2);
        this.resType = resType2;
    }

    private static int a(int width2, int height2) {
        if (width2 <= 0 || height2 <= 0) {
            return 1638400;
        }
        if (width2 == Integer.MAX_VALUE || height2 == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return width2 * height2;
    }

    private static int b(int width2, int height2) {
        if (width2 <= 0 || height2 <= 0) {
            return 512;
        }
        if (width2 == Integer.MAX_VALUE || height2 == Integer.MAX_VALUE) {
            return 128;
        }
        return 256;
    }

    public String complexCacheKey() {
        if (this.a == null) {
            this.a = a(this.key);
        }
        return this.a;
    }

    private static String a(APImageMarkRequest waterMarkReq2) {
        if (waterMarkReq2 == null || !MarkUtil.isValidMarkRequest(waterMarkReq2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("mark@@").append(waterMarkReq2.getMarkId()).append("@@").append(waterMarkReq2.getPosition()).append("@@").append(waterMarkReq2.getTransparency()).append("@@").append(waterMarkReq2.getMarkWidth()).append("@@").append(waterMarkReq2.getMarkHeight()).append("@@").append(waterMarkReq2.getPaddingX()).append("@@").append(waterMarkReq2.getPaddingY()).append("@@").append(waterMarkReq2.getPercent());
        return sb.toString();
    }

    private String a(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(key).append("##").append(this.width).append("##").append(this.height).append("##").append(this.scaleType);
        if (this.pluginKey != null) {
            sb.append("##").append(this.pluginKey);
        }
        if (this.waterMarkKey != null) {
            if (this.pluginKey == null) {
                sb.append("##no_plugin");
            }
            sb.append("##").append(this.waterMarkKey);
        } else if (this.waterMarkReq != null && MarkUtil.isValidMarkRequest(this.waterMarkReq)) {
            if (this.pluginKey == null) {
                sb.append("##no_plugin");
            }
            sb.append("##").append(a(this.waterMarkReq));
        }
        if (CacheUtils.qualityCachekeyCheck(this.quality) && PathUtils.isDjangoPath(key)) {
            sb.append("##q").append(this.quality);
        }
        if (this.resType == 1) {
            sb.append('&').append("rt=").append(this.resType);
        }
        return sb.toString();
    }

    public BitmapCacheKey updateAliasKey(String aliasKey2) {
        this.aliasKey = aliasKey2;
        this.b = null;
        return this;
    }

    public BitmapCacheKey updateQuality(int quality2) {
        this.quality = quality2;
        this.b = null;
        this.a = null;
        return this;
    }

    public String aliasComplexKey() {
        if (this.aliasKey != null && this.b == null) {
            this.b = a(this.aliasKey);
        }
        return this.b;
    }

    public String genJsonExtra() {
        return JSON.toJSONString(this);
    }

    public BitmapCacheKey addTag(int tag2) {
        this.tag |= tag2;
        return this;
    }

    public BitmapCacheKey removeTag(int tag2) {
        if ((this.tag & tag2) == tag2) {
            this.tag ^= tag2;
        }
        return this;
    }

    public boolean equals(Object o) {
        if (o instanceof BitmapCacheKey) {
            return complexCacheKey().equals(((BitmapCacheKey) o).complexCacheKey()) || (this.aliasKey != null && aliasComplexKey().equals(((BitmapCacheKey) o).aliasComplexKey()));
        }
        return super.equals(o);
    }

    public static BitmapCacheKey create(String extraJson) {
        return create(extraJson, (String) null);
    }

    public static BitmapCacheKey create(String extraJson, String key) {
        try {
            BitmapCacheKey cacheKey = (BitmapCacheKey) JSON.parseObject(extraJson, BitmapCacheKey.class);
            cacheKey.key = key;
            return cacheKey;
        } catch (Throwable t) {
            Logger.E((String) "CacheKey", t, "parseObj, extraJson: " + extraJson, new Object[0]);
            return null;
        }
    }

    public static BitmapCacheKey create(BitmapCacheKey key) {
        return create(key, (String) null);
    }

    public static BitmapCacheKey create(BitmapCacheKey key, String newKey) {
        String str;
        BitmapCacheKey newCacheKey = null;
        if (key != null) {
            if (newKey == null) {
                str = key.key;
            } else {
                str = newKey;
            }
            newCacheKey = new BitmapCacheKey(str, key.width, key.height, key.scaleType, key.pluginKey, key.quality, key.waterMarkKey);
        }
        return newCacheKey;
    }

    public static String extractPath(String complexKey) {
        if (!TextUtils.isEmpty(complexKey) && complexKey.contains("##")) {
            String[] parts = complexKey.split("##", 2);
            if (parts.length > 0 && FileUtils.checkFile(parts[0])) {
                return parts[0];
            }
        }
        return null;
    }

    public String toString() {
        return "[ BitmapCacheKey ]: { complexKey: " + complexCacheKey() + ", aliasComplexKey: " + aliasComplexKey() + ", tag: " + this.tag + " }";
    }
}
