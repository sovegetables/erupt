package com.qamslink.mes.model.warehouse;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "mes_picking_out")
@Erupt(name = "领料出库")
public class MesPickingOut extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "领料单", column = "pickingCode"),
            edit = @Edit(title = "领料单", notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "pickingCode"))
    )
    private MesPickingRecord pickingRecord;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", notNull = true)
    )
    private String barcodeNum;

    @EruptField(
            views = @View(title = "出库时间"),
            edit = @Edit(title = "出库时间", notNull = true, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date receivingTime;
}
