package org.android.spdy;

import android.content.Context;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SecurityGuardCacherImp implements QuicCacher {
    private Class IDynamicDataStoreComponent;
    private Class SecurityGuardManager;
    private Object ddsComp;
    private Method getDynamicDataStoreComp;
    private Method getInstance;
    private Method getStringDDpEx;
    private boolean init_ok = false;
    private Method putStringDDpEx;
    private Object sgMgr;

    public void init(Context context) {
        if (!this.init_ok) {
            try {
                this.SecurityGuardManager = Class.forName("com.taobao.wireless.security.sdk.SecurityGuardManager");
                this.getInstance = this.SecurityGuardManager.getDeclaredMethod("getInstance", new Class[]{Context.class});
                this.sgMgr = this.getInstance.invoke(null, new Object[]{context});
                if (this.sgMgr != null) {
                    this.getDynamicDataStoreComp = this.SecurityGuardManager.getDeclaredMethod("getDynamicDataStoreComp", new Class[0]);
                    this.ddsComp = this.getDynamicDataStoreComp.invoke(this.sgMgr, new Object[0]);
                    if (this.ddsComp != null) {
                        this.IDynamicDataStoreComponent = Class.forName("com.taobao.wireless.security.sdk.dynamicdatastore.IDynamicDataStoreComponent");
                        this.putStringDDpEx = this.IDynamicDataStoreComponent.getDeclaredMethod("putStringDDpEx", new Class[]{String.class, String.class, Integer.TYPE});
                        this.getStringDDpEx = this.IDynamicDataStoreComponent.getDeclaredMethod("getStringDDpEx", new Class[]{String.class, Integer.TYPE});
                        this.init_ok = true;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            }
        }
    }

    public boolean store(String str, String str2) {
        if (this.init_ok) {
            try {
                this.putStringDDpEx.invoke(this.ddsComp, new Object[]{str, str2, Integer.valueOf(0)});
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    public byte[] load(String str) {
        if (!this.init_ok) {
            return null;
        }
        try {
            String str2 = (String) this.getStringDDpEx.invoke(this.ddsComp, new Object[]{str, Integer.valueOf(0)});
            if (str2 != null) {
                return str2.getBytes("ISO-8859-1");
            }
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
