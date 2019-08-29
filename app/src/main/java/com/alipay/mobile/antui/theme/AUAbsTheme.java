package com.alipay.mobile.antui.theme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.alipay.mobile.antui.constant.AUConstant;
import java.util.HashMap;
import java.util.Map;

public abstract class AUAbsTheme {
    private Map<String, Object> mCurrentTheme = new HashMap();

    /* access modifiers changed from: protected */
    public abstract void initTheme();

    public AUAbsTheme() {
        initTheme();
    }

    public boolean containsKey(String key) {
        return this.mCurrentTheme.containsKey(key);
    }

    public void put(String key, Object value) {
        this.mCurrentTheme.put(key, value);
    }

    public Map<String, Object> getTheme() {
        return this.mCurrentTheme;
    }

    public Integer getResId(String key) {
        return getResId(key, null);
    }

    public Integer getResId(String key, Integer defValue) {
        Object result = this.mCurrentTheme.get(key);
        if (result instanceof Integer) {
            return (Integer) result;
        }
        return defValue;
    }

    public Integer getColor(Context context, String key) {
        return getColor(context, key, null);
    }

    public Integer getColor(Context context, String key, Integer defValue) {
        Object result = this.mCurrentTheme.get(key);
        try {
            if (result instanceof Integer) {
                return Integer.valueOf(context.getResources().getColor(((Integer) result).intValue()));
            }
            if (result instanceof String) {
                return Integer.valueOf(Color.parseColor((String) result));
            }
            return defValue;
        } catch (Throwable tr) {
            Log.e(AUConstant.TAG, AUAbsTheme.class.getSimpleName(), tr);
            return defValue;
        }
    }

    public Float getDimension(Context context, String key) {
        return getDimension(context, key, null);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Float getDimension(android.content.Context r8, java.lang.String r9, java.lang.Float r10) {
        /*
            r7 = this;
            java.util.Map<java.lang.String, java.lang.Object> r4 = r7.mCurrentTheme
            java.lang.Object r2 = r4.get(r9)
            boolean r4 = r2 instanceof java.lang.Float     // Catch:{ Throwable -> 0x006d }
            if (r4 == 0) goto L_0x000d
            java.lang.Float r2 = (java.lang.Float) r2     // Catch:{ Throwable -> 0x006d }
        L_0x000c:
            return r2
        L_0x000d:
            boolean r4 = r2 instanceof java.lang.Integer     // Catch:{ Throwable -> 0x006d }
            if (r4 == 0) goto L_0x0079
            r4 = 0
            r5 = 0
            boolean r4 = com.alipay.mobile.antui.screenadpt.AUScreenUtils.checkApFlag(r8, r4, r5)     // Catch:{ Throwable -> 0x006d }
            if (r4 == 0) goto L_0x005a
            r0 = r2
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ Exception -> 0x0032 }
            r4 = r0
            int r4 = r4.intValue()     // Catch:{ Exception -> 0x0032 }
            int r4 = com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool.getApFromDimen(r8, r4)     // Catch:{ Exception -> 0x0032 }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x0032 }
            float r4 = java.lang.Float.parseFloat(r4)     // Catch:{ Exception -> 0x0032 }
            java.lang.Float r2 = java.lang.Float.valueOf(r4)     // Catch:{ Exception -> 0x0032 }
            goto L_0x000c
        L_0x0032:
            r1 = move-exception
            java.lang.String r4 = "AUAbsTheme"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x006d }
            java.lang.String r6 = "ap适配，异常 e= "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x006d }
            java.lang.StringBuilder r5 = r5.append(r1)     // Catch:{ Throwable -> 0x006d }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x006d }
            com.alipay.mobile.antui.utils.AuiLogger.debug(r4, r5)     // Catch:{ Throwable -> 0x006d }
            android.content.res.Resources r4 = r8.getResources()     // Catch:{ Throwable -> 0x006d }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Throwable -> 0x006d }
            int r5 = r2.intValue()     // Catch:{ Throwable -> 0x006d }
            float r4 = r4.getDimension(r5)     // Catch:{ Throwable -> 0x006d }
            java.lang.Float r2 = java.lang.Float.valueOf(r4)     // Catch:{ Throwable -> 0x006d }
            goto L_0x000c
        L_0x005a:
            android.content.res.Resources r4 = r8.getResources()     // Catch:{ Throwable -> 0x006d }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Throwable -> 0x006d }
            int r5 = r2.intValue()     // Catch:{ Throwable -> 0x006d }
            float r4 = r4.getDimension(r5)     // Catch:{ Throwable -> 0x006d }
            java.lang.Float r2 = java.lang.Float.valueOf(r4)     // Catch:{ Throwable -> 0x006d }
            goto L_0x000c
        L_0x006d:
            r3 = move-exception
            java.lang.String r4 = "antui"
            java.lang.Class<com.alipay.mobile.antui.theme.AUAbsTheme> r5 = com.alipay.mobile.antui.theme.AUAbsTheme.class
            java.lang.String r5 = r5.getSimpleName()
            android.util.Log.e(r4, r5, r3)
        L_0x0079:
            r2 = r10
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.antui.theme.AUAbsTheme.getDimension(android.content.Context, java.lang.String, java.lang.Float):java.lang.Float");
    }

    public Integer getDimensionPixelOffset(Context context, String key) {
        return getDimensionPixelOffset(context, key, null);
    }

    public Integer getDimensionPixelOffset(Context context, String key, Integer defValue) {
        Object result = this.mCurrentTheme.get(key);
        try {
            if (result instanceof String) {
                return Integer.valueOf((String) result);
            }
            if (result instanceof Integer) {
                return Integer.valueOf(context.getResources().getDimensionPixelOffset(((Integer) result).intValue()));
            }
            return defValue;
        } catch (Throwable tr) {
            Log.e(AUConstant.TAG, AUAbsTheme.class.getSimpleName(), tr);
            return defValue;
        }
    }

    public Drawable getDrawable(Context context, String key) {
        return getDrawable(context, key, null);
    }

    public Drawable getDrawable(Context context, String key, Drawable defValue) {
        Object result = this.mCurrentTheme.get(key);
        try {
            if (result instanceof Drawable) {
                return (Drawable) result;
            }
            if (result instanceof Integer) {
                if (context == null) {
                    return null;
                }
                return context.getResources().getDrawable(((Integer) result).intValue());
            }
            return defValue;
        } catch (Throwable tr) {
            Log.e(AUConstant.TAG, AUAbsTheme.class.getSimpleName(), tr);
        }
    }

    public ColorStateList getColorStateList(Context context, String key) {
        return getColorStateList(context, key, null);
    }

    public ColorStateList getColorStateList(Context context, String key, ColorStateList defValue) {
        Object result = this.mCurrentTheme.get(key);
        try {
            if (result instanceof ColorStateList) {
                return (ColorStateList) result;
            }
            if (result instanceof Integer) {
                if (context == null) {
                    return null;
                }
                return context.getResources().getColorStateList(((Integer) result).intValue());
            }
            return defValue;
        } catch (Throwable tr) {
            Log.e(AUConstant.TAG, AUAbsTheme.class.getSimpleName(), tr);
        }
    }
}
