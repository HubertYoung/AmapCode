package com.autonavi.miniapp.plugin.map.overlay;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLLineOverlay;
import com.autonavi.miniapp.plugin.map.property.Point;
import com.autonavi.miniapp.plugin.map.property.PolylinePropertyProcessor.Polyline;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureCacheManager;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureFactory;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureIdManager;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.miniapp.plugin.map.util.TextureIdGenerator;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MiniAppLineOverlay extends BaseMiniAppLineOverlay {
    private static final String TAG = "MiniAppLineOverlay";
    private MiniAppTextureCacheManager mTextureCacheManager;
    private MiniAppTextureIdManager mTextureIdManager;

    public MiniAppLineOverlay(bty bty, MiniAppTextureIdManager miniAppTextureIdManager, MiniAppTextureCacheManager miniAppTextureCacheManager) {
        super(bty);
        this.mTextureIdManager = miniAppTextureIdManager;
        this.mTextureCacheManager = miniAppTextureCacheManager;
    }

    public void addMiniAppLine(WeakReference<H5Page> weakReference, List<Polyline> list) {
        int i;
        H5Page h5Page = (H5Page) weakReference.get();
        if (list != null && this.mContext != null && h5Page != null) {
            for (Polyline next : list) {
                if (next.points != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Point next2 : next.points) {
                        arrayList.add(new GeoPoint(next2.longitude, next2.latitude));
                    }
                    int size = arrayList.size();
                    if (size < 2) {
                        continue;
                    } else {
                        int i2 = next.dottedLine ? 5 : 9;
                        if (next.zIndex != -1) {
                            setOverlayPriority(next.zIndex);
                        }
                        if (next.colorList != null && !next.colorList.isEmpty()) {
                            ArrayList arrayList2 = new ArrayList(next.colorList.size());
                            for (String convertRGBAColor : next.colorList) {
                                arrayList2.add(Integer.valueOf(H5MapUtils.convertRGBAColor(convertRGBAColor)));
                            }
                            List<MiniAppLineOverlayItem> lineOverlayItems = getLineOverlayItems(next, arrayList, size, i2);
                            int size2 = lineOverlayItems.size();
                            int size3 = arrayList2.size();
                            if (size2 > 0) {
                                for (int i3 = 0; i3 < size2; i3++) {
                                    if (i3 < size3) {
                                        i = ((Integer) arrayList2.get(i3)).intValue();
                                    } else {
                                        i = ((Integer) arrayList2.get(size3 - 1)).intValue();
                                    }
                                    lineOverlayItems.get(i3).setFillLineColor(i);
                                }
                                for (MiniAppLineOverlayItem next3 : lineOverlayItems) {
                                    if (!next.dottedLine) {
                                        next3.mLineProperty.f = R.drawable.map_lr;
                                    } else {
                                        next3.mLineProperty.f = R.drawable.ic_cross_road_dash;
                                    }
                                    if (((Integer) this.mTextureCacheManager.getTextureCache(this.mMapView, 0, Integer.valueOf(next3.mLineProperty.f))) == null) {
                                        MiniAppTextureFactory.createLineTexture(this.mMapView, next3.mLineType, next3.mLineProperty.f);
                                        this.mTextureCacheManager.addTextureCache(this.mMapView, 0, Integer.valueOf(next3.mLineProperty.f), Integer.valueOf(next3.mLineProperty.f));
                                    }
                                    addItem(next3);
                                }
                                return;
                            }
                            LoggerFactory.getTraceLogger().error((String) TAG, "line size is error, linsNum=".concat(String.valueOf(size2)));
                        } else {
                            MiniAppLineOverlayItem miniAppLineOverlayItem = new MiniAppLineOverlayItem(i2, (GeoPoint[]) arrayList.toArray(new GeoPoint[0]), (int) next.width);
                            miniAppLineOverlayItem.setFillLineColor(H5MapUtils.convertRGBAColor(next.color));
                            if (!next.dottedLine) {
                                miniAppLineOverlayItem.mLineProperty.f = R.drawable.map_lr;
                            } else {
                                miniAppLineOverlayItem.mLineProperty.f = R.drawable.ic_cross_road_dash;
                            }
                            if (((Integer) this.mTextureCacheManager.getTextureCache(this.mMapView, 0, Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f))) == null) {
                                MiniAppTextureFactory.createLineTexture(this.mMapView, miniAppLineOverlayItem.mLineType, miniAppLineOverlayItem.mLineProperty.f);
                                this.mTextureCacheManager.addTextureCache(this.mMapView, 0, Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f), Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f));
                            }
                            addItem(miniAppLineOverlayItem);
                        }
                    }
                }
            }
        }
    }

    private List<MiniAppLineOverlayItem> getLineOverlayItems(Polyline polyline, List<GeoPoint> list, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (i3 < i && i3 != i - 1) {
            i3++;
            arrayList.add(new MiniAppLineOverlayItem(i2, new GeoPoint[]{list.get(i3), list.get(i3)}, (int) polyline.width));
        }
        return arrayList;
    }

    public void addMiniLineWithIconPath(TextureIdGenerator textureIdGenerator, Polyline polyline, Bitmap bitmap) {
        ArrayList arrayList = new ArrayList();
        if (polyline.points != null) {
            for (Point next : polyline.points) {
                arrayList.add(new GeoPoint(next.longitude, next.latitude));
            }
            if (TextUtils.isEmpty(polyline.iconPath)) {
                int i = polyline.dottedLine ? 5 : 9;
                if (polyline.color != null) {
                    MiniAppLineOverlayItem miniAppLineOverlayItem = new MiniAppLineOverlayItem(i, (GeoPoint[]) arrayList.toArray(new GeoPoint[0]), (int) polyline.width);
                    miniAppLineOverlayItem.setFillLineColor(H5MapUtils.convertRGBAColor(polyline.color));
                    if (!polyline.dottedLine) {
                        miniAppLineOverlayItem.mLineProperty.f = R.drawable.map_lr;
                    } else {
                        miniAppLineOverlayItem.mLineProperty.f = R.drawable.ic_cross_road_dash;
                    }
                    if (((Integer) this.mTextureCacheManager.getTextureCache(this.mMapView, 0, Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f))) == null) {
                        MiniAppTextureFactory.createLineTexture(this.mMapView, miniAppLineOverlayItem.mLineType, miniAppLineOverlayItem.mLineProperty.f);
                        this.mTextureCacheManager.addTextureCache(this.mMapView, 0, polyline.iconPath, Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f));
                    }
                    addItem(miniAppLineOverlayItem);
                }
                return;
            }
            MiniAppLineOverlayItem miniAppLineOverlayItem2 = new MiniAppLineOverlayItem(8, (GeoPoint[]) arrayList.toArray(new GeoPoint[0]), (int) polyline.width);
            miniAppLineOverlayItem2.mLineProperty.j = (int) (polyline.iconWidth / 2.0d);
            Integer num = (Integer) this.mTextureCacheManager.getTextureCache(this.mMapView, 2, polyline.iconPath);
            if (num != null) {
                miniAppLineOverlayItem2.setFillLineId(num.intValue());
            } else {
                int generateMarkerId = this.mTextureIdManager.generateMarkerId(textureIdGenerator.generate(), getClass());
                if (generateMarkerId >= 0) {
                    miniAppLineOverlayItem2.mLineProperty.f = generateMarkerId;
                    MiniAppTextureFactory.createLineTexture(this.mMapView, miniAppLineOverlayItem2.mLineType, miniAppLineOverlayItem2.mLineProperty.f, bitmap);
                    this.mTextureCacheManager.addTextureCache(this.mMapView, 2, polyline.iconPath, Integer.valueOf(miniAppLineOverlayItem2.mLineProperty.f));
                } else {
                    return;
                }
            }
            addItem(miniAppLineOverlayItem2);
        }
    }

    public void setOverlayPriority(int i) {
        if (this.mGLOverlay != null) {
            ((GLLineOverlay) this.mGLOverlay).setOverlayPriority(i);
        }
    }

    public boolean clear() {
        this.mTextureCacheManager.clearTextureCache(this.mMapView, 2);
        return super.clear();
    }
}
