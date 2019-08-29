package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.search.imagepreview.page.ImageDetailPage;
import java.util.ArrayList;
import java.util.List;

/* renamed from: avr reason: default package */
/* compiled from: ImageDetailPageOpenerImpl */
public final class avr implements avp {
    private PageBundle a = new PageBundle();

    public final avp a(List<String> list) {
        ArrayList arrayList;
        PageBundle pageBundle = this.a;
        if (list == null || list.size() == 0) {
            arrayList = null;
        } else {
            arrayList = new ArrayList();
            int i = 1;
            for (String str : list) {
                cal cal = new cal();
                cal.e = str;
                cal.a = i;
                arrayList.add(cal);
                i++;
            }
        }
        pageBundle.putObject("data", arrayList);
        return this;
    }

    public final avp a() {
        this.a.putBoolean("show_btn", false);
        return this;
    }

    public final avp a(String str) {
        this.a.putString("type", str);
        return this;
    }

    public final avp a(int i) {
        this.a.putInt("jsindex", i);
        return this;
    }

    public final void a(bid bid) {
        bid.startPage(ImageDetailPage.class, this.a);
    }
}
