package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5ActionSheetProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.ArrayList;

public class H5ActionSheetPlugin extends H5SimplePlugin {
    private final String a = "H5ActionSheetPlugin";
    private boolean b = false;
    private ViewGroup c;
    private ViewGroup d;
    private Dialog e;
    private H5ActionSheetProvider f;

    public void onRelease() {
        a();
        if (this.f != null) {
            this.f.onRelease();
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.ACTION_SHEET);
        filter.addAction(CommonEvents.H5_PAGE_BACK);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.H5_PAGE_BACK.equals(action)) {
            if (a()) {
                return true;
            }
        } else if (CommonEvents.ACTION_SHEET.equals(action)) {
            a();
            a(event, bridgeContext);
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean a() {
        if (this.e != null) {
            if (!this.e.isShowing()) {
                return false;
            }
            this.e.cancel();
            return true;
        } else if (!this.b) {
            return false;
        } else {
            this.c.removeView(this.d);
            this.b = false;
            return true;
        }
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject jo;
        JSONObject params = event.getParam();
        Activity activity = event.getActivity();
        if (activity == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String title = H5Utils.getString(params, (String) "title");
        String cancelBtn = H5Utils.getString(params, (String) "cancelBtn");
        JSONArray jaButtons = H5Utils.getJSONArray(params, "btns", null);
        JSONArray jaButtonsRedTexts = H5Utils.getJSONArray(params, "badges", null);
        String destructiveBtn = H5Utils.getString(params, (String) "destructiveBtnIndex");
        this.f = (H5ActionSheetProvider) Nebula.getProviderManager().getProviderUseCache(H5ActionSheetProvider.class.getName(), false);
        if (this.f != null) {
            boolean isNeedRed = false;
            ArrayList btnList = new ArrayList();
            ArrayList btnRedTextsList = new ArrayList();
            if (!TextUtils.isEmpty(destructiveBtn)) {
                btnList.add(destructiveBtn);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "index", (Object) "-1");
                btnRedTextsList.add(jsonObject);
            }
            if (jaButtons != null && !jaButtons.isEmpty()) {
                if (jaButtonsRedTexts != null && !jaButtonsRedTexts.isEmpty()) {
                    isNeedRed = true;
                    for (int index = 0; index < jaButtonsRedTexts.size(); index++) {
                        try {
                            jo = jaButtonsRedTexts.getJSONObject(index);
                        } catch (Throwable th) {
                            jo = new JSONObject();
                            jo.put((String) "index", (Object) "-1");
                        }
                        btnRedTextsList.add(jo);
                    }
                }
                for (int index2 = 0; index2 < jaButtons.size(); index2++) {
                    btnList.add((String) jaButtons.get(index2));
                }
            }
            String title2 = title.trim();
            if (!isNeedRed) {
                this.f.setContextAndContent(activity, btnList, title2, bridgeContext, null);
            } else {
                this.f.setContextAndContent(activity, btnList, btnRedTextsList, title2, bridgeContext, null);
            }
            this.e = this.f.getAntUIActionSheet();
            if (this.e != null) {
                this.e.show();
                return;
            }
            return;
        }
        if (this.c == null) {
            this.c = (ViewGroup) activity.getWindow().getDecorView().findViewById(16908290);
        }
        LayoutInflater inflater = LayoutInflater.from(activity);
        this.d = (ViewGroup) inflater.inflate(R.layout.h5_action_sheet, this.c, false);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        int margin = H5Utils.dip2px(H5Environment.getContext(), 7);
        layoutParams.setMargins(margin, margin, margin, margin);
        final H5BridgeContext h5BridgeContext = bridgeContext;
        AnonymousClass1 r0 = new OnClickListener() {
            public void onClick(View v) {
                H5ActionSheetPlugin.this.a();
                JSONObject data = new JSONObject();
                data.put((String) "success", (Object) "true");
                data.put((String) "index", (Object) Integer.valueOf(-1));
                h5BridgeContext.sendBridgeResult(data);
            }
        };
        LinearLayout apContent = (LinearLayout) this.d.findViewById(R.id.h5_action_sheet_content);
        this.d.findViewById(R.id.rl_h5_action_sheet).setOnClickListener(r0);
        apContent.setOnClickListener(r0);
        TextView tvTitle = (TextView) this.d.findViewById(R.id.h5_action_sheet_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            tvTitle.setVisibility(0);
        } else {
            tvTitle.setVisibility(8);
        }
        final H5BridgeContext h5BridgeContext2 = bridgeContext;
        AnonymousClass2 r02 = new OnClickListener() {
            public void onClick(View v) {
                int index = ((Integer) v.getTag()).intValue();
                H5ActionSheetPlugin.this.a();
                JSONObject data = new JSONObject();
                data.put((String) "success", (Object) "true");
                data.put((String) "index", (Object) Integer.valueOf(index));
                h5BridgeContext2.sendBridgeResult(data);
            }
        };
        int viewIndex = 0;
        if (!TextUtils.isEmpty(destructiveBtn)) {
            Button destructButton = (Button) inflater.inflate(R.layout.h5_as_default_button, null);
            destructButton.setText(destructiveBtn);
            destructButton.setTag(Integer.valueOf(0));
            destructButton.setOnClickListener(r02);
            destructButton.setLayoutParams(layoutParams);
            viewIndex = 0 + 1;
            apContent.addView(destructButton, 1);
        }
        if (jaButtons != null && !jaButtons.isEmpty()) {
            for (int index3 = 0; index3 < jaButtons.size(); index3++) {
                String otherButton = (String) jaButtons.get(index3);
                H5Log.d("H5ActionSheetPlugin", "otherButton =" + otherButton);
                Button apButton = (Button) inflater.inflate(R.layout.h5_as_default_button, null);
                apButton.setText(otherButton);
                apButton.setTag(Integer.valueOf(viewIndex));
                apButton.setOnClickListener(r02);
                apButton.setLayoutParams(layoutParams);
                viewIndex++;
                apContent.addView(apButton, viewIndex);
            }
        }
        if (!TextUtils.isEmpty(cancelBtn)) {
            Button cancelButton = (Button) inflater.inflate(R.layout.h5_as_cancel_button, null);
            cancelButton.setText(cancelBtn);
            cancelButton.setTag(Integer.valueOf(viewIndex));
            cancelButton.setOnClickListener(r02);
            cancelButton.setLayoutParams(layoutParams);
            apContent.addView(cancelButton, viewIndex + 1);
        }
        this.c.addView(this.d);
        this.c.bringChildToFront(this.d);
        this.b = true;
    }
}
