package com.amap.location.common.f;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.amap.location.common.model.CellStatus.HistoryCell;
import java.util.List;

/* compiled from: NetUtil */
public class h {
    public static boolean a(int i) {
        return i >= 0 && i <= 65535;
    }

    public static boolean b(int i) {
        return i >= 0 && i <= 268435455;
    }

    public static boolean c(int i) {
        return i > 0 && i <= 32767;
    }

    public static boolean d(int i) {
        return i >= 0 && i <= 65535;
    }

    public static boolean e(int i) {
        return i >= 0 && i <= 65535;
    }

    public static String a(long j) {
        if (j < 0 || j > 281474976710655L) {
            return null;
        }
        return c.a(c.a(j, 6, true), (String) null, (String) ":");
    }

    public static long a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int i = 0;
        long j = 0;
        for (int length = str.length() - 1; length >= 0; length--) {
            long charAt = (long) str.charAt(length);
            if (charAt >= 48 && charAt <= 57) {
                j += (charAt - 48) << i;
                i += 4;
            } else if (charAt >= 97 && charAt <= 102) {
                j += ((charAt - 97) + 10) << i;
                i += 4;
            } else if (charAt >= 65 && charAt <= 70) {
                j += ((charAt - 65) + 10) << i;
                i += 4;
            } else if (!(charAt == 58 || charAt == 124)) {
                return 0;
            }
        }
        if (i != 48) {
            return 0;
        }
        return j;
    }

    public static int a(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                int type = activeNetworkInfo.getType();
                if (type == 1 || type == 0) {
                    return type;
                }
            }
        } catch (Throwable unused) {
        }
        return -1;
    }

    public static void a(HistoryCell historyCell, List<HistoryCell> list, int i) {
        if (historyCell != null && list != null) {
            int size = list.size();
            if (size == 0) {
                list.add(historyCell);
                return;
            }
            long j = Long.MAX_VALUE;
            int i2 = 0;
            int i3 = -1;
            int i4 = -1;
            while (true) {
                if (i2 >= size) {
                    i3 = i4;
                    break;
                }
                HistoryCell historyCell2 = list.get(i2);
                if (!historyCell.equals(historyCell2)) {
                    j = Math.min(j, historyCell2.lastUpdateTimeMills);
                    if (j == historyCell2.lastUpdateTimeMills) {
                        i4 = i2;
                    }
                    i2++;
                } else if (historyCell.rssi != historyCell2.rssi) {
                    historyCell2.lastUpdateTimeMills = historyCell.lastUpdateTimeMills;
                    historyCell2.rssi = historyCell.rssi;
                }
            }
            if (i3 >= 0) {
                if (size < i) {
                    list.add(historyCell);
                } else if (historyCell.lastUpdateTimeMills > j && i3 < size) {
                    list.remove(i3);
                    list.add(historyCell);
                }
            }
        }
    }
}
