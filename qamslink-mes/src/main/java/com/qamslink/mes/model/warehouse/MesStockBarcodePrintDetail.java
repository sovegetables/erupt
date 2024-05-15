package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.converter.BarCodeTypeConverter;
import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.supplier.PurMaterialGenerator;
import com.qamslink.mes.type.BarCodeType;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "mes_stock_barcode_record")
@Erupt(name = "批次条码",
        orderBy = "MesStockBarcodePrintDetail.id desc",
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesStockBarcodePrintDetail extends BaseModel {

    @EruptField(
            views = @View(title = "条码类型", show = false),
            edit = @Edit(title = "条码类型", readonly = @Readonly(), type = EditType.CHOICE,
                    choiceType = @ChoiceType(fetchHandler = BarCodeType.Handler.class))
    )
    @Convert(converter = BarCodeTypeConverter.class)
    private BarCodeType type;

    @ManyToOne
    @EruptField(
//            views = {
//                    @View(title = "采购订单号", column = "order.orderCode"),
//                    @View(title = "供应商", column = "order.supplier.name"),
//                    @View(title = "物料名称", column = "stock.name"),
//                    @View(title = "物料编码", column = "stock.code"),
//            },
            edit = @Edit(title = "采购订单号",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "purOrder_code"))
    )
    private PurMaterialGenerator purBatchBarcodeId;

//    @ManyToOne
//    @EruptField(
//            views = {@View(title = "采购入库单号", column = "sn")
//            },
//            edit = @Edit(title = "采购入库单", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "sn"))
//    )
//    private MesOrderStockIn orderStockIn;
//
//    @ManyToOne
//    @EruptField(
//            views = {
//                    @View(title = "生产工单", column = "workOrder.orderCode"),
//            },
//            edit = @Edit(title = "生产工单", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "workOrder.orderCode"))
//    )
//    private MesProductBatchPrinter productBatchPrinter;

//    @Transient
//    @EruptField(
//            edit = @Edit(title = "物料名称", search = @Search(vague = true))
//    )
//    private String stockName;
//
//    @Transient
//    @EruptField(
//            edit = @Edit(title = "物料编码", search = @Search(vague = true))
//    )
//    private String stockCode;

    @EruptField(
            views = @View(title = "条码"),
            edit = @Edit(title = "条码", readonly = @Readonly(), search = @Search(vague = true))
    )
    private String barcodeNum;

    @EruptField(
            views = @View(title = "数量"),
            edit = @Edit(title = "数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料编码", column = "code", width = "200px"),
                    @View(title = "物料名称", column = "name", width = "120px"),
                    @View(title = "规格型号", column = "spec", width = "120px"),
            },
            edit = @Edit(title = "物料编码", notNull = true,
                    search = @Search(vague = true), type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "可用量"),
            edit = @Edit(title = "可用量", numberType = @NumberType(min = 0))
    )
    private BigDecimal availableQuantity;

//    @EruptField(
//            views = @View(title = "入库时间"),
//            edit = @Edit(title = "入库时间", readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
//    )
//    private Date inDate;

    @ManyToOne
    @EruptField(
            views = @View(title = "存放库位", column = "locationCode"),
            edit = @Edit(title = "存放库位", readonly = @Readonly, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation location;

    @ManyToOne
    @EruptField(
            views = @View(title = "存放仓库", column = "name"),
            edit = @Edit(title = "存放仓库", readonly = @Readonly, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "name"))
    )
    private MesWarehouse warehouse;

    public static final int STATUS_IN = 1;
    public static final int STATUS_OUT = 0;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态",
                    search = @Search(vague = true),
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未入库", value = STATUS_OUT + ""),
                            @VL(label = "已入库", value = STATUS_IN + ""),
                    })
            )
    )
    private Integer status = 0;

//    @EruptField(
//            views = @View(title = "是否需要检验"),
//            edit = @Edit(title = "是否需要检验",
//                    boolType = @BoolType(trueText = "需要", falseText = "不需要"))
//    )
//    private Boolean needCheck = false;
//
//    @EruptField(
//            views = @View(title = "检验状态"),
//            edit = @Edit(title = "检验状态",
//                    boolType = @BoolType(trueText = "已检验", falseText = "未检验"))
//    )
//    private Boolean iqcStatus = false;

    @EruptField(
            views = @View(title = "条码状态"),
            edit = @Edit(title = "条码状态",
                    boolType = @BoolType(trueText = "生效", falseText = "失效"))
    )
    private Boolean barcodeStatus = true;
}
