package defpackage;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import com.uc.webview.export.HttpAuthHandler;
import com.uc.webview.export.SslErrorHandler;
import com.uc.webview.export.WebResourceError;
import com.uc.webview.export.WebResourceRequest;
import com.uc.webview.export.WebResourceResponse;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebViewClient;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ery reason: default package */
/* compiled from: WebViewClientDispatcher */
public class ery extends WebViewClient {
    public List<WebViewClient> b = new ArrayList();

    public final void a(WebViewClient webViewClient) {
        if (!this.b.contains(webViewClient)) {
            this.b.add(webViewClient);
        }
    }

    public final void b(WebViewClient webViewClient) {
        if (this.b.contains(webViewClient)) {
            this.b.remove(webViewClient);
        }
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onPageStarted(webView, str, bitmap);
        }
    }

    public void onFormResubmission(WebView webView, Message message, Message message2) {
        try {
            for (int size = this.b.size() - 1; size >= 0; size--) {
                this.b.get(size).onFormResubmission(webView, message, Message.obtain(message2));
            }
        } catch (Exception unused) {
        }
    }

    public void onLoadResource(WebView webView, String str) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onLoadResource(webView, str);
        }
    }

    public void onPageFinished(WebView webView, String str) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onPageFinished(webView, str);
        }
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).shouldOverrideUrlLoading(webView, str)) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(21)
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            WebResourceResponse shouldInterceptRequest = this.b.get(size).shouldInterceptRequest(webView, webResourceRequest);
            if (shouldInterceptRequest != null) {
                return shouldInterceptRequest;
            }
        }
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @TargetApi(23)
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedError(webView, webResourceRequest, webResourceError);
        }
    }

    @TargetApi(23)
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    }

    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).doUpdateVisitedHistory(webView, str, z);
        }
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    }

    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
        }
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).shouldOverrideKeyEvent(webView, keyEvent)) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(21)
    public void onUnhandledInputEvent(WebView webView, InputEvent inputEvent) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onUnhandledInputEvent(webView, inputEvent);
        }
    }

    public void onScaleChanged(WebView webView, float f, float f2) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onScaleChanged(webView, f, f2);
        }
    }

    public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedLoginRequest(webView, str, str2, str3);
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            WebResourceResponse shouldInterceptRequest = this.b.get(size).shouldInterceptRequest(webView, str);
            if (shouldInterceptRequest != null) {
                return shouldInterceptRequest;
            }
        }
        return super.shouldInterceptRequest(webView, str);
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedError(webView, i, str, str2);
        }
    }

    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        super.onUnhandledKeyEvent(webView, keyEvent);
    }
}
