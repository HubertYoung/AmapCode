package defpackage;

import android.net.http.SslError;
import android.os.Environment;
import android.text.format.DateFormat;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.uc.webview.export.SslErrorHandler;
import com.uc.webview.export.WebView;
import java.io.File;
import java.io.FileOutputStream;

/* renamed from: aiz reason: default package */
/* compiled from: WebViewSslErrorHandler */
public final class aiz {
    /* access modifiers changed from: private */
    public static boolean a = false;
    private static final String b;

    /* renamed from: aiz$a */
    /* compiled from: WebViewSslErrorHandler */
    public interface a {
        void a();
    }

    public static void a(WebView webView, final SslErrorHandler sslErrorHandler, SslError sslError) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------\nOriginalUrl:");
        sb.append(webView.getOriginalUrl() == null ? "null" : webView.getOriginalUrl());
        sb.append("\nurl:");
        sb.append(webView.getUrl() == null ? "null" : webView.getUrl());
        sb.append("\nCertificate:");
        sb.append(webView.getCertificate() == null ? "null" : webView.getCertificate().toString());
        sb.append("\nerrorUrl:");
        sb.append(sslError.getUrl() == null ? "null" : sslError.getUrl());
        sb.append("\nerrorType:");
        sb.append(sslError.getPrimaryError());
        sb.append("\nerror:");
        sb.append(sslError.toString());
        a("SslErrorHandler", sb.toString());
        final bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
            aVar.a(R.string.ssl_error_tips);
            aVar.a(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    aiz.a = true;
                    sslErrorHandler.cancel();
                }
            });
            aVar.b(R.string.ssl_error_continue, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    aiz.a = true;
                    sslErrorHandler.proceed();
                }
            });
            aVar.b = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    if (!aiz.a) {
                        sslErrorHandler.cancel();
                    }
                }
            };
            aVar.c = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    if (!aiz.a) {
                        sslErrorHandler.cancel();
                    }
                }
            };
            aVar.a(false);
            AlertView a2 = aVar.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
        }
    }

    public static void a(WebView webView, final SslErrorHandler sslErrorHandler, SslError sslError, final a aVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------\nOriginalUrl:");
        sb.append(webView.getOriginalUrl() == null ? "null" : webView.getOriginalUrl());
        sb.append("\nurl:");
        sb.append(webView.getUrl() == null ? "null" : webView.getUrl());
        sb.append("\nCertificate:");
        sb.append(webView.getCertificate() == null ? "null" : webView.getCertificate().toString());
        sb.append("\nerrorUrl:");
        sb.append(sslError.getUrl() == null ? "null" : sslError.getUrl());
        sb.append("\nerrorType:");
        sb.append(sslError.getPrimaryError());
        sb.append("\nerror:");
        sb.append(sslError.toString());
        a("SslErrorHandler", sb.toString());
        final bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            com.autonavi.widget.ui.AlertView.a aVar2 = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
            aVar2.a(R.string.ssl_error_tips);
            aVar2.a(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    aiz.a = true;
                    sslErrorHandler.cancel();
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            });
            aVar2.b(R.string.ssl_error_continue, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    aiz.a = true;
                    sslErrorHandler.proceed();
                }
            });
            aVar2.b = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    if (!aiz.a) {
                        sslErrorHandler.cancel();
                        if (aVar != null) {
                            aVar.a();
                        }
                    }
                }
            };
            aVar2.c = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    if (!aiz.a) {
                        sslErrorHandler.cancel();
                        if (aVar != null) {
                            aVar.a();
                        }
                    }
                }
            };
            aVar2.a(false);
            AlertView a2 = aVar2.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
        }
    }

    public static void a(android.webkit.WebView webView, final android.webkit.SslErrorHandler sslErrorHandler, SslError sslError, final a aVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------\nOriginalUrl:");
        sb.append(webView.getOriginalUrl() == null ? "null" : webView.getOriginalUrl());
        sb.append("\nurl:");
        sb.append(webView.getUrl() == null ? "null" : webView.getUrl());
        sb.append("\nCertificate:");
        sb.append(webView.getCertificate() == null ? "null" : webView.getCertificate().toString());
        sb.append("\nerrorUrl:");
        sb.append(sslError.getUrl() == null ? "null" : sslError.getUrl());
        sb.append("\nerrorType:");
        sb.append(sslError.getPrimaryError());
        sb.append("\nerror:");
        sb.append(sslError.toString());
        a("SslErrorHandler", sb.toString());
        final bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            com.autonavi.widget.ui.AlertView.a aVar2 = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
            aVar2.a(R.string.ssl_error_tips);
            aVar2.a(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    aiz.a = true;
                    sslErrorHandler.cancel();
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            });
            aVar2.b(R.string.ssl_error_continue, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    aiz.a = true;
                    sslErrorHandler.proceed();
                }
            });
            aVar2.b = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    if (!aiz.a) {
                        sslErrorHandler.cancel();
                        if (aVar != null) {
                            aVar.a();
                        }
                    }
                }
            };
            aVar2.c = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    if (!aiz.a) {
                        sslErrorHandler.cancel();
                        if (aVar != null) {
                            aVar.a();
                        }
                    }
                }
            };
            aVar2.a(false);
            AlertView a2 = aVar2.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/autonavi/log/SslError");
        b = sb.toString();
    }

    private static boolean a(String str, String str2) {
        try {
            File file = new File(b);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file, true);
                try {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(DateFormat.format("[yyyy-MM-dd kk:mm:ss]", System.currentTimeMillis()).toString());
                    stringBuffer.append(SimpleComparison.LESS_THAN_OPERATION);
                    stringBuffer.append(str);
                    stringBuffer.append(SimpleComparison.GREATER_THAN_OPERATION);
                    stringBuffer.append(str2);
                    stringBuffer.append("\n");
                    fileOutputStream2.write(stringBuffer.toString().getBytes("UTF-8"));
                    bow.a(fileOutputStream2);
                } catch (Exception e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    try {
                        e.printStackTrace();
                        bow.a(fileOutputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        bow.a(fileOutputStream2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bow.a(fileOutputStream2);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                bow.a(fileOutputStream);
                return false;
            }
        } catch (Throwable unused) {
        }
        return true;
    }
}
