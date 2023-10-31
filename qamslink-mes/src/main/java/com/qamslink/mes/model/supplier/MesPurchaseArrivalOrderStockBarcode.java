package com.qamslink.mes.model.supplier;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.production.PurOrder;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "mes_purchase_arrival_order_stock_barcode")
@Erupt(name = "采购到货单物料条码详情")
@Getter
@Setter
public class MesPurchaseArrivalOrderStockBarcode extends BaseModel {

    @ManyToOne
    private PurOrder order;

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "单位", column = "unit")},
            edit = @Edit(title = "物料名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = {@View(title = "到货数量", column = "arrivalQuantity"),
                    @View(title = "入库数量", column = "receiptQuantity"),
                    @View(title = "拒收数量", column = "rejectQuantity")}
    )
    private MesPurchaseArrivalOrderStock mesPurchaseArrivalOrderStock;

    @EruptField(
            views = {@View(title = "条码编号")},
            edit = @Edit(title = "条码编号", notNull = true, numberType = @NumberType(min = 0))
    )
    private String barcodeNum;
}

