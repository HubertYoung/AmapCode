package com.autonavi.minimap.route.bus.model;

import android.content.res.Resources;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.BusEta;
import com.autonavi.bundle.routecommon.entity.BusPathSection.IrregularTime;
import com.autonavi.minimap.R;
import java.io.Serializable;
import java.text.DecimalFormat;

public class Bus implements Serializable {
    private static final long serialVersionUID = -1;
    public int air;
    public String areacode;
    public int auto;
    public String basic_price;
    public String basic_price_air;
    public String color;
    public String company;
    public int[] coordX;
    public int[] coordY;
    public String description = "";
    public a emergency;
    public String endName;
    public int endTime;
    public BusEta eta;
    public String frontNameSpell;
    public int icCard;
    public String id;
    public String interval = "";
    public IrregularTime irregulartime;
    public boolean isRealTime;
    public String key_name;
    public int length;
    public String name;
    public String otherLen;
    public int point_num;
    public String returnId;
    public String startName;
    public int startTime;
    public int stationEndTime;
    public String[] stationIds;
    public int stationStartTime;
    public int[] stationX;
    public int[] stationY;
    public int station_num;
    public int[] stationdirection;
    public String[] stationpoiid1;
    public String[] stationpoiid2;
    public String[] stations;
    public int[] stationstatus;
    public int status = 1;
    public BusSubwayInfo subwayInfo = new BusSubwayInfo();
    public String terminalNameSpell;
    public String total_price;
    public String total_price_air;
    public int type;

    public static class a {
        public int a;
        public String b;
    }

    public String getBusRouteName() {
        String str = this.name;
        int lastIndexOf = str.lastIndexOf("(");
        if (lastIndexOf >= 0) {
            str = str.substring(0, lastIndexOf);
        }
        StringBuilder sb = new StringBuilder("(");
        sb.append(this.startName);
        sb.append(" → ");
        sb.append(this.endName);
        sb.append(")");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(sb2);
        return sb3.toString();
    }

    public String getTicketDesc() {
        String str = "";
        try {
            Resources resources = AMapAppGlobal.getApplication().getResources();
            if (!TextUtils.isEmpty(this.total_price_air)) {
                if (!"0".equals(this.total_price_air)) {
                    if (TextUtils.isEmpty(this.basic_price)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(resources.getString(R.string.cost_full_trip));
                        sb.append(this.total_price_air);
                        sb.append(resources.getString(R.string.rmb));
                        str = sb.toString();
                        return str;
                    } else if (this.basic_price.equals(this.total_price_air)) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(resources.getString(R.string.cost_ticket));
                        sb2.append(":");
                        sb2.append(this.basic_price);
                        sb2.append(resources.getString(R.string.rmb));
                        return sb2.toString();
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append(resources.getString(R.string.cost_ticket));
                        sb3.append(":");
                        sb3.append(this.basic_price);
                        sb3.append("-");
                        sb3.append(this.total_price_air);
                        sb3.append(resources.getString(R.string.rmb));
                        return sb3.toString();
                    }
                }
            }
            if (!TextUtils.isEmpty(this.total_price)) {
                if (!"0".equals(this.total_price)) {
                    if (!TextUtils.isEmpty(this.basic_price)) {
                        if (!"0".equals(this.basic_price)) {
                            if (!TextUtils.isEmpty(this.basic_price) && !TextUtils.isEmpty(this.total_price)) {
                                if (this.basic_price.equals(this.total_price)) {
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append(str);
                                    sb4.append(resources.getString(R.string.cost_ticket));
                                    sb4.append(":");
                                    sb4.append(this.basic_price);
                                    sb4.append(resources.getString(R.string.rmb));
                                    return sb4.toString();
                                }
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append(str);
                                sb5.append(resources.getString(R.string.cost_ticket));
                                sb5.append(":");
                                sb5.append(this.basic_price);
                                sb5.append("-");
                                sb5.append(this.total_price);
                                sb5.append(resources.getString(R.string.rmb));
                                return sb5.toString();
                            }
                            return str;
                        }
                    }
                    if (!TextUtils.isEmpty(this.total_price) && !"0".equals(this.total_price)) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(str);
                        sb6.append(resources.getString(R.string.cost_full_trip));
                        sb6.append(this.total_price);
                        sb6.append(resources.getString(R.string.rmb));
                        return sb6.toString();
                    }
                    return str;
                }
            }
            if (!TextUtils.isEmpty(this.basic_price) && !"0".equals(this.basic_price)) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(str);
                sb7.append(resources.getString(R.string.cost_starting_price));
                sb7.append(this.basic_price);
                sb7.append(resources.getString(R.string.rmb));
                return sb7.toString();
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return str;
    }

    public Bus copyObject() {
        Bus bus = new Bus();
        bus.air = this.air;
        bus.areacode = this.areacode;
        bus.auto = this.auto;
        bus.basic_price = this.basic_price;
        bus.company = this.company;
        bus.coordX = this.coordX;
        bus.coordY = this.coordY;
        bus.description = this.description;
        bus.endTime = this.endTime;
        bus.endName = this.endName;
        bus.frontNameSpell = this.frontNameSpell;
        bus.icCard = this.icCard;
        bus.id = this.id;
        bus.interval = this.interval;
        bus.isRealTime = this.isRealTime;
        bus.key_name = this.key_name;
        bus.length = this.length;
        bus.otherLen = getAdecimalLen(this.otherLen);
        bus.name = this.name;
        bus.point_num = this.point_num;
        bus.startTime = this.startTime;
        bus.startName = this.startName;
        bus.station_num = this.station_num;
        bus.stationIds = this.stationIds;
        bus.stations = this.stations;
        bus.stationX = this.stationX;
        bus.stationY = this.stationY;
        bus.status = this.status;
        bus.terminalNameSpell = this.terminalNameSpell;
        bus.total_price = this.total_price;
        bus.total_price_air = this.total_price_air;
        bus.type = this.type;
        bus.subwayInfo = this.subwayInfo;
        bus.stationstatus = this.stationstatus;
        bus.stationpoiid1 = this.stationpoiid1;
        bus.stationpoiid2 = this.stationpoiid2;
        bus.color = this.color;
        bus.irregulartime = this.irregulartime;
        bus.stationdirection = this.stationdirection;
        bus.emergency = this.emergency;
        bus.returnId = this.returnId;
        bus.stationStartTime = this.stationStartTime;
        bus.stationEndTime = this.stationEndTime;
        return bus;
    }

    public String getAdecimalLen(String str) {
        if (TextUtils.isEmpty(str) || "0.0".equals(str)) {
            return "0.0";
        }
        return new DecimalFormat("#.0").format(Double.parseDouble(str));
    }

    public String getIdByName(String str) {
        String str2 = "";
        if (this.stations == null || this.stationIds == null || this.stations.length != this.stationIds.length || TextUtils.isEmpty(str)) {
            return str2;
        }
        int i = 0;
        while (true) {
            if (i >= this.stations.length) {
                break;
            } else if (str.equalsIgnoreCase(this.stations[i])) {
                str2 = this.stationIds[i];
                break;
            } else {
                i++;
            }
        }
        return str2;
    }

    public int getIndexByName(String str) {
        if (this.stations == null || TextUtils.isEmpty(str)) {
            return 0;
        }
        int i = 0;
        while (true) {
            if (i >= this.stations.length) {
                i = 0;
                break;
            } else if (str.equalsIgnoreCase(this.stations[i])) {
                break;
            } else {
                i++;
            }
        }
        return i;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:23|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r0.startTime = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r0.endTime = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r0.stationStartTime = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        r0.stationEndTime = -1;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0077 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x008d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00a5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x00bd */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.minimap.route.bus.model.Bus parse(org.json.JSONObject r11) {
        /*
            com.autonavi.minimap.route.bus.model.Bus r0 = new com.autonavi.minimap.route.bus.model.Bus
            r0.<init>()
            java.lang.String r1 = "id"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x0015
            java.lang.String r1 = "id"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.id = r1     // Catch:{ Exception -> 0x0344 }
        L_0x0015:
            java.lang.String r1 = "type"
            int r1 = r11.optInt(r1)     // Catch:{ Exception -> 0x0344 }
            r0.type = r1     // Catch:{ Exception -> 0x0344 }
            java.lang.String r1 = "name"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x002e
            java.lang.String r1 = "name"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.name = r1     // Catch:{ Exception -> 0x0344 }
        L_0x002e:
            java.lang.String r1 = "front_name"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x003e
            java.lang.String r1 = "front_name"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.startName = r1     // Catch:{ Exception -> 0x0344 }
        L_0x003e:
            java.lang.String r1 = "terminal_name"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x0050
            java.lang.String r1 = "terminal_name"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.endName = r1     // Catch:{ Exception -> 0x0344 }
        L_0x0050:
            java.lang.String r1 = "color"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x0060
            java.lang.String r1 = "color"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.color = r1     // Catch:{ Exception -> 0x0344 }
        L_0x0060:
            r1 = -1
            java.lang.String r2 = "start_time"
            boolean r2 = r11.has(r2)     // Catch:{ JSONException -> 0x0077 }
            if (r2 == 0) goto L_0x0074
            java.lang.String r2 = "start_time"
            int r2 = r11.getInt(r2)     // Catch:{ JSONException -> 0x0077 }
            r0.startTime = r2     // Catch:{ JSONException -> 0x0077 }
            goto L_0x0079
        L_0x0074:
            r0.startTime = r1     // Catch:{ JSONException -> 0x0077 }
            goto L_0x0079
        L_0x0077:
            r0.startTime = r1     // Catch:{ Exception -> 0x0344 }
        L_0x0079:
            java.lang.String r2 = "end_time"
            boolean r2 = r11.has(r2)     // Catch:{ JSONException -> 0x008d }
            if (r2 == 0) goto L_0x008a
            java.lang.String r2 = "end_time"
            int r2 = r11.getInt(r2)     // Catch:{ JSONException -> 0x008d }
            r0.endTime = r2     // Catch:{ JSONException -> 0x008d }
            goto L_0x008f
        L_0x008a:
            r0.endTime = r1     // Catch:{ JSONException -> 0x008d }
            goto L_0x008f
        L_0x008d:
            r0.endTime = r1     // Catch:{ Exception -> 0x0344 }
        L_0x008f:
            java.lang.String r2 = "stationStartTime"
            boolean r2 = r11.has(r2)     // Catch:{ JSONException -> 0x00a5 }
            if (r2 == 0) goto L_0x00a2
            java.lang.String r2 = "stationStartTime"
            int r2 = r11.getInt(r2)     // Catch:{ JSONException -> 0x00a5 }
            r0.stationStartTime = r2     // Catch:{ JSONException -> 0x00a5 }
            goto L_0x00a7
        L_0x00a2:
            r0.stationStartTime = r1     // Catch:{ JSONException -> 0x00a5 }
            goto L_0x00a7
        L_0x00a5:
            r0.stationStartTime = r1     // Catch:{ Exception -> 0x0344 }
        L_0x00a7:
            java.lang.String r2 = "stationEndTime"
            boolean r2 = r11.has(r2)     // Catch:{ JSONException -> 0x00bd }
            if (r2 == 0) goto L_0x00ba
            java.lang.String r2 = "stationEndTime"
            int r2 = r11.getInt(r2)     // Catch:{ JSONException -> 0x00bd }
            r0.stationEndTime = r2     // Catch:{ JSONException -> 0x00bd }
            goto L_0x00bf
        L_0x00ba:
            r0.stationEndTime = r1     // Catch:{ JSONException -> 0x00bd }
            goto L_0x00bf
        L_0x00bd:
            r0.stationEndTime = r1     // Catch:{ Exception -> 0x0344 }
        L_0x00bf:
            java.lang.String r1 = "key_name"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x00cf
            java.lang.String r1 = "key_name"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.key_name = r1     // Catch:{ Exception -> 0x0344 }
        L_0x00cf:
            java.lang.String r1 = "status"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            r2 = 1
            if (r1 == 0) goto L_0x00e2
            java.lang.String r1 = "status"
            int r1 = r11.optInt(r1, r2)     // Catch:{ Exception -> 0x0344 }
            r0.status = r1     // Catch:{ Exception -> 0x0344 }
        L_0x00e2:
            java.lang.String r1 = "description"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x00f2
            java.lang.String r1 = "description"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.description = r1     // Catch:{ Exception -> 0x0344 }
        L_0x00f2:
            java.lang.String r1 = "emergency"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x0118
            java.lang.String r1 = "emergency"
            org.json.JSONObject r1 = r11.getJSONObject(r1)     // Catch:{ Exception -> 0x0344 }
            com.autonavi.minimap.route.bus.model.Bus$a r3 = new com.autonavi.minimap.route.bus.model.Bus$a     // Catch:{ Exception -> 0x0344 }
            r3.<init>()     // Catch:{ Exception -> 0x0344 }
            java.lang.String r4 = "state"
            int r4 = r1.optInt(r4)     // Catch:{ Exception -> 0x0344 }
            r3.a = r4     // Catch:{ Exception -> 0x0344 }
            java.lang.String r4 = "description"
            java.lang.String r1 = r1.optString(r4)     // Catch:{ Exception -> 0x0344 }
            r3.b = r1     // Catch:{ Exception -> 0x0344 }
            r0.emergency = r3     // Catch:{ Exception -> 0x0344 }
        L_0x0118:
            java.lang.String r1 = "interval"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x012c
            java.lang.String r1 = "interval"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ Exception -> 0x0344 }
            java.lang.String r1 = com.autonavi.minimap.route.bus.inter.impl.BusLineSearchResultImpl.getInterval4String(r1)     // Catch:{ Exception -> 0x0344 }
            r0.interval = r1     // Catch:{ Exception -> 0x0344 }
        L_0x012c:
            java.lang.String r1 = "direc"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x013c
            java.lang.String r1 = "direc"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.returnId = r1     // Catch:{ Exception -> 0x0344 }
        L_0x013c:
            java.lang.String r1 = "basic_price"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x014c
            java.lang.String r1 = "basic_price"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.basic_price = r1     // Catch:{ Exception -> 0x0344 }
        L_0x014c:
            java.lang.String r1 = "total_price"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x015e
            java.lang.String r1 = "total_price"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.total_price = r1     // Catch:{ Exception -> 0x0344 }
        L_0x015e:
            java.lang.String r1 = "basic_price_air"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x016e
            java.lang.String r1 = "basic_price_air"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.basic_price_air = r1     // Catch:{ Exception -> 0x0344 }
        L_0x016e:
            java.lang.String r1 = "total_price_air"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x0180
            java.lang.String r1 = "total_price_air"
            java.lang.String r1 = r11.optString(r1)     // Catch:{ Exception -> 0x0344 }
            r0.total_price_air = r1     // Catch:{ Exception -> 0x0344 }
        L_0x0180:
            java.lang.String r1 = "length"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            r3 = 0
            if (r1 == 0) goto L_0x01ad
            java.lang.String r1 = "length"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x01a7
            java.lang.String r4 = r1.trim()     // Catch:{ Exception -> 0x0344 }
            java.lang.String r5 = ""
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0344 }
            if (r4 != 0) goto L_0x01a7
            double r4 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x0344 }
            int r4 = (int) r4     // Catch:{ Exception -> 0x0344 }
            r0.length = r4     // Catch:{ Exception -> 0x0344 }
            r0.otherLen = r1     // Catch:{ Exception -> 0x0344 }
            goto L_0x01ad
        L_0x01a7:
            r0.length = r3     // Catch:{ Exception -> 0x0344 }
            java.lang.String r1 = "0.0"
            r0.otherLen = r1     // Catch:{ Exception -> 0x0344 }
        L_0x01ad:
            java.lang.String r1 = "is_realtime"
            int r1 = r11.optInt(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 != r2) goto L_0x01b7
            r1 = 1
            goto L_0x01b8
        L_0x01b7:
            r1 = 0
        L_0x01b8:
            r0.isRealTime = r1     // Catch:{ Exception -> 0x0344 }
            java.lang.String r1 = "irregular_time"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x01ce
            java.lang.String r1 = "irregular_time"
            java.lang.String r1 = defpackage.axr.e(r11, r1)     // Catch:{ Exception -> 0x0344 }
            com.autonavi.bundle.routecommon.entity.BusPathSection$IrregularTime r1 = com.autonavi.minimap.route.bus.inter.impl.BusLineSearchResultImpl.getIrregularTime(r1)     // Catch:{ Exception -> 0x0344 }
            r0.irregulartime = r1     // Catch:{ Exception -> 0x0344 }
        L_0x01ce:
            java.lang.String r1 = "xs"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x024e
            java.lang.String r1 = "ys"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x024e
            java.lang.String r1 = "xs"
            java.lang.String r1 = r11.getString(r1)     // Catch:{ Exception -> 0x0344 }
            java.lang.String r4 = "ys"
            java.lang.String r4 = r11.getString(r4)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x024e
            java.lang.String r5 = r1.trim()     // Catch:{ Exception -> 0x0344 }
            java.lang.String r6 = ""
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x0344 }
            if (r5 != 0) goto L_0x024e
            if (r4 == 0) goto L_0x024e
            java.lang.String r5 = r4.trim()     // Catch:{ Exception -> 0x0344 }
            java.lang.String r6 = ""
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x0344 }
            if (r5 != 0) goto L_0x024e
            java.lang.String r5 = ","
            java.lang.String[] r1 = r1.split(r5)     // Catch:{ Exception -> 0x0344 }
            java.lang.String r5 = ","
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ Exception -> 0x0344 }
            int r5 = r1.length     // Catch:{ Exception -> 0x0344 }
            int r6 = r1.length     // Catch:{ Exception -> 0x0344 }
            int[] r6 = new int[r6]     // Catch:{ Exception -> 0x0344 }
            r0.coordX = r6     // Catch:{ Exception -> 0x0344 }
            int r6 = r1.length     // Catch:{ Exception -> 0x0344 }
            int[] r6 = new int[r6]     // Catch:{ Exception -> 0x0344 }
            r0.coordY = r6     // Catch:{ Exception -> 0x0344 }
            r6 = 0
        L_0x0222:
            if (r6 >= r5) goto L_0x024e
            int r7 = r4.length     // Catch:{ Exception -> 0x0344 }
            if (r6 >= r7) goto L_0x024b
            r7 = r1[r6]     // Catch:{ Exception -> 0x0344 }
            java.lang.Double r7 = java.lang.Double.valueOf(r7)     // Catch:{ Exception -> 0x0344 }
            double r7 = r7.doubleValue()     // Catch:{ Exception -> 0x0344 }
            r9 = r4[r6]     // Catch:{ Exception -> 0x0344 }
            java.lang.Double r9 = java.lang.Double.valueOf(r9)     // Catch:{ Exception -> 0x0344 }
            double r9 = r9.doubleValue()     // Catch:{ Exception -> 0x0344 }
            android.graphics.Point r7 = defpackage.cfg.a(r9, r7)     // Catch:{ Exception -> 0x0344 }
            int[] r8 = r0.coordX     // Catch:{ Exception -> 0x0344 }
            int r9 = r7.x     // Catch:{ Exception -> 0x0344 }
            r8[r6] = r9     // Catch:{ Exception -> 0x0344 }
            int[] r8 = r0.coordY     // Catch:{ Exception -> 0x0344 }
            int r7 = r7.y     // Catch:{ Exception -> 0x0344 }
            r8[r6] = r7     // Catch:{ Exception -> 0x0344 }
        L_0x024b:
            int r6 = r6 + 1
            goto L_0x0222
        L_0x024e:
            java.lang.String r1 = "stations"
            boolean r1 = r11.has(r1)     // Catch:{ Exception -> 0x0344 }
            if (r1 == 0) goto L_0x0343
            java.lang.String r1 = "stations"
            org.json.JSONArray r11 = r11.getJSONArray(r1)     // Catch:{ Exception -> 0x0344 }
            if (r11 == 0) goto L_0x0343
            int r1 = r11.length()     // Catch:{ Exception -> 0x0344 }
            java.lang.String[] r4 = new java.lang.String[r1]     // Catch:{ Exception -> 0x0344 }
            r0.stations = r4     // Catch:{ Exception -> 0x0344 }
            int[] r4 = new int[r1]     // Catch:{ Exception -> 0x0344 }
            r0.stationX = r4     // Catch:{ Exception -> 0x0344 }
            int[] r4 = new int[r1]     // Catch:{ Exception -> 0x0344 }
            r0.stationY = r4     // Catch:{ Exception -> 0x0344 }
            java.lang.String[] r4 = new java.lang.String[r1]     // Catch:{ Exception -> 0x0344 }
            r0.stationIds = r4     // Catch:{ Exception -> 0x0344 }
            int[] r4 = new int[r1]     // Catch:{ Exception -> 0x0344 }
            r0.stationstatus = r4     // Catch:{ Exception -> 0x0344 }
            java.lang.String[] r4 = new java.lang.String[r1]     // Catch:{ Exception -> 0x0344 }
            r0.stationpoiid1 = r4     // Catch:{ Exception -> 0x0344 }
            java.lang.String[] r4 = new java.lang.String[r1]     // Catch:{ Exception -> 0x0344 }
            r0.stationpoiid2 = r4     // Catch:{ Exception -> 0x0344 }
            r4 = 0
        L_0x0281:
            if (r4 >= r1) goto L_0x0343
            org.json.JSONObject r5 = r11.getJSONObject(r4)     // Catch:{ Exception -> 0x0344 }
            java.lang.String[] r6 = r0.stations     // Catch:{ Exception -> 0x0344 }
            java.lang.String r7 = "name"
            java.lang.String r7 = r5.getString(r7)     // Catch:{ Exception -> 0x0344 }
            r6[r4] = r7     // Catch:{ Exception -> 0x0344 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0344 }
            r6.<init>()     // Catch:{ Exception -> 0x0344 }
            java.lang.String r7 = "code"
            long r7 = r5.getLong(r7)     // Catch:{ Exception -> 0x0344 }
            r6.append(r7)     // Catch:{ Exception -> 0x0344 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0344 }
            r0.areacode = r6     // Catch:{ Exception -> 0x0344 }
            java.lang.String r6 = "xy_coords"
            java.lang.String r6 = r5.getString(r6)     // Catch:{ Exception -> 0x0344 }
            java.lang.String r7 = ";"
            java.lang.String[] r6 = r6.split(r7)     // Catch:{ Exception -> 0x0344 }
            int r7 = r6.length     // Catch:{ Exception -> 0x0344 }
            r8 = 2
            if (r7 != r8) goto L_0x02da
            r7 = r6[r3]     // Catch:{ Exception -> 0x0344 }
            java.lang.Double r7 = java.lang.Double.valueOf(r7)     // Catch:{ Exception -> 0x0344 }
            double r7 = r7.doubleValue()     // Catch:{ Exception -> 0x0344 }
            r6 = r6[r2]     // Catch:{ Exception -> 0x0344 }
            java.lang.Double r6 = java.lang.Double.valueOf(r6)     // Catch:{ Exception -> 0x0344 }
            double r9 = r6.doubleValue()     // Catch:{ Exception -> 0x0344 }
            android.graphics.Point r6 = defpackage.cfg.a(r9, r7)     // Catch:{ Exception -> 0x0344 }
            int[] r7 = r0.stationX     // Catch:{ Exception -> 0x0344 }
            int r8 = r6.x     // Catch:{ Exception -> 0x0344 }
            r7[r4] = r8     // Catch:{ Exception -> 0x0344 }
            int[] r7 = r0.stationY     // Catch:{ Exception -> 0x0344 }
            int r6 = r6.y     // Catch:{ Exception -> 0x0344 }
            r7[r4] = r6     // Catch:{ Exception -> 0x0344 }
        L_0x02da:
            java.lang.String r6 = "name"
            java.lang.String r6 = r5.getString(r6)     // Catch:{ Exception -> 0x0344 }
            java.lang.String[] r7 = r0.stations     // Catch:{ Exception -> 0x0344 }
            r7[r4] = r6     // Catch:{ Exception -> 0x0344 }
            java.lang.String r7 = "station_id"
            java.lang.String r7 = r5.getString(r7)     // Catch:{ Exception -> 0x0344 }
            java.lang.String[] r8 = r0.stationIds     // Catch:{ Exception -> 0x0344 }
            r8[r4] = r7     // Catch:{ Exception -> 0x0344 }
            java.lang.String r8 = "subways"
            boolean r8 = r5.has(r8)     // Catch:{ Exception -> 0x0344 }
            if (r8 == 0) goto L_0x0302
            java.lang.String r8 = "subways"
            org.json.JSONArray r8 = r5.getJSONArray(r8)     // Catch:{ Exception -> 0x0344 }
            com.autonavi.minimap.route.bus.inter.impl.BusLineSearchResultImpl.parseSubways(r0, r8, r6, r7)     // Catch:{ Exception -> 0x0344 }
        L_0x0302:
            java.lang.String r6 = "status"
            boolean r6 = r5.has(r6)     // Catch:{ Exception -> 0x0344 }
            if (r6 == 0) goto L_0x0317
            int[] r6 = r0.stationstatus     // Catch:{ Exception -> 0x0344 }
            java.lang.String r7 = "status"
            int r7 = r5.getInt(r7)     // Catch:{ Exception -> 0x0344 }
            r6[r4] = r7     // Catch:{ Exception -> 0x0344 }
            goto L_0x031b
        L_0x0317:
            int[] r6 = r0.stationstatus     // Catch:{ Exception -> 0x0344 }
            r6[r4] = r2     // Catch:{ Exception -> 0x0344 }
        L_0x031b:
            java.lang.String r6 = "poiid1"
            boolean r6 = r5.has(r6)     // Catch:{ Exception -> 0x0344 }
            if (r6 == 0) goto L_0x032d
            java.lang.String[] r6 = r0.stationpoiid1     // Catch:{ Exception -> 0x0344 }
            java.lang.String r7 = "poiid1"
            java.lang.String r7 = r5.getString(r7)     // Catch:{ Exception -> 0x0344 }
            r6[r4] = r7     // Catch:{ Exception -> 0x0344 }
        L_0x032d:
            java.lang.String r6 = "poiid2"
            boolean r6 = r5.has(r6)     // Catch:{ Exception -> 0x0344 }
            if (r6 == 0) goto L_0x033f
            java.lang.String[] r6 = r0.stationpoiid2     // Catch:{ Exception -> 0x0344 }
            java.lang.String r7 = "poiid2"
            java.lang.String r5 = r5.getString(r7)     // Catch:{ Exception -> 0x0344 }
            r6[r4] = r5     // Catch:{ Exception -> 0x0344 }
        L_0x033f:
            int r4 = r4 + 1
            goto L_0x0281
        L_0x0343:
            return r0
        L_0x0344:
            r11 = move-exception
            defpackage.kf.a(r11)
            r11 = 0
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.model.Bus.parse(org.json.JSONObject):com.autonavi.minimap.route.bus.model.Bus");
    }
}
