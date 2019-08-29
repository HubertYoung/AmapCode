package com.autonavi.minimap.basemap.favorites.data;

import android.text.TextUtils;
import com.autonavi.common.model.POI;

public class RouteItem extends ItemKey {
    public static final int BUSLINE_TYPE = 0;
    public static final int BUS_PATH_TYPE = 2;
    public static final int CAR_PATH_TYPE = 1;
    public static final int EXTBUS_PATH_TYPE = 4;
    public static final int FOOT_PATH_TYPE = 3;
    public static final String ITEM_TAG = "item";
    public static final String MEHOD = "method";
    public static final String NOTE_TAG = "route_alias";
    public static final String ROUTE_DATA = "route_data";
    public static final String ROUTE_LENGTH = "route_len";
    public static final String ROUTE_NAME = "route_name";
    public static final String ROUTE_TYPE = "route_type";
    public static final String VERSON = "version";
    private static final long serialVersionUID = 6156269996960931875L;
    public int endX;
    public int endY;
    public POI fromPoi;
    public boolean hasMidPoi;
    public String method;
    public POI midPoi;
    public Object routeData;
    public int routeLength;
    public String routeName;
    public String routeNote;
    public int routeType;
    public String sourceKey;
    public int startX;
    public int startY;
    public POI toPoi;
    public String version;

    public RouteItem() {
        this.routeType = -1;
        this.version = "1.0.0";
        this.midPoi = null;
        this.hasMidPoi = false;
        this.routeNote = "";
        this.sourceKey = null;
        this.type = 1;
    }

    public void generateKeyId() {
        if (this.routeType == 0) {
            asy asy = (asy) a.a.a(asy.class);
            if (asy != null) {
                this.id = createMD5(asy.c().b(this.routeData));
            }
        } else if (this.routeType == 1) {
            dia dia = (dia) this.routeData;
            StringBuilder sb = new StringBuilder();
            sb.append(this.startX);
            sb.append("+");
            sb.append(this.startY);
            sb.append("+");
            sb.append(this.endX);
            sb.append("+");
            sb.append(this.endY);
            sb.append("+");
            if (this.hasMidPoi && this.midPoi != null) {
                sb.append(this.midPoi.getPoint().x);
                sb.append("+");
                sb.append(this.midPoi.getPoint().y);
                sb.append("+");
            }
            sb.append(this.routeType);
            sb.append("+");
            sb.append(this.method);
            sb.append("+");
            sb.append(dia.getPathStrategy());
            this.id = createMD5(sb.toString());
        } else if (this.routeType == 2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.startX);
            sb2.append("+");
            String sb3 = sb2.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb3);
            sb4.append(this.startY);
            sb4.append("+");
            String sb5 = sb4.toString();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(sb5);
            sb6.append(this.endX);
            sb6.append("+");
            String sb7 = sb6.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(sb7);
            sb8.append(this.endY);
            sb8.append("+");
            String sb9 = sb8.toString();
            StringBuilder sb10 = new StringBuilder();
            sb10.append(sb9);
            sb10.append(this.routeType);
            sb10.append("+");
            String sb11 = sb10.toString();
            StringBuilder sb12 = new StringBuilder();
            sb12.append(sb11);
            sb12.append(this.routeLength);
            this.id = createMD5(sb12.toString());
        } else if (this.routeType == 3) {
            StringBuilder sb13 = new StringBuilder();
            sb13.append(this.startX);
            sb13.append("+");
            String sb14 = sb13.toString();
            StringBuilder sb15 = new StringBuilder();
            sb15.append(sb14);
            sb15.append(this.startY);
            sb15.append("+");
            String sb16 = sb15.toString();
            StringBuilder sb17 = new StringBuilder();
            sb17.append(sb16);
            sb17.append(this.endX);
            sb17.append("+");
            String sb18 = sb17.toString();
            StringBuilder sb19 = new StringBuilder();
            sb19.append(sb18);
            sb19.append(this.endY);
            sb19.append("+");
            String sb20 = sb19.toString();
            StringBuilder sb21 = new StringBuilder();
            sb21.append(sb20);
            sb21.append(this.routeType);
            sb21.append("+");
            String sb22 = sb21.toString();
            StringBuilder sb23 = new StringBuilder();
            sb23.append(sb22);
            sb23.append(this.method);
            this.id = createMD5(sb23.toString());
        } else {
            if (this.routeType == 4) {
                StringBuilder sb24 = new StringBuilder();
                sb24.append(this.startX);
                sb24.append("+");
                String sb25 = sb24.toString();
                StringBuilder sb26 = new StringBuilder();
                sb26.append(sb25);
                sb26.append(this.startY);
                sb26.append("+");
                String sb27 = sb26.toString();
                StringBuilder sb28 = new StringBuilder();
                sb28.append(sb27);
                sb28.append(this.endX);
                sb28.append("+");
                String sb29 = sb28.toString();
                StringBuilder sb30 = new StringBuilder();
                sb30.append(sb29);
                sb30.append(this.endY);
                sb30.append("+");
                String sb31 = sb30.toString();
                StringBuilder sb32 = new StringBuilder();
                sb32.append(sb31);
                sb32.append(this.routeType);
                sb32.append("+");
                String sb33 = sb32.toString();
                StringBuilder sb34 = new StringBuilder();
                sb34.append(sb33);
                sb34.append(this.method);
                this.id = createMD5(sb34.toString());
            }
        }
    }

    public String getSourceKey() {
        if (!TextUtils.isEmpty(this.sourceKey)) {
            return this.sourceKey;
        }
        if (this.routeType == 1) {
            dia dia = (dia) this.routeData;
            StringBuilder sb = new StringBuilder();
            sb.append(this.startX);
            sb.append("+");
            sb.append(this.startY);
            sb.append("+");
            sb.append(this.endX);
            sb.append("+");
            sb.append(this.endY);
            sb.append("+");
            if (this.hasMidPoi && this.midPoi != null) {
                sb.append(this.midPoi.getPoint().x);
                sb.append("+");
                sb.append(this.midPoi.getPoint().y);
                sb.append("+");
            }
            sb.append(this.routeType);
            sb.append("+");
            sb.append(this.method);
            sb.append("+");
            sb.append(dia.getPathStrategy());
            this.sourceKey = sb.toString();
        } else if (this.routeType == 2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.startX);
            sb2.append("+");
            String sb3 = sb2.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb3);
            sb4.append(this.startY);
            sb4.append("+");
            String sb5 = sb4.toString();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(sb5);
            sb6.append(this.endX);
            sb6.append("+");
            String sb7 = sb6.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(sb7);
            sb8.append(this.endY);
            sb8.append("+");
            String sb9 = sb8.toString();
            StringBuilder sb10 = new StringBuilder();
            sb10.append(sb9);
            sb10.append(this.routeType);
            sb10.append("+");
            String sb11 = sb10.toString();
            StringBuilder sb12 = new StringBuilder();
            sb12.append(sb11);
            sb12.append(this.routeLength);
            this.sourceKey = sb12.toString();
        } else if (this.routeType == 3) {
            StringBuilder sb13 = new StringBuilder();
            sb13.append(this.startX);
            sb13.append("+");
            String sb14 = sb13.toString();
            StringBuilder sb15 = new StringBuilder();
            sb15.append(sb14);
            sb15.append(this.startY);
            sb15.append("+");
            String sb16 = sb15.toString();
            StringBuilder sb17 = new StringBuilder();
            sb17.append(sb16);
            sb17.append(this.endX);
            sb17.append("+");
            String sb18 = sb17.toString();
            StringBuilder sb19 = new StringBuilder();
            sb19.append(sb18);
            sb19.append(this.endY);
            sb19.append("+");
            String sb20 = sb19.toString();
            StringBuilder sb21 = new StringBuilder();
            sb21.append(sb20);
            sb21.append(this.routeType);
            sb21.append("+");
            String sb22 = sb21.toString();
            StringBuilder sb23 = new StringBuilder();
            sb23.append(sb22);
            sb23.append(this.method);
            this.sourceKey = sb23.toString();
        }
        return this.sourceKey;
    }
}
