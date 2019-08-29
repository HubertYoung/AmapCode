package com.alipay.mobile.nebulacore.core;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.manager.H5PluginManagerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class H5CoreTarget implements H5CoreNode {
    public static final String TAG = "H5CoreTarget";
    protected H5Data a;
    private H5PluginManager b = new H5PluginManagerImpl(this);
    private H5CoreNode c = null;
    private List<H5CoreNode> d = new ArrayList();

    public void onInitialize(H5CoreNode coreNode) {
    }

    public void onPrepare(H5EventFilter filter) {
    }

    public H5CoreNode getParent() {
        return this.c;
    }

    public void setParent(H5CoreNode parent) {
        if (parent != this.c) {
            if (this.c != null) {
                this.c.removeChild(this);
            }
            this.c = parent;
            if (this.c != null) {
                this.c.addChild(this);
            }
        }
    }

    public synchronized boolean addChild(H5CoreNode child) {
        boolean z;
        if (child != null) {
            Iterator<H5CoreNode> it = this.d.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().equals(child)) {
                        z = false;
                        break;
                    }
                } else {
                    this.d.add(child);
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
            Iterator iterator = this.d.iterator();
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
        return this.b != null && this.b.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        return this.b != null && this.b.handleEvent(event, context);
    }

    public void onRelease() {
        if (this.b != null) {
            this.b.onRelease();
            this.b = null;
        }
        this.a = null;
    }

    public H5PluginManager getPluginManager() {
        return this.b;
    }

    public H5Data getData() {
        return this.a;
    }

    public void setData(H5Data data) {
        this.a = data;
    }

    public void sendEvent(String action, JSONObject param) {
        if (Nebula.DEBUG) {
            H5Log.d(TAG, "dispatch action " + action);
        }
        Nebula.getDispatcher().sendEvent(action, param, this);
    }
}
