package com.autonavi.minimap.basemap.traffic;

import com.amap.bundle.network.request.param.builder.URLBuilder.ResultProperty;
import java.util.ArrayList;

@ResultProperty(parser = csc.class)
public class TileArrayList extends ArrayList<String> {
}
