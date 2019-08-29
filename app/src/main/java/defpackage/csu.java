package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.autonavi.minimap.R;

/* renamed from: csu reason: default package */
/* compiled from: TrafficItemButtonFactory */
public final class csu {
    @NonNull
    public static SparseArray<View> a(@NonNull Context context, int i, boolean z) {
        SparseArray<View> sparseArray = new SparseArray<>();
        sparseArray.put(1, a(context, 1));
        sparseArray.put(2, a(context, 2));
        if (i <= 0 || z) {
            return sparseArray;
        }
        if (css.a(i)) {
            sparseArray.put(4, a(context, 4));
        } else if (css.b(i)) {
            sparseArray.put(8, a(context, 8));
        } else if (css.c(i)) {
            sparseArray.put(16, a(context, 16));
        }
        return sparseArray;
    }

    private static View a(Context context, int i) {
        View view;
        LayoutInflater from = LayoutInflater.from(context);
        Resources resources = context.getResources();
        if (i == 4) {
            View inflate = from.inflate(R.layout.item_view_traffic_layout, null);
            ((TextView) inflate.findViewById(R.id.item_view_traffic_text)).setText(resources.getString(R.string.traffic_report_update));
            return inflate;
        } else if (i == 8) {
            View inflate2 = from.inflate(R.layout.item_view_traffic_layout, null);
            ((TextView) inflate2.findViewById(R.id.item_view_traffic_text)).setText(resources.getString(R.string.traffic_report_open));
            return inflate2;
        } else if (i != 16) {
            switch (i) {
                case 1:
                    view = from.inflate(R.layout.item_view_traffic_layout, null);
                    TextView textView = (TextView) view.findViewById(R.id.item_view_traffic_text);
                    textView.setCompoundDrawablePadding(agn.a(context, 8.0f));
                    Drawable drawable = resources.getDrawable(R.drawable.icon_traffic_like);
                    drawable.setBounds(0, 0, agn.a(context, 20.0f), agn.a(context, 20.0f));
                    textView.setCompoundDrawables(drawable, null, null, null);
                    break;
                case 2:
                    view = from.inflate(R.layout.item_view_traffic_layout, null);
                    TextView textView2 = (TextView) view.findViewById(R.id.item_view_traffic_text);
                    textView2.setCompoundDrawablePadding(agn.a(context, 8.0f));
                    textView2.setCompoundDrawablePadding(agn.a(context, 8.0f));
                    Drawable drawable2 = resources.getDrawable(R.drawable.icon_traffic_dislike);
                    drawable2.setBounds(0, 0, agn.a(context, 20.0f), agn.a(context, 20.0f));
                    textView2.setCompoundDrawables(drawable2, null, null, null);
                    break;
                default:
                    return null;
            }
            return view;
        } else {
            View inflate3 = from.inflate(R.layout.item_view_traffic_layout, null);
            ((TextView) inflate3.findViewById(R.id.item_view_traffic_text)).setText(resources.getString(R.string.traffic_report_complete));
            return inflate3;
        }
    }
}
