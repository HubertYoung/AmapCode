package com.alipay.android.phone.inside.framework.plugin;

import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.util.AssetsStorage;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.mobile.framework.util.xml.MetaInfoXmlParser;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class PluginProvider {
    private List<PluginDesc> a;

    PluginProvider() {
    }

    public final List<PluginDesc> a() {
        if (this.a == null) {
            this.a = b();
        }
        return this.a;
    }

    private static List<PluginDesc> b() {
        ArrayList arrayList = new ArrayList();
        if (LauncherApplication.a() == null) {
            return arrayList;
        }
        try {
            String a2 = AssetsStorage.a(MetaInfoXmlParser.META_INFO_XML, LauncherApplication.a().getResources().getAssets());
            LoggerFactory.f().b((String) "inside", a2);
            NodeList childNodes = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(a2.getBytes())).getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item.getNodeType() == 1 && TextUtils.equals(item.getNodeName(), "plugins")) {
                    a(item, arrayList);
                }
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return arrayList;
    }

    private static void a(Node node, List<PluginDesc> list) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == 1 && TextUtils.equals(item.getNodeName(), "plugin")) {
                Element element = (Element) item;
                String attribute = element.getAttribute("key");
                String attribute2 = element.getAttribute("value");
                if (!TextUtils.isEmpty(attribute) && !TextUtils.isEmpty(attribute2)) {
                    list.add(new PluginDesc(attribute, attribute2));
                }
            }
        }
    }
}
