package com.alipay.mobile.security.zim.plugin;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.zim.api.ZIMFacade;
import com.alipay.mobile.security.zim.api.ZIMFacadeBuilder;
import java.util.ArrayList;
import java.util.List;

public class ZIMH5Plugin extends H5SimplePlugin {
    public static final String TAG = "ZIMH5Plugin";
    public static final String ZIM_IDENTIFY = "zimIdentity";
    public static final String ZIM_IDENTIFY_ACTION = "action";
    public static final String ZIM_IDENTIFY_GETBIOINFO = "getBioInfo";
    public static final String ZIM_IDENTIFY_ISNEEDFP = "isNeedFP";
    public static final String ZIM_IDENTIFY_VERIFYID = "verifyId";
    public static final String ZIM_IDENTIFY_VERIFYTYPE = "verifyType";
    public static List<String> list;

    static {
        ArrayList arrayList = new ArrayList();
        list = arrayList;
        arrayList.add(ZIM_IDENTIFY);
    }

    public void onRelease() {
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction(ZIM_IDENTIFY);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (ZIM_IDENTIFY.equals(h5Event.getAction()) && h5Event != null) {
            JSONObject param = h5Event.getParam();
            String string = H5Utils.getString(param, (String) "action", (String) "");
            String string2 = H5Utils.getString(param, (String) ZIM_IDENTIFY_VERIFYTYPE, (String) "");
            String string3 = H5Utils.getString(param, (String) ZIM_IDENTIFY_VERIFYID, (String) "");
            new StringBuilder("action:").append(string).append(" verifyType:").append(string2).append(" verifyId:").append(string3).append(" isNeedFP:").append(H5Utils.getBoolean(param, (String) ZIM_IDENTIFY_ISNEEDFP, false));
            if (ZIM_IDENTIFY_GETBIOINFO.equals(string)) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put((String) "action", (Object) ZIM_IDENTIFY_GETBIOINFO);
                jSONObject.put((String) "actionResult", (Object) ZIMFacade.getMetaInfos(h5Event.getActivity().getApplicationContext()));
                h5BridgeContext.sendBridgeResult(jSONObject);
            } else {
                ZIMFacadeBuilder.create(h5Event.getActivity()).verify(string3, null, new a(this, h5BridgeContext));
            }
        }
        return true;
    }
}
