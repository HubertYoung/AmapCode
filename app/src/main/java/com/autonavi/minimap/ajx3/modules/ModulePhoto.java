package com.autonavi.minimap.ajx3.modules;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.scansdk.constant.Constants;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.exception.InternalJsException;
import com.autonavi.minimap.ajx3.exception.InvalidParamJsException;
import com.autonavi.minimap.ajx3.modules.PicViewPagerDialog.OnDeleteListener;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.PhotoGraphedUtil;
import com.autonavi.minimap.ajx3.util.PhotoGraphedUtil.IPhotoGraphedListener;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("photo")
public class ModulePhoto extends AbstractModule {
    public static final String ACTION_NEW_PICTURE = "new_picture";
    public static final String ACTION_OPEN_CAMERA = "open_camera";
    public static final String MODULE_NAME = "photo";
    public static final String URL = "url";
    private JsAdapter mJsMethods = new JsAdapter(AMapPageFramework.getPageContext());
    /* access modifiers changed from: private */
    public d resultListener;

    public ModulePhoto(IAjxContext iAjxContext) {
        super(iAjxContext);
        getNativeContext();
    }

    @AjxMethod("preview")
    public void preview(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.put("action", "imagePreview");
            jSONObject.put("_action", "imagePreview");
            this.mJsMethods.sendAjx(jSONObject, (JsFunctionCallback) null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JsAdapter getJsMethods() {
        return this.mJsMethods;
    }

    @AjxMethod("add")
    public void addPhoto(final JsFunctionCallback jsFunctionCallback, String str) {
        if (jsFunctionCallback != null) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (TextUtils.isEmpty(str) || pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject optJSONObject = jSONObject.optJSONObject("example");
                String optString = jSONObject.optString("_action", "");
                String optString2 = jSONObject.optString("businessName", "");
                String optString3 = jSONObject.optString(Constants.SERVICE_TITLE_TEXT, "");
                String optString4 = jSONObject.optString("maxLength", "");
                String optString5 = jSONObject.optString("onlyCamera", "");
                String optString6 = jSONObject.optString("onlyPicture", "");
                String optString7 = jSONObject.optString("returnType", "imgbase64");
                AnonymousClass1 r10 = new Callback<JSONObject>() {
                    public void callback(JSONObject jSONObject) {
                        jsFunctionCallback.callback(jSONObject.toString());
                    }

                    public void error(Throwable th, boolean z) {
                        jsFunctionCallback.callback("");
                    }
                };
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("_action", optString);
                pageBundle.putObject("callback", r10);
                pageBundle.putString("businessName", optString2);
                pageBundle.putString(Constants.SERVICE_TITLE_TEXT, optString3);
                pageBundle.putString("maxLength", optString4);
                pageBundle.putObject("example", optJSONObject);
                pageBundle.putString("returnType", optString7);
                boolean z = !TextUtils.isEmpty(optString5) && Boolean.parseBoolean(optString5);
                boolean z2 = !TextUtils.isEmpty(optString6) && Boolean.parseBoolean(optString6);
                if (z) {
                    pageContext.startPage((String) "amap.basemap.action.photo_select_camera", pageBundle);
                } else if (z2) {
                    pageContext.startPage((String) "amap.basemap.action.photo_select_gallery", pageBundle);
                } else {
                    pageContext.startPage((String) "amap.basemap.action.photo_select_camera_gallery", pageBundle);
                }
            } catch (JSONException unused) {
                jsFunctionCallback.callback("");
            }
        }
    }

    @AjxMethod("imagePreview")
    public void jumpToPreview(final JsFunctionCallback jsFunctionCallback, String str) {
        if (jsFunctionCallback != null) {
            if (TextUtils.isEmpty(str)) {
                jsFunctionCallback.callback("");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                ArrayList<String> prepareBeanData = prepareBeanData(jSONObject);
                if (prepareBeanData != null && prepareBeanData.size() > 0) {
                    new PicViewPagerDialog(DoNotUseTool.getActivity(), prepareBeanData, new OnDeleteListener() {
                        public void onDelete(String str) {
                        }

                        public void onIndexDelete(int i) {
                            if (i >= 0) {
                                jsFunctionCallback.callback(Integer.valueOf(i));
                            }
                        }
                    }, jSONObject.optInt("index", 0) + 1).show();
                }
            } catch (JSONException unused) {
                jsFunctionCallback.callback("");
            }
        }
    }

    private ArrayList<String> prepareBeanData(JSONObject jSONObject) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (!(jSONObject == null || jSONObject.optJSONArray("list") == null)) {
            JSONArray optJSONArray = jSONObject.optJSONArray("list");
            if (optJSONArray != null) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        arrayList.add(optJSONObject.optString("url", ""));
                    }
                }
            }
        }
        return arrayList;
    }

    @AjxMethod("startPhotograph")
    public void startPhotoGraphed(final JsFunctionCallback jsFunctionCallback) {
        this.resultListener = new d() {
            public void onActivityResult(@Nullable Class<?> cls, int i, int i2, Intent intent) {
                PhotoGraphedUtil.onCaptureResult(i, i2, intent, new IPhotoGraphedListener() {
                    public void onPhotoCaptureResult(String str) {
                        jsFunctionCallback.callback(str);
                        ModulePhoto.this.sendBroadcast(ModulePhoto.ACTION_NEW_PICTURE);
                    }
                });
                drl.a().b((a) ModulePhoto.this.resultListener);
            }
        };
        drl.a().a((a) this.resultListener);
        PhotoGraphedUtil.doTakePhoto(DoNotUseTool.getActivity());
        sendBroadcast(ACTION_OPEN_CAMERA);
    }

    /* access modifiers changed from: private */
    public void sendBroadcast(String str) {
        Context nativeContext = getNativeContext();
        if (nativeContext != null) {
            nativeContext.sendBroadcast(new Intent(str));
        }
    }

    @AjxMethod("pick")
    public void pick(final JsFunctionCallback jsFunctionCallback, String str) {
        boolean z;
        if (jsFunctionCallback != null) {
            caf caf = (caf) a.a.a(caf.class);
            boolean z2 = false;
            if (caf == null) {
                jsFunctionCallback.callback(new InternalJsException("IPhotoUploadService instance is fail"));
                return;
            }
            int i = 6;
            if (!TextUtils.isEmpty(str)) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Object opt = jSONObject.opt("filterRule");
                    if (opt == null) {
                        z = false;
                    } else if (!(opt instanceof String)) {
                        jsFunctionCallback.callback(new InvalidParamJsException("filterRule no String"));
                        return;
                    } else if (!TextUtils.equals((String) opt, "location")) {
                        StringBuilder sb = new StringBuilder("filterRule is invalid no location, ");
                        sb.append((String) opt);
                        jsFunctionCallback.callback(new InvalidParamJsException(sb.toString()));
                        return;
                    } else {
                        z = true;
                    }
                    try {
                        Object opt2 = jSONObject.opt("maxPickCount");
                        if (opt2 != null) {
                            if (!(opt2 instanceof Number)) {
                                jsFunctionCallback.callback(new InvalidParamJsException("maxPickCount no Number"));
                                return;
                            }
                            int intValue = ((Number) opt2).intValue();
                            if (intValue > 0) {
                                if (intValue <= 6) {
                                    i = intValue;
                                }
                            }
                            jsFunctionCallback.callback(new InvalidParamJsException("maxPickCount is invalid = ".concat(String.valueOf(intValue))));
                            return;
                        }
                    } catch (JSONException e) {
                        z2 = z;
                        e = e;
                        e.printStackTrace();
                        z = z2;
                        caf.a(AMapPageFramework.getPageContext(), z, i, new a() {
                            public void onPhotoSelection(List<ImageInfo> list) {
                                if (list != null && list.size() > 0) {
                                    JSONArray jSONArray = new JSONArray();
                                    for (ImageInfo next : list) {
                                        JSONObject jSONObject = new JSONObject();
                                        try {
                                            StringBuilder sb = new StringBuilder("file://");
                                            sb.append(next.b);
                                            jSONObject.put("path", sb.toString());
                                            if (!TextUtils.isEmpty(next.i)) {
                                                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, Double.parseDouble(next.i));
                                            }
                                            if (!TextUtils.isEmpty(next.j)) {
                                                jSONObject.put("lat", Double.parseDouble(next.j));
                                            }
                                            jSONObject.put("width", (double) DimensionUtils.pixelToStandardUnit((float) next.n));
                                            jSONObject.put("height", (double) DimensionUtils.pixelToStandardUnit((float) next.o));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jSONArray.put(jSONObject);
                                    }
                                    if (jSONArray.length() > 0) {
                                        jsFunctionCallback.callback(new Object[0], jSONArray.toString());
                                        return;
                                    }
                                }
                                jsFunctionCallback.callback(new InternalJsException("photo no select"));
                            }
                        });
                    }
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    z = z2;
                    caf.a(AMapPageFramework.getPageContext(), z, i, new a() {
                        public void onPhotoSelection(List<ImageInfo> list) {
                            if (list != null && list.size() > 0) {
                                JSONArray jSONArray = new JSONArray();
                                for (ImageInfo next : list) {
                                    JSONObject jSONObject = new JSONObject();
                                    try {
                                        StringBuilder sb = new StringBuilder("file://");
                                        sb.append(next.b);
                                        jSONObject.put("path", sb.toString());
                                        if (!TextUtils.isEmpty(next.i)) {
                                            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, Double.parseDouble(next.i));
                                        }
                                        if (!TextUtils.isEmpty(next.j)) {
                                            jSONObject.put("lat", Double.parseDouble(next.j));
                                        }
                                        jSONObject.put("width", (double) DimensionUtils.pixelToStandardUnit((float) next.n));
                                        jSONObject.put("height", (double) DimensionUtils.pixelToStandardUnit((float) next.o));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jSONArray.put(jSONObject);
                                }
                                if (jSONArray.length() > 0) {
                                    jsFunctionCallback.callback(new Object[0], jSONArray.toString());
                                    return;
                                }
                            }
                            jsFunctionCallback.callback(new InternalJsException("photo no select"));
                        }
                    });
                }
            } else {
                z = false;
            }
            caf.a(AMapPageFramework.getPageContext(), z, i, new a() {
                public void onPhotoSelection(List<ImageInfo> list) {
                    if (list != null && list.size() > 0) {
                        JSONArray jSONArray = new JSONArray();
                        for (ImageInfo next : list) {
                            JSONObject jSONObject = new JSONObject();
                            try {
                                StringBuilder sb = new StringBuilder("file://");
                                sb.append(next.b);
                                jSONObject.put("path", sb.toString());
                                if (!TextUtils.isEmpty(next.i)) {
                                    jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, Double.parseDouble(next.i));
                                }
                                if (!TextUtils.isEmpty(next.j)) {
                                    jSONObject.put("lat", Double.parseDouble(next.j));
                                }
                                jSONObject.put("width", (double) DimensionUtils.pixelToStandardUnit((float) next.n));
                                jSONObject.put("height", (double) DimensionUtils.pixelToStandardUnit((float) next.o));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jSONArray.put(jSONObject);
                        }
                        if (jSONArray.length() > 0) {
                            jsFunctionCallback.callback(new Object[0], jSONArray.toString());
                            return;
                        }
                    }
                    jsFunctionCallback.callback(new InternalJsException("photo no select"));
                }
            });
        }
    }
}
