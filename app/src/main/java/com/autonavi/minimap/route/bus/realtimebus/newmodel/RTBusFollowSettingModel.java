package com.autonavi.minimap.route.bus.realtimebus.newmodel;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.net.parser.AosRealTimeAttentionCleanParser;
import com.autonavi.minimap.route.bus.realtimebus.net.parser.AosRealTimeAttentionParser;
import java.util.ArrayList;
import java.util.List;

public final class RTBusFollowSettingModel {
    Context a;
    public bso b;
    MapSharePreference c = new MapSharePreference(SharePreferenceName.user_route_method_info);
    public a d;

    public class SubscribeCallback implements Callback<AosRealTimeAttentionParser> {
        private SubscribeCallback() {
        }

        public /* synthetic */ SubscribeCallback(RTBusFollowSettingModel rTBusFollowSettingModel, byte b) {
            this();
        }

        public void callback(AosRealTimeAttentionParser aosRealTimeAttentionParser) {
            if (RTBusFollowSettingModel.this.d != null) {
                if (aosRealTimeAttentionParser.errorCode == 1) {
                    RTBusFollowSettingModel.this.d.a();
                    return;
                }
                RTBusFollowSettingModel.this.d.a(aosRealTimeAttentionParser.getErrorDesc(aosRealTimeAttentionParser.errorCode));
            }
        }

        public void error(Throwable th, boolean z) {
            if (RTBusFollowSettingModel.this.d != null) {
                RTBusFollowSettingModel.this.d.a(RTBusFollowSettingModel.this.a.getString(R.string.busline_attention_save_error));
            }
        }
    }

    public interface a {
        void a();

        void a(String str);

        void b();

        void c();
    }

    public RTBusFollowSettingModel(Context context) {
        this.a = context;
        this.b = bso.a();
    }

    public static String a(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (Integer next : list) {
            if (sb.toString().isEmpty()) {
                sb.append(next.intValue() + 1);
            } else {
                sb.append(',');
                sb.append(next.intValue() + 1);
            }
        }
        return sb.toString();
    }

    public static List<Integer> a(String str) {
        int i;
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            for (String b2 : str.split(",")) {
                try {
                    i = ahh.b(b2);
                } catch (NumberFormatException unused) {
                    i = 1;
                }
                arrayList.add(Integer.valueOf(i - 1));
            }
        }
        return arrayList;
    }

    private String c(List<Integer> list) {
        if (this.a == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (list.toString().contains("0")) {
            sb.append(this.a.getString(R.string.busline_setting_day_one));
            sb.append(",");
        }
        if (list.toString().contains("1")) {
            sb.append(this.a.getString(R.string.busline_setting_day_two));
            sb.append(",");
        }
        if (list.toString().contains("2")) {
            sb.append(this.a.getString(R.string.busline_setting_day_three));
            sb.append(",");
        }
        if (list.toString().contains("3")) {
            sb.append(this.a.getString(R.string.busline_setting_day_four));
            sb.append(",");
        }
        if (list.toString().contains("4")) {
            sb.append(this.a.getString(R.string.busline_setting_day_five));
            sb.append(",");
        }
        if (list.toString().contains("5")) {
            sb.append(this.a.getString(R.string.busline_setting_day_six));
            sb.append(",");
        }
        if (list.toString().contains("6")) {
            sb.append(this.a.getString(R.string.busline_setting_day_seven));
            sb.append(",");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public final String b(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return this.a.getString(R.string.busline_setting_day_ever);
        }
        if (list.size() == 7) {
            return this.a.getString(R.string.busline_setting_day_everyday);
        }
        if (list.size() != 5) {
            return c(list);
        }
        boolean z = true;
        for (int i = 0; i < 5; i++) {
            if (!list.toString().contains(String.valueOf(i))) {
                z = false;
            }
        }
        if (z) {
            return this.a.getString(R.string.busline_setting_day_workday);
        }
        return c(list);
    }

    public final boolean a() {
        return this.c.getBooleanValue("realbus_position_push_clean", true);
    }

    public final void a(btd btd) {
        this.b.a(btd);
        this.c.putBooleanValue("realbus_position_attention_need_roaledDB", true);
    }

    public final void b(btd btd) {
        this.b.b(btd);
        this.c.putBooleanValue("realbus_position_attention_need_roaledDB", true);
    }

    public final void b(String str) {
        this.b.b(str);
        this.c.putBooleanValue("realbus_position_attention_need_roaledDB", true);
    }

    public final void b() {
        dyj.a(new Callback<AosRealTimeAttentionCleanParser>() {
            public void callback(AosRealTimeAttentionCleanParser aosRealTimeAttentionCleanParser) {
                if (aosRealTimeAttentionCleanParser.errorCode == 1) {
                    RTBusFollowSettingModel.this.c.putBooleanValue("realbus_position_push_clean", false);
                    if (RTBusFollowSettingModel.this.d != null) {
                        RTBusFollowSettingModel.this.d.b();
                    }
                } else if (RTBusFollowSettingModel.this.d != null) {
                    RTBusFollowSettingModel.this.d.c();
                }
            }

            public void error(Throwable th, boolean z) {
                if (RTBusFollowSettingModel.this.d != null) {
                    RTBusFollowSettingModel.this.d.c();
                }
            }
        });
    }
}
