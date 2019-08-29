package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.webview.h5template.WebTemplateUpdateServerImpl;

@BundleInterface(bgx.class)
/* renamed from: bgy reason: default package */
/* compiled from: H5TemplateExporter */
public class bgy implements bgx {
    private WebTemplateUpdateServerImpl a = new WebTemplateUpdateServerImpl(AMapAppGlobal.getApplication());

    public void update() {
        this.a.update();
    }

    public void getUrl(String str, a aVar) {
        this.a.getUrl(str, aVar);
    }

    public String getUrl(String str) {
        return this.a.getUrl(str);
    }

    public String getTemplateFilePath(String str) {
        return this.a.getTemplateFilePath(str);
    }
}
