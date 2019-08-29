package com.uc.webview.export.internal.interfaces;

import com.uc.webview.export.annotations.Reflection;
import java.util.Map;

@Reflection
/* compiled from: ProGuard */
public interface IRequest {
    void cancel();

    EventHandler getEventHandler();

    Map<String, String> getHeaders();

    boolean getIsUCProxy();

    int getLoadtype();

    String getMethod();

    int getRequestType();

    Map<String, String> getUCHeaders();

    Map<String, byte[]> getUploadDataMap();

    Map<String, String> getUploadFileMap();

    long getUploadFileTotalLen();

    String getUrl();

    void handleSslErrorResponse(boolean z);

    void setEventHandler(EventHandler eventHandler);

    void waitUntilComplete(int i);
}
