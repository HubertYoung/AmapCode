package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic;

import android.content.SharedPreferences;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.HostConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TaskStatistics {
    private static TaskStatistics a;
    private SharedPreferences b = AppUtils.getApplicationContext().getSharedPreferences("mmtask_sp", 0);
    private Map<String, TaskStaticsItem> c = new HashMap();

    private class TaskStaticsItem {
        String a;
        AtomicInteger b;
        AtomicInteger c;
        AtomicLong d;
        AtomicInteger e;

        private TaskStaticsItem() {
            this.a = "";
            this.b = new AtomicInteger(0);
            this.c = new AtomicInteger(0);
            this.d = new AtomicLong(System.currentTimeMillis());
            this.e = new AtomicInteger(0);
        }

        /* synthetic */ TaskStaticsItem(TaskStatistics x0, byte b2) {
            this();
        }

        /* access modifiers changed from: 0000 */
        public final boolean a() {
            return this.e.get() >= 10;
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            this.d.set(System.currentTimeMillis());
            this.b.set(0);
            this.c.set(0);
            this.e.set(0);
        }

        /* access modifiers changed from: 0000 */
        public final boolean c() {
            return Math.abs(System.currentTimeMillis() - this.d.get()) >= 86400000;
        }

        public String toString() {
            return "TaskStaticsItem{name='" + this.a + '\'' + ", totalCount=" + this.b + ", blockCount=" + this.c + ", time=" + this.d + ", num=" + this.e + '}';
        }
    }

    private TaskStatistics() {
    }

    public static TaskStatistics getInstance() {
        if (a == null) {
            synchronized (TaskStatistics.class) {
                try {
                    if (a == null) {
                        a = new TaskStatistics();
                    }
                }
            }
        }
        return a;
    }

    public void addCount(String key, boolean bBlock) {
        HostConfig config = ConfigManager.getInstance().getHostConfig();
        if (config != null && config.taskOccursSwitch != 0) {
            try {
                TaskStaticsItem item = this.c.get(key);
                if (item == null) {
                    item = new TaskStaticsItem(this, 0);
                    item.a = key;
                    a(this.b.getString(key, ""), item);
                    this.c.put(key, item);
                }
                if (bBlock) {
                    item.c.getAndAdd(1);
                }
                item.b.getAndAdd(1);
                item.e.getAndAdd(1);
                if (item.c()) {
                    a(item);
                    c(item);
                    item.b();
                } else if (item.a()) {
                    b(item);
                    item.e.set(0);
                }
            } catch (Throwable t) {
                Logger.E((String) "TaskStatistics", t, (String) "unknown exp", new Object[0]);
            }
        }
    }

    private void a(TaskStaticsItem item) {
        this.b.edit().remove(item.a).apply();
    }

    private void b(TaskStaticsItem item) {
        this.b.edit().putString(item.a, item.d + MergeUtil.SEPARATOR_KV + item.c + MergeUtil.SEPARATOR_KV + item.b).apply();
    }

    private static void a(String value, TaskStaticsItem item) {
        if (value.contains(MergeUtil.SEPARATOR_KV)) {
            String[] values = value.split("\\|");
            item.d.set(Long.parseLong(values[0]));
            item.c.set(Integer.parseInt(values[1]));
            item.b.set(Integer.parseInt(values[2]));
        }
    }

    private static void c(TaskStaticsItem item) {
        if (item != null) {
            UCLogUtil.UC_MM_C52(item.c.get(), item.b.get(), item.a);
        }
    }
}
