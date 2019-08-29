package com.alipay.mobile.quinox.utils;

import android.os.SystemClock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TimingLogger {
    private static TimingLogger sBootLogger = new TimingLogger("BootPerformance", "Startup");
    private static TimingLogger sPreLaunchLogger = new TimingLogger("PreLaunchPerformance", "PreLaunch");
    private String mLabel;
    Map<Long, ArrayList<String>> mSplitLabels;
    Map<Long, ArrayList<Long>> mSplits;
    private String mTag;
    Map<Long, String> mThreadNames;

    static class Stamp {
        long id;
        int index;
        long stamp;

        private Stamp() {
        }
    }

    public TimingLogger(String str, String str2) {
        reset(str, str2);
    }

    public void reset(String str, String str2) {
        this.mTag = str;
        this.mLabel = str2;
        reset();
    }

    public void reset() {
        if (this.mSplits == null) {
            this.mSplits = new LinkedHashMap();
            this.mSplitLabels = new LinkedHashMap();
            this.mThreadNames = new LinkedHashMap();
        } else {
            this.mSplits.clear();
            this.mSplitLabels.clear();
            this.mThreadNames.clear();
        }
        addSplit(null);
    }

    public void addSplit(String str) {
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Thread currentThread = Thread.currentThread();
            long id = currentThread.getId();
            this.mThreadNames.put(Long.valueOf(id), currentThread.getName());
            if (this.mSplits.get(Long.valueOf(id)) == null) {
                this.mSplits.put(Long.valueOf(id), new ArrayList());
            }
            this.mSplits.get(Long.valueOf(id)).add(Long.valueOf(elapsedRealtime));
            if (this.mSplitLabels.get(Long.valueOf(id)) == null) {
                this.mSplitLabels.put(Long.valueOf(id), new ArrayList());
            }
            this.mSplitLabels.get(Long.valueOf(id)).add(str);
            if (str != null) {
                log(this.mTag, str);
            }
        } catch (Throwable unused) {
        }
    }

    public void addSplitSpecfic(String str) {
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mThreadNames.put(Long.valueOf(1), "main");
            if (this.mSplits.get(Long.valueOf(1)) == null) {
                this.mSplits.put(Long.valueOf(1), new ArrayList());
            }
            this.mSplits.get(Long.valueOf(1)).add(Long.valueOf(elapsedRealtime));
            if (this.mSplitLabels.get(Long.valueOf(1)) == null) {
                this.mSplitLabels.put(Long.valueOf(1), new ArrayList());
            }
            this.mSplitLabels.get(Long.valueOf(1)).add(str);
            log(this.mTag, str, false);
        } catch (Throwable unused) {
        }
    }

    public Map<String, Long> dumpTogether() {
        int i;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = this.mSplits.keySet().iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            Long next = it.next();
            ArrayList arrayList2 = this.mSplits.get(next);
            while (i < arrayList2.size()) {
                Stamp stamp = new Stamp();
                stamp.stamp = ((Long) arrayList2.get(i)).longValue();
                stamp.id = next.longValue();
                stamp.index = i;
                arrayList.add(stamp);
                i++;
            }
        }
        Collections.sort(arrayList, new Comparator<Stamp>() {
            public int compare(Stamp stamp, Stamp stamp2) {
                if (stamp.stamp < stamp2.stamp) {
                    return -1;
                }
                return stamp == stamp2 ? 0 : 1;
            }
        });
        HashMap hashMap = new HashMap();
        Iterator it2 = arrayList.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            Stamp stamp2 = (Stamp) it2.next();
            long j = stamp2.id;
            int i3 = stamp2.index;
            ArrayList arrayList3 = this.mSplits.get(Long.valueOf(j));
            long longValue = ((Long) arrayList3.get(i3)).longValue();
            String str = (String) this.mSplitLabels.get(Long.valueOf(j)).get(i3);
            if (!hashMap.containsKey(Long.valueOf(j))) {
                hashMap.put(Long.valueOf(j), Integer.valueOf(i2));
                i2++;
            }
            StringBuilder sb = new StringBuilder();
            for (Integer valueOf = Integer.valueOf(i); valueOf.intValue() < ((Integer) hashMap.get(Long.valueOf(j))).intValue(); valueOf = Integer.valueOf(valueOf.intValue() + 1)) {
                sb.append("      ");
            }
            if (i3 == 0) {
                sb.append(this.mThreadNames.get(Long.valueOf(j)));
                sb.append(", begin:");
                if (str != null) {
                    sb.append(str);
                    sb.append(",");
                }
            } else {
                sb.append(str);
                sb.append(": ");
                long longValue2 = longValue - ((Long) arrayList3.get(i3 - 1)).longValue();
                sb.append(longValue2);
                sb.append(" ms.");
                linkedHashMap.put(str, Long.valueOf(longValue2));
            }
            if (i3 == arrayList3.size() - 1) {
                sb.append("end, ");
                i = 0;
                sb.append(longValue - ((Long) arrayList3.get(0)).longValue());
            } else {
                i = 0;
            }
            log(this.mTag, sb.toString());
        }
        return linkedHashMap;
    }

    public void dumpSeperately() {
        String str = this.mTag;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mLabel);
        sb.append(": begin");
        TraceLogger.i(str, sb.toString());
        for (Long next : this.mThreadNames.keySet()) {
            String str2 = this.mTag;
            StringBuilder sb2 = new StringBuilder("Thread, ");
            sb2.append(this.mThreadNames.get(next));
            sb2.append(": ");
            log(str2, sb2.toString());
            ArrayList arrayList = this.mSplits.get(next);
            ArrayList arrayList2 = this.mSplitLabels.get(next);
            long longValue = ((Long) arrayList.get(0)).longValue();
            long j = longValue;
            for (int i = 1; i < arrayList.size(); i++) {
                j = ((Long) arrayList.get(i)).longValue();
                long longValue2 = ((Long) arrayList.get(i - 1)).longValue();
                String str3 = this.mTag;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.mLabel);
                sb3.append(":      ");
                sb3.append(j - longValue2);
                sb3.append(" ms, ");
                sb3.append((String) arrayList2.get(i));
                log(str3, sb3.toString());
            }
            String str4 = this.mTag;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this.mLabel);
            sb4.append(": end, ");
            sb4.append(j - longValue);
            sb4.append(" ms");
            log(str4, sb4.toString());
        }
    }

    private void log(String str, String str2) {
        log(str, str2, true);
    }

    private void log(String str, String str2, boolean z) {
        if (z) {
            TraceLogger.i(str, str2);
        }
    }

    public static TimingLogger getBootLogger() {
        return sBootLogger;
    }

    public static TimingLogger getPreLaunchLogger() {
        return sPreLaunchLogger;
    }
}
