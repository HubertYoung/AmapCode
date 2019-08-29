package com.mpaas.nebula.plugin;

import android.app.Activity;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.model.PickerDataModel;
import com.alipay.mobile.antui.picker.AUCascadePicker;
import com.alipay.mobile.antui.picker.AUCascadePicker.OnLinkagePickerListener;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import java.util.ArrayList;
import java.util.List;

public class AntUILinkagePickerPlugin extends H5SimplePlugin {
    public static final String GET_LINKAGE_PICKER = "antUIGetCascadePicker";
    public static final String TAG = "AntUILinkagePickerPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(GET_LINKAGE_PICKER);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return super.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!TextUtils.equals(event.getAction(), GET_LINKAGE_PICKER)) {
            return super.handleEvent(event, context);
        }
        a(event, context);
        return true;
    }

    private void a(H5Event event, final H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        param.getString("title");
        String listString = param.getString("list");
        String selectedListString = param.getString("selectedList");
        List modelList = null;
        List selectedList = null;
        AUCascadePicker pickerView = new AUCascadePicker((Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get());
        try {
            if (!TextUtils.isEmpty(listString)) {
                modelList = JSONArray.parseArray(listString, PickerDataModel.class);
            }
            if (!TextUtils.isEmpty(selectedListString)) {
                selectedList = JSONArray.parseArray(selectedListString, PickerDataModel.class);
            }
            if (!(modelList == null || modelList.size() == 0)) {
                pickerView.setDateData(modelList);
            }
            if (selectedList != null && selectedList.size() > 0) {
                pickerView.setSelectedItem(selectedList.get(0));
            }
            pickerView.setOnLinkagePickerListener(new OnLinkagePickerListener() {
                public void onLinkagePicked(PickerDataModel pickerDataModel) {
                    JSONObject resObj = new JSONObject();
                    if (pickerDataModel != null) {
                        try {
                            List resultList = new ArrayList();
                            resultList.add(pickerDataModel);
                            resObj.put((String) "success", (Object) Boolean.TRUE);
                            resObj.put((String) "result", (Object) resultList);
                        } catch (Exception e) {
                            resObj.put((String) "success", (Object) Boolean.FALSE);
                            resObj.put((String) "result", (Object) "");
                        }
                        bridgeContext.sendBridgeResult(resObj);
                    }
                }
            });
            pickerView.show();
        } catch (Exception e) {
            AuiLogger.error(TAG, "parse json fail:" + e);
            bridgeContext.sendError(event, Error.INVALID_PARAM);
        }
    }
}
