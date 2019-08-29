package com.autonavi.minimap.shortcutbadger.shortcutbadger;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import com.autonavi.minimap.IPushConfigService;
import com.autonavi.minimap.ajx3.util.RomUtil;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.AdwHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.ApexHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.AsusHomeLauncher;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.DefaultBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.HWHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NovaHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.OPPOHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.SolidHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.SonyHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.VIVOHomeBadger;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.XiaomiHomeBadger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class ShortcutBadger {
    private static final List<Class<? extends ShortcutBadger>> BADGERS;
    private static final String LOG_TAG = "ShortcutBadger";
    private static ShortcutBadger mShortcutBadger;
    protected Context mContext;

    /* access modifiers changed from: protected */
    public abstract void executeBadge(int i) throws ShortcutBadgeException;

    /* access modifiers changed from: protected */
    public abstract void executeBadge(int i, String str) throws ShortcutBadgeException;

    /* access modifiers changed from: protected */
    public abstract List<String> getSupportLaunchers();

    static {
        LinkedList linkedList = new LinkedList();
        BADGERS = linkedList;
        linkedList.add(AdwHomeBadger.class);
        BADGERS.add(ApexHomeBadger.class);
        BADGERS.add(NewHtcHomeBadger.class);
        BADGERS.add(NovaHomeBadger.class);
        BADGERS.add(SolidHomeBadger.class);
        BADGERS.add(SonyHomeBadger.class);
        BADGERS.add(XiaomiHomeBadger.class);
        BADGERS.add(AsusHomeLauncher.class);
        BADGERS.add(VIVOHomeBadger.class);
        BADGERS.add(HWHomeBadger.class);
        BADGERS.add(OPPOHomeBadger.class);
    }

    public static ShortcutBadger with(Context context) {
        return getShortcutBadger(context);
    }

    public static void setBadge(Context context, int i) throws ShortcutBadgeException {
        try {
            getShortcutBadger(context).executeBadge(i);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("Unable to execute badge:");
            sb.append(th.getMessage());
            throw new ShortcutBadgeException(sb.toString());
        }
    }

    private static ShortcutBadger getShortcutBadger(Context context) {
        ShortcutBadger shortcutBadger;
        if (mShortcutBadger != null) {
            return mShortcutBadger;
        }
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            String str = context.getPackageManager().resolveActivity(intent, 65536).activityInfo.packageName;
            Iterator<Class<? extends ShortcutBadger>> it = BADGERS.iterator();
            do {
                boolean z = false;
                if (it.hasNext()) {
                    shortcutBadger = (ShortcutBadger) it.next().getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
                } else if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                    XiaomiHomeBadger xiaomiHomeBadger = new XiaomiHomeBadger(context);
                    mShortcutBadger = xiaomiHomeBadger;
                    return xiaomiHomeBadger;
                } else if (Build.MANUFACTURER.equalsIgnoreCase("BBK")) {
                    VIVOHomeBadger vIVOHomeBadger = new VIVOHomeBadger(context);
                    mShortcutBadger = vIVOHomeBadger;
                    return vIVOHomeBadger;
                } else if (!Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") || !elz.a()) {
                    if (Build.MANUFACTURER.equalsIgnoreCase(RomUtil.ROM_OPPO)) {
                        if (VERSION.SDK_INT >= 23) {
                            z = true;
                        }
                        if (z) {
                            OPPOHomeBadger oPPOHomeBadger = new OPPOHomeBadger(context);
                            mShortcutBadger = oPPOHomeBadger;
                            return oPPOHomeBadger;
                        }
                    }
                    if (mShortcutBadger == null) {
                        mShortcutBadger = new DefaultBadger(context);
                    }
                    new StringBuilder("Returning badger:").append(mShortcutBadger.getClass().getCanonicalName());
                    return mShortcutBadger;
                } else {
                    HWHomeBadger hWHomeBadger = new HWHomeBadger(context);
                    mShortcutBadger = hWHomeBadger;
                    return hWHomeBadger;
                }
            } while (!shortcutBadger.getSupportLaunchers().contains(str));
            mShortcutBadger = shortcutBadger;
            return shortcutBadger;
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private ShortcutBadger() {
    }

    protected ShortcutBadger(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public String getEntryActivityName() {
        return this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName()).getComponent().getClassName();
    }

    /* access modifiers changed from: protected */
    public String getContextPackageName() {
        return this.mContext.getPackageName();
    }

    public void countMainLauncher(int i) {
        IPushConfigService iPushConfigService = (IPushConfigService) ank.a(IPushConfigService.class);
        if (iPushConfigService != null) {
            iPushConfigService.a(i);
        }
        try {
            executeBadge(i);
        } catch (Throwable th) {
            th.getMessage();
        }
    }

    public void countTripHelperShortcut(int i) {
        count(i, "com.autonavi.map.shortcut.view.ShortcutActivity");
    }

    private void count(int i, String str) {
        try {
            executeBadge(i, str);
        } catch (Throwable th) {
            th.getMessage();
        }
    }

    public void remove() {
        countMainLauncher(0);
    }
}
