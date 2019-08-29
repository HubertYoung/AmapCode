package com.alipay.mobile.nebulauc.impl.network;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.common.transport.h5.H5NetworkManager;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory.DiscardOldestPolicy;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory.H5SingleThreadFactory;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.uc.webview.export.internal.interfaces.EventHandler;
import com.uc.webview.export.internal.interfaces.INetwork;
import com.uc.webview.export.internal.interfaces.IRequest;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AlipayNetwork implements INetwork {
    public static final String TAG = "AlipayNetwork";
    private static JSONArray mEnableUploadNetInfoUrlRegexList;
    private static JSONArray mHighPriorityAppIdList;
    private static JSONArray mHighPriorityUrlRegexList;
    private static JSONArray mHttp2HostList;
    private static ThreadPoolExecutor mRequestExecutor = null;
    static boolean sEnableSendErrorToUcWhenException = true;
    static boolean sEnableUploadNetInfoWhenError = true;
    static boolean sEnableUploadNetInfoWhenSuccess = false;
    private static AlipayNetwork sInstance = null;
    static boolean sInterceptInvalidDomain = true;
    private static JSONArray sSubResInSecBlackList1;
    private static JSONArray sSubResInSecBlackList2;
    private JSONArray forceHostList;
    private H5NetworkManager h5NetworkManager;
    private boolean hasInit = false;
    private boolean hasSetAbroad = false;
    private int mBridgeThreadSize = 2;
    private boolean mEnableBridgeThread = false;
    private JSONArray spdyBlackListV2;
    private boolean spdySwitch;
    private JSONArray spdyWhiteListV2;
    private JSONArray subResAppBlackList;
    private JSONArray subResBlackList;
    private JSONArray subResTypeList;
    /* access modifiers changed from: private */
    public boolean useSpdy;
    private boolean useSpdyInHttps;

    public AlipayNetwork() {
        sInstance = this;
    }

    @Nullable
    public static AlipayNetwork getInstance() {
        return sInstance;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void init() {
        /*
            r15 = this;
            r11 = 0
            r10 = 1
            monitor-enter(r15)
            boolean r12 = r15.hasInit     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x0009
            monitor-exit(r15)     // Catch:{ all -> 0x021f }
        L_0x0008:
            return
        L_0x0009:
            r12 = 1
            r15.hasInit = r12     // Catch:{ all -> 0x021f }
            android.content.Context r1 = com.alipay.mobile.nebula.util.H5Utils.getContext()     // Catch:{ all -> 0x021f }
            com.alipay.mobile.common.transport.h5.H5NetworkManager r12 = new com.alipay.mobile.common.transport.h5.H5NetworkManager     // Catch:{ all -> 0x021f }
            r12.<init>(r1)     // Catch:{ all -> 0x021f }
            r15.h5NetworkManager = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "h5_androidSpdyV2"
            java.lang.String r12 = com.alipay.mobile.nebulauc.util.H5ConfigUtil.getConfig(r12)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONObject r3 = com.alipay.mobile.nebula.util.H5Utils.parseObject(r12)     // Catch:{ all -> 0x021f }
            if (r3 == 0) goto L_0x0031
            java.lang.String r12 = "YES"
            java.lang.String r13 = "useSpdy"
            java.lang.String r13 = r3.getString(r13)     // Catch:{ all -> 0x021f }
            boolean r12 = r12.equals(r13)     // Catch:{ all -> 0x021f }
            r15.spdySwitch = r12     // Catch:{ all -> 0x021f }
        L_0x0031:
            java.lang.String r12 = "h5_networkBridgeThread"
            java.lang.String r12 = com.alipay.mobile.nebulauc.util.H5ConfigUtil.getConfig(r12)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONObject r4 = com.alipay.mobile.nebula.util.H5Utils.parseObject(r12)     // Catch:{ all -> 0x021f }
            if (r4 == 0) goto L_0x0058
            java.lang.String r12 = "enable"
            java.lang.Boolean r12 = r4.getBoolean(r12)     // Catch:{ all -> 0x021f }
            boolean r12 = r12.booleanValue()     // Catch:{ all -> 0x021f }
            r15.mEnableBridgeThread = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "size"
            int r12 = r4.getIntValue(r12)     // Catch:{ all -> 0x021f }
            r15.mBridgeThreadSize = r12     // Catch:{ all -> 0x021f }
            int r12 = r15.mBridgeThreadSize     // Catch:{ all -> 0x021f }
            if (r12 >= r10) goto L_0x0058
            r12 = 1
            r15.mBridgeThreadSize = r12     // Catch:{ all -> 0x021f }
        L_0x0058:
            java.lang.String r12 = "AlipayNetwork"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x021f }
            r13.<init>()     // Catch:{ all -> 0x021f }
            java.lang.String r14 = "online config spdySwitch "
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x021f }
            boolean r14 = r15.spdySwitch     // Catch:{ all -> 0x021f }
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x021f }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x021f }
            com.alipay.mobile.nebula.util.H5Log.d(r12, r13)     // Catch:{ all -> 0x021f }
            boolean r12 = r15.spdySwitch     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x016f
            java.lang.String r12 = "whiteList"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r3, r12, r13)     // Catch:{ all -> 0x021f }
            r15.spdyWhiteListV2 = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "blackList"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r3, r12, r13)     // Catch:{ all -> 0x021f }
            r15.spdyBlackListV2 = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "subrestype"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r3, r12, r13)     // Catch:{ all -> 0x021f }
            r15.subResTypeList = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "bsubreshost"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r3, r12, r13)     // Catch:{ all -> 0x021f }
            r15.subResBlackList = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "tinybsubresappid"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r3, r12, r13)     // Catch:{ all -> 0x021f }
            r15.subResAppBlackList = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "forcehosts"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r3, r12, r13)     // Catch:{ all -> 0x021f }
            r15.forceHostList = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "T"
            java.lang.String r13 = "useSpdyInHttps"
            java.lang.String r13 = r3.getString(r13)     // Catch:{ all -> 0x021f }
            boolean r12 = r12.equalsIgnoreCase(r13)     // Catch:{ all -> 0x021f }
            r15.useSpdyInHttps = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "abroad"
            java.lang.String r0 = r3.getString(r12)     // Catch:{ all -> 0x021f }
            boolean r12 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x021f }
            if (r12 != 0) goto L_0x0222
            java.lang.String r12 = "T"
            boolean r12 = r0.equalsIgnoreCase(r12)     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x0222
            r12 = 1
            r15.hasSetAbroad = r12     // Catch:{ all -> 0x021f }
        L_0x00d1:
            java.lang.String r13 = "AlipayNetwork"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x021f }
            r12.<init>()     // Catch:{ all -> 0x021f }
            java.lang.String r14 = "online config spdyWhiteListV2 "
            java.lang.StringBuilder r14 = r12.append(r14)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONArray r12 = r15.spdyWhiteListV2     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x0227
            com.alibaba.fastjson.JSONArray r12 = r15.spdyWhiteListV2     // Catch:{ all -> 0x021f }
            java.lang.String r12 = r12.toJSONString()     // Catch:{ all -> 0x021f }
        L_0x00e8:
            java.lang.StringBuilder r12 = r14.append(r12)     // Catch:{ all -> 0x021f }
            java.lang.String r14 = ", spdyBlackListV2 "
            java.lang.StringBuilder r14 = r12.append(r14)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONArray r12 = r15.spdyBlackListV2     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x022b
            com.alibaba.fastjson.JSONArray r12 = r15.spdyBlackListV2     // Catch:{ all -> 0x021f }
            java.lang.String r12 = r12.toJSONString()     // Catch:{ all -> 0x021f }
        L_0x00fc:
            java.lang.StringBuilder r12 = r14.append(r12)     // Catch:{ all -> 0x021f }
            java.lang.String r14 = ", hasSetAbroad "
            java.lang.StringBuilder r12 = r12.append(r14)     // Catch:{ all -> 0x021f }
            boolean r14 = r15.hasSetAbroad     // Catch:{ all -> 0x021f }
            java.lang.StringBuilder r12 = r12.append(r14)     // Catch:{ all -> 0x021f }
            java.lang.String r14 = ", subResTypeList "
            java.lang.StringBuilder r14 = r12.append(r14)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONArray r12 = r15.subResTypeList     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x022f
            com.alibaba.fastjson.JSONArray r12 = r15.subResTypeList     // Catch:{ all -> 0x021f }
            java.lang.String r12 = r12.toJSONString()     // Catch:{ all -> 0x021f }
        L_0x011c:
            java.lang.StringBuilder r12 = r14.append(r12)     // Catch:{ all -> 0x021f }
            java.lang.String r14 = ", subResBlackList "
            java.lang.StringBuilder r14 = r12.append(r14)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONArray r12 = r15.subResBlackList     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x0233
            com.alibaba.fastjson.JSONArray r12 = r15.subResBlackList     // Catch:{ all -> 0x021f }
            java.lang.String r12 = r12.toJSONString()     // Catch:{ all -> 0x021f }
        L_0x0130:
            java.lang.StringBuilder r12 = r14.append(r12)     // Catch:{ all -> 0x021f }
            java.lang.String r14 = ", subResAppBlackList "
            java.lang.StringBuilder r14 = r12.append(r14)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONArray r12 = r15.subResAppBlackList     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x0237
            com.alibaba.fastjson.JSONArray r12 = r15.subResAppBlackList     // Catch:{ all -> 0x021f }
            java.lang.String r12 = r12.toJSONString()     // Catch:{ all -> 0x021f }
        L_0x0144:
            java.lang.StringBuilder r12 = r14.append(r12)     // Catch:{ all -> 0x021f }
            java.lang.String r14 = ", forceHostList "
            java.lang.StringBuilder r14 = r12.append(r14)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONArray r12 = r15.forceHostList     // Catch:{ all -> 0x021f }
            if (r12 == 0) goto L_0x023b
            com.alibaba.fastjson.JSONArray r12 = r15.forceHostList     // Catch:{ all -> 0x021f }
            java.lang.String r12 = r12.toJSONString()     // Catch:{ all -> 0x021f }
        L_0x0158:
            java.lang.StringBuilder r12 = r14.append(r12)     // Catch:{ all -> 0x021f }
            java.lang.String r14 = ", useSpdyInHttps "
            java.lang.StringBuilder r12 = r12.append(r14)     // Catch:{ all -> 0x021f }
            boolean r14 = r15.useSpdyInHttps     // Catch:{ all -> 0x021f }
            java.lang.StringBuilder r12 = r12.append(r14)     // Catch:{ all -> 0x021f }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x021f }
            com.alipay.mobile.nebula.util.H5Log.d(r13, r12)     // Catch:{ all -> 0x021f }
        L_0x016f:
            java.lang.String r12 = "h5_http2HostList"
            java.lang.String r12 = com.alipay.mobile.nebulauc.util.H5ConfigUtil.getConfig(r12)     // Catch:{ all -> 0x021f }
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.parseArray(r12)     // Catch:{ all -> 0x021f }
            mHttp2HostList = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "h5_networkChannel"
            com.alibaba.fastjson.JSONObject r6 = com.alipay.mobile.nebulauc.util.H5ConfigUtil.getConfigJSONObject(r12)     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "hpappid"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r6, r12, r13)     // Catch:{ all -> 0x021f }
            mHighPriorityAppIdList = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "hpurl"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r6, r12, r13)     // Catch:{ all -> 0x021f }
            mHighPriorityUrlRegexList = r12     // Catch:{ all -> 0x021f }
            boolean r12 = com.alipay.mobile.nebula.util.H5Utils.isInTinyProcess()     // Catch:{ all -> 0x021f }
            if (r12 != 0) goto L_0x019c
            com.alipay.mobile.nebula.networksupervisor.H5NetworkSuScheduler.getInstance()     // Catch:{ all -> 0x021f }
        L_0x019c:
            java.lang.Class<com.alipay.mobile.nebula.provider.H5ConfigProvider> r12 = com.alipay.mobile.nebula.provider.H5ConfigProvider.class
            java.lang.String r12 = r12.getName()     // Catch:{ all -> 0x021f }
            java.lang.Object r2 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r12)     // Catch:{ all -> 0x021f }
            com.alipay.mobile.nebula.provider.H5ConfigProvider r2 = (com.alipay.mobile.nebula.provider.H5ConfigProvider) r2     // Catch:{ all -> 0x021f }
            if (r2 == 0) goto L_0x021c
            java.lang.String r12 = "h5_enableSendErrorToUcWhenException"
            com.alipay.mobile.nebulauc.impl.network.AlipayNetwork$1 r13 = new com.alipay.mobile.nebulauc.impl.network.AlipayNetwork$1     // Catch:{ all -> 0x021f }
            r13.<init>()     // Catch:{ all -> 0x021f }
            java.lang.String r9 = r2.getConfigWithNotifyChange(r12, r13)     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "no"
            boolean r12 = r12.equalsIgnoreCase(r9)     // Catch:{ all -> 0x021f }
            if (r12 != 0) goto L_0x023f
            r12 = r10
        L_0x01be:
            sEnableSendErrorToUcWhenException = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "h5_netstatlog"
            com.alibaba.fastjson.JSONObject r5 = r2.getConfigJSONObject(r12)     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "no"
            java.lang.String r13 = "errswitch"
            java.lang.String r13 = com.alipay.mobile.nebula.util.H5Utils.getString(r5, r13)     // Catch:{ all -> 0x021f }
            boolean r12 = r12.equalsIgnoreCase(r13)     // Catch:{ all -> 0x021f }
            if (r12 != 0) goto L_0x0242
            r12 = r10
        L_0x01d5:
            sEnableUploadNetInfoWhenError = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "yes"
            java.lang.String r13 = "switch"
            java.lang.String r13 = com.alipay.mobile.nebula.util.H5Utils.getString(r5, r13)     // Catch:{ all -> 0x021f }
            boolean r12 = r12.equalsIgnoreCase(r13)     // Catch:{ all -> 0x021f }
            sEnableUploadNetInfoWhenSuccess = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "url"
            r13 = 0
            com.alibaba.fastjson.JSONArray r12 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r5, r12, r13)     // Catch:{ all -> 0x021f }
            mEnableUploadNetInfoUrlRegexList = r12     // Catch:{ all -> 0x021f }
            java.lang.String r12 = "no"
            java.lang.String r13 = "h5_interceptInvalidDomain"
            java.lang.String r13 = r2.getConfig(r13)     // Catch:{ all -> 0x021f }
            boolean r12 = r12.equalsIgnoreCase(r13)     // Catch:{ all -> 0x021f }
            if (r12 != 0) goto L_0x0244
        L_0x01fc:
            sInterceptInvalidDomain = r10     // Catch:{ all -> 0x021f }
            java.lang.String r10 = "h5_ResourceFilter1"
            com.alibaba.fastjson.JSONObject r7 = r2.getConfigJSONObject(r10)     // Catch:{ all -> 0x021f }
            java.lang.String r10 = "h5_ResourceFilter2"
            com.alibaba.fastjson.JSONObject r8 = r2.getConfigJSONObject(r10)     // Catch:{ all -> 0x021f }
            java.lang.String r10 = "blackList"
            r11 = 0
            com.alibaba.fastjson.JSONArray r10 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r7, r10, r11)     // Catch:{ all -> 0x021f }
            sSubResInSecBlackList1 = r10     // Catch:{ all -> 0x021f }
            java.lang.String r10 = "blackList"
            r11 = 0
            com.alibaba.fastjson.JSONArray r10 = com.alipay.mobile.nebula.util.H5Utils.getJSONArray(r8, r10, r11)     // Catch:{ all -> 0x021f }
            sSubResInSecBlackList2 = r10     // Catch:{ all -> 0x021f }
        L_0x021c:
            monitor-exit(r15)     // Catch:{ all -> 0x021f }
            goto L_0x0008
        L_0x021f:
            r10 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x021f }
            throw r10
        L_0x0222:
            r12 = 0
            r15.hasSetAbroad = r12     // Catch:{ all -> 0x021f }
            goto L_0x00d1
        L_0x0227:
            java.lang.String r12 = "null"
            goto L_0x00e8
        L_0x022b:
            java.lang.String r12 = "null"
            goto L_0x00fc
        L_0x022f:
            java.lang.String r12 = "null"
            goto L_0x011c
        L_0x0233:
            java.lang.String r12 = "null"
            goto L_0x0130
        L_0x0237:
            java.lang.String r12 = "null"
            goto L_0x0144
        L_0x023b:
            java.lang.String r12 = "null"
            goto L_0x0158
        L_0x023f:
            r12 = r11
            goto L_0x01be
        L_0x0242:
            r12 = r11
            goto L_0x01d5
        L_0x0244:
            r10 = r11
            goto L_0x01fc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulauc.impl.network.AlipayNetwork.init():void");
    }

    public IRequest formatRequest(EventHandler handler, String url, String method, boolean isUCProxyReq, Map<String, String> headers, Map<String, String> ucHeaders, Map<String, String> uploadFileMap, Map<String, byte[]> uploadDataMap, long uploadFileTotalLen, int requestType, int loadType) {
        init();
        boolean checkCrossOrigin = H5Utils.containNebulaAddcors(url);
        String url2 = H5UrlHelper.stripAnchor(url);
        H5Log.d(TAG, "formatRequest url is " + url2 + ", requestType is " + requestType + ", loadType is " + loadType);
        AlipayRequest alipayRequest = null;
        H5Page topH5Page = getTopH5Page();
        if (CommonUtil.isMainDoc(requestType) && handler != null && H5PreConnectManager.isPreDownloadEnabled() && !FallbackRequestHelper.isFallbackRequest(url2, topH5Page)) {
            alipayRequest = H5PreConnectManager.getInstance().getRequest(url2);
        }
        if (alipayRequest != null) {
            H5Log.d(TAG, "get pre connect request for url: " + url2);
            if (topH5Page != null) {
                if (topH5Page.getPageData() != null) {
                    topH5Page.getPageData().setUsePreRequest(true);
                }
                alipayRequest.attachPage(topH5Page);
            }
            alipayRequest.setEventHandler(handler);
            return alipayRequest;
        }
        AlipayRequest alipayRequest2 = new AlipayRequest(url2, method, isUCProxyReq, headers, ucHeaders, uploadFileMap, uploadDataMap, uploadFileTotalLen, requestType, loadType);
        alipayRequest2.setEventHandler(handler);
        alipayRequest2.setNetWorkManager(this.h5NetworkManager);
        alipayRequest2.setCheckCrossOrigin(checkCrossOrigin);
        if (!(topH5Page == null || handler == null)) {
            alipayRequest2.applyStartParams(topH5Page.getParams());
            alipayRequest2.attachPage(topH5Page);
        }
        this.useSpdy = false;
        if (!AlipaySpdyDowngrade.getSwitchControl()) {
            H5Log.d(TAG, "formatRequest !useSpdyFromJS return");
            alipayRequest2.setCapture(true);
            return alipayRequest2;
        } else if (CommonUtil.isApk(url2)) {
            H5Log.d(TAG, "formatRequest isApk return");
            return alipayRequest2;
        } else if (AlipaySpdyDowngrade.isExistMemoryDowngradeRule(url2)) {
            H5Log.d(TAG, "formatRequest isExistMemoryDowngradeRule return");
            return alipayRequest2;
        } else if (AlipaySpdyDowngrade.isExistDiskDowngradeRule(url2)) {
            H5Log.d(TAG, "formatRequest isExistDiskDowngradeRule return");
            return alipayRequest2;
        } else {
            if (CommonUtil.isMainDoc(requestType)) {
                H5Log.d(TAG, "handleMainFrame");
                handleMainFrame(url2);
                if (this.useSpdy) {
                    String host = getHost(url2);
                    if (host != null) {
                        AlipaySpdyDowngrade.addSMainDocSpdyTable(host);
                    }
                }
            } else {
                H5Log.d(TAG, "handleSubRes");
                handleSubRes(headers, url2);
            }
            return alipayRequest2;
        }
    }

    private H5Page getTopH5Page() {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            H5Page h5Page = h5Service.getTopH5Page();
            if (h5Page != null) {
                return h5Page;
            }
        }
        return null;
    }

    private void handleMainFrame(String url) {
        String host = getHost(url);
        if (host == null) {
            H5Log.d(TAG, "handleMainFrame host == null");
            return;
        }
        if (this.spdyWhiteListV2 != null && !this.spdyWhiteListV2.isEmpty()) {
            int i = 0;
            while (true) {
                if (i >= this.spdyWhiteListV2.size()) {
                    break;
                }
                String whiteItem = this.spdyWhiteListV2.getString(i);
                if (!TextUtils.isEmpty(whiteItem) && CommonUtil.isUrlMatch(whiteItem, host)) {
                    this.useSpdy = true;
                    H5Log.d(TAG, "spdyWhiteListV2 match");
                    break;
                }
                i++;
            }
        }
        if (this.spdyBlackListV2 != null && !this.spdyBlackListV2.isEmpty()) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.spdyBlackListV2.size()) {
                    break;
                }
                String blackItem = this.spdyBlackListV2.getString(i2);
                if (!TextUtils.isEmpty(blackItem) && CommonUtil.isUrlMatch(blackItem, host)) {
                    this.useSpdy = false;
                    H5Log.d(TAG, "spdyBlackListV2 match");
                    break;
                }
                i2++;
            }
        }
        boolean isMatchForceHostList = false;
        if (this.forceHostList != null && !this.forceHostList.isEmpty()) {
            int i3 = 0;
            while (true) {
                if (i3 >= this.forceHostList.size()) {
                    break;
                }
                String whiteItem2 = this.forceHostList.getString(i3);
                if (!TextUtils.isEmpty(whiteItem2) && CommonUtil.isUrlMatch(whiteItem2, host)) {
                    this.useSpdy = true;
                    isMatchForceHostList = true;
                    H5Log.d(TAG, "forceHostList match");
                    break;
                }
                i3++;
            }
        }
        if ("https".equalsIgnoreCase(getScheme(url)) && !this.useSpdyInHttps) {
            if (!isMatchForceHostList || !this.useSpdy) {
                H5Log.d(TAG, "handleMainFrame forbid https");
                this.useSpdy = false;
            } else {
                H5Log.d(TAG, "handleMainFrame pass https");
                this.useSpdy = true;
            }
        }
        if (!H5Flag.isInChane && !this.hasSetAbroad && !isMatchForceHostList) {
            H5Log.d(TAG, "formatRequest !hasSetAbroad");
            this.useSpdy = false;
        }
    }

    private void handleSubRes(Map<String, String> headers, String url) {
        String host = getHost(url);
        if (host == null) {
            H5Log.d(TAG, "handleSubRes host == null");
            return;
        }
        boolean ifSpecialXHRHeader = false;
        String refererHost = null;
        if (headers != null) {
            for (String key : headers.keySet()) {
                if ("referer".equalsIgnoreCase(key)) {
                    refererHost = getHost(headers.get(key));
                }
                if ("X-Requested-With".equalsIgnoreCase(key)) {
                    ifSpecialXHRHeader = "XMLHttpRequest".equalsIgnoreCase(headers.get(key));
                }
            }
        }
        H5Log.d(TAG, "ifSpecialXHRHeader " + ifSpecialXHRHeader);
        if (refererHost == null) {
            H5Log.d(TAG, "handleSubRes refererHost == null");
        } else if (AlipaySpdyDowngrade.isInsMainDocSpdyTable(refererHost)) {
            String scheme = getScheme(url);
            if (this.subResTypeList != null && !this.subResTypeList.isEmpty()) {
                int i = 0;
                while (true) {
                    if (i >= this.subResTypeList.size()) {
                        break;
                    }
                    String subResTypeItem = this.subResTypeList.getString(i);
                    if (!TextUtils.isEmpty(subResTypeItem)) {
                        String urlWithoutQuery = CommonUtil.getUrlWithOutQuery(url);
                        if (!TextUtils.isEmpty(urlWithoutQuery) && (urlWithoutQuery.endsWith(subResTypeItem) || urlWithoutQuery.endsWith(subResTypeItem.toUpperCase()))) {
                            this.useSpdy = true;
                            H5Log.d(TAG, "subResTypeList match");
                        }
                    }
                    i++;
                }
                this.useSpdy = true;
                H5Log.d(TAG, "subResTypeList match");
            }
            boolean isMatchBlackList = false;
            if (this.subResBlackList != null && !this.subResBlackList.isEmpty()) {
                int i2 = 0;
                while (true) {
                    if (i2 >= this.subResBlackList.size()) {
                        break;
                    }
                    String subResBlackItem = this.subResBlackList.getString(i2);
                    if (!TextUtils.isEmpty(subResBlackItem) && CommonUtil.isUrlMatch(subResBlackItem, host)) {
                        this.useSpdy = false;
                        isMatchBlackList = true;
                        H5Log.d(TAG, "subResBlackList match");
                        break;
                    }
                    i2++;
                }
            }
            if (this.subResTypeList != null && !this.subResTypeList.isEmpty() && this.subResTypeList.contains("ajax") && ifSpecialXHRHeader && !isMatchBlackList) {
                this.useSpdy = true;
                H5Log.d(TAG, "ifSpecialXHRHeader match");
            }
            boolean isMatchForceHostList = false;
            if (this.forceHostList != null && !this.forceHostList.isEmpty()) {
                int i3 = 0;
                while (true) {
                    if (i3 >= this.forceHostList.size()) {
                        break;
                    }
                    String whiteItem = this.forceHostList.getString(i3);
                    if (!TextUtils.isEmpty(whiteItem) && CommonUtil.isUrlMatch(whiteItem, host)) {
                        this.useSpdy = true;
                        isMatchForceHostList = true;
                        H5Log.d(TAG, "forceHostList match");
                        break;
                    }
                    i3++;
                }
            }
            if ("https".equalsIgnoreCase(scheme) && !this.useSpdyInHttps) {
                if (!isMatchForceHostList || !this.useSpdy) {
                    H5Log.d(TAG, "handleSubRes forbid https");
                    this.useSpdy = false;
                    return;
                }
                H5Log.d(TAG, "handleSubRes pass https");
                this.useSpdy = true;
            }
        }
    }

    public boolean requestURL(EventHandler handler, String url, String method, boolean isUCProxyReq, Map<String, String> headers, Map<String, String> ucHeaders, Map<String, String> uploadFileMap, Map<String, byte[]> uploadDataMap, long uploadFileTotalLen, int requestType, int loadType) {
        init();
        H5Log.d(TAG, "requestURL url is " + url + ", requestType is " + requestType + ", loadType is " + loadType);
        return sendRequest(formatRequest(handler, url, method, isUCProxyReq, headers, ucHeaders, uploadFileMap, uploadDataMap, uploadFileTotalLen, requestType, loadType));
    }

    public void cancelPrefetchLoad() {
        init();
    }

    public void clearUserSslPrefTable() {
        init();
    }

    public boolean sendRequest(IRequest request) {
        init();
        H5Log.d(TAG, "sendRequest useSpdy " + this.useSpdy);
        if (!(request instanceof AlipayRequest)) {
            return false;
        }
        final AlipayRequest alipayRequest = (AlipayRequest) request;
        if (this.mEnableBridgeThread) {
            if (mRequestExecutor == null) {
                mRequestExecutor = new ThreadPoolExecutor(this.mBridgeThreadSize, this.mBridgeThreadSize + 2, 15, TimeUnit.SECONDS, new LinkedBlockingQueue(200), new H5SingleThreadFactory("H5_delegateNetwork"), new DiscardOldestPolicy());
            }
            mRequestExecutor.execute(new Runnable() {
                public void run() {
                    alipayRequest.sendRequest(AlipayNetwork.this.useSpdy, "NO", true);
                }
            });
        } else {
            alipayRequest.sendRequest(this.useSpdy, "NO", true);
        }
        return true;
    }

    public String getVersion() {
        init();
        return "1.0";
    }

    public int getNetworkType() {
        init();
        return 2;
    }

    private String getHost(String url) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri != null) {
            return uri.getHost();
        }
        return null;
    }

    private String getScheme(String url) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri != null) {
            return uri.getScheme();
        }
        return null;
    }

    public boolean isUseSpdy() {
        return this.useSpdy;
    }

    public H5NetworkManager getH5NetworkManager() {
        return this.h5NetworkManager;
    }

    static boolean isUseHttp2(String url) {
        String url2 = H5Utils.getCleanUrl(url);
        if (!TextUtils.isEmpty(url2) && mHttp2HostList != null && !mHttp2HostList.isEmpty()) {
            int i = 0;
            while (i < mHttp2HostList.size()) {
                String host = mHttp2HostList.getString(i);
                if (TextUtils.isEmpty(host) || !url2.contains(host)) {
                    i++;
                } else {
                    H5Log.d(TAG, "isUseHttp2, " + url2 + ", use http2");
                    return true;
                }
            }
        }
        H5Log.d(TAG, "isUseHttp2, " + url2 + ", not use http2");
        return false;
    }

    static boolean isHighPriority(String appId, String url) {
        return isHighPriorityApp(appId) || isHighPriorityUrl(url);
    }

    private static boolean isHighPriorityApp(String appId) {
        if (!TextUtils.isEmpty(appId) && mHighPriorityAppIdList != null && !mHighPriorityAppIdList.isEmpty()) {
            for (int i = 0; i < mHighPriorityAppIdList.size(); i++) {
                if (appId.equalsIgnoreCase(mHighPriorityAppIdList.getString(i))) {
                    H5Log.d(TAG, appId + "is high priority app");
                    return true;
                }
            }
        }
        H5Log.d(TAG, appId + "is not high priority app");
        return false;
    }

    private static boolean isHighPriorityUrl(String url) {
        String url2 = H5Utils.getCleanUrl(url);
        if (!TextUtils.isEmpty(url2) && mHighPriorityUrlRegexList != null && !mHighPriorityUrlRegexList.isEmpty()) {
            for (int i = 0; i < mHighPriorityUrlRegexList.size(); i++) {
                if (H5PatternHelper.matchRegex(mHighPriorityUrlRegexList.getString(i), url2)) {
                    H5Log.d(TAG, url2 + "is high priority url");
                    return true;
                }
            }
        }
        return false;
    }

    static boolean canUploadNetInfo(String url) {
        if (!TextUtils.isEmpty(url) && mEnableUploadNetInfoUrlRegexList != null && !mEnableUploadNetInfoUrlRegexList.isEmpty()) {
            for (int i = 0; i < mEnableUploadNetInfoUrlRegexList.size(); i++) {
                if (H5PatternHelper.matchRegex(mEnableUploadNetInfoUrlRegexList.getString(i), url)) {
                    H5Log.d(TAG, "canUploadNetInfo for url : " + url);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean shouldInterceptSubResRequestForSec(String url) {
        String url2 = H5Utils.getCleanUrl(url);
        if (!TextUtils.isEmpty(url2)) {
            if (sSubResInSecBlackList1 != null && !sSubResInSecBlackList1.isEmpty()) {
                for (int i = 0; i < sSubResInSecBlackList1.size(); i++) {
                    if (H5PatternHelper.matchRegex(sSubResInSecBlackList1.getString(i), url2)) {
                        H5Log.d(TAG, url2 + " is in the sec black list!");
                        return true;
                    }
                }
            }
            if (sSubResInSecBlackList2 != null && !sSubResInSecBlackList2.isEmpty()) {
                for (int i2 = 0; i2 < sSubResInSecBlackList2.size(); i2++) {
                    if (H5PatternHelper.matchRegex(sSubResInSecBlackList2.getString(i2), url2)) {
                        H5Log.d(TAG, url2 + " is in the sec black list!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
