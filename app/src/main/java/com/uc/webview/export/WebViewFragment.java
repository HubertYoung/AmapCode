package com.uc.webview.export;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.uc.webview.export.annotations.Api;

@Api
/* compiled from: ProGuard */
public class WebViewFragment extends Fragment {
    private WebView a;
    private boolean b;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.a != null) {
            this.a.destroy();
        }
        this.a = new WebView(getActivity());
        this.b = true;
        return this.a;
    }

    public void onPause() {
        super.onPause();
        this.a.onPause();
    }

    public void onResume() {
        this.a.onResume();
        super.onResume();
    }

    public void onDestroyView() {
        this.b = false;
        super.onDestroyView();
    }

    public void onDestroy() {
        if (this.a != null) {
            this.a.destroy();
            this.a = null;
        }
        super.onDestroy();
    }

    public WebView getWebView() {
        if (this.b) {
            return this.a;
        }
        return null;
    }
}
