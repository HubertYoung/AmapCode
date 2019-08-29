package com.autonavi.minimap.ajx3.dom.managers;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.JsDomEventListCellData;
import com.autonavi.minimap.ajx3.dom.JsDomEventListDataAttribute;
import com.autonavi.minimap.ajx3.dom.JsDomEventListDataProperty;
import com.autonavi.minimap.ajx3.dom.JsDomEventListDataSizeChange;
import com.autonavi.minimap.ajx3.dom.JsDomEventListInit;
import com.autonavi.minimap.ajx3.dom.JsDomEventListSection;
import com.autonavi.minimap.ajx3.dom.JsDomEventListTemplateAdd;
import com.autonavi.minimap.ajx3.dom.JsDomEventListTemplateAttribute;
import com.autonavi.minimap.ajx3.dom.JsDomEventListTemplateProperty;
import com.autonavi.minimap.ajx3.dom.JsDomList;
import com.autonavi.minimap.ajx3.dom.ajxnode.AjxListDomNode;

public class AjxListManager extends AjxUiEventManager {
    public void destroy() {
    }

    public AjxListManager(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public boolean listInitEvent(JsDomEventListInit jsDomEventListInit) {
        JsDomList jsDomList = jsDomEventListInit.list;
        if (jsDomList == null) {
            return false;
        }
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomList.getId());
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).updateData(this.mAjxContext, jsDomList);
        }
        return false;
    }

    public boolean listTemplateAddEvent(JsDomEventListTemplateAdd jsDomEventListTemplateAdd) {
        if (jsDomEventListTemplateAdd.node == null) {
            return false;
        }
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListTemplateAdd.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).templateAddEvent(jsDomEventListTemplateAdd);
        }
        return false;
    }

    public boolean listSectionAddEvent(JsDomEventListSection jsDomEventListSection) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListSection.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).sectionAddEvent(jsDomEventListSection);
        }
        return false;
    }

    public boolean listSectionReplaceEvent(JsDomEventListSection jsDomEventListSection) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListSection.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).sectionReplaceEvent(jsDomEventListSection);
        }
        return false;
    }

    public boolean listSectionRemoveEvent(JsDomEventListSection jsDomEventListSection) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListSection.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).sectionRemoveEvent(jsDomEventListSection);
        }
        return false;
    }

    public boolean listDataAddEvent(JsDomEventListCellData jsDomEventListCellData) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListCellData.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).dataAddEvent(jsDomEventListCellData);
        }
        return false;
    }

    public boolean listDataReplaceEvent(JsDomEventListCellData jsDomEventListCellData) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListCellData.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).dataReplaceEvent(jsDomEventListCellData);
        }
        return false;
    }

    public boolean listDataRemoveEvent(JsDomEventListCellData jsDomEventListCellData) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListCellData.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).dataRemoveEvent(jsDomEventListCellData);
        }
        return false;
    }

    public boolean listDataSizeChangeEvent(JsDomEventListDataSizeChange jsDomEventListDataSizeChange) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListDataSizeChange.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).dataSizeChangeEvent(jsDomEventListDataSizeChange);
        }
        return false;
    }

    public boolean listDataAttributeAddEvent(JsDomEventListDataAttribute jsDomEventListDataAttribute) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListDataAttribute.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).dataAttributeAddEvent(jsDomEventListDataAttribute);
        }
        return false;
    }

    public boolean listDataStyleAddEvent(JsDomEventListDataProperty jsDomEventListDataProperty) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListDataProperty.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).dataStyleAddEvent(jsDomEventListDataProperty);
        }
        return false;
    }

    public boolean listDataAttributeRemoveEvent(JsDomEventListDataAttribute jsDomEventListDataAttribute) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListDataAttribute.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).dataAttributeRemoveEvent(jsDomEventListDataAttribute);
        }
        return false;
    }

    public boolean listDataStyleRemoveEvent(JsDomEventListDataProperty jsDomEventListDataProperty) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListDataProperty.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).dataStyleRemoveEvent(jsDomEventListDataProperty);
        }
        return false;
    }

    public boolean listTemplateAttributeChange(JsDomEventListTemplateAttribute jsDomEventListTemplateAttribute) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListTemplateAttribute.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).templateAttributeChange(jsDomEventListTemplateAttribute);
        }
        return false;
    }

    public boolean listTemplateStyleChange(JsDomEventListTemplateProperty jsDomEventListTemplateProperty) {
        AjxDomNode findNodeById = this.mAjxContext.getDomTree().findNodeById(jsDomEventListTemplateProperty.listId);
        if (findNodeById instanceof AjxListDomNode) {
            return ((AjxListDomNode) findNodeById).templateStyleChange(jsDomEventListTemplateProperty);
        }
        return false;
    }
}
