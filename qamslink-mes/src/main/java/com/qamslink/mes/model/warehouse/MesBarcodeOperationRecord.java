package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_barcode_operation_record")
@Erupt(name = "拆合码操作记录",
//        dataProxy = MesBarcodeOperationRecordService.class,
        orderBy = "MesBarcodeOperationRecord.createTime desc",
        power = @Power(add = false,edit = false,delete = false),
        filter = @Filter(value = "MesBarcodeOperationRecord.tenantId",
                params = {"and MesBarcodeOperationRecord.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_barcode_operation_record set deleted = true where id = ?")
public class MesBarcodeOperationRecord extends HyperModelCreatorVo {

    @ManyToOne
    @EruptField(
            views = @View(title = "条码编码", column = "barcodeNum"),
            edit = @Edit(title = "条码编码", search = @Search(vague = true), notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "barcodeNum"))
    )
    private MesStockBarcodePrintDetail stockBarcodePrintDetail;

    @ManyToOne
    @EruptField(
            views = @View(title = "产品", column = "name"),
            edit = @Edit(title = "产品", search = @Search(vague = true), notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", search = @Search, type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "拆码", value = "1"),
                                    @VL(label = "合码", value = "2")
                            }
                    ))
    )
    private Integer type;

    @EruptField(
            views = @View(title = "条码数量"),
            edit = @Edit(title = "条码数量",readonly = @Readonly(), numberType = @NumberType(min = 1))
    )
    private BigDecimal capacity;

    @EruptField(
            views = @View(title = "条码类型"),
            edit = @Edit(title = "条码类型", search = @Search, type = EditType.CHOICE,
                    readonly = @Readonly(),
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "产品批次条码", value = "1"),
                                    @VL(label = "物料条码", value = "2"),
                                    @VL(label = "废料条码", value = "3"),
                                    @VL(label = "历史产品条码", value = "4")
                            }
                    ))
    )
    private Integer barcodeType;

    @EruptField(
            views = @View(title = "打印机名称")
    )
    private String printerName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "barcode_operation_record_id")
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD, readonly = @Readonly()
            )
    )
    private List<MesBarcodeOperationRecordDetail> barcodeOperationRecordDetails;

    @EruptField(
            views = @View(title = "租户", show = false)
    )
    private Long tenantId;

    private Boolean deleted = false;
}
