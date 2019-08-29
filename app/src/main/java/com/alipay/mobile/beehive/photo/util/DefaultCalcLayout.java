package com.alipay.mobile.beehive.photo.util;

import android.widget.ImageView.ScaleType;
import com.alipay.android.phone.falcon.falconimg.layout.CellDetail;
import com.alipay.android.phone.falcon.falconimg.layout.LayoutDetail;
import com.alipay.android.phone.falcon.falconimg.layout.PhotoDetail;
import java.util.ArrayList;

public class DefaultCalcLayout {
    private static final float GRID_WIDTH = 0.3037037f;
    public static final float MARGIN = 0.033333335f;
    private static final float SPACING = 0.011111111f;
    private static int columns;
    private static int rows;

    public static synchronized LayoutDetail getLayoutRules(int num, ArrayList<PhotoDetail> photos) {
        LayoutDetail layoutDetail;
        PhotoDetail photoDetail;
        synchronized (DefaultCalcLayout.class) {
            if (photos != null) {
                try {
                    if (photos.size() != 0) {
                        if (num == 1) {
                            if (photos != null) {
                                photoDetail = photos.get(0);
                            } else {
                                photoDetail = null;
                            }
                            layoutDetail = getDefault1picLayout(photoDetail);
                        } else {
                            layoutDetail = getGridLayout(num);
                        }
                    }
                } catch (Exception e) {
                    PhotoLogger.e("DefaultCalcLayout error", e.toString());
                }
            }
            if (num <= 0) {
                PhotoLogger.e("DefaultCalcLayout", "getLayoutRules inputPicnum 0");
                layoutDetail = null;
            }
            layoutDetail = null;
        }
        return layoutDetail;
    }

    public static LayoutDetail getDefault1picLayout(PhotoDetail detail) {
        LayoutDetail layoutDetail = new LayoutDetail();
        if (detail == null) {
            return null;
        }
        if (detail.width <= 0 || detail.height <= 0) {
            CellDetail defaultCellDetail = new CellDetail();
            defaultCellDetail.upLeftx = 0.033333335f;
            defaultCellDetail.upLefty = 0.0f;
            defaultCellDetail.scaleType = ScaleType.CENTER_CROP;
            defaultCellDetail.addNum = 0;
            defaultCellDetail.height = 0.56296295f;
            defaultCellDetail.width = 0.56296295f;
            layoutDetail.fullHeight = defaultCellDetail.height;
            layoutDetail.layoutType = "1-1";
            layoutDetail.fullWidth = 1.0f;
            layoutDetail.itemList.add(defaultCellDetail);
            return layoutDetail;
        }
        float imageRatio = ((float) detail.height) / ((float) detail.width);
        CellDetail cellDetail = new CellDetail();
        cellDetail.upLeftx = 0.033333335f;
        cellDetail.upLefty = 0.0f;
        cellDetail.scaleType = ScaleType.CENTER_CROP;
        cellDetail.addNum = 0;
        if (0.93333334f * imageRatio > 0.56296295f) {
            cellDetail.height = 0.56296295f;
            cellDetail.width = 0.56296295f / imageRatio;
            if (cellDetail.width < 0.175f) {
                cellDetail.width = 0.175f;
            }
        } else if (0.56296295f / imageRatio > 0.93333334f) {
            cellDetail.width = 0.93333334f;
            cellDetail.height = 0.93333334f * imageRatio;
            if (cellDetail.height < 0.175f) {
                cellDetail.height = 0.175f;
            }
        } else {
            cellDetail.width = 0.93333334f;
            cellDetail.height = 0.93333334f * imageRatio;
        }
        layoutDetail.fullHeight = cellDetail.height;
        layoutDetail.layoutType = "1-1";
        layoutDetail.fullWidth = cellDetail.width + 0.06666667f;
        layoutDetail.itemList.add(cellDetail);
        return layoutDetail;
    }

    public static LayoutDetail getGridLayout(int num) {
        LayoutDetail layoutDetail = new LayoutDetail();
        generateChildrenLayout(num);
        int addnum = 0;
        if (num > 9) {
            addnum = num - 9;
            num = 9;
        }
        for (int i = 0; i < num; i++) {
            CellDetail cellDetail = new CellDetail();
            cellDetail.upLeftx = 0.0f;
            cellDetail.upLefty = 0.0f;
            cellDetail.height = 0.4935f;
            cellDetail.width = 0.4935f;
            int[] position = findPosition(i);
            cellDetail.upLeftx = 0.033333335f + (((float) position[1]) * 0.3148148f);
            cellDetail.upLefty = ((float) position[0]) * 0.3148148f;
            cellDetail.height = GRID_WIDTH;
            cellDetail.width = GRID_WIDTH;
            if (i == num - 1 && addnum > 0) {
                cellDetail.addNum = addnum;
                PhotoLogger.i("DefaultCalcLayout", "getGridLayout addNum：" + addnum);
            }
            layoutDetail.itemList.add(cellDetail);
        }
        layoutDetail.fullHeight = (((float) rows) * GRID_WIDTH) + (SPACING * ((float) (rows - 1)));
        layoutDetail.layoutType = String.valueOf(num);
        layoutDetail.weight = 1.0f;
        return layoutDetail;
    }

    private static void generateChildrenLayout(int length) {
        if (length <= 3) {
            rows = 1;
            columns = length;
        } else if (length <= 6) {
            rows = 2;
            columns = 3;
            if (length == 4) {
                columns = 2;
            }
        } else {
            rows = 3;
            columns = 3;
        }
    }

    private static int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < rows; i++) {
            int j = 0;
            while (true) {
                if (j >= columns) {
                    break;
                } else if ((columns * i) + j == childNum) {
                    position[0] = i;
                    position[1] = j;
                    break;
                } else {
                    j++;
                }
            }
        }
        return position;
    }
}
