package com.autonavi.minimap.ajx3.widget.view.list;

import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.autonavi.minimap.ajx3.dom.AjxDomGroupNode;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsAttribute;
import com.autonavi.minimap.ajx3.dom.JsDomList;
import com.autonavi.minimap.ajx3.dom.JsDomListCellData;
import com.autonavi.minimap.ajx3.dom.JsDomListSection;
import com.autonavi.minimap.ajx3.dom.JsDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomNodeFake;
import com.autonavi.minimap.ajx3.dom.JsDomProperty;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.StringUtils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AjxListData {
    public static int EMPTY_ITEM_POSITION = -1;
    private static final String TAG = "AjxListData";
    private int VIEW_TYPE = 100;
    /* access modifiers changed from: private */
    public boolean hasSectionFooter;
    /* access modifiers changed from: private */
    public boolean hasSectionHeader;
    private LongSparseArray<Float> mBeforeExpandCellHeightArray;
    /* access modifiers changed from: private */
    public List<AjxListCell> mCells = new LinkedList();
    /* access modifiers changed from: private */
    public int mColumnCount;
    /* access modifiers changed from: private */
    public boolean mIsWaterFall;
    private AjxListCell mListFooter = null;
    /* access modifiers changed from: private */
    public AjxListCell mListHeader = null;
    private LongSparseArray<AjxDomNode> mNodeMap = new LongSparseArray<>();
    private LongSparseArray<Integer> mNodeViewType = new LongSparseArray<>();
    /* access modifiers changed from: private */
    public List<Section> mSections = new ArrayList();
    private LongSparseArray<AjxDomNode> mTemplateMap = new LongSparseArray<>();
    private SparseArray<AjxDomNode> mTemplateNodes = new SparseArray<>();
    /* access modifiers changed from: private */
    public List<AjxListCell> mValidCells = new LinkedList();

    public class Section {
        float[] columnHeight;
        private boolean isFullSpan = false;
        List<AjxListCell> mChildren = new ArrayList();
        LinkedList[] mColumnArray;
        AjxListCell mFooter = null;
        AjxListCell mHeader = null;
        int mSectionColumnCount = AjxListData.this.mColumnCount;
        List<AjxListCell> mValidChildren = new ArrayList();
        float sectionHeight = 0.0f;

        public Section() {
        }

        private void initCellList() {
            this.columnHeight = new float[this.mSectionColumnCount];
            this.mColumnArray = new LinkedList[this.mSectionColumnCount];
            for (int i = 0; i < this.mSectionColumnCount; i++) {
                this.mColumnArray[i] = new LinkedList();
            }
        }

        private void dump() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.mSectionColumnCount; i++) {
                sb.append(" ---- > column: ".concat(String.valueOf(i)));
                sb.append(" ；columnHeight: ");
                sb.append(this.columnHeight[i]);
                sb.append("\n");
                int size = this.mColumnArray[i].size();
                long j = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    Object obj = this.mColumnArray[i].get(i2);
                    if (obj instanceof AjxListCell) {
                        AjxListCell ajxListCell = (AjxListCell) obj;
                        StringBuilder sb2 = new StringBuilder("      cell: ");
                        sb2.append(ajxListCell.getId());
                        sb2.append("  top: ");
                        sb2.append(ajxListCell.mScrollTopInSection);
                        sb2.append(" ,height: ");
                        sb2.append(ajxListCell.getHeight());
                        sb.append(sb2.toString());
                        sb.append("\n");
                        j = (long) (((float) j) + ajxListCell.getHeight());
                    }
                }
                sb.append("      totalHeight: ".concat(String.valueOf(j)));
                sb.append("\n");
                sb.append("\n");
            }
            StringBuilder sb3 = new StringBuilder(" Section: ");
            sb3.append(this.sectionHeight);
            sb3.append(" \n ");
            sb3.append(sb.toString());
        }

        private int findTargetColumn() {
            int i = 0;
            if (this.mSectionColumnCount <= 1) {
                return 0;
            }
            float f = this.columnHeight[0];
            for (int i2 = 1; i2 < this.mSectionColumnCount; i2++) {
                if (f > this.columnHeight[i2]) {
                    f = this.columnHeight[i2];
                    i = i2;
                }
            }
            return i;
        }

        private void updateSectionHeight() {
            float f = this.columnHeight[0];
            for (int i = 1; i < this.mSectionColumnCount; i++) {
                if (f < this.columnHeight[i]) {
                    f = this.columnHeight[i];
                }
            }
            this.sectionHeight = f;
        }

        private void recomputeColumnHeight() {
            int size = AjxListData.this.mValidCells.size();
            initCellList();
            for (int i = 0; i < size; i++) {
                if (i == 0 && AjxListData.this.hasSectionHeader && this.mHeader != null) {
                    AjxListCell ajxListCell = this.mHeader;
                    ajxListCell.mScrollTopInSection = this.sectionHeight;
                    this.sectionHeight += ajxListCell.getHeight();
                    for (int i2 = 0; i2 < this.mSectionColumnCount; i2++) {
                        this.columnHeight[i2] = this.sectionHeight;
                    }
                } else if (i != size - 1 || !AjxListData.this.hasSectionFooter) {
                    AjxListCell ajxListCell2 = (AjxListCell) AjxListData.this.mValidCells.get(i);
                    int findTargetColumn = findTargetColumn();
                    ajxListCell2.mScrollTopInSection = this.columnHeight[findTargetColumn];
                    float[] fArr = this.columnHeight;
                    fArr[findTargetColumn] = fArr[findTargetColumn] + ajxListCell2.getHeight();
                    this.mColumnArray[findTargetColumn].add(ajxListCell2);
                    updateSectionHeight();
                } else {
                    AjxListCell ajxListCell3 = this.mFooter;
                    ajxListCell3.mScrollTopInSection = this.sectionHeight;
                    this.sectionHeight += ajxListCell3.getHeight();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void init(JsDomListSection jsDomListSection) {
            if (AjxListData.this.mIsWaterFall) {
                String attributeValue = jsDomListSection.getAttributeValue((String) "column-count");
                if (!TextUtils.isEmpty(attributeValue)) {
                    try {
                        int parseInt = StringUtils.parseInt(attributeValue);
                        if (parseInt == 1) {
                            this.isFullSpan = true;
                            this.mSectionColumnCount = parseInt;
                        }
                    } catch (Exception unused) {
                    }
                }
            }
            initCellList();
            JsDomNode header = jsDomListSection.getHeader();
            if (header != null) {
                AjxDomNode createAjxNode = header.createAjxNode();
                Integer access$500 = AjxListData.this.getViewTypeByNodeId(Long.valueOf(createAjxNode.getId()));
                AjxListCell ajxListCell = new AjxListCell((JsDomNode) createAjxNode.getData(), this, access$500.intValue());
                AjxListData.this.saveTemplateToMap(access$500.intValue(), createAjxNode);
                createAjxNode.setIsHeader();
                ajxListCell.setIsHeader();
                this.mChildren.add(ajxListCell);
                this.mValidChildren.add(ajxListCell);
                AjxListData.this.saveNodeToMap(ajxListCell);
                this.mHeader = ajxListCell;
                AjxListData.this.hasSectionHeader = true;
                ajxListCell.mScrollTopInSection = this.sectionHeight;
                this.sectionHeight += ajxListCell.getHeight();
                new StringBuilder(" hasSectionHeader: ").append(this.sectionHeight);
                for (int i = 0; i < this.mSectionColumnCount; i++) {
                    this.columnHeight[i] = this.sectionHeight;
                }
            }
            JsDomListCellData[] cells = jsDomListSection.getCells();
            if (cells != null && cells.length > 0) {
                for (JsDomListCellData jsDomListCellData : cells) {
                    AjxListCell ajxListCell2 = new AjxListCell(jsDomListCellData, this, AjxListData.this.getViewTypeByNodeId(Long.valueOf(jsDomListCellData.getCellNodeId())).intValue());
                    this.mChildren.add(ajxListCell2);
                    if (ajxListCell2.isValidListCell()) {
                        this.mValidChildren.add(ajxListCell2);
                    }
                    if (AjxListData.this.mIsWaterFall && this.isFullSpan) {
                        ajxListCell2.mIsFullSpan = true;
                    }
                    AjxListData.this.saveNodeToMap(ajxListCell2);
                    int findTargetColumn = findTargetColumn();
                    ajxListCell2.mScrollTopInSection = this.columnHeight[findTargetColumn];
                    float[] fArr = this.columnHeight;
                    fArr[findTargetColumn] = fArr[findTargetColumn] + ajxListCell2.getHeight();
                    this.mColumnArray[findTargetColumn].add(ajxListCell2);
                    updateSectionHeight();
                }
            }
            JsDomNode footer = jsDomListSection.getFooter();
            if (footer != null) {
                AjxDomNode createAjxNode2 = footer.createAjxNode();
                Integer access$5002 = AjxListData.this.getViewTypeByNodeId(Long.valueOf(createAjxNode2.getId()));
                AjxListCell ajxListCell3 = new AjxListCell((JsDomNode) createAjxNode2.getData(), this, access$5002.intValue());
                AjxListData.this.saveTemplateToMap(access$5002.intValue(), createAjxNode2);
                createAjxNode2.setIsFooter();
                ajxListCell3.setIsFooter();
                this.mChildren.add(ajxListCell3);
                this.mValidChildren.add(ajxListCell3);
                AjxListData.this.saveNodeToMap(ajxListCell3);
                this.mFooter = ajxListCell3;
                AjxListData.this.hasSectionFooter = true;
                ajxListCell3.mScrollTopInSection = this.sectionHeight;
                this.sectionHeight += ajxListCell3.getHeight();
            }
            if (AjxListData.this.mIsWaterFall) {
                AjxDomNode createAjxNode3 = new JsDomNodeFake(System.currentTimeMillis()).createAjxNode();
                Integer access$5003 = AjxListData.this.getViewTypeByNodeId(Long.valueOf(createAjxNode3.getId()));
                AjxListCell ajxListCell4 = new AjxListCell((JsDomNode) createAjxNode3.getData(), this, access$5003.intValue());
                AjxListData.this.saveTemplateToMap(access$5003.intValue(), createAjxNode3);
                createAjxNode3.setIsFooter();
                ajxListCell4.setIsFooter();
                this.mChildren.add(ajxListCell4);
                this.mValidChildren.add(ajxListCell4);
                AjxListData.this.saveNodeToMap(ajxListCell4);
                this.mFooter = ajxListCell4;
                AjxListData.this.hasSectionFooter = true;
            }
            dump();
        }

        /* access modifiers changed from: 0000 */
        public void clear() {
            for (AjxListCell access$800 : this.mValidChildren) {
                AjxListData.this.removeNodeFromMap(access$800);
            }
            this.mChildren.clear();
            this.mValidChildren.clear();
            this.mHeader = null;
            this.mFooter = null;
            this.mColumnArray = null;
            this.columnHeight = null;
        }

        /* access modifiers changed from: 0000 */
        public AjxListCell getValidCellAtPos(int i) {
            while (i < this.mChildren.size()) {
                AjxListCell ajxListCell = this.mChildren.get(i);
                if (ajxListCell.isValidListCell()) {
                    return ajxListCell;
                }
                i++;
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public int add(int i, AjxListCell ajxListCell) {
            int i2;
            if (this.mHeader != null) {
                i++;
            }
            int i3 = AjxListData.EMPTY_ITEM_POSITION;
            int indexOf = AjxListData.this.mSections.indexOf(this);
            int i4 = 1;
            int i5 = AjxListData.this.mListHeader == null ? 0 : 1;
            if (AjxListData.this.mListHeader == null) {
                i4 = 0;
            }
            for (int i6 = 0; i6 < indexOf; i6++) {
                Section section = (Section) AjxListData.this.mSections.get(i6);
                i5 += section.mChildren.size();
                i4 += section.mValidChildren.size();
            }
            AjxListData.this.mCells.add(i5 + i, ajxListCell);
            AjxListData.this.saveNodeToMap(ajxListCell);
            if (!ajxListCell.isValidListCell()) {
                this.mChildren.add(i, ajxListCell);
                return i3;
            }
            AjxListCell validCellAtPos = getValidCellAtPos(i);
            this.mChildren.add(i, ajxListCell);
            if (validCellAtPos != null) {
                i2 = this.mValidChildren.indexOf(validCellAtPos);
            } else {
                i2 = this.mValidChildren.size();
            }
            this.mValidChildren.add(i2, ajxListCell);
            int i7 = i4 + i2;
            AjxListData.this.mValidCells.add(i7, ajxListCell);
            if (i7 >= 0) {
                recomputeColumnHeight();
            }
            return i7;
        }

        /* access modifiers changed from: 0000 */
        public int replace(int i, AjxListCell ajxListCell) {
            int i2;
            if (this.mHeader != null) {
                i++;
            }
            int i3 = AjxListData.EMPTY_ITEM_POSITION;
            if (i < 0 || i > this.mChildren.size()) {
                return i3;
            }
            AjxListCell ajxListCell2 = this.mChildren.get(i);
            int indexOf = AjxListData.this.mCells.indexOf(ajxListCell2);
            if (indexOf >= 0 && indexOf <= AjxListData.this.mCells.size()) {
                AjxListData.this.mCells.set(indexOf, ajxListCell);
            }
            boolean isValidListCell = ajxListCell2.isValidListCell();
            boolean isValidListCell2 = ajxListCell.isValidListCell();
            if (isValidListCell && isValidListCell2) {
                i3 = AjxListData.this.mValidCells.indexOf(ajxListCell2);
                AjxListData.this.mValidCells.set(i3, ajxListCell);
                this.mValidChildren.set(this.mValidChildren.indexOf(ajxListCell2), ajxListCell);
            } else if (isValidListCell) {
                i3 = AjxListData.this.mValidCells.indexOf(ajxListCell2);
                AjxListData.this.mValidCells.remove(ajxListCell2);
                this.mValidChildren.remove(ajxListCell2);
            } else if (isValidListCell2) {
                int indexOf2 = AjxListData.this.mSections.indexOf(this);
                int i4 = AjxListData.this.mListHeader == null ? 0 : 1;
                for (int i5 = 0; i5 < indexOf2; i5++) {
                    i4 += ((Section) AjxListData.this.mSections.get(i5)).mValidChildren.size();
                }
                AjxListCell validCellAtPos = getValidCellAtPos(i);
                if (validCellAtPos != null) {
                    i2 = this.mValidChildren.indexOf(validCellAtPos);
                } else {
                    i2 = this.mValidChildren.size();
                }
                this.mValidChildren.add(i2, ajxListCell);
                i3 = i2 + i4;
                AjxListData.this.mValidCells.add(i3, ajxListCell);
            }
            this.mChildren.set(i, ajxListCell);
            AjxListData.this.saveNodeToMap(ajxListCell);
            AjxListData.this.removeNodeFromMap(ajxListCell2);
            if (i3 >= 0) {
                recomputeColumnHeight();
            }
            return i3;
        }

        /* access modifiers changed from: 0000 */
        public int remove(int i) {
            if (this.mHeader != null) {
                i++;
            }
            int i2 = AjxListData.EMPTY_ITEM_POSITION;
            if (i >= 0 && i < this.mChildren.size()) {
                AjxListCell remove = this.mChildren.remove(i);
                if (remove == null) {
                    return i2;
                }
                AjxListData.this.mCells.remove(remove);
                if (remove.isValidListCell()) {
                    i2 = AjxListData.this.mValidCells.indexOf(remove);
                    this.mValidChildren.remove(remove);
                    AjxListData.this.mValidCells.remove(remove);
                }
                AjxListData.this.removeNodeFromMap(remove);
            }
            if (i2 >= 0) {
                recomputeColumnHeight();
            }
            return i2;
        }
    }

    public AjxListData(JsDomList jsDomList, int i) {
        boolean z = false;
        this.hasSectionHeader = false;
        this.hasSectionFooter = false;
        this.mBeforeExpandCellHeightArray = new LongSparseArray<>();
        this.mIsWaterFall = false;
        this.mColumnCount = 1;
        this.mColumnCount = i <= 0 ? 1 : i;
        this.mIsWaterFall = this.mColumnCount > 1 ? true : z;
        init(jsDomList);
    }

    public void addTemplate(AjxDomNode ajxDomNode) {
        saveTemplateToMap(getViewTypeByNodeId(Long.valueOf(ajxDomNode.getId())).intValue(), ajxDomNode);
    }

    public void updateTemplateAttribute(long j, long j2, JsAttribute jsAttribute) {
        updateTemplateProperty(j, j2, jsAttribute, null);
    }

    public void updateTemplateStyle(long j, long j2, JsDomProperty jsDomProperty) {
        updateTemplateProperty(j, j2, null, jsDomProperty);
    }

    private void updateTemplateProperty(long j, long j2, JsAttribute jsAttribute, JsDomProperty jsDomProperty) {
        int intValue = getViewTypeByNodeId(Long.valueOf(j)).intValue();
        AjxDomNode ajxDomNode = this.mTemplateNodes.get(intValue);
        if (ajxDomNode != null) {
            SparseIntArray sparseIntArray = new SparseIntArray();
            if (findNodeInTemplate(sparseIntArray, ajxDomNode, j2, 0, 0) != null) {
                for (AjxListCell next : this.mCells) {
                    if (next.mItemViewType == intValue) {
                        AjxDomNode findNodeInCell = findNodeInCell(next, sparseIntArray);
                        if (findNodeInCell != null) {
                            if (jsAttribute != null) {
                                findNodeInCell.setAttribute(jsAttribute, true);
                            } else if (jsDomProperty != null) {
                                findNodeInCell.setStyle(jsDomProperty, true);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean addSection(int i, JsDomListSection jsDomListSection, BaseListAdapter baseListAdapter) {
        if (i < 0 || i > this.mSections.size()) {
            LogHelper.throwRunTimeException("addSection() the sectionIndex is invalid !! which value is: ".concat(String.valueOf(i)));
            return false;
        }
        Section section = new Section();
        int i2 = this.mListHeader == null ? 0 : 1;
        int i3 = this.mListHeader == null ? 0 : 1;
        for (int i4 = 0; i4 < i; i4++) {
            Section section2 = this.mSections.get(i4);
            i3 += section2.mChildren.size();
            i2 += section2.mValidChildren.size();
        }
        this.mSections.add(i, section);
        section.init(jsDomListSection);
        this.mCells.addAll(i3, section.mChildren);
        int size = section.mValidChildren.size();
        if (size <= 0) {
            return false;
        }
        for (int i5 = size - 1; i5 >= 0; i5--) {
            this.mValidCells.add(i2, section.mValidChildren.get(i5));
            if (baseListAdapter != null) {
                baseListAdapter.notifyItemInserted(i2);
            }
        }
        return true;
    }

    public boolean replaceSection(int i, JsDomListSection jsDomListSection, BaseListAdapter baseListAdapter) {
        if (i < 0 || i >= this.mSections.size()) {
            LogHelper.throwRunTimeException("can not find this section index! which value is: ".concat(String.valueOf(i)));
            return false;
        }
        removeSection(i, baseListAdapter);
        addSection(i, jsDomListSection, baseListAdapter);
        return true;
    }

    public boolean removeSection(int i, BaseListAdapter baseListAdapter) {
        if (i < 0 || i >= this.mSections.size()) {
            LogHelper.throwRunTimeException("removeSection() can not find this section!! which value is: ".concat(String.valueOf(i)));
            return false;
        }
        int i2 = EMPTY_ITEM_POSITION;
        Section section = this.mSections.get(i);
        if (section.mValidChildren.size() > 0) {
            i2 = this.mValidCells.indexOf(section.mValidChildren.get(0));
        }
        this.mCells.removeAll(section.mChildren);
        for (AjxListCell remove : section.mValidChildren) {
            this.mValidCells.remove(remove);
            if (baseListAdapter != null) {
                baseListAdapter.notifyItemRemoved(i2);
            }
        }
        section.clear();
        this.mSections.remove(i);
        return true;
    }

    public int addCell(JsDomListCellData jsDomListCellData) {
        int i = EMPTY_ITEM_POSITION;
        if (jsDomListCellData != null) {
            int sectionIndex = jsDomListCellData.getSectionIndex();
            int dataIndex = jsDomListCellData.getDataIndex();
            if (sectionIndex < 0 || sectionIndex >= this.mSections.size()) {
                LogHelper.throwRunTimeException("addCell() can not find this section!! which value is: ".concat(String.valueOf(sectionIndex)));
                return i;
            }
            Section section = this.mSections.get(sectionIndex);
            i = section.add(dataIndex, new AjxListCell(jsDomListCellData, section, getViewTypeByNodeId(Long.valueOf(jsDomListCellData.getCellNodeId())).intValue()));
        }
        return i;
    }

    public int replaceCell(JsDomListCellData jsDomListCellData) {
        int i = EMPTY_ITEM_POSITION;
        if (jsDomListCellData != null) {
            int sectionIndex = jsDomListCellData.getSectionIndex();
            if (sectionIndex < 0 || sectionIndex >= this.mSections.size()) {
                LogHelper.throwRunTimeException("replaceCell() can not find this section!! which value is: ".concat(String.valueOf(sectionIndex)));
                return i;
            }
            int dataIndex = jsDomListCellData.getDataIndex();
            Section section = this.mSections.get(sectionIndex);
            i = section.replace(dataIndex, new AjxListCell(jsDomListCellData, section, getViewTypeByNodeId(Long.valueOf(jsDomListCellData.getCellNodeId())).intValue()));
        }
        return i;
    }

    public int removeCell(int i, int i2) {
        if (i >= 0 && i < this.mSections.size()) {
            return this.mSections.get(i).remove(i2);
        }
        LogHelper.throwRunTimeException("replaceCell() can not find this section!! which value is: ".concat(String.valueOf(i)));
        return EMPTY_ITEM_POSITION;
    }

    public int displayChanged(AjxListCell ajxListCell, int i, int i2) {
        if (i == i2) {
            return EMPTY_ITEM_POSITION;
        }
        if (1056964728 == i2) {
            int indexOf = this.mValidCells.indexOf(ajxListCell);
            if (ajxListCell.mBelongSection != null) {
                ajxListCell.mBelongSection.mValidChildren.remove(ajxListCell);
            }
            this.mValidCells.remove(ajxListCell);
            return indexOf;
        } else if (this.mValidCells.contains(ajxListCell) || ajxListCell.mBelongSection == null) {
            return EMPTY_ITEM_POSITION;
        } else {
            Section section = ajxListCell.mBelongSection;
            int indexOf2 = section.mChildren.indexOf(ajxListCell);
            if (indexOf2 < 0) {
                return EMPTY_ITEM_POSITION;
            }
            int i3 = 1;
            AjxListCell validCellAtPos = section.getValidCellAtPos(indexOf2 + 1);
            int indexOf3 = validCellAtPos != null ? section.mValidChildren.indexOf(validCellAtPos) : section.mValidChildren.size();
            section.mValidChildren.add(indexOf3, ajxListCell);
            int indexOf4 = this.mSections.indexOf(section);
            if (this.mListHeader == null) {
                i3 = 0;
            }
            int i4 = indexOf3 + i3;
            if (indexOf4 > 0) {
                for (int i5 = 0; i5 < indexOf4; i5++) {
                    i4 += this.mSections.get(i5).mValidChildren.size();
                }
            }
            this.mValidCells.add(i4, ajxListCell);
            return i4;
        }
    }

    public void init(JsDomList jsDomList) {
        this.mListFooter = null;
        this.mListHeader = null;
        this.mNodeViewType.clear();
        this.mTemplateNodes.clear();
        this.mCells.clear();
        this.mSections.clear();
        this.mValidCells.clear();
        this.mNodeMap.clear();
        this.mTemplateMap.clear();
        AjxDomNode[] templates = jsDomList.getTemplates();
        if (templates != null && templates.length > 0) {
            for (AjxDomNode ajxDomNode : templates) {
                if (ajxDomNode != null) {
                    saveTemplateToMap(getViewTypeByNodeId(Long.valueOf(ajxDomNode.getId())).intValue(), ajxDomNode);
                }
            }
        }
        AjxDomNode header = jsDomList.getHeader();
        if (header != null) {
            Integer viewTypeByNodeId = getViewTypeByNodeId(Long.valueOf(header.getId()));
            AjxListCell ajxListCell = new AjxListCell((JsDomNode) header.getData(), (Section) null, viewTypeByNodeId.intValue());
            saveTemplateToMap(viewTypeByNodeId.intValue(), header);
            ajxListCell.setIsHeader();
            header.setIsHeader();
            this.mCells.add(ajxListCell);
            this.mValidCells.add(ajxListCell);
            this.mListHeader = ajxListCell;
            saveNodeToMap(ajxListCell);
        }
        JsDomListSection[] sections = jsDomList.getSections();
        if (sections != null) {
            for (JsDomListSection init : sections) {
                Section section = new Section();
                section.init(init);
                this.mSections.add(section);
                this.mCells.addAll(section.mChildren);
                this.mValidCells.addAll(section.mValidChildren);
            }
        }
        AjxDomNode footer = jsDomList.getFooter();
        if (footer != null) {
            Integer viewTypeByNodeId2 = getViewTypeByNodeId(Long.valueOf(footer.getId()));
            AjxListCell ajxListCell2 = new AjxListCell((JsDomNode) footer.getData(), (Section) null, viewTypeByNodeId2.intValue());
            saveTemplateToMap(viewTypeByNodeId2.intValue(), footer);
            ajxListCell2.setIsFooter();
            footer.setIsFooter();
            this.mCells.add(ajxListCell2);
            this.mValidCells.add(ajxListCell2);
            this.mListFooter = ajxListCell2;
            saveNodeToMap(ajxListCell2);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getDataCount() {
        return this.mValidCells.size();
    }

    /* access modifiers changed from: 0000 */
    public int getItemViewType(int i) {
        return this.mValidCells.get(i).mItemViewType;
    }

    /* access modifiers changed from: 0000 */
    public AjxListCell getCellData(int i) {
        return this.mValidCells.get(i);
    }

    /* access modifiers changed from: 0000 */
    public int getCellPosition(long j) {
        for (int i = 0; i < this.mValidCells.size(); i++) {
            AjxListCell ajxListCell = this.mValidCells.get(i);
            if (ajxListCell != null && ajxListCell.getId() == j) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public AjxDomNode getTemplate(int i) {
        return this.mTemplateNodes.get(i);
    }

    /* access modifiers changed from: 0000 */
    public int[] getSectionHeaderAndFooter(int i) {
        int[] iArr = {-1, -1};
        if (i < 0 || i >= this.mValidCells.size()) {
            LogHelper.throwRunTimeException("get wrong list cell index!!");
            return iArr;
        }
        Section section = this.mValidCells.get(i).mBelongSection;
        if (section == null) {
            return iArr;
        }
        if (section.mHeader != null && section.mValidChildren.size() > 1) {
            iArr[0] = this.mValidCells.indexOf(section.mHeader);
        }
        if (section.mFooter == null || section.mValidChildren.size() <= 1) {
            return iArr;
        }
        iArr[1] = this.mValidCells.indexOf(section.mFooter);
        return iArr;
    }

    /* access modifiers changed from: 0000 */
    public Section getSectionPosition(int i) {
        if (i >= 0 && i < this.mValidCells.size()) {
            Section section = this.mValidCells.get(i).mBelongSection;
            if (section != null) {
                return section;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public int getScrollToPosition(long j) {
        AjxDomNode ajxDomNode = (AjxDomNode) this.mNodeMap.get(j);
        if (!(ajxDomNode instanceof AjxListCell)) {
            return -1;
        }
        return this.mValidCells.indexOf(getListCell((AjxListCell) ajxDomNode));
    }

    /* access modifiers changed from: 0000 */
    public int getScrollToPosition(float f, boolean z) {
        float f2 = 0.0f;
        if (f <= 0.0f) {
            return 0;
        }
        int size = this.mValidCells.size();
        for (int i = 0; i < size; i++) {
            f2 += this.mValidCells.get(i).getHeight();
            if (Math.abs(f2 - f) <= 2.0f || f2 >= f) {
                int i2 = i + 1;
                return i2 < size ? i2 : size - 1;
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public float getTargetScrollOffsetY(long j) {
        AjxDomNode ajxDomNode = (AjxDomNode) this.mNodeMap.get(j);
        if (ajxDomNode instanceof AjxListCell) {
            AjxListCell listCell = getListCell((AjxListCell) ajxDomNode);
            if (listCell != null) {
                Section section = listCell.mBelongSection;
                float f = 0.0f;
                if (section == null) {
                    return 0.0f;
                }
                int indexOf = this.mSections.indexOf(section);
                if (indexOf < 0 || indexOf >= this.mSections.size()) {
                    return 0.0f;
                }
                for (int i = 0; i < indexOf; i++) {
                    f += this.mSections.get(i).sectionHeight;
                }
                return f + listCell.mScrollTopInSection;
            }
        }
        return -1.0f;
    }

    /* access modifiers changed from: 0000 */
    public float getCellTotalHeight(int i) {
        int i2 = 0;
        float f = 0.0f;
        if (this.mIsWaterFall) {
            AjxListCell ajxListCell = this.mValidCells.get(i);
            if (ajxListCell == null) {
                return 0.0f;
            }
            Section section = ajxListCell.mBelongSection;
            if (section == null) {
                return 0.0f;
            }
            int indexOf = this.mSections.indexOf(section);
            if (indexOf < 0 || indexOf >= this.mSections.size()) {
                return 0.0f;
            }
            while (i2 < indexOf) {
                f += this.mSections.get(i2).sectionHeight;
                i2++;
            }
            return f + ajxListCell.mScrollTopInSection;
        } else if (i >= this.mValidCells.size()) {
            StringBuilder sb = new StringBuilder(" measure cell height index > data size: ");
            sb.append(i);
            sb.append(" , total: ");
            sb.append(this.mValidCells.size());
            LogHelper.throwRunTimeException(sb.toString());
            return 0.0f;
        } else {
            while (i2 < i) {
                f += this.mValidCells.get(i2).getHeight();
                i2++;
            }
            return f;
        }
    }

    /* access modifiers changed from: 0000 */
    public float getCellTotalHeight() {
        float f = 0.0f;
        if (this.mIsWaterFall) {
            for (int i = 0; i < this.mSections.size(); i++) {
                f += this.mSections.get(i).sectionHeight;
            }
            return f;
        }
        for (AjxListCell height : this.mValidCells) {
            f += height.getHeight();
        }
        return f;
    }

    /* access modifiers changed from: private */
    public Integer getViewTypeByNodeId(Long l) {
        Integer num = (Integer) this.mNodeViewType.get(l.longValue());
        if (num != null) {
            return num;
        }
        this.VIEW_TYPE++;
        Integer valueOf = Integer.valueOf(this.VIEW_TYPE);
        this.mNodeViewType.put(l.longValue(), valueOf);
        return valueOf;
    }

    private AjxDomNode findNodeInTemplate(SparseIntArray sparseIntArray, AjxDomNode ajxDomNode, long j, int i, int i2) {
        sparseIntArray.put(i, i2);
        if (ajxDomNode.getId() == j) {
            return ajxDomNode;
        }
        List<AjxDomNode> children = ajxDomNode.getChildren();
        if (children != null) {
            for (int i3 = 0; i3 < children.size(); i3++) {
                AjxDomNode findNodeInTemplate = findNodeInTemplate(sparseIntArray, children.get(i3), j, i + 1, i3);
                if (findNodeInTemplate != null) {
                    return findNodeInTemplate;
                }
            }
        }
        sparseIntArray.put(i, -1);
        return null;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell, code=com.autonavi.minimap.ajx3.dom.AjxDomNode, for r4v0, types: [com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell, com.autonavi.minimap.ajx3.dom.AjxDomNode] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.minimap.ajx3.dom.AjxDomNode findNodeInCell(com.autonavi.minimap.ajx3.dom.AjxDomNode r4, android.util.SparseIntArray r5) {
        /*
            r3 = this;
            r0 = 1
        L_0x0001:
            int r1 = r5.size()
            if (r0 >= r1) goto L_0x0026
            int r1 = r5.get(r0)
            r2 = -1
            if (r1 != r2) goto L_0x000f
            return r4
        L_0x000f:
            java.util.List r4 = r4.getChildren()
            if (r4 == 0) goto L_0x0024
            int r2 = r4.size()
            if (r2 <= r1) goto L_0x0024
            java.lang.Object r4 = r4.get(r1)
            com.autonavi.minimap.ajx3.dom.AjxDomNode r4 = (com.autonavi.minimap.ajx3.dom.AjxDomNode) r4
            int r0 = r0 + 1
            goto L_0x0001
        L_0x0024:
            r4 = 0
            return r4
        L_0x0026:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.view.list.AjxListData.findNodeInCell(com.autonavi.minimap.ajx3.dom.ajxnode.AjxListCell, android.util.SparseIntArray):com.autonavi.minimap.ajx3.dom.AjxDomNode");
    }

    /* access modifiers changed from: 0000 */
    public boolean isHasSectionHeader() {
        return this.hasSectionHeader;
    }

    /* access modifiers changed from: 0000 */
    public boolean isHasSectionFooter() {
        return this.hasSectionFooter;
    }

    public float getBeforeExpandCellHeight(long j) {
        return ((Float) this.mBeforeExpandCellHeightArray.get(j, Float.valueOf(-1.0f))).floatValue();
    }

    public AjxListCell getListCell(AjxListCell ajxListCell) {
        if (ajxListCell.mIsListCell) {
            return ajxListCell;
        }
        AjxDomGroupNode parent = ajxListCell.getParent();
        if (parent instanceof AjxListCell) {
            return getListCell((AjxListCell) parent);
        }
        return null;
    }

    public boolean isHeaderOrFooter(AjxListCell ajxListCell) {
        if (ajxListCell == null) {
            return false;
        }
        for (Section next : this.mSections) {
            if (next.mHeader != null && next.mHeader.getId() == ajxListCell.getId()) {
                return true;
            }
            if (next.mFooter != null && next.mFooter.getId() == ajxListCell.getId()) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void saveNodeToMap(AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            this.mNodeMap.put(ajxDomNode.getId(), ajxDomNode);
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null && children.size() > 0) {
                for (AjxDomNode saveNodeToMap : children) {
                    saveNodeToMap(saveNodeToMap);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void removeNodeFromMap(AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            this.mNodeMap.remove(ajxDomNode.getId());
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null && children.size() > 0) {
                for (AjxDomNode removeNodeFromMap : children) {
                    removeNodeFromMap(removeNodeFromMap);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void saveTemplateToMap(int i, AjxDomNode ajxDomNode) {
        if (!ajxDomNode.isTemplate()) {
            ajxDomNode.setIsTemplate();
        }
        this.mTemplateNodes.put(i, ajxDomNode);
        saveToMap(ajxDomNode);
    }

    /* access modifiers changed from: 0000 */
    public AjxDomNode findTemplateByNodeId(long j) {
        return (AjxDomNode) this.mTemplateMap.get(j, null);
    }

    public AjxDomNode findNodeById(long j) {
        return (AjxDomNode) this.mNodeMap.get(j, null);
    }

    private void saveToMap(AjxDomNode ajxDomNode) {
        if (ajxDomNode != null) {
            this.mTemplateMap.put(ajxDomNode.getId(), ajxDomNode);
            List<AjxDomNode> children = ajxDomNode.getChildren();
            if (children != null && children.size() > 0) {
                for (AjxDomNode saveToMap : children) {
                    saveToMap(saveToMap);
                }
            }
        }
    }

    public int getCellCount() {
        return this.mCells.size();
    }

    public LongSparseArray<AjxDomNode> getNodeMap() {
        return this.mNodeMap;
    }
}
