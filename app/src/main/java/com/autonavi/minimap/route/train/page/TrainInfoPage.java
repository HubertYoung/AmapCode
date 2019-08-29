package com.autonavi.minimap.route.train.page;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.adapter.TrainInfoListAdapter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TrainInfoPage extends AbstractBasePage<ejl> {
    public ejd a;
    public Map<String, String> b = new HashMap();
    public TextView c;
    public ImageButton d;
    public View e;
    public ListView f;
    public TrainInfoListAdapter g;
    public Comparator<Entry<String, String>> h = new Comparator<Entry<String, String>>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return a((Entry) obj, (Entry) obj2);
        }

        private static int a(Entry<String, String> entry, Entry<String, String> entry2) {
            try {
                double doubleValue = Double.valueOf(entry.getValue()).doubleValue();
                double doubleValue2 = Double.valueOf(entry2.getValue()).doubleValue();
                if (doubleValue > doubleValue2) {
                    return -1;
                }
                if (doubleValue < doubleValue2) {
                    return 1;
                }
                if (entry.getKey().equals("seat_2") && entry2.getKey().equals("seat_yz")) {
                    return -1;
                }
                if (!entry.getKey().equals("seat_yz") || !entry2.getKey().equals("seat_2")) {
                    return 0;
                }
                return 1;
            } catch (NumberFormatException unused) {
                return 0;
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.train_info_layout);
    }

    public static String a(String str) {
        return "￥".concat(String.valueOf(str));
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ejl(this);
    }
}
