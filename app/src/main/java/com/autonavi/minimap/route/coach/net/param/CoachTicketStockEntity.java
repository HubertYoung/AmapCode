package com.autonavi.minimap.route.coach.net.param;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.minimap.route.coach.model.CoachPlanItem;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"cpSource", "busNoId"}, url = "ws/boss/order/bus/ticket_stock?")
public class CoachTicketStockEntity implements ParamEntity {
    public String arriveCity;
    public String arriveStation;
    public String busNoId;
    public String cpSource;
    public String departCity;
    public String departStation;
    public String departTime;
    public int ticketPrice;

    public CoachTicketStockEntity(CoachPlanItem coachPlanItem, String str, String str2, String str3) {
        if (coachPlanItem != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(coachPlanItem.dateSource);
            this.cpSource = sb.toString();
            this.busNoId = coachPlanItem.busNumber;
            this.ticketPrice = (int) coachPlanItem.fullPrice;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(Token.SEPARATOR);
            sb2.append(coachPlanItem.departTime);
            this.departTime = sb2.toString();
            this.departStation = coachPlanItem.depName;
            this.departCity = coachPlanItem.depCity;
            this.arriveStation = coachPlanItem.arrName;
            this.arriveCity = coachPlanItem.arrCity;
        }
    }
}
