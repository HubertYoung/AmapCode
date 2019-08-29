package defpackage;

import android.widget.CheckBox;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.mine.page.setting.sysabout.page.ConfigPage;

/* renamed from: cgr reason: default package */
/* compiled from: ConfigPresenter */
public final class cgr extends AbstractBasePresenter<ConfigPage> {
    public MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);
    public CheckBox b;
    public CheckBox c;
    private CheckBox d;
    private CheckBox e;
    private CheckBox f;
    private CheckBox g;
    private CheckBox h;
    private CheckBox i;

    public cgr(ConfigPage configPage) {
        super(configPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final ON_BACK_TYPE onBackPressed() {
        return super.onBackPressed();
    }

    @android.annotation.SuppressLint({"CutPasteId"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.view.View r3, java.lang.String r4) {
        /*
            r2 = this;
            int r0 = r4.hashCode()
            r1 = 1
            switch(r0) {
                case -1652137401: goto L_0x003b;
                case -1581073276: goto L_0x0031;
                case -332603157: goto L_0x0027;
                case 2995364: goto L_0x001d;
                case 3432931: goto L_0x0013;
                case 108704329: goto L_0x0009;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x0045
        L_0x0009:
            java.lang.String r0 = "route"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0045
            r0 = 1
            goto L_0x0046
        L_0x0013:
            java.lang.String r0 = "paas"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0045
            r0 = 0
            goto L_0x0046
        L_0x001d:
            java.lang.String r0 = "ajx3"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0045
            r0 = 5
            goto L_0x0046
        L_0x0027:
            java.lang.String r0 = "basemap"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0045
            r0 = 3
            goto L_0x0046
        L_0x0031:
            java.lang.String r0 = "sharetrip"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0045
            r0 = 2
            goto L_0x0046
        L_0x003b:
            java.lang.String r0 = "infoservice"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0045
            r0 = 4
            goto L_0x0046
        L_0x0045:
            r0 = -1
        L_0x0046:
            switch(r0) {
                case 0: goto L_0x0082;
                case 1: goto L_0x0077;
                case 2: goto L_0x006c;
                case 3: goto L_0x0061;
                case 4: goto L_0x0056;
                case 5: goto L_0x004b;
                default: goto L_0x0049;
            }
        L_0x0049:
            r3 = 0
            goto L_0x008c
        L_0x004b:
            int r0 = com.autonavi.minimap.R.id.cb_alc
            android.view.View r3 = r3.findViewById(r0)
            android.widget.CheckBox r3 = (android.widget.CheckBox) r3
            r2.i = r3
            goto L_0x008c
        L_0x0056:
            int r0 = com.autonavi.minimap.R.id.cb_alc
            android.view.View r3 = r3.findViewById(r0)
            android.widget.CheckBox r3 = (android.widget.CheckBox) r3
            r2.h = r3
            goto L_0x008c
        L_0x0061:
            int r0 = com.autonavi.minimap.R.id.cb_alc
            android.view.View r3 = r3.findViewById(r0)
            android.widget.CheckBox r3 = (android.widget.CheckBox) r3
            r2.g = r3
            goto L_0x008c
        L_0x006c:
            int r0 = com.autonavi.minimap.R.id.cb_alc
            android.view.View r3 = r3.findViewById(r0)
            android.widget.CheckBox r3 = (android.widget.CheckBox) r3
            r2.f = r3
            goto L_0x008c
        L_0x0077:
            int r0 = com.autonavi.minimap.R.id.cb_alc
            android.view.View r3 = r3.findViewById(r0)
            android.widget.CheckBox r3 = (android.widget.CheckBox) r3
            r2.e = r3
            goto L_0x008c
        L_0x0082:
            int r0 = com.autonavi.minimap.R.id.cb_alc
            android.view.View r3 = r3.findViewById(r0)
            android.widget.CheckBox r3 = (android.widget.CheckBox) r3
            r2.d = r3
        L_0x008c:
            r3.setText(r4)
            com.amap.bundle.mapstorage.MapSharePreference r0 = r2.a
            boolean r0 = r0.getBooleanValue(r4, r1)
            r3.setChecked(r0)
            com.autonavi.mine.page.setting.sysabout.presenter.ConfigPresenter$3 r0 = new com.autonavi.mine.page.setting.sysabout.presenter.ConfigPresenter$3
            r0.<init>(r2, r4)
            r3.setOnCheckedChangeListener(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cgr.a(android.view.View, java.lang.String):void");
    }
}
