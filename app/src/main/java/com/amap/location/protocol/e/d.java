package com.amap.location.protocol.e;

import android.text.TextUtils;
import com.amap.location.common.f.c;
import com.amap.location.common.f.g;
import com.amap.location.common.model.AmapLoc;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: ResponseParser */
public class d {

    /* compiled from: ResponseParser */
    static class a extends DefaultHandler {
        public AmapLoc a;
        private String b;

        private a() {
            this.a = new AmapLoc();
            this.b = "";
        }

        public void characters(char[] cArr, int i, int i2) {
            this.b = String.valueOf(cArr, i, i2);
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) {
            this.b = "";
        }

        public void endElement(String str, String str2, String str3) {
            if (str2.equals("retype")) {
                this.a.setRetype(this.b);
            } else if (str2.equals("err")) {
                if (!TextUtils.isEmpty(this.b)) {
                    this.a.setServerError(Integer.parseInt(this.b));
                }
            } else if (str2.equals("rdesc")) {
                this.a.setRdesc(this.b);
            } else if (str2.equals(AutoJsonUtils.JSON_ADCODE)) {
                this.a.setAdcode(this.b);
            } else if (str2.equals("citycode")) {
                this.a.setCitycode(this.b);
            } else if (str2.equals("radius")) {
                try {
                    this.a.setAccuracy(Float.parseFloat(this.b));
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                    this.a.setAccuracy(3891.0f);
                    this.a.setIsError(true);
                }
            } else if (str2.equals("cenx")) {
                try {
                    this.a.setLon(Double.parseDouble(this.b));
                } catch (Exception e2) {
                    com.amap.location.common.d.a.a((Throwable) e2);
                    this.a.setLon(0.0d);
                    this.a.setIsError(true);
                }
            } else if (str2.equals("ceny")) {
                try {
                    this.a.setLat(Double.parseDouble(this.b));
                } catch (Exception e3) {
                    com.amap.location.common.d.a.a((Throwable) e3);
                    this.a.setLat(0.0d);
                    this.a.setIsError(true);
                }
            } else if (str2.equals("desc")) {
                this.a.setDesc(this.b);
            } else if (str2.equals("country")) {
                this.a.setCountry(this.b);
            } else if (str2.equals("province")) {
                this.a.setProvince(this.b);
            } else if (str2.equals("city")) {
                this.a.setCity(this.b);
            } else if (str2.equals("district")) {
                this.a.setDistrict(this.b);
            } else if (str2.equals("road")) {
                this.a.setRoad(this.b);
            } else if (str2.equals("street")) {
                this.a.setStreet(this.b);
            } else if (str2.equals("number")) {
                this.a.setNumber(this.b);
            } else if (str2.equals("aoiname")) {
                this.a.setAoiname(this.b);
            } else if (str2.equals("poiname")) {
                this.a.setPoiname(this.b);
            } else if (str2.equals("BIZ")) {
                if (this.a.getExtra() == null) {
                    this.a.setExtra(new JSONObject());
                }
                try {
                    this.a.getExtra().put("BIZ", this.b);
                } catch (Exception e4) {
                    com.amap.location.common.d.a.a((Throwable) e4);
                }
            } else if (str2.equals("cens")) {
                this.a.setCens(this.b);
            } else if (str2.equals("pid")) {
                this.a.setPoiid(this.b);
            } else if (str2.equals("flr")) {
                this.a.setFloor(this.b);
            } else if (str2.equals("coord")) {
                if (TextUtils.isEmpty(a.a)) {
                    a.a = this.b;
                }
                this.a.setCoord(this.b);
            } else if (str2.equals("mcell")) {
                this.a.setMcell(this.b);
            } else if (!str2.equals("gkeyloc") && !str2.equals("gkeygeo")) {
                if (str2.equals("ctx")) {
                    AmapLoc.sCxtFromServer = this.b;
                } else if (str2.equals("con_scenario")) {
                    int i = -1;
                    try {
                        i = Integer.parseInt(this.b);
                    } catch (Throwable unused) {
                    }
                    this.a.setScenarioConfidence(i);
                } else if (str2.equals("resubtype")) {
                    this.a.setSubType(this.b);
                }
            }
            if (this.a.getExtra() == null) {
                this.a.setExtra(new JSONObject());
            }
            try {
                if (str2.equals("eab")) {
                    this.a.getExtra().put(str2, this.b);
                } else if (str2.equals("ctl")) {
                    this.a.getExtra().put(str2, this.b);
                } else if (str2.equals("suc")) {
                    this.a.getExtra().put(str2, this.b);
                } else {
                    if (str2.equals("spa")) {
                        this.a.getExtra().put(str2, this.b);
                    }
                }
            } catch (Exception e5) {
                com.amap.location.common.d.a.a((Throwable) e5);
            }
        }
    }

    public static AmapLoc a(String str) {
        ByteArrayInputStream byteArrayInputStream = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!str.contains("SuccessCode")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.reverse();
            str = c.b(sb.toString());
        }
        if (TextUtils.isEmpty(str)) {
            com.amap.location.common.d.a.c("resparser", "data error");
            return null;
        }
        if (str.contains("SuccessCode=\"0\"")) {
            com.amap.location.common.d.a.c("resparser", "no result");
        }
        SAXParserFactory newInstance = SAXParserFactory.newInstance();
        a aVar = new a();
        try {
            newInstance.setFeature("http://xml.org/sax/features/external-general-entities", false);
            newInstance.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(str.getBytes("UTF-8"));
            try {
                newInstance.newSAXParser().parse(byteArrayInputStream2, aVar);
                byteArrayInputStream2.close();
                aVar.a.setProvider("network");
                aVar.a.setTime(System.currentTimeMillis());
                g.a((Closeable) byteArrayInputStream2);
            } catch (Exception e) {
                e = e;
                byteArrayInputStream = byteArrayInputStream2;
                try {
                    com.amap.location.common.d.a.b("resparser", "error pase:".concat(String.valueOf(str)), e);
                    g.a((Closeable) byteArrayInputStream);
                    return aVar.a;
                } catch (Throwable th) {
                    th = th;
                    g.a((Closeable) byteArrayInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                byteArrayInputStream = byteArrayInputStream2;
                g.a((Closeable) byteArrayInputStream);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            com.amap.location.common.d.a.b("resparser", "error pase:".concat(String.valueOf(str)), e);
            g.a((Closeable) byteArrayInputStream);
            return aVar.a;
        }
        return aVar.a;
    }
}
