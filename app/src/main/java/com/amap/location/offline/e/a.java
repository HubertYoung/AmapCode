package com.amap.location.offline.e;

import android.text.TextUtils;
import com.amap.location.common.f.h;
import com.amap.location.common.model.CellState;
import com.amap.location.common.model.CellStatus;
import com.amap.location.security.Core;

/* compiled from: EncryptUtil */
public class a {
    public static String a(CellStatus cellStatus) {
        if (cellStatus == null || cellStatus.mainCell == null) {
            return "";
        }
        CellState cellState = cellStatus.mainCell;
        if (cellState.type == 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(cellState.sid);
            sb.append(":");
            sb.append(cellState.nid);
            sb.append(":");
            sb.append(cellState.bid);
            return sb.toString();
        } else if (cellState.type == 0 || cellState.mcc == 0 || cellState.mcc == 65535) {
            return "";
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(cellState.mcc);
            sb2.append(":");
            sb2.append(cellState.mnc);
            sb2.append(":");
            sb2.append(cellState.lac);
            sb2.append(":");
            sb2.append(cellState.cid);
            return sb2.toString();
        }
    }

    public static long a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Core.encMac(str);
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
        return -1;
    }

    public static long a(long j) {
        try {
            return Core.encMac(h.a(j));
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return -1;
        }
    }
}
