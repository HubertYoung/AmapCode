package com.mpaas.nebula.plugin;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.dialog.AUInputDialog;
import com.alipay.mobile.antui.dialog.AUInputDialog.OnClickNegativeListener;
import com.alipay.mobile.antui.dialog.AUInputDialog.OnClickPositiveListener;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Value;

public class H5PromptPlugin extends H5SimplePlugin {
    private static String a = "prompt";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(a);
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        String action = event.getAction();
        JSONObject jsonObject = event.getParam();
        if (a.equals(action)) {
            String title = H5Utils.getString(jsonObject, (String) "title");
            String message = H5Utils.getString(jsonObject, (String) "message");
            String okButton = H5Utils.getString(jsonObject, (String) "okButton");
            String cancelButton = H5Utils.getString(jsonObject, (String) "cancelButton");
            String hint = H5Utils.getString(jsonObject, (String) "placeholder");
            String confirmColor = H5Utils.getString(jsonObject, (String) "confirmColor");
            String cancelColor = H5Utils.getString(jsonObject, (String) "cancelColor");
            final AUInputDialog auInputDialog = new AUInputDialog(event.getActivity(), title, message, okButton, cancelButton, false);
            auInputDialog.setPositiveTextColor(confirmColor);
            auInputDialog.setNegativeTextColor(cancelColor);
            auInputDialog.show();
            auInputDialog.getInputContent().setHint(hint);
            auInputDialog.setNegativeListener(new OnClickNegativeListener() {
                public void onClick() {
                    JSONObject data = new JSONObject();
                    data.put((String) Value.OK, (Object) Boolean.valueOf(false));
                    context.sendBridgeResult(data);
                    auInputDialog.dismiss();
                    H5PromptPlugin.a(event.getActivity(), auInputDialog.getWindow().getDecorView());
                }
            });
            auInputDialog.setPositiveListener(new OnClickPositiveListener() {
                public void onClick(String s) {
                    JSONObject data = new JSONObject();
                    data.put((String) "inputValue", (Object) auInputDialog.getInputContent().getText().toString());
                    data.put((String) Value.OK, (Object) Boolean.valueOf(true));
                    context.sendBridgeResult(data);
                    auInputDialog.dismiss();
                    H5PromptPlugin.a(event.getActivity(), auInputDialog.getWindow().getDecorView());
                }
            });
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void a(Activity activity, View view) {
        try {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            H5Log.e((String) "H5PromptPlugin", (Throwable) e);
        }
    }
}
