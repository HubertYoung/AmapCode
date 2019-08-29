package com.alipay.mobile.antui.screenadpt;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUViewInterface;
import java.util.HashMap;

public class AUScreenUtils {
    private static HashMap<String, Boolean> mIsAPMap = new HashMap<>();
    private static String mWhiteListAP = "";
    private static Boolean preCheck;

    public static void setWhiteListAP(String whiteListAP, Object password) {
        if (password != null && password.getClass().getName().equals("com.alipay.android.phone.home.widget.HomeWidgetGroup")) {
            if ("emptyList".equals(whiteListAP)) {
                whiteListAP = "";
            }
            mWhiteListAP = whiteListAP;
        }
    }

    public static boolean checkApFlag(Context context, AttributeSet attrs, AUViewInterface auView) {
        if (preCheck == null) {
            preCheck = Boolean.valueOf(Float.compare(AUScreenAdaptTool.getAPDensity(context), AUScreenAdaptTool.getDensity(context)) == 0);
        }
        if ((preCheck != null && preCheck.booleanValue()) || TextUtils.isEmpty(mWhiteListAP) || !mWhiteListAP.contains(context.getClass().getName())) {
            return false;
        }
        if (auView instanceof View) {
            ViewParent parentView = ((View) auView).getParent();
            if (auView.isAP() == null && (parentView instanceof AUViewInterface)) {
                auView.setAP(((AUViewInterface) parentView).isAP());
            }
        }
        String hashCode = "";
        if (attrs != null) {
            hashCode = String.valueOf(attrs.hashCode());
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AUScreenAdapt);
            if (a.hasValue(0) && auView != null) {
                auView.setAP(Boolean.valueOf(a.getBoolean(0, false)));
            } else if (mIsAPMap.containsKey(hashCode)) {
                return mIsAPMap.get(hashCode).booleanValue();
            } else {
                for (StackTraceElement className : Thread.currentThread().getStackTrace()) {
                    if (TextUtils.equals(className.getClassName(), "com.alipay.android.phone.discovery.o2ohome.O2oWidgetGroup")) {
                        mIsAPMap.put(hashCode, Boolean.FALSE);
                        return false;
                    }
                }
            }
            a.recycle();
        }
        if (auView != null && auView.isAP() != null) {
            return auView.isAP().booleanValue();
        }
        if (!TextUtils.isEmpty(hashCode)) {
            mIsAPMap.put(hashCode, Boolean.TRUE);
        }
        return true;
    }
}
