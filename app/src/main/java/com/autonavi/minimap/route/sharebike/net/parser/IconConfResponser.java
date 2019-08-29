package com.autonavi.minimap.route.sharebike.net.parser;

import com.autonavi.minimap.route.sharebike.model.IconConf;
import com.autonavi.minimap.route.sharebike.model.IconConf.a.C0053a;
import com.autonavi.minimap.route.sharebike.model.IconConf.a.C0053a.C0054a;
import com.autonavi.minimap.route.sharebike.model.IconConf.a.b;
import com.autonavi.minimap.route.sharebike.model.IconConf.a.c;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IconConfResponser extends BaseResponser {
    public IconConfResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        if (bArr != null && bArr.length != 0) {
            parseHeader(bArr);
            IconConf iconConf = new IconConf();
            IconConf.a aVar = new IconConf.a();
            ArrayList arrayList = new ArrayList();
            iconConf.result = this.result;
            iconConf.errorCode = this.errorCode;
            if (this.result && this.errorCode == 1) {
                JSONObject optJSONObject = new JSONObject(new String(bArr)).optJSONObject("data");
                if (optJSONObject != null) {
                    JSONArray optJSONArray = optJSONObject.optJSONArray("bike");
                    if (optJSONArray != null) {
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                            if (optJSONObject2 != null) {
                                JSONObject optJSONObject3 = optJSONObject2.optJSONObject("url");
                                C0054a aVar2 = new C0054a();
                                if (optJSONObject3 != null) {
                                    aVar2.b = optJSONObject3.optString("normal");
                                    aVar2.a = optJSONObject3.optString("select");
                                }
                                C0053a aVar3 = new C0053a();
                                aVar3.a = aVar2;
                                aVar3.b = optJSONObject2.optString("type");
                                arrayList.add(aVar3);
                            }
                        }
                        aVar.a = arrayList;
                        iconConf.setData(aVar);
                    }
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("cp");
                    ArrayList arrayList2 = new ArrayList();
                    if (optJSONArray2 != null) {
                        for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                            JSONObject optJSONObject4 = optJSONArray2.optJSONObject(i2);
                            b bVar = new b();
                            if (optJSONObject4 != null) {
                                bVar.a = optJSONObject4.optString("url");
                                bVar.b = optJSONObject4.optString("type");
                                if (optJSONObject4.optString("url_big") != null) {
                                    bVar.c = optJSONObject4.optString("url_big");
                                }
                                arrayList2.add(bVar);
                            }
                        }
                    }
                    aVar.b = arrayList2;
                    JSONObject optJSONObject5 = optJSONObject.optJSONObject("scanbutton");
                    if (optJSONObject5 != null) {
                        c cVar = new c();
                        cVar.b = optJSONObject5.optString("normal");
                        cVar.a = optJSONObject5.optString("select");
                        aVar.c = cVar;
                    }
                }
            }
            setResult(iconConf);
        }
    }
}
