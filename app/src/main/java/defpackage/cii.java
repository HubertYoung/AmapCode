package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.widget.AGroupAlertFactory;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import com.autonavi.widget.ui.AlertView;

/* renamed from: cii reason: default package */
/* compiled from: AgroupPassphraseHelper */
public final class cii {
    MapSharePreference a;
    cjx b;

    /* renamed from: cii$a */
    /* compiled from: AgroupPassphraseHelper */
    public static class a {
        public static cii a = new cii(0);
    }

    /* synthetic */ cii(byte b2) {
        this();
    }

    private cii() {
        this.a = new MapSharePreference(SharePreferenceName.SharedPreferences);
    }

    static /* synthetic */ boolean a(cii cii, final bid bid, final TeamStatus teamStatus, final String str) {
        cii.b = AGroupAlertFactory.a(bid.getContext(), teamStatus, new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                if (bid != null && cii.this.b != null) {
                    bid.dismissViewLayer(cii.this.b);
                    cii.this.b = null;
                }
            }
        }, new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                if (teamStatus == TeamStatus.STATUS_USER_NOT_LOGIN) {
                    c.a.a(str, 1, new defpackage.cik.a() {
                        public final void a(String str) {
                            cjp.a("amapuri://AGroup/joinGroup?clearStack=1", "");
                        }
                    });
                } else if (teamStatus == TeamStatus.STATUS_LEADER_IN_OTHER_TEAM || teamStatus == TeamStatus.STATUS_USER_IN_OTHER_TEAM) {
                    cin.a(str, (defpackage.cik.a) new defpackage.cik.a() {
                        public final void a(String str) {
                            int a2 = cju.a(str);
                            if (a2 == 0) {
                                ToastHelper.showToast(bid.getContext().getString(R.string.network_error));
                                return;
                            }
                            final TeamStatus a3 = cju.a(a2);
                            if (a2 == 1 || a3 == TeamStatus.STATUS_USER_IN_TEAM) {
                                cii.a(cii.this);
                            } else {
                                aho.a(new Runnable() {
                                    public final void run() {
                                        cii.a(cii.this, bid, a3, str);
                                    }
                                });
                            }
                        }
                    });
                } else if (teamStatus == TeamStatus.STATUS_USER_IN_THIS_TEAM) {
                    cii.a(cii.this);
                }
                if (bid != null && cii.this.b != null) {
                    bid.dismissViewLayer(cii.this.b);
                    cii.this.b = null;
                }
            }
        });
        if (cii.b == null) {
            return false;
        }
        bid.showViewLayer(cii.b);
        cjs.a(bid);
        return true;
    }

    static /* synthetic */ void a(cii cii) {
        if (cjq.a()) {
            aho.a(new Runnable() {
                public final void run() {
                    cjp.a("amapuri://AGroup/joinGroup?clearStack=1", "");
                }
            });
            return;
        }
        final cig cig = new cig();
        if (cig.a != null) {
            cig.a.a((com.autonavi.minimap.bundle.agroup.api.IDataService.a) cig);
        }
        cig.b = new defpackage.cig.a() {
            public final void a() {
                aho.a(new Runnable() {
                    public final void run() {
                        cjp.a("amapuri://AGroup/joinGroup?clearStack=1", "");
                    }
                });
                cig cig = cig;
                if (cig.a != null) {
                    cig.a.b((com.autonavi.minimap.bundle.agroup.api.IDataService.a) cig);
                }
            }
        };
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.c((JsFunctionCallback) null);
        }
    }

    static /* synthetic */ void a(cii cii, final bid bid, final String str, String str2) {
        c.a.a(str2, 1, new defpackage.cik.a() {
            public final void a(String str) {
                int a2 = cju.a(str);
                final TeamStatus a3 = cju.a(a2);
                if (a2 == 1 || a3 == TeamStatus.STATUS_USER_IN_TEAM) {
                    cii.a(cii.this);
                } else {
                    aho.a(new Runnable() {
                        public final void run() {
                            cii.a(cii.this, bid, a3, str);
                        }
                    });
                }
            }
        });
        LogManager.actionLogV2("P00319", "B008");
    }
}
