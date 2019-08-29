package com.autonavi.minimap.offline.koala.model;

import android.support.annotation.Keep;
import android.text.TextUtils;
import com.autonavi.minimap.offline.koala.KoalaDownloadStatus;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloadLocalPathBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

@Keep
public class KoalaDownloadModel extends Vector<KoalaDownloadEntity> {
    private static final long serialVersionUID = 5848184035114380345L;
    private KoalaModelCaretaker mCaretaker;
    private String mKey;

    public KoalaDownloadModel(String str) {
        this.mKey = str;
    }

    public String getKey() {
        return this.mKey;
    }

    public synchronized KoalaDownloadModel deepClone() {
        KoalaDownloadModel koalaDownloadModel;
        int size = size();
        koalaDownloadModel = new KoalaDownloadModel(getKey());
        Vector vector = new Vector(size);
        for (int i = 0; i < size; i++) {
            vector.add(((KoalaDownloadEntity) get(i)).clone(koalaDownloadModel));
        }
        koalaDownloadModel.addAll(vector);
        return koalaDownloadModel;
    }

    public synchronized KoalaDownloadEntity findById(int i) {
        Iterator it = iterator();
        while (it.hasNext()) {
            KoalaDownloadEntity koalaDownloadEntity = (KoalaDownloadEntity) it.next();
            if (koalaDownloadEntity.getId() == i) {
                return koalaDownloadEntity;
            }
        }
        return null;
    }

    private boolean isSameUrls(KoalaDownloadSpecialData[] koalaDownloadSpecialDataArr, String[] strArr) {
        if (koalaDownloadSpecialDataArr == null || koalaDownloadSpecialDataArr.length == 0 || koalaDownloadSpecialDataArr.length != strArr.length) {
            return false;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        for (KoalaDownloadSpecialData koalaDownloadSpecialData : koalaDownloadSpecialDataArr) {
            if (!arrayList.contains(koalaDownloadSpecialData.getUrl())) {
                return false;
            }
            arrayList.remove(koalaDownloadSpecialData.getUrl());
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002a, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity findByUrls(java.lang.String[] r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            if (r5 == 0) goto L_0x0029
            int r1 = r5.length     // Catch:{ all -> 0x0026 }
            if (r1 != 0) goto L_0x0008
            goto L_0x0029
        L_0x0008:
            java.util.Iterator r1 = r4.iterator()     // Catch:{ all -> 0x0026 }
        L_0x000c:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0026 }
            if (r2 == 0) goto L_0x0024
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0026 }
            com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity r2 = (com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity) r2     // Catch:{ all -> 0x0026 }
            com.autonavi.minimap.offline.koala.model.KoalaDownloadSpecialData[] r3 = r2.getSpecialDatas()     // Catch:{ all -> 0x0026 }
            boolean r3 = r4.isSameUrls(r3, r5)     // Catch:{ all -> 0x0026 }
            if (r3 == 0) goto L_0x000c
            monitor-exit(r4)
            return r2
        L_0x0024:
            monitor-exit(r4)
            return r0
        L_0x0026:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        L_0x0029:
            monitor-exit(r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.koala.model.KoalaDownloadModel.findByUrls(java.lang.String[]):com.autonavi.minimap.offline.koala.model.KoalaDownloadEntity");
    }

    public synchronized KoalaDownloadEntity addNewEntity(String[] strArr, String str, IKoalaDownloadLocalPathBuilder iKoalaDownloadLocalPathBuilder) {
        KoalaDownloadEntity createNewEntity;
        createNewEntity = createNewEntity(newDownloadId(), strArr, str, iKoalaDownloadLocalPathBuilder);
        add(createNewEntity);
        return createNewEntity;
    }

    private synchronized KoalaDownloadEntity createNewEntity(int i, String[] strArr, String str, IKoalaDownloadLocalPathBuilder iKoalaDownloadLocalPathBuilder) {
        KoalaDownloadEntity koalaDownloadEntity;
        checkUrlIsEmpty(strArr);
        koalaDownloadEntity = new KoalaDownloadEntity(this);
        koalaDownloadEntity.setTime(0);
        koalaDownloadEntity.setStatus(KoalaDownloadStatus.PENDING);
        koalaDownloadEntity.setId(i);
        KoalaDownloadSpecialData[] koalaDownloadSpecialDataArr = new KoalaDownloadSpecialData[strArr.length];
        int length = strArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            String str2 = strArr[i2];
            KoalaDownloadSpecialData koalaDownloadSpecialData = new KoalaDownloadSpecialData(koalaDownloadEntity);
            koalaDownloadSpecialData.setUrl(str2);
            koalaDownloadSpecialData.setTotalBytes(-1);
            koalaDownloadSpecialData.setDownloadBytes(0);
            koalaDownloadSpecialData.setLocalPath(iKoalaDownloadLocalPathBuilder.build(str2, str));
            koalaDownloadSpecialDataArr[i3] = koalaDownloadSpecialData;
            i2++;
            i3++;
        }
        koalaDownloadEntity.setSpecialDatas(koalaDownloadSpecialDataArr);
        koalaDownloadEntity.checkValid();
        return koalaDownloadEntity;
    }

    private void checkUrlIsEmpty(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("parameter urls is null! ");
        }
        for (String isEmpty : strArr) {
            if (TextUtils.isEmpty(isEmpty)) {
                throw new IllegalArgumentException("the element in url array is null!");
            }
        }
    }

    public void checkValid() {
        Iterator it = iterator();
        while (it.hasNext()) {
            ((KoalaDownloadEntity) it.next()).checkValid();
        }
    }

    public int newDownloadId() {
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            KoalaDownloadEntity koalaDownloadEntity = (KoalaDownloadEntity) it.next();
            if (i < koalaDownloadEntity.getId()) {
                i = koalaDownloadEntity.getId();
            }
        }
        return i + 1;
    }

    public void setCaretaker(KoalaModelCaretaker koalaModelCaretaker) {
        this.mCaretaker = koalaModelCaretaker;
    }

    public void save() {
        if (this.mCaretaker != null) {
            this.mCaretaker.save(this);
        }
    }
}
