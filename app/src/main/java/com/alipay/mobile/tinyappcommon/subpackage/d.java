package com.alipay.mobile.tinyappcommon.subpackage;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5RsaUtil;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.tar.TarEntry;
import com.alipay.mobile.nebula.util.tar.TarInputStream;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.api.H5ParseResult;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalPackage;
import com.alipay.mobile.nebulacore.appcenter.parse.H5PackageParser;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/* compiled from: SubPackageParser */
public final class d {
    public static H5ParseResult a(Bundle params, Map<String, byte[]> resPkg, String downloadUrl) {
        String host;
        if (params == null || TextUtils.isEmpty(downloadUrl)) {
            H5Log.e((String) "SubPackageParser", (String) "parseSubPackage() invalid params");
            return a(1, (String) "INVALID_PARAM");
        }
        String appId = H5Utils.getString(params, (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            H5Log.e((String) "SubPackageParser", (String) "parseSubPackage() invalid app_id");
            return a(1, (String) "INVALID_APP_ID");
        }
        String offlineHost = H5Utils.getString(params, (String) H5Param.OFFLINE_HOST);
        String onlineHost = H5Utils.getString(params, (String) H5Param.ONLINE_HOST);
        boolean pureOnline = H5Utils.getInt(params, (String) "appType") == 2;
        boolean mapHost = H5Utils.getBoolean(params, (String) H5Param.MAP_HOST, false);
        String version = H5Utils.getString(params, (String) "appVersion");
        boolean isNebula = H5Utils.getBoolean(params, (String) H5Param.IS_NEBULA_APP, false);
        if (TextUtils.isEmpty(offlineHost)) {
            H5AppProvider appProvider = (H5AppProvider) Nebula.getProviderManager().getProvider(H5AppProvider.class.getName());
            if (appProvider != null) {
                String installedPath = appProvider.getInstallPath(appId, version);
                if (!TextUtils.isEmpty(installedPath)) {
                    offlineHost = "file://" + installedPath;
                    if (!offlineHost.endsWith("/")) {
                        offlineHost = offlineHost + "/";
                    }
                }
            }
        }
        String subPackagePath = a.a(downloadUrl, appId);
        if (TextUtils.isEmpty(subPackagePath)) {
            H5Log.e((String) "SubPackageParser", (String) "parseSubPackage() SubPackagePath_IS_NULL");
            return a(1, (String) "SubPackagePath_IS_NULL");
        }
        if (!subPackagePath.endsWith("/")) {
            subPackagePath = subPackagePath + "/";
        }
        H5Log.d("SubPackageParser", "parseSubPackage() appId " + appId + " mapHost: " + mapHost + " offlineHost:" + offlineHost + " onlineHost:" + onlineHost + " subPackagePath" + subPackagePath + " version:" + version + " isNebula " + isNebula);
        if (!H5FileUtil.exists(subPackagePath)) {
            H5Log.e((String) "SubPackageParser", "parseSubPackage() offlinePath " + subPackagePath + " not exists!");
            a(appId, "TINY_APP_SUBPACKAGE_PATH_NOT_EXIST", subPackagePath, 2, "OFFLINE_PATH_NOT_EXIST");
            return a(2, (String) "OFFLINE_PATH_NOT_EXIST");
        }
        String tarPath = a(subPackagePath);
        H5Log.e((String) "SubPackageParser", "parseSubPackage() tarPath : " + tarPath);
        if (TextUtils.isEmpty(tarPath) || !H5FileUtil.exists(tarPath)) {
            H5Log.w("SubPackageParser", "parseSubPackage() tar package not exists!");
            a(appId, "TINY_APP_SUBPACKAGE_TAR_PATH_NOT_EXIST", subPackagePath, 3, "TAR_PATH_NOT_EXIST");
            return a(3, (String) "TAR_PATH_NOT_EXIST");
        }
        H5ParseResult verifyResult = a(params, subPackagePath);
        if (verifyResult.code != 0) {
            a(appId, "TINY_APP_SUBPACKAGE_VERIFY_CERT_FAIL", subPackagePath, 3, "TAR_PATH_NOT_EXIST");
            return verifyResult;
        } else if (resPkg == null) {
            H5Log.e((String) "SubPackageParser", (String) "parseSubPackage() invalid resPkg");
            return a(8, (String) "INVALID_PARAM");
        } else {
            if (mapHost) {
                host = onlineHost;
            } else {
                host = offlineHost;
            }
            TarInputStream tis = null;
            byte[] buffer = H5IOUtils.getBuf(2048);
            try {
                TarInputStream tarInputStream = new TarInputStream(new BufferedInputStream(new FileInputStream(tarPath)));
                while (true) {
                    try {
                        TarEntry te = tarInputStream.getNextEntry();
                        if (te != null) {
                            String entryName = te.getName();
                            if (!te.isDirectory() && !TextUtils.isEmpty(entryName) && !TextUtils.equals(entryName, H5WalletWrapper.HPM_FILE_NAME)) {
                                PoolingByteArrayOutputStream poolingByteArrayOutputStream = new PoolingByteArrayOutputStream();
                                while (true) {
                                    int count = tarInputStream.read(buffer);
                                    if (count == -1) {
                                        break;
                                    }
                                    poolingByteArrayOutputStream.write(buffer, 0, count);
                                }
                                byte[] data = poolingByteArrayOutputStream.toByteArray();
                                H5IOUtils.closeQuietly(poolingByteArrayOutputStream);
                                if (data != null) {
                                    if (entryName.startsWith("_animation")) {
                                        resPkg.put(entryName, data);
                                    } else if (pureOnline) {
                                        if ("yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_parse_http"))) {
                                            resPkg.put(new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(entryName).toString(), data);
                                        }
                                        resPkg.put(new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS).append(entryName).toString(), data);
                                    } else {
                                        resPkg.put(host + entryName, data);
                                    }
                                    if (H5StartParamManager.appConfig.equalsIgnoreCase(entryName)) {
                                        H5StartParamManager.getInstance().put(appId, data);
                                        Nebula.isDSL = true;
                                    }
                                    if (H5PackageParser.ENTRY_TABBAR_JSON.equalsIgnoreCase(entryName)) {
                                        H5TabbarUtils.setTabData(appId, data);
                                    }
                                    if ("header.json".equalsIgnoreCase(entryName)) {
                                        H5GlobalPackage.addHeader(appId, data);
                                    }
                                }
                            }
                        } else {
                            H5IOUtils.returnBuf(buffer);
                            H5IOUtils.closeQuietly(tarInputStream);
                            return a(0, (String) "parseSubPackage_SUCCESS");
                        }
                    } catch (Throwable th) {
                        th = th;
                        tis = tarInputStream;
                        H5IOUtils.returnBuf(buffer);
                        H5IOUtils.closeQuietly(tis);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                e = th2;
                H5Log.e("SubPackageParser", "parseSubPackage() exception", e);
                a(appId, "TINY_APP_SUBPACKAGE_PARSE_EXCEPTION", subPackagePath, 7, e.toString());
                H5ParseResult verifyResult2 = a(7, e.toString());
                H5IOUtils.returnBuf(buffer);
                H5IOUtils.closeQuietly(tis);
                return verifyResult2;
            }
        }
    }

    private static H5ParseResult a(Bundle params, String subPackagePath) {
        JSONObject joCert;
        boolean result;
        if (params == null || TextUtils.isEmpty(subPackagePath)) {
            H5Log.e((String) "SubPackageParser", (String) "verifyCert() invalid params");
            return a(1, (String) "INVALID_PARAM");
        }
        boolean needForceVerify = H5Utils.getBoolean(params, (String) "needForceVerify", false);
        String publicKey = H5Utils.getString(params, (String) "customPublicKey", (String) "");
        String certPath = subPackagePath + "/CERT.json";
        String signPath = subPackagePath + "/SIGN.json";
        if (H5FileUtil.exists(certPath) || H5FileUtil.exists(signPath)) {
            if (H5PackageParser.isNeedVerify() || needForceVerify) {
                File[] childrenFiles = new File(subPackagePath).listFiles();
                if (childrenFiles == null || childrenFiles.length == 0) {
                    H5Log.e((String) "SubPackageParser", (String) "parseSubPackage VerifyCert() childrenFiles length is 0");
                    return a(2, (String) "OFFLINE_PATH_NOT_EXIST");
                }
                boolean useNewSignKey = H5FileUtil.exists(signPath);
                if (!useNewSignKey || !TextUtils.isEmpty(publicKey) || !TextUtils.isEmpty(H5PackageParser.TAR_PUBLIC_KEY)) {
                    H5Log.d("SubPackageParser", "parseSubPackage VerifyCert() signPath is exist : " + useNewSignKey + ", publicKey : " + publicKey + ", H5PackageParser.TAR_PUBLIC_KEY : " + H5PackageParser.TAR_PUBLIC_KEY);
                    useNewSignKey = false;
                }
                H5Log.d("SubPackageParser", "parseSubPackage VerifyCert() useNewSignKey : " + useNewSignKey);
                if (useNewSignKey) {
                    joCert = H5Utils.parseObject(H5Utils.read(signPath));
                } else {
                    joCert = H5Utils.parseObject(H5Utils.read(certPath));
                }
                if (joCert == null || joCert.isEmpty()) {
                    H5Log.e((String) "SubPackageParser", (String) "parseSubPackage VerifyCert() joCert is empty");
                    return a(5, (String) "TAR_SIGNATURE_IS_EMPTY");
                }
                try {
                    for (File name : childrenFiles) {
                        String signKey = name.getName();
                        if (!TextUtils.isEmpty(signKey) && !TextUtils.equals(signKey, "CERT.json") && !TextUtils.equals(signKey, "SIGN.json") && !signKey.contains("ios") && !signKey.contains("android.tiny.tar")) {
                            String signValue = joCert.get(signKey).toString();
                            if (useNewSignKey) {
                                result = H5RsaUtil.verify(subPackagePath + "/" + signKey, H5PackageParser.NEW_H5APP_SIGN_PUBLIC_KEY, signValue);
                            } else if (!TextUtils.isEmpty(publicKey)) {
                                result = H5RsaUtil.verify(subPackagePath + "/" + signKey, publicKey, signValue);
                            } else if (!TextUtils.isEmpty(H5PackageParser.TAR_PUBLIC_KEY)) {
                                result = H5RsaUtil.verify(subPackagePath + "/" + signKey, H5PackageParser.TAR_PUBLIC_KEY, signValue);
                            } else {
                                result = H5RsaUtil.verify(subPackagePath + "/" + signKey, H5PackageParser.DEFAULT_TAR_PUBLIC_KEY, signValue);
                            }
                            H5Log.d("SubPackageParser", "signKey: " + signKey + " signValue: " + signValue);
                            if (!result) {
                                return a(6, (String) "VERIFY_FAIL");
                            }
                        }
                    }
                } catch (Exception e) {
                    H5Log.e((String) "SubPackageParser", (Throwable) e);
                }
            }
            H5Log.d("SubPackageParser", "parseSubPackage VerifyCert Success");
            return a(0, (String) "VERIFY_CERT_SUCCESS");
        }
        H5Log.e((String) "SubPackageParser", (String) "parseSubPackage VerifyCert() cert file not exists!");
        return a(4, (String) "CERT_PATH_NOT_EXIST");
    }

    private static String a(String subPackagePath) {
        if (TextUtils.isEmpty(subPackagePath)) {
            return "";
        }
        try {
            File file = new File(subPackagePath);
            if (H5FileUtil.exists(file)) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (File name : listFiles) {
                        String fileName = name.getName();
                        if (fileName.endsWith("tar")) {
                            return subPackagePath + fileName;
                        }
                    }
                }
            }
        } catch (Exception e) {
            H5Log.e((String) "SubPackageParser", (Throwable) e);
        }
        return "";
    }

    private static void a(String appId, String seedId, String subPackagePath, int resultCode, String resultMsg) {
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(subPackagePath) && !TextUtils.isEmpty(resultMsg)) {
            H5LogData logData = H5LogData.seedId(seedId);
            logData.param1().add(appId, null).param2().add("result.code", Integer.valueOf(resultCode)).add("result.msg", resultMsg).add("subPackagePath", subPackagePath);
            H5LogUtil.logNebulaTech(logData);
            try {
                File file = new File(subPackagePath);
                if (H5FileUtil.exists(file)) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null) {
                        H5Log.e((String) "SubPackageParser", (String) "notifyFailed file lists:");
                        for (File f : listFiles) {
                            H5Log.e((String) "SubPackageParser", "notifyFailed file: " + f.getName());
                        }
                        return;
                    }
                    H5Log.e((String) "SubPackageParser", (String) "notifyFailed listFiles is null");
                }
            } catch (Exception e) {
                H5Log.e((String) "SubPackageParser", (Throwable) e);
            }
        }
    }

    private static H5ParseResult a(int code, String msg) {
        H5ParseResult h5ParseResult = new H5ParseResult();
        h5ParseResult.code = code;
        h5ParseResult.msg = msg;
        return h5ParseResult;
    }
}
