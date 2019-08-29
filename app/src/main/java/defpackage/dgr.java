package defpackage;

import android.os.StatFs;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.SdCardInfo;
import com.amap.bundle.blutils.SdCardInfo.SDCardType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: dgr reason: default package */
/* compiled from: StorageUtil */
public final class dgr {
    public static List<dgn> a() {
        ArrayList arrayList = new ArrayList();
        Iterator it = new CopyOnWriteArrayList(FileUtil.enumExternalSDcardInfo(dhd.b())).iterator();
        int i = 1;
        while (it.hasNext()) {
            SdCardInfo sdCardInfo = (SdCardInfo) it.next();
            if (!TextUtils.isEmpty(sdCardInfo.b)) {
                StatFs statFs = new StatFs(sdCardInfo.b);
                long blockSize = (long) statFs.getBlockSize();
                long availableBlocks = ((long) statFs.getAvailableBlocks()) * blockSize;
                long blockCount = ((long) statFs.getBlockCount()) * blockSize;
                if (availableBlocks >= 0 && blockCount >= 0) {
                    if (sdCardInfo.a == SDCardType.INNERCARD) {
                        dgn dgn = new dgn(sdCardInfo.b, availableBlocks, blockCount, false, -1);
                        arrayList.add(dgn);
                    } else if (sdCardInfo.a == SDCardType.EXTERNALCARD) {
                        dgn dgn2 = new dgn(sdCardInfo.b, availableBlocks, blockCount, true, i);
                        arrayList.add(dgn2);
                        i++;
                    }
                }
            }
        }
        return arrayList;
    }
}
