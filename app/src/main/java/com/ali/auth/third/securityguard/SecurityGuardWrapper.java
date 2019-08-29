package com.ali.auth.third.securityguard;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Base64;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.exception.SecRuntimeException;
import com.ali.auth.third.core.model.HistoryAccount;
import com.ali.auth.third.core.model.LoginHistory;
import com.ali.auth.third.core.service.StorageService;
import com.ali.auth.third.core.trace.SDKLogger;
import com.alibaba.analytics.core.Constants;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SecurityGuardWrapper implements StorageService {
    public static final String TAG = "auth.SecurityGuardWrapper";

    private SecurityGuardManager a() {
        try {
            return SecurityGuardManager.getInstance(KernelContext.context);
        } catch (SecException e) {
            throw new SecRuntimeException(e.getErrorCode(), e);
        }
    }

    private String a(String str, String str2) {
        try {
            return a().getSafeTokenComp().signWithToken(str, str2.getBytes("UTF-8"), 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (SecException e2) {
            e2.printStackTrace();
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private void a(String str) {
    }

    private byte[] a(byte[] bArr, String str) {
        try {
            a(str);
            return a().getStaticKeyEncryptComp().encrypt(16, str, bArr);
        } catch (SecException e) {
            throw new SecRuntimeException(e.getErrorCode(), e);
        }
    }

    private boolean b() {
        return a().getSDKVerison().contains("weak");
    }

    private byte[] b(byte[] bArr, String str) {
        try {
            return a().getStaticKeyEncryptComp().decrypt(16, str, bArr);
        } catch (SecException e) {
            throw new SecRuntimeException(e.getErrorCode(), e);
        }
    }

    public static boolean equals(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }

    public HistoryAccount findHistoryAccount(String str) {
        try {
            List<HistoryAccount> historyAccounts = getHistoryAccounts();
            if (historyAccounts != null) {
                for (HistoryAccount next : historyAccounts) {
                    if (next.userId != null && next.userId.equals(str)) {
                        return next;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public String getAppKey() {
        try {
            return a().getStaticDataStoreComp().getAppKeyByIndex(ConfigManager.getAppKeyIndex(), ConfigManager.POSTFIX_OF_SECURITY_JPG);
        } catch (SecException e) {
            throw new SecRuntimeException(e.getErrorCode(), e);
        }
    }

    public byte[] getByteArray(String str) {
        try {
            return a().getDynamicDataStoreComp().getByteArray(str);
        } catch (SecException unused) {
            return null;
        }
    }

    public List<HistoryAccount> getHistoryAccounts() {
        try {
            String stringDDpEx = a().getDynamicDataStoreComp().getStringDDpEx("taesdk_history_acounts", 0);
            if (TextUtils.isEmpty(stringDDpEx)) {
                return null;
            }
            LoginHistory parseObject = parseObject(stringDDpEx);
            if (parseObject != null) {
                return parseObject.accountHistory;
            }
            return null;
        } catch (Exception unused) {
        }
    }

    public String getProviderName() {
        return b() ? Constants.SDK_TYPE : "full";
    }

    public String getUmid() {
        try {
            return a().getUMIDComp().getSecurityToken();
        } catch (SecException e) {
            throw new SecRuntimeException(e.getErrorCode(), e);
        }
    }

    public String getValue(String str, boolean z) {
        if (!z) {
            return a().getStaticDataStoreComp().getExtraData(str, ConfigManager.POSTFIX_OF_SECURITY_JPG);
        }
        try {
            return a().getDynamicDataStoreComp().getString(str);
        } catch (SecException unused) {
            return null;
        }
    }

    public HistoryAccount matchHistoryAccount(String str) {
        List<HistoryAccount> historyAccounts = getHistoryAccounts();
        if (historyAccounts != null) {
            for (HistoryAccount next : historyAccounts) {
                if (equals(str, next.nick) || equals(str, next.email)) {
                    return next;
                }
                if (equals(str, next.mobile)) {
                    return next;
                }
            }
        }
        return null;
    }

    public LoginHistory parseObject(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONArray jSONArray = new JSONArray(str);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (jSONObject != null) {
                HistoryAccount historyAccount = new HistoryAccount();
                historyAccount.userId = jSONObject.optString("userId");
                historyAccount.tokenKey = jSONObject.optString("tokenKey");
                historyAccount.mobile = jSONObject.optString("mobile");
                historyAccount.nick = jSONObject.optString("nick");
                historyAccount.email = jSONObject.optString(NotificationCompat.CATEGORY_EMAIL);
                arrayList.add(historyAccount);
            }
        }
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.accountHistory = arrayList;
        return loginHistory;
    }

    public void putLoginHistory(HistoryAccount historyAccount, String str) {
        LoginHistory loginHistory;
        if (saveToken(historyAccount.tokenKey, str)) {
            IDynamicDataStoreComponent dynamicDataStoreComp = a().getDynamicDataStoreComp();
            if (dynamicDataStoreComp != null) {
                String str2 = null;
                try {
                    str2 = dynamicDataStoreComp.getStringDDpEx("taesdk_history_acounts", 0);
                } catch (SecException e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(str2)) {
                    loginHistory = new LoginHistory();
                } else {
                    try {
                        loginHistory = parseObject(str2);
                    } catch (JSONException unused) {
                        loginHistory = new LoginHistory();
                        try {
                            dynamicDataStoreComp.removeStringDDpEx("taesdk_history_acounts", 0);
                        } catch (SecException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                if (loginHistory != null) {
                    if (loginHistory.accountHistory != null) {
                        ArrayList arrayList = new ArrayList();
                        for (HistoryAccount next : loginHistory.accountHistory) {
                            if (TextUtils.isEmpty(next.userId) || !next.userId.equals(historyAccount.userId)) {
                                arrayList.add(next);
                            } else {
                                next.update(historyAccount);
                                historyAccount = next;
                            }
                        }
                        arrayList.add(historyAccount);
                        for (int size = arrayList.size() - 3; size > 0; size--) {
                            removeSafeToken(((HistoryAccount) arrayList.remove(0)).tokenKey);
                        }
                        loginHistory.accountHistory = arrayList;
                        try {
                            dynamicDataStoreComp.putStringDDpEx("taesdk_history_acounts", toJSONString(loginHistory), 0);
                        } catch (SecException e3) {
                            e3.printStackTrace();
                        }
                    } else {
                        loginHistory.accountHistory = new ArrayList();
                        loginHistory.accountHistory.add(historyAccount);
                        try {
                            dynamicDataStoreComp.putStringDDpEx("taesdk_history_acounts", toJSONString(loginHistory), 0);
                        } catch (SecException e4) {
                            e4.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void putValue(String str, String str2, boolean z) {
        try {
            a().getDynamicDataStoreComp().putString(str, str2);
        } catch (SecException e) {
            e.printStackTrace();
        }
    }

    public void removeSafeToken(String str) {
        try {
            a().getSafeTokenComp().removeToken(str);
        } catch (SecException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeValue(String str, boolean z) {
        if (z) {
            try {
                a().getDynamicDataStoreComp().removeString(str);
            } catch (SecException e) {
                e.printStackTrace();
            }
        }
    }

    public void savePublicKey(byte[] bArr) {
        if (getProviderName().equals(ProcessInfo.ALIAS_LITE)) {
            try {
                Method declaredMethod = a().getClass().getDeclaredMethod("saveCertPublicKey", new Class[]{byte[].class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(a(), new Object[]{bArr});
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Fail to invoke the saveCertPublicKey, the error message is ");
                sb.append(e.getMessage());
                SDKLogger.e(TAG, sb.toString(), e);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0049, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean saveToken(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x004a }
            r1 = 0
            if (r0 != 0) goto L_0x0048
            com.alibaba.wireless.security.open.SecurityGuardManager r0 = r6.a()     // Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x000f
            goto L_0x0048
        L_0x000f:
            com.alibaba.wireless.security.open.SecurityGuardManager r0 = r6.a()     // Catch:{ SecException -> 0x0034, Throwable -> 0x002f }
            com.alibaba.wireless.security.open.safetoken.ISafeTokenComponent r0 = r0.getSafeTokenComp()     // Catch:{ SecException -> 0x0034, Throwable -> 0x002f }
            if (r0 == 0) goto L_0x002d
            java.lang.String r2 = ""
            java.lang.String r3 = ""
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            java.lang.String[] r2 = new java.lang.String[]{r2, r3, r4, r5}     // Catch:{ SecException -> 0x0034, Throwable -> 0x002f }
            r2 = r2[r1]     // Catch:{ SecException -> 0x0034, Throwable -> 0x002f }
            boolean r7 = r0.saveToken(r7, r8, r2, r1)     // Catch:{ SecException -> 0x0034, Throwable -> 0x002f }
            monitor-exit(r6)
            return r7
        L_0x002d:
            monitor-exit(r6)
            return r1
        L_0x002f:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ all -> 0x004a }
            goto L_0x0046
        L_0x0034:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ all -> 0x004a }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x004a }
            java.lang.String r0 = "errorCode"
            r8.<init>(r0)     // Catch:{ all -> 0x004a }
            int r7 = r7.getErrorCode()     // Catch:{ all -> 0x004a }
            r8.append(r7)     // Catch:{ all -> 0x004a }
        L_0x0046:
            monitor-exit(r6)
            return r1
        L_0x0048:
            monitor-exit(r6)
            return r1
        L_0x004a:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.securityguard.SecurityGuardWrapper.saveToken(java.lang.String, java.lang.String):boolean");
    }

    public void setUmid(String str) {
    }

    public String signMap(String str, TreeMap<String, String> treeMap) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Entry next : treeMap.entrySet()) {
            sb.append((String) next.getKey());
            sb.append("=");
            sb.append((String) next.getValue());
            sb.append("&");
        }
        return a(str, sb.substring(0, sb.length() - 1));
    }

    public String symDecrypt(String str, String str2) {
        try {
            byte[] decode = Base64.decode(str, 8);
            if (TextUtils.isEmpty(str2)) {
                str2 = "seed_key";
            }
            return new String(b(decode, str2), "UTF-8");
        } catch (SecRuntimeException e) {
            throw e;
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2);
        }
    }

    public String symEncrypt(String str, String str2) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            if (TextUtils.isEmpty(str2)) {
                str2 = "seed_key";
            }
            try {
                return Base64.encodeToString(a(bytes, str2), 11);
            } catch (SecRuntimeException e) {
                throw e;
            }
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException();
        }
    }

    public String toJSONString(LoginHistory loginHistory) {
        if (loginHistory == null || loginHistory.accountHistory == null || loginHistory.accountHistory.size() <= 0) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (HistoryAccount next : loginHistory.accountHistory) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("userId", next.userId);
                jSONObject.put("tokenKey", next.tokenKey);
                jSONObject.put("nick", next.nick);
                jSONObject.put(NotificationCompat.CATEGORY_EMAIL, next.email);
                jSONObject.put("mobile", next.mobile);
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        }
        return jSONArray.toString();
    }
}
