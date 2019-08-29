package com.autonavi.bundle.routecommute.drive.overlay;

import android.content.Context;
import com.autonavi.ae.gmap.gloverlay.GLRouteProperty.EAMapRouteTexture;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

public final class DriveTrafficRouteItem extends bad {
    public static final int a = agn.a((Context) AMapAppGlobal.getApplication(), 22.0f);
    public static final int b = agn.a((Context) AMapAppGlobal.getApplication(), 22.0f);
    public static final int c = agn.a((Context) AMapAppGlobal.getApplication(), 3.0f);

    public enum Texture {
        FISHBONE(EAMapRouteTexture.AMAP_ROUTE_TEXTURE_ARROW, 6, R.drawable.map_aolr, -1, R.drawable.map_aolr_3d, -1, -1, -1, -1, -1, -1, -1, -1, false),
        NAVIABLE(r21, 1, r24, r24, -1, -16739841, -16553003, -6239252, -8276762, -4926485, -7164221, -14917999, -15512457, true),
        UNNAVIABLE(EAMapRouteTexture.AMAP_ROUTE_TEXTURE_NONAVI, 4, R.drawable.drive_map_lr_dot, -1, -1, -16739841, -1, -16739841, -1, -6834194, -1, -15512457, -1, false),
        FERRY(EAMapRouteTexture.AMAP_ROUTE_TEXTURE_FERRY, 4, R.drawable.drive_map_lr_dot, -1, -1, -16739841, -1, -16739841, -1, -6834194, -1, -15512457, -1, false),
        DEFAULT(r4, 1, r7, r7, -1, -16739841, -16553003, -6239252, -8276762, -4926485, -7164221, -14917999, -15512457, true),
        SMOOTH(r21, 1, r24, r24, -1, -16729569, -16749038, -6564923, -8537177, -4922425, -7289704, -12159132, -11694996, true),
        SLOW(r4, 1, r7, r7, -1, -17920, -3047422, -272715, -2378091, -267849, -3556721, -6322620, -4876471, true),
        BLOCK(r21, 1, r24, r24, -1, -844512, -5566703, -1985857, -3170388, -609098, -2714732, -8173752, -6532533, true),
        BLOCK_HEAVY(r4, 1, r7, r7, -1, -5764853, -7665397, -3172444, -4487018, -2120538, -3043440, -10078666, -8173501, true);
        
        public boolean isShowArrow;
        public int lineType;
        public int mBKBgDayColor;
        public int mBKBgNightColor;
        public int mBKFilledDayColor;
        public int mBKFilledNightColor;
        public int mBgColor;
        public int mBgResId;
        public int mFilledColor;
        public int mFilledResId;
        public int mHLBgColor;
        public int mHLFilledColor;
        public int mSimple3DFilledResId;
        public int mUnHLFilledResId;
        public EAMapRouteTexture textureType;

        private Texture(EAMapRouteTexture eAMapRouteTexture, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, boolean z) {
            this.textureType = eAMapRouteTexture;
            this.lineType = i;
            this.mFilledResId = i2;
            this.mBgResId = i3;
            this.mSimple3DFilledResId = i4;
            this.mHLFilledColor = i5;
            this.mHLBgColor = i6;
            this.mUnHLFilledResId = R.drawable.map_frontlr;
            this.mFilledColor = i7;
            this.mBgColor = i8;
            this.mBKFilledDayColor = i9;
            this.mBKBgDayColor = i10;
            this.mBKFilledNightColor = i11;
            this.mBKBgNightColor = i12;
            this.isShowArrow = z;
        }

        public final int getFilledColor(boolean z, boolean z2) {
            if (z) {
                return this.mHLFilledColor;
            }
            if (z2) {
                return this.mBKFilledNightColor;
            }
            return this.mBKFilledDayColor;
        }

        public final int getBgResColor(boolean z, boolean z2) {
            if (z) {
                return this.mHLBgColor;
            }
            if (z2) {
                return this.mBKBgNightColor;
            }
            return this.mBKBgDayColor;
        }

        public final int getFillResId(boolean z) {
            if (z) {
                return this.mFilledResId;
            }
            return this.mUnHLFilledResId;
        }
    }
}
