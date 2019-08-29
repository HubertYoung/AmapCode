package com.alipay.mobile.nebula.basebridge;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class H5BaseNebulaService implements H5CoreNode {
    public static final String TAG = "H5BaseNebulaService";
    private List<H5CoreNode> children = new ArrayList();
    protected H5Data h5Data;
    private H5CoreNode parent = null;
    private H5PluginManager pluginManager = H5ServiceUtils.getH5Service().createPluginManager(this);

    public void onInitialize(H5CoreNode coreNode) {
    }

    public void onPrepare(H5EventFilter filter) {
    }

    public H5CoreNode getParent() {
        return this.parent;
    }

    public void setParent(H5CoreNode parent2) {
        if (parent2 != this.parent) {
            if (this.parent != null) {
                this.parent.removeChild(this);
            }
            this.parent = parent2;
            if (this.parent != null) {
                this.parent.addChild(this);
            }
        }
    }

    public synchronized boolean addChild(H5CoreNode child) {
        boolean z;
        if (child != null) {
            Iterator<H5CoreNode> it = this.children.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().equals(child)) {
                        z = false;
                        break;
                    }
                } else {
                    this.children.add(child);
                    child.setParent(this);
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    public synchronized boolean removeChild(H5CoreNode child) {
        boolean z;
        if (child != null) {
            Iterator iterator = this.children.iterator();
            while (true) {
                if (iterator.hasNext()) {
                    if (iterator.next().equals(child)) {
                        iterator.remove();
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
        } else {
            z = false;
        }
        return z;
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return this.pluginManager != null && this.pluginManager.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        return this.pluginManager != null && this.pluginManager.handleEvent(event, context);
    }

    public void onRelease() {
        if (this.pluginManager != null) {
            this.pluginManager.onRelease();
            this.pluginManager = null;
        }
        this.h5Data = null;
    }

    public H5PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public H5Data getData() {
        return this.h5Data;
    }

    public void setData(H5Data data) {
        this.h5Data = data;
    }

    public void sendEvent(String action, JSONObject param) {
    }
}
