package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5ToastLikeDialog;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.util.H5ToastUtil;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;

public class H5ToastPlugin extends H5SimplePlugin {
    public static final String TAG = "H5ToastPlugin";
    private static final int a = R.drawable.h5_toast_ok;
    private static final int b = R.drawable.h5_toast_false;
    private static final int c = R.drawable.h5_toast_exception;
    private Toast d = null;
    private H5ToastLikeDialog e = null;
    /* access modifiers changed from: private */
    public boolean f = false;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("toast");
        filter.addAction("hideToast");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgecontext) {
        String action = event.getAction();
        if ("toast".equals(action)) {
            a(event, bridgecontext);
        } else if ("hideToast".equals(action)) {
            a();
        }
        return true;
    }

    private static int a(String image) {
        if (TextUtils.equals(image, "success")) {
            return a;
        }
        if (TextUtils.equals(image, UploadDataResult.FAIL_MSG)) {
            return b;
        }
        if (TextUtils.equals(image, LogCategory.CATEGORY_EXCEPTION)) {
            return c;
        }
        return 0;
    }

    public void onRelease() {
        super.onRelease();
        this.d = null;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject params = event.getParam();
        if (params == null || params.isEmpty()) {
            bridgeContext.sendError(Error.INVALID_PARAM.ordinal(), (String) "参数为空");
        } else if (this.f || (this.e != null && this.e.isShowing())) {
            H5Log.d(TAG, "toast showing");
        } else {
            String content = H5Utils.getString(params, (String) "content");
            String type = H5Utils.getString(params, (String) "type");
            int duration = H5Utils.getInt(params, (String) "duration");
            if (duration == 0) {
                duration = 2000;
            }
            a(event.getActivity(), a(type), content, duration, bridgeContext);
            H5Log.d(TAG, "toast show");
        }
    }

    private void a(Context context, int iconRes, String textRes, int duration, H5BridgeContext bridgeContext) {
        if (context instanceof H5Activity) {
            if (!H5ToastUtil.isNotificationEnabled(context)) {
                H5Log.d(TAG, "notification is not enabled, show dialog.");
                if (c()) {
                    this.e = new H5ToastLikeDialog(context);
                    this.e.setText(textRes);
                    this.e.setDuration(duration);
                    this.e.setTextColor(-1);
                    if (iconRes != 0) {
                        this.e.setImageView(context.getResources().getDrawable(iconRes));
                    }
                    a(bridgeContext, true, duration);
                    return;
                }
                return;
            }
            View view = LayoutInflater.from(context).inflate(R.layout.h5_toast, null);
            ImageView image = (ImageView) view.findViewById(R.id.h5_mini_toast_icon);
            if (iconRes != 0) {
                image.setImageResource(iconRes);
            } else {
                image.setVisibility(8);
            }
            TextView text = (TextView) view.findViewById(R.id.h5_mini_toast_text);
            if (TextUtils.isEmpty(textRes)) {
                text.setVisibility(8);
            } else {
                text.setText(textRes);
            }
            view.getBackground().setAlpha(192);
            if (this.d == null) {
                this.d = new Toast(context);
            }
            this.d.setGravity(17, 0, 0);
            this.d.setDuration(1);
            this.d.setView(view);
        } else if (this.d == null) {
            this.d = Toast.makeText(context, textRes, 1);
        } else {
            this.d.setText(textRes);
            this.d.setDuration(1);
        }
        a(bridgeContext, false, duration);
    }

    private void a(final H5BridgeContext bridgeContext, final boolean isDialogLike, int duration) {
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5Log.d(H5ToastPlugin.TAG, "toast show call back");
                JSONObject result = new JSONObject();
                result.put((String) "toastCallBack", (Object) "true");
                bridgeContext.sendBridgeResult(result);
                if (isDialogLike) {
                    H5ToastPlugin.this.b();
                    return;
                }
                H5ToastPlugin.this.f = false;
                H5ToastPlugin.this.a();
            }
        }, (long) duration);
        if (isDialogLike) {
            this.e.show();
            return;
        }
        this.d.show();
        this.f = true;
    }

    /* access modifiers changed from: private */
    public void a() {
        H5Log.d(TAG, "hideToast");
        if (this.d != null) {
            this.d.cancel();
            this.d = null;
        }
        b();
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.e != null) {
            Activity activity = null;
            if (this.e.getContext() instanceof Activity) {
                activity = (Activity) this.e.getContext();
            } else if ((this.e.getContext() instanceof ContextWrapper) && (((ContextWrapper) this.e.getContext()).getBaseContext() instanceof Activity)) {
                activity = (Activity) ((ContextWrapper) this.e.getContext()).getBaseContext();
            }
            if (!a(activity)) {
                this.e.dismiss();
            }
        }
    }

    private static boolean a(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return true;
        }
        if (VERSION.SDK_INT >= 17) {
            return activity.isDestroyed();
        }
        return false;
    }

    private static boolean c() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_showToastLikeDialog"))) {
            return false;
        }
        return true;
    }
}
