package com.xiaomi.smack.util;

import android.text.TextUtils;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.autonavi.minimap.ajx3.util.Constants;
import com.xiaomi.push.service.aq;
import com.xiaomi.push.service.ax;
import com.xiaomi.push.service.f;
import com.xiaomi.smack.l;
import com.xiaomi.smack.packet.b;
import com.xiaomi.smack.packet.d;
import com.xiaomi.smack.packet.g;
import com.xiaomi.smack.packet.h;
import com.xiaomi.smack.provider.c;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class a {
    private static XmlPullParser a;

    public static com.xiaomi.smack.packet.a a(String str, String str2, XmlPullParser xmlPullParser) {
        Object a2 = c.a().a("all", "xm:chat");
        if (a2 == null || !(a2 instanceof f)) {
            return null;
        }
        return ((f) a2).b(xmlPullParser);
    }

    public static b a(XmlPullParser xmlPullParser, com.xiaomi.smack.a aVar) {
        String attributeValue = xmlPullParser.getAttributeValue("", "id");
        String attributeValue2 = xmlPullParser.getAttributeValue("", "to");
        String attributeValue3 = xmlPullParser.getAttributeValue("", "from");
        String attributeValue4 = xmlPullParser.getAttributeValue("", "chid");
        com.xiaomi.smack.packet.b.a a2 = com.xiaomi.smack.packet.b.a.a(xmlPullParser.getAttributeValue("", "type"));
        HashMap hashMap = new HashMap();
        boolean z = false;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            hashMap.put(attributeName, xmlPullParser.getAttributeValue("", attributeName));
        }
        b bVar = null;
        h hVar = null;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("error")) {
                    hVar = d(xmlPullParser);
                } else {
                    bVar = new b();
                    bVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("iq")) {
                z = true;
            }
        }
        if (bVar == null) {
            if (com.xiaomi.smack.packet.b.a.a == a2 || com.xiaomi.smack.packet.b.a.b == a2) {
                b bVar2 = new b();
                bVar2.k(attributeValue);
                bVar2.m(attributeValue3);
                bVar2.n(attributeValue2);
                bVar2.a(com.xiaomi.smack.packet.b.a.d);
                bVar2.l(attributeValue4);
                bVar2.a(new h(com.xiaomi.smack.packet.h.a.e));
                aVar.a((d) bVar2);
                com.xiaomi.channel.commonutils.logger.b.d("iq usage error. send packet in packet parser.");
                return null;
            }
            bVar = new c();
        }
        bVar.k(attributeValue);
        bVar.m(attributeValue2);
        bVar.l(attributeValue4);
        bVar.n(attributeValue3);
        bVar.a(a2);
        bVar.a(hVar);
        bVar.a((Map<String, String>) hashMap);
        return bVar;
    }

    public static d a(XmlPullParser xmlPullParser) {
        String str;
        boolean z = false;
        String str2 = null;
        if ("1".equals(xmlPullParser.getAttributeValue("", "s"))) {
            String attributeValue = xmlPullParser.getAttributeValue("", "chid");
            String attributeValue2 = xmlPullParser.getAttributeValue("", "id");
            String attributeValue3 = xmlPullParser.getAttributeValue("", "from");
            String attributeValue4 = xmlPullParser.getAttributeValue("", "to");
            String attributeValue5 = xmlPullParser.getAttributeValue("", "type");
            aq.b b = aq.a().b(attributeValue, attributeValue4);
            if (b == null) {
                b = aq.a().b(attributeValue, attributeValue3);
            }
            if (b == null) {
                throw new l((String) "the channel id is wrong while receiving a encrypted message");
            }
            d dVar = null;
            while (!z) {
                int next = xmlPullParser.next();
                if (next == 2) {
                    if (!"s".equals(xmlPullParser.getName())) {
                        throw new l((String) "error while receiving a encrypted message with wrong format");
                    } else if (xmlPullParser.next() != 4) {
                        throw new l((String) "error while receiving a encrypted message with wrong format");
                    } else {
                        String text = xmlPullParser.getText();
                        if ("5".equals(attributeValue) || "6".equals(attributeValue)) {
                            com.xiaomi.smack.packet.c cVar = new com.xiaomi.smack.packet.c();
                            cVar.l(attributeValue);
                            cVar.b(true);
                            cVar.n(attributeValue3);
                            cVar.m(attributeValue4);
                            cVar.k(attributeValue2);
                            cVar.f(attributeValue5);
                            com.xiaomi.smack.packet.a aVar = new com.xiaomi.smack.packet.a("s", null, null, null);
                            aVar.b(text);
                            cVar.a(aVar);
                            return cVar;
                        }
                        a(ax.a(ax.a(b.i, attributeValue2), text));
                        a.next();
                        dVar = a(a);
                    }
                } else if (next == 3 && xmlPullParser.getName().equals("message")) {
                    z = true;
                }
            }
            if (dVar != null) {
                return dVar;
            }
            throw new l((String) "error while receiving a encrypted message with wrong format");
        }
        com.xiaomi.smack.packet.c cVar2 = new com.xiaomi.smack.packet.c();
        String attributeValue6 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue6 == null) {
            attributeValue6 = "ID_NOT_AVAILABLE";
        }
        cVar2.k(attributeValue6);
        cVar2.m(xmlPullParser.getAttributeValue("", "to"));
        cVar2.n(xmlPullParser.getAttributeValue("", "from"));
        cVar2.l(xmlPullParser.getAttributeValue("", "chid"));
        cVar2.a(xmlPullParser.getAttributeValue("", "appid"));
        try {
            str = xmlPullParser.getAttributeValue("", "transient");
        } catch (Exception unused) {
            str = null;
        }
        try {
            String attributeValue7 = xmlPullParser.getAttributeValue("", "seq");
            if (!TextUtils.isEmpty(attributeValue7)) {
                cVar2.b(attributeValue7);
            }
        } catch (Exception unused2) {
        }
        try {
            String attributeValue8 = xmlPullParser.getAttributeValue("", "mseq");
            if (!TextUtils.isEmpty(attributeValue8)) {
                cVar2.c(attributeValue8);
            }
        } catch (Exception unused3) {
        }
        try {
            String attributeValue9 = xmlPullParser.getAttributeValue("", "fseq");
            if (!TextUtils.isEmpty(attributeValue9)) {
                cVar2.d(attributeValue9);
            }
        } catch (Exception unused4) {
        }
        try {
            String attributeValue10 = xmlPullParser.getAttributeValue("", "status");
            if (!TextUtils.isEmpty(attributeValue10)) {
                cVar2.e(attributeValue10);
            }
        } catch (Exception unused5) {
        }
        cVar2.a(!TextUtils.isEmpty(str) && str.equalsIgnoreCase("true"));
        cVar2.f(xmlPullParser.getAttributeValue("", "type"));
        String f = f(xmlPullParser);
        if (f == null || "".equals(f.trim())) {
            d.u();
        } else {
            cVar2.j(f);
        }
        while (!z) {
            int next2 = xmlPullParser.next();
            if (next2 == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (TextUtils.isEmpty(namespace)) {
                    namespace = "xm";
                }
                if (name.equals("subject")) {
                    f(xmlPullParser);
                    cVar2.g(e(xmlPullParser));
                } else if (name.equals(Constants.BODY)) {
                    String attributeValue11 = xmlPullParser.getAttributeValue("", "encode");
                    String e = e(xmlPullParser);
                    if (!TextUtils.isEmpty(attributeValue11)) {
                        cVar2.a(e, attributeValue11);
                    } else {
                        cVar2.h(e);
                    }
                } else if (name.equals("thread")) {
                    if (str2 == null) {
                        str2 = xmlPullParser.nextText();
                    }
                } else if (name.equals("error")) {
                    cVar2.a(d(xmlPullParser));
                } else {
                    cVar2.a(a(name, namespace, xmlPullParser));
                }
            } else if (next2 == 3 && xmlPullParser.getName().equals("message")) {
                z = true;
            }
        }
        cVar2.i(str2);
        return cVar2;
    }

    private static void a(byte[] bArr) {
        if (a == null) {
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                a = newPullParser;
                newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
    }

    public static com.xiaomi.smack.packet.f b(XmlPullParser xmlPullParser) {
        com.xiaomi.smack.packet.f.b bVar = com.xiaomi.smack.packet.f.b.available;
        String attributeValue = xmlPullParser.getAttributeValue("", "type");
        if (attributeValue != null && !attributeValue.equals("")) {
            try {
                bVar = com.xiaomi.smack.packet.f.b.valueOf(attributeValue);
            } catch (IllegalArgumentException unused) {
                PrintStream printStream = System.err;
            }
        }
        com.xiaomi.smack.packet.f fVar = new com.xiaomi.smack.packet.f(bVar);
        fVar.m(xmlPullParser.getAttributeValue("", "to"));
        fVar.n(xmlPullParser.getAttributeValue("", "from"));
        fVar.l(xmlPullParser.getAttributeValue("", "chid"));
        String attributeValue2 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue2 == null) {
            attributeValue2 = "ID_NOT_AVAILABLE";
        }
        fVar.k(attributeValue2);
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("status")) {
                    fVar.a(xmlPullParser.nextText());
                } else if (name.equals("priority")) {
                    try {
                        fVar.a(Integer.parseInt(xmlPullParser.nextText()));
                    } catch (NumberFormatException unused2) {
                    } catch (IllegalArgumentException unused3) {
                        fVar.a(0);
                    }
                } else if (name.equals(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW)) {
                    try {
                        fVar.a(com.xiaomi.smack.packet.f.a.valueOf(xmlPullParser.nextText()));
                    } catch (IllegalArgumentException unused4) {
                        PrintStream printStream2 = System.err;
                    }
                } else if (name.equals("error")) {
                    fVar.a(d(xmlPullParser));
                } else {
                    fVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("presence")) {
                z = true;
            }
        }
        return fVar;
    }

    public static g c(XmlPullParser xmlPullParser) {
        g gVar = null;
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                gVar = new g(xmlPullParser.getName());
            } else if (next == 3 && xmlPullParser.getName().equals("error")) {
                z = true;
            }
        }
        return gVar;
    }

    public static h d(XmlPullParser xmlPullParser) {
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        String str = "-1";
        String str2 = null;
        String str3 = null;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).equals("code")) {
                str = xmlPullParser.getAttributeValue("", "code");
            }
            if (xmlPullParser.getAttributeName(i).equals("type")) {
                str2 = xmlPullParser.getAttributeValue("", "type");
            }
            if (xmlPullParser.getAttributeName(i).equals("reason")) {
                str3 = xmlPullParser.getAttributeValue("", "reason");
            }
        }
        String str4 = null;
        String str5 = null;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (xmlPullParser.getName().equals("text")) {
                    str5 = xmlPullParser.nextText();
                } else {
                    String name = xmlPullParser.getName();
                    String namespace = xmlPullParser.getNamespace();
                    if ("urn:ietf:params:xml:ns:xmpp-stanzas".equals(namespace)) {
                        str4 = name;
                    } else {
                        arrayList.add(a(name, namespace, xmlPullParser));
                    }
                }
            } else if (next == 3) {
                if (xmlPullParser.getName().equals("error")) {
                    z = true;
                }
            } else if (next == 4) {
                str5 = xmlPullParser.getText();
            }
        }
        h hVar = new h(Integer.parseInt(str), str2 == null ? "cancel" : str2, str3, str4, str5, arrayList);
        return hVar;
    }

    private static String e(XmlPullParser xmlPullParser) {
        String str = "";
        int depth = xmlPullParser.getDepth();
        while (true) {
            if (xmlPullParser.next() == 3 && xmlPullParser.getDepth() == depth) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(xmlPullParser.getText());
            str = sb.toString();
        }
    }

    private static String f(XmlPullParser xmlPullParser) {
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            if ("xml:lang".equals(attributeName) || ("lang".equals(attributeName) && "xml".equals(xmlPullParser.getAttributePrefix(i)))) {
                return xmlPullParser.getAttributeValue(i);
            }
        }
        return null;
    }
}
