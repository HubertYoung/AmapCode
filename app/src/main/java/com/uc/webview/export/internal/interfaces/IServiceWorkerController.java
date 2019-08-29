package com.uc.webview.export.internal.interfaces;

import com.uc.webview.export.ServiceWorkerClient;
import com.uc.webview.export.ServiceWorkerWebSettings;
import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
public interface IServiceWorkerController {
    ServiceWorkerWebSettings getServiceWorkerWebSettings();

    void setServiceWorkerClient(ServiceWorkerClient serviceWorkerClient);
}
