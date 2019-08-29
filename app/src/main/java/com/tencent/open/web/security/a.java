package com.tencent.open.web.security;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import com.tencent.open.a.f;

/* compiled from: ProGuard */
public class a extends InputConnectionWrapper {
    public static String a = null;
    public static boolean b = false;
    public static boolean c = false;

    public a(InputConnection inputConnection, boolean z) {
        super(inputConnection, z);
    }

    public boolean setComposingText(CharSequence charSequence, int i) {
        c = true;
        a = charSequence.toString();
        StringBuilder sb = new StringBuilder("-->setComposingText: ");
        sb.append(charSequence.toString());
        f.a("openSDK_LOG.CaptureInputConnection", sb.toString());
        return super.setComposingText(charSequence, i);
    }

    public boolean commitText(CharSequence charSequence, int i) {
        c = true;
        a = charSequence.toString();
        StringBuilder sb = new StringBuilder("-->commitText: ");
        sb.append(charSequence.toString());
        f.a("openSDK_LOG.CaptureInputConnection", sb.toString());
        return super.commitText(charSequence, i);
    }

    public boolean sendKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            f.c("openSDK_LOG.CaptureInputConnection", "sendKeyEvent");
            a = String.valueOf((char) keyEvent.getUnicodeChar());
            c = true;
            StringBuilder sb = new StringBuilder("s: ");
            sb.append(a);
            f.b("openSDK_LOG.CaptureInputConnection", sb.toString());
        }
        StringBuilder sb2 = new StringBuilder("-->sendKeyEvent: ");
        sb2.append(a);
        f.b("openSDK_LOG.CaptureInputConnection", sb2.toString());
        return super.sendKeyEvent(keyEvent);
    }
}
