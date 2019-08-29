package com.autonavi.bundle.entity.sugg;

import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class TipItem {
    public final int MAX_INPUT_COUNT = 3;
    public String adcode;
    public String addr;
    public String childType;
    public int column;
    public int dataType;
    public String displayInfo;
    public String district;
    public String endPoiExtension;
    public auy extensionInfo;
    public String extensionType;
    public String f_nona;
    public String funcText;
    public int historyType;
    public int iconinfo;
    public Long id;
    public int ignoreDistrict;
    public List<String> inputs = new ArrayList();
    public boolean isFromRealSceneSearch = false;
    public boolean isRectSearch = false;
    public boolean isShowRating = false;
    public boolean isSugChildClick;
    public String label;
    private String mChildNodeIconUrl;
    private String mChildNodeName;
    private Pattern mPattern = Pattern.compile("[0-9]{1}.?[0-9]*");
    private String mTopListName;
    private String mTopListType;
    private String mTopListUrl;
    public String name;
    public String needHistory;
    public boolean needSearch = true;
    public String newType;
    public String numReview;
    public JSONObject origJson;
    public String parent;
    public POI poi;
    public String poiTag;
    public String poiid;
    public String poiinfo;
    public int poiinfoColor;
    public String ratingMsg = "";
    public String richRating;
    public String searchQuery;
    public String searchTag;
    public String searchType;
    public String shortname;
    public String sndtFloorName;
    public String strf_nona;
    public String super_address;
    public String taginfo;
    public String terminals;
    public Date time;
    public List<TipItem> tipItemList;
    public String towardsAngle;
    public String transparent;
    public int type;
    public String userInput;
    public double x;
    public double x_entr;
    public double y;
    public double y_entr;

    public void addInputs(String str) {
        int i = 0;
        while (true) {
            if (i >= this.inputs.size()) {
                break;
            } else if (str.equals(this.inputs.get(i))) {
                this.inputs.remove(i);
                break;
            } else {
                i++;
            }
        }
        this.inputs.add(0, str);
        if (this.inputs.size() > 3) {
            this.inputs.remove(3);
        }
    }

    public boolean isRating(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return this.mPattern.matcher(str).matches();
    }

    public String getChildNodeIconUrl() {
        return this.mChildNodeIconUrl;
    }

    public void setChildNodeIconUrl(String str) {
        this.mChildNodeIconUrl = str;
    }

    public String getChildNodeName() {
        return this.mChildNodeName;
    }

    public void setChildNodeName(String str) {
        this.mChildNodeName = str;
    }

    public String getTopListType() {
        return this.mTopListType;
    }

    public void setTopListType(String str) {
        this.mTopListType = str;
    }

    public String getTopListName() {
        return this.mTopListName;
    }

    public void setTopListName(String str) {
        this.mTopListName = str;
    }

    public String getTopListUrl() {
        return this.mTopListUrl;
    }

    public void setTopListUrl(String str) {
        this.mTopListUrl = str;
    }
}
