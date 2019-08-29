package com.autonavi.bundle.agroup.ajx;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.entity.GroupInfo;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.minimap.agroup.entity.TeamInfo;
import com.autonavi.minimap.agroup.widget.AGroupProtocolAlertView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;

@AjxModule("agroup")
@KeepName
public class ModuleAgroup extends AbstractModule implements com.autonavi.minimap.bundle.agroup.api.IDataService.a, cuj {
    public static final String GROUP_ANNOUNCEMENT_PATH = "path://amap_bundle_agroup/src/BizBasemapAGroupNoticePage.page.js";
    public static final String MODULE_NAME = "agroup";
    public static final String MYGROUP_SETTING_PATH = "path://amap_bundle_agroup/src/BizBasemapAgroupSettings.page.js";
    public static final String MY_GROUP_PATH = "path://amap_bundle_agroup/src/BizBasemapAgroupMyGroup.page.js";
    public static final String SP_KEY_AGROUP_JOIN_TEAM_PROTOCOL_AGREE = "agroup_join_team_protocol_agree";
    private static final String TAG = "ModuleAgroup";
    private cie mActiviesHelper;
    private cjt mDataService = cjt.a();
    private JsFunctionCallback mGroupDissolutionCallBack;
    private int mLastDestAjxViewHeight;
    private JsFunctionCallback mMembersChangedCallback;
    private a mOpenAnimateStatueListener;
    private JsFunctionCallback mOverlayItemCallback;
    private JsFunctionCallback mSuperGroupInfoChangedCallback;
    private JsFunctionCallback mTeamInfoChangedCallback;
    private JsFunctionCallback mTeamStatusChangedCallBack;

    public interface a {
        void a(String str);
    }

    public ModuleAgroup(IAjxContext iAjxContext) {
        super(iAjxContext);
        if (this.mDataService != null) {
            this.mDataService.a((com.autonavi.minimap.bundle.agroup.api.IDataService.a) this);
        }
        this.mActiviesHelper = new cie();
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        if (this.mDataService != null) {
            this.mDataService.b((com.autonavi.minimap.bundle.agroup.api.IDataService.a) this);
        }
        this.mOverlayItemCallback = null;
        this.mTeamInfoChangedCallback = null;
        this.mMembersChangedCallback = null;
        this.mGroupDissolutionCallBack = null;
        this.mTeamStatusChangedCallBack = null;
        this.mSuperGroupInfoChangedCallback = null;
    }

    @AjxMethod("createTeam")
    public void createTeam(String str) {
        GroupInfo c = cju.c(str);
        if (this.mDataService != null) {
            this.mDataService.a(c);
        }
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.q();
        }
    }

    @AjxMethod("joinTeam")
    public void joinTeam(String str, final JsFunctionCallback jsFunctionCallback) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("type");
            c.a.a(jSONObject.optString("teamNumber"), optInt, new defpackage.cik.a() {
                public final void a(String str) {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(str);
                    }
                }
            });
        } catch (JSONException unused) {
        }
    }

    @AjxMethod("changeTeam")
    public void changeTeam(String str, final JsFunctionCallback jsFunctionCallback) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("type");
            String optString = jSONObject.optString("teamNumber");
            cik a2 = c.a;
            AnonymousClass2 r2 = new defpackage.cik.a() {
                public final void a(String str) {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(str);
                    }
                }
            };
            if (!cik.a()) {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null && pageContext.isAlive()) {
                    if (pageContext.getContext() != null) {
                        a2.a(pageContext);
                        a2.a(pageContext, optString, optInt, r2);
                        return;
                    }
                }
                return;
            }
            cin.a(optString, (defpackage.cik.a) r2);
        } catch (JSONException unused) {
        }
    }

    @AjxMethod("openMyGroupPage")
    @Deprecated
    public void openMyGroupPage(String str) {
        cjp.a("amapuri://AGroup/joinGroup", str);
    }

    @AjxMethod("setTeamInfo")
    public void setTeamInfo(String str) {
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                TeamInfo a2 = cju.a(jSONObject);
                String optString = jSONObject.optString("teamStamp");
                if (this.mDataService != null) {
                    this.mDataService.a(a2, TeamStatus.STATUS_USER_IN_THIS_TEAM);
                    this.mDataService.a(optString, (String) null);
                }
            } catch (JSONException unused) {
            }
        }
    }

    @AjxMethod("registerGroupDissolutionCallBack")
    public void registerGroupDissolutionCallBack(JsFunctionCallback jsFunctionCallback) {
        this.mGroupDissolutionCallBack = jsFunctionCallback;
        if (this.mGroupDissolutionCallBack != null && this.mDataService != null) {
            onTeamStatusChanged(this.mDataService.k());
        }
    }

    @AjxMethod("quitGroup")
    public void quitGroup(String str) {
        TeamStatus teamStatus;
        if (!TextUtils.isEmpty(str)) {
            try {
                int optInt = new JSONObject(str).optInt("reason");
                if (optInt == 1) {
                    teamStatus = TeamStatus.STATUS_USER_NOT_JOIN_IN_TEAM;
                } else if (optInt == 2) {
                    teamStatus = TeamStatus.STATUS_TEAM_DISMISS;
                } else if (optInt == 3) {
                    teamStatus = TeamStatus.STATUS_NONE;
                } else {
                    teamStatus = null;
                }
                cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
                if (cuh != null) {
                    cuh.a(teamStatus);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                cuh cuh2 = (cuh) defpackage.esb.a.a.a(cuh.class);
                if (cuh2 != null) {
                    cuh2.a((TeamStatus) null);
                }
            } catch (Throwable th) {
                cuh cuh3 = (cuh) defpackage.esb.a.a.a(cuh.class);
                if (cuh3 != null) {
                    cuh3.a((TeamStatus) null);
                }
                throw th;
            }
        }
    }

    @AjxMethod("kickMember")
    public void kickMember(String str) {
        if (str != null) {
            GroupInfo c = cju.c(str);
            if (this.mDataService != null) {
                this.mDataService.a(c);
            }
        }
    }

    @AjxMethod("overview")
    public void overview() {
        cji.e().h();
    }

    @AjxMethod("onClickMemberItem")
    public void onClickMemberItem(String str) {
        cji.e().onMemberSelected(str);
    }

    @AjxMethod("registerOverlayItemClickListener")
    public void registerOverlayItemClickListener(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            cji.e().l = this;
        } else {
            cji.e().l = null;
        }
        this.mOverlayItemCallback = jsFunctionCallback;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @com.autonavi.minimap.ajx3.modules.AjxMethod("invite")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void invite(java.lang.String r5) {
        /*
            r4 = this;
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0025 }
            r1.<init>(r5)     // Catch:{ JSONException -> 0x0025 }
            java.lang.String r5 = "inviteCode"
            java.lang.String r5 = r1.optString(r5)     // Catch:{ JSONException -> 0x0025 }
            java.lang.String r2 = "channel"
            java.lang.String r1 = r1.optString(r2)     // Catch:{ JSONException -> 0x0025 }
            esb r2 = defpackage.esb.a.a     // Catch:{ JSONException -> 0x0025 }
            java.lang.Class<dcb> r3 = defpackage.dcb.class
            esc r2 = r2.a(r3)     // Catch:{ JSONException -> 0x0025 }
            dcb r2 = (defpackage.dcb) r2     // Catch:{ JSONException -> 0x0025 }
            if (r2 == 0) goto L_0x0025
            boolean r5 = r2.a(r5, r1)     // Catch:{ JSONException -> 0x0025 }
            goto L_0x0026
        L_0x0025:
            r5 = 0
        L_0x0026:
            if (r5 == 0) goto L_0x006c
            cie r5 = r4.mActiviesHelper
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r2 = 0
            if (r1 != 0) goto L_0x0032
            goto L_0x0039
        L_0x0032:
            boolean r3 = r1 instanceof com.autonavi.map.fragmentcontainer.page.AbstractBasePage
            if (r3 == 0) goto L_0x0039
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r1 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r1
            r2 = r1
        L_0x0039:
            if (r2 == 0) goto L_0x006c
            java.lang.ref.WeakReference r1 = new java.lang.ref.WeakReference
            r1.<init>(r2)
            r5.b = r1
            ctl r1 = r5.b()
            java.lang.String r2 = "21"
            r1.a(r2)
            r5.c = r0
            java.lang.String r0 = ""
            r5.a = r0
            r5.a()
            drn$e r0 = r5.d
            defpackage.drm.a(r0)
            dro$c r0 = r5.e
            defpackage.drm.a(r0)
            ctl r0 = r5.b()
            java.lang.String r1 = "21"
            com.autonavi.minimap.agroup.helper.AGroupActiviesHelper$3 r2 = new com.autonavi.minimap.agroup.helper.AGroupActiviesHelper$3
            r2.<init>(r5)
            r0.a(r1, r2)
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.agroup.ajx.ModuleAgroup.invite(java.lang.String):void");
    }

    @AjxMethod("getGroupInfo")
    public void getGroupInfo(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            JSONObject a2 = cju.a();
            Object[] objArr = new Object[1];
            objArr[0] = a2 != null ? a2.toString() : "";
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod("getTeamInfo")
    public void getTeamInfo(JsFunctionCallback jsFunctionCallback) {
        if (!(jsFunctionCallback == null || this.mDataService == null)) {
            JSONObject a2 = cju.a(this.mDataService.c());
            Object[] objArr = new Object[1];
            objArr[0] = a2 != null ? a2.toString() : "";
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod("getMembers")
    public void getMembers(JsFunctionCallback jsFunctionCallback) {
        if (!(jsFunctionCallback == null || this.mDataService == null)) {
            JSONArray a2 = cju.a(this.mDataService.f());
            Object[] objArr = new Object[1];
            objArr[0] = a2 != null ? a2.toString() : "";
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod("registerTeamInfoChangedLister")
    public void registerTeamInfoChangedLister(JsFunctionCallback jsFunctionCallback) {
        this.mTeamInfoChangedCallback = jsFunctionCallback;
    }

    @AjxMethod("getSuperGroupInfo")
    public void getSuperGroupInfo(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null && this.mDataService != null) {
            JSONObject a2 = cju.a(this.mDataService.b());
            Object[] objArr = new Object[1];
            objArr[0] = a2 != null ? a2.toString() : "";
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod("registerSuperGroupInfoChangedLister")
    public void registerSuperGroupInfoChangedLister(JsFunctionCallback jsFunctionCallback) {
        this.mSuperGroupInfoChangedCallback = jsFunctionCallback;
    }

    @AjxMethod("str2int")
    public void str2int(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(cjw.a(str));
        }
    }

    @AjxMethod("int2str")
    public void int2str(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(cjw.b(str));
        }
    }

    @AjxMethod("registerMembersChangedLister")
    public void registerMembersChangedLister(JsFunctionCallback jsFunctionCallback) {
        this.mMembersChangedCallback = jsFunctionCallback;
    }

    @AjxMethod("isUnknownTeamInfo")
    public void isUnknownTeamInfo(JsFunctionCallback jsFunctionCallback) {
        if (this.mDataService != null && jsFunctionCallback != null) {
            cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
            Object[] objArr = new Object[1];
            objArr[0] = cuh != null ? cuh.e() : false ? "1" : "0";
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod("registerGetTeamInfoStatus")
    public void registerGetTeamInfoStatus(JsFunctionCallback jsFunctionCallback) {
        this.mTeamStatusChangedCallBack = jsFunctionCallback;
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.a(jsFunctionCallback);
        }
    }

    @AjxMethod("getTeamNumber")
    public void getTeamNumber(JsFunctionCallback jsFunctionCallback) {
        if (!(jsFunctionCallback == null || this.mDataService == null)) {
            TeamInfo c = this.mDataService.c();
            Object[] objArr = new Object[1];
            objArr[0] = c != null ? c.teamNumber : "";
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod("openAnimateStatue")
    public void openAnimateStatue(String str) {
        if (this.mOpenAnimateStatueListener != null) {
            this.mOpenAnimateStatueListener.a(str);
        }
    }

    public void setOpenAnimateStatueListener(a aVar) {
        this.mOpenAnimateStatueListener = aVar;
    }

    @AjxMethod("showGroupProtocol")
    public void showGroupProtocol(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            final bid pageContext = AMapPageFramework.getPageContext();
            if (pageContext != null) {
                final AGroupProtocolAlertView aGroupProtocolAlertView = new AGroupProtocolAlertView(pageContext.getContext());
                aGroupProtocolAlertView.setNegativeListener(new OnClickListener() {
                    public final void onClick(View view) {
                        pageContext.dismissViewLayer(aGroupProtocolAlertView);
                        jsFunctionCallback.callback(Boolean.FALSE);
                    }
                });
                aGroupProtocolAlertView.setPositiveListener(new OnClickListener() {
                    public final void onClick(View view) {
                        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(ModuleAgroup.SP_KEY_AGROUP_JOIN_TEAM_PROTOCOL_AGREE, true);
                        pageContext.dismissViewLayer(aGroupProtocolAlertView);
                        jsFunctionCallback.callback(Boolean.TRUE);
                    }
                });
                pageContext.showViewLayer(aGroupProtocolAlertView);
            }
        }
    }

    @AjxMethod("hasAgreeGroupProtocol")
    public void hasAgreeGroupProtocol(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(SP_KEY_AGROUP_JOIN_TEAM_PROTOCOL_AGREE, false)));
        }
    }

    @AjxMethod("openDestSearchPage")
    public void openDestSearchPage(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("search_for", 2);
            pageBundle.putString("hint", getNativeContext().getString(R.string.agroup_dest_search_hint));
            pageBundle.putObject("callback", new Callback<POI>() {
                public void callback(POI poi) {
                    String str;
                    try {
                        JSONObject b2 = bnx.b(poi);
                        str = b2 != null ? b2.toString() : "";
                    } catch (Exception unused) {
                        str = "";
                    }
                    jsFunctionCallback.callback(str);
                }

                public void error(Throwable th, boolean z) {
                    jsFunctionCallback.callback("");
                }
            });
            pageContext.startPage((String) "search.fragment.SearchCallbackFragment", pageBundle);
        }
    }

    @AjxMethod("updateTeamNickName")
    public void updateTeamNickName(String str) {
        MemberInfo g = this.mDataService.g();
        if (g != null) {
            g.nickname = str;
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setViewState")
    public void setViewState(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                int optInt = new JSONObject(str).optInt("destinationH");
                if (optInt >= 0) {
                    int pixelToDip = (int) DimensionUtils.pixelToDip((float) DimensionUtils.standardUnitToPixel((float) optInt));
                    int i = pixelToDip - this.mLastDestAjxViewHeight;
                    cji.e().m = i;
                    cji e = cji.e();
                    if (e.d != null) {
                        int i2 = e.d.b;
                        cjl cjl = e.d;
                        cjl.a(cjl.a, i2 + i, cjl.c, cjl.d);
                    }
                    this.mLastDestAjxViewHeight = pixelToDip;
                }
            } catch (Exception unused) {
            }
        }
    }

    public void onMemberSelected(String str) {
        if (this.mOverlayItemCallback != null) {
            this.mOverlayItemCallback.callback(str);
        }
    }

    public void onTeamInfoChanged() {
        if (this.mTeamInfoChangedCallback != null && this.mDataService != null) {
            JSONObject a2 = cju.a(this.mDataService.c());
            JsFunctionCallback jsFunctionCallback = this.mTeamInfoChangedCallback;
            Object[] objArr = new Object[1];
            objArr[0] = a2 != null ? a2.toString() : "";
            jsFunctionCallback.callback(objArr);
        }
    }

    public void onMemberBaseInfoChanged() {
        if (!(this.mMembersChangedCallback == null || this.mDataService == null)) {
            JSONArray a2 = cju.a(this.mDataService.f());
            JsFunctionCallback jsFunctionCallback = this.mMembersChangedCallback;
            Object[] objArr = new Object[1];
            objArr[0] = a2 != null ? a2.toString() : "";
            jsFunctionCallback.callback(objArr);
        }
        if (this.mTeamStatusChangedCallBack != null) {
            JSONObject a3 = cju.a();
            JsFunctionCallback jsFunctionCallback2 = this.mTeamStatusChangedCallBack;
            Object[] objArr2 = new Object[1];
            objArr2[0] = a3 != null ? a3.toString() : "";
            jsFunctionCallback2.callback(objArr2);
        }
    }

    public void onMemberLocationInfoChanged() {
        if (this.mMembersChangedCallback != null && this.mDataService != null) {
            JSONArray a2 = cju.a(this.mDataService.f());
            JsFunctionCallback jsFunctionCallback = this.mMembersChangedCallback;
            Object[] objArr = new Object[1];
            objArr[0] = a2 != null ? a2.toString() : "";
            jsFunctionCallback.callback(objArr);
        }
    }

    public void onTeamStatusChanged(TeamStatus teamStatus) {
        String str;
        if (this.mGroupDissolutionCallBack != null) {
            if (teamStatus == TeamStatus.STATUS_USER_NOT_JOIN_IN_TEAM) {
                str = "1";
            } else if (teamStatus == TeamStatus.STATUS_TEAM_DISMISS) {
                str = "2";
            } else {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("reason", str);
            } catch (JSONException unused) {
            }
            this.mGroupDissolutionCallBack.callback(jSONObject.toString());
        }
    }

    public void onSuperGroupInfoChanged() {
        if (this.mSuperGroupInfoChangedCallback != null && this.mDataService != null) {
            JSONObject a2 = cju.a(this.mDataService.b());
            JsFunctionCallback jsFunctionCallback = this.mSuperGroupInfoChangedCallback;
            Object[] objArr = new Object[1];
            objArr[0] = a2 != null ? a2.toString() : "";
            jsFunctionCallback.callback(objArr);
        }
    }

    @AjxMethod("updateGroupInfo")
    public void updateGroupInfo(String str, JsFunctionCallback jsFunctionCallback) {
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b(jsFunctionCallback);
        }
    }
}
