package com.autonavi.minimap.drive.navi.navitts;

import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.autonavi.bundle.account.api.IAccountService;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.xidea.el.json.JSONDecoder;
import org.xidea.el.json.JSONEncoder;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepImplementations
@KeepName
public class NaviRecordUtil {

    public static final class a {
        public String a;
        public String b;
        public String c;
        public int d;
        public long e;
        public long f;
        public int g;
        public String h;
        public int i;
        public long j;

        public a() {
        }

        public a(String str, long j2, String str2) {
            this.a = str;
            this.b = str;
            this.c = str;
            this.d = 50;
            this.e = j2;
            this.f = j2;
            this.g = 4;
            this.h = str2;
            this.i = 100;
            this.j = j2;
        }

        public static String a() {
            IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
            if (iAccountService == null) {
                return "";
            }
            ant e2 = iAccountService.e();
            if (e2 == null) {
                return "";
            }
            return (!iAccountService.a() || e2.b == null) ? "" : e2.b;
        }
    }

    public static void convertToAddNaviTts(String str) {
        File file = new File(dgu.c(PathManager.a().b(DirType.DRIVE_VOICE)), str);
        if (file.exists()) {
            save(str, getFolderSize(file), file.getAbsolutePath());
        }
    }

    private static void save(String str, long j, String str2) {
        a aVar = new a(str, j, str2);
        String c = dgu.c(PathManager.a().b(DirType.DRIVE_VOICE));
        String encode = JSONEncoder.encode(aVar);
        if (encode != null) {
            Set<String> e = dfo.e(c);
            String str3 = null;
            Iterator<String> it = e.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                a aVar2 = (a) JSONDecoder.decode(next, a.class);
                if (aVar2 != null && aVar.a.equals(aVar2.a)) {
                    str3 = next;
                    break;
                }
            }
            if (str3 != null) {
                e.remove(str3);
            }
            e.add(encode);
            dfo.a(c, e);
        }
    }

    public static void init() {
        String c = dgu.c(PathManager.a().b(DirType.DRIVE_VOICE));
        File file = new File(c);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory() && (file2.getName().length() < 2 || !file2.getName().substring(0, 2).equals("__"))) {
                        long folderSize = getFolderSize(file2);
                        if (folderSize != 0) {
                            linkedHashSet.add(JSONEncoder.encode(new a(file2.getName(), folderSize, file2.getAbsolutePath())));
                        } else {
                            file2.delete();
                        }
                    }
                }
            }
        }
        dfo.a(c, (Set<String>) linkedHashSet);
    }

    public static ArrayList<a> getCustomVoices() {
        Set<String> e = dfo.e(dgu.c(PathManager.a().b(DirType.DRIVE_VOICE)));
        ArrayList arrayList = new ArrayList();
        for (String decode : e) {
            a aVar = (a) JSONDecoder.decode(decode, a.class);
            if (aVar != null && !TextUtils.isEmpty(aVar.h)) {
                File file = new File(aVar.h);
                if (file.exists() && (file.getName().length() < 2 || !file.getName().substring(0, 2).equals("__"))) {
                    arrayList.add(aVar);
                }
            }
        }
        return getSortedCustomVoice(arrayList);
    }

    private static ArrayList<a> getSortedCustomVoice(List<a> list) {
        a[] aVarArr = (a[]) list.toArray(new a[0]);
        Arrays.sort(aVarArr, new Comparator<a>() {
            public final /* synthetic */ int compare(Object obj, Object obj2) {
                return ((a) obj).b.compareTo(((a) obj2).b);
            }
        });
        ArrayList<a> arrayList = new ArrayList<>();
        for (a add : aVarArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static void delete(dgl dgl) {
        String str;
        String c = dgu.c(PathManager.a().b(DirType.DRIVE_VOICE));
        Set<String> e = dfo.e(c);
        Iterator<String> it = e.iterator();
        while (true) {
            if (!it.hasNext()) {
                str = null;
                break;
            }
            str = it.next();
            a aVar = (a) JSONDecoder.decode(str, a.class);
            if (aVar != null && aVar.a.equals(dgl.a.c)) {
                File file = new File(dgl.a());
                if (file.exists()) {
                    deleteFile(file);
                }
            }
        }
        e.remove(str);
        dfo.a(c, e);
    }

    private static boolean deleteFile(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            if (list != null) {
                for (String file2 : list) {
                    if (!deleteFile(new File(file, file2))) {
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    public static void convertToModifyNaviTts(dgl dgl, String str) {
        File file = new File(dgu.c(PathManager.a().b(DirType.DRIVE_VOICE)), str);
        if (file.exists()) {
            if (dgl != null) {
                String str2 = dgl.a.c;
                String g = dfo.g();
                if (str2 != null && !str2.equals(str)) {
                    delete(dgl);
                }
                if (str2 != null && str2.equals(g)) {
                    dfo.d(str);
                }
            }
            save(str, getFolderSize(file), file.getAbsolutePath());
        }
    }

    public static long getFolderSize(File file) {
        long j = 0;
        try {
            LinkedList linkedList = new LinkedList();
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return 0;
            }
            long j2 = 0;
            int i = 0;
            while (i < listFiles.length) {
                try {
                    if (listFiles[i].isDirectory()) {
                        linkedList.add(listFiles[i]);
                    } else {
                        j2 += listFiles[i].length();
                    }
                    i++;
                } catch (Exception e) {
                    e = e;
                    j = j2;
                    e.printStackTrace();
                    return j;
                }
            }
            loop1:
            while (true) {
                j = j2;
                while (!linkedList.isEmpty()) {
                    File file2 = (File) linkedList.removeFirst();
                    if (file2.isDirectory()) {
                        File[] listFiles2 = file2.listFiles();
                        if (listFiles2 != null) {
                            j2 = j;
                            for (int i2 = 0; i2 < listFiles2.length; i2++) {
                                if (listFiles2[i2].isDirectory()) {
                                    linkedList.add(listFiles2[i2]);
                                } else {
                                    j2 += listFiles2[i2].length();
                                }
                            }
                        }
                    } else {
                        j += file2.length();
                    }
                }
                break loop1;
            }
            return j;
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return j;
        }
    }
}
