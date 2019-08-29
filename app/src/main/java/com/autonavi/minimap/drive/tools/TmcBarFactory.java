package com.autonavi.minimap.drive.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.navi.navidata.TmcColor;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.ae.route.RouteService;
import com.autonavi.jni.ae.route.model.LightBarItem;
import com.autonavi.jni.ae.route.model.TmcRoutePath;
import java.util.ArrayList;

public class TmcBarFactory {
    private int a;
    private int b;
    private int c;
    private int d;

    public TmcBarFactory() {
        this.a = 280;
        this.b = 6;
        this.c = 3;
        this.d = 0;
        this.a = agn.a((Context) AMapAppGlobal.getApplication(), (float) this.a);
        this.b = agn.a((Context) AMapAppGlobal.getApplication(), (float) this.b);
        this.c = agn.a((Context) AMapAppGlobal.getApplication(), (float) this.c);
    }

    public Bitmap loadResultTMCBar(ICarRouteResult iCarRouteResult) {
        Bitmap bitmap;
        TmcColor tmcColor;
        try {
            NavigationPath focusNavigationPath = iCarRouteResult.getFocusNavigationPath();
            if (focusNavigationPath == null) {
                return null;
            }
            int i = focusNavigationPath.mPathlength;
            LightBarItem[] decodeRouteTmcBar = RouteService.decodeRouteTmcBar(iCarRouteResult.getBackUpTbtData(), new TmcRoutePath());
            if (decodeRouteTmcBar != null && i > 0) {
                ArrayList arrayList = new ArrayList();
                if (decodeRouteTmcBar == null) {
                    bitmap = null;
                } else {
                    int length = decodeRouteTmcBar.length - 1;
                    int i2 = 0;
                    for (int i3 = 0; i3 <= length; i3++) {
                        int i4 = decodeRouteTmcBar[i3].length;
                        i2 += i4;
                        int i5 = (i4 * this.a) / i;
                        if (i5 <= 0) {
                            i5 = 1;
                        }
                        switch (decodeRouteTmcBar[i3].status) {
                            case 0:
                                tmcColor = TmcColor.UNKNOWN;
                                break;
                            case 1:
                                tmcColor = TmcColor.UNBLOCK;
                                break;
                            case 2:
                                tmcColor = TmcColor.SLOW;
                                break;
                            case 3:
                                tmcColor = TmcColor.BLOCK;
                                break;
                            case 4:
                                tmcColor = TmcColor.GRIDLOCKED;
                                break;
                            default:
                                tmcColor = TmcColor.UNKNOWN;
                                break;
                        }
                        arrayList.add(a(i5, this.b, tmcColor));
                    }
                    if (i2 < i) {
                        int i6 = ((i - i2) * this.a) / i;
                        if (i6 <= 0) {
                            i6 = 1;
                        }
                        arrayList.add(a(i6, this.b, TmcColor.UNBLOCK));
                    }
                    if (this.d < this.a) {
                        int i7 = this.a - this.d;
                        if (i7 <= 0) {
                            i7 = 1;
                        }
                        arrayList.add(a(i7, this.b, TmcColor.UNBLOCK));
                    }
                    bitmap = a(arrayList);
                }
                if (bitmap != null) {
                    return bitmap;
                }
            }
            return null;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return null;
        }
    }

    private Bitmap a(ArrayList<Bitmap> arrayList) {
        Bitmap createBitmap = Bitmap.createBitmap(this.a, arrayList.get(0).getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        int size = arrayList.size() - 1;
        int i = 0;
        for (int i2 = 0; i2 <= size; i2++) {
            Bitmap bitmap = arrayList.get(i2);
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, (float) i, 0.0f, null);
                i += bitmap.getWidth();
                bitmap.recycle();
            }
        }
        return i > this.a ? Bitmap.createScaledBitmap(createBitmap, (int) (((double) createBitmap.getWidth()) * 0.9d), createBitmap.getHeight(), true) : createBitmap;
    }

    private Bitmap a(int i, int i2, TmcColor tmcColor) {
        this.d += i;
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        new Canvas(createBitmap).drawARGB(255, tmcColor.R(), tmcColor.G(), tmcColor.B());
        return createBitmap;
    }
}
