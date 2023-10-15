package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.converter.BarCodeTypeConverter;
import com.qamslink.mes.type.BarCodeType;
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
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mes_stock_barcode_print_detail")
@Erupt(name = "销售退货条码打印详情",
//        dataProxy = MesSaleReturnBarcodePrintDetailService.class,
        orderBy = "MesSaleReturnBarcodePrintDetail.id desc",
        filter = @Filter(value = "MesSaleReturnBarcodePrintDetail.stockBarcodePrint.tenantId",
                params = {"and MesSaleReturnBarcodePrintDetail.barcodeStatus = 1"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_stock_barcode_print_detail set barcode_status = 0 where id = ?")
public class MesSaleReturnBarcodePrintDetail extends HyperModelVo {

    @EruptField(
            views = @View(title = "条码类型"),
            edit = @Edit(title = "条码类型", readonly = @Readonly(), type = EditType.CHOICE,
                    choiceType = @ChoiceType(fetchHandler = BarCodeType.Handler.class))
    )
    @Convert(converter = BarCodeTypeConverter.class)
    private BarCodeType type;

    @ManyToOne
    @EruptField(
            views = {@View(title = "销售退货单号", column = "mesSaleReturnRecord.code"),
                    @View(title = "供应商", column = "mesSaleReturnRecord.customer.name"),
                    @View(title = "物料名称", column = "stock.name"),
                    @View(title = "物料编码", column = "stock.code"),
            },
            edit = @Edit(title = "销售退货单物料条码", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "mesSaleReturnRecord.code"))
    )
    private MesSaleReturnBarcodePrint saleReturnBarcodePrint;

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

    // 物料ID
    private Long stockId;

    // 标识补打
    private Boolean reprintFlag;
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
            views = @View(title = "来料检测状态"),
            edit = @Edit(title = "来料检测状态",
                    boolType = @BoolType(trueText = "已检验", falseText = "未检验"))
    )
    private Boolean iqcStatus = false;

    @EruptField(
            views = @View(title = "条码状态"),
            edit = @Edit(title = "条码状态",
                    boolType = @BoolType(trueText = "生效", falseText = "失效"))
    )
    private Boolean barcodeStatus = true;
}
