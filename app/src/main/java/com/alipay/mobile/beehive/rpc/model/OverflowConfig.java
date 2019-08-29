package com.alipay.mobile.beehive.rpc.model;

import android.text.TextUtils;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.h5container.api.H5Param;
import org.json.JSONException;
import org.json.JSONObject;

public class OverflowConfig {
    private static final int MAX_WAIT_TIME = 60;
    public static final int SHOW_DIALOG = 2;
    public static final int SHOW_NOTHING = 0;
    public static final int SHOW_TOAST = 1;
    public String background;
    public String color;
    public String desc;
    public String imgUrl;
    public boolean isSpanner;
    public int showType;
    public String subDesc = null;
    public int waitTime = 0;

    public static OverflowConfig fromRpcException(RpcException ex) {
        OverflowConfig result = new OverflowConfig();
        boolean isControlValid = false;
        if (!TextUtils.isEmpty(ex.getControl())) {
            try {
                JSONObject c = new JSONObject(ex.getControl());
                isControlValid = true;
                result.showType = 2;
                try {
                    result.waitTime = Integer.parseInt(c.getString("waittime"));
                } catch (Exception e) {
                }
                if (result.waitTime > 60) {
                    result.waitTime = 60;
                }
                try {
                    result.desc = c.getString("title");
                    result.desc = getNullIfEmpty(result.desc);
                } catch (JSONException e2) {
                }
                try {
                    result.color = c.getString("color");
                } catch (JSONException e3) {
                }
                try {
                    result.background = c.getString(Subscribe.THREAD_BACKGROUND);
                } catch (JSONException e4) {
                }
                try {
                    result.imgUrl = c.getString(H5Param.MENU_ICON);
                } catch (JSONException e5) {
                }
            } catch (Exception e6) {
                LoggerFactory.getTraceLogger().error((String) "beehive-rpc", (Throwable) e6);
            }
        }
        if (isControlValid) {
            result.isSpanner = true;
        } else {
            result.isSpanner = false;
            if (TextUtils.isEmpty(result.desc)) {
                result.desc = getNullIfEmpty(getOverflowDescFromException(ex));
            }
            if (ex.getAlert() == 0) {
                result.showType = 0;
            } else if (ex.getAlert() == 1) {
                result.showType = 1;
            } else {
                result.showType = 2;
            }
        }
        return result;
    }

    private static String getNullIfEmpty(String s) {
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        return s;
    }

    public static String getOverflowDescFromException(RpcException ex) {
        return ex.getMsg();
    }
}
