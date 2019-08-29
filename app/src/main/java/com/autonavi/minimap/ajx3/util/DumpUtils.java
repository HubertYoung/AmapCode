package com.autonavi.minimap.ajx3.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.dom.KeyDefine;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class DumpUtils {
    @NonNull
    public static String dumpProperties(Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        if (objArr == null || objArr.length <= 0) {
            return sb.toString();
        }
        for (Object[] objArr2 : objArr) {
            if (objArr2 != null && objArr2.length == 3) {
                sb.append("                                    type:");
                sb.append(((Integer) objArr2[0]).intValue());
                if (objArr2[1] != null) {
                    sb.append(Token.SEPARATOR);
                    sb.append(objArr2[1].toString());
                    sb.append(" : ");
                    if (!"padding".equals(objArr2[1].toString())) {
                        sb.append(objArr2[2] != null ? objArr2[2].toString() : "null");
                    } else if (objArr2[2] != null) {
                        float[] fArr = (float[]) objArr2[2];
                        sb.append(String.format(Locale.CHINA, "%f,%f,%f,%f", new Object[]{Float.valueOf(fArr[0]), Float.valueOf(fArr[1]), Float.valueOf(fArr[2]), Float.valueOf(fArr[3])}));
                    }
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    @NonNull
    public static String dumpProperty(Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        if (objArr != null && objArr.length == 3) {
            sb.append("type:");
            sb.append(objArr[0].intValue());
            if (objArr[1] != null) {
                sb.append(Token.SEPARATOR);
                sb.append(objArr[1].toString());
                sb.append(" : ");
                if (!"padding".equals(objArr[1].toString())) {
                    sb.append(objArr[2] != null ? objArr[2].toString() : "null");
                } else if (objArr[2] != null) {
                    int[] iArr = objArr[2];
                    sb.append(String.format(Locale.CHINA, "%d,%d,%d,%d", new Object[]{Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]), Integer.valueOf(iArr[2]), Integer.valueOf(iArr[3])}));
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @NonNull
    public static String dumpStyle(@Nullable SparseArray<Object> sparseArray) {
        if (sparseArray == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sparseArray.size(); i++) {
            String type2Name = KeyDefine.type2Name(sparseArray.keyAt(i));
            sb.append("\t");
            sb.append(type2Name);
            sb.append(":");
            Object obj = sparseArray.get(sparseArray.keyAt(i));
            if (!"padding".equals(type2Name)) {
                sb.append(obj.toString());
            } else if (obj != null) {
                int[] iArr = (int[]) obj;
                sb.append(String.format(Locale.CHINA, "%d,%d,%d,%d", new Object[]{Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]), Integer.valueOf(iArr[2]), Integer.valueOf(iArr[3])}));
            }
        }
        return sb.toString();
    }

    @NonNull
    public static String dumpAttribute(@Nullable Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Entry next : map.entrySet()) {
            sb.append("\t");
            sb.append((String) next.getKey());
            sb.append(":");
            sb.append(next.getValue());
        }
        return sb.toString();
    }
}
