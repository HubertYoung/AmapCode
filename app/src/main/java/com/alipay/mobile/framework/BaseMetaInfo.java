package com.alipay.mobile.framework;

import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.msg.BroadcastReceiverDescription;
import com.alipay.mobile.framework.pipeline.ValveDescription;
import com.alipay.mobile.framework.service.ServiceDescription;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMetaInfo {
    private List<ApplicationDescription> a;
    private List<ServiceDescription> b;
    private List<BroadcastReceiverDescription> c;
    private String d;
    private List<ValveDescription> e;

    public String getEntry() {
        return this.d;
    }

    public void setEntry(String entry) {
        this.d = entry;
    }

    public List<ApplicationDescription> getApplications() {
        return this.a;
    }

    public void setApplications(List<ApplicationDescription> applications) {
        this.a = applications;
    }

    public void addApplication(ApplicationDescription app) {
        if (this.a == null) {
            this.a = new ArrayList();
        }
        this.a.add(app);
    }

    public List<ServiceDescription> getServices() {
        return this.b;
    }

    public void setServices(List<ServiceDescription> services) {
        this.b = services;
    }

    public void addService(ServiceDescription service) {
        if (this.b == null) {
            this.b = new ArrayList();
        }
        this.b.add(service);
    }

    public List<BroadcastReceiverDescription> getBroadcastReceivers() {
        return this.c;
    }

    public void setBroadcastReceivers(List<BroadcastReceiverDescription> broadcastReceivers) {
        this.c = broadcastReceivers;
    }

    public void addBroadcastReceiver(BroadcastReceiverDescription broadcastReceiver) {
        if (this.c == null) {
            this.c = new ArrayList();
        }
        this.c.add(broadcastReceiver);
    }

    public List<ValveDescription> getValves() {
        return this.e;
    }

    public void setValves(List<ValveDescription> valves) {
        this.e = valves;
    }

    public void addValve(ValveDescription valve) {
        if (this.e == null) {
            this.e = new ArrayList();
        }
        this.e.add(valve);
    }

    public void setClassLoader(ClassLoader classLoader) {
        if (this.a != null && !this.a.isEmpty()) {
            for (ApplicationDescription classLoader2 : this.a) {
                classLoader2.setClassLoader(classLoader);
            }
        }
        if (this.b != null && !this.b.isEmpty()) {
            for (ServiceDescription classLoader3 : this.b) {
                classLoader3.setClassLoader(classLoader);
            }
        }
        if (this.c != null && !this.c.isEmpty()) {
            for (BroadcastReceiverDescription classLoader4 : this.c) {
                classLoader4.setClassLoader(classLoader);
            }
        }
        if (this.e != null && !this.e.isEmpty()) {
            for (ValveDescription classLoader5 : this.e) {
                classLoader5.setClassLoader(classLoader);
            }
        }
    }
}
