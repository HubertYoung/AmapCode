package defpackage;

import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: ejw reason: default package */
/* compiled from: TrainSeatUtil */
public final class ejw {
    public static final int[] a = {10, 11, 12, 13, 8, 7, 21, 9, 16, 18, 3, 1, 20, 6, 4, 5, 2};

    public static String a(int i) {
        switch (i) {
            case 1:
                return "动软";
            case 2:
                return "无座";
            case 3:
                return "动卧";
            case 4:
                return "软包";
            case 5:
                return "观光";
            case 6:
                return "特软";
            case 7:
                return "一等";
            case 8:
                return "二等";
            case 9:
                return "特等";
            case 10:
                return "硬座";
            case 11:
                return "软座";
            case 12:
                return "软座";
            case 13:
                return "软座";
            case 16:
                return "硬卧";
            case 18:
                return "软卧";
            case 20:
                return "高软";
            case 21:
                return "商务";
            default:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_OTHER;
        }
    }

    public static ArrayList<eiw> a(ArrayList<eiw> arrayList) {
        int i;
        eiw[] eiwArr = new eiw[a.length];
        ArrayList arrayList2 = new ArrayList();
        ArrayList<eiw> arrayList3 = new ArrayList<>();
        Iterator<eiw> it = arrayList.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            eiw next = it.next();
            int i2 = 0;
            while (true) {
                if (i2 >= a.length) {
                    break;
                } else if (a[i2] == next.e) {
                    eiwArr[i2] = next;
                    i = 1;
                    break;
                } else {
                    i2++;
                }
            }
            if (i == 0) {
                arrayList2.add(next);
            }
        }
        int length = eiwArr.length;
        while (i < length) {
            eiw eiw = eiwArr[i];
            if (eiw != null) {
                arrayList3.add(eiw);
            }
            i++;
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            eiw eiw2 = (eiw) it2.next();
            if (eiw2 != null) {
                arrayList3.add(eiw2);
            }
        }
        return arrayList3;
    }
}
