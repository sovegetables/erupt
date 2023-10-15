package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.converter.BarCodeTypeConverter;
import com.qamslink.mes.type.BarCodeType;
import com.qamslink.mes.model.production.MesOrderStockIn;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
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

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mes_stock_barcode_print_detail")
@Erupt(name = "批次条码",
//        dataProxy = MesStockBarcodePrintDetailService.class,
        orderBy = "MesStockBarcodePrintDetail.id desc",
        filter = @Filter(value = "MesStockBarcodePrintDetail.stockBarcodePrint.tenantId",
        params = {"and MesStockBarcodePrintDetail.barcodeStatus = 1"},
        conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_stock_barcode_print_detail set barcode_status = 0 where id = ?")
public class MesStockBarcodePrintDetail extends BaseModel {

    @EruptField(
            views = @View(title = "条码类型"),
            edit = @Edit(title = "条码类型", readonly = @Readonly(), type = EditType.CHOICE,
                    choiceType = @ChoiceType(fetchHandler = BarCodeType.Handler.class))
    )
    @Convert(converter = BarCodeTypeConverter.class)
    private BarCodeType type;

    @ManyToOne
    @EruptField(
            views = {@View(title = "采购订单号", column = "order.orderCode"),
                    @View(title = "供应商", column = "order.supplier.name"),
                    @View(title = "物料名称", column = "stock.name"),
                    @View(title = "物料编码", column = "stock.code"),
            },
            edit = @Edit(title = "采购订单物料条码", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "order.orderCode"))
    )
    private MesStockBarcodePrint stockBarcodePrint;

    @ManyToOne
    @EruptField(
            views = {@View(title = "采购入库单号", column = "sn")
            },
            edit = @Edit(title = "采购入库单", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "sn"))
    )
    private MesOrderStockIn orderStockIn;

    @Transient
    @EruptField(
            edit = @Edit(title = "物料名称", search = @Search(vague = true))
    )
    private String stockName;

    @Transient
    @EruptField(
            edit = @Edit(title = "物料编码", search = @Search(vague = true))
    )
    private String stockCode;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "生产工单", column = "workOrder.orderCode"),
            },
            edit = @Edit(title = "生产工单", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "workOrder.orderCode"))
    )
    private MesProductBatchPrinter productBatchPrinter;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", readonly = @Readonly(), search = @Search(vague = true))
    )
    private String barcodeNum;

    @EruptField(
            views = @View(title = "数量"),
            edit = @Edit(title = "数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

    @EruptField(
            views = @View(title = "可用量"),
            edit = @Edit(title = "可用量", numberType = @NumberType(min = 0))
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

    @EruptField(
            views = @View(title = "是否需要检验"),
            edit = @Edit(title = "是否需要检验",
                    boolType = @BoolType(trueText = "需要", falseText = "不需要"))
    )
    private Boolean needCheck = false;

    @EruptField(
            views = @View(title = "检验状态"),
            edit = @Edit(title = "检验状态",
                    boolType = @BoolType(trueText = "已检验", falseText = "未检验"))
    )
    private Boolean iqcStatus = false;

    @EruptField(
            views = @View(title = "条码状态"),
            edit = @Edit(title = "条码状态",
                    boolType = @BoolType(trueText = "生效", falseText = "失效"))
    )
    private Boolean barcodeStatus = true;

    // 物料ID
    private Long stockId;

    // 标识补打
    private Boolean reprintFlag;
}
