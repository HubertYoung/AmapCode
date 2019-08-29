package com.xiaomi.slim;

import com.alipay.mobile.antui.basic.AUButton;
import com.xiaomi.smack.a;
import com.xiaomi.smack.l;
import com.xiaomi.smack.packet.d;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class e {
    private XmlPullParser a;

    e() {
        try {
            this.a = XmlPullParserFactory.newInstance().newPullParser();
            this.a.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        } catch (XmlPullParserException unused) {
        }
    }

    /* access modifiers changed from: 0000 */
    public d a(byte[] bArr, a aVar) {
        this.a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
        this.a.next();
        int eventType = this.a.getEventType();
        String name = this.a.getName();
        if (eventType == 2) {
            if (name.equals("message")) {
                return com.xiaomi.smack.util.a.a(this.a);
            }
            if (name.equals("iq")) {
                return com.xiaomi.smack.util.a.a(this.a, aVar);
            }
            if (name.equals("presence")) {
                return com.xiaomi.smack.util.a.b(this.a);
            }
            if (!this.a.getName().equals("stream")) {
                if (this.a.getName().equals("error")) {
                    throw new l(com.xiaomi.smack.util.a.c(this.a));
                } else if (this.a.getName().equals(AUButton.BTN_TYPE_WARNING)) {
                    this.a.next();
                    boolean equals = this.a.getName().equals("multi-login");
                } else {
                    this.a.getName().equals("bind");
                }
            }
        }
        return null;
    }
}
