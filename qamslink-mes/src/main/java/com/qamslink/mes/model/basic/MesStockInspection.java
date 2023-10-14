package com.qamslink.mes.model.basic;

import com.qamslink.mes.model.quality.MesInspectionItem;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_stock_inspection")
@Setter
@Getter
@Erupt(name = "物料检测项")
public class MesStockInspection extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "检测项id",column = "name"),
            edit = @Edit(title = "检测项id",type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesInspectionItem inspectionItem;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料id",column = "name"),
            edit = @Edit(title = "物料id",type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesStock stock;
}
