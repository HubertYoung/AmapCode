package com.alipay.mobile.nebula.process;

import android.net.Uri;
import android.os.Bundle;
import java.util.Map;

public interface H5IpcServer {
    boolean containDevAppId(String str);

    boolean containPreferAppId(String str);

    String decodeToPath(String str);

    void downloadApp(String str, String str2, String str3);

    String encodeToLocalId(String str);

    String getAudioPathById(String str);

    boolean getBooleanConfig(String str, boolean z);

    String getConfig(String str);

    String getDevNbsv(String str);

    String getExtern_token();

    String getLoginId();

    String getNick();

    String getPreferVersion(String str);

    String getStringConfig(String str, String str2);

    Map<String, String> getTinyProcessUseConfigList();

    String getUserAvatar();

    String getUserId();

    String getVideoPathById(String str);

    boolean hasAccessToDebug(String str);

    boolean isDownloading(String str);

    boolean isLogin();

    void killTinyOpenMainUrl(String str, String str2);

    int process(Uri uri);

    int process(H5IpcSchemeModel h5IpcSchemeModel);

    String removeApiPermission(String str, String str2);

    String removeAppConfigByte(String str, String str2);

    void removeDevApp(String str);

    void setStringConfig(String str, String str2);

    void startApp(String str, String str2, Bundle bundle);

    String syncScanBitmapFromPath(String str);
}
