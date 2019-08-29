package org.altbeacon.beacon.service.a;

import android.annotation.TargetApi;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanFilter.Builder;
import android.os.ParcelUuid;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.List;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.j;

@TargetApi(21)
/* compiled from: ScanFilterUtils */
public final class r {
    private List<s> a(j beaconParser) {
        int[] b;
        ArrayList scanFilters = new ArrayList();
        for (int manufacturer : beaconParser.b()) {
            Long serviceUuid = beaconParser.f();
            long typeCode = beaconParser.c().longValue();
            int startOffset = beaconParser.d();
            int endOffset = beaconParser.e();
            byte[] filter = new byte[((endOffset + 1) - 2)];
            byte[] mask = new byte[((endOffset + 1) - 2)];
            byte[] typeCodeBytes = j.a(typeCode, (endOffset - startOffset) + 1);
            for (int layoutIndex = 2; layoutIndex <= endOffset; layoutIndex++) {
                int filterIndex = layoutIndex - 2;
                if (layoutIndex < startOffset) {
                    filter[filterIndex] = 0;
                    mask[filterIndex] = 0;
                } else {
                    filter[filterIndex] = typeCodeBytes[layoutIndex - startOffset];
                    mask[filterIndex] = -1;
                }
            }
            s sfd = new s(this);
            sfd.b = manufacturer;
            sfd.c = filter;
            sfd.d = mask;
            sfd.a = serviceUuid;
            scanFilters.add(sfd);
        }
        return scanFilters;
    }

    public final List<ScanFilter> a(List<j> beaconParsers) {
        List scanFilters = new ArrayList();
        for (j beaconParser : beaconParsers) {
            for (s sfd : a(beaconParser)) {
                Builder builder = new Builder();
                if (sfd.a != null) {
                    String serviceUuidString = String.format("0000%04X-0000-1000-8000-00805f9b34fb", new Object[]{sfd.a});
                    ParcelUuid parcelUuid = ParcelUuid.fromString(serviceUuidString);
                    ParcelUuid parcelUuidMask = ParcelUuid.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF");
                    if (d.a()) {
                        d.a("ScanFilterUtils", "making scan filter for service: " + serviceUuidString + Token.SEPARATOR + parcelUuid, new Object[0]);
                        d.a("ScanFilterUtils", "making scan filter with service mask: " + "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF" + Token.SEPARATOR + parcelUuidMask, new Object[0]);
                    }
                    builder.setServiceUuid(parcelUuid, parcelUuidMask);
                } else {
                    builder.setServiceUuid(null);
                    builder.setManufacturerData(sfd.b, sfd.c, sfd.d);
                }
                ScanFilter scanFilter = builder.build();
                if (d.a()) {
                    d.a("ScanFilterUtils", "Set up a scan filter: " + scanFilter, new Object[0]);
                }
                scanFilters.add(scanFilter);
            }
        }
        return scanFilters;
    }
}
