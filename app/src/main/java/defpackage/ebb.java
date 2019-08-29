package defpackage;

import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

/* renamed from: ebb reason: default package */
/* compiled from: RouteDebugUtil */
public final class ebb {
    public static long a = (System.currentTimeMillis() - 2000);

    private ebb() {
    }

    public static ebb a() {
        return new ebb();
    }

    public static boolean b() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/testugcbusentrancesimtrue");
        return new File(sb.toString()).exists();
    }

    public static boolean c() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/testvoicesimtrue");
        return new File(sb.toString()).exists();
    }

    public static ArrayList<String> d() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("公交语音播报开启");
        arrayList.add("上车");
        arrayList.add("两站后下车");
        arrayList.add("下车");
        arrayList.add("公交导航结束");
        arrayList.add("公交偏离路线");
        return arrayList;
    }

    public static ArrayList<String> e() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("步行起始音播报");
        arrayList.add("起始反向提示");
        arrayList.add("前方过马路");
        arrayList.add("前方过红绿灯");
        arrayList.add("通过广场");
        arrayList.add("通过公园");
        arrayList.add("GPS信号弱");
        arrayList.add("步行导航结束");
        arrayList.add("偏航提示（微偏）");
        arrayList.add("偏航提示（全偏）");
        return arrayList;
    }

    public static ArrayList<String> f() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("，《。");
        arrayList.add("，》。");
        arrayList.add("，“。");
        arrayList.add("，”。");
        arrayList.add("，；。");
        arrayList.add("，、。");
        return arrayList;
    }
}
