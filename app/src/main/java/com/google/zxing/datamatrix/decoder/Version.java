package com.google.zxing.datamatrix.decoder;

import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.route.sharebike.model.EndBill;
import com.google.zxing.FormatException;

public final class Version {
    private static final Version[] VERSIONS = buildVersions();
    private final int dataRegionSizeColumns;
    private final int dataRegionSizeRows;
    private final ECBlocks ecBlocks;
    private final int symbolSizeColumns;
    private final int symbolSizeRows;
    private final int totalCodewords;
    private final int versionNumber;

    static final class ECB {
        private final int count;
        private final int dataCodewords;

        private ECB(int i, int i2) {
            this.count = i;
            this.dataCodewords = i2;
        }

        /* synthetic */ ECB(int i, int i2, ECB ecb) {
            this(i, i2);
        }

        /* access modifiers changed from: 0000 */
        public final int getCount() {
            return this.count;
        }

        /* access modifiers changed from: 0000 */
        public final int getDataCodewords() {
            return this.dataCodewords;
        }
    }

    static final class ECBlocks {
        private final ECB[] ecBlocks;
        private final int ecCodewords;

        private ECBlocks(int i, ECB ecb) {
            this.ecCodewords = i;
            this.ecBlocks = new ECB[]{ecb};
        }

        /* synthetic */ ECBlocks(int i, ECB ecb, ECBlocks eCBlocks) {
            this(i, ecb);
        }

        private ECBlocks(int i, ECB ecb, ECB ecb2) {
            this.ecCodewords = i;
            this.ecBlocks = new ECB[]{ecb, ecb2};
        }

        /* synthetic */ ECBlocks(int i, ECB ecb, ECB ecb2, ECBlocks eCBlocks) {
            this(i, ecb, ecb2);
        }

        /* access modifiers changed from: 0000 */
        public final int getECCodewords() {
            return this.ecCodewords;
        }

        /* access modifiers changed from: 0000 */
        public final ECB[] getECBlocks() {
            return this.ecBlocks;
        }
    }

    private Version(int i, int i2, int i3, int i4, int i5, ECBlocks eCBlocks) {
        ECB[] eCBlocks2;
        this.versionNumber = i;
        this.symbolSizeRows = i2;
        this.symbolSizeColumns = i3;
        this.dataRegionSizeRows = i4;
        this.dataRegionSizeColumns = i5;
        this.ecBlocks = eCBlocks;
        int eCCodewords = eCBlocks.getECCodewords();
        int i6 = 0;
        for (ECB ecb : eCBlocks.getECBlocks()) {
            i6 += ecb.getCount() * (ecb.getDataCodewords() + eCCodewords);
        }
        this.totalCodewords = i6;
    }

    public final int getVersionNumber() {
        return this.versionNumber;
    }

    public final int getSymbolSizeRows() {
        return this.symbolSizeRows;
    }

    public final int getSymbolSizeColumns() {
        return this.symbolSizeColumns;
    }

    public final int getDataRegionSizeRows() {
        return this.dataRegionSizeRows;
    }

    public final int getDataRegionSizeColumns() {
        return this.dataRegionSizeColumns;
    }

    public final int getTotalCodewords() {
        return this.totalCodewords;
    }

    /* access modifiers changed from: 0000 */
    public final ECBlocks getECBlocks() {
        return this.ecBlocks;
    }

    public static Version getVersionForDimensions(int i, int i2) throws FormatException {
        Version[] versionArr;
        if ((i & 1) == 0 && (i2 & 1) == 0) {
            for (Version version : VERSIONS) {
                if (version.symbolSizeRows == i && version.symbolSizeColumns == i2) {
                    return version;
                }
            }
            throw FormatException.getFormatInstance();
        }
        throw FormatException.getFormatInstance();
    }

    public final String toString() {
        return String.valueOf(this.versionNumber);
    }

    private static Version[] buildVersions() {
        Version version = new Version(1, 10, 10, 8, 8, new ECBlocks(5, new ECB(1, 3, null), (ECBlocks) null));
        Version version2 = new Version(2, 12, 12, 10, 10, new ECBlocks(7, new ECB(1, 5, null), (ECBlocks) null));
        Version version3 = new Version(3, 14, 14, 12, 12, new ECBlocks(10, new ECB(1, 8, null), (ECBlocks) null));
        Version version4 = new Version(4, 16, 16, 14, 14, new ECBlocks(12, new ECB(1, 12, null), (ECBlocks) null));
        Version version5 = new Version(5, 18, 18, 16, 16, new ECBlocks(14, new ECB(1, 18, null), (ECBlocks) null));
        Version version6 = new Version(6, 20, 20, 18, 18, new ECBlocks(18, new ECB(1, 22, null), (ECBlocks) null));
        Version version7 = new Version(7, 22, 22, 20, 20, new ECBlocks(20, new ECB(1, 30, null), (ECBlocks) null));
        Version version8 = new Version(8, 24, 24, 22, 22, new ECBlocks(24, new ECB(1, 36, null), (ECBlocks) null));
        Version version9 = new Version(9, 26, 26, 24, 24, new ECBlocks(28, new ECB(1, 44, null), (ECBlocks) null));
        Version version10 = new Version(10, 32, 32, 14, 14, new ECBlocks(36, new ECB(1, 62, null), (ECBlocks) null));
        Version version11 = new Version(11, 36, 36, 16, 16, new ECBlocks(42, new ECB(1, 86, null), (ECBlocks) null));
        Version version12 = new Version(12, 40, 40, 18, 18, new ECBlocks(48, new ECB(1, 114, null), (ECBlocks) null));
        Version version13 = new Version(13, 44, 44, 20, 20, new ECBlocks(56, new ECB(1, 144, null), (ECBlocks) null));
        Version version14 = new Version(14, 48, 48, 22, 22, new ECBlocks(68, new ECB(1, 174, null), (ECBlocks) null));
        Version version15 = new Version(15, 52, 52, 24, 24, new ECBlocks(42, new ECB(2, 102, null), (ECBlocks) null));
        Version version16 = new Version(16, 64, 64, 14, 14, new ECBlocks(56, new ECB(2, 140, null), (ECBlocks) null));
        Version version17 = new Version(17, 72, 72, 16, 16, new ECBlocks(36, new ECB(4, 92, null), (ECBlocks) null));
        Version version18 = new Version(18, 80, 80, 18, 18, new ECBlocks(48, new ECB(4, 114, null), (ECBlocks) null));
        Version version19 = new Version(19, 88, 88, 20, 20, new ECBlocks(56, new ECB(4, 144, null), (ECBlocks) null));
        Version version20 = new Version(20, 96, 96, 22, 22, new ECBlocks(68, new ECB(4, 174, null), (ECBlocks) null));
        Version version21 = new Version(21, 104, 104, 24, 24, new ECBlocks(56, new ECB(6, 136, null), (ECBlocks) null));
        Version version22 = new Version(22, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 18, 18, new ECBlocks(68, new ECB(6, 175, null), (ECBlocks) null));
        Version version23 = new Version(23, 132, 132, 20, 20, new ECBlocks(62, new ECB(8, EndBill.END_ORDER_FAILED, null), (ECBlocks) null));
        Version version24 = new Version(24, 144, 144, 22, 22, new ECBlocks(62, new ECB(8, 156, null), new ECB(2, 155, null), null));
        Version version25 = new Version(25, 8, 18, 6, 16, new ECBlocks(7, new ECB(1, 5, null), (ECBlocks) null));
        Version version26 = new Version(26, 8, 32, 6, 14, new ECBlocks(11, new ECB(1, 10, null), (ECBlocks) null));
        Version version27 = new Version(27, 12, 26, 10, 24, new ECBlocks(14, new ECB(1, 16, null), (ECBlocks) null));
        Version version28 = new Version(28, 12, 36, 10, 16, new ECBlocks(18, new ECB(1, 22, null), (ECBlocks) null));
        Version version29 = new Version(29, 16, 36, 14, 16, new ECBlocks(24, new ECB(1, 32, null), (ECBlocks) null));
        Version version30 = new Version(30, 16, 48, 14, 22, new ECBlocks(28, new ECB(1, 49, null), (ECBlocks) null));
        return new Version[]{version, version2, version3, version4, version5, version6, version7, version8, version9, version10, version11, version12, version13, version14, version15, version16, version17, version18, version19, version20, version21, version22, version23, version24, version25, version26, version27, version28, version29, version30};
    }
}
