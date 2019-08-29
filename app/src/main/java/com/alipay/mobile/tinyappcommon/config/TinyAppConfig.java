package com.alipay.mobile.tinyappcommon.config;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.base.config.ConfigService.ConfigChangeListener;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TinyAppConfig implements ConfigChangeListener {
    private static final String a = TinyAppConfig.class.getSimpleName();
    private JSONObject A;
    private JSONArray B;
    private JSONObject C;
    private boolean D;
    private boolean E;
    private boolean F;
    private boolean G;
    private boolean H;
    private boolean I;
    private List<String> J;
    private List<String> K;
    private List<String> L;
    private boolean M;
    private boolean N;
    private List<String> O;
    private boolean P;
    private boolean Q;
    private boolean R;
    private List<String> S;
    private List<String> T;
    private List<String> U;
    private List<String> V;
    private List<String> W;
    private List<String> X;
    private boolean Y;
    private boolean Z;
    private List<String> aa;
    private List<String> ab;
    private List<String> ac;
    private List<String> ad;
    private boolean ae;
    private boolean af;
    private List<String> ag;
    private boolean ah;
    private JSONArray b;
    private JSONArray c;
    private Set<String> d;
    private Set<String> e;
    private Set<String> f;
    private int g;
    private JSONArray h;
    private Map<String, JSONObject> i;
    private boolean j;
    private JSONObject k;
    private boolean l;
    private boolean m;
    private int n;
    private List<String> o;
    private JSONObject p;
    private boolean q;
    private Set<String> r;
    private Set<String> s;
    private JSONArray t;
    private JSONObject u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y;
    private boolean z;

    private static class TinyAppConfigInner {
        public static TinyAppConfig a = new TinyAppConfig(0);

        private TinyAppConfigInner() {
        }
    }

    /* synthetic */ TinyAppConfig(byte b2) {
        this();
    }

    private TinyAppConfig() {
        this.b = new JSONArray();
        this.c = new JSONArray();
        this.g = 2000;
        this.h = new JSONArray();
        this.i = new HashMap();
        this.j = true;
        this.n = 1;
        this.o = new ArrayList();
        this.q = true;
        this.v = true;
        this.w = true;
        this.x = true;
        this.y = false;
        this.z = false;
        this.A = new JSONObject();
        this.B = new JSONArray();
        this.C = new JSONObject();
        this.D = false;
        this.E = true;
        this.F = true;
        this.G = false;
        this.H = true;
        this.I = false;
        this.J = new ArrayList();
        this.K = new ArrayList();
        this.L = new ArrayList();
        this.M = true;
        this.N = true;
        this.O = new ArrayList();
        this.P = true;
        this.Q = true;
        this.R = true;
        this.S = new ArrayList();
        this.T = new ArrayList();
        this.U = new ArrayList();
        this.V = new ArrayList();
        this.W = new ArrayList();
        this.X = new ArrayList();
        this.Y = true;
        this.Z = false;
        this.aa = new ArrayList();
        this.ab = new ArrayList();
        this.ac = new ArrayList();
        this.ad = new ArrayList();
        this.ae = false;
        this.af = false;
        this.ag = new ArrayList();
        this.ah = true;
        ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
        H5Log.d(a, "configService = " + configService);
        if (configService != null) {
            D(configService.getConfig("ta_cfg"));
            z(configService.getConfig("h5_white_jsapiList"));
            B(configService.getConfig("h5_enableTinyIgnorePermission"));
            C(configService.getConfig("use_native_share_cfg"));
            l(configService.getConfig("ta_scene_cfg"));
            m(configService.getConfig("ta_scene_shutdown"));
            p(configService.getConfig("ta_rpc_api_cfg"));
            q(configService.getConfig("ta_appx_rpc_whitelist_cfg"));
            u(configService.getConfig("ta_use_new_debug_server"));
            v(configService.getConfig("ta_useTinyAppManagerProcess"));
            w(configService.getConfig("ta_h5TransferTiny"));
            x(configService.getConfig("ta_setMinAppxBlacklist"));
            y(configService.getConfig("ta_webviewOpenAppIdList"));
            A(configService.getConfig("ta_navigate_alipay_pages"));
            N(configService.getConfig("ta_hide_about_item_blacklist"));
            t(configService.getConfig("remote_debug_mode"));
            r(configService.getConfig("ta_qrcodeshare_use_rpcservice"));
            s(configService.getConfig("ta_navigate_app_debug"));
            k(configService.getConfig("ta_http_domain_blacklist"));
            h(configService.getConfig("ta_webview_sp"));
            j(configService.getConfig("ta_maxWorkerCount"));
            n(configService.getConfig("ta_useSysWebView"));
            o(configService.getConfig("ta_enableModuleApp"));
            i(configService.getConfig("ta_embed_webview_server_jsapi_intercept_switch"));
            S(configService.getConfig("ta_launch_app_jsapi_blacklist"));
            Q(configService.getConfig("ta_startPreload"));
            R(configService.getConfig("ta_closeAppPair"));
            g(configService.getConfig("ta_taobao_app_info"));
            c(configService.getConfig("ta_cookie_part_wl"));
            d(configService.getConfig("ta_fallbackApp"));
            T(configService.getConfig("ta_add_favorite_alert_whitelist"));
            U(configService.getConfig("ta_useTlsWhitelist"));
            V(configService.getConfig("ta_close_webviewSchemaWhiteList"));
            f(configService.getConfig("ta_useWholePkg"));
            e(configService.getConfig("ta_renderJsapiBList"));
            b(configService.getConfig("ta_injectChInfo"));
            W(configService.getConfig("minSdkVersion_switch"));
            X(configService.getConfig("ta_recent_miniapp_blacklist"));
            Y(configService.getConfig("ta_embedwebview_white_domain_list"));
            a(configService.getConfig("ta_preferConfigWaitTime"));
        }
    }

    private void a(String value) {
        if (!TextUtils.isEmpty(value)) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(value);
                if (jsonObject != null) {
                    this.g = (int) (jsonObject.getFloatValue("time") * 1000.0f);
                }
            } catch (Throwable e2) {
                H5Log.e(a, "initTemplateAppWaitTime..e:" + e2);
            }
        }
    }

    public static TinyAppConfig getInstance() {
        return TinyAppConfigInner.a;
    }

    private void b(String taInjectChInfo) {
        if (!TextUtils.isEmpty(taInjectChInfo)) {
            try {
                this.c = JSON.parseArray(taInjectChInfo);
            } catch (Exception e2) {
                H5Log.e(a, (Throwable) e2);
            }
        }
    }

    private void c(String taCookiePartWhiteList) {
        if (!TextUtils.isEmpty(taCookiePartWhiteList)) {
            try {
                this.h = JSON.parseArray(taCookiePartWhiteList);
            } catch (Exception e2) {
                H5Log.e(a, (Throwable) e2);
            }
        }
    }

    private void d(String value) {
        if (!TextUtils.isEmpty(value)) {
            JSONArray jsonArray = H5Utils.parseArray(value);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                if (this.e == null) {
                    this.e = Collections.synchronizedSet(new HashSet());
                } else {
                    this.e.clear();
                }
                for (int i2 = 0; i2 < jsonArray.size(); i2++) {
                    this.e.add(jsonArray.getString(i2));
                }
            }
        }
    }

    private void e(String value) {
        if (!TextUtils.isEmpty(value)) {
            JSONArray jsonArray = H5Utils.parseArray(value);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                if (this.f == null) {
                    this.f = Collections.synchronizedSet(new HashSet());
                } else {
                    this.f.clear();
                }
                for (int i2 = 0; i2 < jsonArray.size(); i2++) {
                    this.f.add(jsonArray.getString(i2));
                }
            }
        }
    }

    private void f(String value) {
        if (!TextUtils.isEmpty(value)) {
            JSONArray jsonArray = H5Utils.parseArray(value);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                if (this.d == null) {
                    this.d = Collections.synchronizedSet(new HashSet());
                } else {
                    this.d.clear();
                }
                for (int i2 = 0; i2 < jsonArray.size(); i2++) {
                    this.d.add(jsonArray.getString(i2));
                }
            }
        }
    }

    private void g(String taTaoBaoAppInfo) {
        if (!TextUtils.isEmpty(taTaoBaoAppInfo)) {
            try {
                Map taoBaoAppInfoMap = new HashMap();
                for (Entry entry : JSON.parseObject(taTaoBaoAppInfo).entrySet()) {
                    Object appInfo = entry.getValue();
                    if (appInfo instanceof JSONObject) {
                        taoBaoAppInfoMap.put((String) entry.getKey(), (JSONObject) appInfo);
                    }
                }
                this.i = Collections.unmodifiableMap(taoBaoAppInfoMap);
            } catch (Exception e2) {
                H5Log.e(a, (Throwable) e2);
            }
        }
    }

    private void h(String taWebViewShowProgress) {
        if (!TextUtils.isEmpty(taWebViewShowProgress)) {
            this.l = "1".equals(taWebViewShowProgress);
        }
    }

    private void i(String interceptSwitch) {
        if (!TextUtils.isEmpty(interceptSwitch)) {
            this.m = "1".equals(interceptSwitch);
        }
    }

    private void j(String value) {
        if (!TextUtils.isEmpty(value)) {
            try {
                this.n = Integer.parseInt(value);
            } catch (Throwable e2) {
                H5Log.e(a, "initWorkerCount...e:" + e2);
            }
        }
    }

    private void k(String taHttpDomainBlackList) {
        if (!TextUtils.isEmpty(taHttpDomainBlackList)) {
            try {
                List list = new ArrayList();
                JSONArray jsonArray = JSON.parseArray(taHttpDomainBlackList);
                int size = jsonArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    list.add(jsonArray.getString(i2));
                }
                this.o = list;
            } catch (Exception e2) {
                H5Log.e(a, "init http domain black list error", e2);
            }
        }
    }

    private void l(String sceneConfig) {
        if (!TextUtils.isEmpty(sceneConfig)) {
            try {
                this.C = JSON.parseObject(sceneConfig);
            } catch (Exception e2) {
                H5Log.e(a, "init scene config error", e2);
            }
        }
    }

    private void m(String sceneTransformShutdown) {
        if (!TextUtils.isEmpty(sceneTransformShutdown)) {
            this.D = "1".equals(sceneTransformShutdown);
        }
    }

    private void n(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.q = "0".equals(value);
        }
    }

    public boolean isEnableTemplateApp() {
        return this.ah;
    }

    private void o(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.ah = !"0".equals(value);
        }
    }

    public int getTemplateAppWaitTime() {
        return this.g;
    }

    private void p(String rpcApiConfig) {
        if (!TextUtils.isEmpty(rpcApiConfig)) {
            try {
                this.A = JSON.parseObject(rpcApiConfig);
            } catch (Exception e2) {
                H5Log.e(a, "init rpc api config error", e2);
            }
        }
    }

    private void q(String appxRpcApiConfig) {
        if (!TextUtils.isEmpty(appxRpcApiConfig)) {
            try {
                this.B = JSON.parseArray(appxRpcApiConfig);
            } catch (Exception e2) {
                H5Log.e(a, "init rpc api config error", e2);
            }
        }
    }

    private void r(String useRpcMerge) {
        if (!TextUtils.isEmpty(useRpcMerge)) {
            this.y = "1".equals(useRpcMerge);
        }
    }

    private void s(String navigateAppDebug) {
        if (!TextUtils.isEmpty(navigateAppDebug)) {
            this.z = "1".equals(navigateAppDebug);
        }
    }

    private void t(String mode) {
        if (!TextUtils.isEmpty(mode)) {
            this.x = "1".equals(mode);
        }
    }

    private void u(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.v = "1".equals(value);
        }
    }

    private void v(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.w = "1".equals(value);
        }
    }

    private void w(String value) {
        if (!TextUtils.isEmpty(value)) {
            JSONArray jsonArray = H5Utils.parseArray(value);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                if (this.s == null) {
                    this.s = Collections.synchronizedSet(new HashSet());
                } else {
                    this.s.clear();
                }
                for (int i2 = 0; i2 < jsonArray.size(); i2++) {
                    this.s.add(jsonArray.getString(i2));
                }
            }
        }
    }

    private void x(String value) {
        if (!TextUtils.isEmpty(value)) {
            JSONArray jsonArray = H5Utils.parseArray(value);
            if (jsonArray != null && !jsonArray.isEmpty()) {
                if (this.r == null) {
                    this.r = Collections.synchronizedSet(new HashSet());
                } else {
                    this.r.clear();
                }
                for (int i2 = 0; i2 < jsonArray.size(); i2++) {
                    this.r.add(jsonArray.getString(i2));
                }
            }
        }
    }

    private void y(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.p = H5Utils.parseObject(value);
        }
    }

    private void z(String whitelistValue) {
        H5Log.d(a, "initJsapiWhitelist...whitelistValue=" + whitelistValue);
        if (!TextUtils.isEmpty(whitelistValue)) {
            try {
                this.t = H5Utils.parseArray(whitelistValue);
            } catch (Throwable e2) {
                H5Log.d(a, "initJsapiWhitelist...e=" + e2);
            }
        }
    }

    private void A(String rules) {
        H5Log.d(a, "initNavigateAlipayPages...rules=" + rules);
        if (!TextUtils.isEmpty(rules)) {
            try {
                this.u = H5Utils.parseObject(rules);
            } catch (Throwable e2) {
                H5Log.d(a, "initNavigateAlipayPages...e=" + e2);
            }
        }
    }

    private void B(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.F = !BQCCameraParam.VALUE_NO.equals(value);
        }
    }

    private void C(String value) {
        if (!TextUtils.isEmpty(value)) {
            H5Log.d(a, "initShareNativeConfigKey...value=" + value);
            this.E = "1".equals(value);
        }
    }

    private void D(String configValue) {
        H5Log.d(a, "init...configValue=" + configValue);
        if (!TextUtils.isEmpty(configValue)) {
            try {
                JSONObject value = JSON.parseObject(configValue);
                E(value.getString("tpc"));
                F(value.getString("ans"));
                G(value.getString("antns"));
                H(value.getString("ntmpw"));
                a(value.getJSONArray("sil"));
                b(value.getJSONArray("swal"));
                I(value.getString("sw"));
                J(value.getString("cib"));
                c(value.getJSONArray("awl"));
                K(value.getString("saa"));
                L(value.getString("cuap"));
                M(value.getString("hus"));
                d(value.getJSONArray("husb"));
                e(value.getJSONArray("smb"));
                f(value.getJSONArray("wjw"));
                O(value.getString("sltct"));
                h(value.getJSONArray("fmb"));
                i(value.getJSONArray("amb"));
                P(value.getString("slsp"));
                g(value.getJSONArray("ola"));
            } catch (Exception e2) {
                H5Log.e(a, "init...e=" + e2);
            }
        }
    }

    private void E(String value) {
        if (TextUtils.isEmpty(value)) {
            H5Log.d(a, "initTradePaySwitch..value is empty");
        } else {
            this.G = "1".equals(value);
        }
    }

    private void F(String value) {
        if (TextUtils.isEmpty(value)) {
            H5Log.d(a, "initAllowedNonSubjectLifestyle..value is empty");
        } else {
            this.H = "1".equals(value);
        }
    }

    private void G(String value) {
        if (TextUtils.isEmpty(value)) {
            H5Log.d(a, "initAllowedNaviToNonSubjectMiniProgram..value is empty");
        } else {
            this.I = "1".equals(value);
        }
    }

    private void H(String value) {
        try {
            H5Log.d(a, "initNaviToMiniProgramWhitelist..value=" + value);
            if (TextUtils.isEmpty(value)) {
                H5Log.d(a, "initNaviToMiniProgramWhitelist..value is empty");
                return;
            }
            String[] whitelist = value.split(",");
            if (whitelist.length <= 0) {
                H5Log.d(a, "initNaviToMiniProgramWhitelist..list is empty");
            } else {
                this.J = Arrays.asList(whitelist);
            }
        } catch (Throwable e2) {
            H5Log.e(a, "initNaviToMiniProgramWhitelist..e=" + e2);
        }
    }

    private void a(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initSupportedInternalApiList..valueArray is empty");
            return;
        }
        this.K.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.K.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initSupportedInternalApiList...e=" + e2);
            }
        }
    }

    private void b(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initSupportedWebviewApiList..valueArray is empty");
            return;
        }
        this.L.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.L.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initSupportedWebviewApiList...e=" + e2);
            }
        }
    }

    private void I(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.M = !"0".equals(value);
        }
    }

    private void J(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.N = !"0".equals(value);
        }
    }

    private void c(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initPermissionDialogWhitelist..value is empty");
            return;
        }
        this.O.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.O.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initPermissionDialogWhitelist...e=" + e2);
            }
        }
    }

    private void K(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.P = !"0".equals(value);
        }
    }

    private void L(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.Q = !"0".equals(value);
        }
    }

    private void M(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.R = "1".equals(value);
        }
    }

    private void d(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initHttpsUseSpdyBlacklist..value is empty");
            return;
        }
        this.S.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.S.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initHttpsUseSpdyBlacklist...e=" + e2);
            }
        }
    }

    private void e(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initShareMenuBlacklist..value is empty");
            return;
        }
        this.T.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.T.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initShareMenuBlacklist...e=" + e2);
            }
        }
    }

    private void N(String blackList) {
        if (TextUtils.isEmpty(blackList)) {
            H5Log.d(a, "initAboutMenuBlacklist..config is empty");
            return;
        }
        JSONArray valueArray = H5Utils.parseArray(blackList);
        if (valueArray == null) {
            H5Log.d(a, "initAboutMenuBlacklist..value is empty");
            return;
        }
        this.U.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.U.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initAboutMenuBlacklist...e=" + e2);
            }
        }
    }

    private void f(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initWebviewJsapiWhitelist..value is empty");
            return;
        }
        this.X.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.X.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initWebviewJsapiWhitelist...e=" + e2);
            }
        }
    }

    private void O(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.Y = !"0".equals(value);
        }
    }

    private void P(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.Z = "1".equals(value);
        }
    }

    private void Q(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.j = !"0".equals(value);
        }
    }

    private void R(String value) {
        if (value != null) {
            try {
                this.k = JSONObject.parseObject(value);
            } catch (Throwable e2) {
                H5Log.e(a, "initCloseAppPairs...e=" + e2);
            }
        }
    }

    private void g(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initOldLaunchAnimatorList..value is empty");
            return;
        }
        this.aa.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.aa.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initOldLaunchAnimatorList...e=" + e2);
            }
        }
    }

    private void h(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initFavoriteMenuBlacklist..value is empty");
            return;
        }
        this.V.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.V.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initFavoriteMenuBlacklist...e=" + e2);
            }
        }
    }

    private void i(JSONArray valueArray) {
        if (valueArray == null) {
            H5Log.d(a, "initAddToDesktopMenuBlacklist..value is empty");
            return;
        }
        this.W.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.W.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initAddToDesktopMenuBlacklist...e=" + e2);
            }
        }
    }

    private void S(String blackList) {
        if (TextUtils.isEmpty(blackList)) {
            H5Log.d(a, "initLaunchAppJspiBlacklist..config is empty");
            return;
        }
        JSONArray valueArray = H5Utils.parseArray(blackList);
        if (valueArray == null) {
            H5Log.d(a, "initLaunchAppJspiBlacklist..value is empty");
            return;
        }
        this.ab.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.ab.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initLaunchAppJspiBlacklist...e=" + e2);
            }
        }
    }

    private void T(String whiteList) {
        if (TextUtils.isEmpty(whiteList)) {
            H5Log.d(a, "initAddFavoriteAlertWhiteList..config is empty");
            return;
        }
        JSONArray valueArray = H5Utils.parseArray(whiteList);
        if (valueArray == null) {
            H5Log.d(a, "initAddFavoriteAlertWhiteList..value is empty");
            return;
        }
        this.ac.clear();
        int length = valueArray.size();
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.ac.add(valueArray.getString(i2));
            } catch (Throwable e2) {
                H5Log.e(a, "initLaunchAppJspiBlacklist...e=" + e2);
            }
        }
    }

    private void U(String whiteList) {
        try {
            this.ad.clear();
        } catch (Throwable e2) {
            H5Log.e(a, "initTlsWhiteList...e=" + e2);
        }
        if (TextUtils.isEmpty(whiteList)) {
            H5Log.d(a, "initTlsWhiteList..config is empty");
            try {
                this.ad.add("all");
            } catch (Throwable e3) {
                H5Log.e(a, "initTlsWhiteList...e=" + e3);
            }
        } else {
            JSONArray valueArray = H5Utils.parseArray(whiteList);
            if (valueArray == null || valueArray.isEmpty()) {
                H5Log.d(a, "initTlsWhiteList..value is empty");
                try {
                    this.ad.add("all");
                } catch (Throwable e4) {
                    H5Log.e(a, "initTlsWhiteList...e=" + e4);
                }
            } else {
                int length = valueArray.size();
                for (int i2 = 0; i2 < length; i2++) {
                    try {
                        this.ad.add(valueArray.getString(i2));
                    } catch (Throwable e5) {
                        H5Log.e(a, "initTlsWhiteList...e=" + e5);
                    }
                }
            }
        }
    }

    private void V(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.ae = "1".equals(value);
        }
    }

    private void W(String value) {
        if (!TextUtils.isEmpty(value)) {
            this.af = "YES".equalsIgnoreCase(value.trim());
        }
    }

    private void X(String value) {
        if (!TextUtils.isEmpty(value)) {
            JSONArray array = H5Utils.parseArray(value);
            if (array != null && array.size() > 0) {
                int size = array.size();
                for (int i2 = 0; i2 < size; i2++) {
                    try {
                        this.ag.add(array.getString(i2));
                    } catch (Throwable e2) {
                        H5Log.e(a, "initRecentMiniAppBlackList...e=" + e2);
                    }
                }
            }
        }
    }

    private void Y(String taWebViewJSAPIDomainWhiteList) {
        if (!TextUtils.isEmpty(taWebViewJSAPIDomainWhiteList)) {
            try {
                this.b = H5Utils.parseArray(taWebViewJSAPIDomainWhiteList);
            } catch (Exception e2) {
                H5Log.e(a, (Throwable) e2);
            }
        }
    }

    public boolean tradePayCheck() {
        return this.G;
    }

    public boolean allowedNonSubjectLifestyle() {
        return this.H;
    }

    public boolean allowedNaviToNonSubjectMiniProgram() {
        return this.I;
    }

    public List<String> getNaviToMiniProgramWhitelist() {
        return this.J;
    }

    public List<String> getSupportedInternalApiList() {
        return this.K;
    }

    public List<String> getSupportedWebviewApiList() {
        return this.L;
    }

    public boolean isSupportedWebview() {
        return this.M;
    }

    public boolean canInterceptBackEvent() {
        return this.N;
    }

    public List<String> getPermissionDialogWhitelist() {
        return this.O;
    }

    public boolean getPermissionDialogSwitch() {
        return this.P;
    }

    public boolean getAssistantPanelSwitch() {
        return this.Q;
    }

    public boolean shouldHttpsUseSpdy() {
        return this.R;
    }

    public List<String> getHttpsUseSpdyBlacklist() {
        return this.S;
    }

    public List<String> getShareMenuBlacklist() {
        return this.T;
    }

    public List<String> getAboutMenuBlackList() {
        return this.U;
    }

    public List<String> getWebviewJsapiWhitelist() {
        return this.X;
    }

    public boolean shouldLoginTokenUseClearText() {
        return this.Y;
    }

    public List<String> getFavoriteMenuBlacklist() {
        return this.V;
    }

    public List<String> getAddToDesktopMenuBlacklist() {
        return this.W;
    }

    public boolean shouldLongClickShowPanel() {
        return this.Z;
    }

    public JSONArray getWhiteJsApiJsonArray() {
        return this.t;
    }

    public JSONObject getNavigateToAlipayPageRules() {
        return this.u;
    }

    public boolean enableTinyIgnorePermission() {
        return this.F;
    }

    public boolean isUseSysWebView() {
        return this.q;
    }

    public List<String> getOldLaunchAnimatorList() {
        return this.aa;
    }

    public boolean isUseNativeShareSwitch() {
        return this.E;
    }

    public JSONObject getSceneConfig() {
        return this.C;
    }

    public boolean getSceneTransformShutdown() {
        return this.D;
    }

    public JSONObject getRpcApiConfig() {
        return this.A;
    }

    public JSONArray getAppxRpcApiConfig() {
        return this.B;
    }

    public boolean isUseRpcMergeForQrCodeShare() {
        return this.y;
    }

    public boolean isNavigateAppDebug() {
        return this.z;
    }

    public boolean isUseNewDebugServer() {
        return this.v;
    }

    public boolean isUseTinyAppManagerProcess() {
        return this.w;
    }

    public Set<String> getH5TransferTinyArray() {
        return this.s;
    }

    public Set<String> getSetMinAppxBlacklist() {
        return this.r;
    }

    public JSONObject getWebviewOpenAppIdList() {
        return this.p;
    }

    public boolean supportRemoteDebugMode() {
        return this.x;
    }

    public List<String> getHttpDomainBlacklist() {
        return this.o;
    }

    public boolean isWebViewShowProgress() {
        return this.l;
    }

    public boolean isEmbedWebViewServerInterceptOpen() {
        return this.m;
    }

    public int getMaxWorkerCount() {
        return this.n;
    }

    public List<String> getLaunchAppJsapiBlacklist() {
        return this.ab;
    }

    public boolean canStartPreload() {
        return this.j;
    }

    public JSONObject getCloseAppPairObject() {
        return this.k;
    }

    public boolean isCloseWebviewSchema() {
        return this.ae;
    }

    public JSONObject getTaoBaoAppInfo(String appId) {
        JSONObject defValue = new JSONObject();
        if (this.i == null || TextUtils.isEmpty(appId)) {
            return defValue;
        }
        JSONObject appInfo = this.i.get(appId);
        if (appInfo != null) {
            return appInfo;
        }
        return defValue;
    }

    public JSONArray getCookiePartWhiteList() {
        return this.h;
    }

    public Set<String> getUcFailFallbackAppBlacklist() {
        return this.e;
    }

    public List<String> getAddFavoriteAlertWhiteList() {
        return this.ac;
    }

    public List<String> getUseTlsWhiteList() {
        return this.ad;
    }

    public Set<String> getUseWholePackageAppIdSet() {
        return this.d;
    }

    public Set<String> getRenderJsApiBlacklist() {
        return this.f;
    }

    public List<String> getRecentMiniAppBlackList() {
        return this.ag;
    }

    public JSONArray getInjectChInfoList() {
        return this.c;
    }

    public JSONArray getWebViewJSAPIDomainWhiteList() {
        return this.b;
    }

    public boolean getMinSdkVersionCheck() {
        return this.af;
    }

    public List<String> getKeys() {
        ArrayList keyList = new ArrayList();
        keyList.add("ta_cfg");
        keyList.add("h5_white_jsapiList");
        keyList.add("h5_enableTinyIgnorePermission");
        keyList.add("use_native_share_cfg");
        keyList.add("ta_scene_cfg");
        keyList.add("ta_scene_shutdown");
        keyList.add("ta_rpc_api_cfg");
        keyList.add("ta_appx_rpc_whitelist_cfg");
        keyList.add("ta_use_new_debug_server");
        keyList.add("ta_useTinyAppManagerProcess");
        keyList.add("ta_h5TransferTiny");
        keyList.add("ta_setMinAppxBlacklist");
        keyList.add("ta_webviewOpenAppIdList");
        keyList.add("ta_navigate_alipay_pages");
        keyList.add("ta_hide_about_item_blacklist");
        keyList.add("remote_debug_mode");
        keyList.add("ta_qrcodeshare_use_rpcservice");
        keyList.add("ta_navigate_app_debug");
        keyList.add("ta_http_domain_blacklist");
        keyList.add("ta_useSysWebView");
        keyList.add("ta_webview_sp");
        keyList.add("ta_embed_webview_server_jsapi_intercept_switch");
        keyList.add("ta_maxWorkerCount");
        keyList.add("ta_launch_app_jsapi_blacklist");
        keyList.add("ta_startPreload");
        keyList.add("ta_closeAppPair");
        keyList.add("ta_taobao_app_info");
        keyList.add("ta_cookie_part_wl");
        keyList.add("ta_fallbackApp");
        keyList.add("ta_add_favorite_alert_whitelist");
        keyList.add("ta_useTlsWhitelist");
        keyList.add("ta_close_webviewSchemaWhiteList");
        keyList.add("ta_useWholePkg");
        keyList.add("ta_renderJsapiBList");
        keyList.add("ta_injectChInfo");
        keyList.add("ta_recent_miniapp_blacklist");
        keyList.add("ta_embedwebview_white_domain_list");
        keyList.add("minSdkVersion_switch");
        keyList.add("ta_preferConfigWaitTime");
        keyList.add("ta_enableModuleApp");
        return keyList;
    }

    public void onConfigChange(String key, String value) {
        if ("ta_cfg".equals(key)) {
            D(value);
        } else if ("h5_white_jsapiList".equals(key)) {
            z(value);
        } else if ("h5_enableTinyIgnorePermission".equals(key)) {
            B(value);
        } else if ("use_native_share_cfg".equals(key)) {
            C(value);
        } else if ("ta_scene_cfg".equals(key)) {
            l(value);
        } else if ("ta_scene_shutdown".equals(key)) {
            m(value);
        } else if ("ta_rpc_api_cfg".equals(key)) {
            p(value);
        } else if ("ta_appx_rpc_whitelist_cfg".equals(key)) {
            q(key);
        } else if ("ta_use_new_debug_server".equals(key)) {
            u(value);
        } else if ("ta_useTinyAppManagerProcess".equals(key)) {
            v(value);
        } else if ("ta_h5TransferTiny".equals(key)) {
            w(value);
        } else if ("ta_setMinAppxBlacklist".equals(key)) {
            x(value);
        } else if ("ta_webviewOpenAppIdList".equals(key)) {
            y(value);
        } else if ("ta_navigate_alipay_pages".equals(key)) {
            A(value);
        } else if ("ta_hide_about_item_blacklist".equals(key)) {
            N(value);
        } else if ("remote_debug_mode".equals(key)) {
            t(value);
        } else if ("ta_qrcodeshare_use_rpcservice".equals(key)) {
            r(value);
        } else if ("ta_navigate_app_debug".equals(key)) {
            s(value);
        } else if ("ta_http_domain_blacklist".equals(key)) {
            k(value);
        } else if ("ta_useSysWebView".equals(key)) {
            n(value);
        } else if ("ta_webview_sp".equals(key)) {
            h(value);
        } else if ("ta_embed_webview_server_jsapi_intercept_switch".equals(key)) {
            i(value);
        } else if ("ta_maxWorkerCount".equals(key)) {
            j(value);
        } else if ("ta_launch_app_jsapi_blacklist".equals(key)) {
            S(value);
        } else if ("ta_taobao_app_info".equals(key)) {
            g(value);
        } else if ("ta_startPreload".equals(key)) {
            Q(value);
        } else if ("ta_closeAppPair".equals(key)) {
            R(value);
        } else if ("ta_cookie_part_wl".equals(key)) {
            c(value);
        } else if ("ta_fallbackApp".equals(key)) {
            d(value);
        } else if ("ta_add_favorite_alert_whitelist".equals(key)) {
            T(value);
        } else if ("ta_useTlsWhitelist".equals(key)) {
            U(value);
        } else if ("ta_close_webviewSchemaWhiteList".equals(key)) {
            V(value);
        } else if ("ta_useWholePkg".equals(key)) {
            f(value);
        } else if ("ta_renderJsapiBList".equals(key)) {
            e(value);
        } else if ("ta_injectChInfo".equalsIgnoreCase(key)) {
            b(value);
        } else if ("ta_recent_miniapp_blacklist".equals(key)) {
            X(value);
        } else if ("ta_embedwebview_white_domain_list".equals(key)) {
            Y(value);
        } else if ("minSdkVersion_switch".equals(key)) {
            W(value);
        } else if ("ta_preferConfigWaitTime".equals(key)) {
            a(value);
        } else if ("ta_enableModuleApp".equals(key)) {
            o(value);
        }
    }
}
