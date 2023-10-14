package com.qamslink.mes.model.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "mes_allocation_in")
@Erupt(name = "调拨入库")
public class MesAllocationIn extends BaseModel {
    @ManyToOne
    @EruptField(
            views = @View(title = "调拨单", column = "code"),
            edit = @Edit(title = "调拨单", notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesAllocationRecord allocationRecord;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", notNull = true)
    )
    private String barcodeNum;

    @EruptField(
            views = @View(title = "物料编码"),
            edit = @Edit(title = "物料编码", notNull = true)
    )
    private String stockNum;

    @EruptField(
            views = @View(title = "调入数量"),
            edit = @Edit(title = "调入数量", notNull = true)
    )
    private BigDecimal capacity;

    @ManyToOne
    @EruptField(
            views = @View(title = "调入库位"),
            edit = @Edit(title = "调入库位", notNull = false)
    )
    private MesLocation location;

    @EruptField(
            views = @View(title = "入库时间"),
            edit = @Edit(title = "入库时间", notNull = true, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date receivingTime;
}
