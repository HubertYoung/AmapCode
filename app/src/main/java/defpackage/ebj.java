package defpackage;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.Trip;
import com.autonavi.bundle.routecommon.entity.Trip.b;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: ebj reason: default package */
/* compiled from: RouteRealTimeBusUtil */
public final class ebj {
    private static SparseArray<String> a = new SparseArray<>();

    public static int a(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return 0;
        }
        return (i <= 0 || i >= 60) ? 2 : 1;
    }

    public static boolean a(int i) {
        return i < 2;
    }

    private static String a(@NonNull Context context, @StringRes int i) {
        String str = a.get(i);
        if (str != null) {
            return str;
        }
        String string = context.getString(i);
        a.append(i, string);
        return string;
    }

    public static String a(Context context, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        switch (a(i, i2)) {
            case 0:
                sb.append(context.getString(R.string.arrived_in_station));
                break;
            case 1:
                sb.append(a(context, R.string.about_to_arriving_in_station));
                break;
            case 2:
                sb.append(i2 + 1);
                sb.append(a(context, R.string.bus_stop));
                sb.append(Token.SEPARATOR);
                sb.append(a(context, R.string.approx));
                sb.append(axt.a(i, true));
                break;
        }
        return sb.toString();
    }

    public static String a(Context context, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        switch (a(i2, i3)) {
            case 0:
                sb.append(context.getString(R.string.arrived_in_station));
                break;
            case 1:
                sb.append(a(context, R.string.about_to_arriving_in_station));
                break;
            case 2:
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            if (i != 4) {
                                if (i == 5) {
                                    sb.append(i3 + 1);
                                    sb.append(a(context, R.string.bus_stop));
                                    break;
                                }
                            } else {
                                sb.append(i3 + 1);
                                sb.append(a(context, R.string.bus_stop));
                                sb.append("/");
                                sb.append(a(context, R.string.approx));
                                sb.append(axt.a(i2, true));
                                break;
                            }
                        } else {
                            sb.append(i3 + 1);
                            sb.append(a(context, R.string.bus_stop));
                            sb.append(a(context, R.string.approx));
                            sb.append(axt.a(i2, true));
                            break;
                        }
                    } else {
                        sb.append(i3 + 1);
                        sb.append(a(context, R.string.bus_stop));
                        sb.append("/");
                        sb.append(a(context, R.string.approx));
                        sb.append(axt.a(i2, true));
                        sb.append(a(context, R.string.arrive_after_arrived));
                        break;
                    }
                } else {
                    sb.append(i3 + 1);
                    sb.append(a(context, R.string.bus_stop));
                    sb.append(a(context, R.string.arrive_after_arrived));
                    break;
                }
                break;
        }
        return sb.toString();
    }

    public static String a(Context context, BusPath busPath) {
        int i;
        Trip trip;
        if (context == null || busPath == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (busPath.mNearTrip != null) {
            trip = busPath.mNearTrip;
            i = busPath.mFailTimes;
        } else {
            BusPathSection busPathSection = busPath.mPathSections[0];
            if (busPathSection == null) {
                return "";
            }
            trip = busPathSection.getNearRealTimeBusTrip();
            i = busPathSection.mFailTimes;
        }
        if (trip != null) {
            String str = trip.lindName == null ? "" : trip.lindName;
            int indexOf = str.indexOf("(");
            if (indexOf > 0) {
                str = str.substring(0, indexOf);
            }
            sb.append(str);
            sb.append(Token.SEPARATOR);
            if (a(i)) {
                sb.append(a(context, 1, trip.arrival, trip.station_left));
            } else {
                sb.append("--");
                sb.append(a(context, R.string.bus_stop));
                sb.append(a(context, R.string.arrive_after_arrived));
            }
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038 A[Catch:{ NumberFormatException -> 0x0040 }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0039 A[Catch:{ NumberFormatException -> 0x0040 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r2, java.lang.String r3, com.autonavi.minimap.route.bus.realtimebus.model.TripInfo r4, int r5) {
        /*
            if (r2 == 0) goto L_0x0065
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x0065
            if (r4 != 0) goto L_0x000b
            goto L_0x0065
        L_0x000b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r3)
            java.lang.String r3 = " "
            r0.append(r3)
            boolean r3 = a(r5)
            if (r3 == 0) goto L_0x0049
            r3 = 0
            java.lang.String r5 = r4.sitetime     // Catch:{ NumberFormatException -> 0x002f }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ NumberFormatException -> 0x002f }
            if (r5 == 0) goto L_0x0028
            goto L_0x002f
        L_0x0028:
            java.lang.String r5 = r4.sitetime     // Catch:{ NumberFormatException -> 0x002f }
            int r5 = defpackage.ahh.b(r5)     // Catch:{ NumberFormatException -> 0x002f }
            goto L_0x0030
        L_0x002f:
            r5 = 0
        L_0x0030:
            java.lang.String r1 = r4.stationleft     // Catch:{ NumberFormatException -> 0x0040 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ NumberFormatException -> 0x0040 }
            if (r1 == 0) goto L_0x0039
            goto L_0x0040
        L_0x0039:
            java.lang.String r4 = r4.stationleft     // Catch:{ NumberFormatException -> 0x0040 }
            int r4 = defpackage.ahh.b(r4)     // Catch:{ NumberFormatException -> 0x0040 }
            r3 = r4
        L_0x0040:
            r4 = 1
            java.lang.String r2 = a(r2, r4, r5, r3)
            r0.append(r2)
            goto L_0x0060
        L_0x0049:
            java.lang.String r3 = "--"
            r0.append(r3)
            int r3 = com.autonavi.minimap.R.string.bus_stop
            java.lang.String r3 = a(r2, r3)
            r0.append(r3)
            int r3 = com.autonavi.minimap.R.string.arrive_after_arrived
            java.lang.String r2 = a(r2, r3)
            r0.append(r2)
        L_0x0060:
            java.lang.String r2 = r0.toString()
            return r2
        L_0x0065:
            java.lang.String r2 = ""
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ebj.a(android.content.Context, java.lang.String, com.autonavi.minimap.route.bus.realtimebus.model.TripInfo, int):java.lang.String");
    }

    public static void a(TextView textView, String str, String str2) {
        if (textView != null) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                a(textView, false, 0);
                return;
            }
            textView.setText(a(textView.getContext(), str, str2, false, 0));
            if (str.contains("--")) {
                a(textView, false, R.drawable.bus_gif_01);
            } else {
                a(textView, true, R.anim.realtime_busline_gif);
            }
        }
    }

    public static void b(TextView textView, String str, String str2) {
        if (textView != null) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                a(textView, false, 0);
                return;
            }
            textView.setText(a(textView.getContext(), str, str2, true, 1));
            if (str.contains("--")) {
                a(textView, false, R.drawable.bus_gif_011);
            } else {
                a(textView, true, R.anim.bus_radar_tip_anim);
            }
        }
    }

    private static SpannableString a(Context context, String str, String str2, boolean z, int i) {
        int i2;
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(Token.SEPARATOR);
        sb.append(str2);
        String sb2 = sb.toString();
        SpannableString a2 = axs.a(context, sb2, R.drawable.bus_result_item_real_time_point);
        if (i == 1) {
            a2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.bus_radar_tip_label_color)), sb2.length() - str2.length(), sb2.length(), 33);
        } else {
            a2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_16)), sb2.length() - str2.length(), sb2.length(), 33);
        }
        int i3 = sb2.contains("--") ? sb2.indexOf("--") + 2 : z ? 0 : sb2.indexOf(Token.SEPARATOR) + 1;
        String a3 = a(context, R.string.about_to_arriving_in_station);
        String a4 = a(context, R.string.arrived_in_station);
        if (sb2.contains(a3)) {
            i3 = sb2.indexOf(a3);
            i2 = a3.length() + i3;
        } else if (sb2.contains(a4)) {
            i3 = sb2.indexOf(a4);
            i2 = a4.length() + i3;
        } else {
            i2 = sb2.indexOf(a(context, R.string.arrive_after_arrived));
        }
        if (i3 >= 0 && i2 > i3) {
            if (i == 1) {
                a2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.bus_radar_tip_label_color)), i3, i2, 33);
            } else {
                a2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_16)), i3, i2, 33);
            }
        }
        return a2;
    }

    private static void a(@NonNull TextView textView, boolean z, int i) {
        Drawable drawable = textView.getCompoundDrawables()[0];
        AnimationDrawable animationDrawable = drawable instanceof AnimationDrawable ? (AnimationDrawable) drawable : null;
        if (z) {
            if (animationDrawable == null) {
                Context context = textView.getContext();
                if (context != null) {
                    Drawable drawable2 = context.getResources().getDrawable(i);
                    if (drawable2 instanceof AnimationDrawable) {
                        animationDrawable = (AnimationDrawable) drawable2;
                        textView.setCompoundDrawablesWithIntrinsicBounds(animationDrawable, null, null, null);
                    }
                }
            }
            if (animationDrawable != null && !animationDrawable.isRunning()) {
                animationDrawable.start();
            }
        } else {
            if (animationDrawable != null && animationDrawable.isRunning()) {
                animationDrawable.stop();
            }
            if (i > 0) {
                textView.setCompoundDrawablesWithIntrinsicBounds(i, 0, 0, 0);
            }
        }
    }

    public static List<Trip> a(@NonNull BusPathSection busPathSection) {
        ArrayList arrayList = new ArrayList();
        if (busPathSection.tripList != null) {
            List<Trip> list = (List) busPathSection.tripList.clone();
            if (a(busPathSection.mFailTimes)) {
                String str = busPathSection.bus_id;
                for (Trip trip : list) {
                    if (str.equals(trip.lindID)) {
                        arrayList.add(trip);
                    }
                }
                Collections.sort(arrayList, new b());
            }
        }
        return arrayList;
    }

    public static void a(TextView textView, int i) {
        if (textView != null) {
            Context context = textView.getContext();
            if (context != null) {
                if (i > 0) {
                    if (i > 3) {
                        i = 3;
                    }
                    textView.setText(context.getString(R.string.real_time_arriving_bus_count, new Object[]{Integer.valueOf(i)}));
                    return;
                }
                textView.setText(a(context, R.string.real_time_no_arriving_bus));
            }
        }
    }

    public static void a(RouteArrivingBusInfoView[] routeArrivingBusInfoViewArr, List<Trip> list) {
        if (routeArrivingBusInfoViewArr != null && routeArrivingBusInfoViewArr.length == 3 && list != null) {
            for (int i = 0; i < 3; i++) {
                RouteArrivingBusInfoView routeArrivingBusInfoView = routeArrivingBusInfoViewArr[i];
                if (list.size() > i) {
                    Trip trip = list.get(i);
                    routeArrivingBusInfoView.showData(trip.arrival, trip.station_left);
                } else {
                    routeArrivingBusInfoView.showNoData();
                }
            }
        }
    }

    public static void a(RouteArrivingBusInfoView[] routeArrivingBusInfoViewArr, int i) {
        if (routeArrivingBusInfoViewArr != null && routeArrivingBusInfoViewArr.length == 3) {
            for (int i2 = 0; i2 < 3; i2++) {
                RouteArrivingBusInfoView routeArrivingBusInfoView = routeArrivingBusInfoViewArr[i2];
                if (i2 == 0) {
                    routeArrivingBusInfoView.showDataByStatus(i);
                } else {
                    routeArrivingBusInfoView.showNoData();
                }
            }
        }
    }

    public static void b(int i) {
        String str = i == 1 ? "实时公交刷新成功" : i == 2 ? "无法获取实时公交，请稍后重试" : i == 3 ? "请检查网络后重试" : null;
        if (str != null) {
            ToastHelper.showToast(str);
        }
    }
}
