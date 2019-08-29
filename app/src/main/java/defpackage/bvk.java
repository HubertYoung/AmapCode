package defpackage;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings({"IS2_INCONSISTENT_SYNC"})
/* renamed from: bvk reason: default package */
/* compiled from: LocalImageHelper */
public final class bvk {
    /* access modifiers changed from: private */
    public static final String[] j = {"_id", "_data", CaptureParam.ORIENTATION_MODE, "datetaken", "latitude", "longitude", "title", "width", "height"};
    private static final String[] k = {"_id", "_data", "image_id"};
    public Context a;
    public List<ImageInfo> b = new ArrayList();
    public boolean c = false;
    public b d;
    public int e;
    public int f;
    public boolean g = true;
    public boolean h = false;
    public List<ImageInfo> i;
    private int l = 48;

    /* renamed from: bvk$a */
    /* compiled from: LocalImageHelper */
    public interface a {
        void a(List<cak> list, Map<String, List<ImageInfo>> map, List<ImageInfo> list2, boolean z);
    }

    /* renamed from: bvk$b */
    /* compiled from: LocalImageHelper */
    public interface b {
        void a(Map<String, List<ImageInfo>> map);
    }

    public bvk(Context context) {
        this.a = context;
    }

    public final List<ImageInfo> a() {
        ArrayList arrayList = new ArrayList();
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            try {
                arrayList.add(this.b.get(i2));
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    public final synchronized void a(final a aVar) {
        if (!this.c) {
            this.c = true;
            new Thread(new Runnable() {
                public final void run() {
                    StringBuilder sb = new StringBuilder();
                    if (bvk.this.g) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(bvk.j[4]);
                        sb2.append(" IS NOT NULL and ");
                        sb2.append(bvk.j[5]);
                        sb2.append(" IS NOT NULL");
                        sb.append(sb2.toString());
                    }
                    if (bvk.this.e > 0) {
                        if (sb.length() > 0) {
                            sb.append(" and ");
                        }
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(bvk.j[7]);
                        sb3.append(" > ");
                        sb3.append(bvk.this.e);
                        sb.append(sb3.toString());
                    }
                    if (bvk.this.f > 0) {
                        if (sb.length() > 0) {
                            sb.append(" and ");
                        }
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(bvk.j[8]);
                        sb4.append(" > ");
                        sb4.append(bvk.this.f);
                        sb.append(sb4.toString());
                    }
                    Cursor query = bvk.this.a.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, bvk.j, sb.toString(), null, "datetaken DESC");
                    if (query.getCount() == 0) {
                        StringBuilder sb5 = new StringBuilder();
                        if (bvk.this.g) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(bvk.j[4]);
                            sb6.append(" IS NOT NULL and ");
                            sb6.append(bvk.j[5]);
                            sb6.append(" IS NOT NULL");
                            sb5.append(sb6.toString());
                        }
                        query = bvk.this.a.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, bvk.j, sb5.toString(), null, "datetaken DESC");
                    }
                    bvk.a(bvk.this, query, aVar);
                    if (query != null) {
                        query.close();
                    }
                    bvk.this.c = false;
                }
            }).start();
        }
    }

    public static void a(Map<String, List<ImageInfo>> map, ImageInfo imageInfo, String str) {
        if (map.containsKey(str)) {
            map.get(str).add(imageInfo);
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(imageInfo);
        map.put(str, arrayList);
    }

    private static int a(Cursor cursor) {
        try {
            return cursor.getInt(cursor.getColumnIndexOrThrow("width"));
        } catch (IllegalArgumentException e2) {
            AMapLog.error("paas.photoupload", "LocalImageHelper", e2.getMessage());
            return 0;
        }
    }

    private static int b(Cursor cursor) {
        try {
            return cursor.getInt(cursor.getColumnIndexOrThrow("height"));
        } catch (IllegalArgumentException e2) {
            AMapLog.error("paas.photoupload", "LocalImageHelper", e2.getMessage());
            return 0;
        }
    }

    static /* synthetic */ void a(bvk bvk, Cursor cursor, a aVar) {
        GeoPoint geoPoint;
        bvk bvk2 = bvk;
        Cursor cursor2 = cursor;
        a aVar2 = aVar;
        if (cursor2 == null || cursor.getCount() == 0) {
            aVar2.a(null, null, null, true);
            StringBuilder sb = new StringBuilder("loadLoacalImageCallback:");
            sb.append(bvk2.b.size());
            AMapLog.i("lb", sb.toString());
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap = new HashMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (cursor.moveToNext()) {
            String string = cursor2.getString(cursor2.getColumnIndexOrThrow("_data"));
            if (!TextUtils.isEmpty(string)) {
                File file = new File(string);
                if (file.exists()) {
                    String uri = Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(Integer.toString(cursor2.getInt(cursor2.getColumnIndexOrThrow("_id")))).build().toString();
                    if (!TextUtils.isEmpty(uri)) {
                        ImageInfo imageInfo = new ImageInfo();
                        imageInfo.b = string;
                        imageInfo.a = uri;
                        imageInfo.c = cursor2.getString(cursor2.getColumnIndexOrThrow("title"));
                        int i2 = cursor2.getInt(cursor2.getColumnIndexOrThrow(CaptureParam.ORIENTATION_MODE));
                        if (i2 != 0) {
                            i2 += 180;
                        }
                        imageInfo.f = 360 - i2;
                        long j2 = cursor2.getLong(cursor2.getColumnIndexOrThrow("datetaken"));
                        imageInfo.h = bvj.b(j2);
                        cak cak = new cak();
                        String a2 = bvj.a(j2);
                        cak.a = a2;
                        if (!arrayList.contains(cak)) {
                            arrayList.add(cak);
                        }
                        int columnIndexOrThrow = cursor2.getColumnIndexOrThrow("latitude");
                        int columnIndexOrThrow2 = cursor2.getColumnIndexOrThrow("longitude");
                        float f2 = cursor2.getFloat(columnIndexOrThrow);
                        float f3 = cursor2.getFloat(columnIndexOrThrow2);
                        if (f2 == 0.0f || f3 == 0.0f) {
                            geoPoint = null;
                        } else {
                            geoPoint = bvj.a((double) f3, (double) f2);
                        }
                        if (geoPoint != null) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(geoPoint.getLongitude());
                            imageInfo.i = sb2.toString();
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(geoPoint.getLatitude());
                            imageInfo.j = sb3.toString();
                        }
                        imageInfo.n = a(cursor);
                        imageInfo.o = b(cursor);
                        boolean z = false;
                        if (imageInfo.n <= 0 || imageInfo.o <= 0) {
                            String str = imageInfo.b;
                            Options options = new Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(str, options);
                            int[] iArr = {options.outWidth, options.outHeight};
                            imageInfo.n = iArr[0];
                            imageInfo.o = iArr[1];
                        }
                        if (bvk2.i != null) {
                            for (ImageInfo imageInfo2 : bvk2.i) {
                                if (TextUtils.equals(imageInfo2.b, imageInfo.b)) {
                                    imageInfo.g = true;
                                    if (!arrayList2.contains(imageInfo)) {
                                        arrayList2.add(imageInfo);
                                    }
                                }
                            }
                        }
                        a((Map<String, List<ImageInfo>>) hashMap, imageInfo, a2);
                        bvk2.b.add(imageInfo);
                        if (bvk2.h) {
                            a((Map<String, List<ImageInfo>>) linkedHashMap, imageInfo, file.getParent());
                        }
                        if (bvk2.b.size() == bvk2.l) {
                            bvk2.l += 100;
                            if (aVar2 != null) {
                                if (bvk2.b.size() == cursor.getCount()) {
                                    z = true;
                                }
                                aVar2.a(arrayList, hashMap, arrayList2, z);
                                ArrayList arrayList3 = new ArrayList();
                                HashMap hashMap2 = new HashMap();
                                StringBuilder sb4 = new StringBuilder("loadLoacalImageCallback:");
                                sb4.append(bvk2.b.size());
                                AMapLog.i("lb", sb4.toString());
                                arrayList = arrayList3;
                                hashMap = hashMap2;
                            }
                        }
                    }
                }
            }
        }
        if (hashMap.size() > 0 && aVar2 != null) {
            aVar2.a(arrayList, hashMap, arrayList2, true);
            StringBuilder sb5 = new StringBuilder("loadLoacalImageCallback:");
            sb5.append(bvk2.b.size());
            AMapLog.i("lb", sb5.toString());
        }
        if (bvk2.d != null && bvk2.h) {
            bvk2.d.a(linkedHashMap);
        }
    }
}
