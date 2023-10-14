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

@Getter
@Setter
@Entity
@Table(name = "mes_feeding_operation_record_detail")
@Erupt(name = "领料单明细"
//        , dataProxy = MesFeedingOperationRecordDetailService.class
)
public class MesFeedingOperationRecordDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "生产任务", column = "productionScheduleDetail.code")},
            edit = @Edit(title = "生产任务", type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "productionScheduleDetail.code"))
    )
    private MesFeedingOperationRecord feedingOperationRecord;

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

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", readonly = @Readonly())
    )
    private String barcodeNum;

    @EruptField(
            views = @View(title = "物料名称"),
            edit = @Edit(title = "物料名称", search = @Search(vague = true))
    )
    private String stockName;

    @EruptField(
            views = @View(title = "物料编码"),
            edit = @Edit(title = "物料编码")
    )
    private String stockCode;

    @EruptField(
            views = @View(title = "条码数量"),
            edit = @Edit(title = "条码数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

    @EruptField(
            views = @View(title = "使用量"),
            edit = @Edit(title = "使用量", numberType = @NumberType(min = 0))
    )
    private BigDecimal feedingAmount;
}
