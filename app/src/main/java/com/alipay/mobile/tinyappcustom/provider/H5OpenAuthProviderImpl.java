package com.alipay.mobile.tinyappcustom.provider;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigs;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5OpenAuthProvider;
import com.alipay.mobile.nebula.provider.H5OpenAuthProvider.OnGetAuthListener;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcustom.h5plugin.MiniProgramUtils;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.H5OpenAuthHelper;
import com.alipay.mobile.tinyappcustom.h5plugin.openauth.request.MapStringString;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5LinkMovementMethod;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5LinkMovementMethod.OnLinkClickListener;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5OpenAuthClickableSpan;
import com.alipay.mobile.tinyappcustom.h5plugin.ui.H5OpenAuthDialog;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H5OpenAuthProviderImpl implements H5OpenAuthProvider {
    private static final String TAG = "H5OpenAuthProvider";

    public void getAuthCode(Activity context, String appId, String bundleAppId, String url, List<String> scopeNicks, String isvAppId, Map<String, Object> extInfoMap, boolean showErrorTip, OnGetAuthListener listener) {
        if (context != null && !context.isFinishing()) {
            H5OpenAuthHelper helper = new H5OpenAuthHelper(context, listener);
            MapStringString extInfo = H5OpenAuthHelper.mapToMapStringString(extInfoMap);
            Map appExtInfoMap = new HashMap();
            appExtInfoMap.put("channel", H5NebulaAppConfigs.ASY_REQ_RATE_SHORT);
            appExtInfoMap.put("clientAppId", bundleAppId);
            helper.getAuthContentOrAutoAuth(appId, url, scopeNicks, isvAppId, extInfo, showErrorTip, H5OpenAuthHelper.mapToMapStringString(appExtInfoMap));
        }
    }

    public void getAuthCodeLocal(Activity activity, String appName, List<String> authText, List<String> protocol, List<String> protocolLink, OnGetAuthListener listener) {
        if ((protocol != null && protocolLink == null) || !(protocol == null || protocolLink == null || protocol.size() == protocolLink.size())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "error", (Object) Integer.valueOf(11));
            if (listener != null) {
                listener.onResult(jsonObject);
            }
        }
        if (activity != null && !activity.isFinishing()) {
            final H5OpenAuthDialog h5OpenAuthDialog = new H5OpenAuthDialog(activity);
            final OnGetAuthListener onGetAuthListener = listener;
            AnonymousClass1 r0 = new OnClickListener() {
                public void onClick(View v) {
                    h5OpenAuthDialog.cancel();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
                    if (onGetAuthListener != null) {
                        onGetAuthListener.onResult(jsonObject);
                    }
                }
            };
            h5OpenAuthDialog.setOnConfirmClickListener(r0);
            final OnGetAuthListener onGetAuthListener2 = listener;
            AnonymousClass2 r02 = new OnClickListener() {
                public void onClick(View v) {
                    h5OpenAuthDialog.cancel();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) "error", (Object) Integer.valueOf(11));
                    if (onGetAuthListener2 != null) {
                        onGetAuthListener2.onResult(jsonObject);
                    }
                }
            };
            h5OpenAuthDialog.setOnCloseClickListener(r02);
            h5OpenAuthDialog.getAuthContentTitle().setText(Html.fromHtml("授权 <b>appName</b> 获取以下信息为你服务".replace("appName", appName)));
            LinearLayout authContentContainer = h5OpenAuthDialog.getAuthContentContainer();
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            if (authText != null && !authText.isEmpty()) {
                for (String item : authText) {
                    LinearLayout itemContainer = new LinearLayout(activity);
                    itemContainer.setOrientation(0);
                    TextView dot = new TextView(activity);
                    dot.setText("• ");
                    dot.setTextSize(1, 15.0f);
                    dot.setTextColor(-6710887);
                    dot.setLayoutParams(layoutParams);
                    TextView itemView = new TextView(activity);
                    itemView.setText(item.trim());
                    itemView.setTextSize(1, 15.0f);
                    itemView.setTextColor(-6710887);
                    itemView.setPadding(0, 1, 0, 1);
                    itemView.setLineSpacing((float) H5DimensionUtil.dip2px(activity, 7.0f), 1.0f);
                    itemView.setLayoutParams(layoutParams);
                    itemContainer.addView(dot);
                    itemContainer.addView(itemView);
                    layoutParams.setMargins(0, 10, 0, 0);
                    itemContainer.setLayoutParams(layoutParams);
                    authContentContainer.addView(itemContainer);
                }
            }
            if (protocol != null && !protocol.isEmpty()) {
                H5LinkMovementMethod h5LinkMovementMethod = new H5LinkMovementMethod();
                AnonymousClass3 r03 = new OnLinkClickListener() {
                    public void onLinkClick(String url) {
                        H5Log.d(H5OpenAuthProviderImpl.TAG, "onLinkClick url is " + url);
                        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                        if (h5ConfigProvider == null || BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_use_new_scheme"))) {
                            Bundle startParams = new Bundle();
                            startParams.putString("url", url);
                            startParams.putString("startMultApp", "YES");
                            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, "20000067", startParams);
                            return;
                        }
                        MiniProgramUtils.goToSchemeService(H5UrlHelper.parseUrl("alipays://platformapi/startapp?appId=20000067&startMultApp=YES&url=" + H5UrlHelper.encode(url)));
                    }
                };
                h5LinkMovementMethod.setOnLinkClickListener(r03);
                TextView authProtocol = h5OpenAuthDialog.getAuthContentProtocol();
                authProtocol.setMovementMethod(h5LinkMovementMethod);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("同意");
                int size = protocol.size();
                int[] segmentStart = new int[size];
                int[] segmentEnd = new int[size];
                int index = 0;
                int cursor = 2;
                for (String name : protocol) {
                    stringBuilder.append(Token.SEPARATOR);
                    int cursor2 = cursor + 1;
                    segmentStart[index] = cursor2;
                    r0 = "《tagname》";
                    stringBuilder.append("《tagname》".replace("tagname", name));
                    cursor = name.length() + cursor2 + 1 + 1;
                    segmentEnd[index] = cursor;
                    index++;
                }
                SpannableString spannableString = new SpannableString(stringBuilder.toString());
                int index2 = 0;
                for (String link : protocolLink) {
                    H5OpenAuthClickableSpan h5OpenAuthClickableSpan = new H5OpenAuthClickableSpan(link);
                    spannableString.setSpan(h5OpenAuthClickableSpan, segmentStart[index2], segmentEnd[index2], 34);
                    index2++;
                }
                authProtocol.setText(spannableString);
            }
            try {
                h5OpenAuthDialog.show();
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        }
    }
}
