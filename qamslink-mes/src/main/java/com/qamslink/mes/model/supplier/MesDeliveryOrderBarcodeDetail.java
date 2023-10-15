package com.qamslink.mes.model.supplier;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.basic.MesUnitMeasureCode;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "mes_delivery_order_stock_barcode")
@Erupt(name = "送货单物料扫码详情")
@Getter
@Setter
public class MesDeliveryOrderBarcodeDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code")
            },
            edit = @Edit(
                    title = "物料名称",
                    notNull = true,
                    search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE
            )
    )
    private MesStock stock;

    @EruptField(
            views = {@View(title = "条码编码")},
            edit = @Edit(title = "条码编码", notNull = true)
    )
    private String barcodeNum;

    @EruptField(
            views = @View(title = "单位条码数"),
            edit = @Edit(title = "单位条码数", numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

    @ManyToOne
    @EruptField(
            views = @View(title = "计量单位" ,column = "name"),
            edit = @Edit(title = "计量单位", notNull = true,type = EditType.REFERENCE_TREE)

    )
    private MesUnitMeasureCode unitMeasureCode;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "数量", column = "num"),
                    @View(title = "交期", column = "endDate")
            },
            edit = @Edit(
                    title = "送货单物料详情",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "id")
            )
    )
    private MesDeliveryOrderFeeding deliveryOrderStock;


}

