package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.agroup.entity.DestinationInfo;
import com.autonavi.minimap.agroup.entity.GroupInfo;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.minimap.agroup.entity.TeamInfo;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import com.autonavi.sdk.location.LocationInstrument;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cju reason: default package */
/* compiled from: GroupParseUtil */
public final class cju {
    private static final SparseArray<TeamStatus> a;

    static {
        SparseArray<TeamStatus> sparseArray = new SparseArray<>(9);
        a = sparseArray;
        sparseArray.append(1, TeamStatus.STATUS_SUCCESS);
        a.append(2001, TeamStatus.STATUS_USER_NOT_LOGIN);
        a.append(2002, TeamStatus.STATUS_USER_IN_TEAM);
        a.append(2006, TeamStatus.STATUS_TEAM_DISMISS);
        a.append(2007, TeamStatus.STATUS_TEAM_NOT_EXISTS);
        a.append(2008, TeamStatus.STATUS_TEAM_MEMBER_LIMITED);
        a.append(2009, TeamStatus.STATUS_USER_IN_THIS_TEAM);
        a.append(2010, TeamStatus.STATUS_USER_IN_OTHER_TEAM);
        a.append(2013, TeamStatus.STATUS_USER_NOT_JOIN_IN_TEAM);
        a.append(2027, TeamStatus.STATUS_LEADER_IN_OTHER_TEAM);
    }

    public static TeamStatus a(int i) {
        TeamStatus teamStatus = a.get(i, TeamStatus.STATUS_NONE);
        return teamStatus == TeamStatus.STATUS_SUCCESS ? TeamStatus.STATUS_USER_IN_THIS_TEAM : teamStatus;
    }

    public static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return new JSONObject(str).optInt("code", 0);
        } catch (Exception e) {
            DebugLog.e("TeamStatus", "parseAosStatusCode", e);
            return 0;
        }
    }

    public static TeamStatus b(String str) {
        return a(a(str));
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [com.autonavi.minimap.agroup.entity.GroupInfo] */
    /* JADX WARNING: type inference failed for: r0v2, types: [com.autonavi.minimap.agroup.entity.GroupInfo] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r1v2, types: [com.autonavi.minimap.agroup.entity.GroupInfo] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r2v2, types: [com.autonavi.minimap.agroup.entity.GroupInfo] */
    /* JADX WARNING: type inference failed for: r1v3, types: [com.autonavi.minimap.agroup.entity.TeamInfo] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v7, types: [com.autonavi.minimap.agroup.entity.TeamInfo] */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v3
      assigns: []
      uses: []
      mth insns count: 43
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.minimap.agroup.entity.GroupInfo c(java.lang.String r7) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            com.autonavi.minimap.agroup.entity.GroupInfo r0 = new com.autonavi.minimap.agroup.entity.GroupInfo
            r0.<init>()
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0078 }
            r2.<init>(r7)     // Catch:{ Exception -> 0x0078 }
            java.lang.String r7 = "code"
            r3 = 0
            int r7 = r2.optInt(r7, r3)     // Catch:{ Exception -> 0x0078 }
            com.autonavi.minimap.bundle.agroup.api.IDataService$TeamStatus r3 = a(r7)     // Catch:{ Exception -> 0x0078 }
            r0.setTeamStatus(r3)     // Catch:{ Exception -> 0x0078 }
            r0.setCode(r7)     // Catch:{ Exception -> 0x0078 }
            r4 = 1
            if (r7 != r4) goto L_0x0080
            java.lang.String r7 = "data"
            org.json.JSONObject r7 = r2.getJSONObject(r7)     // Catch:{ Exception -> 0x0078 }
            if (r7 != 0) goto L_0x002f
            goto L_0x006b
        L_0x002f:
            com.autonavi.minimap.agroup.entity.GroupInfo r2 = new com.autonavi.minimap.agroup.entity.GroupInfo     // Catch:{ Exception -> 0x0078 }
            r2.<init>()     // Catch:{ Exception -> 0x0078 }
            java.lang.String r5 = "teamStamp"
            java.lang.String r5 = a(r7, r5)     // Catch:{ Exception -> 0x0078 }
            java.lang.String r6 = "memberStamp"
            java.lang.String r6 = a(r7, r6)     // Catch:{ Exception -> 0x0078 }
            r2.setTeamStamp(r5)     // Catch:{ Exception -> 0x0078 }
            r2.setMemberStamp(r6)     // Catch:{ Exception -> 0x0078 }
            java.lang.String r5 = "supNum"
            r6 = -1
            int r5 = r7.optInt(r5, r6)     // Catch:{ Exception -> 0x0078 }
            r2.setSuperGroupMemberCount(r5)     // Catch:{ Exception -> 0x0078 }
            if (r7 != 0) goto L_0x0055
            goto L_0x0060
        L_0x0055:
            java.lang.String r1 = "team"
            org.json.JSONObject r1 = r7.optJSONObject(r1)     // Catch:{ Exception -> 0x0078 }
            com.autonavi.minimap.agroup.entity.TeamInfo r1 = a(r1)     // Catch:{ Exception -> 0x0078 }
        L_0x0060:
            r2.setTeamInfo(r1)     // Catch:{ Exception -> 0x0078 }
            java.util.List r7 = b(r7)     // Catch:{ Exception -> 0x0078 }
            r2.setMemberInfoList(r7)     // Catch:{ Exception -> 0x0078 }
            r1 = r2
        L_0x006b:
            if (r1 == 0) goto L_0x0077
            r1.setTeamStatus(r3)     // Catch:{ Exception -> 0x0074 }
            r1.setCode(r4)     // Catch:{ Exception -> 0x0074 }
            goto L_0x0077
        L_0x0074:
            r7 = move-exception
            r0 = r1
            goto L_0x0079
        L_0x0077:
            return r1
        L_0x0078:
            r7 = move-exception
        L_0x0079:
            java.lang.String r1 = "GroupInfo"
            java.lang.String r2 = "parseAosGroupInfo"
            com.amap.bundle.blutils.log.DebugLog.e(r1, r2, r7)
        L_0x0080:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cju.c(java.lang.String):com.autonavi.minimap.agroup.entity.GroupInfo");
    }

    public static cid d(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        cid cid = new cid();
        try {
            JSONObject jSONObject = new JSONObject(str);
            cid.a = jSONObject.optInt("state", 0);
            cid.b = str;
            cid.c = b(jSONObject);
            cid.d = jSONObject.optInt("supNum", -1);
            return cid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TeamInfo a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.teamId = a(jSONObject, "teamId");
        teamInfo.teamName = a(jSONObject, "teamName");
        teamInfo.teamNumber = a(jSONObject, "teamNumber");
        teamInfo.teamLeaderUid = a(jSONObject, "leader");
        teamInfo.teamDismissTime = jSONObject.optLong("teamDismissTime");
        teamInfo.teamCreatedTime = jSONObject.optLong("teamCreatedTime");
        teamInfo.lastUpdate = jSONObject.optLong("lastUpdate");
        JSONObject optJSONObject = jSONObject.optJSONObject("announcement");
        if (optJSONObject != null) {
            teamInfo.mAnnouncement.setTimeStamp(optJSONObject.optLong("ts", -1));
            teamInfo.mAnnouncement.setContent(optJSONObject.optString("content", null));
        }
        JSONObject optJSONObject2 = jSONObject.optJSONObject("destination");
        if (optJSONObject2 != null) {
            DestinationInfo destinationInfo = new DestinationInfo();
            destinationInfo.poiid = a(optJSONObject2, LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
            destinationInfo.name = a(optJSONObject2, "name");
            destinationInfo.address = a(optJSONObject2, "address");
            destinationInfo.x = optJSONObject2.optInt(DictionaryKeys.CTRLXY_X);
            destinationInfo.y = optJSONObject2.optInt(DictionaryKeys.CTRLXY_Y);
            destinationInfo.cityCode = a(optJSONObject2, "city_code");
            destinationInfo.poiType = a(optJSONObject2, "poiType");
            destinationInfo.phoneNumbers = a(optJSONObject2, "phoneNumbers");
            destinationInfo.newType = a(optJSONObject2, "new_type");
            destinationInfo.industry = a(optJSONObject2, "industry");
            destinationInfo.towardsAngle = a(optJSONObject2, "towards_angle");
            destinationInfo.endPoiExtension = a(optJSONObject2, "end_poi_extension");
            destinationInfo.transparent = a(optJSONObject2, H5Param.LONG_TRANSPARENT);
            destinationInfo.parent = a(optJSONObject2, "parent");
            destinationInfo.floor = a(optJSONObject2, "f_nona");
            destinationInfo.childType = a(optJSONObject2, "childType");
            destinationInfo.entranceList = a(optJSONObject2, "entranceList");
            destinationInfo.exitList = a(optJSONObject2, "exitList");
            teamInfo.setDestination(destinationInfo);
        }
        return teamInfo;
    }

    private static List<MemberInfo> b(JSONObject jSONObject) throws JSONException {
        List<MemberInfo> list;
        int i;
        int i2;
        List<MemberInfo> list2;
        Object obj;
        List<MemberInfo> list3;
        MemberInfo memberInfo;
        JSONObject jSONObject2 = jSONObject;
        Object obj2 = null;
        if (jSONObject2 == null) {
            return null;
        }
        JSONArray optJSONArray = jSONObject2.optJSONArray("members");
        if (optJSONArray != null) {
            List<MemberInfo> arrayList = new ArrayList<>();
            int length = optJSONArray.length();
            int i3 = 0;
            while (i3 < length) {
                JSONObject jSONObject3 = optJSONArray.getJSONObject(i3);
                if (jSONObject3 != null) {
                    if (jSONObject3 == null) {
                        i2 = length;
                        i = i3;
                        List<MemberInfo> list4 = arrayList;
                        obj = obj2;
                        list2 = list4;
                    } else {
                        String a2 = a(jSONObject3, Oauth2AccessToken.KEY_UID);
                        String a3 = a(jSONObject3, ActionConstant.IMG_URL);
                        long optLong = jSONObject3.optLong("locationUpdateTime");
                        String a4 = a(jSONObject3, "source");
                        String a5 = a(jSONObject3, "nickName");
                        String a6 = a(jSONObject3, "tnn");
                        if (!TextUtils.isEmpty(a6)) {
                            a5 = a6;
                        }
                        long optLong2 = jSONObject3.optLong("joinTime");
                        JSONObject optJSONObject = jSONObject3.optJSONObject("locInfo");
                        GeoPoint geoPoint = new GeoPoint();
                        if (optJSONObject == null) {
                            list3 = arrayList;
                            geoPoint.setLonLat(0.0d, 0.0d);
                            i2 = length;
                            i = i3;
                        } else {
                            list3 = arrayList;
                            i2 = length;
                            i = i3;
                            geoPoint.setLonLat(optJSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON), optJSONObject.optDouble("lat"));
                        }
                        boolean optBoolean = jSONObject3.optBoolean("online");
                        if (!TextUtils.isEmpty(a5) || jSONObject3.has(ActionConstant.IMG_URL) || jSONObject3.has("joinTime")) {
                            memberInfo = MemberInfo.createMemberInfo(a2, a3, a5, geoPoint.getLatitude(), geoPoint.getLongitude(), optLong, optLong2, optBoolean, a4, i);
                        } else {
                            memberInfo = MemberInfo.createMemberInfo(a2, geoPoint.getLatitude(), geoPoint.getLongitude(), optLong, optBoolean);
                        }
                        obj = memberInfo;
                        list2 = list3;
                    }
                    list2.add(obj);
                } else {
                    list2 = arrayList;
                    i2 = length;
                    i = i3;
                }
                i3 = i + 1;
                arrayList = list2;
                length = i2;
                obj2 = null;
            }
            list = arrayList;
        } else {
            list = null;
        }
        return list;
    }

    public static String a(GeoPoint geoPoint) {
        JSONObject jSONObject = new JSONObject();
        if (geoPoint != null) {
            try {
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, geoPoint.getLongitude());
                jSONObject.put("lat", geoPoint.getLatitude());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, 0.0d);
            jSONObject.put("lat", 0.0d);
        }
        return jSONObject.toString();
    }

    public static TeamInfo e(String str) {
        if (str == null) {
            return null;
        }
        TeamInfo teamInfo = new TeamInfo();
        try {
            JSONObject optJSONObject = new JSONObject(str).optJSONObject("data");
            if (optJSONObject != null) {
                teamInfo.teamNumber = a(optJSONObject, "teamNumber");
                teamInfo.teamId = a(optJSONObject, "teamId");
                return teamInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String a(JSONObject jSONObject, String str) {
        String optString = jSONObject.optString(str);
        return "null".equalsIgnoreCase(optString) ? "" : optString;
    }

    @Nullable
    public static JSONObject a() {
        cjt a2 = cjt.a();
        JSONObject jSONObject = null;
        if (a2 == null) {
            return null;
        }
        GroupInfo b = a2.b();
        if (b != null) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                JSONObject a3 = a(b.getTeamInfo());
                if (a3 != null) {
                    jSONObject2.put("team", a3);
                    String teamStamp = b.getTeamStamp();
                    if (!TextUtils.isEmpty(teamStamp)) {
                        jSONObject2.put("teamStamp", teamStamp);
                        String memberStamp = b.getMemberStamp();
                        if (!TextUtils.isEmpty(memberStamp)) {
                            jSONObject2.put("memberStamp", memberStamp);
                            JSONArray a4 = a(b.getMemberInfoList());
                            if (a4 != null) {
                                if (a4.length() > 0) {
                                    jSONObject2.put("members", a4);
                                    jSONObject2.put("code", b.getCode());
                                    jSONObject2.put("supNum", b.getSuperGroupMemberCount());
                                    jSONObject = jSONObject2;
                                }
                            }
                        }
                    }
                }
            } catch (JSONException unused) {
            }
        }
        return jSONObject;
    }

    public static JSONObject a(TeamInfo teamInfo) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = null;
        if (teamInfo == null) {
            return null;
        }
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put("teamId", teamInfo.teamId);
            jSONObject3.put("teamName", teamInfo.teamName);
            jSONObject3.put("teamNumber", teamInfo.teamNumber);
            jSONObject3.put("leader", teamInfo.teamLeaderUid);
            jSONObject3.put("teamDismissTime", teamInfo.teamDismissTime);
            jSONObject3.put("teamCreatedTime", teamInfo.teamCreatedTime);
            jSONObject3.put("lastUpdate", teamInfo.lastUpdate);
            if (teamInfo.mAnnouncement != null) {
                jSONObject = new JSONObject();
                jSONObject.put("content", teamInfo.mAnnouncement.getContent());
                jSONObject.put("ts", teamInfo.mAnnouncement.getTimeStamp());
            } else {
                jSONObject = null;
            }
            if (jSONObject != null) {
                jSONObject3.put("announcement", jSONObject);
            }
            DestinationInfo destinationInfo = teamInfo.getDestinationInfo();
            if (destinationInfo != null) {
                jSONObject2 = new JSONObject();
                jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, destinationInfo.poiid);
                jSONObject2.put("name", destinationInfo.name);
                jSONObject2.put("address", destinationInfo.address);
                jSONObject2.put(DictionaryKeys.CTRLXY_X, destinationInfo.x);
                jSONObject2.put(DictionaryKeys.CTRLXY_Y, destinationInfo.y);
                jSONObject2.put("city_code", destinationInfo.cityCode);
                jSONObject2.put("poiType", destinationInfo.poiType);
                jSONObject2.put("phoneNumbers", destinationInfo.phoneNumbers);
                jSONObject2.put("new_type", destinationInfo.newType);
                jSONObject2.put("industry", destinationInfo.industry);
                jSONObject2.put("towards_angle", destinationInfo.towardsAngle);
                jSONObject2.put("end_poi_extension", destinationInfo.endPoiExtension);
                jSONObject2.put(H5Param.LONG_TRANSPARENT, destinationInfo.transparent);
                jSONObject2.put("parent", destinationInfo.parent);
                jSONObject2.put("f_nona", destinationInfo.floor);
                jSONObject2.put("childType", destinationInfo.childType);
                jSONObject2.put("entranceList", destinationInfo.entranceList);
                jSONObject2.put("exitList", destinationInfo.exitList);
            }
            if (jSONObject2 != null) {
                jSONObject3.put("destination", jSONObject2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject3;
    }

    public static JSONArray a(@NonNull List<MemberInfo> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (MemberInfo next : list) {
            if (next != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(Oauth2AccessToken.KEY_UID, next.uid);
                    jSONObject.put(ActionConstant.IMG_URL, next.imgUrl);
                    jSONObject.put("nickName", next.nickname);
                    jSONObject.put("locationUpdateTime", next.locationUpdateTime);
                    jSONObject.put("joinTime", next.joinTime);
                    jSONObject.put("online", next.online);
                    jSONObject.put("source", next.source);
                    jSONObject.put("isSelf", next.isSelf);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("lat", next.lat);
                    jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, next.lon);
                    jSONObject.put("locInfo", jSONObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }

    public static JSONObject a(GroupInfo groupInfo) {
        if (groupInfo == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("supNum", groupInfo.getSuperGroupMemberCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
