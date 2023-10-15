package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.production.MesWorkOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author LiYongGui
 * @create 2023-07-28 下午4:35
 */
@Setter
@Getter
@Entity
@Table(name = "mes_production_barcode_record")
@Erupt(name = "生产条码记录",
//        dataProxy = MesProductionBarcodeRecordService.class,
        orderBy = "MesProductionBarcodeRecord.createTime desc",
        filter = @Filter(value = "MesProductionBarcodeRecord.tenantId",
                params = {"and MesProductionBarcodeRecord.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false))
@SQLDelete(sql = "update mes_production_barcode_record set deleted = true where id = ?")
public class MesProductionBarcodeRecord extends HyperModelCreatorVo {
    private static final long serialVersionUID = -5378409609599527472L;


    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", type = EditType.INPUT, search = @Search)
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产工单", column = "orderCode"),
            edit = @Edit(title = "生产工单", notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "产品名称", column = "name"),
                    @View(title = "产品编码", column = "code"),
            },
            edit = @Edit(title = "产品", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search)
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "可用量"),
            edit = @Edit(title = "可用量", numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

    @EruptField(
            views = @View(title = "打印机"),
            edit = @Edit(title = "打印机", type = EditType.TEXTAREA)
    )
    private String printerName;

    @EruptField(
            views = @View(title = "检验状态"),
            edit = @Edit(title = "检验状态", type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "已检验", value = "0"),
                            @VL(label = "已生产检验", value = "1"),
                            @VL(label = "已出货检验", value = "2")
                    }
            ))
    )
    private Integer inspectStatus;

    @EruptField(
            views = @View(title = "条码状态"),
            edit = @Edit(title = "条码状态", type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未入库", value = "0"),
                            @VL(label = "已物料入库", value = "1"),
                            @VL(label = "已成品入库", value = "2"),
                            @VL(label = "已成品出库", value = "3"),
                            @VL(label = "已退货出库", value = "4")
                    }
            ), search = @Search)
    )
    private Integer barcodeStatus;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;



    private Boolean deleted = false;

    @EruptField
    private Long warehouseId;

    @EruptField
    private Long locationId;

}
