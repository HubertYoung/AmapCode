package com.alipay.mobile.framework.util.xml;

import com.alipay.mobile.framework.MicroDescription;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.msg.BroadcastReceiverDescription;
import com.alipay.mobile.framework.pipeline.ValveDescription;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.StreamUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MetaInfoXmlParser {
    public static String DDD = "http://apache.org/xml/features/disallow-doctype-decl";
    public static final String KEY_APPLICATION = "application";
    public static final String KEY_APPLICATION_APP_ID = "appId";
    public static final String KEY_APPLICATION_ENGINE_TYPE = "engineTyp";
    public static final String KEY_BROADCAST_RECEIVER = "broadcastReceiver";
    public static final String KEY_BROADCAST_RECEIVER_ACTION = "action";
    public static final String KEY_CLASS_NAME = "className";
    public static final String KEY_META_INFO = "metainfo";
    public static final String KEY_SERVICE = "service";
    public static final String KEY_SERVICE_INTERFACE_NAME = "interfaceName";
    public static final String KEY_SERVICE_IS_LAZY = "isLazy";
    public static final String KEY_VALVE = "valve";
    public static final String KEY_VALVE_PIPELINE_NAME = "pipelineName";
    public static final String KEY_VALVE_THREAD_NAME = "threadName";
    public static final String KEY_VALVE_WEIGHT = "weight";
    public static final String META_INFO_XML = "metainfo.xml";
    public static final String TAG = "MetaInfoXmlReader";
    /* access modifiers changed from: private */
    public int a = -1;

    private class MetaInfoContentHandler implements ContentHandler {
        ArrayList<String> actions;
        StringBuilder buff;
        MicroDescription<?> desc;
        List<MicroDescription<?>> list;

        private MetaInfoContentHandler() {
        }

        public List<MicroDescription<?>> getList() {
            return this.list;
        }

        public void setDocumentLocator(Locator locator) {
            Log.i((String) MetaInfoXmlParser.TAG, (String) "setDocumentLocator");
        }

        public void startDocument() {
            Log.i((String) MetaInfoXmlParser.TAG, (String) "startDocument");
        }

        public void endDocument() {
            Log.i((String) MetaInfoXmlParser.TAG, (String) "endDocument");
        }

        public void startPrefixMapping(String prefix, String uri) {
            Log.i((String) MetaInfoXmlParser.TAG, "startPrefixMapping:prefix=" + prefix + ",uri=" + uri);
        }

        public void endPrefixMapping(String prefix) {
            Log.i((String) MetaInfoXmlParser.TAG, "endPrefixMapping:" + prefix);
        }

        public void startElement(String uri, String localName, String qName, Attributes atts) {
            Log.v((String) MetaInfoXmlParser.TAG, String.format("startElement(uri=%s, localName=%s, qName=%s, atts=%s)", new Object[]{uri, localName, qName, atts}));
            if (MetaInfoXmlParser.KEY_META_INFO.equals(qName)) {
                this.list = new ArrayList();
                this.buff = new StringBuilder();
            } else if ("className".equals(qName)) {
                MetaInfoXmlParser.this.a = 1;
            } else if (MetaInfoXmlParser.KEY_APPLICATION.equals(qName)) {
                this.desc = new ApplicationDescription();
                this.list.add(this.desc);
            } else if ("appId".equals(qName)) {
                MetaInfoXmlParser.this.a = 11;
            } else if (MetaInfoXmlParser.KEY_APPLICATION_ENGINE_TYPE.equals(qName)) {
                MetaInfoXmlParser.this.a = 12;
            } else if ("service".equals(qName)) {
                this.desc = new ServiceDescription();
                this.list.add(this.desc);
            } else if (MetaInfoXmlParser.KEY_SERVICE_INTERFACE_NAME.equals(qName)) {
                MetaInfoXmlParser.this.a = 21;
            } else if (MetaInfoXmlParser.KEY_SERVICE_IS_LAZY.equals(qName)) {
                MetaInfoXmlParser.this.a = 22;
            } else if (MetaInfoXmlParser.KEY_BROADCAST_RECEIVER.equals(qName)) {
                this.desc = new BroadcastReceiverDescription();
                this.list.add(this.desc);
                if (this.actions == null) {
                    this.actions = new ArrayList<>();
                } else {
                    this.actions.clear();
                }
            } else if ("action".equals(qName)) {
                MetaInfoXmlParser.this.a = 31;
            } else if (MetaInfoXmlParser.KEY_VALVE.equals(qName)) {
                this.desc = new ValveDescription();
                this.list.add(this.desc);
            } else if (MetaInfoXmlParser.KEY_VALVE_PIPELINE_NAME.equals(qName)) {
                MetaInfoXmlParser.this.a = 41;
            } else if (MetaInfoXmlParser.KEY_VALVE_THREAD_NAME.equals(qName)) {
                MetaInfoXmlParser.this.a = 42;
            } else if (MetaInfoXmlParser.KEY_VALVE_WEIGHT.equals(qName)) {
                MetaInfoXmlParser.this.a = 43;
            }
        }

        public void endElement(String uri, String localName, String qName) {
            Log.v((String) MetaInfoXmlParser.TAG, String.format("endElement(uri=%s, localName=%s, qName=%s)", new Object[]{uri, localName, qName}));
            if (MetaInfoXmlParser.KEY_BROADCAST_RECEIVER.equals(qName)) {
                ((BroadcastReceiverDescription) this.desc).setMsgCode((String[]) this.actions.toArray(new String[this.actions.size()]));
            }
            MetaInfoXmlParser.this.a = -1;
            if (this.buff.length() > 0) {
                this.buff.delete(0, this.buff.length());
            }
        }

        public void characters(char[] ch, int start, int length) {
            Log.i((String) MetaInfoXmlParser.TAG, "characters:" + new String(ch, start, length));
            this.buff.append(ch, start, length);
            String string = this.buff.toString().trim();
            switch (MetaInfoXmlParser.this.a) {
                case 1:
                    this.desc.setClassName(string);
                    return;
                case 11:
                    ((ApplicationDescription) this.desc).setAppId(string);
                    return;
                case 12:
                    ((ApplicationDescription) this.desc).setEngineType(string);
                    return;
                case 21:
                    ((ServiceDescription) this.desc).setInterfaceClass(string);
                    return;
                case 22:
                    ((ServiceDescription) this.desc).setLazy(Boolean.parseBoolean(string));
                    return;
                case 31:
                    this.actions.add(string);
                    return;
                case 41:
                    ((ValveDescription) this.desc).setPipelineName(string);
                    return;
                case 42:
                    ((ValveDescription) this.desc).setThreadName(string);
                    return;
                case 43:
                    ((ValveDescription) this.desc).setWeight(Integer.parseInt(string));
                    return;
                default:
                    return;
            }
        }

        public void ignorableWhitespace(char[] ch, int start, int length) {
            Log.i((String) MetaInfoXmlParser.TAG, "ignorableWhitespace:" + new String(ch, start, length));
        }

        public void processingInstruction(String target, String data) {
            Log.i((String) MetaInfoXmlParser.TAG, "processingInstruction:target=" + target + ",data=" + data);
        }

        public void skippedEntity(String name) {
            Log.i((String) MetaInfoXmlParser.TAG, "skippedEntity:" + name);
        }
    }

    private class MetaInfoErrorHandler implements ErrorHandler {
        private MetaInfoErrorHandler() {
        }

        public void warning(SAXParseException exception) {
            Log.w((String) MetaInfoXmlParser.TAG, (Throwable) exception);
        }

        public void error(SAXParseException exception) {
            Log.w((String) MetaInfoXmlParser.TAG, (Throwable) exception);
        }

        public void fatalError(SAXParseException exception) {
            Log.w((String) MetaInfoXmlParser.TAG, (Throwable) exception);
        }
    }

    private List<MicroDescription<?>> a(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            MetaInfoContentHandler contentHandler = new MetaInfoContentHandler();
            reader.setContentHandler(contentHandler);
            reader.setErrorHandler(new MetaInfoErrorHandler());
            reader.parse(new InputSource(is));
            return contentHandler.getList();
        } catch (SAXException e) {
            throw new IOException("Failed to parse xml.", e);
        }
    }

    public List<MicroDescription<?>> readMetaInfo(InputStream is) {
        if (is == null) {
            return null;
        }
        List<MicroDescription<?>> a2 = a(is);
        StreamUtil.closeSafely(is);
        return a2;
    }

    public List<MicroDescription<?>> readMetaInfo(File file) {
        return readMetaInfo((InputStream) new FileInputStream(file));
    }
}
