package com.qamslink.mes.model.warehouse;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mes_stock_barcode_print_detail")
@Erupt(name = "产品条码打印详情"
//        ,
//        dataProxy = MesStockBarcodePrintDetailService.class
)
public class MesProductBatchPrinterDetail extends BaseModel {
    @EruptField(
            views = @View(title = "条码类型"),
            edit = @Edit(title = "条码类型", readonly = @Readonly(), type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "产品批次条码", value = "1"),
                            @VL(label = "物料条码", value = "2"),
                            @VL(label = "废料条码", value = "3"),
                            @VL(label = "历史产品条码", value = "4")
                    }))
    )
    private Integer type;

    @ManyToOne
    @EruptField(
            views = { @View(title = "生产工单", column = "workOrder.orderCode"),
                    @View(title = "物料名称", column = "stock.name")},
            edit = @Edit(title = "生产工单", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "workOrder.orderCode"))
    )
    private MesProductBatchPrinter productBatchPrinter;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", readonly = @Readonly())
    )
    private String barcodeNum;

    @EruptField(
            views = @View(title = "数量"),
            edit = @Edit(title = "数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

    @EruptField(
            views = @View(title = "条码可用量"),
            edit = @Edit(title = "条码可用量", numberType = @NumberType(min = 0))
    )
    private BigDecimal availableQuantity;

    @EruptField(
            views = @View(title = "入库时间"),
            edit = @Edit(title = "入库时间", readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date inDate;

    @ManyToOne
    @EruptField(
            views = @View(title = "存放库位", column = "locationCode"),
            edit = @Edit(title = "存放库位", readonly = @Readonly, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation location;

    @EruptField(
            views = @View(title = "产品检测状态"),
            edit = @Edit(title = "产品检测状态",
                    boolType = @BoolType(trueText = "已检验", falseText = "未检验"))
    )
    private Boolean pqcStatus = false;

    @EruptField(
            views = @View(title = "出货检测状态"),
            edit = @Edit(title = "出货检测状态",
                    boolType = @BoolType(trueText = "已检验", falseText = "未检验"))
    )
    private Boolean fqcStatus = false;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", search = @Search(vague = true), type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未入库", value = "0"),
                            @VL(label = "已物料入库", value = "1"),
                            @VL(label = "已成品入库", value = "2"),
                            @VL(label = "已成品出库", value = "3")
                    }
            ))
    )
    private Integer status;

    @EruptField(
            views = @View(title = "条码状态"),
            edit = @Edit(title = "条码状态",
                    boolType = @BoolType(trueText = "生效", falseText = "失效"))
    )
    private Boolean barcodeStatus;

    

    // 物料ID
    private Long stockId;

    // 标识补打
    private Boolean reprintFlag;
}