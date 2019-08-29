package com.autonavi.minimap.bundle.share.entity;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.tservice.TserviceRequestHolder;
import com.autonavi.minimap.tservice.param.SendPoi2CarRequest;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public final class SendToCarShare extends ShareBase {
    Send2CarCallback a = null;
    private f b;
    private bid c;
    private CompatDialog d;

    public static class Send2CarCallback extends FalconAosPrepareResponseCallback<JSONObject> {
        private WeakReference<bid> a;
        private CompatDialog b;

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return b(aosByteResponse);
        }

        public final /* synthetic */ void a(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            a();
            if (this.a == null || this.a.get() == null || !((bid) this.a.get()).isAlive()) {
                ddi.a().b();
            } else if (jSONObject == null) {
                ToastHelper.showLongToast(((bid) this.a.get()).getContext().getString(R.string.share_auto_network_err));
            } else {
                int optInt = jSONObject.optInt("code", 0);
                jSONObject.optString("message", "");
                if (optInt == 1) {
                    bid bid = (bid) this.a.get();
                    if (bid != null) {
                        Context context = bid.getContext();
                        a aVar = new a(context);
                        aVar.a(LayoutInflater.from(context).inflate(R.layout.layout_share_auto_succeed_alert_content, null));
                        aVar.a(R.string.share_auto_share_succeed_title);
                        aVar.a(true);
                        aVar.a(R.string.share_auto_share_fail_succeed_text, (a) new a(bid) {
                            final /* synthetic */ bid a;

                            {
                                this.a = r1;
                            }

                            public final void onClick(AlertView alertView, int i) {
                                this.a.dismissViewLayer(alertView);
                            }
                        });
                        aVar.b = new a() {
                            public final void onClick(AlertView alertView, int i) {
                            }
                        };
                        aVar.c = new a() {
                            public final void onClick(AlertView alertView, int i) {
                            }
                        };
                        bid.showViewLayer(aVar.a());
                    }
                    ddi.a().a(2, 0);
                } else if (optInt != 14) {
                    if (optInt == 2) {
                        bid bid2 = (bid) this.a.get();
                        if (bid2 != null) {
                            Context context2 = bid2.getContext();
                            a aVar2 = new a(context2);
                            aVar2.a(LayoutInflater.from(context2).inflate(R.layout.layout_share_auto_fail_alert_content, null));
                            aVar2.a(R.string.share_auto_share_fail_title);
                            aVar2.a(true);
                            aVar2.a(R.string.share_auto_share_fail_positive_text, (a) new a(bid2) {
                                final /* synthetic */ bid a;

                                {
                                    this.a = r1;
                                }

                                public final void onClick(AlertView alertView, int i) {
                                    awt awt = (awt) a.a.a(awt.class);
                                    if (awt != null) {
                                        awt.a((String) "share");
                                        this.a.dismissViewLayer(alertView);
                                    }
                                }
                            });
                            aVar2.b(R.string.share_auto_share_fail_negative_text, (a) new a(bid2) {
                                final /* synthetic */ bid a;

                                {
                                    this.a = r1;
                                }

                                public final void onClick(AlertView alertView, int i) {
                                    this.a.dismissViewLayer(alertView);
                                }
                            });
                            aVar2.b = new a() {
                                public final void onClick(AlertView alertView, int i) {
                                }
                            };
                            aVar2.c = new a() {
                                public final void onClick(AlertView alertView, int i) {
                                }
                            };
                            bid2.showViewLayer(aVar2.a());
                        }
                        ddi.a().a(2, -1);
                    } else if (optInt >= 0) {
                        ToastHelper.showLongToast(((bid) this.a.get()).getContext().getString(R.string.share_auto_network_err));
                    }
                }
                ddi.a().b();
            }
        }

        public Send2CarCallback(bid bid, CompatDialog compatDialog) {
            this.a = new WeakReference<>(bid);
            this.b = compatDialog;
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            a();
            if (this.a == null || this.a.get() == null || !((bid) this.a.get()).isAlive()) {
                ddi.a().b();
                return;
            }
            ToastHelper.showLongToast(((bid) this.a.get()).getContext().getString(R.string.share_auto_network_err));
            ddi.a().b();
        }

        private static JSONObject b(AosByteResponse aosByteResponse) {
            if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                return null;
            }
            try {
                return AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        private void a() {
            if (this.b != null && this.b.isShowing()) {
                this.b.dismiss();
            }
        }
    }

    public final int getShareType() {
        return 2;
    }

    public final void onFinishResult(String str) {
    }

    public SendToCarShare(f fVar) {
        this.b = fVar;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        if (this.c == null) {
            ddi.a().b();
            return;
        }
        String b2 = b();
        if (!TextUtils.isEmpty(b2)) {
            SendPoi2CarRequest sendPoi2CarRequest = new SendPoi2CarRequest();
            sendPoi2CarRequest.b = b2;
            sendPoi2CarRequest.c = "true";
            sendPoi2CarRequest.d = "86400";
            sendPoi2CarRequest.e = "auto_amap";
            sendPoi2CarRequest.f = "amap";
            sendPoi2CarRequest.g = "aimpoi";
            if (this.d != null) {
                this.d.dismiss();
                this.d = null;
            }
            this.d = aav.a(sendPoi2CarRequest, "");
            this.d.show();
            this.a = new Send2CarCallback(this.c, this.d);
            TserviceRequestHolder.getInstance().sendSendPoi2Car(sendPoi2CarRequest, this.a);
            return;
        }
        ddi.a().b();
    }

    private static String b() {
        String ajxStorageItem = getAjxStorageItem("localPoiInfo");
        if (TextUtils.isEmpty(ajxStorageItem)) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject(ajxStorageItem);
            JSONObject optJSONObject = jSONObject.optJSONObject("clientData");
            if (optJSONObject == null) {
                return "";
            }
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("poiInfo");
            if (optJSONObject2 == null) {
                return "";
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, optJSONObject2.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON));
            jSONObject2.put("lat", optJSONObject2.optDouble("lat"));
            jSONObject2.put("poiID", optJSONObject2.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            jSONObject2.put("name", optJSONObject2.optString("name"));
            jSONObject2.put("address", optJSONObject2.optString("address"));
            jSONObject2.put("poi_type", optJSONObject2.optString("new_type"));
            JSONObject optJSONObject3 = jSONObject.optJSONObject("aosData");
            if (optJSONObject3 != null) {
                JSONObject optJSONObject4 = optJSONObject3.optJSONObject(RpcConstant.BASE);
                if (optJSONObject4 != null) {
                    String optString = optJSONObject4.optString("navi_geometry");
                    if (!TextUtils.isEmpty(optString)) {
                        String[] split = optString.split(",");
                        if (split.length == 2) {
                            try {
                                double parseDouble = Double.parseDouble(split[0]);
                                double parseDouble2 = Double.parseDouble(split[1]);
                                jSONObject2.put("nav_lon", parseDouble);
                                jSONObject2.put("nav_lat", parseDouble2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return jSONObject2.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public final void startShare() {
        boolean z;
        this.c = AMapPageUtil.getPageContext();
        if (this.c == null || TextUtils.isEmpty(this.b.g)) {
            ddi.a().b();
            return;
        }
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            z = false;
        } else {
            z = iAccountService.a();
        }
        if (!z) {
            AnonymousClass1 r0 = new anq() {
                public final void onComplete(boolean z) {
                    if (z) {
                        SendToCarShare.this.a();
                    }
                }

                public final void loginOrBindCancel() {
                    ddi.a().b();
                }
            };
            IAccountService iAccountService2 = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService2 != null) {
                iAccountService2.a(this.c, (anq) r0);
            }
            return;
        }
        a();
    }
}
