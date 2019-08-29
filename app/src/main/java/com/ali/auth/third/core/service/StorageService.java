package com.ali.auth.third.core.service;

import com.ali.auth.third.core.model.HistoryAccount;
import java.util.List;
import java.util.TreeMap;

public interface StorageService {
    HistoryAccount findHistoryAccount(String str);

    String getAppKey();

    byte[] getByteArray(String str);

    List<HistoryAccount> getHistoryAccounts();

    String getUmid();

    String getValue(String str, boolean z);

    HistoryAccount matchHistoryAccount(String str);

    void putLoginHistory(HistoryAccount historyAccount, String str);

    void putValue(String str, String str2, boolean z);

    void removeSafeToken(String str);

    void removeValue(String str, boolean z);

    void savePublicKey(byte[] bArr);

    void setUmid(String str);

    String signMap(String str, TreeMap<String, String> treeMap);

    String symDecrypt(String str, String str2);

    String symEncrypt(String str, String str2);
}
