package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.antui.basic.AUButton;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.msgbox.db.MsgboxDao.Properties;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessageBtnAction;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import com.sina.weibo.sdk.statistic.LogBuilder;
import de.greenrobot.dao.query.WhereCondition;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR"})
/* renamed from: dar reason: default package */
/* compiled from: GetMessageResponse */
public final class dar extends AbstractAOSParser {
    public ArrayList<AmapMessage> a = new ArrayList<>();
    public List<btb> b = new ArrayList();
    public String c;
    public String d;
    public boolean e;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        String str;
        int i = -1;
        try {
            JSONObject parseHeader = parseHeader(bArr);
            if (this.result) {
                this.c = parseHeader.optString("vernier");
                this.d = parseHeader.optString("category_conf_md5");
                JSONArray optJSONArray = parseHeader.optJSONArray("category_conf");
                int i2 = 0;
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    int length = optJSONArray.length();
                    for (int i3 = 0; i3 < length; i3++) {
                        JSONObject jSONObject = optJSONArray.getJSONObject(i3);
                        if (jSONObject != null) {
                            btb btb = new btb();
                            btb.a = jSONObject.optString("id");
                            btb.b = jSONObject.optString("name");
                            btb.c = jSONObject.optString("pattern");
                            btb.d = jSONObject.optString(H5Param.MENU_ICON);
                            btb.e = jSONObject.optString("top_image");
                            btb.f = jSONObject.optString("section_type");
                            if (!TextUtils.isEmpty(btb.a)) {
                                this.b.add(btb);
                            }
                        }
                    }
                }
                JSONArray optJSONArray2 = parseHeader.optJSONArray("instructions");
                int i4 = 1;
                if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                    for (int i5 = 0; i5 < optJSONArray2.length(); i5++) {
                        JSONObject optJSONObject = optJSONArray2.optJSONObject(i5);
                        if (optJSONObject != null) {
                            int optInt = optJSONObject.optInt("type", 1);
                            int optInt2 = optJSONObject.optInt("delmode", 1);
                            int optInt3 = optJSONObject.optInt("reqmode", 1);
                            int optInt4 = optJSONObject.optInt("storage", 1);
                            bty mapView = DoNotUseTool.getMapManager().getMapView();
                            if (mapView != null) {
                                mapView.c(optInt, optInt2, optInt3, optInt4);
                            }
                        }
                    }
                }
                JSONArray optJSONArray3 = parseHeader.optJSONArray("msgs");
                HashMap hashMap = new HashMap();
                HashMap hashMap2 = new HashMap();
                if (optJSONArray3 != null) {
                    int length2 = optJSONArray3.length();
                    if (length2 > 0) {
                        this.e = true;
                        int i6 = 0;
                        while (i6 < length2) {
                            JSONObject optJSONObject2 = optJSONArray3.optJSONObject(i6);
                            if (optJSONObject2 != null) {
                                AmapMessage amapMessage = new AmapMessage();
                                amapMessage.id = optJSONObject2.optString("id");
                                amapMessage.createdTime = optJSONObject2.optLong(LogBuilder.KEY_START_TIME) * 1000;
                                amapMessage.expireAt = optJSONObject2.optLong("expiretime") * 1000;
                                amapMessage.features = "remote";
                                amapMessage.actionUri = optJSONObject2.optString("actionUri");
                                amapMessage.creator = optJSONObject2.optString("server");
                                amapMessage.trackId = optJSONObject2.optString("trackId");
                                amapMessage.title = optJSONObject2.optString("title");
                                amapMessage.descMessage = optJSONObject2.optString(Constants.BODY);
                                amapMessage.priority = optJSONObject2.optInt("priority", 501);
                                amapMessage.category = optJSONObject2.optString("category", "1");
                                amapMessage.reside = optJSONObject2.optString("reside", "1");
                                amapMessage.msgType = optJSONObject2.optInt("msgType", i2);
                                amapMessage.showType = optJSONObject2.optInt(RpcConstant.SHOW_TYPE, i2);
                                JSONArray optJSONArray4 = optJSONObject2.optJSONArray("baricon");
                                if (optJSONArray4 == null || optJSONArray4.length() <= 0) {
                                    amapMessage.baricon = "";
                                } else {
                                    amapMessage.baricon = optJSONArray4.optString(i2, "");
                                }
                                JSONArray optJSONArray5 = optJSONObject2.optJSONArray("msgImgUrI");
                                if (optJSONArray5 == null || optJSONArray5.length() <= 0) {
                                    amapMessage.msgImgUri = "";
                                } else {
                                    amapMessage.msgImgUri = optJSONArray5.optString(i2, "");
                                }
                                JSONArray optJSONArray6 = optJSONObject2.optJSONArray("msgImgUrIV2");
                                if (optJSONArray6 == null || optJSONArray6.length() <= 0) {
                                    amapMessage.msgImgUriV2 = "";
                                } else {
                                    amapMessage.msgImgUriV2 = optJSONArray6.optString(i2, "");
                                }
                                amapMessage.label = optJSONObject2.optString("label", "");
                                amapMessage.labelColor = optJSONObject2.optString("labelColor", "");
                                amapMessage.countdownEndtime = optJSONObject2.optString("countdownEndtime", "");
                                amapMessage.isEnable = optJSONObject2.optString("isEnable", "");
                                amapMessage.parentId = optJSONObject2.optString("parentId", "");
                                amapMessage.wordStatus = optJSONObject2.optString("workStatus", "");
                                amapMessage.showBody = optJSONObject2.optString("showBody", "");
                                amapMessage.pushMsgId = optJSONObject2.optString("pushMsgId", "");
                                amapMessage.impression = optJSONObject2.optString("impression", "");
                                JSONObject optJSONObject3 = optJSONObject2.optJSONObject("extData");
                                if (optJSONObject3 != null) {
                                    JSONObject optJSONObject4 = optJSONObject3.optJSONObject("credit");
                                    if (optJSONObject4 != null) {
                                        amapMessage.goldNum = optJSONObject4.optInt("delta", i);
                                        amapMessage.totalGoldNum = optJSONObject4.optInt("total", i);
                                    }
                                    JSONArray optJSONArray7 = optJSONObject3.optJSONArray("images");
                                    if (optJSONArray7 != null && optJSONArray7.length() > 0) {
                                        amapMessage.goldImage1 = optJSONArray7.optString(i2, "");
                                        if (optJSONArray7.length() > i4) {
                                            amapMessage.goldImage2 = optJSONArray7.optString(i4, "");
                                        }
                                    }
                                    JSONArray optJSONArray8 = optJSONObject3.optJSONArray("bus_name");
                                    if (optJSONArray8 == null || optJSONArray8.length() <= 0) {
                                        amapMessage.extData_gj_name_array = null;
                                    } else {
                                        String[] strArr = new String[optJSONArray8.length()];
                                        int i7 = 0;
                                        while (i7 < optJSONArray8.length()) {
                                            try {
                                                strArr[i7] = optJSONArray8.optString(i7, "");
                                                i7++;
                                            } catch (Exception unused) {
                                                i = -1;
                                                this.errorCode = i;
                                            }
                                        }
                                        amapMessage.extData_gj_name_array = strArr;
                                    }
                                    amapMessage.extData_gj_type = optJSONObject3.optString("bus_type", "");
                                }
                                JSONArray optJSONArray9 = optJSONObject2.optJSONArray("nickname");
                                if (optJSONArray9 == null || optJSONArray9.length() <= 0) {
                                    amapMessage.nickName = "";
                                } else {
                                    amapMessage.nickName = optJSONArray9.optString(i2, "");
                                }
                                JSONArray optJSONArray10 = optJSONObject2.optJSONArray("actions");
                                if (optJSONArray10 == null || optJSONArray10.length() <= 0) {
                                    amapMessage.amapMessageBtnActions = null;
                                } else {
                                    amapMessage.amapMessageBtnActions = new AmapMessageBtnAction[optJSONArray10.length()];
                                    for (int i8 = 0; i8 < optJSONArray10.length(); i8++) {
                                        JSONObject optJSONObject5 = optJSONArray10.optJSONObject(i8);
                                        if (optJSONObject5 != null) {
                                            amapMessage.amapMessageBtnActions[i8] = new AmapMessageBtnAction();
                                            amapMessage.amapMessageBtnActions[i8].actionKey = optJSONObject5.optString("actionKey", "");
                                            amapMessage.amapMessageBtnActions[i8].actionUri = optJSONObject5.optString("actionUri", "");
                                        }
                                    }
                                }
                                amapMessage.ope = optJSONObject2.optString("operate");
                                int optInt5 = optJSONObject2.optInt("kind", -1);
                                if (optInt5 == -1) {
                                    amapMessage.type = AmapMessage.TYPE_MSG;
                                } else if (optInt5 == 0) {
                                    amapMessage.type = AmapMessage.TYPE_MSG;
                                } else if (optInt5 == 1) {
                                    amapMessage.type = AmapMessage.TYPE_ACTIVITY;
                                }
                                if (AmapMessage.TYPE_MSG.equals(amapMessage.type)) {
                                    amapMessage.tag = optJSONObject2.optInt("tag", 0);
                                    if ((amapMessage.tag < 0 || amapMessage.tag > 6) && amapMessage.tag != 8) {
                                        amapMessage.tag = 0;
                                    }
                                } else {
                                    amapMessage.tag = optJSONObject2.optInt("tag", -1);
                                    if (amapMessage.tag != 7) {
                                        amapMessage.tag = -1;
                                    }
                                }
                                JSONArray optJSONArray11 = optJSONObject2.optJSONArray("launchImage");
                                if (optJSONArray11 == null || optJSONArray11.length() <= 0) {
                                    amapMessage.imgUrl = null;
                                } else {
                                    amapMessage.imgUrl = new String[optJSONArray11.length()];
                                    for (int i9 = 0; i9 < optJSONArray11.length(); i9++) {
                                        amapMessage.imgUrl[i9] = optJSONArray11.getString(i9);
                                    }
                                }
                                amapMessage.page = optJSONObject2.optInt("page", -1);
                                amapMessage.location = optJSONObject2.optInt("location", -1);
                                JSONObject optJSONObject6 = optJSONObject2.optJSONObject(AUButton.BTN_TYPE_SUB);
                                if (optJSONObject6 != null) {
                                    amapMessage.hasSub = true;
                                    JSONArray optJSONArray12 = optJSONObject6.optJSONArray("sub_launchImage");
                                    if (optJSONArray12 == null || optJSONArray12.length() <= 0) {
                                        amapMessage.subImgUrl = null;
                                    } else {
                                        amapMessage.subImgUrl = new String[optJSONArray12.length()];
                                        for (int i10 = 0; i10 < optJSONArray12.length(); i10++) {
                                            amapMessage.subImgUrl[i10] = optJSONArray12.getString(i10);
                                        }
                                    }
                                    amapMessage.subTitle = optJSONObject6.optString("sub_title");
                                    amapMessage.sub_page = optJSONObject6.optInt("sub_page", -1);
                                    amapMessage.sub_location = optJSONObject6.optInt("sub_location", -1);
                                } else {
                                    amapMessage.hasSub = false;
                                }
                                JSONObject jSONObject2 = new JSONObject();
                                try {
                                    jSONObject2.put("text", !TextUtils.isEmpty(amapMessage.title) ? amapMessage.title : "");
                                    jSONObject2.put("type", amapMessage.id);
                                    jSONObject2.put("time", dbf.a());
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(amapMessage.tag);
                                    jSONObject2.put("from", sb.toString());
                                } catch (JSONException e2) {
                                    e2.printStackTrace();
                                }
                                if (AmapMessage.TYPE_ACTIVITY.equals(amapMessage.type) && amapMessage.tag == 7) {
                                    ArrayList arrayList = (ArrayList) hashMap2.get(amapMessage.id);
                                    if (arrayList != null) {
                                        arrayList.add(amapMessage);
                                    } else {
                                        ArrayList arrayList2 = new ArrayList();
                                        arrayList2.add(amapMessage);
                                        hashMap2.put(amapMessage.id, arrayList2);
                                    }
                                } else if (AmapMessage.TYPE_MSG.equals(amapMessage.type) && amapMessage.tag == 5) {
                                    AMapAppGlobal.getApplication().getApplicationContext();
                                    if (!dbc.b().a(amapMessage.id)) {
                                        if (!TextUtils.isEmpty(amapMessage.parentId)) {
                                            str = amapMessage.parentId;
                                        } else {
                                            str = amapMessage.id;
                                        }
                                        ArrayList arrayList3 = (ArrayList) hashMap.get(str);
                                        if (arrayList3 != null) {
                                            arrayList3.add(amapMessage);
                                        } else {
                                            ArrayList arrayList4 = new ArrayList();
                                            arrayList4.add(amapMessage);
                                            hashMap.put(str, arrayList4);
                                        }
                                    }
                                } else if (amapMessage.ope.equals("delete")) {
                                    String str2 = amapMessage.id;
                                    if (!TextUtils.isEmpty(str2)) {
                                        AMapAppGlobal.getApplication().getApplicationContext();
                                        dbc.b().b(str2);
                                    }
                                } else {
                                    this.a.add(amapMessage);
                                }
                            }
                            i6++;
                            i = -1;
                            i2 = 0;
                            i4 = 1;
                        }
                    }
                }
                AMapAppGlobal.getApplication().getApplicationContext();
                dbc.b().b.queryBuilder().where(Properties.ab.eq(Integer.valueOf(1)), new WhereCondition[0]).buildDelete().executeDeleteWithoutDetachingEntities();
                if (hashMap2.size() > 0) {
                    for (String str3 : hashMap2.keySet()) {
                        ArrayList arrayList5 = (ArrayList) hashMap2.get(str3);
                        Collections.sort(arrayList5, new dbd());
                        AmapMessage amapMessage2 = (AmapMessage) arrayList5.get(arrayList5.size() - 1);
                        amapMessage2.id = str3;
                        if (TextUtils.isEmpty(amapMessage2.ope) || !amapMessage2.ope.equals("delete")) {
                            AMapAppGlobal.getApplication().getApplicationContext();
                            dbc.b().b(amapMessage2);
                        } else {
                            AMapAppGlobal.getApplication().getApplicationContext();
                            dbc.b().b(amapMessage2.id);
                        }
                    }
                }
                if (hashMap.size() > 0) {
                    for (String str4 : hashMap.keySet()) {
                        ArrayList arrayList6 = (ArrayList) hashMap.get(str4);
                        Collections.sort(arrayList6, new dbd());
                        AmapMessage amapMessage3 = (AmapMessage) arrayList6.get(arrayList6.size() - 1);
                        amapMessage3.id = str4;
                        if (TextUtils.isEmpty(amapMessage3.ope) || !amapMessage3.ope.equals("delete")) {
                            AMapAppGlobal.getApplication().getApplicationContext();
                            dbc.b().b(amapMessage3);
                            if (!TextUtils.isEmpty(amapMessage3.pushMsgId) && new MapSharePreference((String) MessageBoxManager.SP_NAME_PUSH_MSG).sharedPrefs().getLong(amapMessage3.pushMsgId, -1) > 0) {
                                MessageBoxManager.getInstance().setReadSync(str4);
                            }
                        } else {
                            AMapAppGlobal.getApplication().getApplicationContext();
                            dbc.b().b(amapMessage3);
                        }
                    }
                }
            }
        } catch (Exception unused2) {
            this.errorCode = i;
        }
    }
}
