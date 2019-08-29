package com.alipay.mobile.nebula.log.linkmonitor;

import java.util.ArrayList;
import java.util.List;

public class H5LinkNode {
    private Runnable checkRunnable;
    private List<H5LinkNode> childList = new ArrayList();
    private String nodeName;
    private H5LinkNode parentNode;
    private long triggerTime;

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName2) {
        this.nodeName = nodeName2;
    }

    public long getTriggerTime() {
        return this.triggerTime;
    }

    public void setTriggerTime(long triggerTime2) {
        this.triggerTime = triggerTime2;
    }

    public H5LinkNode getParentNode() {
        return this.parentNode;
    }

    public void setParentNode(H5LinkNode parentNode2) {
        this.parentNode = parentNode2;
    }

    public List<H5LinkNode> getChildList() {
        return this.childList;
    }

    public void addChildNode(H5LinkNode childNode) {
        this.childList.add(childNode);
    }

    public Runnable getCheckRunnable() {
        return this.checkRunnable;
    }

    public void setCheckRunnable(Runnable checkRunnable2) {
        this.checkRunnable = checkRunnable2;
    }

    public String toString() {
        return "H5LinkNode{nodeName='" + this.nodeName + '\'' + ", triggerTime=" + this.triggerTime + ", parentNode=" + this.parentNode + ", checkRunnable=" + this.checkRunnable + '}';
    }
}
