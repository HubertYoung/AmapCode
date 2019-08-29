package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.cityselect.page.SwitchCityNodePage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/* renamed from: ats reason: default package */
/* compiled from: SwitchCityNodePresenter */
public final class ats extends AbstractBasePresenter<SwitchCityNodePage> {
    public static String[] e = {AMapAppGlobal.getApplication().getString(R.string.common_used_city), "A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    public int a = 0;
    public int b = 0;
    public ArrayList<lj> c = null;
    public ArrayList<cew> d = null;
    private ArrayList<lj> f = new ArrayList<>();

    public ats(SwitchCityNodePage switchCityNodePage) {
        super(switchCityNodePage);
    }

    public final ArrayList<cew> a(ArrayList<lj> arrayList, boolean z) {
        if (z) {
            return a(arrayList);
        }
        return b(arrayList);
    }

    private static ArrayList<cew> a(ArrayList<lj> arrayList) {
        ArrayList<cew> arrayList2 = new ArrayList<>();
        cew cew = new cew();
        cew.b = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            cew.b.add(arrayList.get(i));
        }
        arrayList2.add(cew);
        return arrayList2;
    }

    private ArrayList<cew> b(ArrayList<lj> arrayList) {
        int i;
        ArrayList arrayList2 = new ArrayList();
        for (String str : e) {
            cew cew = new cew();
            cew.a = str;
            cew.b = new ArrayList<>();
            arrayList2.add(cew);
        }
        ((cew) arrayList2.get(0)).b.addAll(this.f);
        int i2 = 0;
        while (true) {
            i = 1;
            if (i2 >= arrayList.size()) {
                break;
            }
            lj ljVar = arrayList.get(i2);
            String str2 = ljVar.b;
            if (str2 != null) {
                String substring = str2.substring(0, 1);
                while (true) {
                    if (i >= e.length) {
                        break;
                    } else if (e[i].equalsIgnoreCase(substring)) {
                        ((cew) arrayList2.get(i)).b.add(ljVar);
                        break;
                    } else {
                        i++;
                    }
                }
            }
            i2++;
        }
        ArrayList<cew> arrayList3 = new ArrayList<>();
        if (((cew) arrayList2.get(0)).b.size() > 0) {
            arrayList3.add(arrayList2.get(0));
        }
        while (i < arrayList2.size()) {
            ArrayList<lj> arrayList4 = ((cew) arrayList2.get(i)).b;
            Collections.sort(arrayList4, new atr());
            if (arrayList4.size() > 0) {
                arrayList3.add(arrayList2.get(i));
            }
            i++;
        }
        return arrayList3;
    }

    public final ArrayList<lj> a() {
        ArrayList<lj> b2 = li.a().b();
        try {
            int size = b2.size();
            lj[] ljVarArr = new lj[(this.b == 1 ? 14 : 4)];
            for (int i = 0; i < size; i++) {
                lj ljVar = b2.get(i);
                if (this.b == 1) {
                    this.f.clear();
                    String[] strArr = {"110000", "310000", "440100", "440300", "330100", "510100", "320100", "350100", "420100", "120000", "500000", "430100", "410100", "610100"};
                    for (int i2 = 0; i2 < 14; i2++) {
                        if (String.valueOf(ljVar.j).equals(strArr[i2])) {
                            ljVarArr[i2] = ljVar;
                        }
                    }
                } else {
                    this.f.clear();
                    String[] split = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString("hotcity", "110000,310000,440100,440300").split(",");
                    for (int i3 = 0; i3 < split.length; i3++) {
                        if (String.valueOf(ljVar.j).equals(split[i3])) {
                            ljVarArr[i3] = ljVar;
                        }
                    }
                }
            }
            this.f = new ArrayList<>(Arrays.asList(ljVarArr));
            Iterator<lj> it = this.f.iterator();
            while (it.hasNext()) {
                if (it.next() == null) {
                    it.remove();
                }
            }
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
        return b2;
    }

    public static void a(String str, ArrayList<String> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(arrayList);
        arrayList.clear();
        int i = 0;
        arrayList.add(0, str);
        while (i < 3) {
            int i2 = i + 1;
            arrayList.add(i2, arrayList2.get(i));
            i = i2;
        }
    }

    public final synchronized void a(String str) {
        boolean z = true;
        if (TextUtils.isEmpty(str)) {
            this.d = a(this.c, false);
            z = false;
        } else {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (int i = 0; i < this.c.size(); i++) {
                lj ljVar = this.c.get(i);
                if (ljVar.c.equalsIgnoreCase(str)) {
                    arrayList2.add(ljVar);
                } else if (ljVar.c.toLowerCase().indexOf(str) == 0) {
                    arrayList2.add(ljVar);
                } else if (ljVar.a.indexOf(str) == 0) {
                    arrayList4.add(ljVar);
                } else if (ljVar.b.toLowerCase().indexOf(str) == 0) {
                    arrayList3.add(ljVar);
                }
            }
            arrayList.addAll(arrayList3);
            arrayList.addAll(arrayList2);
            arrayList.addAll(arrayList4);
            this.d = a(arrayList, true);
        }
        ((SwitchCityNodePage) this.mPage).a(this.d, z);
    }
}
