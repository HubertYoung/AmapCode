package com.autonavi.minimap.bundle.share.ajxmodule;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleShare;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam.SendType;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxModuleShare extends AbstractModuleShare {
    private static final String CONFIG = "config";
    private static final String CONFIG_CHANNEL = "channel";
    private static final String CONFIG_SHARE_DIRECT = "isDirect";
    private static final String CONFIG_SHORT_URL = "needShortUrl";
    private static final String DESC = "descr";
    private static final String LINK_URL = "linkURL";
    private static final String SHARE_IMAGE = "shareImage";
    private static final String TEXT = "text";
    private static final String THUMB_IMAGE = "thumbImage";
    private static final String TITLE = "title";
    private static final String URL = "url";

    public class a extends dcd {
        private String b;
        private JsFunctionCallback c;

        a(String str, JsFunctionCallback jsFunctionCallback) {
            this.b = str;
            this.c = jsFunctionCallback;
        }

        public final ShareParam getShareDataByType(int i) {
            boolean z = false;
            if (i != 11) {
                switch (i) {
                    case 3:
                        e eVar = new e(0);
                        try {
                            JSONObject jSONObject = new JSONObject(this.b);
                            eVar.h = AjxModuleShare.this.tailorPath(jSONObject.optString(AjxModuleShare.SHARE_IMAGE));
                            eVar.g = AjxModuleShare.this.getThumbnailBitmap(AjxModuleShare.this.tailorPath(jSONObject.optString(AjxModuleShare.THUMB_IMAGE)));
                            eVar.e = 3;
                            eVar.b = jSONObject.optString(AjxModuleShare.LINK_URL);
                            if (jSONObject.optInt(AjxModuleShare.CONFIG_SHORT_URL) == 1) {
                                z = true;
                            }
                            eVar.c = z;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return eVar;
                    case 4:
                        e eVar2 = new e(1);
                        try {
                            JSONObject jSONObject2 = new JSONObject(this.b);
                            eVar2.h = AjxModuleShare.this.tailorPath(jSONObject2.optString(AjxModuleShare.SHARE_IMAGE));
                            eVar2.g = AjxModuleShare.this.getThumbnailBitmap(AjxModuleShare.this.tailorPath(jSONObject2.optString(AjxModuleShare.THUMB_IMAGE)));
                            if (jSONObject2.optInt(AjxModuleShare.CONFIG_SHORT_URL) == 1) {
                                z = true;
                            }
                            eVar2.c = z;
                            eVar2.e = 3;
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        return eVar2;
                    case 5:
                        f fVar = new f();
                        try {
                            JSONObject jSONObject3 = new JSONObject(this.b);
                            fVar.a = jSONObject3.optString("text");
                            fVar.j = true;
                            fVar.h = AjxModuleShare.this.tailorPath(jSONObject3.optString(AjxModuleShare.SHARE_IMAGE));
                            fVar.b = jSONObject3.optString(AjxModuleShare.LINK_URL);
                            if (jSONObject3.optInt(AjxModuleShare.CONFIG_SHORT_URL) == 1) {
                                z = true;
                            }
                            fVar.c = z;
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                        return fVar;
                    default:
                        return null;
                }
            } else {
                DingDingParam dingDingParam = new DingDingParam();
                try {
                    JSONObject jSONObject4 = new JSONObject(this.b);
                    Bitmap access$100 = AjxModuleShare.this.getThumbnailBitmap(jSONObject4.optString(AjxModuleShare.SHARE_IMAGE));
                    if (access$100 != null) {
                        dingDingParam.g = access$100;
                    }
                    String optString = jSONObject4.optString(AjxModuleShare.SHARE_IMAGE);
                    if (!TextUtils.isEmpty(optString)) {
                        if (optString.startsWith("http")) {
                            dingDingParam.h = jSONObject4.optString(AjxModuleShare.SHARE_IMAGE);
                            dingDingParam.e = SendType.OnlineImage;
                        }
                        if (optString.startsWith("file://")) {
                            dingDingParam.i = jSONObject4.optString(AjxModuleShare.SHARE_IMAGE);
                            dingDingParam.e = SendType.LocalImage;
                        }
                    }
                    dingDingParam.f = jSONObject4.optString("title");
                    dingDingParam.a = jSONObject4.optString("text");
                    dingDingParam.b = jSONObject4.optString(AjxModuleShare.LINK_URL);
                    if (jSONObject4.optInt(AjxModuleShare.CONFIG_SHORT_URL) == 1) {
                        z = true;
                    }
                    dingDingParam.c = z;
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
                return dingDingParam;
            }
        }

        public final void onFinish(int i, int i2) {
            super.onFinish(i, i2);
            if (this.c != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("status", i2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.c.callback(jSONObject.toString());
            }
        }

        public final void onEntrySelected(int i) {
            super.onEntrySelected(i);
            if (this.c != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("channel", AjxModuleShare.this.mappingShareType(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.c.callback(jSONObject.toString());
            }
        }
    }

    /* access modifiers changed from: private */
    public int mappingShareType(int i) {
        switch (i) {
            case 2:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
            case 6:
                return 6;
            case 7:
                return 7;
            case 10:
                return 5;
            case 11:
                return 3;
            default:
                return 0;
        }
    }

    public AjxModuleShare(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void openShareUI(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str)) {
            dct dct = new dct(0);
            if (!updateEntry(str, dct)) {
                AMapLog.e("ShareUI", str);
                return;
            }
            ((dcb) defpackage.esb.a.a.a(dcb.class)).a(dct, (dcd) new a(str, jsFunctionCallback));
        }
    }

    private boolean updateEntry(String str, dct dct) {
        try {
            JSONObject optJSONObject = new JSONObject(str).optJSONObject(CONFIG);
            JSONArray jSONArray = null;
            if (optJSONObject != null) {
                jSONArray = optJSONObject.optJSONArray("channel");
                dct.m = optJSONObject.optInt(CONFIG_SHARE_DIRECT) == 1;
            }
            if (jSONArray == null || jSONArray.length() == 0) {
                return false;
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                switch (jSONArray.getInt(i)) {
                    case 1:
                        dct.d = true;
                        break;
                    case 2:
                        dct.e = true;
                        break;
                    case 3:
                        dct.l = true;
                        break;
                    case 4:
                        dct.c = true;
                        break;
                    case 5:
                        dct.k = true;
                        break;
                    case 6:
                        if (TextUtils.isEmpty(optJSONObject.optString(LINK_URL))) {
                            break;
                        } else {
                            dct.g = true;
                            break;
                        }
                    case 7:
                        dct.h = true;
                        break;
                }
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public String tailorPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (str.startsWith("file:///")) {
            return str.replace("file://", "");
        }
        return str.startsWith("file://") ? str.replace("file:/", "") : str;
    }

    /* access modifiers changed from: private */
    public Bitmap getThumbnailBitmap(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        if (decodeFile == null) {
            return null;
        }
        return decodeFile;
    }
}
