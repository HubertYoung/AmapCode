package com.alipay.mobile.beehive.compositeui.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.compositeui.multilevel.MultilevelSelectActivity;
import com.alipay.mobile.beehive.compositeui.multilevel.MultilevelSelectActivity_;
import com.alipay.mobile.beehive.compositeui.multilevel.MultilevelSelectCallBack;
import com.alipay.mobile.beehive.compositeui.wheelview.picker.LimitedHoursPickerDialog;
import com.alipay.mobile.beehive.compositeui.wheelview.picker.LimitedHoursPickerDialog.OnOptionPickListener;
import com.alipay.mobile.beehive.compositeui.wheelview.picker.OptionPickerDialog;
import com.alipay.mobile.beehive.compositeui.wheelview.picker.TwoWheelOptionPickerDialog;
import com.alipay.mobile.beehive.util.JumpUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class H5BeehiveViewPlugin extends H5SimplePlugin {
    public static final String LIMITEDHOURS_PICKER = "beehiveLimitedHoursPicker";
    public static final String MULTILEVEL_SELECT = "beehiveMultilevelSelect";
    public static final String OPTIONS_PICKER = "beehiveOptionsPicker";
    public static final String TAG = "H5POIPickPlugin";
    /* access modifiers changed from: private */
    public final String[] paramKeyArray = {"title", "optionsOne", "optionsTwo", "selectedOneIndex", "selectedTwoIndex", "selectedOneOption", "selectedTwoOption", "positiveString", "negativeString", "startTimestamp", "duartion", "startHour", "endHour"};

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(OPTIONS_PICKER);
        filter.addAction(LIMITEDHOURS_PICKER);
        filter.addAction(MULTILEVEL_SELECT);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        try {
            if (OPTIONS_PICKER.equals(action)) {
                optionsPicker(event, context);
            } else if (LIMITEDHOURS_PICKER.equals(action)) {
                limitedhoursPicker(event, context);
            } else if (!MULTILEVEL_SELECT.equals(action)) {
                return false;
            } else {
                multilevelSelect(event, context);
            }
            return true;
        } catch (Throwable throwable) {
            LoggerFactory.getTraceLogger().error((String) TAG, throwable);
            return false;
        }
    }

    private void multilevelSelect(H5Event event, final H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        Bundle bundle = new Bundle();
        for (Entry<String, Object> key : param.entrySet()) {
            String key2 = (String) key.getKey();
            char c = 65535;
            switch (key2.hashCode()) {
                case -934426595:
                    if (key2.equals("result")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3322014:
                    if (key2.equals("list")) {
                        c = 2;
                        break;
                    }
                    break;
                case 110371416:
                    if (key2.equals("title")) {
                        c = 0;
                        break;
                    }
                    break;
                case 150981469:
                    if (key2.equals("defaultSegmentName")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                    bundle.putString(key2, param.getString(key2));
                    break;
            }
        }
        JumpUtil.startActivity(bundle, MultilevelSelectActivity_.class);
        MultilevelSelectActivity.multilevelSelectCallBack = new MultilevelSelectCallBack() {
            public final void onSuccess(JSONArray result) {
                JSONObject resultJson = new JSONObject();
                resultJson.put((String) "success", (Object) Boolean.valueOf(true));
                List wrapResult = new ArrayList();
                for (int i = 0; i < result.size(); i++) {
                    JSONObject object = new JSONObject();
                    object.put((String) "name", result.getJSONObject(i).get("name"));
                    wrapResult.add(object);
                }
                resultJson.put((String) "result", (Object) wrapResult);
                bridgeContext.sendBridgeResult(resultJson);
            }

            public final void onCancel() {
                JSONObject resultJson = new JSONObject();
                resultJson.put((String) "success", (Object) Boolean.valueOf(false));
                bridgeContext.sendBridgeResult(resultJson);
            }
        };
    }

    private void limitedhoursPicker(H5Event event, final H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String title = "";
        if (param.containsKey(this.paramKeyArray[0])) {
            title = param.getString(this.paramKeyArray[0]);
        }
        String positiveString = "确认";
        if (param.containsKey(this.paramKeyArray[7])) {
            positiveString = param.getString(this.paramKeyArray[7]);
        }
        String negativeString = "取消";
        if (param.containsKey(this.paramKeyArray[8])) {
            negativeString = param.getString(this.paramKeyArray[8]);
        }
        long startTimestamp = 0;
        if (param.containsKey(this.paramKeyArray[9])) {
            startTimestamp = param.getLong(this.paramKeyArray[9]).longValue();
        }
        long duartion = 0;
        if (param.containsKey(this.paramKeyArray[10])) {
            duartion = param.getLong(this.paramKeyArray[10]).longValue();
        }
        int startHour = 0;
        if (param.containsKey(this.paramKeyArray[11])) {
            startHour = param.getInteger(this.paramKeyArray[11]).intValue();
        }
        int endHour = 23;
        if (param.containsKey(this.paramKeyArray[12])) {
            endHour = param.getInteger(this.paramKeyArray[12]).intValue();
        }
        LimitedHoursPickerDialog pickerDialog = new LimitedHoursPickerDialog((Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get(), title, positiveString, negativeString, startTimestamp, duartion, startHour, endHour);
        pickerDialog.setOnOptionPickListener(new OnOptionPickListener() {
            public final void onOptionPicked(long result) {
                JSONObject resultJson = new JSONObject();
                resultJson.put((String) "result", (Object) Long.valueOf(result));
                bridgeContext.sendBridgeResult(resultJson);
            }
        });
        pickerDialog.show();
    }

    private void optionsPicker(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        String title = "";
        if (param.containsKey(this.paramKeyArray[0])) {
            title = param.getString(this.paramKeyArray[0]);
        }
        String[] optionsOne = null;
        if (param.containsKey(this.paramKeyArray[1])) {
            optionsOne = (String[]) param.getJSONArray(this.paramKeyArray[1]).toArray(new String[0]);
        }
        String[] optionsTwo = null;
        if (param.containsKey(this.paramKeyArray[2])) {
            optionsTwo = (String[]) param.getJSONArray(this.paramKeyArray[2]).toArray(new String[0]);
        }
        String positiveString = "确认";
        if (param.containsKey(this.paramKeyArray[7])) {
            positiveString = param.getString(this.paramKeyArray[7]);
        }
        String negativeString = "取消";
        if (param.containsKey(this.paramKeyArray[8])) {
            negativeString = param.getString(this.paramKeyArray[8]);
        }
        int selectedOneIndex = 0;
        if (param.containsKey(this.paramKeyArray[3])) {
            selectedOneIndex = param.getInteger(this.paramKeyArray[3]).intValue();
        }
        Activity activity = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
        if (optionsOne != null && optionsTwo != null) {
            showTwoWheelDialog(bridgeContext, param, title, optionsOne, optionsTwo, positiveString, negativeString, selectedOneIndex, activity);
        } else if (optionsOne != null) {
            showSingleWheelDialog(bridgeContext, title, positiveString, negativeString, optionsOne, selectedOneIndex, activity);
        }
    }

    private void showSingleWheelDialog(final H5BridgeContext bridgeContext, String title, String positiveString, String negativeString, String[] optionsOne, int selectedOneIndex, Activity activity) {
        OptionPickerDialog picker = new OptionPickerDialog(activity, title, positiveString, negativeString, optionsOne);
        picker.setOnOptionPickListener(new OptionPickerDialog.OnOptionPickListener() {
            public final void onOptionPicked(String option, int index) {
                JSONObject result = new JSONObject();
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[3], (Object) Integer.valueOf(index));
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[5], (Object) option);
                bridgeContext.sendBridgeResult(result);
            }
        });
        picker.setNegativeListener(new OnClickListener() {
            public final void onClick(View v) {
                JSONObject result = new JSONObject();
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[3], (Object) "");
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[5], (Object) "");
                bridgeContext.sendBridgeResult(result);
            }
        });
        picker.setSelectedIndex(selectedOneIndex);
        picker.show();
    }

    private void showTwoWheelDialog(final H5BridgeContext bridgeContext, JSONObject param, String title, String[] optionsOne, String[] optionsTwo, String positiveString, String negativeString, int selectedOneIndex, Activity activity) {
        TwoWheelOptionPickerDialog picker = new TwoWheelOptionPickerDialog(activity, title, positiveString, negativeString, optionsOne, optionsTwo);
        picker.setOnOptionPickListener(new TwoWheelOptionPickerDialog.OnOptionPickListener() {
            public final void onOptionPicked(String leftOption, int leftSelectedIndex, String rightOption, int rightSelectedIndex) {
                JSONObject result = new JSONObject();
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[3], (Object) Integer.valueOf(leftSelectedIndex));
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[4], (Object) Integer.valueOf(rightSelectedIndex));
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[5], (Object) leftOption);
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[6], (Object) rightOption);
                bridgeContext.sendBridgeResult(result);
            }
        });
        picker.setNegativeListener(new OnClickListener() {
            public final void onClick(View v) {
                JSONObject result = new JSONObject();
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[3], (Object) "");
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[4], (Object) "");
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[5], (Object) "");
                result.put(H5BeehiveViewPlugin.this.paramKeyArray[6], (Object) "");
                bridgeContext.sendBridgeResult(result);
            }
        });
        int selectedTwoIndex = 0;
        if (param.containsKey(this.paramKeyArray[4])) {
            selectedTwoIndex = param.getInteger(this.paramKeyArray[4]).intValue();
        }
        picker.setLeftSelectedIndex(selectedOneIndex);
        picker.setRightSelectedIndex(selectedTwoIndex);
        picker.show();
    }
}
