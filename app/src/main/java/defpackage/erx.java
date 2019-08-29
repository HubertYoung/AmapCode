package defpackage;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import com.uc.webview.export.GeolocationPermissions.Callback;
import com.uc.webview.export.JsPromptResult;
import com.uc.webview.export.JsResult;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebChromeClient.CustomViewCallback;
import com.uc.webview.export.WebChromeClient.FileChooserParams;
import com.uc.webview.export.WebView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: erx reason: default package */
/* compiled from: WebChromeClientDispather */
public class erx extends WebChromeClient {
    public List<WebChromeClient> b = new ArrayList();

    public final void a(@NonNull WebChromeClient webChromeClient) {
        if (!this.b.contains(webChromeClient)) {
            this.b.add(webChromeClient);
        }
    }

    public final void b(@NonNull WebChromeClient webChromeClient) {
        if (this.b.contains(webChromeClient)) {
            this.b.remove(webChromeClient);
        }
    }

    public void onProgressChanged(WebView webView, int i) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onProgressChanged(webView, i);
        }
    }

    public void onReceivedTitle(WebView webView, String str) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedTitle(webView, str);
        }
    }

    public void onReceivedIcon(WebView webView, Bitmap bitmap) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedIcon(webView, bitmap);
        }
    }

    public void onReceivedTouchIconUrl(WebView webView, String str, boolean z) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onReceivedTouchIconUrl(webView, str, z);
        }
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onShowCustomView(view, customViewCallback);
        }
    }

    public void onHideCustomView() {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onHideCustomView();
        }
    }

    public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).onCreateWindow(webView, z, z2, message)) {
                return true;
            }
        }
        return false;
    }

    public void onRequestFocus(WebView webView) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onRequestFocus(webView);
        }
    }

    public void onCloseWindow(WebView webView) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onCloseWindow(webView);
        }
    }

    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).onJsAlert(webView, str, str2, jsResult)) {
                return true;
            }
        }
        return false;
    }

    public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).onJsConfirm(webView, str, str2, jsResult)) {
                return true;
            }
        }
        return false;
    }

    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).onJsPrompt(webView, str, str2, str3, jsPromptResult)) {
                return true;
            }
        }
        return false;
    }

    public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).onJsBeforeUnload(webView, str, str2, jsResult)) {
                return true;
            }
        }
        return false;
    }

    public void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
        callback.invoke(str, true, false);
    }

    public void onGeolocationPermissionsHidePrompt() {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).onGeolocationPermissionsHidePrompt();
        }
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).onConsoleMessage(consoleMessage)) {
                return true;
            }
        }
        return false;
    }

    public Bitmap getDefaultVideoPoster() {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            Bitmap defaultVideoPoster = this.b.get(size).getDefaultVideoPoster();
            if (defaultVideoPoster != null) {
                return defaultVideoPoster;
            }
        }
        return super.getDefaultVideoPoster();
    }

    public View getVideoLoadingProgressView() {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            View videoLoadingProgressView = this.b.get(size).getVideoLoadingProgressView();
            if (videoLoadingProgressView != null) {
                return videoLoadingProgressView;
            }
        }
        return super.getVideoLoadingProgressView();
    }

    public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            this.b.get(size).getVisitedHistory(valueCallback);
        }
    }

    @TargetApi(21)
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            if (this.b.get(size).onShowFileChooser(webView, valueCallback, fileChooserParams)) {
                return true;
            }
        }
        return false;
    }
}
