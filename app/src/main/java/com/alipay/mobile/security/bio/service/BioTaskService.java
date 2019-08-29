package com.alipay.mobile.security.bio.service;

import com.alipay.mobile.security.bio.task.ActionFrame;
import com.alipay.mobile.security.bio.task.SubTask;
import java.util.Vector;

public abstract class BioTaskService extends BioService {

    public interface TaskListener {
        void onTasksBegin();

        void onTasksEnd();
    }

    public abstract <T> void action(ActionFrame<T> actionFrame);

    public abstract void addTask(SubTask subTask);

    public abstract void clearTask();

    public abstract SubTask getCurrentTask();

    public abstract int getLeftTaskCount();

    public abstract int getSubTaskCount();

    public abstract Vector<SubTask> getTasks();

    public abstract void initAndBegin();

    public abstract void reset();

    public abstract void setTaskListener(TaskListener taskListener);
}
