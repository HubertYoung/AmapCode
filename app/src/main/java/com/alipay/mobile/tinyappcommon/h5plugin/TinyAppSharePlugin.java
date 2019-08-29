package com.alipay.mobile.tinyappcommon.h5plugin;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadOption.QUALITITY;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUpRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageUploadRsp;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.alipay.mobile.nebula.basebridge.H5BaseBridgeContext;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.api.TinyAppShareInterface;
import com.alipay.mobile.tinyappcommon.embedview.H5EmbedWebView;
import com.alipay.mobile.tinyappcommon.mode.TinyAppEnvMode;
import com.alipay.multimedia.js.file.H5FileUploadPlugin;
import com.alipay.multimedia.js.image.H5CompressImagePlugin;
import com.tencent.connect.common.Constants;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TinyAppSharePlugin extends H5SimplePlugin {
    public static final String SHARE_TINY_APP_MSG = "shareTinyAppMsg";
    /* access modifiers changed from: private */
    public static final String TAG = TinyAppSharePlugin.class.getSimpleName();
    /* access modifiers changed from: private */
    public static final ArrayList channelBlacklist = new ArrayList();
    private static final ArrayList onlySelectChannel = new ArrayList();

    /* renamed from: com.alipay.mobile.tinyappcommon.h5plugin.TinyAppSharePlugin$4 reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$alipay$mobile$tinyappcommon$mode$TinyAppEnvMode = new int[TinyAppEnvMode.values().length];

        static {
            try {
                $SwitchMap$com$alipay$mobile$tinyappcommon$mode$TinyAppEnvMode[TinyAppEnvMode.DEVELOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public TinyAppSharePlugin() {
        onlySelectChannel.add("qrcode");
        onlySelectChannel.add("Weibo");
        onlySelectChannel.add("ALPContact");
        onlySelectChannel.add("ALPTimeLine");
        onlySelectChannel.add("SMS");
        onlySelectChannel.add("Weixin");
        onlySelectChannel.add("WeixinTimeLine");
        onlySelectChannel.add(Constants.SOURCE_QQ);
        onlySelectChannel.add("QQZone");
        onlySelectChannel.add("DingTalkSession");
        channelBlacklist.add("Weixin");
        channelBlacklist.add(Constants.SOURCE_QQ);
        channelBlacklist.add("WeixinTimeLine");
        channelBlacklist.add("QQZone");
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(SHARE_TINY_APP_MSG);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (SHARE_TINY_APP_MSG.equals(event.getAction())) {
            shareTinyAppMsg(event, context);
        }
        return true;
    }

    private void shareTinyAppMsg(final H5Event event, final H5BridgeContext context) {
        H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
            public void run() {
                String appId;
                JSONObject object = event.getParam();
                String page = object.getString("page");
                String title = object.getString("title");
                String desc = object.getString("desc");
                String content = object.getString("content");
                String imageUrl = object.getString("imageUrl");
                String bgImageUrl = object.getString("bgImgUrl");
                String appId2 = object.getString("appId");
                H5Page h5Page = event.getH5page();
                if (h5Page == null) {
                    context.sendError(event, Error.UNKNOWN_ERROR);
                    return;
                }
                if (TextUtils.isEmpty(appId2)) {
                    appId2 = H5Utils.getString(h5Page.getParams(), (String) "tinyAppId");
                }
                if (TextUtils.isEmpty(appId)) {
                    if (TinyAppMiniServicePlugin.appIsMiniService(h5Page)) {
                        appId = H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
                    } else if (h5Page.getExtra(H5EmbedWebView.WEB_VIEW_PAGE_TAG) instanceof String) {
                        appId = H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
                    } else {
                        appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
                    }
                    if (TextUtils.isEmpty(appId)) {
                        context.sendError(event, Error.INVALID_PARAM);
                        H5Log.d(TinyAppSharePlugin.TAG, "shareTinyAppMsg... appId is null");
                        return;
                    }
                }
                if (TextUtils.isEmpty(title)) {
                    title = TinyappUtils.getAppName(appId, h5Page);
                }
                String page2 = TinyAppSharePlugin.this.encodeURIComponent(page);
                String url = "alipays://platformapi/startapp?appId=" + appId;
                if (!TextUtils.isEmpty(page2)) {
                    url = url + (H5Utils.canTransferH5ToTiny(appId) ? "&url=" : "&page=") + page2;
                }
                TinyAppEnvMode env = TinyAppEnvMode.valueOf(h5Page);
                if (env != TinyAppEnvMode.RELEASE) {
                    url = url + "&nbsource=debug&nbsn=" + env.toStringOfNebula();
                }
                String nbsv = H5Utils.getString(h5Page.getParams(), (String) H5PreferAppList.nbsv);
                if (!TextUtils.isEmpty(nbsv)) {
                    url = url + "&nbsv=" + nbsv;
                }
                String version = H5Utils.getString(h5Page.getParams(), (String) "appVersion");
                if (!TextUtils.isEmpty(version)) {
                    url = url + "&enbsv=" + version;
                }
                TinyAppShareInterface shareInterface = TinyAppService.get().getTinyAppShareInterface();
                if (shareInterface != null) {
                    H5Log.d(TinyAppSharePlugin.TAG, "shareTinyAppMsg..." + url + ",title=" + title + ",desc=" + desc + ",content=" + content);
                    shareInterface.startShare(appId, url, title, desc, content, imageUrl, h5Page, event.getActivity(), context);
                    return;
                }
                TinyAppSharePlugin.this.alipayShareTinyAppMsg(appId, url, title, desc, content, imageUrl, bgImageUrl, event, context, object);
            }
        });
    }

    /* access modifiers changed from: private */
    public void alipayShareTinyAppMsg(String appId, String url, String title, String desc, String content, String imageUrl, String bgImageUrl, H5Event h5Event, H5BridgeContext bridgeContext, JSONObject eventParams) {
        final H5Page h5Page = h5Event.getH5page();
        if (h5Page != null) {
            final H5Service h5Service = H5ServiceUtils.getH5Service();
            if (h5Service != null) {
                JSONObject param1 = new JSONObject();
                param1.put((String) "override", (Object) Boolean.valueOf(true));
                h5Page.sendEvent(CommonEvents.SET_TOOL_MENU, param1);
                JSONObject param2 = new JSONObject();
                param2.put((String) "bizType", (Object) "H5App_XCX");
                param2.put((String) "sendEvent", (Object) Boolean.valueOf(false));
                if (TextUtils.equals(H5Utils.getString(eventParams, (String) "sharePadType", (String) "C2A"), "C2C")) {
                    param2.put((String) "bizType", (Object) "H5App_XCX_O2O");
                } else {
                    param2.put((String) "bizType", (Object) "H5App_XCX");
                }
                JSONArray array = new JSONArray();
                array.addAll(onlySelectChannel);
                param2.put((String) "onlySelectChannel", (Object) array);
                h5Event.setAction(CommonEvents.START_SHARE);
                h5Event.setParam(param2);
                final String str = appId;
                final H5Event h5Event2 = h5Event;
                final String str2 = url;
                final String str3 = title;
                final String str4 = desc;
                final String str5 = content;
                final String str6 = imageUrl;
                final H5BridgeContext h5BridgeContext = bridgeContext;
                final JSONObject jSONObject = eventParams;
                final String str7 = bgImageUrl;
                h5Service.sendEvent(h5Event, new H5BaseBridgeContext() {
                    public boolean sendBack(JSONObject param, boolean keep) {
                        String message = null;
                        if (param != null) {
                            message = param.getString("message");
                        }
                        if (!TextUtils.isEmpty(message) && message.contains("canceled")) {
                            return false;
                        }
                        String tempChannelName = "";
                        if (param != null) {
                            tempChannelName = param.getString("channelName");
                        }
                        final String channelName = tempChannelName;
                        JSONObject param3 = new JSONObject();
                        param3.put((String) "appId", (Object) str);
                        h5Event2.setAction("getAppInfo");
                        h5Event2.setParam(param3);
                        return h5Service.sendEvent(h5Event2, new H5BaseBridgeContext() {
                            public boolean sendBack(JSONObject param, boolean keep) {
                                String str;
                                String str2;
                                String name = "";
                                String iconUrl = "";
                                if (param != null) {
                                    name = param.getString("name");
                                    iconUrl = param.getString("iconUrl");
                                }
                                String afterUrl = str2 + "&chInfo=ch_share__chsub_" + channelName;
                                if (TinyAppEnvMode.valueOf(h5Page) != TinyAppEnvMode.RELEASE || TinyAppMiniServicePlugin.appIsMiniService(h5Page)) {
                                    afterUrl = afterUrl + "&ap_framework_sceneId=10000007";
                                }
                                JSONObject shareParams = new JSONObject();
                                shareParams.put((String) "name", (Object) channelName);
                                final JSONObject param4 = new JSONObject();
                                param4.put((String) "title", (Object) str3);
                                param4.put((String) "content", (Object) str4);
                                param4.put((String) "imageUrl", (Object) iconUrl);
                                param4.put((String) "captureScreen", (Object) Boolean.valueOf(false));
                                param4.put((String) "url", (Object) afterUrl);
                                param4.put((String) "contentType", (Object) "url");
                                shareParams.put((String) "param", (Object) param4);
                                if (TinyAppSharePlugin.channelBlacklist.contains(channelName)) {
                                    String customContent = "";
                                    if (!TextUtils.isEmpty(str5)) {
                                        if (str5.length() >= 28) {
                                            customContent = str5.substring(0, 28);
                                        } else {
                                            customContent = str5;
                                        }
                                    }
                                    if (TextUtils.isEmpty(name)) {
                                        name = "";
                                    }
                                    if (TextUtils.isEmpty(str4) && !TextUtils.isEmpty(name)) {
                                        param4.put((String) "content", (Object) "支付宝小程序 - " + name);
                                    }
                                    JSONObject param5 = new JSONObject();
                                    param5.put((String) "bizType", (Object) "COMMON_CONFIG");
                                    param5.put((String) "iconURL", (Object) iconUrl);
                                    param5.put((String) "btn1", (Object) "取消");
                                    param5.put((String) "btn2", (Object) "去看看");
                                    param5.put((String) "btn2A", (Object) afterUrl);
                                    param5.put((String) "preContent", (Object) customContent + "#吱口令#长按复制此条消息，打开支付宝即可使用" + name + "小程序");
                                    param4.put((String) "otherParams", (Object) param5);
                                    if (!TextUtils.isEmpty(str6)) {
                                        param4.put((String) "imageUrl", (Object) str6);
                                    }
                                }
                                if ("Weibo".equals(channelName)) {
                                    param4.put((String) "url", (Object) "https://ds.alipay.com/?scheme=" + TinyAppSharePlugin.this.encodeURIComponent(afterUrl));
                                } else if ("ALPTimeLine".equals(channelName) && !TextUtils.isEmpty(str4)) {
                                    param4.put((String) "title", (Object) str3 + " - " + str4);
                                    if (!TextUtils.isEmpty(str6)) {
                                        if (!str6.startsWith("http")) {
                                            String absUrl = H5Utils.getAbsoluteUrlV2(H5Utils.getString(h5Page.getParams(), (String) "url"), str6, h5Page.getParams());
                                            if (!TextUtils.isEmpty(absUrl)) {
                                                H5ContentProvider provider = h5Page.getSession() != null ? h5Page.getSession().getWebProvider() : null;
                                                if (provider != null) {
                                                    final JSONObject jSONObject = shareParams;
                                                    AnonymousClass1 r0 = new ResponseListen() {
                                                        public void onGetResponse(WebResourceResponse webResourceResponse) {
                                                            if (webResourceResponse == null || webResourceResponse.getData() == null) {
                                                                TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject, h5BridgeContext);
                                                                return;
                                                            }
                                                            try {
                                                                APImageUpRequest req = new APImageUpRequest();
                                                                req.fileData = TinyappUtils.toByteArray(webResourceResponse.getData(), false);
                                                                req.option = new APImageUploadOption();
                                                                req.option.setQua(QUALITITY.MIDDLE);
                                                                req.option.setPublic = Boolean.valueOf(true);
                                                                req.callback = new APImageUploadCallback() {
                                                                    public void onCompressSucc(Drawable drawable) {
                                                                    }

                                                                    public void onStartUpload(APMultimediaTaskModel taskModel) {
                                                                    }

                                                                    public void onProcess(APMultimediaTaskModel task, int percentage) {
                                                                    }

                                                                    public void onSuccess(APImageUploadRsp apImageUploadRsp) {
                                                                        String multimediaID = (apImageUploadRsp == null || apImageUploadRsp.getTaskStatus() == null || apImageUploadRsp.getTaskStatus().getCloudId() == null) ? "" : apImageUploadRsp.getTaskStatus().getCloudId();
                                                                        String url = (apImageUploadRsp == null || TextUtils.isEmpty(apImageUploadRsp.getPublicUrl())) ? "" : apImageUploadRsp.getPublicUrl();
                                                                        H5Log.d(TinyAppSharePlugin.TAG, "upload success: multimediaID:" + multimediaID + ", url: " + url);
                                                                        if (!TextUtils.isEmpty(url)) {
                                                                            param4.put((String) "imageUrl", (Object) url);
                                                                        }
                                                                        TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject, h5BridgeContext);
                                                                    }

                                                                    public void onError(APImageUploadRsp apImageUploadRsp, Exception e) {
                                                                        H5Log.d(TinyAppSharePlugin.TAG, "upload error: " + e);
                                                                        TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject, h5BridgeContext);
                                                                    }
                                                                };
                                                                ((MultimediaImageService) H5Utils.findServiceByInterface(MultimediaImageService.class.getName())).uploadImage(req, (String) "multiMedia");
                                                            } catch (Throwable e) {
                                                                H5Log.e(TinyAppSharePlugin.TAG, "read image error", e);
                                                                TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject, h5BridgeContext);
                                                            }
                                                        }
                                                    };
                                                    provider.getContent(absUrl, (ResponseListen) r0);
                                                    return true;
                                                }
                                            }
                                        } else {
                                            param4.put((String) "imageUrl", (Object) str6);
                                        }
                                    }
                                }
                                if ("ALPContact".equals(channelName)) {
                                    shareParams.clear();
                                    shareParams.put((String) "name", (Object) channelName);
                                    switch (AnonymousClass4.$SwitchMap$com$alipay$mobile$tinyappcommon$mode$TinyAppEnvMode[TinyAppEnvMode.valueOf(h5Page).ordinal()]) {
                                        case 1:
                                            name = "开发版·" + TinyappUtils.getAppName(str, h5Page);
                                            break;
                                        default:
                                            if (TextUtils.isEmpty(name)) {
                                                name = TinyappUtils.getAppName(str, h5Page);
                                                break;
                                            }
                                            break;
                                    }
                                    final JSONObject param6 = new JSONObject();
                                    if (!TextUtils.isEmpty(H5Utils.getString(h5Page.getParams(), (String) "tinyAppId"))) {
                                        param6.put((String) "contentType", (Object) "tinyAppShort");
                                    } else {
                                        param6.put((String) "contentType", (Object) Const.TYPE_RN);
                                    }
                                    if (TextUtils.isEmpty(str3)) {
                                        str2 = name;
                                    } else {
                                        str2 = str3;
                                    }
                                    r0 = "title";
                                    param6.put((String) "title", (Object) str2);
                                    if (!TextUtils.isEmpty(str4)) {
                                        param6.put((String) "content", (Object) str4);
                                    }
                                    param6.put((String) "url", (Object) afterUrl);
                                    JSONObject param7 = new JSONObject();
                                    param7.put((String) "appName", (Object) name);
                                    param7.put((String) H5AppHandler.sAppIcon, (Object) iconUrl);
                                    param7.put((String) "appType", (Object) "小程序");
                                    param6.put((String) "otherParams", (Object) param7);
                                    shareParams.put((String) "param", (Object) param6);
                                    if (TextUtils.isEmpty(str6)) {
                                        h5Event2.setAction(H5Param.SNAPSHOT);
                                        JSONObject param8 = new JSONObject();
                                        param8.put((String) "range", (Object) "embedview");
                                        param8.put((String) "dataType", (Object) H5CompressImagePlugin.DATA_TYPE_FILE_URL);
                                        param8.put((String) "saveToGallery", (Object) Boolean.valueOf(false));
                                        h5Event2.setParam(param8);
                                        H5Service h5Service = h5Service;
                                        H5Event h5Event = h5Event2;
                                        final JSONObject jSONObject2 = shareParams;
                                        AnonymousClass2 r02 = new H5BaseBridgeContext() {
                                            public boolean sendBack(JSONObject param, boolean keep) {
                                                String fileURL = null;
                                                if (param != null) {
                                                    fileURL = param.getString(H5CompressImagePlugin.DATA_TYPE_FILE_URL);
                                                }
                                                if (TextUtils.isEmpty(fileURL)) {
                                                    return TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject2, h5BridgeContext);
                                                }
                                                JSONObject param9 = new JSONObject();
                                                param9.put((String) "data", (Object) fileURL);
                                                param9.put((String) "dataType", (Object) H5CompressImagePlugin.DATA_TYPE_FILE_URL);
                                                param9.put((String) MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, (Object) Integer.valueOf(3));
                                                param9.put((String) com.alipay.mobile.beehive.audio.Constants.KEY_AUDIO_BUSINESS_ID, (Object) "multiMedia");
                                                h5Event2.setAction("uploadImage");
                                                h5Event2.setParam(param9);
                                                return h5Service.sendEvent(h5Event2, new H5BaseBridgeContext() {
                                                    public boolean sendBack(JSONObject param, boolean keep) {
                                                        String multimediaID = null;
                                                        if (param != null) {
                                                            multimediaID = param.getString(H5FileUploadPlugin.RESULT_ID);
                                                        }
                                                        if (!TextUtils.isEmpty(multimediaID)) {
                                                            param6.put((String) "iconUrl", (Object) multimediaID);
                                                        }
                                                        return TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject2, h5BridgeContext);
                                                    }
                                                });
                                            }
                                        };
                                        return h5Service.sendEvent(h5Event, r02);
                                    }
                                    h5Event2.setAction("getShareImageUrl");
                                    JSONObject param10 = new JSONObject();
                                    param10.put((String) "originalImageUrl", (Object) str6);
                                    h5Event2.setParam(param10);
                                    H5Service h5Service2 = h5Service;
                                    H5Event h5Event2 = h5Event2;
                                    final JSONObject jSONObject3 = shareParams;
                                    AnonymousClass3 r03 = new H5BaseBridgeContext() {
                                        public boolean sendBack(JSONObject param, boolean keep) {
                                            if (param == null || !TextUtils.isEmpty(param.getString("error"))) {
                                                param6.put((String) "imageUrl", (Object) str6);
                                            } else {
                                                param6.put((String) "imageUrl", (Object) param.getString("imageUrl"));
                                            }
                                            return TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject3, h5BridgeContext);
                                        }
                                    };
                                    return h5Service2.sendEvent(h5Event2, r03);
                                } else if ("qrcode".equals(channelName)) {
                                    shareParams.put((String) "name", (Object) channelName);
                                    JSONObject param62 = new JSONObject();
                                    param62.put((String) "contentType", (Object) Const.TYPE_RN);
                                    if (TextUtils.isEmpty(str3)) {
                                        str = name;
                                    } else {
                                        str = str3;
                                    }
                                    r0 = "title";
                                    param62.put((String) "title", (Object) str);
                                    if (!TextUtils.isEmpty(str4)) {
                                        param62.put((String) "content", (Object) str4);
                                    }
                                    param62.put((String) "url", (Object) afterUrl);
                                    final JSONObject param72 = new JSONObject();
                                    param72.put((String) "appName", (Object) name);
                                    param72.put((String) H5AppHandler.sAppIcon, (Object) iconUrl);
                                    param72.put((String) "appType", (Object) "小程序");
                                    param62.put((String) "otherParams", (Object) param72);
                                    param72.put((String) "extraParams", (Object) H5Utils.getJSONObject(jSONObject, "extraParams", new JSONObject()));
                                    shareParams.put((String) "param", (Object) param62);
                                    if (TextUtils.isEmpty(str7)) {
                                        h5Event2.setAction(H5Param.SNAPSHOT);
                                        JSONObject param82 = new JSONObject();
                                        param82.put((String) "range", (Object) "viewport");
                                        param82.put((String) "dataType", (Object) H5CompressImagePlugin.DATA_TYPE_FILE_URL);
                                        param82.put((String) "saveToGallery", (Object) Boolean.valueOf(false));
                                        param82.put((String) "hasMapTitleBar", (Object) Boolean.valueOf(false));
                                        param82.put((String) "quality", (Object) Integer.valueOf(50));
                                        h5Event2.setParam(param82);
                                        H5Service h5Service3 = h5Service;
                                        H5Event h5Event3 = h5Event2;
                                        final JSONObject jSONObject4 = shareParams;
                                        AnonymousClass4 r04 = new H5BaseBridgeContext() {
                                            public boolean sendBack(JSONObject param, boolean keep) {
                                                String fileURL = null;
                                                if (param != null) {
                                                    fileURL = param.getString(H5CompressImagePlugin.DATA_TYPE_FILE_URL);
                                                }
                                                if (TextUtils.isEmpty(fileURL)) {
                                                    return TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject4, h5BridgeContext);
                                                }
                                                if (TinyAppService.get().getMixActionService() == null || !TinyAppService.get().getMixActionService().isUseRpcMergeForQrCodeShare()) {
                                                    param72.put((String) "useMergeService", (Object) Boolean.valueOf(false));
                                                    JSONObject param9 = new JSONObject();
                                                    param9.put((String) "data", (Object) fileURL);
                                                    param9.put((String) "dataType", (Object) H5CompressImagePlugin.DATA_TYPE_FILE_URL);
                                                    param9.put((String) MultimediaImageProcessor.COMPOSITE_INT_KEY_COMPRESS_LEVEL, (Object) Integer.valueOf(3));
                                                    param9.put((String) com.alipay.mobile.beehive.audio.Constants.KEY_AUDIO_BUSINESS_ID, (Object) "multiMedia");
                                                    param9.put((String) "publicDomain", (Object) Boolean.valueOf(true));
                                                    h5Event2.setAction("uploadImage");
                                                    h5Event2.setParam(param9);
                                                    return h5Service.sendEvent(h5Event2, new H5BaseBridgeContext() {
                                                        public boolean sendBack(JSONObject param, boolean keep) {
                                                            String publicUrl = null;
                                                            if (param != null) {
                                                                publicUrl = param.getString("publicUrl");
                                                            }
                                                            if (!TextUtils.isEmpty(publicUrl)) {
                                                                param72.put((String) "bgImgUrl", (Object) publicUrl);
                                                            }
                                                            return TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject4, h5BridgeContext);
                                                        }
                                                    });
                                                }
                                                param72.put((String) "useMergeService", (Object) Boolean.valueOf(true));
                                                param72.put((String) "bgImgUrl", (Object) H5ResourceHandlerUtil.localIdToUrl(TinyappUtils.encodeToLocalId(fileURL), "image"));
                                                return TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, jSONObject4, h5BridgeContext);
                                            }
                                        };
                                        return h5Service3.sendEvent(h5Event3, r04);
                                    }
                                    param72.put((String) "bgImgUrl", (Object) str7);
                                    return TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, shareParams, h5BridgeContext);
                                } else {
                                    return TinyAppSharePlugin.this.shareToChannel(h5Service, h5Event2, shareParams, h5BridgeContext);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean shareToChannel(H5Service h5Service, H5Event h5Event, JSONObject shareParams, final H5BridgeContext h5BridgeContext) {
        H5Log.d(TAG, "shareToChannel..." + shareParams.toString());
        h5Event.setAction("shareToChannel");
        h5Event.setParam(shareParams);
        return h5Service.sendEvent(h5Event, new H5BaseBridgeContext() {
            public boolean sendBack(JSONObject param, boolean keep) {
                if (h5BridgeContext != null) {
                    return h5BridgeContext.sendBridgeResult(param);
                }
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public String encodeURIComponent(String uri) {
        try {
            return URLEncoder.encode(uri, "UTF-8");
        } catch (Throwable e) {
            H5Log.e(TAG, "shareTinyAppMsg...e=" + e);
            return uri;
        }
    }
}
