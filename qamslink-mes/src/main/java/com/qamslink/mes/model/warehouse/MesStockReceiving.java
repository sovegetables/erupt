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

@Getter
@Setter
@Entity
@Table(name = "mes_stock_receiving")
@Erupt(name = "物料入库")
public class MesStockReceiving extends BaseModel {

    // 条码编号
    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号")
    )
    private String barcodeNum;

    // 库位ID
    @ManyToOne
    @EruptField(
            views = {@View(title = "库位", column = "warehouse.code",show = false),
                    @View(title = "库位", column = "storageSiteCode",show = false),
                    @View(title = "库位", column = "locationCode",show = false),
                    @View(title = "库位", column = "locationCode",template = "item.location_warehouse_code + '-' + item.location_storageSiteCode + '-' + item.location_locationCode"),
            },
            edit = @Edit(title = "库位", notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))

    )
    private MesLocation location;

    // 收货时间
    @EruptField(
            views = @View(title = "收货时间"),
            edit = @Edit(title = "收货时间", dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date receivingTime;
}
