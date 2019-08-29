package com.autonavi.minimap.bundle.share;

import android.content.ClipData;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.bundle.account.api.IThirdAuth.b;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.bl.NetworkService;
import com.autonavi.minimap.bl.net.AosRequest;
import com.autonavi.minimap.bundle.share.ajx.ModuleShare;
import com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponse;
import com.autonavi.minimap.bundle.share.passphrase.param.ParseCodeParam;

@VirtualApp(priority = 10000)
public class ShareVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleShare.class);
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        dca.a().b();
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        dca.a();
        dca.a((b) null);
    }

    public void vAppEnterForeground() {
        String str;
        ddc ddc = a.a;
        if (!ddc.a.a()) {
            str = "";
        } else {
            CharSequence b = ddc.a.b();
            if (b == null) {
                str = null;
            } else {
                str = b.toString();
            }
        }
        if (!TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf(12304);
            int indexOf2 = str.indexOf(12305);
            if (indexOf >= 0 && indexOf2 > indexOf) {
                ddb ddb = new ddb(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_SNS_URL_KEY));
                ParseCodeParam parseCodeParam = new ParseCodeParam();
                parseCodeParam.shareText = str;
                AnonymousClass1 r1 = new dko<PassphraseResponse>(System.currentTimeMillis()) {
                    final /* synthetic */ long a;

                    {
                        this.a = r2;
                    }

                    public final /* synthetic */ void a(dkm dkm) {
                        final PassphraseResponse passphraseResponse = (PassphraseResponse) dkm;
                        if (passphraseResponse == null) {
                            if (bno.a) {
                                AMapLog.e("SharedPassphraseManager", "checkClipboard response==null", true);
                            }
                        } else if (passphraseResponse.code != 1) {
                            if (bno.a) {
                                StringBuilder sb = new StringBuilder("checkClipboard response.code is error, response.code = ");
                                sb.append(passphraseResponse.code);
                                AMapLog.e("SharedPassphraseManager", sb.toString(), true);
                            }
                        } else if (passphraseResponse.data == null) {
                            if (bno.a) {
                                AMapLog.e("SharedPassphraseManager", "checkClipboard response.data is null ", true);
                            }
                        } else if (System.currentTimeMillis() - this.a <= 5000) {
                            ddg ddg = ddc.this.a;
                            if (ddg.a != null) {
                                try {
                                    ddg.a.setPrimaryClip(ClipData.newPlainText("", ""));
                                } catch (Exception unused) {
                                }
                            }
                            aho.a(new Runnable() {
                                /* JADX WARNING: Removed duplicated region for block: B:17:0x004e A[RETURN] */
                                /* JADX WARNING: Removed duplicated region for block: B:18:0x004f  */
                                /* Code decompiled incorrectly, please refer to instructions dump. */
                                public final void run() {
                                    /*
                                        r6 = this;
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponse r0 = r6
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponseData r0 = r0.data
                                        int r0 = r0.codeType
                                        java.lang.Class<dfm> r1 = defpackage.dfm.class
                                        java.lang.Object r1 = defpackage.ank.a(r1)
                                        dfm r1 = (defpackage.dfm) r1
                                        r2 = 0
                                        r3 = 1
                                        if (r1 == 0) goto L_0x004b
                                        boolean r4 = r1.b()
                                        if (r4 == 0) goto L_0x004b
                                        bid r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
                                        if (r4 == 0) goto L_0x004b
                                        boolean r5 = r4.isAlive()
                                        if (r5 == 0) goto L_0x004b
                                        android.content.Context r5 = r4.getContext()
                                        if (r5 != 0) goto L_0x002b
                                        goto L_0x004b
                                    L_0x002b:
                                        java.lang.String r5 = ""
                                        switch(r0) {
                                            case 0: goto L_0x003c;
                                            case 1: goto L_0x0031;
                                            default: goto L_0x0030;
                                        }
                                    L_0x0030:
                                        goto L_0x0046
                                    L_0x0031:
                                        android.content.Context r4 = r4.getContext()
                                        int r5 = com.autonavi.minimap.R.string.autonavi_agroup_tip
                                        java.lang.String r5 = r4.getString(r5)
                                        goto L_0x0046
                                    L_0x003c:
                                        android.content.Context r4 = r4.getContext()
                                        int r5 = com.autonavi.minimap.R.string.autonavi_passphrase_tip
                                        java.lang.String r5 = r4.getString(r5)
                                    L_0x0046:
                                        r1.a(r0, r5)
                                        r0 = 1
                                        goto L_0x004c
                                    L_0x004b:
                                        r0 = 0
                                    L_0x004c:
                                        if (r0 == 0) goto L_0x004f
                                        return
                                    L_0x004f:
                                        ddc$1 r0 = defpackage.ddc.AnonymousClass1.this
                                        ddc r0 = defpackage.ddc.this
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponse r1 = r6
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponseData r1 = r1.data
                                        int r1 = r1.codeType
                                        esb r4 = defpackage.esb.a.a
                                        java.lang.Class<bax> r5 = defpackage.bax.class
                                        esc r4 = r4.a(r5)
                                        bax r4 = (defpackage.bax) r4
                                        if (r4 == 0) goto L_0x006f
                                        int r4 = r4.c()
                                        if (r4 <= 0) goto L_0x006f
                                        r4 = 1
                                        goto L_0x0070
                                    L_0x006f:
                                        r4 = 0
                                    L_0x0070:
                                        boolean r5 = defpackage.ddc.a()
                                        if (r4 != 0) goto L_0x0078
                                        if (r5 == 0) goto L_0x0097
                                    L_0x0078:
                                        switch(r1) {
                                            case 0: goto L_0x0093;
                                            case 1: goto L_0x008f;
                                            default: goto L_0x007b;
                                        }
                                    L_0x007b:
                                        boolean r0 = defpackage.bno.a
                                        if (r0 == 0) goto L_0x0096
                                        java.lang.String r0 = "SharedPassphraseManager"
                                        java.lang.String r2 = "isInRoutePage codeType is invalid codeType:"
                                        java.lang.String r1 = java.lang.String.valueOf(r1)
                                        java.lang.String r1 = r2.concat(r1)
                                        com.amap.bundle.logs.AMapLog.e(r0, r1, r3)
                                        goto L_0x0096
                                    L_0x008f:
                                        r0.a(r3)
                                        goto L_0x0096
                                    L_0x0093:
                                        r0.a(r2)
                                    L_0x0096:
                                        r2 = 1
                                    L_0x0097:
                                        if (r2 == 0) goto L_0x009a
                                        return
                                    L_0x009a:
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponse r0 = r6
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponseData r0 = r0.data
                                        int r0 = r0.codeType
                                        if (r0 != 0) goto L_0x00b3
                                        ddc$1 r0 = defpackage.ddc.AnonymousClass1.this
                                        ddc r0 = defpackage.ddc.this
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponse r1 = r6
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponseData r1 = r1.data
                                        ddc$3 r2 = new ddc$3
                                        r2.<init>(r1)
                                        defpackage.aho.a(r2)
                                        return
                                    L_0x00b3:
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponse r0 = r6
                                        com.autonavi.minimap.bundle.share.passphrase.model.PassphraseResponseData r0 = r0.data
                                        java.lang.String r0 = r0.actionScheme
                                        boolean r1 = android.text.TextUtils.isEmpty(r0)
                                        if (r1 == 0) goto L_0x00cb
                                        boolean r1 = defpackage.bno.a
                                        if (r1 == 0) goto L_0x00cb
                                        java.lang.String r0 = "SharedPassphraseManager"
                                        java.lang.String r1 = "jumpScheme uri is empty"
                                        com.amap.bundle.logs.AMapLog.e(r0, r1, r3)
                                        return
                                    L_0x00cb:
                                        java.lang.Class<com.autonavi.minimap.bundle.maphome.service.IMainMapService> r1 = com.autonavi.minimap.bundle.maphome.service.IMainMapService.class
                                        java.lang.Object r1 = defpackage.ank.a(r1)
                                        com.autonavi.minimap.bundle.maphome.service.IMainMapService r1 = (com.autonavi.minimap.bundle.maphome.service.IMainMapService) r1
                                        if (r1 == 0) goto L_0x00e9
                                        bid r1 = r1.e()
                                        if (r1 == 0) goto L_0x00e9
                                        android.content.Intent r2 = new android.content.Intent
                                        java.lang.String r3 = "android.intent.action.VIEW"
                                        android.net.Uri r0 = android.net.Uri.parse(r0)
                                        r2.<init>(r3, r0)
                                        r1.startScheme(r2)
                                    L_0x00e9:
                                        return
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: defpackage.ddc.AnonymousClass1.AnonymousClass1.run():void");
                                }
                            });
                        } else {
                            if (bno.a) {
                                AMapLog.e("SharedPassphraseManager", "checkClipboard response is over 5s ", true);
                            }
                        }
                    }

                    public final void a(Exception exc) {
                        if (bno.a) {
                            StringBuilder sb = new StringBuilder("sendParseCode onError e:");
                            sb.append(exc.toString());
                            AMapLog.e("SharedPassphraseManager", sb.toString(), true);
                        }
                    }
                };
                AosRequest aosRequest = new AosRequest();
                StringBuilder sb = new StringBuilder();
                sb.append(ddb.a);
                sb.append("/ws/oss/operation/parse-code");
                aosRequest.setUrl(sb.toString());
                aosRequest.setMethod(ddb.b ? 1 : 0);
                aosRequest.setHttpBodyRecvType(0);
                aosRequest.addSignParam("channel");
                aosRequest.addSignParam("tid");
                aosRequest.addReqParam("share_text", parseCodeParam.shareText);
                ddb.c = NetworkService.getAosNetwork().send(aosRequest, new dkq<PassphraseResponse, dko>(r1) {
                    public final /* synthetic */ dkm a() {
                        return new PassphraseResponse();
                    }
                });
            }
        }
    }
}
