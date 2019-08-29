package com.alipay.mobile.nebulacore.appcenter.parse;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5RsaUtil;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import java.io.File;

public class H5PackageParserRn {
    public static final String TAG = "H5PackageParserRn";

    public static synchronized int parseRNPackage(String appId) {
        long time;
        int i;
        JSONObject joCert;
        boolean result;
        synchronized (H5PackageParserRn.class) {
            time = System.currentTimeMillis();
            H5AppProvider nebulaAppProvider = (H5AppProvider) Nebula.getProviderManager().getProvider(H5AppProvider.class.getName());
            if (nebulaAppProvider == null) {
                H5Log.e((String) TAG, (String) "nebulaAppProvider==null");
                i = 1;
            } else {
                String installPath = nebulaAppProvider.getInstallPath(appId, nebulaAppProvider.getVersion(appId));
                if (TextUtils.isEmpty(installPath)) {
                    i = 1;
                } else {
                    StringBuilder sb = new StringBuilder("file://");
                    String offlineHost = sb.append(installPath).toString();
                    if (!offlineHost.endsWith("/")) {
                        offlineHost = offlineHost + "/";
                    }
                    H5Log.d(TAG, "offlineHost " + offlineHost);
                    Uri offlineUri = H5UrlHelper.parseUrl(offlineHost);
                    if (offlineUri == null || TextUtils.isEmpty(offlineUri.getPath())) {
                        i = 1;
                    } else {
                        String offlinePath = offlineUri.getPath();
                        if (!H5FileUtil.exists(offlinePath)) {
                            H5Log.e((String) TAG, "offlinePath " + offlinePath + " not exists!");
                            i = 2;
                        } else {
                            String certPath = offlinePath + "/CERT.json";
                            String signPath = offlinePath + "/SIGN.json";
                            if (H5FileUtil.exists(certPath) || H5FileUtil.exists(signPath)) {
                                File file = new File(offlinePath);
                                File[] childrenFiles = file.listFiles();
                                if (childrenFiles == null || childrenFiles.length == 0) {
                                    H5Log.e((String) TAG, (String) "childrenFiles length == 0");
                                    i = 2;
                                } else {
                                    boolean useNewSignKey = H5FileUtil.exists(signPath);
                                    boolean signPathExist = useNewSignKey;
                                    if (!useNewSignKey || !TextUtils.isEmpty(H5PackageParser.TAR_PUBLIC_KEY)) {
                                        H5Log.d(TAG, "signPath is exist : " + signPathExist + ", H5PackageParser.TAR_PUBLIC_KEY : " + H5PackageParser.TAR_PUBLIC_KEY);
                                        useNewSignKey = false;
                                    }
                                    H5Log.d(TAG, "useNewSignKey : " + useNewSignKey);
                                    if (useNewSignKey) {
                                        joCert = H5Utils.parseObject(H5Utils.read(signPath));
                                    } else {
                                        joCert = H5Utils.parseObject(H5Utils.read(certPath));
                                    }
                                    if (joCert == null || joCert.isEmpty()) {
                                        H5Log.e((String) TAG, (String) "joCert is empty");
                                        i = 5;
                                    } else {
                                        try {
                                            int length = childrenFiles.length;
                                            int i2 = 0;
                                            while (true) {
                                                if (i2 >= length) {
                                                    break;
                                                }
                                                String signKey = childrenFiles[i2].getName();
                                                if (!TextUtils.isEmpty(signKey) && !TextUtils.equals(signKey, "CERT.json") && !TextUtils.equals(signKey, "SIGN.json") && !signKey.contains("ios")) {
                                                    String signValue = joCert.get(signKey).toString();
                                                    if (useNewSignKey) {
                                                        result = H5RsaUtil.verify(offlinePath + "/" + signKey, H5PackageParser.getPublicKey(true), signValue);
                                                    } else if (!TextUtils.isEmpty(H5PackageParser.TAR_PUBLIC_KEY)) {
                                                        result = H5RsaUtil.verify(offlinePath + "/" + signKey, H5PackageParser.TAR_PUBLIC_KEY, signValue);
                                                    } else {
                                                        result = H5RsaUtil.verify(offlinePath + "/" + signKey, H5PackageParser.getPublicKey(false), signValue);
                                                    }
                                                    H5Log.d(TAG, "signKey " + signKey + " signValue " + signValue + " result:" + result);
                                                    if (!result) {
                                                        H5PackageParser.notifyFail(appId, offlineHost, null);
                                                        i = 6;
                                                        break;
                                                    }
                                                }
                                                i2++;
                                            }
                                        } catch (Exception e) {
                                            H5Log.e((String) TAG, (Throwable) e);
                                        }
                                    }
                                }
                            } else {
                                H5Log.w(TAG, "cert not exists!");
                                H5PackageParser.notifyFail(appId, offlineHost, null);
                                i = 4;
                            }
                        }
                    }
                }
            }
        }
        return i;
        H5Log.d(TAG, "parse package elapse " + (System.currentTimeMillis() - time) + " appId:" + appId);
        i = 0;
        return i;
    }
}
