package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.entity.sugg.TipItem;
import java.util.ArrayList;
import java.util.List;

/* renamed from: afb reason: default package */
/* compiled from: SearchSuggUtils */
public final class afb {
    public static List<TipItem> a(List<TipItem> list, List<TipItem> list2, String str) {
        boolean z;
        if (list != null && list2 == null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        if (list2 != null) {
            int size = list2.size();
            int i = 0;
            for (int i2 = 0; i2 < size && i != 3; i2++) {
                TipItem tipItem = list2.get(i2);
                if (tipItem.name.indexOf(str) == 0) {
                    i++;
                    tipItem.type = 0;
                    arrayList.add(tipItem);
                }
            }
        }
        if (list != null && list.size() > 0) {
            int size2 = list.size();
            for (int i3 = 0; i3 < size2; i3++) {
                TipItem tipItem2 = list.get(i3);
                int i4 = 0;
                while (true) {
                    z = true;
                    if (i4 >= arrayList.size()) {
                        break;
                    }
                    TipItem tipItem3 = (TipItem) arrayList.get(i4);
                    if (!(TextUtils.equals(tipItem3.name, tipItem2.name) && TextUtils.equals(tipItem3.poiid, tipItem2.poiid) && (TextUtils.equals(tipItem3.adcode, tipItem2.adcode) || TextUtils.isEmpty(tipItem3.adcode) || TextUtils.isEmpty(tipItem2.adcode)))) {
                        i4++;
                    } else if (tipItem2.tipItemList == null || tipItem2.tipItemList.size() <= 0) {
                        tipItem3.poiinfo = tipItem2.poiinfo;
                        tipItem3.poiinfoColor = tipItem2.poiinfoColor;
                        tipItem3.taginfo = tipItem2.taginfo;
                        tipItem3.funcText = tipItem2.funcText;
                        if (tipItem2.isRating(tipItem2.richRating)) {
                            tipItem3.richRating = tipItem2.richRating;
                        } else {
                            tipItem3.richRating = "";
                        }
                        if (tipItem3.richRating == null || tipItem3.richRating.isEmpty()) {
                            tipItem3.numReview = "";
                        } else {
                            tipItem3.numReview = tipItem2.numReview;
                        }
                        z = false;
                    } else {
                        arrayList.remove(i4);
                    }
                }
                if (z) {
                    arrayList.add(tipItem2);
                }
            }
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }
}
