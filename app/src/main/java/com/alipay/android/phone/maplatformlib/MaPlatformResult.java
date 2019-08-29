package com.alipay.android.phone.maplatformlib;

import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobilecodec.service.pai.res.RouteRes;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MaPlatformResult {
    public static final String METHOD_NATIVE = "native";
    public MaPlatformException exception;
    public List<RouteInfo> routeInfo;
    public boolean succeed;

    public static class MaPlatformException {
        public int errorCode;
        public String errorMessage;

        public MaPlatformException(RpcException e) {
            this.errorCode = e.getCode();
            this.errorMessage = e.getMsg();
        }
    }

    public static class RouteInfo {
        public String method;
        public String uri;

        public static List<RouteInfo> createListFromJson(String json) {
            List<RouteInfo> routeInfoList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RouteInfo routeInfo = new RouteInfo();
                    routeInfo.method = jsonObject.optString("method");
                    routeInfo.uri = jsonObject.optString("uri");
                    routeInfoList.add(routeInfo);
                }
            } catch (JSONException e) {
            }
            return routeInfoList;
        }

        public String toString() {
            return "method=" + this.method + ", uri=" + this.uri;
        }
    }

    public MaPlatformResult(RouteRes rr) {
        if (!rr.success) {
            rpcResultsJsonInvalid(-1000, "服务端数据异常");
            return;
        }
        List<RouteInfo> routeInfoList = RouteInfo.createListFromJson(rr.routeInfos);
        if (routeInfoList == null || routeInfoList.size() == 0) {
            rpcResultsJsonInvalid(-1010, "服务端数据异常");
            return;
        }
        this.succeed = true;
        this.routeInfo = routeInfoList;
    }

    public MaPlatformResult(RpcException e) {
        this.succeed = false;
        this.exception = new MaPlatformException(e);
    }

    public MaPlatformResult(Exception e) {
        rpcResultsJsonInvalid(-1020, e.getMessage());
    }

    private void rpcResultsJsonInvalid(int code, String errorMsg) {
        RpcException rpcException = new RpcException(Integer.valueOf(code), errorMsg);
        this.succeed = false;
        this.exception = new MaPlatformException(rpcException);
    }
}
