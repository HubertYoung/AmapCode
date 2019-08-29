package com.xiaomi.push.service;

import com.xiaomi.smack.packet.a;
import com.xiaomi.smack.provider.b;
import com.xiaomi.smack.provider.c;
import com.xiaomi.smack.util.d;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

public class f implements b {
    public static a a(XmlPullParser xmlPullParser) {
        List list;
        String str;
        String[] strArr;
        String[] strArr2;
        if (xmlPullParser.getEventType() != 2) {
            return null;
        }
        String name = xmlPullParser.getName();
        String namespace = xmlPullParser.getNamespace();
        if (xmlPullParser.getAttributeCount() > 0) {
            String[] strArr3 = new String[xmlPullParser.getAttributeCount()];
            String[] strArr4 = new String[xmlPullParser.getAttributeCount()];
            for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                strArr3[i] = xmlPullParser.getAttributeName(i);
                strArr4[i] = d.b(xmlPullParser.getAttributeValue(i));
            }
            strArr2 = strArr3;
            str = null;
            list = null;
            strArr = strArr4;
        } else {
            strArr2 = null;
            strArr = null;
            str = null;
            list = null;
        }
        while (true) {
            int next = xmlPullParser.next();
            if (next == 3) {
                a aVar = new a(name, namespace, strArr2, strArr, str, list);
                return aVar;
            } else if (next == 4) {
                str = xmlPullParser.getText().trim();
            } else if (next == 2) {
                if (list == null) {
                    list = new ArrayList();
                }
                a a = a(xmlPullParser);
                if (a != null) {
                    list.add(a);
                }
            }
        }
    }

    public void a() {
        c.a().a("all", "xm:chat", this);
    }

    public a b(XmlPullParser xmlPullParser) {
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1 && eventType != 2) {
            eventType = xmlPullParser.next();
        }
        if (eventType == 2) {
            return a(xmlPullParser);
        }
        return null;
    }
}
