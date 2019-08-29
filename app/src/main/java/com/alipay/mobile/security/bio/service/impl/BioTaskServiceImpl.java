package com.alipay.mobile.security.bio.service.impl;

import android.content.Context;
import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioTaskService;
import com.alipay.mobile.security.bio.service.BioTaskService.TaskListener;
import com.alipay.mobile.security.bio.task.ActionFrame;
import com.alipay.mobile.security.bio.task.SubTask;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.util.Vector;

public class BioTaskServiceImpl extends BioTaskService {
    private Context a;
    private TaskListener b;
    private Vector<SubTask> c = new Vector<>();
    private SubTask d = null;
    private int e;

    public BioTaskServiceImpl(Context context) {
        if (context == null) {
            throw new BioIllegalArgumentException();
        }
        this.a = context;
    }

    public void onCreate(BioServiceManager bioServiceManager) {
        this.c.clear();
    }

    public void onDestroy() {
        if (this.b != null) {
            this.b = null;
        }
        if (this.c != null) {
            this.c.clear();
            this.d = null;
        }
    }

    public void addTask(SubTask subTask) {
        if (subTask != null && this.c != null) {
            BioLog.i(subTask.getClass().getCanonicalName());
            this.c.add(subTask);
        }
    }

    public int getLeftTaskCount() {
        return this.c.size() - this.e;
    }

    public int getSubTaskCount() {
        return this.c.size();
    }

    public Vector<SubTask> getTasks() {
        return this.c;
    }

    public void setTaskListener(TaskListener taskListener) {
        this.b = taskListener;
    }

    public void initAndBegin() {
        if (this.b != null) {
            this.b.onTasksBegin();
        }
        if (this.c.size() > 0) {
            this.d = this.c.get(0);
            this.d.init();
        }
        this.e = 0;
    }

    public void clearTask() {
        if (this.c != null) {
            this.c.clear();
        }
        this.e = 0;
    }

    public SubTask getCurrentTask() {
        return this.d;
    }

    public <T> void action(ActionFrame<T> actionFrame) {
        if (this.d != null) {
            switch (b.a[this.d.action(actionFrame).ordinal()]) {
                case 3:
                    if (this.d != null) {
                        this.d.done();
                        this.e++;
                        if (this.e < this.c.size()) {
                            this.d = this.c.get(this.e);
                            this.d.init();
                            return;
                        }
                        this.d = null;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void reset() {
        this.e = 0;
        if (this.c != null) {
            this.c.clear();
        }
    }
}
