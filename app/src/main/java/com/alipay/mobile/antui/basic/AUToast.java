package com.alipay.mobile.antui.basic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Binder;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.iconfont.AUIconDrawable;
import com.alipay.mobile.antui.iconfont.model.IconPaintBuilder;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.antui.utils.ToolUtils;
import com.alipay.mobile.common.share.widget.ResUtils;

@SuppressLint({"ShowToast"})
public class AUToast {
    public static Toast makeToast(Context context, int drawableId, int tipSrcId, int duration) {
        return makeToast(context, drawableId, context.getResources().getText(tipSrcId), duration);
    }

    public static Toast makeToast(Context context, int tipSrcId, int duration) {
        return makeToast(context, 0, context.getResources().getText(tipSrcId), duration);
    }

    public static Toast makeToast(Context context, int drawableId, CharSequence tipSrc, int duration) {
        View toastView;
        AuiLogger.debug("AUToast", "makeToast : " + ((context != null ? context.getClass().getName() : "") + ", tipSrc:" + tipSrc));
        Toast toast = Toast.makeText(context, tipSrc, duration);
        if (drawableId != 0) {
            toastView = LayoutInflater.from(context).inflate(R.layout.au_toast_with_img, null);
            ImageView indexDrawable = (ImageView) toastView.findViewById(R.id.index_drawable);
            if (TextUtils.equals(ToolUtils.judgeRes(drawableId), ResUtils.STRING)) {
                indexDrawable.setImageDrawable(new AUIconDrawable(context, new IconPaintBuilder(-1, DensityUtil.dip2px(context, 36.0f), drawableId)));
            } else {
                indexDrawable.setBackgroundResource(drawableId);
            }
            int size = context.getResources().getDimensionPixelSize(R.dimen.toast_size);
            toastView.setLayoutParams(new LayoutParams(size, size));
        } else {
            toastView = LayoutInflater.from(context).inflate(R.layout.au_toast, null);
        }
        ((TextView) toastView.findViewById(R.id.tip_content)).setText(tipSrc);
        toast.setView(toastView);
        toast.setGravity(17, 0, 0);
        return toast;
    }

    public static boolean showToastWithSuper(Activity context, int drawableId, CharSequence tipSrc, int duration) {
        try {
            boolean isAllowed = isNotifyAllowed(context);
            AuiLogger.debug("AUToast", "isAllowed = " + isAllowed);
            if (!isAllowed) {
                showSuperToast(context, drawableId, tipSrc, duration);
            } else {
                showSysToast(context, drawableId, tipSrc, duration);
            }
            return true;
        } catch (Throwable e) {
            AuiLogger.error("AUToast", e.getMessage());
            return false;
        }
    }

    public static Toast showSysToast(Activity context, int drawableId, CharSequence tipSrc, int duration) {
        Toast toast = makeToast((Context) context, drawableId, tipSrc, duration);
        toast.show();
        return toast;
    }

    public static AUToastPopupWindow showSuperToast(Activity context, int drawableId, CharSequence tipSrc) {
        return showSuperToast(context, drawableId, tipSrc, 0);
    }

    public static AUToastPopupWindow showSuperToast(Activity context, int drawableId, CharSequence tipSrc, int duration) {
        AUToastPopupWindow mSuperToast = new AUToastPopupWindow(context, drawableId);
        mSuperToast.setMessage(tipSrc);
        if (duration == 1) {
            mSuperToast.showTime(3500);
        } else if (duration == 0) {
            mSuperToast.showTime(2000);
        } else {
            mSuperToast.showTime(duration);
        }
        mSuperToast.show();
        return mSuperToast;
    }

    public static boolean isNotifyAllowed(Context context) {
        int version = VERSION.SDK_INT;
        if (version >= 26) {
            Object object = context.getSystemService("notification");
            try {
                if (((Integer) object.getClass().getDeclaredMethod("getImportance", new Class[0]).invoke(object, new Object[0])).intValue() != 0) {
                    return true;
                }
                return false;
            } catch (Throwable e) {
                AuiLogger.error("AUToast", "isNotifyChangedAllowed" + e.getMessage());
            }
        } else {
            if (version >= 18) {
                return 1 != checkOp(context, 11);
            }
            return true;
        }
    }

    public static int checkOp(Context context, int op) {
        Object object = context.getSystemService("appops");
        try {
            return ((Integer) object.getClass().getDeclaredMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(object, new Object[]{Integer.valueOf(op), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue();
        } catch (Throwable e) {
            AuiLogger.error("SimpleToast", e.getMessage());
            return -1;
        }
    }
}
