package defpackage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.utils.Constant;
import com.autonavi.core.network.inter.response.InputStreamResponse;
import com.autonavi.minimap.R;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@TargetApi(25)
/* renamed from: epw reason: default package */
/* compiled from: Amap3DTouchShortcutHelper */
public final class epw {
    private static Context a;
    private static lp b = new lp() {
        public final void onConfigCallBack(int i) {
        }

        public final void onConfigResultCallBack(int i, String str) {
            if (i != 4) {
                switch (i) {
                    case 0:
                    case 1:
                        break;
                    default:
                        return;
                }
            }
            epw.a(str);
        }
    };

    public static void a(Context context) {
        if (VERSION.SDK_INT >= 25 && context != null) {
            a = context.getApplicationContext();
            a(lo.a().a((String) "amap_basemap_config"));
        }
    }

    public static void a(String str) {
        if (VERSION.SDK_INT >= 25 && !TextUtils.isEmpty(str)) {
            try {
                JSONArray optJSONArray = new JSONObject(str).optJSONArray("and_3dtouch_shortcut_config");
                if (optJSONArray != null) {
                    if (optJSONArray.length() != 0) {
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < optJSONArray.length(); i++) {
                            JSONObject jSONObject = optJSONArray.getJSONObject(i);
                            if (jSONObject != null) {
                                String optString = jSONObject.optString("shortcut_item_id");
                                String optString2 = jSONObject.optString("shortcut_short_label");
                                String optString3 = jSONObject.optString("shortcut_long_label");
                                String optString4 = jSONObject.optString("url");
                                String optString5 = jSONObject.optString("icon_url");
                                if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2) && !TextUtils.isEmpty(optString4) && !TextUtils.isEmpty(optString5)) {
                                    epx epx = new epx();
                                    epx.a = optString;
                                    epx.b = optString2;
                                    epx.c = optString3;
                                    if (!TextUtils.isEmpty(optString3)) {
                                        epx.c = optString3;
                                    } else {
                                        epx.c = optString2;
                                    }
                                    epx.d = optString4;
                                    epx.e = optString5;
                                    epx.f = i;
                                    arrayList.add(epx);
                                }
                            }
                        }
                        if (arrayList.size() != 0) {
                            b((List<epx>) arrayList);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static void b(final List<epx> list) {
        ahm.a(new Runnable() {
            public final void run() {
                try {
                    ShortcutManager a2 = epw.c();
                    if (a2 != null) {
                        List b = epw.b();
                        ArrayList arrayList = new ArrayList();
                        Iterator it = b.iterator();
                        while (true) {
                            int i = 0;
                            if (!it.hasNext()) {
                                break;
                            }
                            ShortcutInfo shortcutInfo = (ShortcutInfo) it.next();
                            String id = shortcutInfo.getId();
                            while (true) {
                                if (i >= list.size()) {
                                    break;
                                }
                                epx epx = (epx) list.get(i);
                                if (epx.a.equals(id)) {
                                    ShortcutInfo a3 = epw.a(epx, shortcutInfo);
                                    if (a3 != null) {
                                        arrayList.add(a3);
                                    }
                                    it.remove();
                                    list.remove(epx);
                                } else {
                                    i++;
                                }
                            }
                        }
                        if (arrayList.size() > 0) {
                            a2.updateShortcuts(arrayList);
                        }
                        ArrayList arrayList2 = new ArrayList();
                        for (int i2 = 0; i2 < b.size(); i2++) {
                            ShortcutInfo shortcutInfo2 = (ShortcutInfo) b.get(i2);
                            if (shortcutInfo2.isPinned()) {
                                arrayList2.add(shortcutInfo2.getId());
                            } else {
                                a2.removeDynamicShortcuts(Arrays.asList(new String[]{shortcutInfo2.getId()}));
                            }
                        }
                        if (arrayList2.size() > 0) {
                            a2.disableShortcuts(arrayList2);
                        }
                        List a4 = epw.a(list);
                        if (a4.size() > 0 && (a2.getMaxShortcutCountPerActivity() - a2.getDynamicShortcuts().size()) - a2.getManifestShortcuts().size() >= a4.size()) {
                            a2.addDynamicShortcuts(a4);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static ShortcutInfo a(epx epx, Bitmap bitmap) {
        Builder builder = new Builder(a, epx.a);
        Intent intent = new Intent("com.autonavi.minimap.ACTION", Uri.parse(epx.d).buildUpon().appendQueryParameter("shortcutLabel", epx.b).build());
        intent.setFlags(268435456);
        intent.setClassName(AMapAppGlobal.getApplication(), Constant.LAUNCHER_ACTIVITY_NAME);
        builder.setIntent(intent);
        builder.setRank(epx.f);
        builder.setShortLabel(epx.b);
        builder.setLongLabel(epx.c);
        PersistableBundle persistableBundle = new PersistableBundle();
        if (bitmap != null) {
            builder.setIcon(Icon.createWithBitmap(bitmap));
            persistableBundle.putBoolean("shortcut_icon_load_success", true);
        } else {
            builder.setIcon(Icon.createWithResource(a, R.drawable.sci_default_icon));
            persistableBundle.putBoolean("shortcut_icon_load_success", false);
        }
        persistableBundle.putString("shortcut_data_sign", epx.a());
        persistableBundle.putString("shortcut_icon_url", epx.e);
        persistableBundle.putString("shortcut_data_url", epx.d);
        builder.setExtras(persistableBundle);
        return builder.build();
    }

    private static Bitmap b(String str) {
        try {
            bpf bpf = new bpf();
            bpf.setUrl(str);
            yq.a();
            bpk a2 = yq.a((bph) bpf, InputStreamResponse.class);
            if (a2 == null) {
                return null;
            }
            return BitmapFactory.decodeStream(new BufferedInputStream(a2.getBodyInputStream(), 8192));
        } catch (Exception e) {
            ku a3 = ku.a();
            StringBuilder sb = new StringBuilder("fetchFavicon ");
            sb.append(str);
            sb.append("\n");
            sb.append(e.toString());
            a3.a((String) "Amap3DTouchShortcutHelper", sb.toString());
            return null;
        }
    }

    /* access modifiers changed from: private */
    @Nullable
    public static ShortcutManager c() {
        if (VERSION.SDK_INT < 25) {
            return null;
        }
        return (ShortcutManager) a.getSystemService("shortcut");
    }

    private static ShortcutInfo a(epx epx) {
        return a(epx, b(epx.e));
    }

    static /* synthetic */ List b() {
        ArrayList arrayList = new ArrayList();
        ShortcutManager c = c();
        if (c != null) {
            for (ShortcutInfo next : c.getDynamicShortcuts()) {
                if (!next.isImmutable()) {
                    arrayList.add(next);
                }
            }
        }
        return arrayList;
    }

    static /* synthetic */ ShortcutInfo a(epx epx, ShortcutInfo shortcutInfo) {
        if (shortcutInfo.getExtras() == null) {
            return a(epx);
        }
        String a2 = epx.a();
        StringBuilder sb = new StringBuilder();
        sb.append(shortcutInfo.getId());
        sb.append(shortcutInfo.getShortLabel());
        sb.append(shortcutInfo.getLongLabel());
        if (shortcutInfo.getExtras() != null) {
            sb.append(shortcutInfo.getExtras().getString("shortcut_data_url", ""));
        }
        sb.append(shortcutInfo.getRank());
        if (shortcutInfo.getExtras() != null) {
            sb.append(shortcutInfo.getExtras().getString("shortcut_icon_url", ""));
        }
        if (!a2.equals(sb.toString())) {
            return a(epx);
        }
        if (!shortcutInfo.getExtras().getBoolean("shortcut_icon_load_success", true)) {
            Bitmap b2 = b(epx.e);
            if (b2 != null) {
                return a(epx, b2);
            }
        }
        return null;
    }

    static /* synthetic */ List a(List list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(a((epx) list.get(i)));
        }
        return arrayList;
    }
}
