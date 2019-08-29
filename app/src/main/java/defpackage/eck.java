package defpackage;

import com.autonavi.bundle.footresult.ajx.ModuleFoot;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;

/* renamed from: eck reason: default package */
/* compiled from: IModuleFootImpl */
public final class eck implements avm {

    /* renamed from: eck$a */
    /* compiled from: IModuleFootImpl */
    static class a {
        static eck a = new eck();
    }

    public final String a(AmapAjxViewInterface amapAjxViewInterface) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                return moduleFoot.getErrorReportData();
            }
        }
        return null;
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axi axi) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.setHistoryItemClickListener(axi);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axm axm) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.setOnFootEndClickUGCListener(axm);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axl axl) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.setRideEndShareListener(axl);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axk axk) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.setOnNotifyChangeInterface(axk);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axn axn) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.setOnOpenCompassViewListener(axn);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axo axo) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.setOnOperationActivitiesListener(axo);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, axj axj) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.setWidgetPosListener(axj);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, String str) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.notifyUGCStateChange(str);
            }
        }
    }

    public final void b(AmapAjxViewInterface amapAjxViewInterface, String str) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.notifySelPoiPage(str);
            }
        }
    }

    public final void a(AmapAjxViewInterface amapAjxViewInterface, boolean z) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.setOnRideAccuracyChanged(z);
            }
        }
    }

    public final void b(AmapAjxViewInterface amapAjxViewInterface) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.finishRideNaviCallBack();
            }
        }
    }

    public final void b(AmapAjxViewInterface amapAjxViewInterface, boolean z) {
        if (amapAjxViewInterface != null) {
            ModuleFoot moduleFoot = (ModuleFoot) amapAjxViewInterface.getJsModule(ModuleFoot.MODULE_NAME);
            if (moduleFoot != null) {
                moduleFoot.showCompassView(z);
            }
        }
    }
}
