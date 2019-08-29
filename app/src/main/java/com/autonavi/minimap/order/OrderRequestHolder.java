package com.autonavi.minimap.order;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.order.param.BikeBalancePayRequest;
import com.autonavi.minimap.order.param.BikeCheckLockRequest;
import com.autonavi.minimap.order.param.BikeCheckOrderRequest;
import com.autonavi.minimap.order.param.BikeEndBillingRequest;
import com.autonavi.minimap.order.param.BikeOrderDetailRequest;
import com.autonavi.minimap.order.param.BikePayRequest;
import com.autonavi.minimap.order.param.BikeScanQrcodeRequest;
import com.autonavi.minimap.order.param.BusListRequest;
import com.autonavi.minimap.order.param.BusOrderSubmitRequest;
import com.autonavi.minimap.order.param.HotelDeleteRequest;
import com.autonavi.minimap.order.param.HotelListRequest;
import com.autonavi.minimap.order.param.OrderListRequest;
import com.autonavi.minimap.order.param.ScenicDeleteRequest;
import com.autonavi.minimap.order.param.ScenicListRequest;
import com.autonavi.minimap.order.param.TicketStockRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class OrderRequestHolder {
    private static volatile OrderRequestHolder instance;

    private OrderRequestHolder() {
    }

    public static OrderRequestHolder getInstance() {
        if (instance == null) {
            synchronized (OrderRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new OrderRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendBusList(BusListRequest busListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBusList(busListRequest, new dkn(), aosResponseCallback);
    }

    public void sendBusOrderSubmit(BusOrderSubmitRequest busOrderSubmitRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBusOrderSubmit(busOrderSubmitRequest, new dkn(), aosResponseCallback);
    }

    public void sendTicketStock(TicketStockRequest ticketStockRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTicketStock(ticketStockRequest, new dkn(), aosResponseCallback);
    }

    public void sendHotelDelete(HotelDeleteRequest hotelDeleteRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendHotelDelete(hotelDeleteRequest, new dkn(), aosResponseCallback);
    }

    public void sendHotelList(HotelListRequest hotelListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendHotelList(hotelListRequest, new dkn(), aosResponseCallback);
    }

    public void sendScenicDelete(ScenicDeleteRequest scenicDeleteRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendScenicDelete(scenicDeleteRequest, new dkn(), aosResponseCallback);
    }

    public void sendScenicList(ScenicListRequest scenicListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendScenicList(scenicListRequest, new dkn(), aosResponseCallback);
    }

    public void sendOrderList(OrderListRequest orderListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendOrderList(orderListRequest, new dkn(), aosResponseCallback);
    }

    public void sendBikeBalancePay(BikeBalancePayRequest bikeBalancePayRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBikeBalancePay(bikeBalancePayRequest, new dkn(), aosResponseCallback);
    }

    public void sendBikeCheckLock(BikeCheckLockRequest bikeCheckLockRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBikeCheckLock(bikeCheckLockRequest, new dkn(), aosResponseCallback);
    }

    public void sendBikeCheckOrder(BikeCheckOrderRequest bikeCheckOrderRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBikeCheckOrder(bikeCheckOrderRequest, new dkn(), aosResponseCallback);
    }

    public void sendBikeEndBilling(BikeEndBillingRequest bikeEndBillingRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBikeEndBilling(bikeEndBillingRequest, new dkn(), aosResponseCallback);
    }

    public void sendBikeOrderDetail(BikeOrderDetailRequest bikeOrderDetailRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBikeOrderDetail(bikeOrderDetailRequest, new dkn(), aosResponseCallback);
    }

    public void sendBikePay(BikePayRequest bikePayRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBikePay(bikePayRequest, new dkn(), aosResponseCallback);
    }

    public void sendBikeScanQrcode(BikeScanQrcodeRequest bikeScanQrcodeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBikeScanQrcode(bikeScanQrcodeRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendBusList(BusListRequest busListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            busListRequest.addHeaders(dkn.d);
            busListRequest.setTimeout(dkn.b);
            busListRequest.setRetryTimes(dkn.c);
        }
        busListRequest.setUrl(BusListRequest.a);
        busListRequest.addSignParam("channel");
        busListRequest.addReqParam("page_num", Integer.toString(busListRequest.b));
        busListRequest.addReqParam("page_size", Integer.toString(busListRequest.c));
        if (dkn != null) {
            in.a().a((AosRequest) busListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) busListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBusOrderSubmit(BusOrderSubmitRequest busOrderSubmitRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            busOrderSubmitRequest.addHeaders(dkn.d);
            busOrderSubmitRequest.setTimeout(dkn.b);
            busOrderSubmitRequest.setRetryTimes(dkn.c);
        }
        busOrderSubmitRequest.setUrl(BusOrderSubmitRequest.a);
        busOrderSubmitRequest.addSignParam("channel");
        busOrderSubmitRequest.addSignParam("cpSource");
        busOrderSubmitRequest.addSignParam("busNoId");
        busOrderSubmitRequest.addReqParam("cpSource", busOrderSubmitRequest.b);
        busOrderSubmitRequest.addReqParam("busNoId", busOrderSubmitRequest.c);
        busOrderSubmitRequest.addReqParam("departCity", busOrderSubmitRequest.d);
        busOrderSubmitRequest.addReqParam("departStation", busOrderSubmitRequest.e);
        busOrderSubmitRequest.addReqParam("departTime", busOrderSubmitRequest.f);
        busOrderSubmitRequest.addReqParam("ticketPrice", Integer.toString(busOrderSubmitRequest.g));
        busOrderSubmitRequest.addReqParam("arriveCity", busOrderSubmitRequest.h);
        busOrderSubmitRequest.addReqParam("arriveStation", busOrderSubmitRequest.i);
        if (dkn != null) {
            in.a().a((AosRequest) busOrderSubmitRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) busOrderSubmitRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTicketStock(TicketStockRequest ticketStockRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            ticketStockRequest.addHeaders(dkn.d);
            ticketStockRequest.setTimeout(dkn.b);
            ticketStockRequest.setRetryTimes(dkn.c);
        }
        ticketStockRequest.setUrl(TicketStockRequest.a);
        ticketStockRequest.addSignParam("cpSource");
        ticketStockRequest.addSignParam("busNoId");
        ticketStockRequest.addReqParam("cpSource", ticketStockRequest.b);
        ticketStockRequest.addReqParam("busNoId", ticketStockRequest.c);
        ticketStockRequest.addReqParam("departCity", ticketStockRequest.d);
        ticketStockRequest.addReqParam("departStation", ticketStockRequest.e);
        ticketStockRequest.addReqParam("departTime", ticketStockRequest.f);
        ticketStockRequest.addReqParam("ticketPrice", Integer.toString(ticketStockRequest.g));
        ticketStockRequest.addReqParam("arriveCity", ticketStockRequest.h);
        ticketStockRequest.addReqParam("arriveStation", ticketStockRequest.i);
        if (dkn != null) {
            in.a().a((AosRequest) ticketStockRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) ticketStockRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendHotelDelete(HotelDeleteRequest hotelDeleteRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            hotelDeleteRequest.addHeaders(dkn.d);
            hotelDeleteRequest.setTimeout(dkn.b);
            hotelDeleteRequest.setRetryTimes(dkn.c);
        }
        hotelDeleteRequest.setUrl(HotelDeleteRequest.a);
        hotelDeleteRequest.addSignParam("channel");
        hotelDeleteRequest.addSignParam("order_ids");
        hotelDeleteRequest.addReqParam("order_ids", hotelDeleteRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) hotelDeleteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) hotelDeleteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendHotelList(HotelListRequest hotelListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            hotelListRequest.addHeaders(dkn.d);
            hotelListRequest.setTimeout(dkn.b);
            hotelListRequest.setRetryTimes(dkn.c);
        }
        hotelListRequest.setUrl(HotelListRequest.a);
        hotelListRequest.addSignParam("channel");
        hotelListRequest.addReqParam("pagenum", Integer.toString(hotelListRequest.b));
        hotelListRequest.addReqParam("pagesize", Integer.toString(hotelListRequest.c));
        if (dkn != null) {
            in.a().a((AosRequest) hotelListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) hotelListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendScenicDelete(ScenicDeleteRequest scenicDeleteRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            scenicDeleteRequest.addHeaders(dkn.d);
            scenicDeleteRequest.setTimeout(dkn.b);
            scenicDeleteRequest.setRetryTimes(dkn.c);
        }
        scenicDeleteRequest.setUrl(ScenicDeleteRequest.a);
        scenicDeleteRequest.addSignParam("channel");
        scenicDeleteRequest.addSignParam("order_ids");
        scenicDeleteRequest.addReqParam("order_ids", scenicDeleteRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) scenicDeleteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) scenicDeleteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendScenicList(ScenicListRequest scenicListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            scenicListRequest.addHeaders(dkn.d);
            scenicListRequest.setTimeout(dkn.b);
            scenicListRequest.setRetryTimes(dkn.c);
        }
        scenicListRequest.setUrl(ScenicListRequest.a);
        scenicListRequest.addSignParam("channel");
        scenicListRequest.addReqParam("pagenum", Integer.toString(scenicListRequest.b));
        scenicListRequest.addReqParam("pagesize", Integer.toString(scenicListRequest.c));
        if (dkn != null) {
            in.a().a((AosRequest) scenicListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) scenicListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendOrderList(OrderListRequest orderListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            orderListRequest.addHeaders(dkn.d);
            orderListRequest.setTimeout(dkn.b);
            orderListRequest.setRetryTimes(dkn.c);
        }
        orderListRequest.setUrl(OrderListRequest.a);
        orderListRequest.addSignParam("channel");
        orderListRequest.addSignParam("tid");
        orderListRequest.addReqParam("tid", orderListRequest.b);
        orderListRequest.addReqParam("pagenum", Integer.toString(orderListRequest.c));
        orderListRequest.addReqParam("pagesize", Integer.toString(orderListRequest.d));
        if (dkn != null) {
            in.a().a((AosRequest) orderListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) orderListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBikeBalancePay(BikeBalancePayRequest bikeBalancePayRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bikeBalancePayRequest.addHeaders(dkn.d);
            bikeBalancePayRequest.setTimeout(dkn.b);
            bikeBalancePayRequest.setRetryTimes(dkn.c);
        }
        bikeBalancePayRequest.setUrl(BikeBalancePayRequest.a);
        bikeBalancePayRequest.addSignParam("channel");
        bikeBalancePayRequest.addSignParam("cpSource");
        bikeBalancePayRequest.addSignParam("orderId");
        bikeBalancePayRequest.addReqParam("cpSource", bikeBalancePayRequest.b);
        bikeBalancePayRequest.addReqParam("orderId", bikeBalancePayRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) bikeBalancePayRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bikeBalancePayRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBikeCheckLock(BikeCheckLockRequest bikeCheckLockRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bikeCheckLockRequest.addHeaders(dkn.d);
            bikeCheckLockRequest.setTimeout(dkn.b);
            bikeCheckLockRequest.setRetryTimes(dkn.c);
        }
        bikeCheckLockRequest.setUrl(BikeCheckLockRequest.a);
        bikeCheckLockRequest.addSignParam("channel");
        bikeCheckLockRequest.addSignParam("cpSource");
        bikeCheckLockRequest.addSignParam("bikeId");
        bikeCheckLockRequest.addReqParam("cpSource", bikeCheckLockRequest.b);
        bikeCheckLockRequest.addReqParam("orderId", bikeCheckLockRequest.c);
        bikeCheckLockRequest.addReqParam("bikeId", bikeCheckLockRequest.d);
        if (dkn != null) {
            in.a().a((AosRequest) bikeCheckLockRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bikeCheckLockRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBikeCheckOrder(BikeCheckOrderRequest bikeCheckOrderRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bikeCheckOrderRequest.addHeaders(dkn.d);
            bikeCheckOrderRequest.setTimeout(dkn.b);
            bikeCheckOrderRequest.setRetryTimes(dkn.c);
        }
        bikeCheckOrderRequest.setUrl(BikeCheckOrderRequest.a);
        bikeCheckOrderRequest.addSignParam("channel");
        if (dkn != null) {
            in.a().a((AosRequest) bikeCheckOrderRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bikeCheckOrderRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBikeEndBilling(BikeEndBillingRequest bikeEndBillingRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bikeEndBillingRequest.addHeaders(dkn.d);
            bikeEndBillingRequest.setTimeout(dkn.b);
            bikeEndBillingRequest.setRetryTimes(dkn.c);
        }
        bikeEndBillingRequest.setUrl(BikeEndBillingRequest.a);
        bikeEndBillingRequest.addSignParam("channel");
        bikeEndBillingRequest.addSignParam("cpSource");
        bikeEndBillingRequest.addSignParam("orderId");
        bikeEndBillingRequest.addReqParam("cpSource", bikeEndBillingRequest.b);
        bikeEndBillingRequest.addReqParam("orderId", bikeEndBillingRequest.c);
        bikeEndBillingRequest.addReqParam("latitude", bikeEndBillingRequest.d);
        bikeEndBillingRequest.addReqParam("longitude", bikeEndBillingRequest.e);
        if (dkn != null) {
            in.a().a((AosRequest) bikeEndBillingRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bikeEndBillingRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBikeOrderDetail(BikeOrderDetailRequest bikeOrderDetailRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bikeOrderDetailRequest.addHeaders(dkn.d);
            bikeOrderDetailRequest.setTimeout(dkn.b);
            bikeOrderDetailRequest.setRetryTimes(dkn.c);
        }
        bikeOrderDetailRequest.setUrl(BikeOrderDetailRequest.a);
        bikeOrderDetailRequest.addSignParam("channel");
        bikeOrderDetailRequest.addSignParam("cpSource");
        bikeOrderDetailRequest.addSignParam("orderId");
        bikeOrderDetailRequest.addReqParam("cpSource", bikeOrderDetailRequest.b);
        bikeOrderDetailRequest.addReqParam("orderId", bikeOrderDetailRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) bikeOrderDetailRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bikeOrderDetailRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBikePay(BikePayRequest bikePayRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bikePayRequest.addHeaders(dkn.d);
            bikePayRequest.setTimeout(dkn.b);
            bikePayRequest.setRetryTimes(dkn.c);
        }
        bikePayRequest.setUrl(BikePayRequest.a);
        bikePayRequest.addSignParam("channel");
        bikePayRequest.addSignParam("cpSource");
        bikePayRequest.addSignParam("orderId");
        bikePayRequest.addReqParam("cpSource", bikePayRequest.b);
        bikePayRequest.addReqParam("orderId", bikePayRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) bikePayRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bikePayRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBikeScanQrcode(BikeScanQrcodeRequest bikeScanQrcodeRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bikeScanQrcodeRequest.addHeaders(dkn.d);
            bikeScanQrcodeRequest.setTimeout(dkn.b);
            bikeScanQrcodeRequest.setRetryTimes(dkn.c);
        }
        bikeScanQrcodeRequest.setUrl(BikeScanQrcodeRequest.a);
        bikeScanQrcodeRequest.addSignParam("channel");
        bikeScanQrcodeRequest.addSignParam("bikecode");
        bikeScanQrcodeRequest.addReqParam("bikecode", bikeScanQrcodeRequest.b);
        bikeScanQrcodeRequest.addReqParam("citycode", bikeScanQrcodeRequest.c);
        bikeScanQrcodeRequest.addReqParam("bind", bikeScanQrcodeRequest.d);
        bikeScanQrcodeRequest.addReqParam("latitude", bikeScanQrcodeRequest.e);
        bikeScanQrcodeRequest.addReqParam("longitude", bikeScanQrcodeRequest.f);
        if (dkn != null) {
            in.a().a((AosRequest) bikeScanQrcodeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bikeScanQrcodeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
