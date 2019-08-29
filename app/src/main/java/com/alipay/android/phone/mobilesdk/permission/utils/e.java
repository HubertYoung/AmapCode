package com.alipay.android.phone.mobilesdk.permission.utils;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/* compiled from: MathUtil */
public final class e {
    private static long a(int n) {
        if (n > 1) {
            return ((long) n) * a(n - 1);
        }
        return 1;
    }

    private static long b(int n) {
        if (n >= 2) {
            return (a(n) / a(n - 2)) / a(2);
        }
        return 0;
    }

    public static List<String[]> a(String[] dataList) {
        int size = (int) b(dataList.length);
        LoggerFactory.getTraceLogger().info("MathUtil", String.format(Locale.US, "C(%d, %d) = %d", new Object[]{Integer.valueOf(dataList.length), Integer.valueOf(2), Integer.valueOf(size)}));
        List result = new ArrayList(size);
        a(dataList, 0, new String[2], 0, result);
        return result;
    }

    private static void a(String[] dataList, int dataIndex, String[] resultList, int resultIndex, List<String[]> result) {
        int resultLen = resultList.length;
        int resultCount = resultIndex + 1;
        if (resultCount > resultLen) {
            result.add(resultList.clone());
            return;
        }
        for (int i = dataIndex; i < (dataList.length + resultCount) - resultLen; i++) {
            resultList[resultIndex] = dataList[i];
            a(dataList, i + 1, resultList, resultIndex + 1, result);
        }
    }

    public static <T> void a(List<T> raw, Comparator<T> comparator) {
        if (raw.size() >= 2) {
            int replaceIdx = -1;
            try {
                int rawSize = raw.size();
                for (int i = 1; i < rawSize; i++) {
                    boolean same = false;
                    int j = 0;
                    while (true) {
                        if (j >= i) {
                            break;
                        } else if (comparator.compare(raw.get(i), raw.get(j)) == 0) {
                            if (replaceIdx == -1) {
                                replaceIdx = i;
                            }
                            same = true;
                        } else {
                            j++;
                        }
                    }
                    if (!same && replaceIdx != -1) {
                        Object temp = raw.get(i);
                        raw.set(i, raw.get(replaceIdx));
                        raw.set(replaceIdx, temp);
                        if (i > replaceIdx) {
                            replaceIdx++;
                        } else {
                            replaceIdx = -1;
                        }
                    }
                }
                if (replaceIdx != -1) {
                    for (int i2 = rawSize - replaceIdx; i2 > 0; i2--) {
                        raw.remove(raw.size() - 1);
                    }
                }
                LoggerFactory.getTraceLogger().info("MathUtil", String.format(Locale.US, "distinct, rawSize: %s, finalSize: %s", new Object[]{Integer.valueOf(rawSize), Integer.valueOf(raw.size())}));
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().error("MathUtil", "distinct error.", tr);
            }
        }
    }
}
