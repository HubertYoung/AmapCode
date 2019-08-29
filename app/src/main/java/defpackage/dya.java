package defpackage;

import android.content.res.Resources;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.widget.wheel.TimePickerAdapter;
import com.autonavi.minimap.R;

/* renamed from: dya reason: default package */
/* compiled from: RouteRealTimeDatePickerAdapter */
public final class dya implements TimePickerAdapter {
    private int a = 0;
    private int b = 1;

    public final int getCurrentIndex() {
        return 0;
    }

    public final String getItem(int i) {
        Resources resources = AMapAppGlobal.getApplication().getApplicationContext().getResources();
        if (i == 0) {
            return resources.getString(R.string.date_am);
        }
        return i == 1 ? resources.getString(R.string.date_pm) : "";
    }

    public final int getItemsCount() {
        return (this.b - this.a) + 1;
    }

    public final int getMaximumLength() {
        int length = Integer.toString(Math.max(Math.abs(this.b), Math.abs(this.a))).length();
        return this.a < 0 ? length + 1 : length;
    }
}
