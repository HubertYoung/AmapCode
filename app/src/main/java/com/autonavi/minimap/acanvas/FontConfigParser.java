package com.autonavi.minimap.acanvas;

import android.os.Build.VERSION;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

class FontConfigParser {
    private static final String FALLBACK_FONT = "DroidSansFallback.ttf";
    private static final String FALLBACK_FONTS_FILE = "/system/etc/fallback_fonts.xml";
    private static final String SYSTEM_FONTS_FILE = "/system/etc/system_fonts.xml";
    private static final String SYSTEM_FONTS_FILE_LOLLIPOP = "/system/etc/fonts.xml";
    private static final String SYSTEM_FONT_LOCATION = "/system/fonts/";
    private static boolean mIsInitialized = false;
    private final String TAG = FontConfigParser.class.getSimpleName();
    private List<String> mFallbackFonts = null;
    private HashMap<List<String>, List<String>> mFontFamilies = null;

    public String getSystemFontLocation() {
        return SYSTEM_FONT_LOCATION;
    }

    public FontConfigParser() {
        if (!mIsInitialized) {
            readConfigFile();
            readFallbackConfigFile();
            mIsInitialized = true;
        }
    }

    public HashMap<List<String>, List<String>> getFontFamilies() {
        return this.mFontFamilies;
    }

    public List<String> getFallbackFontsList() {
        return this.mFallbackFonts;
    }

    public String getFallbackFont() {
        if (new File("/system/fonts/DroidSansFallbackBBK.ttf").exists()) {
            return "DroidSansFallbackBBK.ttf";
        }
        if (new File("/system/fonts/NotoSansHans-Regular.otf").exists()) {
            return "NotoSansHans-Regular.otf";
        }
        if (new File("/system/fonts/NotoSansSC-Regular.otf").exists()) {
            return "NotoSansSC-Regular.otf";
        }
        if (new File("/system/fonts/NotoSansCJK-Regular.ttc").exists()) {
            return "NotoSansCJK-Regular.ttc";
        }
        return new File("/system/fonts/DroidSansFallback.ttf").exists() ? FALLBACK_FONT : "DroidSans.ttf";
    }

    private void readFallbackConfigFile() {
        try {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            File file = new File(FALLBACK_FONTS_FILE);
            if (file.exists()) {
                parseFallbackXML(newDocumentBuilder.parse(file));
            }
        } catch (Exception e) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("readFallbackConfigFile ");
            sb.append(e.getMessage());
            ACanvasLog.e(str, sb.toString());
        }
    }

    private void readConfigFile() {
        Document document;
        try {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            if (VERSION.SDK_INT >= 21) {
                document = newDocumentBuilder.parse(new File(SYSTEM_FONTS_FILE_LOLLIPOP));
            } else {
                document = newDocumentBuilder.parse(new File(SYSTEM_FONTS_FILE));
            }
            parseXML(document);
        } catch (Exception e) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("readConfigFile exception:");
            sb.append(e.getMessage());
            ACanvasLog.e(str, sb.toString());
        }
    }

    private void parseXML(Document document) throws Exception {
        Node node;
        if (this.mFontFamilies == null) {
            this.mFontFamilies = new HashMap<>();
        } else {
            this.mFontFamilies.clear();
        }
        Element documentElement = document.getDocumentElement();
        if (!"familyset".equals(documentElement.getTagName())) {
            ACanvasLog.w(this.TAG, "Can't find familyset.");
        } else if (VERSION.SDK_INT >= 21) {
            NodeList elementsByTagName = documentElement.getElementsByTagName(OnNativeInvokeListener.ARG_FAMILIY);
            int length = elementsByTagName.getLength();
            for (int i = 0; i < length; i++) {
                Node item = elementsByTagName.item(i);
                Node namedItem = item.getAttributes().getNamedItem("name");
                if (namedItem == null) {
                    break;
                }
                NodeList elementsByTagName2 = ((Element) item).getElementsByTagName("font");
                if (elementsByTagName2 == null) {
                    ACanvasLog.w(this.TAG, "nameset or fileset is invalid.");
                    return;
                }
                int length2 = elementsByTagName2.getLength();
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(namedItem.getNodeValue());
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < length2; i2++) {
                    Node item2 = elementsByTagName2.item(i2);
                    if (item2 instanceof Element) {
                        arrayList2.add(((Element) item2).getTextContent());
                    }
                }
                NodeList elementsByTagName3 = documentElement.getElementsByTagName("alias");
                int length3 = elementsByTagName3.getLength();
                for (int i3 = 0; i3 < length3; i3++) {
                    Node item3 = elementsByTagName3.item(i3);
                    Node namedItem2 = item3.getAttributes().getNamedItem("name");
                    Node namedItem3 = item3.getAttributes().getNamedItem("to");
                    if (namedItem2 == null || namedItem3 == null) {
                        break;
                    }
                    ArrayList arrayList3 = new ArrayList();
                    for (String equals : arrayList) {
                        if (equals.equals(namedItem3.getNodeValue())) {
                            arrayList3.add(namedItem2.getNodeValue());
                        }
                    }
                    arrayList.addAll(arrayList3);
                }
                this.mFontFamilies.put(arrayList, arrayList2);
            }
        } else {
            NodeList elementsByTagName4 = documentElement.getElementsByTagName(OnNativeInvokeListener.ARG_FAMILIY);
            int length4 = elementsByTagName4.getLength();
            for (int i4 = 0; i4 < length4; i4++) {
                Node item4 = elementsByTagName4.item(i4);
                Node node2 = null;
                if (item4 instanceof Element) {
                    Element element = (Element) item4;
                    NodeList elementsByTagName5 = element.getElementsByTagName("nameset");
                    NodeList elementsByTagName6 = element.getElementsByTagName("fileset");
                    if (elementsByTagName5 == null || elementsByTagName6 == null || elementsByTagName5.getLength() != 1 || elementsByTagName6.getLength() != 1) {
                        ACanvasLog.w(this.TAG, "nameset or fileset node doesn't exist.");
                        return;
                    } else {
                        node2 = elementsByTagName5.item(0);
                        node = elementsByTagName6.item(0);
                    }
                } else {
                    node = null;
                }
                if (node2 == null || node == null) {
                    ACanvasLog.w(this.TAG, "nameset or fileset is invalid.");
                    return;
                }
                NodeList childNodes = node2.getChildNodes();
                NodeList childNodes2 = node.getChildNodes();
                if (childNodes == null || childNodes2 == null) {
                    ACanvasLog.w(this.TAG, "nameset or fileset is empty.");
                    return;
                }
                int length5 = childNodes.getLength();
                int length6 = childNodes2.getLength();
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                for (int i5 = 0; i5 < length6; i5++) {
                    Node item5 = childNodes2.item(i5);
                    if (item5 instanceof Element) {
                        Element element2 = (Element) item5;
                        if ("file".equals(element2.getTagName())) {
                            arrayList5.add(element2.getTextContent());
                        }
                    }
                }
                for (int i6 = 0; i6 < length5; i6++) {
                    Node item6 = childNodes.item(i6);
                    if (item6 instanceof Element) {
                        Element element3 = (Element) item6;
                        if ("name".equals(element3.getTagName())) {
                            arrayList4.add(element3.getTextContent());
                        }
                    }
                }
                this.mFontFamilies.put(arrayList4, arrayList5);
            }
        }
    }

    private void parseFallbackXML(Document document) throws Exception {
        if (this.mFallbackFonts == null) {
            this.mFallbackFonts = new ArrayList();
        } else {
            this.mFallbackFonts.clear();
        }
        Element documentElement = document.getDocumentElement();
        if (!"familyset".equals(documentElement.getTagName())) {
            ACanvasLog.w(this.TAG, "Can't find familyset.");
            return;
        }
        NodeList elementsByTagName = documentElement.getElementsByTagName(OnNativeInvokeListener.ARG_FAMILIY);
        int length = elementsByTagName.getLength();
        for (int i = 0; i < length; i++) {
            Node item = elementsByTagName.item(i);
            Node node = null;
            if (item instanceof Element) {
                NodeList elementsByTagName2 = ((Element) item).getElementsByTagName("fileset");
                if (elementsByTagName2 == null || elementsByTagName2.getLength() != 1) {
                    ACanvasLog.w(this.TAG, "nameset or fileset node doesn't exist.");
                    return;
                }
                node = elementsByTagName2.item(0);
            }
            if (node == null) {
                ACanvasLog.w(this.TAG, "nameset or fileset is invalid.");
                return;
            }
            NodeList childNodes = node.getChildNodes();
            if (childNodes == null) {
                ACanvasLog.w(this.TAG, "nameset or fileset is empty.");
                return;
            }
            int length2 = childNodes.getLength();
            for (int i2 = 0; i2 < length2; i2++) {
                Node item2 = childNodes.item(i2);
                if (item2 instanceof Element) {
                    Element element = (Element) item2;
                    if ("file".equals(element.getTagName())) {
                        this.mFallbackFonts.add(element.getTextContent());
                    }
                }
            }
        }
    }
}
