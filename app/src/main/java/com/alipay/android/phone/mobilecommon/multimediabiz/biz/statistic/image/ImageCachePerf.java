package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.BaseStatistics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP1;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP2;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SP3;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.SPExt;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.annotations.ST;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@ST(caseId = "UC-MM-C34", seedId = "ImageCachePerf")
public class ImageCachePerf extends BaseStatistics implements Cloneable {
    private static ImageCachePerf a;
    private static long b = -1;
    @SPExt(name = "dbt")
    public AtomicInteger dbHitTime = new AtomicInteger();
    @SPExt(name = "dbh")
    public AtomicInteger dbHits = new AtomicInteger();
    @SPExt(name = "dbnt")
    public AtomicInteger dbNotHitTime = new AtomicInteger();
    @SPExt(name = "dbnh")
    public AtomicInteger dbNotHits = new AtomicInteger();
    @SPExt(name = "dt")
    public AtomicInteger diskHitTime = new AtomicInteger();
    @SPExt(name = "dh")
    public AtomicInteger diskHits = new AtomicInteger();
    @SPExt(name = "dnt")
    public AtomicInteger diskNotHitTime = new AtomicInteger();
    @SPExt(name = "dnh")
    public AtomicInteger diskNotHits = new AtomicInteger();
    @SPExt(name = "mt")
    public AtomicInteger memHitTime = new AtomicInteger();
    @SPExt(name = "mh")
    public AtomicInteger memHits = new AtomicInteger();
    @SPExt(name = "mnt")
    public AtomicInteger memNotHitTime = new AtomicInteger();
    @SPExt(name = "mnh")
    public AtomicInteger memNotHits = new AtomicInteger();
    @SP1
    public int retCode = 0;
    @SP2
    public long size = 0;
    @SP3
    public long totalTime;

    public static ImageCachePerf getCurrent() {
        if (System.currentTimeMillis() - b > 120000) {
            if (a != null) {
                a();
            }
            a = new ImageCachePerf();
            b = System.currentTimeMillis();
        }
        if (a == null) {
            a = new ImageCachePerf();
            b = System.currentTimeMillis();
        }
        return a;
    }

    public ImageCachePerf addDiskHitTime(boolean hit, long time) {
        if (hit) {
            this.diskHits.incrementAndGet();
            this.diskHitTime.addAndGet((int) time);
        } else {
            this.diskNotHits.incrementAndGet();
            this.diskNotHitTime.addAndGet((int) time);
        }
        return this;
    }

    public ImageCachePerf addMemHitTime(boolean hit, long time) {
        if (hit) {
            this.memHits.incrementAndGet();
            this.memHitTime.addAndGet((int) time);
        } else {
            this.memNotHits.incrementAndGet();
            this.memNotHitTime.addAndGet((int) time);
        }
        return this;
    }

    public ImageCachePerf addDbHitTime(boolean hit, long time) {
        if (hit) {
            this.dbHits.incrementAndGet();
            this.dbHitTime.addAndGet((int) time);
        } else {
            this.dbNotHits.incrementAndGet();
            this.dbNotHitTime.addAndGet((int) time);
        }
        return this;
    }

    private static void a() {
        ImageCachePerf perf;
        try {
            perf = (ImageCachePerf) a.clone();
        } catch (CloneNotSupportedException e) {
            perf = a;
        }
        perf.submitRemoteAsync();
    }

    public String getCaseId() {
        return "UC-MM-C34";
    }

    public String getSeedId() {
        return "ImageCachePerf";
    }

    public String getParam1() {
        return String.valueOf(this.retCode);
    }

    public String getParam2() {
        return String.valueOf(this.size);
    }

    public String getParam3() {
        return String.valueOf(this.totalTime);
    }

    /* access modifiers changed from: protected */
    public void fillExtParams(Map<String, String> ext) {
        ext.put("mh", String.valueOf(this.memHits));
        ext.put("mt", String.valueOf(this.memHitTime));
        ext.put("mnh", String.valueOf(this.memNotHits));
        ext.put("mnt", String.valueOf(this.memNotHitTime));
        ext.put("dh", String.valueOf(this.diskHits));
        ext.put("dt", String.valueOf(this.diskHitTime));
        ext.put("mnh", String.valueOf(this.diskNotHits));
        ext.put("dnt", String.valueOf(this.diskNotHitTime));
        ext.put("dbh", String.valueOf(this.dbHits));
        ext.put("dbt", String.valueOf(this.dbHitTime));
        ext.put("dbnh", String.valueOf(this.dbNotHits));
        ext.put("dbnt", String.valueOf(this.dbNotHitTime));
    }
}
