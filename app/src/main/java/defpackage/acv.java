package defpackage;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.HashMap;

/* renamed from: acv reason: default package */
/* compiled from: WidgetIdRes */
public final class acv {
    private static HashMap<Integer, Integer> a;

    public static int a(int i) {
        switch (i) {
            case 64:
            case 65:
            case 66:
                return 0;
            default:
                switch (i) {
                    case 80:
                    case 81:
                    case 82:
                        return 1;
                    default:
                        switch (i) {
                            case 96:
                            case 97:
                            case 98:
                                return 2;
                            default:
                                return -1;
                        }
                }
        }
    }

    static {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        a = hashMap;
        hashMap.put(Integer.valueOf(1), Integer.valueOf(R.id.route_edit_back));
        a.put(Integer.valueOf(2), Integer.valueOf(R.id.route_edit_add));
        a.put(Integer.valueOf(3), Integer.valueOf(R.id.route_edit_exchange));
        a.put(Integer.valueOf(4), Integer.valueOf(R.id.route_edit_frame));
        a.put(Integer.valueOf(16), Integer.valueOf(R.id.route_edit_start));
        a.put(Integer.valueOf(17), Integer.valueOf(R.id.route_edit_start_text));
        a.put(Integer.valueOf(32), Integer.valueOf(R.id.route_edit_end));
        a.put(Integer.valueOf(33), Integer.valueOf(R.id.route_edit_end_text));
        a.put(Integer.valueOf(48), Integer.valueOf(R.id.route_edit_pre_mid));
        a.put(Integer.valueOf(49), Integer.valueOf(R.id.route_edit_pre_mid_text));
        a.put(Integer.valueOf(64), Integer.valueOf(R.id.route_edit_mid));
        a.put(Integer.valueOf(65), Integer.valueOf(R.id.route_edit_mid_text));
        a.put(Integer.valueOf(66), Integer.valueOf(R.id.route_edit_mid_remove));
        a.put(Integer.valueOf(80), Integer.valueOf(R.id.route_edit_mid2));
        a.put(Integer.valueOf(81), Integer.valueOf(R.id.route_edit_mid2_text));
        a.put(Integer.valueOf(82), Integer.valueOf(R.id.route_edit_mid2_remove));
        a.put(Integer.valueOf(96), Integer.valueOf(R.id.route_edit_mid3));
        a.put(Integer.valueOf(97), Integer.valueOf(R.id.route_edit_mid3_text));
        a.put(Integer.valueOf(98), Integer.valueOf(R.id.route_edit_mid3_remove));
        a.put(Integer.valueOf(256), Integer.valueOf(R.id.route_edit_summary_start));
        a.put(Integer.valueOf(512), Integer.valueOf(R.id.route_edit_summary_end));
        a.put(Integer.valueOf(768), Integer.valueOf(R.id.route_edit_summary_mid));
    }

    private static int b(int i) {
        Integer num = a.get(Integer.valueOf(i));
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public static View a(@NonNull View view, int i) {
        return e(view, i);
    }

    public static ImageView b(@NonNull View view, int i) {
        return (ImageView) e(view, i);
    }

    public static TextView c(@NonNull View view, int i) {
        return (TextView) e(view, i);
    }

    public static EditText d(@NonNull View view, int i) {
        return (EditText) e(view, i);
    }

    private static <T extends View> T e(@NonNull View view, int i) {
        int b = b(i);
        if (b > 0) {
            return view.findViewById(b);
        }
        return null;
    }
}
