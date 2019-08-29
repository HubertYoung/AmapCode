package com.autonavi.bundle.searchservice.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.ae.search.model.GPoiBean;
import com.autonavi.ae.search.model.GPoiGroup;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.sdk.location.LocationInstrument;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepImplementations
@SuppressFBWarnings({"ICAST_IDIV_CAST_TO_DOUBLE", "LI_LAZY_INIT_STATIC"})
@KeepName
public class SearchUtils {
    public static final int DATATYPE_POI_OFFLINE = 3;
    public static final String DOWNLOADVOICESCHEME = "androidamap://openFeature?featureName=tts&sourceApplication=";
    public static final String DOWNLOADVOICESCHEMESOURCE = "naviTts_searchScheme";
    public static boolean mOfflineSearchNearestPoi = false;

    public static final class a {
        final Object a;
        final Class<?> b;
    }

    public static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]+\\.?[0-9]*").matcher(str).matches() || Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static boolean isWIFI() {
        Application application = AMapAppGlobal.getApplication();
        return application != null && 4 == aaw.b(application.getApplicationContext());
    }

    public static boolean isNetwork() {
        Application application = AMapAppGlobal.getApplication();
        return application != null && aaw.c(application);
    }

    public static String processTipItemName(TipItem tipItem) {
        String str = null;
        if (tipItem == null) {
            return null;
        }
        if (!TextUtils.isEmpty(tipItem.name)) {
            str = tipItem.name;
        }
        return str;
    }

    public static CharSequence processTipItemTag(TipItem tipItem) {
        CharSequence charSequence = "";
        if (tipItem == null) {
            return charSequence;
        }
        if (!TextUtils.isEmpty(tipItem.taginfo)) {
            charSequence = Html.fromHtml(tipItem.taginfo);
        }
        return charSequence;
    }

    public static CharSequence processTipItemPoiTag(TipItem tipItem) {
        Spanned spanned = null;
        if (tipItem == null) {
            return null;
        }
        if (!TextUtils.isEmpty(tipItem.poiTag)) {
            spanned = Html.fromHtml(tipItem.poiTag);
        }
        return spanned;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int processTipItemIcon(com.autonavi.bundle.entity.sugg.TipItem r2) {
        /*
            if (r2 != 0) goto L_0x0004
            r2 = 0
            return r2
        L_0x0004:
            int r0 = r2.iconinfo
            switch(r0) {
                case 0: goto L_0x0023;
                case 1: goto L_0x0020;
                case 2: goto L_0x001d;
                case 3: goto L_0x001a;
                case 4: goto L_0x0017;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.String r0 = r2.poiid
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0031
            int r0 = r2.dataType
            r1 = 1
            if (r0 != r1) goto L_0x002e
            goto L_0x0031
        L_0x0017:
            int r0 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_searchicon_normal
            goto L_0x0033
        L_0x001a:
            int r0 = com.autonavi.minimap.R.drawable.ditie
            goto L_0x0033
        L_0x001d:
            int r0 = com.autonavi.minimap.R.drawable.gongjiao
            goto L_0x0033
        L_0x0020:
            int r0 = com.autonavi.minimap.R.drawable.xianlu
            goto L_0x0033
        L_0x0023:
            java.lang.String r0 = r2.poiid
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x002e
            int r0 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_searchicon_normal
            goto L_0x0033
        L_0x002e:
            int r0 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_tqueryicon_normal
            goto L_0x0033
        L_0x0031:
            int r0 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_searchicon_normal
        L_0x0033:
            int r2 = r2.dataType
            r1 = 3
            if (r2 != r1) goto L_0x003a
            int r0 = com.autonavi.minimap.R.drawable.default_generalsearch_sugg_tqueryicon_normal
        L_0x003a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.searchservice.utils.SearchUtils.processTipItemIcon(com.autonavi.bundle.entity.sugg.TipItem):int");
    }

    public static int processTipItemRightButton(TipItem tipItem) {
        int i = 0;
        if (tipItem == null) {
            return 0;
        }
        if (tipItem.x > 0.0d && tipItem.y > 0.0d) {
            i = R.drawable.search_home_item_right;
        }
        return i;
    }

    public static float processTipItemRichRating(TipItem tipItem) {
        if (tipItem == null || tipItem.richRating == null || tipItem.richRating.isEmpty()) {
            return 0.0f;
        }
        return Float.valueOf(tipItem.richRating).floatValue();
    }

    public static String processTipItemNumReview(TipItem tipItem) {
        return (tipItem == null || tipItem.numReview == null || tipItem.numReview.isEmpty()) ? "" : tipItem.numReview;
    }

    public static boolean isNaviOfflineSearch() {
        if (aaw.b(AMapAppGlobal.getApplication().getApplicationContext()) == 4) {
            return false;
        }
        boolean z = AMapAppGlobal.getApplication().getApplicationContext().getSharedPreferences(DriveSpUtil.SP_ONLINE_OFFLINE, 0).getBoolean("navi_config_online", true);
        Context context = DoNotUseTool.getContext();
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView == null || context == null) {
            return false;
        }
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        boolean z2 = iOfflineManager != null && iOfflineManager.checkCityDownload((int) li.a().a(mapView.p(), mapView.q()));
        if ((!aaw.c(context) || !z) && z2) {
            return true;
        }
        return false;
    }

    public static String getLoadingMessage(String str) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (TextUtils.isEmpty(str)) {
            return resources.getString(R.string.searching);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(resources.getString(R.string.searching));
        sb.append("\"");
        sb.append(str);
        sb.append("\"");
        return sb.toString();
    }

    public static GeoPoint getCenterPoint(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split("\\|");
        if (split == null || split.length < 4) {
            return null;
        }
        double a2 = ahh.a(split[0]);
        double a3 = ahh.a(split[1]);
        return new GeoPoint((a2 + ahh.a(split[2])) / 2.0d, (a3 + ahh.a(split[3])) / 2.0d);
    }

    public static Intent getOpenDownloadVoiceScheme(String str) {
        return new Intent("android.intent.action.VIEW", Uri.parse("androidamap://openFeature?featureName=tts&sourceApplication=".concat(String.valueOf(str))));
    }

    public static void openOfflineNaviTtsFragment() {
        DoNotUseTool.startScheme(getOpenDownloadVoiceScheme("naviTts_searchScheme"));
    }

    public static TipItem convertGPoiBase2TipItem(GPoiBase gPoiBase) {
        if (gPoiBase == null) {
            return null;
        }
        TipItem tipItem = new TipItem();
        if (gPoiBase instanceof GPoiBean) {
            GPoiBean gPoiBean = (GPoiBean) gPoiBase;
            tipItem.name = gPoiBase.getName() == null ? "" : gPoiBase.getName();
            tipItem.addr = gPoiBean.getAddr();
            int adcode = gPoiBean.getAdcode();
            if (adcode > 0) {
                tipItem.adcode = String.valueOf(adcode);
            } else {
                tipItem.adcode = "";
            }
            if (!TextUtils.isEmpty(gPoiBean.getCatName())) {
                tipItem.poiTag = gPoiBean.getCatName();
            }
            if (!TextUtils.isEmpty(gPoiBean.getPoiID())) {
                tipItem.poiid = gPoiBean.getPoiID();
            } else {
                tipItem.poiid = "";
            }
            tipItem.iconinfo = convertIconCode(gPoiBean);
            tipItem.x = (double) gPoiBean.getLocalPoint().x;
            tipItem.y = (double) gPoiBean.getLocalPoint().y;
            if (gPoiBean.getNaviPoint() != null) {
                tipItem.x_entr = (double) gPoiBean.getNaviPoint().x;
                tipItem.y_entr = (double) gPoiBean.getNaviPoint().y;
            }
            int catCode = gPoiBean.getCatCode();
            if (catCode > 0) {
                tipItem.newType = String.valueOf(catCode);
            }
        } else if (gPoiBase instanceof GPoiGroup) {
            tipItem.name = gPoiBase.getName() == null ? "" : gPoiBase.getName();
        }
        return tipItem;
    }

    private static int convertIconCode(GPoiBean gPoiBean) {
        int catCode = gPoiBean.getCatCode();
        if (catCode != 150600) {
            if (catCode != 150800) {
                switch (catCode) {
                    case 150500:
                    case 150501:
                        break;
                    default:
                        switch (catCode) {
                            case 150700:
                            case 150701:
                            case 150702:
                            case 150703:
                                break;
                            default:
                                return -1;
                        }
                }
            }
            return 2;
        }
        return 3;
    }

    public static void invokeMethodExceptionSafe(Object obj, String str, a... aVarArr) {
        Class[] clsArr;
        if (obj != null) {
            if (aVarArr == null) {
                try {
                    clsArr = new Class[0];
                } catch (Throwable unused) {
                    return;
                }
            } else {
                clsArr = new Class[aVarArr.length];
            }
            Object[] objArr = aVarArr == null ? new Object[0] : new Object[aVarArr.length];
            if (aVarArr != null) {
                int length = clsArr.length;
                for (int i = 0; i < length; i++) {
                    clsArr[i] = aVarArr[i].b;
                    objArr[i] = aVarArr[i].a;
                }
            }
            Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, objArr);
        }
    }

    public static boolean checkCategory(String str, String[] strArr, String[] strArr2) {
        String[] split;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String str2 : str.trim().split("\\|")) {
            if (strArr != null && strArr.length > 0) {
                for (String startsWith : strArr) {
                    if (str2.startsWith(startsWith)) {
                        return true;
                    }
                }
            }
            if (strArr2 != null && strArr2.length > 0) {
                for (String equals : strArr2) {
                    if (equals.equals(str2)) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }

    public static String formatDistance(Context context, POI poi) {
        return formatDistance(context, poi, true);
    }

    public static String formatDistance(Context context, POI poi, boolean z) {
        int distance = poi.getDistance();
        String str = "";
        if (!z) {
            String str2 = null;
            if (!(poi.getPoiExtra() == null || poi.getPoiExtra().get("query_type") == null)) {
                str2 = (String) poi.getPoiExtra().get("query_type");
            }
            str = "RQBXY".equals(str2) ? "距查询位置" : "距您";
        }
        if (distance == -100) {
            return Token.SEPARATOR;
        }
        if (distance <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("0");
            sb.append(lh.a(context, R.string.meter));
            return sb.toString();
        } else if (distance < 1000) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(distance);
            sb2.append(lh.a(context, R.string.meter));
            return sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(String.valueOf(((double) (distance / 100)) / 10.0d));
            sb3.append(lh.a(context, R.string.kilometer));
            return sb3.toString();
        }
    }

    public static int safeParseColor(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        try {
            if (!str.startsWith(MetaRecord.LOG_SEPARATOR)) {
                str = MetaRecord.LOG_SEPARATOR.concat(String.valueOf(str));
            }
            return Color.parseColor(str);
        } catch (Throwable unused) {
            return i;
        }
    }

    public static int safeParseColor(String str, String str2) {
        return safeParseColor(str, Color.parseColor(str2));
    }

    public static byte[] decodeAssetResData(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read >= 0) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    open.close();
                    return byteArray;
                }
            }
        } catch (IOException e) {
            kf.a((Throwable) e);
            return null;
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }

    public static int getLatestPositionAdCode() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            return latestPosition.getAdCode();
        }
        return 0;
    }
}
